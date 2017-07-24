package net.ewide.platform.modules.syndata.listeners;

import java.util.List;

import net.ewide.platform.common.service.ServiceException;
import net.ewide.platform.modules.enums.RedisEnum;
import net.ewide.platform.modules.redis.RedisClientTemplate;
import net.ewide.platform.modules.syndata.event.RoleDataEvent;
import net.ewide.platform.modules.sys.entity.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import com.google.gson.Gson;

/**
 * 用户监听
 * @author TianChong
 * @version 2016年4月18日
 */
@Component
public class RoleDataListener implements ApplicationListener<RoleDataEvent>{
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RedisClientTemplate template;
	
	@Override
	public void onApplicationEvent(RoleDataEvent event) {
		Role role = (Role)event.getSource();
		Gson g = new Gson();
		if(!(event instanceof RoleDataEvent)){
			return;
		}
		Jedis jedis=null;
		try {
			jedis=template.getJedis();
			Transaction tx = jedis.multi();
			if(event.getRedisEnum()==RedisEnum.save){
				//保存用户
				if(!template.exists("roleInfo:"+role.getId())){
					tx.rpush("roles", role.getId());//存放ID列表,追加ID
				}
				tx.set("roleInfo:"+role.getId(), g.toJson(role));
				//删除菜单关联角色
				tx.del("roleMenu:"+role.getId());
				//保存菜单关联角色
				List<String> list = role.getMenuIdList();
				for (String string : list) {
					tx.rpush("roleMenu:"+role.getId(),string);
				}
				//删除用户关联机构
				tx.del("roleOffice:"+role.getId());
				//保存用户关联机构
				List<String> list1 = role.getOfficeIdList();
				for (String string : list1) {
					tx.rpush("roleOffice:"+role.getId(),string);
				}
			}else if(event.getRedisEnum()==RedisEnum.delete){
				tx.del("roleInfo:"+role.getId());
				tx.del("roleMenu:"+role.getId());
				tx.del("roleOffice:"+role.getId());
				tx.lrem("roles:", 0, role.getId());
			}		
			tx.exec();
//			template.disconnect(jedis);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}finally{
			template.returnResource(jedis);
			template.disconnect(jedis);
		}
		
		
//		System.out.println(event.getFlag());
//		
//		
//		
//		String objstr = g.toJson(obj);
//		
//		
//		System.out.println(objstr);
//		
//		
//		throw new IllegalArgumentException("异常测试！");
		
	}
	
	
	

}
