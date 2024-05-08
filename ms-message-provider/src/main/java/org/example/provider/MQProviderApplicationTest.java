package org.example.provider;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MQProviderApplicationTest {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage2SimpleQueue() throws InterruptedException {
        // 1.准备消息
        String message = "hello, spring amqp!";
        // 2.准备CorrelationData（消息ID需要封装到CorrelationData）
        // 2.1.消息ID,确认机制发送消息时，需要给每个消息设置一个全局唯一id，以区分不同消息，避免ack冲突
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        // 2.2.准备ConfirmCallback（Future是对将来的一种处理的封装）（Future.addCallback）
        correlationData.getFuture().addCallback(
                result -> {
                    // 判断结果
                    if (result.isAck()) {
                        // ACK
                        log.debug("消息成功投递到交换机！消息ID: {}", correlationData.getId());
                    } else {
                        // NACK
                        log.error("消息投递到交换机失败！消息ID：{}", correlationData.getId());
                        // 重发消息
                    }
                },
                ex -> {
                    // 记录日志
                    log.error("消息发送失败！", ex);
                    // 重发消息
                });
        // 3.发送消息（这里如果没有绑定交换机和队列关系等，可以去管控台绑定，也可以在消费者的配置类中声明）
        rabbitTemplate.convertAndSend("TestDirectExchge", "TestDirectRouting", message, correlationData);
    }
}
