package cn.xzlei.service;

import cn.xzlei.dto.EmpDTO;
import cn.xzlei.entity.Emp;
import cn.xzlei.entity.R;
import cn.xzlei.dto.EmpQueryParamDTO;

import java.util.List;

public interface EmpService{
    R login(Emp emp);

    R add(EmpDTO emp);

    R update(EmpDTO emp);

    R batchDelete(List<Integer> ids);

    R list(EmpQueryParamDTO queryParam);

    R resetPwd(Integer empId);

    R userInfo();
}
