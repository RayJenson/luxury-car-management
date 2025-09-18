package cn.xzlei.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.strategy.SaStrategy;
import cn.hutool.jwt.JWTUtil;
import cn.xzlei.constant.Constant;
import cn.xzlei.entity.Emp;
import cn.xzlei.entity.TRole;
import cn.xzlei.mapper.EmpMapper;
import cn.xzlei.mapper.EmpRoleMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    private final EmpMapper empMapper;
    private final EmpRoleMapper empRoleMapper;

    public SaTokenConfigure(EmpMapper empMapper, EmpRoleMapper empRoleMapper) {
        this.empMapper = empMapper;
        this.empRoleMapper = empRoleMapper;
    }
    // 注册拦截器


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> {
                    SaRouter.match("/**")
                            .notMatch("/emp/login", "/error", "/error/", "/", "/dept/list")
                            .check(r -> StpUtil.checkLogin());

                    // 根据路由划分模块，不同模块不同鉴权

                    SaRouter.match("/supplier/**", r -> StpUtil.checkRoleOr("admin","super-admin"));
                    SaRouter.match("/customer/**", r -> StpUtil.checkRoleOr("admin","super-admin","sale"));
                    SaRouter.match("/product/**", r -> StpUtil.checkRoleOr("admin","super-admin","sale"));
                    SaRouter.match("/purchase/**", r -> StpUtil.checkRoleOr("admin","super-admin"));
                    SaRouter.match("/cartype/**", r -> StpUtil.checkRoleOr("admin","super-admin","sale"));
                    SaRouter.match("/inventory/**", r -> StpUtil.checkRoleOr("admin","super-admin","logistics"));
                }))
                .addPathPatterns("/**")
                .excludePathPatterns("/error");

//        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
    }

    /**
     * 重写 Sa-Token 框架内部算法策略
     */
    @PostConstruct
    public void rewriteSaStrategy() {
        // 重写 Token 生成策略
        SaStrategy.instance.createToken = (loginId, loginType) -> {
            // 创建自定义token
            Integer id = (Integer) loginId;
            log.info("用户ID：{}",id );
            Emp tEmp = empMapper.selectByPrimaryKey(id);
            List<TRole> tRoles = empRoleMapper.selectByUserId(id);
            // 将角色信息存入List<String>集合中
            List<String> roles = tRoles.stream().map(TRole::getRole).toList();
            Map<String, Object> tokenInfo = new HashMap<>();
            tokenInfo.put("id", tEmp.getEmpId());
            tokenInfo.put("name", tEmp.getName());
            tokenInfo.put("deptId", tEmp.getDeptId());
            tokenInfo.put("phone", tEmp.getPhone());
            tokenInfo.put("avatar", tEmp.getAvatar());
            tokenInfo.put("role", roles);
            tokenInfo.put("login_time", System.currentTimeMillis());
            return JWTUtil.createToken(tokenInfo, Constant.TOKEN_SIGN.getBytes());
        };
    }
}