package cn.xzlei.entity;

import cn.xzlei.annotation.ValidUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * supplier
 */
@Data
public class Supplier {
    private Integer supplierId;

    @NotBlank(message = "供应商名称不能为空")
    @Length(min = 4, max = 20, message = "供应商名称长度在4-20个字符之间")
    @ValidUsername(prefix = "供应商名称",message = "供应商名称格式不正确",allowSpecialChars = false)
    private String supplierName;

    @NotBlank(message = "联系人名称不能为空")
    @ValidUsername(prefix = "联系人",message = "联系人",allowSpecialChars = false,min = 2,max = 20)
    private String contactName;

    @NotBlank(message = "联系人手机号不能为空")
    @Length(min = 11, max = 11, message = "联系人手机号长度为11个字符")
    private String contactPhone;

    @NotBlank(message = "联系人职位不能为空")
    @Length(min = 2, max = 20, message = "联系人职位长度在2-20个字符之间")
    private String address;

    @NotBlank(message = "银行卡账号不能为空")
    @Length(min = 10, max = 30, message = "银行卡账号长度在2-20个字符之间")
    private String account;

    @NotBlank(message = "邮箱不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", message = "邮箱格式不正确")
    private String email;

    private Date updateTime;

    private Date createTime;

}