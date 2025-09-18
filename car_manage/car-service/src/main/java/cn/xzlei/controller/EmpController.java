package cn.xzlei.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.xzlei.constant.MessageConstant;
import cn.xzlei.dto.EmpDTO;
import cn.xzlei.dto.EmpQueryParamDTO;
import cn.xzlei.entity.Emp;
import cn.xzlei.entity.R;
import cn.xzlei.service.EmpService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/emp")
public class EmpController {

    @Resource
    private EmpService empService;

    /**
     * 登录
     *
     * @param emp
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody Emp emp) {
        return empService.login(emp);
    }

    /**
     * 登出
     *
     * @return
     */
    @GetMapping("/logout")
    public R logout() {
        StpUtil.logout();
        return R.success("退出登录成功");
    }

    /**
     * 添加员工
     *
     * @param emp
     * @return
     */
    @SaCheckRole(value = {"admin", "super-admin"},mode = SaMode.OR)
    @PostMapping
    public R add(@RequestBody EmpDTO emp) {
        //如果添加的员工是管理员，需要判断是否为超管，只有超管才能添加管理员级别的员工
        if(!StpUtil.hasRole("super-admin") && emp.getDeptId() == 1){
            return R.error(MessageConstant.NOT_PERMISSION_ADD_ADMIN);
        }
        return empService.add(emp);
    }

    /**
     * 更新员工信息
     *
     * @param emp
     * @return
     */
    @PutMapping
    @SaCheckRole(value = {"admin", "super-admin"}, mode = SaMode.OR)
    public R update(@RequestBody EmpDTO emp) {
        return empService.update(emp);
    }

    /**
     * 批量删除员工信息
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @SaCheckRole(value = {"admin", "super-admin"}, mode = SaMode.OR)
    public R batchDelete(@RequestParam List<Integer> ids) {
        log.info("批量删除员工信息，{}", ids);
        return empService.batchDelete(ids);
    }

    /**
     * 查询所有员工信息
     *
     * @return
     */
    @GetMapping("/list")
    @SaCheckRole(value = {"admin", "super-admin","sale"}, mode = SaMode.OR)
    public R list(EmpQueryParamDTO queryParam) {
        log.info("查询所有员工信息");
        return empService.list(queryParam);
    }

    @PutMapping("/resetPwd")
    @SaCheckRole(value = {"admin", "super-admin"}, mode = SaMode.OR)
    public R resetPwd(Integer id) {
        log.info("重置员工密码，{}", id);
        return empService.resetPwd(id);
    }

    @GetMapping("/userInfo")
    public R userInfo() {
        log.info("获取当前登录用户信息");
        return empService.userInfo();
    }

}
