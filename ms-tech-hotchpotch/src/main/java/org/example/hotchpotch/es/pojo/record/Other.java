package org.example.hotchpotch.es.pojo.record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * 其他
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Other implements Serializable {
    @Field(value = "NIHSS", type = FieldType.Object)
    private NIHSS nihss;

    @Field(value = "是否首次患病", type = FieldType.Boolean)
    private Boolean isFirstDisease;

    @Field(value = "吸烟", type = FieldType.Keyword)
    private String smoke;

    @Field(value = "饮酒", type = FieldType.Keyword)
    private String drink;

    @Field(value = "房颤", type = FieldType.Keyword)
    private String atrial;

    @Field(value = "血压", type = FieldType.Text, analyzer = "ik_smart")
    private String bloodPressure;

    @Field(value = "糖尿病", type = FieldType.Keyword)
    private String diabetes;

    @Field(value = "化学及放射性毒物接触", type = FieldType.Keyword)
    private String isTouchRadioactive;

    @Field(value = "高血压", type = FieldType.Keyword)
    private String hypertension;

    @Field(value = "高血脂", type = FieldType.Keyword)
    private String hyperlipidemia;

    @Field(value = "高血糖", type = FieldType.Keyword)
    private String hyperglycemia;

    @Field(value = "血液样本", type = FieldType.Keyword)
    private String bloodSample;

    @Field(value = "组学数据", type = FieldType.Keyword)
    private String omicsData;

}
