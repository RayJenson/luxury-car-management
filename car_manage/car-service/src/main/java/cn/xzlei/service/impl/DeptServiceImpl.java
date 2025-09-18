package cn.xzlei.service.impl;

import cn.xzlei.entity.Dept;
import cn.xzlei.entity.R;
import cn.xzlei.mapper.DeptMapper;
import cn.xzlei.service.DeptService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptMapper deptMapper;

    @Override
    public R list() {
        List<Dept> depts = deptMapper.selectAll();
        return R.success(depts);
    }
}
