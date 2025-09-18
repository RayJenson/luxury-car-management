package cn.xzlei.service.impl;

import cn.xzlei.constant.MessageConstant;
import cn.xzlei.entity.PurchaseOrder;
import cn.xzlei.entity.R;
import cn.xzlei.mapper.PurchaseOrderMapper;
import cn.xzlei.service.PurchaseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Resource
    private PurchaseOrderMapper purchaseOrderMapper;

    @Override
    public R add(PurchaseOrder purchaseOrder) {
        purchaseOrder.setUpdateTime(new Date());
        purchaseOrder.setCreateTime(new Date());
        purchaseOrder.setOrderId("PO" + System.currentTimeMillis());
        purchaseOrderMapper.insert(purchaseOrder);
        return R.success(MessageConstant.ADD_PURCHASE_ORDER_SUCCESS);
    }

    @Override
    public R update(PurchaseOrder purchaseOrder) {
        purchaseOrder.setUpdateTime(new Date());
        purchaseOrder.setOrderId(null);     // 订单ID不允许更新
        purchaseOrderMapper.updateByPrimaryKeySelective(purchaseOrder);
        return R.success(MessageConstant.UPDATE_PURCHASE_ORDER_SUCCESS);
    }

    @Override
    public R delete(Integer id) {
        purchaseOrderMapper.deleteByPrimaryKey(id);
        return R.success(MessageConstant.DELETE_PURCHASE_ORDER_SUCCESS);
    }

    @Override
    public R list() {
        List<PurchaseOrder> purchaseOrders = purchaseOrderMapper.selectAll();
        return R.success(purchaseOrders);
    }
}
