package cn.xzlei.entity;

import cn.xzlei.annotation.ValidUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * customer
 */
@Data
public class Customer {
    private Integer id;

    @NotBlank(message = "客户名称不能为空")
    @ValidUsername(prefix = "客户名",message = "客户名格式不正确",allowSpecialChars = false,min = 2,max = 20)
    private String customerName;

    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号码格式不正确")
    private String phone;

    @NotBlank(message = "邮箱不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", message = "邮箱格式不正确")
    private String email;

    @NotBlank(message = "地址不能为空")
    @Length(max = 100, message = "地址长度不能超过100个字符")
    private String address;

    private String purchaseHistory;

    @NotBlank(message = "性别不能为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]+$", message = "性别格式不正确")
    private String gender;

    @NotBlank(message = "工作单位不能为空")
    @Length(min = 2, max = 20, message = "工作单位长度在2-20个字符之间")
    private String workplace;

    private String password;

    private String avatar;

    @NotBlank(message = "昵称不能为空")
    @ValidUsername(prefix = "昵称",message = "昵称格式不正确",allowSpecialChars = false,min = 2,max = 20)
    private String nickname;

    private Date updateTime;

    private Date createTime;

}