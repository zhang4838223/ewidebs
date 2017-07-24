package net.ewide.platform.modules.syndata.listeners;

import java.util.List;

import net.ewide.platform.common.service.ServiceException;
import net.ewide.platform.modules.enums.RedisEnum;
import net.ewide.platform.modules.redis.RedisClientTemplate;
import net.ewide.platform.modules.syndata.event.UserDataEvent;
import net.ewide.platform.modules.sys.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 用户监听
 * @author TianChong
 * @version 2016年4月18日
 */
@Component
public class UserDataListener implements ApplicationListener<UserDataEvent>{
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RedisClientTemplate template;
	
	@Override
	public void onApplicationEvent(UserDataEvent event) {
		User user = (User)event.getSource();
		Gson g = new Gson();// new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		if(!(event instanceof UserDataEvent)){
			return;
		}
		Jedis jedis=null;
		try {
			jedis=template.getJedis();
			Transaction tx = jedis.multi();
			if(event.getRedisEnum()==RedisEnum.save){
				//保存用户
				if(!template.exists("userInfo:"+user.getId())){
					tx.rpush("users", user.getId());//存放ID列表,追加ID
				}
				tx.set("userInfo:"+user.getId(), g.toJson(user));
				tx.set("userName:"+user.getLoginName(), g.toJson(user));
				//删除用户关联角色
				tx.del("userRoles:"+user.getId());
				//保存用户关联角色
				List<String> list = user.getRoleIdList();
				for (String string : list) {
					tx.rpush("userRoles:"+user.getId(),string);
				}
				//删除用户关联机构
				tx.del("userOffices:"+user.getId());
				//保存用户关联机构
				List<String> list1 = user.getOfficeIdList();
				for (String string : list1) {
					tx.rpush("userOffices:"+user.getId(),string);
				}
			}else if(event.getRedisEnum()==RedisEnum.delete){
				tx.del("userInfo:"+user.getId());
				tx.del("userRoles:"+user.getId());
				tx.del("userOffices:"+user.getId());
				tx.lrem("users:", 0, user.getId());
			}else if(event.getRedisEnum()==RedisEnum.update){
				tx.set("userInfo:"+user.getId(), g.toJson(user));
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
