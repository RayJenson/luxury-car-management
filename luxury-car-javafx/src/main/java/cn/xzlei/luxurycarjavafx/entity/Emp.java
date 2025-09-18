package cn.xzlei.luxurycarjavafx.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * emp
 */
@Data
@Builder
public class Emp implements Serializable {
    private Integer empId;

    private String account;

    private String name;

    private Integer deptId;

    private String address;

    private String phone;

    private String gender;

    private String password;

    private String job;

    private Double salary;

    private Integer status;

    private String avatar;

    private Date updateTime;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}