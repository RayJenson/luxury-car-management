package cn.xzlei.controller;

import cn.xzlei.entity.PurchaseOrder;
import cn.xzlei.entity.R;
import cn.xzlei.service.PurchaseService;
import cn.xzlei.valid.CreateGroup;
import cn.xzlei.valid.UpdateGroup;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase/order")
public class PurchaseController {
    @Resource
    private PurchaseService purchaseService;

    @PostMapping
    public R add(@RequestBody @Validated(CreateGroup.class) PurchaseOrder purchaseOrder) {
        System.out.println(purchaseOrder);
        return purchaseService.add(purchaseOrder);
    }

    @PutMapping
    public R update(@RequestBody @Validated(UpdateGroup.class) PurchaseOrder purchaseOrder) {
        return purchaseService.update(purchaseOrder);
    }

    @DeleteMapping
    public R delete(Integer id) {
        return purchaseService.delete(id);
    }

    @GetMapping("/list")
    public R list() {
        return purchaseService.list();
    }
}
