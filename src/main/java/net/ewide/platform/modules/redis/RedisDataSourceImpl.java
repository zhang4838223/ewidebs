package net.ewide.platform.modules.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Repository("redisDataSource")
public class RedisDataSourceImpl implements RedisDataSource{
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private JedisPool jedisPool;
	
//	@Autowired
//    private ShardedJedisPool    shardedJedisPool;
	
	public Jedis getRedisClient() {
		try {
			Jedis jedis = jedisPool.getResource();
            return jedis;
        } catch (Exception e) {
        	logger.error("getRedisClent error", e);
        }
		return null;
	}
	
	public void returnResource(Jedis jedis) {
		jedisPool.returnResource(jedis);
	}
	
	/*public ShardedJedis getRedisClient() {
		try {
            ShardedJedis shardJedis = shardedJedisPool.getResource();
            return shardJedis;
        } catch (Exception e) {
        	logger.error("getRedisClent error", e);
        }
		return null;
	}*/

	/*public void returnResource(ShardedJedis shardedJedis) {
		shardedJedisPool.returnResource(shardedJedis);
	}*/
	
}
