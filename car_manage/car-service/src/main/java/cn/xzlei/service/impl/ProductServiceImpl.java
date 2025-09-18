package cn.xzlei.service.impl;

import cn.xzlei.constant.MessageConstant;
import cn.xzlei.dto.ProductQueryParamDTO;
import cn.xzlei.entity.Product;
import cn.xzlei.entity.R;
import cn.xzlei.mapper.ProductMapper;
import cn.xzlei.service.ProductService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;

    @Override
    public R addProduct(Product product) {
        if(product.getMinQuantity() > product.getMaxQuantity()){
            return R.error("最小库存不能大于最大库存");
        }
        if(product.getCarId() == null || product.getCarId() <= 0){
            return R.error("请选择商品类型");
        }
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());
        productMapper.insertSelective(product);
        return R.success(MessageConstant.ADD_PRODUCT_SUCCESS);
    }

    @Override
    public R updateProduct(Product product) {
        if(product.getMinQuantity() > product.getMaxQuantity()){
            return R.error("最小库存不能大于最大库存");
        }
        product.setUpdateTime(new Date());
        productMapper.updateByPrimaryKeySelective(product);
        return R.success(MessageConstant.UPDATE_PRODUCT_SUCCESS);
    }

    @Override
    public R deleteProduct(List<Integer> ids) {
        for (Integer id : ids){
            productMapper.deleteByPrimaryKey(id);
        }
        return R.success(MessageConstant.DELETE_PRODUCT_SUCCESS);
    }

    @Override
    public R list(ProductQueryParamDTO queryParam) {
        List<Product> products = productMapper.selectByParams(queryParam);
        return R.success(products);
    }
}
