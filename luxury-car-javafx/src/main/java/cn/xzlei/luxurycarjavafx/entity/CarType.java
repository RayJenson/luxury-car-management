package cn.xzlei.luxurycarjavafx.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * car_type
 */
@Data
@Builder
public class CarType {
    private Integer id;

    private String typeName;

    private String image;

    private Date updateTime;

    private Date createTime;

}