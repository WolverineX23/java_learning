package org.example.hotchpotch.es.annotations;


import org.example.hotchpotch.es.enums.AnalyzerEnum;
import org.example.hotchpotch.es.enums.ESFieldEnum;

import java.lang.annotation.*;

/**
 * 自定义 es字段 注解
 * 供 高级客户端 使用， spring-data-elasticsearch 已有 @Field 实现
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface ESField {

    ESFieldEnum type() default ESFieldEnum.TEXT;

    AnalyzerEnum analyzer() default AnalyzerEnum.STANDARD;

}
