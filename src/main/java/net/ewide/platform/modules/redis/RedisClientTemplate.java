package net.ewide.platform.modules.redis;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * redis常用方法
 * @author TianChong
 * @version 2016年4月15日
 */
@Component
public class RedisClientTemplate {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private RedisDataSource  redisDataSource;
	

	
	
	 /**
     * 设置单个值
     * 
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        Jedis jedis = redisDataSource.getRedisClient();
        try {
            return jedis.set(key, value);
        } finally {
            redisDataSource.returnResource(jedis);
        }
    }

    /**
     * 获取单个值
     * 
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = redisDataSource.getRedisClient();
        try {
            return jedis.get(key);
        }  finally {
            redisDataSource.returnResource(jedis);
        }
    }
    
    /**
     * 判断Key是否存在
     * @param key
     * @return
     */
    public Boolean exists(String key) {
        Jedis jedis = redisDataSource.getRedisClient();
        try {
            return jedis.exists(key);
        } finally {
            redisDataSource.returnResource(jedis);
        }
    }
    
    /**
     * 返回key对应数据类型
     * @param key
     * @return
     */
    public String type(String key) {
        Jedis jedis = redisDataSource.getRedisClient();
        try {
            return jedis.type(key);
        } finally {
            redisDataSource.returnResource(jedis);
        }
    }
    

    /**
     * 指定键的过期时间
     * 
     * @param key
     * @param unixTime
     * @return
     */
    public Long expire(String key, int seconds) {
        Jedis jedis = redisDataSource.getRedisClient();
        try {
            return jedis.expire(key, seconds);
        } finally {
            redisDataSource.returnResource(jedis);
        }
    }
    
    /**
     * 指定的键过期时间。在这里，时间是在Unix时间戳格式
     * 
     * @param key
     * @param unixTime
     * @return
     */
    public Long expireAt(String key, long unixTime) {
        Jedis jedis = redisDataSource.getRedisClient();
        try {
            return jedis.expireAt(key, unixTime);
        } finally {
            redisDataSource.returnResource(jedis);
        }
    }
    
    /**
     * 获取键到期的剩余时间
     * @param key
     * @return
     */
    public Long ttl(String key) {
        Jedis jedis = redisDataSource.getRedisClient();
        try {
            return jedis.ttl(key);
        } finally {
            redisDataSource.returnResource(jedis);
        }
    }
    
    /**
     * 删除键
     * @param key
     * @return
     */
    public Long del(String key) {
    	if(!exists(key))return null;
        Jedis jedis = redisDataSource.getRedisClient();
        try {
        	return jedis.del(key);
        } finally {
            redisDataSource.returnResource(jedis);
        }
    }
    
    /**
     * 在名称为key的list尾添加一个值为value的元素
     * @param key
     * @param string
     * @return
     */
    public Long rpush(String key, String string) {
        Jedis jedis = redisDataSource.getRedisClient();
        try {
            return jedis.rpush(key, string);
        } finally {
            redisDataSource.returnResource(jedis);
        }
    }
    
    /**
     * 在名称为key的list头添加一个值为value的 元素
     * @param key
     * @param string
     * @return
     */
    public Long lpush(String key, String string) {
        Jedis jedis = redisDataSource.getRedisClient();
        try {
            return jedis.lpush(key, string);
        } finally {
            redisDataSource.returnResource(jedis);
        }
    }
    
    /**
     * 返回名称为key的list的长度
     * @param key
     * @return
     */
    public Long llen(String key) {
        Jedis jedis = redisDataSource.getRedisClient();
        try {
            return jedis.llen(key);
        } finally {
            redisDataSource.returnResource(jedis);
        }
    }
    
    /**
     * 查询指定范围记录 可用于分页
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = redisDataSource.getRedisClient();
        try {
            return jedis.lrange(key, start, end);
        } finally {
            redisDataSource.returnResource(jedis);
        }
    }
    
    /**
     * 移除等于value的元素，当count>0时，从表头开始查找，移除count个；当count=0时，从表头开始查找，移除所有等于value的；当count<0时，从表尾开始查找，移除|count| 个
     * @param key
     * @param count
     * @param value
     * @return
     */
    public Long lrem(String key,int count,String value){
    	 Jedis jedis = redisDataSource.getRedisClient();
    	 try {
             return jedis.lrem(key, count, value);
         } finally {
             redisDataSource.returnResource(jedis);
         }
    }
    
    /**
     * 管道
     * @param shardedJedisPipeline
     * @return
     */
    public Pipeline pipelined() {
        Jedis jedis = redisDataSource.getRedisClient();
        try {
           return jedis.pipelined();
        } finally {
            redisDataSource.returnResource(jedis);
        }
    }
    
    /**
     * 清空所有键值
     * @return
     */
    public String flushDB(){
    	Jedis jedis = redisDataSource.getRedisClient();
        try {
           return jedis.flushDB();
        } finally {
            redisDataSource.returnResource(jedis);
        }
    }
    
    public Jedis getJedis(){
    	return redisDataSource.getRedisClient();
    }
    
    public void returnResource(Jedis jedis){
    	redisDataSource.returnResource(jedis);
    }
    
	public void disconnect(Jedis jedis) {
		if(jedis!=null){
			jedis.disconnect();
		}
//        Jedis jedis = redisDataSource.getRedisClient();
//        jedis.disconnect();
    }
}
