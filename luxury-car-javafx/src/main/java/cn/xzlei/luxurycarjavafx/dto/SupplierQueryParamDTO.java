package cn.xzlei.luxurycarjavafx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierQueryParamDTO {
    private String supplierName;
    private Integer supplierId;
    private String address;
}
