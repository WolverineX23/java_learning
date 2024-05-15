package org.example.base.concurrent.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.base.concurrent.service.AsyncService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
public class AsyncController {

    @Resource
    private AsyncService asyncService;


    @GetMapping("/sendMails")
    @SneakyThrows
    public String sendMails() {

        String[] emails = new String[]{"a1@163.com", "a2@163.com", "a3@163.com", "a4@163.com", "a5@163.com"};
        for (String email : emails) {
            asyncService.sendMail(email, "Sample Text");
        }
        return "success";
    }

    @GetMapping("/blockSendMails")
    @SneakyThrows
    public String blockSendMails() {

        CompletableFuture<String>[] futures = new CompletableFuture[5];

        String[] emails = new String[]{"a1@163.com", "a2@163.com", "a3@163.com", "a4@163.com", "a5@163.com"};
        for (int i = 0; i < emails.length; i++) {
            futures[i] = asyncService.blockSendMail(emails[i], "Sample Text");
        }
        // 屏障：阻塞等待所有 CompletableFuture 响应结果
        CompletableFuture.allOf(futures);

        StringBuffer out = new StringBuffer();
        for (CompletableFuture<String> future : futures) {
            try {
                out.append(future.get()).append("\n");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return out.toString();
    }
}
