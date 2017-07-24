/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.dao;

import net.ewide.platform.common.persistence.CrudDao;
import net.ewide.platform.common.persistence.annotation.MyBatisDao;
import net.ewide.platform.modules.sys.entity.Subsystem;

/**
 * 子系统管理DAO接口
 * @author wangtao
 * @version 2016-04-07
 */
@MyBatisDao
public interface SubsystemDao extends CrudDao<Subsystem> {
	
	/**
	 * 判断name或者code是否唯一
	 * @param subsystem
	 * @return
	 */
	public Integer findNameOrCode(Subsystem subsystem);
	
	/**
	 * 通过code查询子系统（未使用）
	 * @param subsystem
	 * @return
	 */
	public Subsystem getSubsystem(Subsystem subsystem);
	
}