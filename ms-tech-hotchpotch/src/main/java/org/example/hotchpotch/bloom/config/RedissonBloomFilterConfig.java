package org.example.hotchpotch.bloom.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Redisson 布隆过滤器配置类
 *
 */
@Configuration
public class RedissonBloomFilterConfig {

    @Resource
    private RedissonClient redissonClient;

    @Bean
    public RBloomFilter<String> bloomFilter() {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter("bloom_test");
        bloomFilter.tryInit((long) 1E8, 0.003);

        return bloomFilter;
    }
}
