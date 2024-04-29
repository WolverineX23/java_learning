package org.example.commons.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemEntity implements Serializable {

    private double id;

    private double categoryId;

    private String orderSn;

    private String spuName;

}
