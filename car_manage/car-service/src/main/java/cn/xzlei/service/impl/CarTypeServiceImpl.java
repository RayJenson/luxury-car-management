package cn.xzlei.service.impl;

import cn.xzlei.constant.MessageConstant;
import cn.xzlei.entity.CarType;
import cn.xzlei.entity.Product;
import cn.xzlei.entity.R;
import cn.xzlei.mapper.CarTypeMapper;
import cn.xzlei.mapper.ProductMapper;
import cn.xzlei.service.CarTypeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CarTypeServiceImpl implements CarTypeService {

    @Resource
    private CarTypeMapper carTypeMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public R add(CarType carType) {
        carType.setCreateTime(new Date());
        carType.setUpdateTime(new Date());
        carType.setImage("default_cartype_img.png");
        carTypeMapper.insert(carType);
        return R.success(MessageConstant.ADD_CAR_TYPE_SUCCESS);
    }

    @Override
    public R update(CarType carType) {
        carType.setUpdateTime(new Date());
        carTypeMapper.updateByPrimaryKeySelective(carType);
        return R.success(MessageConstant.UPDATE_CAR_TYPE_SUCCESS);
    }

    @Override
    public R delete(Integer id) {
        // 删除之前先判断一下该类型下有没有汽车
        List<Product> productList = productMapper.selectByCarTypeId(id);
        if(!productList.isEmpty()){
            return R.error(MessageConstant.NOT_DELETE_CAR_TYPE);
        }
        carTypeMapper.deleteByPrimaryKey(id);
        return R.success(MessageConstant.DELETE_CAR_TYPE_SUCCESS);
    }

    @Override
    public R list() {
        List<CarType> carTypeList = carTypeMapper.selectAll();
        return R.success(carTypeList);
    }
}
