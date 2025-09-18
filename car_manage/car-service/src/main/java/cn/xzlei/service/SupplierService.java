package cn.xzlei.service;

import cn.xzlei.entity.R;
import cn.xzlei.entity.Supplier;
import cn.xzlei.dto.SupplierQueryParamDTO;

import java.util.List;

public interface SupplierService{
    R add(Supplier supplier);

    R update(Supplier supplier);

    R batchDelete(List<Integer> ids);

    R list(SupplierQueryParamDTO queryParam);
}
