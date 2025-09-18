package cn.xzlei.controller;

import cn.xzlei.entity.R;
import cn.xzlei.entity.SalesRecord;
import cn.xzlei.service.SalesRecordService;
import cn.xzlei.valid.CreateGroup;
import cn.xzlei.valid.UpdateGroup;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salesRecord")
public class SalesRecordController {

    @Resource
    private SalesRecordService salesRecordService;

    @PostMapping
    public R add(@RequestBody @Validated(CreateGroup.class) SalesRecord salesRecord) {
        return salesRecordService.add(salesRecord);
    }

    @PutMapping
    public R update(@RequestBody @Validated(UpdateGroup.class) SalesRecord salesRecord) {
        return salesRecordService.update(salesRecord);
    }

    @DeleteMapping
    public R delete(Integer id) {
        return salesRecordService.delete(id);
    }

    @GetMapping("/list")
    public R list() {
        return salesRecordService.list();
    }
}
