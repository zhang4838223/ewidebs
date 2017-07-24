package net.ewide.platform.modules.syndata.listeners;

import java.util.List;

import net.ewide.platform.common.service.ServiceException;
import net.ewide.platform.modules.enums.RedisEnum;
import net.ewide.platform.modules.redis.RedisClientTemplate;
import net.ewide.platform.modules.syndata.event.OfficeDataEvent;
import net.ewide.platform.modules.sys.entity.Office;
import net.ewide.platform.modules.sys.entity.User;
import net.ewide.platform.modules.sys.entity.UserOffice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import com.google.gson.Gson;

/**
 * 机构监听
 * @author TianChong
 * @version 2016年4月20日
 */
@Component
public class OfficeDataListener implements ApplicationListener<OfficeDataEvent>{
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RedisClientTemplate template;
	
	@Override
	public void onApplicationEvent(OfficeDataEvent event) {
		Office office = (Office)event.getSource();
		Gson g = new Gson();
		if(!(event instanceof OfficeDataEvent)){
			return;
		}
		Jedis jedis=null;
		try {
			jedis=template.getJedis();
			Transaction tx = jedis.multi();
			if(event.getRedisEnum()==RedisEnum.save){
				//保存用户
				if(!template.exists("officeInfo:"+office.getId())){
					tx.rpush("offices", office.getId());//存放ID列表,追加ID
				}
				String primaryPersonId = office.getPrimaryPerson().getId();
				String primaryPersonName = office.getPrimaryPerson().getName();
				String deputyPersonId = office.getDeputyPerson().getId();
				String deputyPersonName = office.getDeputyPerson().getName();
				String str = g.toJson(office);
				str = str.substring(0, str.length()-1)+",\"primaryPersonId\":\""+primaryPersonId
						+"\",\"primaryPersonName\":\""+primaryPersonName
						+"\",\"deputyPersonId\":\""+deputyPersonId
						+"\",\"deputyPersonName\":\""+deputyPersonName+"\"}";
				tx.set("officeInfo:"+office.getId(), str);
				List<UserOffice> list = office.getUserOfficeList();
				if(list != null && list.size() > 0){
					//保存或修改用户关联机构
					for(UserOffice userOffice : list){
						if(!template.exists("userOfficeInfo:"+userOffice.getId())){
							tx.rpush("userOffices", userOffice.getId());//存放ID列表,追加ID
						}
						tx.set("userOfficeInfo:"+userOffice.getId(), g.toJson(userOffice));
					}
				}
			}else if(event.getRedisEnum()==RedisEnum.delete){
				tx.del("officeInfo:"+office.getId());
				tx.lrem("offices:", 0, office.getId());
				List<UserOffice> list = office.getUserOfficeList();
				for(UserOffice userOffice : list){
					tx.del("userOfficeInfo:"+userOffice.getId());
					tx.lrem("userOffices", 0, userOffice.getId());
				}
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
