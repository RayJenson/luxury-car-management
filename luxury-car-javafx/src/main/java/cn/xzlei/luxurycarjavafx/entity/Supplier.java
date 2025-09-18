package cn.xzlei.luxurycarjavafx.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * supplier
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Supplier implements Serializable {
    private Integer supplierId;

    private String supplierName;

    private String contactName;

    private String contactPhone;

    private String address;

    private String account;

    private String email;

    private Date updateTime;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}