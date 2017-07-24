/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import net.ewide.platform.common.persistence.Page;
import net.ewide.platform.common.service.BaseService;
import net.ewide.platform.common.service.TreeService;
import net.ewide.platform.common.utils.StringUtils;
import net.ewide.platform.modules.enums.RedisEnum;
import net.ewide.platform.modules.syndata.event.OfficeDataEvent;
import net.ewide.platform.modules.sys.dao.OfficeDao;
import net.ewide.platform.modules.sys.dao.UserOfficeDao;
import net.ewide.platform.modules.sys.entity.Office;
import net.ewide.platform.modules.sys.entity.Role;
import net.ewide.platform.modules.sys.entity.User;
import net.ewide.platform.modules.sys.entity.UserOffice;
import net.ewide.platform.modules.sys.utils.UserUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 机构Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {
	@Autowired
	private UserOfficeDao userOfficeDao;
	
	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public Page<Office> findList(Page<Office> page, Office office){
		office.setPage(page);
		User user = UserUtils.getUser();
		office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
		office.setParentIds(office.getParentIds()+"%");
		page.setList(dao.findByParentIdsLike(office));
		return page;
	}
	
	@Transactional(readOnly = true)
	public List<Office> findListByParentId(String parentId){
		return dao.findListByParentId(parentId);
	}
	
	@Transactional(readOnly = true)
	public List<Office> findChildList(String parentId){
		Office office = new Office();
		office.getSqlMap().put("dsf", BaseService.dataScopeFilter(UserUtils.getUser(), "a", ""));
		office.setParentIds(parentId);
		return dao.findChildList(office);
	}
	@Transactional(readOnly = true)
	public List<Office> findOfficeList(String id){
		return dao.findOfficeList(id);
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		//如果机构主键层级码修改，需修改用户机构关联表中的层级码
		String primaryLevelCode=office.getPrimaryLevelCode();
		if(!StringUtils.isBlank(primaryLevelCode)){
			String newPrimaryLevelCode=primaryLevelCode.substring(0,primaryLevelCode.lastIndexOf("."))+"."+(office.getType().equals("1")?"org":"dpt");
			office.setPrimaryLevelCode(newPrimaryLevelCode);
			List<UserOffice> list = userOfficeDao.findUserOfficesByOfficeId(office.getId());
			List<UserOffice> list1 = new ArrayList<UserOffice>();
			if(list!=null && list.size()>0){
				for (UserOffice userOffice : list) {
					String oldPrimaryLevelCode=userOffice.getPrimaryLevelCode();
					userOffice.setPrimaryLevelCode(oldPrimaryLevelCode.replace(primaryLevelCode, newPrimaryLevelCode));
					userOfficeDao.update(userOffice);
					list1.add(userOffice);
				}
				office.setUserOfficeList(list1);
			}
		}
		super.save(office);
		OfficeDataEvent event = new OfficeDataEvent(office,RedisEnum.save);
		publisher.publishEvent(event);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
//		userOfficeDao.deleteByOfficeId(office.getId());
		List<UserOffice> list = userOfficeDao.findUserOfficesByOfficeId(office.getId());
		office.setUserOfficeList(list);
		OfficeDataEvent event = new OfficeDataEvent(office,RedisEnum.delete);
		publisher.publishEvent(event);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = true)
	public Office getParent(String id){
		return  dao.getParent(id);
	}
	
	@Transactional(readOnly = true)
	public Integer checkOfficeName(String officeName,String parentId){
		return dao.checkOfficeName(officeName,parentId);
	}
	
	/**
	 * 查询是否有根节点
	 * @return
	 */
	public Integer findParentId() {
		return dao.findParentId();
	}
	
}
