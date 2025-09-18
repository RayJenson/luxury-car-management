package cn.xzlei.service.impl;

import cn.xzlei.entity.PurchaseOrder;
import cn.xzlei.entity.R;
import cn.xzlei.entity.SalesRecord;
import cn.xzlei.mapper.PurchaseOrderMapper;
import cn.xzlei.mapper.SalesRecordMapper;
import cn.xzlei.service.ReportService;
import cn.xzlei.vo.ReportVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Resource
    private PurchaseOrderMapper purchaseOrderMapper;
    @Autowired
    private SalesRecordMapper salesRecordMapper;

    @Override
    public R purchaseStats(Integer page, Integer pageSize) {
        if (page == null || page < 1) {
            page = 1;
        } else if (pageSize == null || pageSize < 1) {
            pageSize = 3;
        }
        PageHelper.startPage(page, pageSize);

        List<PurchaseOrder> purchaseOrders = purchaseOrderMapper.selectAllByIsPurchase();
        Page<PurchaseOrder> pageInfo = (Page<PurchaseOrder>) purchaseOrders;
        return R.success(
                ReportVO.builder()
                        .total(pageInfo.getTotal())
                        .curPageNum((long) pageInfo.getPageNum())
                        .data(pageInfo.getResult())
                        .build()
        );
    }

    @Override
    public R salesStats(Integer page, Integer pageSize) {
        if (page == null || page < 1) {
            page = 1;
        } else if (pageSize == null || pageSize < 1) {
            pageSize = 3;
        }
        PageHelper.startPage(page, pageSize);

        List<SalesRecord> salesRecords = salesRecordMapper.selectAllByIsSales();
        Page<SalesRecord> pageInfo = (Page<SalesRecord>) salesRecords;
        return R.success(
                ReportVO.builder().
                        total(pageInfo.getTotal()).
                        curPageNum((long) pageInfo.getPageNum()).
                        data(pageInfo.getResult()).
                        build()
        );
    }
}
