package cn.xzlei.luxurycarjavafx.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * customer
 */
@Builder
@Data
public class Customer {
    private Integer id;

    private String customerName;

    private String phone;

    private String email;

    private String address;

    private String purchaseHistory;

    private String gender;

    private String workplace;

    private String password;

    private String avatar;

    private String nickname;

    private Date updateTime;

    private Date createTime;

}