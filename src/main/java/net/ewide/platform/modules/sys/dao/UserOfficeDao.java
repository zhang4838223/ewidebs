package net.ewide.platform.modules.sys.dao;

import java.util.List;

import net.ewide.platform.common.persistence.CrudDao;
import net.ewide.platform.common.persistence.annotation.MyBatisDao;
import net.ewide.platform.modules.sys.entity.UserOffice;

/**
 * 用户机构DAO接口
 * @author TianChong
 * @version 2016年3月29日
 */
@MyBatisDao
public interface UserOfficeDao  extends CrudDao<UserOffice>{
	public List<UserOffice> findUserOffices(String id);
	
	public List<UserOffice> findUserOfficesByOfficeId(String id);
	
	public void deleteByOfficeId(String id);
}
