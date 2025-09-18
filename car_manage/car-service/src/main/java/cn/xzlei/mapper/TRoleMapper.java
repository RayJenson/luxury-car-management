package cn.xzlei.mapper;

import cn.xzlei.entity.TRole;
import org.apache.ibatis.annotations.Select;

public interface TRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TRole record);

    int insertSelective(TRole record);

    TRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TRole record);

    int updateByPrimaryKey(TRole record);

    @Select("select * from t_role where role=#{roleName}")
    TRole selectByRole(String s);
}