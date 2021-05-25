package com.yuzb.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 写入缓存
     *
     * @param key   string key
     * @param value string value
     * @return bool
     * @author yuzb
     * @Date: 2021/5/9
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存带有效期
     *
     * @param key        string key
     * @param value      string value
     * @param expireTime Long 过期时间
     * @param timeUnit   TimeUnit 时间格式
     * @return bool
     * @author yuzb
     * @Date: 2021/5/9
     */
    public boolean set(final String key, Object value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 数字自增 计数器使用
     *
     * @param key string key
     * @return bool
     * @author yuzb
     * @since 2020-04-13
     */
    public boolean Incr(final String key) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            operations.increment(key);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys string key
     * @author yuzb
     * @Date: 2021/5/9
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern string key
     * @author yuzb
     * @Date: 2021/5/9
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key string key
     * @author yuzb
     * @Date: 2021/5/9
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }


    /**
     * 判断缓存中是否有对应的value
     *
     * @param key string key
     * @return bool
     * @author yuzb
     * @Date: 2021/5/9
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }


    /**
     * 读取缓存
     *
     * @param key string key
     * @return Object
     * @author yuzb
     * @Date: 2021/5/9
     */

    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */

    /**
     * 哈希添加
     *
     * @param key     string key
     * @param hashKey Object hashKey
     * @param value   Object value
     * @author yuzb
     * @Date: 2021/5/9
     */

    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 哈希删除
     *
     * @param key     string key
     * @param hashKey Object hashKey
     * @author yuzb
     * @Date: 2021/5/9
     */

    public void hmDelete(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.delete(key, hashKey);
    }

    /**
     * 哈希获取数据
     *
     * @param key     string key
     * @param hashKey Object hashKey
     * @return Object
     * @author yuzb
     * @Date: 2021/5/9
     */
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 哈希数量
     *
     * @param key string key
     * @return Object
     * @author yuzb
     * @Date: 2021/5/9
     */
    public Long getHashSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 列表添加左边添加
     *
     * @param k string key
     * @param v Object v
     * @author yuzb
     * @Date: 2021/5/9
     */
    public void lPush(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.leftPush(k, v);
    }

    /**
     * 从右边拿出来一个
     *
     * @param k string key
     * @param t Long 超时秒数
     * @author yuzb
     * @Date: 2021/5/9
     */
    public Object getRightPop(String k, Long t) {
        return redisTemplate.opsForList().rightPop(k, t, TimeUnit.SECONDS);
    }

    /**
     * 列表获取数量
     *
     * @param k string key
     * @return Long
     * @author yuzb
     * @Date: 2021/5/9
     */
    public Long getListSize(String k) {
        return redisTemplate.opsForList().size(k);
    }

    /**
     * 列表获取
     *
     * @param k  string key
     * @param l  long l
     * @param l1 long l1
     * @return List<Object>
     * @author yuzb
     * @Date: 2021/5/9
     */
    public List<Object> lRange(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * 集合添加
     *
     * @param key   string key
     * @param value Object value
     * @author yuzb
     * @Date: 2021/5/9
     */
    public void add(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合获取
     *
     * @param key string key
     * @return Set<Object>
     * @author yuzb
     * @Date: 2021/5/9
     */
    public Set<Object> setMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param scoure
     */

    /**
     * 有序集合添加    排行榜使用
     *
     * @param key   string key
     * @param value Object value
     * @param score double scoure
     * @author yuzb
     * @Date: 2021/5/9
     */
    public void zAdd(String key, Object value, double score) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, score);
    }


    /**
     * 有序集合获取    排行榜使用
     *
     * @param key   string key
     * @param score double scoure
     * @return Set<Object>
     * @author yuzb
     * @Date: 2021/5/9
     */
    public Set<Object> rangeByScore(String key, double score, double score1) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, score, score1);
    }
}
