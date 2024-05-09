package org.example.hotchpotch.redis.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.hotchpotch.redis.dto.ApiResponseDto;
import org.example.hotchpotch.redis.exception.RateLimiterException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<?> handle(HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {
        return logWarn(request.getRequestURI() + " " + exception.getMessage(), null, ApiResponseDto.errRequestMethod("请求方法错误"));
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto handle(MissingServletRequestParameterException exception) {
        return logWarn(exception.getMessage(), null, ApiResponseDto.errParam("参数错误"));
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto handle(RateLimiterException exception) {
        return ApiResponseDto.fail(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto handle(Exception exception) {
        log.info("异常类:{}", exception.getClass().getCanonicalName());
        return logError(null, exception, ApiResponseDto.exception("系统异常"));
    }

    private static ApiResponseDto logWarn(String msg, Exception e, ApiResponseDto responseDto) {
        long timestamp = responseDto.getTimestamp();
        String m = "timestamp is " + timestamp;
        if (msg != null) {
            m += ", " + msg;
        }
        if (e == null) {
            log.warn(m);
        } else {
            log.warn(m, e);
        }
        return responseDto;
    }

    private static ApiResponseDto logError(String msg, Exception e, ApiResponseDto responseDto) {
        long timestamp = responseDto.getTimestamp();
        String m = "timestamp is " + timestamp;
        if (msg != null) {
            m += ", " + msg;
        }
        log.error(m, e);
        return responseDto;
    }

}
