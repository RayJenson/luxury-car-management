package cn.xzlei.controller;

import cn.xzlei.entity.R;
import cn.xzlei.service.InventoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Resource
    private InventoryService inventoryService;

    /**
     * 库存统计
     * @return
     */
    @RequestMapping("/stats")
    public R stats() {
        return inventoryService.stats() ;
    }
}
