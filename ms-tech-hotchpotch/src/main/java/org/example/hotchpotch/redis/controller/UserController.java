package org.example.hotchpotch.redis.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.hotchpotch.redis.annotation.RateLimiter;
import org.example.hotchpotch.redis.annotation.RateLimiters;
import org.example.hotchpotch.redis.dto.ApiResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {


    @RateLimiters(value = {
            @RateLimiter(keys = "#id", capacity = 1, rate = 1, showPromptMsg = "您查询太快了，请稍后再试"),
            @RateLimiter(capacity = 5, rate = 2, showPromptMsg = "系统繁忙，请稍后再试")
    })
    @RequestMapping("/findById/{id}")
    public ApiResponseDto<String> findById(@PathVariable("id") String id) {
        return ApiResponseDto.success("test");
    }

}
