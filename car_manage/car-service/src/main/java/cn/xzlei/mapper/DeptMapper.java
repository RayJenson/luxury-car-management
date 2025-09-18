package cn.xzlei.mapper;

import cn.xzlei.entity.Dept;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DeptMapper {
    int deleteByPrimaryKey(Integer deptId);

    int insert(Dept record);

    int insertSelective(Dept record);

    Dept selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);

    @Select("select * from dept")
    List<Dept> selectAll();
}