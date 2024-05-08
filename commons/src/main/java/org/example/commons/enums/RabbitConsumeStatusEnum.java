package org.example.commons.enums;

import java.util.Arrays;
import java.util.List;

/**
 * RabbitStatusEnum : Message 消费状态枚举类
 *
 */

public enum RabbitConsumeStatusEnum {

    CONSUME(0, "待消费"),
    BEGIN(1, "开始消费"),
    SUCCESS(2, "成功"),
    FAIL(3, "失败"),
    ;

    private Integer code;
    private String message;

    RabbitConsumeStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 获取需要执行的状态集合
     * @return
     */
    public static List<Integer> getNeedExecuteList(){
        return Arrays.asList(CONSUME.getCode(),FAIL.getCode());
    }

    /**
     * 获取不需要执行的状态集合
     * @return
     */
    public static List<Integer> getCompletionExecuteList(){
        return Arrays.asList(CONSUME.getCode(),FAIL.getCode());
    }

}
