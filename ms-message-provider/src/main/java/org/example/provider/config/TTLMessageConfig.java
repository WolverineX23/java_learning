package org.example.provider.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TTLMessageConfig {

    //声明队列ttl.queue，设置超时时间
    @Bean
    public Queue ttlQueue(){
        return QueueBuilder.durable("ttl.queue") // 指定队列名称，并持久化
                .ttl(10000) // 设置队列的超时时间，10秒
                .deadLetterExchange("dl.direct") // 队列指定死信交换机，即消息超时就投到这个交换机
                .deadLetterRoutingKey("dl")//消息到死信交换机的RoutingKey
                .build();
    }
    //正常的声明交换机ttl.direct
    @Bean
    public DirectExchange ttlExchange(){
        return new DirectExchange("ttl.direct");
    }
    //正常的绑定交换机和队列，routingKey为ttl
    @Bean
    public Binding ttlBinding(){
        return BindingBuilder.bind(ttlQueue()).to(ttlExchange()).with("ttl");
    }
}
