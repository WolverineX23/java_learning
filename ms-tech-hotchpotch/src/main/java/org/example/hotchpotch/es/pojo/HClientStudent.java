package org.example.hotchpotch.es.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.example.hotchpotch.es.annotations.ESField;
import org.example.hotchpotch.es.enums.AnalyzerEnum;
import org.example.hotchpotch.es.enums.ESFieldEnum;

import java.io.Serializable;

/**
 * 使用 ES 高级客户端，定义实体类
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)    // 为类的 setter 方法提供链式调用支持，如： student.setName("Alice").setAge(30);
public class HClientStudent implements Serializable {   // 若非实现自定义序列化方式, 不需要显示序列化

    private static final long serialVersionUID = 7100225268368014590L;

    @ESField(type = ESFieldEnum.KEYWORD)
    private String id;

    @ESField(type = ESFieldEnum.KEYWORD)
    private String username;

    @ESField(type = ESFieldEnum.TEXT, analyzer = AnalyzerEnum.IK_MAX_WORD)
    private String address;

    @ESField(type = ESFieldEnum.TEXT, analyzer = AnalyzerEnum.IK_MAX_WORD)
    private String phone;

}
