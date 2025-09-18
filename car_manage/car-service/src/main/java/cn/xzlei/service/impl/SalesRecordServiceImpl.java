package cn.xzlei.service.impl;

import cn.xzlei.constant.MessageConstant;
import cn.xzlei.entity.R;
import cn.xzlei.entity.SalesRecord;
import cn.xzlei.mapper.SalesRecordMapper;
import cn.xzlei.service.SalesRecordService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SalesRecordServiceImpl implements SalesRecordService {
    @Resource
    private SalesRecordMapper salesRecordMapper;

    @Override
    public R add(SalesRecord salesRecord) {
        salesRecord.setCreateTime(new Date());
        salesRecord.setUpdateTime(new Date());
        salesRecordMapper.insertSelective(salesRecord);
        return R.success(MessageConstant.ADD_SALES_RECORD_SUCCESS);
    }

    @Override
    public R update(SalesRecord salesRecord) {
        salesRecord.setUpdateTime(new Date());
        salesRecordMapper.updateByPrimaryKeySelective(salesRecord);
        return R.success(MessageConstant.UPDATE_SALES_RECORD_SUCCESS);
    }

    @Override
    public R delete(Integer id) {
        salesRecordMapper.deleteByPrimaryKey(id);
        return R.success(MessageConstant.DELETE_SALES_RECORD_SUCCESS);
    }

    @Override
    public R list() {
        List<SalesRecord> salesRecords = salesRecordMapper.selectAll();
        return R.success(salesRecords);
    }
}
