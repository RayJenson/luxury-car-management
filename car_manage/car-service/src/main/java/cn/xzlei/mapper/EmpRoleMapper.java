package cn.xzlei.mapper;

import cn.xzlei.entity.EmpRole;
import cn.xzlei.entity.TRole;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EmpRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmpRole record);

    int insertSelective(EmpRole record);

    EmpRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmpRole record);

    int updateByPrimaryKey(EmpRole record);

    @Select("select tr.* from emp_role er left join t_role tr on er.role_id=tr.id where er.emp_id=#{empId}")
    List<TRole> selectByUserId(Integer empId);

    void batchDeleteByEmpId(List<Integer> ids);
}