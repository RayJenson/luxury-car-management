package cn.xzlei.luxurycarjavafx.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * emp_role
 */
@Data
public class EmpRole implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 员工ID
     */
    private Integer empId;

    /**
     * 角色ID
     */
    private Integer roleId;

    private static final long serialVersionUID = 1L;
}