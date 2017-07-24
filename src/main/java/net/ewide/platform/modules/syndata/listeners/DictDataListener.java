package net.ewide.platform.modules.syndata.listeners;

import net.ewide.platform.common.service.ServiceException;
import net.ewide.platform.modules.enums.RedisEnum;
import net.ewide.platform.modules.redis.RedisClientTemplate;
import net.ewide.platform.modules.syndata.event.AreaDataEvent;
import net.ewide.platform.modules.syndata.event.DictDataEvent;
import net.ewide.platform.modules.syndata.event.UserDataEvent;
import net.ewide.platform.modules.sys.entity.Area;
import net.ewide.platform.modules.sys.entity.Dict;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import com.google.gson.Gson;

/**
 * 数据字典监听
 * @author wangtao
 * @version 2016年4月20日
 */
@Component
public class DictDataListener implements ApplicationListener<DictDataEvent>{
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RedisClientTemplate template;
	
	@Override
	public void onApplicationEvent(DictDataEvent event) {
		Dict dict = (Dict)event.getSource();
		Gson g = new Gson();
		if(!(event instanceof DictDataEvent)){
			return;
		}
		Jedis jedis=null;
		try {
			jedis=template.getJedis();
			Transaction tx = jedis.multi();
			if(event.getRedisEnum()==RedisEnum.save){
				if(!template.exists("dictInfo:"+dict.getId())){
					tx.rpush("dicts", dict.getId());//存放ID列表,追加ID
				}
				tx.set("dictInfo:"+dict.getId(), g.toJson(dict));
			}else if(event.getRedisEnum()==RedisEnum.delete){
				tx.del("dictInfo:"+dict.getId());
				tx.lrem("dicts", 0, dict.getId());
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
