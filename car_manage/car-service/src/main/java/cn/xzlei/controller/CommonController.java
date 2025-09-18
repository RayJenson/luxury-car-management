package cn.xzlei.controller;

import cn.xzlei.entity.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CommonController {
    @RequestMapping
    public String index(){
        return "豪车管理系统运行中<br/>当前时间戳："+System.currentTimeMillis();
    }

    @PostMapping("/check/login/status")
    public R checkLoginStatus(){
        return R.success(200,"用户已登录",1);
    }
}
