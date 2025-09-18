package cn.xzlei.mapper;

import cn.xzlei.entity.SalesRecord;

import java.util.List;

public interface SalesRecordMapper {
    int deleteByPrimaryKey(Integer salesId);

    int insert(SalesRecord record);

    int insertSelective(SalesRecord record);

    SalesRecord selectByPrimaryKey(Integer salesId);

    int updateByPrimaryKeySelective(SalesRecord record);

    int updateByPrimaryKey(SalesRecord record);

    List<SalesRecord> selectAll();

    List<SalesRecord> selectAllByIsSales();
}