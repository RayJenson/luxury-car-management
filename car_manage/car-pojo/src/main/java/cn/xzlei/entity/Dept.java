package cn.xzlei.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dept
 */
@Data
public class Dept implements Serializable {
    private Integer deptId;

    private String name;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}