package org.example.provider.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeadLetterRabbitConfig {

    // 【声明普通的 simple.queue队列，并且为其指定死信交换机：dl.direct】
    @Bean
    public Queue simpleQueue2(){
        return QueueBuilder.durable("simple.queue")     // 指定队列名称，并持久化
                .deadLetterExchange("dl.direct")        // 【指定死信交换机】
                .build();
    }
    // 声明死信交换机 dl.direct
    @Bean
    public DirectExchange dlExchange(){
        return new DirectExchange("dl.direct", true, false);
    }

    // 声明存储死信的队列 dl.queue
    @Bean
    public Queue dlQueue(){
        return new Queue("dl.queue", true);
    }

    // 将死信队列 与 死信交换机绑定
    @Bean
    public Binding dlBinding(){
        return BindingBuilder.bind(dlQueue()).to(dlExchange()).with("simple");
    }

}
