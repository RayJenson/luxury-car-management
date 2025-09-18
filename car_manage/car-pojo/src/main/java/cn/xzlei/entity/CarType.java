package cn.xzlei.entity;

import cn.xzlei.annotation.ValidUsername;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

/**
 * car_type
 */
@Data
public class CarType {
    private Integer id;

    @NotBlank(message = "汽车类型名称不能为空")
    @ValidUsername(prefix = "汽车类型名称",message = "汽车类型名称格式不正确",allowSpecialChars = false, min = 2,max = 40)
    private String typeName;

    private String image;

    private Date updateTime;

    private Date createTime;
}