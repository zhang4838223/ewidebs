package net.ewide.platform.modules.sys.dao;

import java.util.List;

import net.ewide.platform.common.persistence.CrudDao;
import net.ewide.platform.common.persistence.annotation.MyBatisDao;
import net.ewide.platform.modules.sys.entity.Position;

/**
 * 职位DAO接口
 * @author Jacky
 * @version 2016-03-29
 */
@MyBatisDao
public interface PositionDao extends CrudDao<Position>{
	
	/**
	 * 判断name或者no唯一
	 * @param position
	 * @return
	 */
	public Integer findNameOrCode(Position position);	
	
	/**
	 * 根据机构ID查询职位
	 * @param companyId
	 * @return
	 */
	public List<Position> findPositionById(Position positions);
}
