package org.example.hotchpotch.es.pojo.record;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * ES 病历索引
 *
 */
@Data
@Document(indexName = "record", shards = 2, replicas = 2)
public class Record {
    @Id
    private String PK;

    @Field(type = FieldType.Keyword)
    private String hospital;

    @Field(value = "基本信息", type = FieldType.Object)
    private BaseInfo baseInfo;

    @Field(value = "个人病史", type = FieldType.Object)
    private PersonalDiseaseHistory personalDiseaseHistory;

    @Field(value = "入院情况", type = FieldType.Object)
    private HospitalSituation hospitalSituation;

    @Field(value = "住院经过", type = FieldType.Object)
    private HospitalProcess hospitalProcess;

    @Field(value = "出院情况", type = FieldType.Object)
    private Discharge discharge;

    @Field(value = "其他", type = FieldType.Object)
    private Other other;

}
