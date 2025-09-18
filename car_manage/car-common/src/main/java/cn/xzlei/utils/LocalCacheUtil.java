package cn.xzlei.utils;

import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存工具类
 * 用于缓存用户角色等信息，避免频繁查询数据库
 */
@Component
public class LocalCacheUtil {
    
    // 角色缓存
    private final Map<String, List<String>> roleCache = new ConcurrentHashMap<>();
    
    // 缓存时间戳
    private final Map<String, Long> cacheTime = new ConcurrentHashMap<>();
    
    // 缓存过期时间（毫秒），默认5分钟
    private static final long DEFAULT_CACHE_TIMEOUT = 5 * 60 * 1000;

    /**
     * -- SETTER --
     *  设置缓存过期时间
     *
     * @param timeout 过期时间（毫秒）
     */
    // 自定义缓存过期时间
    @Setter
    public long cacheTimeout = DEFAULT_CACHE_TIMEOUT;

    /**
     * 获取用户角色列表
     * @param userId 用户ID
     * @return 角色列表，如果缓存不存在或过期返回空列表
     */
    public List<String> getUserRoles(String userId) {
        if (!isCacheValid(userId)) {
            // 缓存无效，移除过期数据
            roleCache.remove(userId);
            cacheTime.remove(userId);
            return new ArrayList<>();
        }
        return roleCache.get(userId);
    }
    
    /**
     * 设置用户角色列表缓存
     * @param userId 用户ID
     * @param roles 角色列表
     */
    public void setUserRoles(String userId, List<String> roles) {
        roleCache.put(userId, roles);
        cacheTime.put(userId, System.currentTimeMillis());
    }
    
    /**
     * 检查缓存是否有效
     * @param key 缓存键
     * @return true-有效，false-无效
     */
    public boolean isCacheValid(String key) {
        Long cacheTimestamp = cacheTime.get(key);
        if (cacheTimestamp == null) {
            return false;
        }
        return (System.currentTimeMillis() - cacheTimestamp) < cacheTimeout;
    }
    
    /**
     * 清除指定用户的缓存
     * @param userId 用户ID
     */
    public void clearUserCache(String userId) {
        roleCache.remove(userId);
        cacheTime.remove(userId);
    }
    
    /**
     * 清除所有缓存
     */
    public void clearAllCache() {
        roleCache.clear();
        cacheTime.clear();
    }
    
    /**
     * 获取缓存大小
     * @return 缓存中的条目数量
     */
    public int getCacheSize() {
        return roleCache.size();
    }
}
