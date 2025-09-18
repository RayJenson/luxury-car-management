package cn.xzlei.mapper;

import cn.xzlei.entity.CarType;

import java.util.List;

public interface CarTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarType record);

    int insertSelective(CarType record);

    CarType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarType record);

    int updateByPrimaryKey(CarType record);

    List<CarType> selectAll();
}