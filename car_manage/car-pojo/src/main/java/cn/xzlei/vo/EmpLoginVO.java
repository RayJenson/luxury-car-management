package cn.xzlei.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EmpLoginVO {
    private String token;
    private String account;
    private String name;
    private String avatar;
    private Integer deptId;
    private List<String> roles;
}
