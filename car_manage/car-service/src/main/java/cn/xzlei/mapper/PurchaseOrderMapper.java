package cn.xzlei.mapper;

import cn.xzlei.entity.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PurchaseOrder record);

    int insertSelective(PurchaseOrder record);

    PurchaseOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(PurchaseOrder record);

    int updateByPrimaryKey(PurchaseOrder record);

    List<PurchaseOrder> selectAll();

    List<PurchaseOrder> selectAllByIsPurchase();
}