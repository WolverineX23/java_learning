package org.example.provider.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import lombok.extern.slf4j.Slf4j;
import org.example.commons.enums.RabbitConsumeStatusEnum;
import org.example.commons.enums.RedisKeyEnum;
import org.example.commons.utils.SnowflakeIdUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * MqService: 幂等性设计 - 生产端
 *
 */

@Service
@Slf4j
public class MqService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private SnowflakeIdUtil snowflakeIdUtil;

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

     /**
     * 批量发送消息
     *
     * @param message
     */
    public void sendQueueBatch(String message) {

        //请求头设置消息id（messageId）
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        for (int i = 0; i < 3; i++) {
            long id = snowflakeIdUtil.nextId();
            map.put("id", id);
            JSONObject entries = JSONUtil.parseObj(map);
            String redisKey = RedisKeyEnum.MQ_STATUS.getKey() + id;
            redisTemplate.opsForValue().set(redisKey, RabbitConsumeStatusEnum.CONSUME.getCode(), 60, TimeUnit.SECONDS);
            rabbitTemplate.convertAndSend("direct.exchange", "direct.key", entries);
        }
        log.info("3个消息都发送成功");
    }

}
