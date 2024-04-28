package org.example.hotchpotch.es.pojo.record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 个人病史
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalDiseaseHistory {

    @Field(value = "现病史", type = FieldType.Text, analyzer = "ik_smart")
    private String historyOfPresentIllness;

    @Field(value = "既往史", type = FieldType.Text, analyzer = "ik_smart")
    private String pastHistory;

    @Field(value = "个人史", type = FieldType.Text, analyzer = "ik_smart")
    private String personalHistory;

    @Field(value = "婚育史", type = FieldType.Text, analyzer = "ik_smart")
    private String maritalHistory;

    @Field(value = "家族史", type = FieldType.Text, analyzer = "ik_smart")
    private String familyHistory;

}
