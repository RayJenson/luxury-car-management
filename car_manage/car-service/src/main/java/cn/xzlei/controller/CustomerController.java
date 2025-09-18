package cn.xzlei.controller;

import cn.xzlei.dto.CustomerQueryParamDTO;
import cn.xzlei.entity.Customer;
import cn.xzlei.entity.R;
import cn.xzlei.service.CustomerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @PostMapping
    public R add(@RequestBody @Valid Customer customer){
        return customerService.add(customer);
    }

    @PutMapping
    public R update(@RequestBody @Valid Customer customer){
        return customerService.update(customer);
    }

    @DeleteMapping
    public R batchDelete(@RequestParam List<Integer> ids){
        return customerService.batchDelete(ids);
    }

    @GetMapping("/list")
    public R list(CustomerQueryParamDTO queryParam){
        return customerService.list(queryParam);
    }
}
