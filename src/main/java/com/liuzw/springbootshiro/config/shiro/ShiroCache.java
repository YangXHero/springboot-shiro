package com.liuzw.springbootshiro.config.shiro;

import com.liuzw.springbootshiro.constants.RedisConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author liuzw
 */
@Slf4j
public class ShiroCache<K, V> implements Cache<K, V> {

    private RedisTemplate redisTemplate;

    ShiroCache(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public V get(K key) throws CacheException {
        log.info("---------->  获取cache, key = [{}]", getCacheKey(key));
        Object value = redisTemplate.opsForValue().get(getCacheKey(key));
        return (V) value;
    }

    @Override
    public V put(K key, V value) throws CacheException {
        log.info("----------> 新增cache, key = [{}]", getCacheKey(key));
        V old = get(key);
        redisTemplate.opsForValue().set(getCacheKey(key), value, 1800, TimeUnit.SECONDS);
        return old;
    }

    @Override
    public V remove(K key) throws CacheException {
        log.info("----------> 移除cache, key = [{}]", getCacheKey(key));
        V old = get(key);
        redisTemplate.delete(getCacheKey(key));
        return old;
    }

    @Override
    public void clear() throws CacheException {
        log.info("----------> 移除cache, ShiroCache.clear()");
        redisTemplate.delete(keys());
    }

    @Override
    public int size() {
        log.info("----------> 获取cache 个数, ShiroCache.size()");
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        log.info("----------> 获取cache 的所有keys, ShiroCache.keys()");
        return redisTemplate.keys(getCacheKey("*"));
    }

    @Override
    public Collection<V> values() {
        log.info("----------> 获取cache 的所有value, ShiroCache.values()");
        Set<K> set = keys();
        List<V> list = new ArrayList<>();
        for (K s : set) {
            list.add(get(s));
        }
        return list;
    }

    private Object getCacheKey(Object k) {
        return RedisConstants.REDIS_SHIRO_CACHE + k;
    }
}