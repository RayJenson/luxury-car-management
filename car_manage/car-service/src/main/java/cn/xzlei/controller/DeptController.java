package cn.xzlei.controller;

import cn.xzlei.entity.R;
import cn.xzlei.service.DeptService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dept")
public class DeptController {

    @Resource
    private DeptService deptService;

    @GetMapping("/list")
    public R getDept(){
        return deptService.list();
    }
}
