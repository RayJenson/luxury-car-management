package cn.xzlei.mapper;

import cn.xzlei.dto.CustomerQueryParamDTO;
import cn.xzlei.entity.Customer;

import java.util.List;

public interface CustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    int batchDeleteById(List<Integer> ids);

    List<Customer> selectByParams(CustomerQueryParamDTO queryParam);
}