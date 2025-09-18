package cn.xzlei.service;

import cn.xzlei.dto.CustomerQueryParamDTO;
import cn.xzlei.entity.Customer;
import cn.xzlei.entity.R;

import java.util.List;

public interface CustomerService {
    R add(Customer customer);

    R update(Customer customer);

    R batchDelete(List<Integer> ids);

    R list(CustomerQueryParamDTO queryParam);
}
