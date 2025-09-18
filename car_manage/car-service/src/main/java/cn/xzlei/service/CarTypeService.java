package cn.xzlei.service;

import cn.xzlei.entity.CarType;
import cn.xzlei.entity.R;

public interface CarTypeService {
    R add(CarType carType);

    R update(CarType carType);

    R delete(Integer id);

    R list();
}
