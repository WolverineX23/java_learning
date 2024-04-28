package org.example.consumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableRabbit
@SpringBootApplication
public class MsgConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsgConsumerApplication.class, args);
    }
}
