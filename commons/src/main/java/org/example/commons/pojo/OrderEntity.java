package org.example.commons.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder                // 需要加 @AllArgsConstructor 否则编译报错
public class OrderEntity implements Serializable {

    private double Id;

    private Date commentTime;

    private Date createTime;

    private Integer confirmStatus;

    private String receiverName;
}
