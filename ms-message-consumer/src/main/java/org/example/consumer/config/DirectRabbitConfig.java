package org.example.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消费者单纯的使用，其实可以不用添加这个配置，直接使用注解来让监听器监听对应的队列即可。
 * 配置上了的话，其实消费者也是生成者的身份，也能推送该消息。
 *
 */
@Configuration
public class DirectRabbitConfig {

    @Bean
    public Jackson2JsonMessageConverter messageConverter(){         //注意导入的包
        return new Jackson2JsonMessageConverter();
    }

    // Queue 队列 起名：TestDirectQueue
    @Bean
    public Queue TestDirectQueue() {

        return new Queue("TestDirectQueue", true);
    }

    // Direct 交换机, 起名：TestDirectExchange
    @Bean
    DirectExchange TestDirectExchange() {

        return new DirectExchange("TestDirectExchange");
    }

    // Binding 绑定, 将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    Binding bindingDirect() {

        return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with("TestDirectRouting");
    }
}
