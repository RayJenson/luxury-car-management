package cn.xzlei.mapper;

import cn.xzlei.dto.ProductQueryParamDTO;
import cn.xzlei.entity.Product;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectByCarTypeId(Integer id);

    List<Product> selectByParams(ProductQueryParamDTO queryParam);
}