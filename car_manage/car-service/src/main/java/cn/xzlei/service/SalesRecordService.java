package cn.xzlei.service;

import cn.xzlei.entity.R;
import cn.xzlei.entity.SalesRecord;

public interface SalesRecordService {
    R add(SalesRecord salesRecord);

    R update(SalesRecord salesRecord);

    R delete(Integer id);

    R list();
}
