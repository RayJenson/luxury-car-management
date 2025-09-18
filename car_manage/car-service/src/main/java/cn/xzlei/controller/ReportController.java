package cn.xzlei.controller;

import cn.xzlei.entity.R;
import cn.xzlei.service.ReportService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Resource
    private ReportService reportService;

    @GetMapping("/purchase")
    public R purchaseStats(Integer page, Integer pageSize) {
        return reportService.purchaseStats(page, pageSize);
    }

    @GetMapping("/sales")
    public R salesStats(Integer page, Integer pageSize) {
        return reportService.salesStats(page, pageSize);
    }
}
