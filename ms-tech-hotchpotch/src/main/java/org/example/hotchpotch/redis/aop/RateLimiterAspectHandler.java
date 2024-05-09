package org.example.hotchpotch.redis.aop;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.hotchpotch.redis.annotation.RateLimiter;
import org.example.hotchpotch.redis.annotation.RateLimiters;
import org.example.hotchpotch.redis.exception.RateLimiterException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

/**
 * 限流切面类：拦截限流注解
 * 1. 切面是针对所有使用了 @RateLimiter 或 @RateLimiters 注解的方法
 * 2. 首先是获取定义的 key 的值，设置了 key 就是针对特定一类限流，没设置就是针对整个接口限流
 * 3. 获取一个方法的唯一值作为 Redis 中 key 的一部分，这里是获取类路径+方法名，然后计算 md5 值作为这个前缀
 * 4. 拼接成最后的 Redis 的 key，传到需要操作的 Lua 脚本中
 * 5. 执行lua脚本，传入的 key 就是 KEYS[] ，传入的参数就是 ARGV[] ，下标从 1 开始取值，参数要注意类型
 * 6. 如果未获取到则抛出异常（限流了），做一个全局异常捕获，统一返回处理
 *
 */
@Slf4j
@Component
@Aspect
public class RateLimiterAspectHandler {

    @Resource
    private RedisScript<Boolean> redisScript;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RateLimiterKeyProvider keyProvider;

    @Around(value = "@annotation(rateLimiter)", argNames = "point,rateLimiter")
    public Object around(ProceedingJoinPoint point, RateLimiter rateLimiter) throws Throwable {
        isAllow(point, rateLimiter);
        return point.proceed();
    }

    @Around(value = " @annotation(rateLimiters)", argNames = "point,rateLimiters")
    public Object around(ProceedingJoinPoint point, RateLimiters rateLimiters) throws Throwable {
        RateLimiter[] limiters = rateLimiters.value();
        for (RateLimiter rateLimiter : limiters) {
            isAllow(point, rateLimiter);
        }
        return point.proceed();
    }

    private void isAllow(ProceedingJoinPoint point, RateLimiter rateLimiter) {
        // 获取key
        String key = keyProvider.getKey(point, rateLimiter);

        // 类路径+方法，然后计算md5
        String uniqueKey = getUniqueKey((MethodSignature) point.getSignature());
        // key名称
        key = StringUtils.isNotBlank(key) ? uniqueKey + "." + key : uniqueKey;
        // 拼接成最后的 Redis 的键,传入需要操作的 key 到 lua 脚本中
        List<String> operateKeys = getOperateKeys(key);

        // 执行 lua 脚本
        Boolean allowed = this.redisTemplate.execute(redisScript, operateKeys, rateLimiter.capacity(), rateLimiter.rate(), Instant.now().getEpochSecond(), 1);
        log.info("rateLimiter {}, result is {}", key, allowed);
        if (Boolean.FALSE.equals(allowed)) {
            log.warn("触发限流，key is : {} ", key);
            throw new RateLimiterException(rateLimiter.showPromptMsg());
        }
    }

    private String getUniqueKey(MethodSignature signature) {
        String format = String.format("%s.%s", signature.getDeclaringTypeName(), signature.getMethod().getName());
        return DigestUtils.md5DigestAsHex(format.getBytes(StandardCharsets.UTF_8));
    }

    private List<String> getOperateKeys(String id) {
        String tokenKey = "request_rate_limiter.{" + id + "}.token";
        String timestampKey = "request_rate_limiter.{" + id + "}.timestamp";
        return Arrays.asList(tokenKey, timestampKey);
    }

}
