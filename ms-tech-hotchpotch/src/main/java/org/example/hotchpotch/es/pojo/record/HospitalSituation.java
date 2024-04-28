package org.example.hotchpotch.es.pojo.record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 入院情况
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalSituation {

    @Field(value = "医院", type = FieldType.Keyword)
    private String hospital;

    @Field(value = "入院时间", type = FieldType.Date, format = DateFormat.date, pattern = "yyyy-MM-dd")
    private String intoTime;

    @Field(value = "入院科别", type = FieldType.Keyword)
    private String department;

    @Field(value = "主诉", type = FieldType.Text, analyzer = "ik_smart")
    private String cardinalSymptom;

    @Field(value = "入院诊断", type = FieldType.Text, analyzer = "ik_smart")
//    List<String> diagnosisList = Collections.synchronizedList(new ArrayList<>());     线程安全列表
    private List<String> diagnosisList;

    @Field(value = "入院情况", type = FieldType.Text, analyzer = "ik_smart")
    private String situation;

//    public void add(String data){ diagnosisList.add(data); }
}
