/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ewide.platform.common.service.CrudService;
import net.ewide.platform.modules.sys.entity.Attachment;
import net.ewide.platform.modules.sys.dao.AttachmentDao;

/**
 * 附件文件Service
 * @author Tianchong
 * @version 2016-05-20
 */
@Service
@Transactional(readOnly = true)
public class AttachmentService extends CrudService<AttachmentDao, Attachment> {
	@Autowired
	private AttachmentDao attachmentDao;
	public int addAttachment(Attachment attachment){
		attachment.setIsNewRecord(true);
		attachment.preInsert();
		return attachmentDao.insert(attachment);
	}
	
	/**
	 * 根据ID查询文件列表
	 * @param ids
	 * @return
	 */
	public List<Attachment> findListByIds(String[] ids){
		return attachmentDao.findListByIds(ids);
	}
}