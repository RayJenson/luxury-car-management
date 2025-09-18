package cn.xzlei.service.impl;

import cn.xzlei.constant.Constant;
import cn.xzlei.constant.MessageConstant;
import cn.xzlei.dto.CustomerQueryParamDTO;
import cn.xzlei.entity.Customer;
import cn.xzlei.entity.R;
import cn.xzlei.mapper.CustomerMapper;
import cn.xzlei.service.CustomerService;
import cn.xzlei.utils.PasswordUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerMapper customerMapper;

    @Override
    public R add(Customer customer) {
        // 设置默认密码
        customer.setPassword(PasswordUtils.encode(Constant.CUSTOMER_DEFAULT_PASSWORD));
        customer.setUpdateTime(new Date());
        customer.setCreateTime(new Date());
        customerMapper.insert(customer);
        return R.success(MessageConstant.ADD_CUSTOMER_SUCCESS);
    }

    @Override
    public R update(Customer customer) {
        customer.setUpdateTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);
        return R.success(MessageConstant.UPDATE_CUSTOMER_SUCCESS);
    }

    @Override
    public R batchDelete(List<Integer> ids) {
        int i = customerMapper.batchDeleteById(ids);
        if (i == 0) {
            return R.error(MessageConstant.CUSTOMER_NOT_EXIST);
        }
        return R.success(MessageConstant.DELETE_CUSTOMER_SUCCESS);
    }

    @Override
    public R list(CustomerQueryParamDTO queryParam) {
        List<Customer> customers = customerMapper.selectByParams(queryParam);
        for (Customer customer : customers){
            customer.setPassword("******");  // 隐藏密码
        }
        return R.success(customers);
    }
}
