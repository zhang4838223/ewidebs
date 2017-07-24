package net.ewide.platform.modules.redis;


import redis.clients.jedis.Jedis;

public interface RedisDataSource {

	 public abstract Jedis getRedisClient();
	 public void returnResource(Jedis jedis);
//	 public abstract ShardedJedis getRedisClient();
//	 public void returnResource(ShardedJedis shardedJedis);
}
