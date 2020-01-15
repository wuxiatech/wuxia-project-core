/*
 * Created on :2016年4月13日
 * Author     :songlin
 * Change History
 * Version       Date         Author           Reason
 * <Ver.No>     <date>        <who modify>       <reason>
 * Copyright 2014-2020 武侠科技 All right reserved.
 */
package cn.wuxia.project.basic.cache;

import cn.wuxia.common.cached.CacheClient;
import cn.wuxia.common.cached.redis.ObjectsTranscoder;
import cn.wuxia.common.cached.redis.RedisUtils;
import cn.wuxia.common.util.ArrayUtil;
import cn.wuxia.common.util.StringUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Iterator;
import java.util.Set;

/**
 * 简单的操作，复杂的操作需要直接操作jedis
 * 推荐使用redisson
 *
 * @author songlin
 */
public class RedisTemplateCacheClient implements CacheClient {
    private static Logger logger = LoggerFactory.getLogger(RedisTemplateCacheClient.class);

    private RedisTemplate redisTemplate;

    private int expiredTime = 0;



    @Override
    public void init(String... server) {
        String host;
        String port = null;
        if (ArrayUtil.isNotEmpty(server)) {
            host = StringUtil.substringBefore(server[0], ":");
            port = StringUtil.substringAfter(server[0], ":");
        } else {
            host = "127.0.0.1";
        }

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(50);
        config.setMaxWaitMillis(3000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);

        port = StringUtil.isBlank(port) ? "6379" : port;
        RedisStandaloneConfiguration standaloneConfig = new org.springframework.data.redis.connection.RedisStandaloneConfiguration(host, Integer.valueOf(port));
        standaloneConfig.setDatabase(0);

        JedisConnectionFactory connectionFactory = new
                org.springframework.data.redis.connection.jedis.JedisConnectionFactory(standaloneConfig);
        connectionFactory.setPoolConfig(config);
        redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory);
    }


    public int getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(int expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Override
    public boolean containKey(String key, String namespace) {
        return containKey(namespace + ":" + key);
    }

    @Override
    public boolean containKey(String key) {
        final byte[] keyb = RedisUtils.formatKey(key).getBytes();
        return BooleanUtils.toBooleanDefaultIfNull(redisTemplate.hasKey(keyb), false);
    }

    @Override
    public void add(String key, Object value, int expiredTime) {
        set(key, value, expiredTime);
    }

    @Override
    public void add(String key, Object value, String namespace) {
        set(namespace + ":" + key, value);
    }

    @Override
    public void add(String key, Object value) {
        set(key, value);

    }

    @Override
    public void set(String key, Object value, int expiredTime) {
        if (value == null) {
            return;
        }
        key = RedisUtils.formatKey(key);
        final byte[] keyf = key.getBytes();
        final byte[] valuef = new ObjectsTranscoder().serialize(value);

        redisTemplate.execute((RedisCallback<Long>) connection -> {
            connection.setEx(keyf, expiredTime, valuef);
            return 1L;
        });
    }

    @Override
    public void set(String key, Object value, String namespace) {
        set(namespace + ":" + key, value);
    }

    @Override
    public void set(String key, Object value) {
        set(key, value, expiredTime);
    }

    @Override
    public void replace(String key, Object value, int expiredTime) {
        set(key, value, expiredTime);
    }

    @Override
    public void replace(String key, Object value, String namespace) {
        set(namespace + ":" + key, value);
    }

    @Override
    public void replace(String key, Object value) {
        set(key, value);
    }

    @Override
    public <T> T get(String key) {
        final String keyf = RedisUtils.formatKey(key);
        return (T) redisTemplate.execute((RedisCallback<T>) connection -> {
            byte[] key1 = keyf.getBytes();
            byte[] value = connection.get(key1);
            if (value == null) {
                return null;
            }
            return (T) new ObjectsTranscoder().deserialize(value);
        });
    }

    @Override
    public long incr(String key) {
        final byte[] keyb = RedisUtils.formatKey(key).getBytes();
        return (long) redisTemplate.execute((RedisCallback<Long>) connection -> connection.incr(keyb));
    }

    @Override
    public long incr(String key, String namespace) {
        return incr(namespace + ":" + key);
    }

    @Override
    public long incr(String key, long by) {
        final byte[] keyb = RedisUtils.formatKey(key).getBytes();
        return (long) redisTemplate.execute((RedisCallback<Long>) connection -> connection.incrBy(keyb, by));
    }

    @Override
    public long incr(String key, long by, long defaultValue) {
        final byte[] keyb = RedisUtils.formatKey(key).getBytes();
        Long r = (Long) redisTemplate.execute((RedisCallback<Long>) connection -> connection.incrBy(keyb, by));
        if (r == null) {
            return defaultValue;
        }
        return r;
    }

    @Override
    public long incr(String key, long by, long defaultValue, String namespace) {
        return incr(namespace + ":" + key, by, defaultValue);
    }

    @Override
    public long decr(String key) {
        final byte[] keyb = RedisUtils.formatKey(key).getBytes();
        return (long) redisTemplate.execute((RedisCallback<Long>) connection -> connection.decr(keyb));
    }

    @Override
    public long decr(String key, String namespace) {
        return decr(namespace + ":" + key);
    }

    @Override
    public long decr(String key, long by) {
        final byte[] keyb = RedisUtils.formatKey(key).getBytes();
        return (long) redisTemplate.execute((RedisCallback<Long>) connection -> connection.decrBy(keyb, by));
    }

    @Override
    public long decr(String key, long by, long defaultValue) {
        final byte[] keyb = RedisUtils.formatKey(key).getBytes();
        Long r = (Long) redisTemplate.execute((RedisCallback<Long>) connection -> connection.decrBy(keyb, by));
        if (r == null) {
            return defaultValue;
        }
        return r;
    }

    @Override
    public long decr(String key, long by, long defaultValue, String namespace) {
        return decr(namespace + ":" + key, by, defaultValue);
    }

    @Override
    public void delete(String key) {
        final String keyf = RedisUtils.formatKey(key);
        redisTemplate.execute((RedisCallback<Long>) connection -> connection.del(keyf.getBytes()));
    }

    @Override
    public void flushAll() {
        redisTemplate.execute((RedisCallback<String>) connection -> {
            connection.flushDb();
            return "ok";
        });
    }

    @Override
    public void flushAll(String[] servers) {
        // TODO Auto-generated method stub
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void add(String key, Object value, int expiredTime, String namespace) {
        set(namespace + ":" + key, value, expiredTime);
    }

    @Override
    public void set(String key, Object value, int expiredTime, String namespace) {
        set(namespace + ":" + key, value, expiredTime);

    }

    @Override
    public void replace(String key, Object value, int expiredTime, String namespace) {
        set(namespace + ":" + key, value, expiredTime);

    }

    @Override
    public <T> T get(String key, String namespace) {
        return get(namespace + ":" + key);
    }

    @Override
    public void delete(String key, String namespace) {
        delete(namespace + ":" + key);
    }

    @Override
    public void flush(String namespace) {
        redisTemplate.execute((RedisCallback<String>) connection -> {
            Set<byte[]> set = connection.hKeys((namespace + "*").getBytes());
            Iterator<byte[]> it = set.iterator();
            while (it.hasNext()) {
                byte[] keyb = it.next();
                connection.del(keyb);
            }
            return "ok";
        });
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
