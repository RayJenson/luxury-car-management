package cn.xzlei.entity;

import cn.xzlei.annotation.FieldRequiredForUpdate;
import cn.xzlei.annotation.ValidUsername;
import cn.xzlei.valid.UpdateGroup;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * product
 */
@Data
@Builder
@FieldRequiredForUpdate(field = "id", message = "更新操作必须提供商品ID", groups = UpdateGroup.class)
public class Product {

    @Min(value = 1, message = "请提供有效的ID字段",groups = UpdateGroup.class)
    private Integer id;

    @NotBlank(message = "商品名称不能为空")
    @ValidUsername(message = "商品名称格式不正确",prefix = "商品名称",min = 2,max = 40)
    private String productName;

    @Min(value = 1,message = "非法汽车类型ID")
    private Integer carId;

    @Min(value = 1,message = "商品价格不能小于1")
    private BigDecimal price;

    @Min(value = 1,message = "商品库存不能小于1")
    private Integer originalQuantity;

    @NotBlank(message = "商品颜色不能为空")
    private String color;

    @Min(value = 1,message = "商品采购价格不能小于1")
    private BigDecimal purchasePrice;

    @Min(value = 1,message = "商品最小库存不能小于1")
    @Max(value = 10000,message = "商品最小库存不能大于10000")
    private Integer minQuantity;

    @Min(value = 1,message = "商品最大库存不能小于1")
    @Max(value = 10000,message = "商品最大库存不能大于10000")
    private Integer maxQuantity;

    private String carDesc;

    private String modelPreviewUrl;

    private Date updateTime;

    private Date createTime;

}