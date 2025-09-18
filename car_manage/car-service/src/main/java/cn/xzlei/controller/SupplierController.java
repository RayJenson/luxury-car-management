package cn.xzlei.controller;

import cn.xzlei.dto.SupplierQueryParamDTO;
import cn.xzlei.entity.R;
import cn.xzlei.entity.Supplier;
import cn.xzlei.service.SupplierService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Resource
    private SupplierService supplierService;

    @PostMapping
    public R add(@RequestBody @Valid Supplier supplier){
        return supplierService.add(supplier);
    }

    @PutMapping
    public R update(@RequestBody @Valid Supplier supplier){
        return supplierService.update(supplier);
    }

    @DeleteMapping
    public R batchDelete(@RequestParam  List<Integer> ids){
        return supplierService.batchDelete(ids);
    }


    @GetMapping("/list")
    public R list(SupplierQueryParamDTO queryParam){
        return supplierService.list(queryParam);
    }
}
