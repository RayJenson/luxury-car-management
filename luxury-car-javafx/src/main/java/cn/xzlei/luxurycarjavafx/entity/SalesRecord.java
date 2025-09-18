package cn.xzlei.luxurycarjavafx.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * sales_record
 */
@Data
@Builder
public class SalesRecord  {
    private Integer salesId;

    private Integer customerId;

    private Integer empId;

    private LocalDateTime saleDate;

    private BigDecimal totalAmount;

    private String payMethod;

    private String status;

    private String address;

    private BigDecimal curAmount;

    private String note;

    private Date updateTime;

    private Date createTime;
}