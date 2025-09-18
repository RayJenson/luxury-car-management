package cn.xzlei.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReportVO {
    private Long total;
    private Long curPageNum;
    private Object data;
}
