package cn.xzlei.service.impl;

import cn.xzlei.entity.R;
import cn.xzlei.mapper.InventoryMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class InventoryService implements cn.xzlei.service.InventoryService {
    @Resource
    private InventoryMapper inventoryMapper;
    @Override
    public R stats() {
        return R.success(inventoryMapper.stats());
    }
}
