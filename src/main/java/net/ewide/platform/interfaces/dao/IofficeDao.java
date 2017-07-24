package net.ewide.platform.interfaces.dao;

import java.util.List;

import net.ewide.platform.common.persistence.CrudDao;
import net.ewide.platform.common.persistence.annotation.MyBatisDao;
import net.ewide.platform.interfaces.dao.OfficeVo;

@MyBatisDao
public interface IofficeDao{
	List<OfficeVo> findList(String id);
}
