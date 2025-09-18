package cn.xzlei.controller;

import cn.xzlei.entity.CarType;
import cn.xzlei.entity.R;
import cn.xzlei.service.CarTypeService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartype")
public class CarTypeController {

    @Resource
    private CarTypeService carTypeService;

    @PostMapping
    public R add(@RequestBody @Valid CarType carType){
        return carTypeService.add(carType);
    }

    @PutMapping
    public R update(@RequestBody @Valid CarType carType){
        System.out.println( carType);
        return carTypeService.update(carType);
    }

    @DeleteMapping
    public R delete(@RequestParam Integer  id){
        return carTypeService.delete(id);
    }

    @GetMapping("/list")
    public R list(){
        return carTypeService.list();
    }
}
