package net.ewide.platform.interfaces.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import net.ewide.platform.interfaces.vo.ErrorCodeEnum;
import net.ewide.platform.interfaces.vo.ResponseVo;
import net.ewide.platform.modules.redis.RedisClientTemplate;

/**
 * @author wanghaozhe
 *
 */
@Component
public class LoginInterfaceService {
	protected Logger logger =  Logger.getLogger(LoginInterfaceService.class);

	@Autowired
	@Qualifier("concreteStrategyDataAll")
	BuildDataStrategy buildDataStrategy;
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
	public ResponseVo handleData(String username, String syscode) {
		ResponseVo responseVo = new ResponseVo();
		Gson gson = new Gson();
		// syscode检查 redis里查找syscode，检查是否是正确的
		if (!redisClientTemplate.exists("subsystemSystemCode:" + syscode)) {
			responseVo.setErrcode(ErrorCodeEnum.SYSCODE_NOT_EXIST.getCode());
			responseVo.setErrmsg(ErrorCodeEnum.SYSCODE_NOT_EXIST.getExplain());
			logger.info("登录验证接口返回response:" + gson.toJson(responseVo));
			return responseVo;
		}
		// 用户名检查 todo
		if (!redisClientTemplate.exists("userName:" + username)) {
			responseVo.setErrcode(ErrorCodeEnum.USERNAME_PASSWORD_ERROR.getCode());
			responseVo.setErrmsg(ErrorCodeEnum.USERNAME_PASSWORD_ERROR.getExplain());
			logger.info("登录验证接口返回response:" + gson.toJson(responseVo));
			return responseVo;
		}
//		//校验密码
//		User user = dataservice.iUserInfo(username);
//		String dataBasePassword = user.getPassword();
//		if(!password.equals(dataBasePassword)){
//			responseVo.setErrcode(ErrorCodeEnum.USERNAME_PASSWORD_ERROR.getCode());
//			responseVo.setErrmsg(ErrorCodeEnum.USERNAME_PASSWORD_ERROR.getExplain());
//			logger.info("登录验证接口返回response:" + gson.toJson(responseVo));
//			return responseVo;
//		}
		responseVo = buildDataStrategy.buildData(username,syscode);// 校验通过
		logger.info("登录验证接口返回response:" + gson.toJson(responseVo));
		return responseVo;
	}

}
