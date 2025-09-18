package cn.xzlei.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EmpDTO {
    private String name;
    private String account;
    private Integer empId;
    private Integer deptId;
    private String address;
    private String phone;
    private String gender;
    private String job;
    private Double salary;
    private List<String> roles;
}