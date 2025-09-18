package cn.xzlei.mapper;

import cn.xzlei.entity.PurchasePlan;

public interface PurchasePlanMapper {
    int deleteByPrimaryKey(Integer planId);

    int insert(PurchasePlan record);

    int insertSelective(PurchasePlan record);

    PurchasePlan selectByPrimaryKey(Integer planId);

    int updateByPrimaryKeySelective(PurchasePlan record);

    int updateByPrimaryKey(PurchasePlan record);
}