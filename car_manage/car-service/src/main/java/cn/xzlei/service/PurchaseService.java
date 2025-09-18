package cn.xzlei.service;

import cn.xzlei.entity.PurchaseOrder;
import cn.xzlei.entity.R;

public interface PurchaseService {
    R add(PurchaseOrder purchaseOrder);

    R update(PurchaseOrder purchaseOrder);

    R delete(Integer id);

    R list();
}
