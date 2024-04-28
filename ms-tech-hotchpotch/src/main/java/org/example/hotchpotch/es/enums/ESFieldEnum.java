package org.example.hotchpotch.es.enums;

import lombok.Getter;

/**
 * ES 字段类型枚举
 * 高级客户端包 自定义使用
 *
 */
@Getter
public enum ESFieldEnum {
    TEXT("text"),

    KEYWORD("keyword"),

    INTEGER("integer"),

    LONG("long"),

    DOUBLE("double"),

    DATE("date"),

    // 单条数据
    OBJECT("object"),

    // 嵌套数组
    NESTED("nested");

    private final String type;

    ESFieldEnum (String type) {
        this.type = type;
    }
}
