package cn.xzlei.mapper;

import cn.xzlei.entity.Supplier;
import cn.xzlei.dto.SupplierQueryParamDTO;

import java.util.List;

public interface SupplierMapper {
    int deleteByPrimaryKey(Integer supplierId);

    int insert(Supplier record);

    int insertSelective(Supplier record);

    Supplier selectByPrimaryKey(Integer supplierId);

    int updateByPrimaryKeySelective(Supplier record);

    int updateByPrimaryKey(Supplier record);

    void batchDeleteById(List<Integer> ids);

    List<Supplier> selectByParams(SupplierQueryParamDTO queryParam);
}