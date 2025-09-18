package cn.xzlei.entity;

import cn.xzlei.valid.CreateGroup;
import cn.xzlei.valid.UpdateGroup;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * emp
 */
@Data
public class Emp implements Serializable {
    @NotNull(groups = {UpdateGroup.class}, message = "id不能为空")
    private Integer empId;

    @NotNull(message = "账号不能为空", groups = {CreateGroup.class})
    private String account;

    @NotNull(message = "名称不能为空", groups = {CreateGroup.class})
    private String name;

    @NotNull(message = "部门id不能为空", groups = {CreateGroup.class})
    private Integer deptId;

    @NotNull(message = "地址不能为空", groups = {CreateGroup.class})
    private String address;

    @NotNull(message = "手机号不能为空", groups = {CreateGroup.class})
    private String phone;

    @NotNull(message = "性别不能为空", groups = {CreateGroup.class})
    @Pattern(regexp = "^([男女])$", message = "性别只能是0或1", groups = {CreateGroup.class})
    private String gender;

    private String password;

    @NotNull(message = "职位不能为空", groups = {CreateGroup.class})
    private String job;

    @NotNull(message = "薪资不能为空", groups = {CreateGroup.class})
    private Double salary;

    @NotNull(message = "状态不能为空", groups = {CreateGroup.class})
    private Integer status;

    private String avatar;

    private Date updateTime;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}