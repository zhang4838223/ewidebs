/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.gen.service;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ewide.platform.common.persistence.Page;
import net.ewide.platform.common.service.BaseService;
import net.ewide.platform.common.utils.StringUtils;
import net.ewide.platform.modules.gen.dao.GenTemplateDao;
import net.ewide.platform.modules.gen.entity.GenTemplate;

/**
 * 代码模板Service
 * @author ThinkGem
 * @version 2013-10-15
 */
@Service
@Transactional(readOnly = true)
public class GenTemplateService extends BaseService {

	@Autowired
	private GenTemplateDao genTemplateDao;
	
	public GenTemplate get(String id) {
		return genTemplateDao.get(id);
	}
	
	public Page<GenTemplate> find(Page<GenTemplate> page, GenTemplate genTemplate) {
		genTemplate.setPage(page);
		page.setList(genTemplateDao.findList(genTemplate));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(GenTemplate genTemplate) {
		if (genTemplate.getContent()!=null){
			genTemplate.setContent(StringEscapeUtils.unescapeHtml4(genTemplate.getContent()));
		}
		if (StringUtils.isBlank(genTemplate.getId())){
			genTemplate.preInsert();
			genTemplateDao.insert(genTemplate);
		}else{
			genTemplate.preUpdate();
			genTemplateDao.update(genTemplate);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(GenTemplate genTemplate) {
		genTemplateDao.delete(genTemplate);
	}
	
}
