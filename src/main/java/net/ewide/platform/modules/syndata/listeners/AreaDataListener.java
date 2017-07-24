package net.ewide.platform.modules.syndata.listeners;

import net.ewide.platform.common.service.ServiceException;
import net.ewide.platform.modules.enums.RedisEnum;
import net.ewide.platform.modules.redis.RedisClientTemplate;
import net.ewide.platform.modules.syndata.event.AreaDataEvent;
import net.ewide.platform.modules.syndata.event.UserDataEvent;
import net.ewide.platform.modules.sys.entity.Area;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import com.google.gson.Gson;

/**
 * 区域监听
 * @author wangtao
 * @version 2016年4月20日
 */
@Component
public class AreaDataListener implements ApplicationListener<AreaDataEvent>{
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RedisClientTemplate template;
	
	@Override
	public void onApplicationEvent(AreaDataEvent event) {
		Area area = (Area)event.getSource();
		Gson g = new Gson();
		if(!(event instanceof AreaDataEvent)){
			return;
		}
		Jedis jedis=null;
		try {
			jedis=template.getJedis();
			Transaction tx = jedis.multi();
			if(event.getRedisEnum()==RedisEnum.save){
				if(!template.exists("areaInfo:"+area.getId())){
					tx.rpush("areas", area.getId());//存放ID列表,追加ID
				}
				tx.set("areaInfo:"+area.getId(), g.toJson(area));
				
			}else if(event.getRedisEnum()==RedisEnum.delete){
				tx.del("areaInfo:"+area.getId());
				tx.lrem("areas", 0, area.getId());
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
