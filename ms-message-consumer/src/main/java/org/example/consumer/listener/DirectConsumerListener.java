package org.example.consumer.listener;

import com.rabbitmq.client.Channel;
import org.example.commons.pojo.OrderEntity;
import org.example.commons.pojo.OrderItemEntity;
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
//@Component
//@RabbitListener(queues = {"TestDirectQueue"})       // 监听的队列列表
// @RabbitListener(queues = "TestDirectQueue")      // 若单个队列，可省略{}
public class DirectConsumerListener {

//    @RabbitHandler
    public void receiveMsg(OrderEntity message) {

        System.out.println("接收到消息：" + message);
    }

//    @RabbitHandler
    public void receiveMsg(OrderItemEntity message) {

        System.out.println("接收到消息2：" + message);
    }

    /* 该方法不可行，会将消息传到 私信队列
    @RabbitHandler
    public void receiveMsg(Message message,
                           OrderEntity content,
                           Channel channel) {

        System.out.println("接收到消息...内容：" + message + "===>对象：" + content);
    }

    @RabbitHandler
    public void receiveMsg2(Message message,
                           OrderItemEntity content,
                           Channel channel) {

        System.out.println("接收到消息...内容：" + message + "===>对象：" + content);
    }
     */

    /**
     * Object 类型： 可接收所有类型的消息，如 String、jdk 默认序列化的 Student 对象、 JSON 序列化的 Student 对象...
     * Student 类型：只会接收经过 JSON 序列化发送到 RabbitMQ 的 Student 对象
     *
     * @param msg
     */
//    @RabbitHandler
    public void process(Student msg) throws InterruptedException {

        Thread.sleep(3000);     // 模拟业务执行
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
//    @RabbitListener(queues = {"TestDirectQueue"})
    public void oriProcess(Message message, Student content, Channel channel) {
        byte[] body = message.getBody();
        System.out.println(Arrays.toString(body));

        // 消息头属性信息
        MessageProperties messageProperties = message.getMessageProperties();

        System.out.println(messageProperties);
        System.out.println("接收到消息...内容："+message+"===>消息对象:"+content);
    }

}
