/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ewide.platform.common.persistence.Page;
import net.ewide.platform.common.service.CrudService;
import net.ewide.platform.modules.enums.RedisEnum;
import net.ewide.platform.modules.syndata.event.AreaDataEvent;
import net.ewide.platform.modules.syndata.event.PositionDataEvent;
import net.ewide.platform.modules.syndata.event.SubsystemDataEvent;
import net.ewide.platform.modules.sys.entity.Subsystem;
import net.ewide.platform.modules.sys.dao.SubsystemDao;

/**
 * 子系统管理Service
 * @author wangtao
 * @version 2016-04-07
 */
@Service
@Transactional(readOnly = true)
public class SubsystemService extends CrudService<SubsystemDao, Subsystem> {

	public Subsystem get(String id) {
		return super.get(id);
	}
	
	public List<Subsystem> findList(Subsystem subsystem) {
		return super.findList(subsystem);
	}
	
	public Integer findNameOrCode(Subsystem subsystem){
		return dao.findNameOrCode(subsystem);
	}
	
	public Page<Subsystem> findPage(Page<Subsystem> page, Subsystem subsystem) {
		subsystem.setPage(page);
		page.setList(dao.findList(subsystem));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(Subsystem subsystem) {
		super.save(subsystem);
		SubsystemDataEvent event = new SubsystemDataEvent(subsystem,RedisEnum.save);
		publisher.publishEvent(event);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		String[] ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			Subsystem subsystem = new Subsystem();
			subsystem.setId(ids[i]);
		    super.delete(subsystem);
		    SubsystemDataEvent event = new SubsystemDataEvent(subsystem,RedisEnum.delete);
			publisher.publishEvent(event);
		}
	}
	
	
}