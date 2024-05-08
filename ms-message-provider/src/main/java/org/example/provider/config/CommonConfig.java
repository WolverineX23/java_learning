package org.example.provider.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

// ApplicationContextAware 保证在项目加载时添加配置
@Slf4j
@Configuration
public class CommonConfig implements ApplicationContextAware {

    //实现了ApplicationContextAware接口的实现类，在Spring容器的Bean工厂创建完毕后会通知该实现类
    //有了Bean工厂类，然后就可以获取并设置ReturnCallback（Spring容器的Bean对象）
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // (从Spring容器中)获取RabbitTemplate对象
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        // 配置ReturnCallback
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            // 记录日志
            log.error("消息发送到队列失败，响应码：{}, 失败原因：{}, 交换机: {}, 路由key：{}, 消息: {}",
                    replyCode, replyText, exchange, routingKey, message.toString());
            // 如果有需要的话，重发消息
        });
    }
}
