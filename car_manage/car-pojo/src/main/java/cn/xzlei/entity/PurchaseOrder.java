package cn.xzlei.entity;

import cn.xzlei.valid.CreateGroup;
import cn.xzlei.valid.UpdateGroup;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

/**
 * purchase_order
 */
@Data
public class PurchaseOrder {

    @NotNull(message = "ID不能为空",groups = UpdateGroup.class)
    private Integer id;

    private String orderId;

    @NotNull(message = "商品ID不能为空", groups = {UpdateGroup.class, CreateGroup.class})
    @Min(value = 1, message = "请提供有效的商品ID", groups = {UpdateGroup.class, CreateGroup.class})
    private Integer productId;

    @NotNull(message = "供应商ID不能为空", groups = {UpdateGroup.class, CreateGroup.class})
    @Min(value = 1, message = "请提供有效的供应商ID", groups = {UpdateGroup.class, CreateGroup.class})
    private Integer supplierId;

    @NotNull(message = "订单数量不能为空", groups = {UpdateGroup.class, CreateGroup.class})
    @Min(value = 1, message = "请提供有效的订单数量", groups = {UpdateGroup.class, CreateGroup.class})
    private Integer orderQuantity;


    private Date orderDate;

    @NotBlank(message = "付款方式不能为空", groups = {UpdateGroup.class, CreateGroup.class})
    @Pattern(regexp = "^(现金|刷卡)$", message = "付款方式格式不正确", groups = {UpdateGroup.class, CreateGroup.class})
    private String payMethod;

    @NotNull(message = "是否采购不能为空", groups = {UpdateGroup.class, CreateGroup.class})
    @Min(value = 0, message = "请提供有效的是否采购字段", groups = {UpdateGroup.class, CreateGroup.class})
    @Max(value = 1, message = "请提供有效的是否采购字段", groups = {UpdateGroup.class, CreateGroup.class})
    private Integer isPurchase;

    private String note;

    private Date updateTime;

    private Date createTime;
}