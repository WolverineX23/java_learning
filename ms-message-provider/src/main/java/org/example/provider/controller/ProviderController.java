package org.example.provider.controller;

import org.example.commons.pojo.Student;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/provider")
public class ProviderController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendDirectMessage")
    public ResponseEntity<String> sendDirectMessage(@RequestBody Student student) {

        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", student);
        return ResponseEntity.ok("Succeed to send the msg");
    }
}
