package cn.xzlei.controller;

import cn.xzlei.dto.ProductQueryParamDTO;
import cn.xzlei.entity.Product;
import cn.xzlei.entity.R;
import cn.xzlei.service.ProductService;
import cn.xzlei.valid.UpdateGroup;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @PostMapping
    public R addProduct(@RequestBody @Validated Product product) {
        return productService.addProduct(product);
    }

    @PutMapping
    public R updateProduct(@RequestBody @Validated(UpdateGroup.class) Product product) {
        System.out.println( product);
        return productService.updateProduct(product);
    }

    @DeleteMapping
    public R deleteProduct(@RequestParam List<Integer> ids) {
        return productService.deleteProduct(ids);
    }

    @GetMapping("/list")
    public R list(ProductQueryParamDTO queryParam) {
        System.out.println(queryParam);
        return productService.list(queryParam);
    }
}
