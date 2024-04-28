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
 * 出院情况
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discharge {

    @Field(value = "出院时间", type = FieldType.Date, format = DateFormat.date, pattern = "yyyy-MM-dd")
    private String time;

    @Field(value = "出院科别", type = FieldType.Keyword)
    private String department;

    @Field(value = "出院情况", type = FieldType.Text, analyzer = "ik_smart")
    private String situation;

    @Field(value = "出院诊断", type = FieldType.Text, analyzer = "ik_smart")
//    List<String> diagnosisList = Collections.synchronizedList(new ArrayList<>());
    private List<String> diagnosisList;

    @Field(value = "出院带药", type = FieldType.Text, analyzer = "ik_smart")
//    List<String> medicationList = Collections.synchronizedList(new ArrayList<>());
    private List<String> medicationList;

    @Field(value = "离院方式", type = FieldType.Text, analyzer = "ik_smart")
    private String leaveMethod;

//    public void addDiagnosis(String data){ diagnosisList.add(data); }

//    public void addMedicationList(String data){ medicationList.add(data); }
}
