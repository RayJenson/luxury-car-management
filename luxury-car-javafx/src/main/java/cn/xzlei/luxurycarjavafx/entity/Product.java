package cn.xzlei.luxurycarjavafx.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * product
 */
@Data
@Builder
public class Product implements Serializable {
    private Integer id;

    private String productName;

    private Integer carId;

    private BigDecimal price;

    private Integer originalQuantity;

    private String color;

    private BigDecimal purchasePrice;

    private Integer minQuantity;

    private Integer maxQuantity;

    private String carDesc;

    private String modelPreviewUrl;

    private Date updateTime;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}