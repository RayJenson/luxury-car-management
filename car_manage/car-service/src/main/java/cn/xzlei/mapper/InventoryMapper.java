package cn.xzlei.mapper;

import cn.xzlei.entity.Inventory;
import cn.xzlei.vo.InventoryStatsVO;

import java.util.List;

public interface InventoryMapper {
    int deleteByPrimaryKey(Integer inventoryId);

    int insert(Inventory record);

    int insertSelective(Inventory record);

    Inventory selectByPrimaryKey(Integer inventoryId);

    int updateByPrimaryKeySelective(Inventory record);

    int updateByPrimaryKey(Inventory record);

    List<InventoryStatsVO> stats();
}