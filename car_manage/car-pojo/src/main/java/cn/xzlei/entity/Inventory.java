package cn.xzlei.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * inventory
 */
@Data
public class Inventory implements Serializable {
    private Integer inventoryId;

    private Integer productId;

    private Integer quantity;

    private String warehouseName;

    private Integer warehouseId;

    private Date updateTime;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}