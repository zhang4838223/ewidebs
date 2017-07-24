package net.ewide.platform.modules.syndata.listeners;

import java.util.List;

import net.ewide.platform.common.service.ServiceException;
import net.ewide.platform.modules.enums.RedisEnum;
import net.ewide.platform.modules.redis.RedisClientTemplate;
import net.ewide.platform.modules.syndata.event.UserGroupDataEvent;
import net.ewide.platform.modules.sys.entity.UserGroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import com.google.gson.Gson;

/**
 * 用户组监听
 * @author wangtao
 * @version 2016年4月20日
 */
@Component
public class UserGroupDataListener implements ApplicationListener<UserGroupDataEvent>{
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RedisClientTemplate template;
	
	@Override
	public void onApplicationEvent(UserGroupDataEvent event) {
		UserGroup userGroup = (UserGroup)event.getSource();
		Gson g = new Gson();
		if(!(event instanceof UserGroupDataEvent)){
			return;
		}
		Jedis jedis = null;
		try {
			jedis = template.getJedis();
			Transaction tx = jedis.multi();
			if (event.getRedisEnum() == RedisEnum.save) {
				if(!template.exists("userGroupInfo:"+userGroup.getId())){
					tx.rpush("userGroups", userGroup.getId());//存放ID列表,追加ID
				}
				tx.set("userGroupInfo:"+userGroup.getId(), g.toJson(userGroup));
				tx.del("userGroupRoles:"+userGroup.getId());
				List<String> list = userGroup.getRoleIdList();
				for (String string : list) {
					tx.rpush("userGroupRoles:"+userGroup.getId(),string);
				}
				tx.del("userGroupUsers:"+userGroup.getId());
				List<String> list1 = userGroup.getUserIdList();
				for (String string : list1) {
					tx.rpush("userGroupUsers:"+userGroup.getId(),string);
					tx.rpush("userUserGroups:"+string,userGroup.getId());
				}
			} else if (event.getRedisEnum() == RedisEnum.delete) {
				tx.del("userGroupInfo:"+userGroup.getId());
				tx.del("userGroupRoles:"+userGroup.getId());
				tx.del("userGroupUsers:"+userGroup.getId());
				tx.lrem("userGroups", 0, userGroup.getId());
			}
			tx.exec();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		} finally {
			template.returnResource(jedis);
			template.disconnect(jedis);
		}
		
	}
	
	
	

}
