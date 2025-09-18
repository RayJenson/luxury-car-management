package cn.xzlei.service;

import cn.xzlei.entity.R;

public interface ReportService {
    R purchaseStats(Integer page, Integer pageSize);

    R salesStats(Integer page, Integer pageSize);
}
