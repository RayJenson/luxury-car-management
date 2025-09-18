package cn.xzlei.handler;

import cn.dev33.satoken.stp.StpInterface;
import cn.xzlei.entity.TRole;
import cn.xzlei.mapper.EmpRoleMapper;
import cn.xzlei.utils.LocalCacheUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义权限加载接口实现类
 */
@Slf4j
@Component    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private EmpRoleMapper empRoleMapper;

    @Resource
    private LocalCacheUtil localCacheUtil;

    @Override
    public List<String> getPermissionList(Object o, String s) {
        return List.of();
    }

    /**
     * 返回一个账号所拥有的角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Integer id = Integer.parseInt(loginId.toString());

        // 检查和缓存是否有效
        if(localCacheUtil.isCacheValid(id.toString())){
            log.debug("从缓存中获取员工{}的角色列表", id);
            return localCacheUtil.getUserRoles(id.toString());
        }

        // 缓存失效，从数据库中查询
        List<TRole> empRoles = empRoleMapper.selectByUserId(id);
        List<String> list = new ArrayList<String>();
        for (TRole empRole : empRoles) {
            list.add(empRole.getRole());
        }
        localCacheUtil.setUserRoles(id.toString(), list);
        log.debug("从数据库中获取员工{}的角色列表", id);
        log.debug("员工的角色是：{}", Arrays.toString(list.toArray()));
        return list;
    }

}
