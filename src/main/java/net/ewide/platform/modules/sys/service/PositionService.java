package net.ewide.platform.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ewide.platform.common.persistence.Page;
import net.ewide.platform.common.service.CrudService;
import net.ewide.platform.common.utils.StringUtils;
import net.ewide.platform.modules.enums.RedisEnum;
import net.ewide.platform.modules.syndata.event.PositionDataEvent;
import net.ewide.platform.modules.syndata.event.SubsystemDataEvent;
import net.ewide.platform.modules.syndata.event.UserDataEvent;
import net.ewide.platform.modules.sys.dao.PositionDao;
import net.ewide.platform.modules.sys.entity.Position;
import net.ewide.platform.modules.sys.entity.Subsystem;

/**
 * 职位管理类
 * 
 * @author Jacky
 * @version 2016-3-29
 */

@Service
@Transactional(readOnly = true)
public class PositionService extends CrudService<PositionDao, Position> {


	public Position getPosition(String id) {
		return dao.get(id);
	}

	public Page<Position> findPosition(Page<Position> page, Position position) {		
		position.setPage(page);
		page.setList(dao.findList(position));
		return page;
	}
	public Integer findNameOrCode(Position position) {		
		return dao.findNameOrCode(position);
	}

	@Transactional(readOnly = false)
	public void savePosition(Position position) {
		if (StringUtils.isBlank(position.getId())) {
			position.preInsert();
			dao.insert(position);
		} else {
			// Position oldPosition = positionDao.get(position.getId());
			position.preUpdate();
			dao.update(position);
		}
		PositionDataEvent event = new PositionDataEvent(position,RedisEnum.save);
		publisher.publishEvent(event);
	}

	@Transactional(readOnly = false)
	public void deletePosition(String id) {
		String[] ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			Position position = new Position();
			position.setId(ids[i]);
			dao.delete(position);
			PositionDataEvent event = new PositionDataEvent(position,RedisEnum.delete);
			publisher.publishEvent(event);
		}
	}
	
	/**
	 * 根据机构ID查询职位
	 * @param companyId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Position> findPositionById(Position positions){
		return dao.findPositionById(positions);
	}

}
