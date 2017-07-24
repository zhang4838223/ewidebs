package net.ewide.platform.interfaces.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import net.ewide.platform.interfaces.vo.ErrorCodeEnum;
import net.ewide.platform.interfaces.vo.ResponseVo;
import net.ewide.platform.interfaces.vo.UserByRoleData;
import net.ewide.platform.modules.redis.RedisClientTemplate;

/**
 * @author wanghaozhe
 *
 */
@Component
public class QueryUserService {
	protected Logger logger = Logger.getLogger(QueryUserService.class);
	@Autowired
	RedisClientTemplate redisClientTemplate;
	@Autowired
	GetDataService dataservice;

	public ResponseVo handleData(String id, String syscode) {
		ResponseVo responseVo = new ResponseVo();
		Gson gson = new Gson();
		// syscode检查 redis里查找syscode，检查是否是正确的
		if (!redisClientTemplate.exists("subsystemSystemCode:" + syscode)) {
			responseVo.setErrcode(ErrorCodeEnum.SYSCODE_NOT_EXIST.getCode());
			responseVo.setErrmsg(ErrorCodeEnum.SYSCODE_NOT_EXIST.getExplain());
		} else {
			UserByRoleData data = new UserByRoleData();
			data.setUser_list(dataservice.iRoleIdAllUser(id));
			responseVo.setData(data);
			responseVo.setErrcode(ErrorCodeEnum.SUCCESS.getCode());
			responseVo.setErrmsg(ErrorCodeEnum.SUCCESS.getExplain());
		}
		logger.info("角色查询用户接口返回response:" + gson.toJson(responseVo));
		return responseVo;
	}
}
