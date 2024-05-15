package org.example.base.concurrent.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class AsyncService {


    /**
     * 要求：返回空值，不能返回其他数据类对象，如 String、List等等，除了 CompletableFuture<>
     *
     * @param email
     * @param message
     */
    @Async("mailThreadPool")
    @SneakyThrows               // 简化 显式检查异常
    public void sendMail(String email, String message) {

        Thread.sleep(new Random().nextInt(3000));
        log.info("The email to {} has been successfully sent. The message is {}.", email, message);
    }


    /**
     * 返回数据：CompletableFuture<>
     *
     * @param email
     * @param message
     * @return
     */
    @Async("mailThreadPool")
    @SneakyThrows
    public CompletableFuture<String> blockSendMail(String email, String message) {

        Thread.sleep(new Random().nextInt(3000));
        log.info("The email to {} has been successfully sent. The message is {}.", email, message);
        return CompletableFuture.completedFuture("The email to " + email + " has been successfully sent. The message is " + message);
    }
}
