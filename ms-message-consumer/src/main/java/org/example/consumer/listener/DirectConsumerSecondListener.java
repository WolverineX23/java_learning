package org.example.consumer.listener;

import org.example.commons.pojo.Student;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者2号：用于消费队列中消息
 *
 */
//@Component
//@RabbitListener(queues = {"TestDirectQueue"})       // 监听的队列列表
public class DirectConsumerSecondListener {

    /**
     * Object 类型： 可接收所有类型的消息，如 String、jdk 默认序列化的 Student 对象、 JSON 序列化的 Student 对象...
     * Student 类型：只会接收经过 JSON 序列化发送到 RabbitMQ 的 Student 对象
     *
     * @param msg
     */
//    @RabbitHandler
    public void process(Student msg) throws InterruptedException {

        Thread.sleep(3000);     // 模拟业务执行
        System.out.println("Direct Consumer - 2 get msg: " + msg);
    }



}
