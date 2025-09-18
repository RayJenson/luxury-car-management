package cn.xzlei.luxurycarjavafx.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * purchase_plan
 */
@Data
public class PurchasePlan implements Serializable {
    private Integer planId;

    private Integer productId;

    private Integer quantity;

    private Integer supplierId;

    private Date orderDate;

    private String note;

    private String payMethod;

    private Boolean isReturn;

    private Date updateTime;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}