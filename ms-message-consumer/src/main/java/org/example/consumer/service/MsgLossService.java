package org.example.consumer.service;


import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MsgLossService implements ChannelAwareMessageListener {

    private int n = 0;

    /**
     * 另一种 监听方式
     * 获取 DELIVERY_TAG：@Header(AmqpHeaders.DELIVERY_TAG) long tag
     *
     * @param message
     * @param channel
     * @param tag
     * @throws IOException
     */
//    @RabbitListener(queues = "TestDirectQueue")
    private void messageConsumer(Message message,
                                 Channel channel,
                                 @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        // ...
    }

    /**
     * 重写 ChannelAwareMessageListener 接口方法
     * 获取 DELIVERY_TAG：message.getMessageProperties().getDeliveryTag();
     * ackMode = "MANUAL" : 手动发送 确认消费 模式
     *
     * @param message
     * @param channel
     * @throws Exception
     */
    @Override
    @RabbitListener(queues = "TestDirectQueue", ackMode = "MANUAL")
    public void onMessage(Message message, Channel channel) throws Exception {

        try {
            // 处理消息逻辑
            processMessage(message);

            // 成功处理后手动确认消息
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            long deliveryTag = message.getMessageProperties().getDeliveryTag();

            // 处理失败，可选择重新入队列 - 取决于业务需求
            channel.basicNack(deliveryTag, false, shouldRequeueOnFailure());
        }
    }

    // 丢失的消息是否重新载入队列
    private boolean shouldRequeueOnFailure() {
        // 根据业务需求决定是否重新入队列
        return true; // 或者 false
    }

    /**
     * 消费逻辑
     * 模拟 消息丢失
     *
     * @param message
     * @throws Exception
     */
    private void processMessage(Message message) throws Exception {
        n++;

        /* 模拟某一条消息 消费失败
        if (n == 3) {
            throw new Exception("模拟第三个消息 消费失败！");
        }
         */

        // 模拟 MQ 宕机
        Thread.sleep(4000);

        System.out.println("Processing message: " + new String(message.getBody()));
        System.out.println("Process: " + n);
    }

}
