package cn.xzlei.service;

import cn.xzlei.dto.ProductQueryParamDTO;
import cn.xzlei.entity.Product;
import cn.xzlei.entity.R;
import jakarta.validation.Valid;

import java.util.List;

public interface ProductService {

    R addProduct(Product product);

    R updateProduct(@Valid Product product);

    R deleteProduct(List<Integer> ids);

    R list(ProductQueryParamDTO queryParam);
}
