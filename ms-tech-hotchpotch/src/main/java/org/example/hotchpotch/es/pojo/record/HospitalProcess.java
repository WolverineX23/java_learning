package org.example.hotchpotch.es.pojo.record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 住院经过
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalProcess {

    @Field(value = "住院天数", type = FieldType.Text, analyzer = "ik_max_word")
    private String day;

    @Field(value = "住院诊治经过", type = FieldType.Text, analyzer = "ik_smart")
    private String diagnosisProcess;

    @Field(value = "手术", type = FieldType.Text, analyzer = "ik_smart")
//    List<String> operationList = Collections.synchronizedList(new ArrayList<>());
    private List<String> operationList;

//    public void add(String data) { operationList.add(data); }
}
