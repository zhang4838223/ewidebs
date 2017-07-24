package net.ewide.platform.modules.syndata.listeners;

import net.ewide.platform.common.service.ServiceException;
import net.ewide.platform.modules.enums.RedisEnum;
import net.ewide.platform.modules.redis.RedisClientTemplate;
import net.ewide.platform.modules.syndata.event.MenuDataEvent;
import net.ewide.platform.modules.sys.entity.Menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import com.google.gson.Gson;

/**
 * 菜单监听
 * @author wangtao
 * @version 2016年4月20日
 */
@Component
public class MenuDataListener implements ApplicationListener<MenuDataEvent>{
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RedisClientTemplate template;
	
	@Override
	public void onApplicationEvent(MenuDataEvent event) {
		Menu menu = (Menu)event.getSource();
		Gson g = new Gson();
		if(!(event instanceof MenuDataEvent)){
			return;
		}
		Jedis jedis=null;
		try {
			jedis=template.getJedis();
			Transaction tx = jedis.multi();
			if(event.getRedisEnum()==RedisEnum.save){
				if(!template.exists("menuInfo:"+menu.getId())){
					tx.rpush("menus", menu.getId());//存放ID列表,追加ID
				}
					tx.set("menuInfo:"+menu.getId(), g.toJson(menu));
			}else if(event.getRedisEnum()==RedisEnum.delete){
					tx.del("menuInfo:"+menu.getId());
					tx.lrem("menus", 0, menu.getId());
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
