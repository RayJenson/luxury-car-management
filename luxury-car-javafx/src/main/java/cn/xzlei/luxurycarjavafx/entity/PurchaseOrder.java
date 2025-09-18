package cn.xzlei.luxurycarjavafx.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * purchase_order
 */
@Setter
@Getter
@Builder
public class PurchaseOrder  {
    private Integer id;

    private String orderId;

    private Integer productId;

    private Integer supplierId;

    private Integer orderQuantity;

    //        if (orderDate != null) {
    //            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d H:mm:ss");
    //            return sdf.format(orderDate);
    //        }
    private LocalDateTime orderDate;

    public LocalDateTime getOrderDateByDate() {
        if (orderDate != null) {
            return orderDate;
        }
        return null;
    }

    private String payMethod;

    private Boolean isPurchase;

    private String note;

    private Date updateTime;

    private Date createTime;

}