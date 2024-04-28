package org.example.hotchpotch.es.pojo.record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 病人基本信息
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseInfo {

    @Field(value = "病历号", type = FieldType.Keyword)
    private String recordId;

    @Field(value = "性别", type = FieldType.Keyword)
    private String gender;

    @Field(value = "职业", type = FieldType.Text, analyzer = "ik_smart")
    private String occupation;

    // 统一存储格式，保证数据一致性; 便于搜索和排序; 无需设置 analyzer
    @Field(value = "出生日期", type = FieldType.Date, format = DateFormat.date, pattern = "yyyy-MM-dd")
    private String bornDate;

    @Field(value = "籍贯", type = FieldType.Text, analyzer = "ik_smart")
    private String nativePlace;

    @Field(value = "婚姻状况", type = FieldType.Keyword)
    private String maritalStatus;

    @Field(value = "年龄", type = FieldType.Integer)
    private Integer age;

}
