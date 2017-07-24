/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.dao;

import java.util.List;

import net.ewide.platform.common.persistence.CrudDao;
import net.ewide.platform.common.persistence.annotation.MyBatisDao;
import net.ewide.platform.modules.sys.entity.Attachment;

/**
 * 附件文件DAO接口
 * @author Tianchong
 * @version 2016-05-20
 */
@MyBatisDao
public interface AttachmentDao extends CrudDao<Attachment> {
	public List<Attachment> findListByIds(String[] ids);
}