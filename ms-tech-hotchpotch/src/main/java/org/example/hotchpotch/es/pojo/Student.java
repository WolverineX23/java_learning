package org.example.hotchpotch.es.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * spring data elasticsearch 定义实体类
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "student_index", shards = 1, replicas = 1)
public class Student {

    @Id
    private Integer id;     // 最好为 String, 对应 ES 的 _id 以及 template 的各种方法

    @Field(type = FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String desc;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String data;

    @Field(type = FieldType.Integer)
    private Integer age;

}
