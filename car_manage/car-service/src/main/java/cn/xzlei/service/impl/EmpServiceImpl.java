package cn.xzlei.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.xzlei.constant.Constant;
import cn.xzlei.constant.MessageConstant;
import cn.xzlei.dto.EmpDTO;
import cn.xzlei.dto.EmpQueryParamDTO;
import cn.xzlei.entity.Emp;
import cn.xzlei.entity.EmpRole;
import cn.xzlei.entity.R;
import cn.xzlei.entity.TRole;
import cn.xzlei.mapper.EmpMapper;
import cn.xzlei.mapper.EmpRoleMapper;
import cn.xzlei.mapper.TRoleMapper;
import cn.xzlei.service.EmpService;
import cn.xzlei.utils.LocalCacheUtil;
import cn.xzlei.utils.PasswordUtils;
import cn.xzlei.vo.EmpLoginVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

    @Resource
    EmpMapper empMapper;
    @Autowired
    private EmpRoleMapper empRoleMapper;
    @Resource
    private LocalCacheUtil localCacheUtil;
    @Resource
    private TRoleMapper tRoleMapper;

    /**
     * 登录
     *
     * @param emp
     * @return
     */
    @Override
    public R login(Emp emp) {
        // 检查是否存在该用户
        emp.setPassword(PasswordUtils.encode(emp.getPassword()));
        Emp tEmp = empMapper.queryByAccountAndPasswordAndDeptId(emp.getAccount(), emp.getPassword(), emp.getDeptId());
        if (tEmp == null) {
            log.info(MessageConstant.EMP_NOT_EXIST);
            return R.error(MessageConstant.EMP_OR_PASSWORD_ERROR);
        }
        log.info(MessageConstant.LOGIN_SUCCESS);


        // 获取用户角色权限
        List<TRole> tRole = empRoleMapper.selectByUserId(tEmp.getEmpId());
        if (tRole.isEmpty()) {
            return R.error(MessageConstant.EMP_NOT_RULE);
        }

        List<String> roles = new ArrayList<>();
        for (TRole role : tRole) {
            roles.add(role.getRole());
        }

        StpUtil.login(tEmp.getEmpId());     // saToken执行登录

        // 封装数据给前端
        EmpLoginVO empLoginVO = EmpLoginVO.builder()
                .token(StpUtil.getTokenValue())
                .account(tEmp.getAccount())
                .name(tEmp.getName())
                .avatar(tEmp.getAvatar())
                .deptId(tEmp.getDeptId())
                .roles(roles)
                .build();

        return R.success(empLoginVO, MessageConstant.LOGIN_SUCCESS);
    }

    @Override
    @Transactional
    public R add(EmpDTO emp) {
        Emp tEmp = new Emp();
        BeanUtils.copyProperties(emp, tEmp);
        tEmp.setPassword(PasswordUtils.encode(Constant.EMP_DEFAULT_PASSWORD));  // 设置默认密码
        tEmp.setStatus(1);  // 默认正常
        tEmp.setCreateTime(new Date());
        tEmp.setUpdateTime(new Date());


        // 根据部门ID，设置默认权限
        if (emp.getDeptId() == 1) {
            emp.setRoles(List.of("admin"));
        } else if (emp.getDeptId() == 2) {
            emp.setRoles(List.of("sale"));
        } else if (emp.getDeptId() == 3) {
            emp.setRoles(List.of("logistics"));
        }

        // 添加员工角色,如果角色为空，抛出异常，如果角色为管理员或超级管理员，先判断是否为超管，只有超管才能添加超级管理员和管理员，
        if (emp.getRoles() == null || emp.getRoles().isEmpty()) {
            return R.error(MessageConstant.NOT_SET_EMP_ROLE);
        }
        if (!StpUtil.hasRole("super-admin") && (emp.getRoles().contains("admin") || emp.getRoles().contains("super-admin"))) {
            return R.error(MessageConstant.NOT_PERMISSION_ADD_ADMIN);
        }
        empMapper.insert(tEmp);
        for (int i = 0; i < emp.getRoles().size(); i++) {
            // 获取角色ID
            TRole tRole = tRoleMapper.selectByRole(emp.getRoles().get(i));
            // 给用户添加角色
            EmpRole empRole = EmpRole.builder().empId(tEmp.getEmpId()).roleId(tRole.getId()).build();
            log.debug("给员工{}添加角色{}", tEmp.getEmpId(), tRole.getRoleName());
            empRoleMapper.insert(empRole);
        }
        return R.success(MessageConstant.ADD_EMP_SUCCESS);
    }

    @Override
    @Transactional
    public R update(EmpDTO emp) {
        // 获取员工原始信息
        Emp oEmp = empMapper.selectByPrimaryKey(emp.getEmpId());

        // 获取当前登录用户信息
        Emp loginEmp = empMapper.selectByPrimaryKey(StpUtil.getLoginIdAsInt());

        if (!StpUtil.hasRole("super-admin") && oEmp.getDeptId() == 1 && !loginEmp.getEmpId().equals(emp.getEmpId())) {
            return R.error(MessageConstant.NOT_PERMISSION_EDIT_ADMIN);
        }

        if (loginEmp.getEmpId().equals(emp.getEmpId())) {
            emp.setDeptId(loginEmp.getDeptId());    // 强制设置部门ID
        }
        if (!StpUtil.hasRole("super-admin") && emp.getDeptId() == 1 && !loginEmp.getEmpId().equals(emp.getEmpId())) {
            return R.error(MessageConstant.NOT_PERMISSION_ADD_ADMIN);
        }

        Emp tEmp = new Emp();
        BeanUtils.copyProperties(emp, tEmp);
        tEmp.setUpdateTime(new Date());
        try {
            empMapper.updateByPrimaryKeySelective(tEmp);

            // 删除员工角色关系
            empRoleMapper.batchDeleteByEmpId(List.of(tEmp.getEmpId()));

            // 根据部门ID，设置默认权限
            if (emp.getDeptId() == 1) {
                emp.setRoles(List.of("admin"));
            } else if (emp.getDeptId() == 2) {
                emp.setRoles(List.of("sale"));
            } else if (emp.getDeptId() == 3) {
                emp.setRoles(List.of("logistics"));
            }
            for (int i = 0; i < emp.getRoles().size(); i++) {
                // 获取角色ID
                TRole tRole = tRoleMapper.selectByRole(emp.getRoles().get(i));
                // 给用户添加角色
                EmpRole empRole = EmpRole.builder().empId(tEmp.getEmpId()).roleId(tRole.getId()).build();
                empRoleMapper.insert(empRole);
            }
        } catch (Exception e) {
            log.error("更新员工信息失败，{}", e.getMessage());
            return R.error(MessageConstant.UPDATE_EMP_ERROR);
        }
        return R.success(MessageConstant.UPDATE_EMP_SUCCESS);
    }

    @Override
    @Transactional
    public R batchDelete(List<Integer> ids) {
        // 删除前先根据员工id获取角色，如果角色中包含管理员，则需要超管权限
        // 如果角色中包含超管权限，则不允许删除
        for (Integer id : ids) {
            // 从缓存中获取角色
            List<String> list = localCacheUtil.getUserRoles(id.toString());
            if (list.isEmpty()) {
                // 缓存中不存在，从数据库中获取
                List<TRole> tRoles = empRoleMapper.selectByUserId(id);
                list = tRoles.stream().map(TRole::getRole).toList();
            }
            if (list.contains("admin") && !StpUtil.hasRole("super-admin")) {
                return R.error(403, MessageConstant.NOT_PERMISSION_DELETE_ADMIN);
            } else if (list.contains("super-admin")) {
                return R.error(403, MessageConstant.SUPER_ADMIN_NOT_ALLOWED_DELETE);
            }
        }
        // 先删除员工角色关系，数据库未设置级联删除，所以需要手动删除
        empRoleMapper.batchDeleteByEmpId(ids); // 删除员工角色关系
        empMapper.batchDelete(ids);
        localCacheUtil.clearAllCache();

        return R.success(MessageConstant.DELETE_EMP_SUCCESS);
    }

    @Override
    public R list(EmpQueryParamDTO queryParam) {
        List<Emp> emps = empMapper.selectByParam(queryParam);
        for (Emp emp : emps) {
            emp.setPassword("******");  // 隐藏密码
        }
        // 如果当前角色不为super-admin，则隐藏超管账号
        if (!StpUtil.hasRole("super-admin")) {
            //先筛选出deptId为1的管理员账号
            List<Emp> admins = emps.stream().filter(emp -> emp.getDeptId() == 1).toList();
            // 循环每一个管理员账号，查询出该管理员账号的角色
            for (Emp admin : admins) {
                // 先去缓存中获取该管理员账号的角色
                List<String> roles = localCacheUtil.getUserRoles(String.valueOf(admin.getEmpId()));
                if (roles.isEmpty()) {
                    List<TRole> tRoleList = empRoleMapper.selectByUserId(admin.getEmpId());
                    List<String> roleList = tRoleList.stream().map(TRole::getRole).toList();
                    // 创建新的可变ArrayList
                    roles = new ArrayList<>(roleList);
                    // 保存缓存数据
                    localCacheUtil.setUserRoles(String.valueOf(admin.getEmpId()), roles);
                    log.debug("从数据库中获取员工{}的角色列表", admin.getEmpId());
                    roles.addAll(tRoleList.stream().map(TRole::getRole).toList());
                }
                // 如果该管理员是否是超管
                if (roles.contains("super-admin")) {
                    // 删除超管的数据
                    emps.remove(admin);
                }
            }
        }

        return R.success(emps);
    }

    @Override
    public R resetPwd(Integer empId) {
        // 获取员工信息
        Emp emp = empMapper.selectByPrimaryKey(empId);

        // 获取登录用户信息
        Emp loginUser = empMapper.selectByPrimaryKey(StpUtil.getLoginIdAsInt());

        if (emp == null) {
            return R.error(MessageConstant.EMP_NOT_EXIST);
        }
        if (emp.getEmpId() == 1 && !StpUtil.hasRole("super-admin") && !loginUser.getEmpId().equals(empId)) {
            return R.error(MessageConstant.NOT_PERMISSION_RESET_ADMIN_PWD);
        }

        String newPwd = PasswordUtils.encode(Constant.EMP_DEFAULT_PASSWORD);
        emp.setPassword(newPwd);
        empMapper.updateByPrimaryKeySelective(emp);
        return R.success(MessageConstant.RESET_EMP_PWD_SUCCESS);
    }

    @Override
    public R userInfo() {
        Emp emp = empMapper.selectByPrimaryKey(StpUtil.getLoginIdAsInt());
        emp.setPassword("******");
        EmpLoginVO empDTO = EmpLoginVO.builder()
                .token(StpUtil.getTokenInfo().getTokenValue())
                .account(emp.getAccount())
                .name(emp.getName())
                .avatar(emp.getAvatar())
                .deptId(emp.getDeptId())
                .roles(StpUtil.getRoleList())
                .build();
        return R.success(empDTO);
    }
}
