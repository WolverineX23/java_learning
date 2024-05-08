package org.example.commons.enums;

import lombok.Getter;

@Getter
public enum RedisKeyEnum {

    MQ_STATUS("mq_status:", "消息队列状态");


    private String key;
    private String desc;

    RedisKeyEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }
}
