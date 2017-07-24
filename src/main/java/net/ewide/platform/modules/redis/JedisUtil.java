package net.ewide.platform.modules.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * jedis工具类
 * @author TianChong
 * @version 2016年4月15日
 */
@Component
public class JedisUtil {
    private final int expire = 60000;//缓存生存时间
    
    @Autowired
    private JedisPool jedisPool;
    
    public JedisPool getPool() {
        return jedisPool;
    }
    
    /**
     * 从jedis连接池中获取获取jedis对象
     * 
     * @return
     */
    public Jedis getJedis() {
        return jedisPool.getResource();
    }
    
    /**
     * 回收jedis
     * 
     * @param jedis
     */
    public void returnJedis(Jedis jedis) {
        jedisPool.returnResource(jedis);
    }
    
    /**
     * 设置过期时间
     * 
     * @author ruan 2013-4-11
     * @param key
     * @param seconds
     */
    public void expire(String key, int seconds) {
        if (seconds <= 0) {
            return;
        }
        Jedis jedis = getJedis();
        jedis.expire(key, seconds);
        returnJedis(jedis);
    }
    
    /**
     * 设置默认过期时间
     * 
     * @author ruan 2013-4-11
     * @param key
     */
    public void expire(String key) {
        expire(key, expire);
    }
 
}
