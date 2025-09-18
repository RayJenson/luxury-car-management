package cn.xzlei.mapper;

import cn.xzlei.entity.Emp;
import cn.xzlei.dto.EmpQueryParamDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EmpMapper {
    int deleteByPrimaryKey(Integer empId);

    int insert(Emp record);

    int insertSelective(Emp record);

    Emp selectByPrimaryKey(Integer empId);

    int updateByPrimaryKeySelective(Emp record);

    int updateByPrimaryKey(Emp record);

    @Select("select * from emp where account = #{account} and password = #{password} and dept_id = #{deptId}")
    Emp queryByAccountAndPasswordAndDeptId(String account, String password, Integer deptId);

    void batchDelete(List<Integer> ids);

    List<Emp> selectByParam(EmpQueryParamDTO queryParam);
}