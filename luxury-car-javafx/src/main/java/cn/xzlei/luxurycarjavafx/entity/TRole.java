package cn.xzlei.luxurycarjavafx.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * t_role
 */
@Data
public class TRole implements Serializable {
    /**
     * 角色ID
     */
    private Integer id;

    /**
     * 角色code
     */
    private String role;

    /**
     * 角色名称
     */
    private String roleName;

    private static final long serialVersionUID = 1L;
}