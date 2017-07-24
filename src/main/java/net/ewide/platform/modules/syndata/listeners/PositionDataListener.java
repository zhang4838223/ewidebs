package net.ewide.platform.modules.syndata.listeners;

import net.ewide.platform.common.service.ServiceException;
import net.ewide.platform.modules.enums.RedisEnum;
import net.ewide.platform.modules.redis.RedisClientTemplate;
import net.ewide.platform.modules.syndata.event.PositionDataEvent;
import net.ewide.platform.modules.sys.entity.Position;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import com.google.gson.Gson;

/**
 * 职位监听
 * @author TianChong
 * @version 2016年4月18日
 */
@Component
public class PositionDataListener implements ApplicationListener<PositionDataEvent>{
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RedisClientTemplate template;
	
	@Override
	public void onApplicationEvent(PositionDataEvent event) {
		Position position = (Position)event.getSource();
		Gson g = new Gson();
		if(!(event instanceof PositionDataEvent)){
			return;
		}
		Jedis jedis=null;
		try {
			jedis=template.getJedis();
			Transaction tx = jedis.multi();
			if(event.getRedisEnum()==RedisEnum.save){
				if(!template.exists("positionInfo:"+position.getId())){
					tx.rpush("positions", position.getId());//存放ID列表,追加ID
				}
				tx.set("positionInfo:"+position.getId(), g.toJson(position));
			}else if(event.getRedisEnum()==RedisEnum.delete){
				tx.del("positionInfo:"+position.getId());
				tx.lrem("positions", 0, position.getId());
			}
			tx.exec();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}finally{
			template.returnResource(jedis);
			template.disconnect(jedis);
		}
	}
	
	
	

}
