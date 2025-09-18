package cn.xzlei.luxurycarjavafx.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * dept
 */
@Data
public class Dept implements Serializable {
    private Integer deptId;

    private String name;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}