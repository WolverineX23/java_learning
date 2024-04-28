package org.example.consumer.listener;

import com.rabbitmq.client.AMQP;
import org.example.commons.pojo.Student;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;


/**
 * 消费者1号：用于消费队列中消息
 *
 */
@Component
@RabbitListener(queues = {"TestDirectQueue"})       // 监听的队列列表
// @RabbitListener(queues = "TestDirectQueue")      // 若单个队列，可省略{}
public class DirectConsumerListener {

    //

    /**
     * Object 类型： 可接收所有类型的消息，如 String、jdk 默认序列化的 Student 对象、 JSON 序列化的 Student 对象...
     * Student 类型：只会接收经过 JSON 序列化发送到 RabbitMQ 的 Student 对象
     *
     * @param msg
     */
    @RabbitHandler
    public void process(Student msg) {
        System.out.println("Direct Consumer - 1 get msg: " + msg);
    }

    /**
     *  Message: 原生消息类型，详细信息 消息头 + 消息体
     *  T: 发送的消息类型，比如这里的发送消息方发送的类型是 Student
     *  Channel: 当前传输数据的通道
     *
     * @param message
     * @param content
     * @param channel
     */
    /*
    @RabbitListener(queues = {"TestDirectQueue"})
    public void oriProcess(Message message, Student content, AMQP.Channel channel) {
        byte[] body = message.getBody();
        System.out.println(Arrays.toString(body));

        // 消息头属性信息
        MessageProperties messageProperties = message.getMessageProperties();

        System.out.println(messageProperties);
        System.out.println("接收到消息...内容："+message+"===>消息对象:"+content);
    }
     */

}
