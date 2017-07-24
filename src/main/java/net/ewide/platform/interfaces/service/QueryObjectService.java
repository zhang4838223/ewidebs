package net.ewide.platform.interfaces.service;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

import net.ewide.platform.interfaces.vo.ErrorCodeEnum;
import net.ewide.platform.interfaces.vo.Office;
import net.ewide.platform.interfaces.vo.OfficeData;
import net.ewide.platform.interfaces.vo.ResponseVo;
import net.ewide.platform.interfaces.vo.Role;
import net.ewide.platform.interfaces.vo.RoleData;
import net.ewide.platform.interfaces.vo.User;
import net.ewide.platform.interfaces.vo.UserData;
import net.ewide.platform.interfaces.vo.UserGroup;
import net.ewide.platform.interfaces.vo.UserGroupData;
import net.ewide.platform.modules.redis.RedisClientTemplate;

/**
 * @author wanghaozhe
 *
 */
@Component
public class QueryObjectService {
	protected Logger logger = Logger.getLogger(QueryObjectService.class);

	@Autowired
	RedisClientTemplate redisClientTemplate;
	@Autowired
	GetDataService dataservice;

	/**
	 * 判断处理请求数据
	 * 
	 * @param requestVo
	 * @return
	 */
	public ResponseVo handleData(String ids, String syscode, int type) {
		ResponseVo responseVo = new ResponseVo();
		Gson gson = new Gson();
		// syscode检查 redis里查找syscode，检查是否是正确的
		if (!redisClientTemplate.exists("subsystemSystemCode:" + syscode)) {
			responseVo.setErrcode(ErrorCodeEnum.SYSCODE_NOT_EXIST.getCode());
			responseVo.setErrmsg(ErrorCodeEnum.SYSCODE_NOT_EXIST.getExplain());
			logger.info("登录验证接口返回response:" + gson.toJson(responseVo));
			return responseVo;
		}
		executeBus(ids, type, responseVo);// 校验通过
		responseVo.setErrcode(ErrorCodeEnum.SUCCESS.getCode());
		responseVo.setErrmsg(ErrorCodeEnum.SUCCESS.getExplain());
		logger.info("登录验证接口返回response:" + gson.toJson(responseVo));
		return responseVo;
	}

	/** 执行业务方法
	 * @param ids
	 * @param type 1:用户；2:部门/机构；3:角色；4:用户组
	 * @param responseVo
	 */
	public void executeBus(String ids, int type, ResponseVo responseVo) {
		String[] idArray = ids.split(",");
		List list = Lists.newArrayList();
		for (String id : idArray) {
			if (type == 1) {
				User user = dataservice.iUser(redisClientTemplate.get("userInfo:" + id));
				UserData userData = new UserData();
				if(user != null){
					list.add(user);
				}
				userData.setUser(list);
				responseVo.setData(userData);
			} else if (type == 2) {
				Office office = dataservice.iOffice(redisClientTemplate.get("officeInfo:" + id));
				OfficeData officeData = new OfficeData();
				if(office != null){
					list.add(office);
				}
				officeData.setOffice(list);
				responseVo.setData(officeData);
			} else if (type == 3) {
				Role role = dataservice.iRole(redisClientTemplate.get("roleInfo:" + id));
				RoleData roleData = new RoleData();
				if(role != null){
					list.add(role);
				}
				roleData.setRole(list);
				responseVo.setData(roleData);
			} else if (type == 4) {
				UserGroup userGroup = dataservice.iUserGroup(redisClientTemplate.get("userGroupInfo:" + id));
				UserGroupData userGroupData = new UserGroupData();
				if (userGroup != null) {
					list.add(userGroup);
				}
				userGroupData.setUserGroup(list);
				responseVo.setData(userGroupData);
			} else {
				responseVo.setData(null);
			}
		}
	}
}
