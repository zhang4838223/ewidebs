package net.ewide.platform.modules.sys.service;

import net.ewide.platform.common.service.CrudService;
import net.ewide.platform.common.utils.StringUtils;
import net.ewide.platform.modules.enums.RedisEnum;
import net.ewide.platform.modules.syndata.event.PositionDataEvent;
import net.ewide.platform.modules.syndata.event.UserGroupDataEvent;
import net.ewide.platform.modules.sys.dao.UserGroupDao;
import net.ewide.platform.modules.sys.entity.UserGroup;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户组Service
 * @author TianChong
 * @version 2016年3月29日
 */
@Service
@Transactional(readOnly = true)
public class UserGroupService extends CrudService<UserGroupDao, UserGroup> {

	
	@Transactional(readOnly = false)
	public void insertUserorRole(UserGroup userGroup) {
		if (userGroup.getIsNewRecord()) {
			userGroup.preInsert();
			dao.insert(userGroup);
		} else {
			userGroup.preUpdate();
			dao.update(userGroup);
		}
		if (StringUtils.isNotBlank(userGroup.getId())) {
				// 更新用户组与角色关联
				dao.deleteUserGroupRole(userGroup);
				// 更新用户组与用户关联
				dao.deleteUserGroupUser(userGroup);
				if (userGroup.getRoleList() != null && userGroup.getRoleList().size() > 0) {
					dao.insertUserGroupRole(userGroup);
				} 
				if (userGroup.getUserList() != null && userGroup.getUserList().size() > 0) {
					dao.insertUserGroupUser(userGroup);
			}
		}
		UserGroupDataEvent event = new UserGroupDataEvent(userGroup,RedisEnum.save);
		publisher.publishEvent(event);
	}
	@Transactional(readOnly = false)
	public void deleteUserorRole(String ids) {
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			UserGroup userGroup = new UserGroup();
			userGroup.setId(id[i]);
			delete(userGroup);
			dao.deleteUserGroupUser(userGroup);
			dao.deleteUserGroupRole(userGroup);
			UserGroupDataEvent event = new UserGroupDataEvent(userGroup,RedisEnum.save);
			publisher.publishEvent(event);
		}
	}
	/**
	 * 判断组名或者组编号是否唯一
	 * @param usergroup
	 * @return
	 */
	public Integer findNameOrCode(UserGroup usergroup){
		return dao.findNameOrCode(usergroup);
	}
}
