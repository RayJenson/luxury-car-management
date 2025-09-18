package cn.xzlei.entity;

import cn.xzlei.valid.CreateGroup;
import cn.xzlei.valid.UpdateGroup;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * sales_record
 */
@Data
public class SalesRecord  {
    @NotNull(message = "销售编号不能为空", groups = {UpdateGroup.class})
    private Integer salesId;

    @NotNull(message = "商品编号不能为空", groups = {CreateGroup.class})
    private Integer customerId;

    @NotNull(message = "商品编号不能为空", groups = {CreateGroup.class})
    private Integer empId;

    @NotNull(message = "销售日期不能为空", groups = {CreateGroup.class})
    private Date saleDate;

    @NotNull(message = "销售金额不能为空", groups = {CreateGroup.class})
    private BigDecimal totalAmount;

    @NotNull(message = "支付方式不能为空", groups = {CreateGroup.class})
    @Pattern(regexp = "^(现金|刷卡)$", message = "支付方式只能是现金或者刷卡", groups = {CreateGroup.class, UpdateGroup.class})
    private String payMethod;

    @NotNull(message = "销售状态不能为空", groups = {CreateGroup.class})
    @Pattern(regexp = "^(未完成|已完成)$", message = "销售状态只能是未完成或者已完成", groups = {CreateGroup.class, UpdateGroup.class})
    private String status;

    @NotNull(message = "地址不能为空", groups = {CreateGroup.class})
    private String address;

    private BigDecimal curAmount;

    private String note;

    private Date updateTime;

    private Date createTime;

}