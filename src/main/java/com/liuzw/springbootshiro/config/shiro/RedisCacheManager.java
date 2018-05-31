package com.liuzw.springbootshiro.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author liuzw
 */
@Slf4j
public class RedisCacheManager implements CacheManager {

    private RedisTemplate redisTemplate;

    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        //设置序列化Key的实例化对象
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        this.redisTemplate = redisTemplate;
    }

    /**
     * 本地缓存caches
     */
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        if (name == null ){
            log.info("-------> RedisCacheManager.getCache name 为空 ");
            return null;
        }
        log.info("-------> 获取名称为: " + name + " 的 RedisCacheManager 实例");
        Cache cache = caches.get(name);
        if (cache == null) {
            // 创建新的Cache
            cache = new ShiroCache<K, V>(redisTemplate);
            caches.put(name, cache);
        }
        return cache;
    }

}