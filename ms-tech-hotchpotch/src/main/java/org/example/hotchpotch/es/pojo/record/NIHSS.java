package org.example.hotchpotch.es.pojo.record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * NIHSS
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NIHSS {

    @Field(value = "入院时", type = FieldType.Integer)
    private Integer in;

    @Field(value = "出院时", type = FieldType.Integer)
    private Integer out;

}
