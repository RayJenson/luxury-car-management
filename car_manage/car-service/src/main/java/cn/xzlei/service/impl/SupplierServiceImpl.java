package cn.xzlei.service.impl;

import cn.xzlei.constant.MessageConstant;
import cn.xzlei.dto.SupplierQueryParamDTO;
import cn.xzlei.entity.R;
import cn.xzlei.entity.Supplier;
import cn.xzlei.mapper.SupplierMapper;
import cn.xzlei.service.SupplierService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Resource
    private SupplierMapper supplierMapper;

    @Override
    public R add(Supplier supplier) {
        supplier.setUpdateTime(new Date());
        supplier.setCreateTime(new Date());

        // 校验参数是否完整
        if(supplier.getSupplierName() == null || supplier.getSupplierName().isEmpty()
        || supplier.getContactName() == null || supplier.getContactName().isEmpty()
        || supplier.getContactPhone() == null || supplier.getContactPhone().isEmpty()
        || supplier.getAddress() == null || supplier.getAddress().isEmpty()
        || supplier.getAccount() == null || supplier.getAccount().isEmpty()
        || supplier.getEmail() == null || supplier.getEmail().isEmpty()){
            return R.error(MessageConstant.SQL_ERROR);
        }

        supplierMapper.insert(supplier);
        return R.success(MessageConstant.ADD_SUPPLIER_SUCCESS);
    }

    @Override
    public R update(Supplier supplier) {
        supplier.setUpdateTime(new Date());
        supplierMapper.updateByPrimaryKeySelective(supplier);
        return R.success(MessageConstant.UPDATE_SUPPLIER_SUCCESS);
    }

    @Override
    public R batchDelete(List<Integer> ids) {
        supplierMapper.batchDeleteById(ids);
        return R.success(MessageConstant.DELETE_SUPPLIER_SUCCESS);
    }

    @Override
    public R list(SupplierQueryParamDTO queryParam) {
        List<Supplier> suppliers = supplierMapper.selectByParams(queryParam);
        return R.success(suppliers);
    }


}
