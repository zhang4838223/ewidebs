package net.ewide.platform.modules.syndata.listeners;

import net.ewide.platform.common.service.ServiceException;
import net.ewide.platform.modules.enums.RedisEnum;
import net.ewide.platform.modules.redis.RedisClientTemplate;
import net.ewide.platform.modules.syndata.event.SubsystemDataEvent;
import net.ewide.platform.modules.sys.entity.Subsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import com.google.gson.Gson;

/**
 * 子系统监听
 * 
 * @author wangtao
 * @version 2016年4月20日
 */
@Component
public class SubsystemDataListener implements
		ApplicationListener<SubsystemDataEvent> {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RedisClientTemplate template;

	@Override
	public void onApplicationEvent(SubsystemDataEvent event) {
		Subsystem subsystem = (Subsystem) event.getSource();
		Gson g = new Gson();
		if (!(event instanceof SubsystemDataEvent)) {
			return;
		}
		Jedis jedis = null;
		try {
			jedis = template.getJedis();
			Transaction tx = jedis.multi();
			if (event.getRedisEnum() == RedisEnum.save) {
				if (!template.exists("subsystemInfo:" + subsystem.getId())) {
					tx.rpush("subsystems", subsystem.getId());// 存放ID列表,追加ID
				}
				tx.set("subsystemInfo:" + subsystem.getId(),
						g.toJson(subsystem));
			} else if (event.getRedisEnum() == RedisEnum.delete) {
				tx.del("subsystemInfo:" + subsystem.getId());
				tx.lrem("subsystems", 0, subsystem.getId());
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
