package org.example.provider.controller;

import org.example.commons.pojo.OrderEntity;
import org.example.commons.pojo.OrderItemEntity;
import org.example.commons.pojo.Student;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/provider")
public class ProviderController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    // 单播模式发送消息
    @GetMapping("/sendDirectMessage")
    public ResponseEntity<String> sendDirectMessage(@RequestBody Student student) {

        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", student);
        return ResponseEntity.ok("Succeed to send the msg");
    }

    // 批量发送不同类型的消息
    @GetMapping("/sendDiffMsg")
    public ResponseEntity<String> sendDifferentMessage(@RequestParam Integer num) {

        OrderEntity orderEntity = OrderEntity.builder()
                .Id(1L)
                .commentTime(new Date())
                .createTime(new Date())
                .confirmStatus(0)
                .build();

        OrderItemEntity orderItemEntity = OrderItemEntity.builder()
                .id(1L)
                .categoryId(225L)
                .orderSn("mall")
                .spuName("华为")
                .build();

        for (int i = 0; i < num; i++) {
            if(i % 2 == 0){
                orderEntity.setReceiverName("FIRE-" + i);
                // 这里使用 CorrelationData, 用于消息标识(UUID), 消息追踪和确认处理
                rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", orderEntity, new CorrelationData(UUID.randomUUID().toString().replace("-","")));
            }else {
                orderItemEntity.setOrderSn("mall-" + i);
                rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", orderItemEntity, new CorrelationData(UUID.randomUUID().toString().replace("-","")));
            }
        }

        return ResponseEntity.ok("Succeed to send different msg!");
    }

    @GetMapping("/simulatedLoss")
    public ResponseEntity<String> simulatedLoss() {
        for (int i = 1; i <= 3; i++) {
            rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", "你好 - i:" + i);
        }
        System.out.println("3个消息都发送成功");

        return ResponseEntity.ok("Succeed to send 3 msg!");
    }


}
