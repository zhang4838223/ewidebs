package net.ewide.platform.interfaces.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;

import net.ewide.platform.common.utils.StringUtils;
import net.ewide.platform.interfaces.service.LoginInterfaceService;
import net.ewide.platform.interfaces.service.QueryObjectService;
import net.ewide.platform.interfaces.service.QueryOfficesService;
import net.ewide.platform.interfaces.service.QueryUserService;
import net.ewide.platform.interfaces.vo.ErrorCodeEnum;
import net.ewide.platform.interfaces.vo.ResponseVo;
import net.ewide.platform.joggle.PermissionInterfaces;

/**
 * @author wanghaozhe 接口入口类
 */
@Controller
public class Interfaces implements PermissionInterfaces{
	protected Logger logger =  Logger.getLogger(Interfaces.class);

	@Autowired
	private LoginInterfaceService loginService;
	@Autowired
	private QueryObjectService queryObjectService;
	@Autowired
	private QueryUserService queryUserService;
	@Autowired
	private QueryOfficesService queryOfficesService;

	/**
	 * 登录验证接口
	 * @return String 返回json格式 {"errcode":xx, "errmsg":"xxx", "data": {
	 *         "userinfo_list":{…}, "department_list":{…}, "role_list":{…},
	 *         "user_group_list":{…}, "authorization_list":{…} }}
	 */
	@GET
	@Path("/login/{syscode}/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@PathParam("username")String username,@PathParam("syscode")String syscode) {
		logger.info("登录验证接口开始，username:"+username+",syscode:"+syscode);
		ResponseVo responseVo = new ResponseVo();
		Gson gson = new Gson();
		if (StringUtils.isBlank(username)||StringUtils.isBlank(syscode)) {
			responseVo.setErrcode(ErrorCodeEnum.PARAM_NULL_FAIL.getCode());
			responseVo.setErrmsg(ErrorCodeEnum.PARAM_NULL_FAIL.getExplain());
			logger.info("登录验证接口返回response:" + gson.toJson(responseVo));
			return gson.toJson(responseVo);
		}
		responseVo = loginService.handleData(username,syscode);
		return gson.toJson(responseVo);
	}

	/**通过id获取组织架构
	 * @param id
	 * @param syscode
	 * @return
	 */
	@GET
	@Path("/queryOfficesById/{syscode}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String queryOfficesById(@PathParam("id") String id,@PathParam("syscode")String syscode) {
		// String ids = request.getParameter("id");
		ResponseVo responseVo = new ResponseVo();
		Gson gson = new Gson();
		logger.info("通过机构id查询机构接口开始，id:" +id+",syscode:"+syscode);
		if (StringUtils.isBlank(id)||StringUtils.isBlank(syscode)) {
			responseVo.setErrcode(ErrorCodeEnum.PARAM_NULL_FAIL.getCode());
			responseVo.setErrmsg(ErrorCodeEnum.PARAM_NULL_FAIL.getExplain());
			logger.info("通过id获取组织架构response:" + gson.toJson(responseVo));
			return gson.toJson(responseVo);
		}
		responseVo = queryOfficesService.handleData(id,syscode);
		return gson.toJson(responseVo);
	}

	/**关联查询对象接口
	 * @param ids
	 * @param syscode
	 * @param type
	 * @return
	 */
	@GET
	@Path("/queryObjectById/{syscode}/{ids}/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public String queryObjectById(@PathParam("ids")String ids,@PathParam("syscode")String syscode,@PathParam("type")int type) {
		logger.info("关联查询对象接口开始,id:" + ids+",type:"+type+",syscode:"+syscode);
		ResponseVo responseVo = new ResponseVo();
		Gson gson = new Gson();
		if(StringUtils.isBlank(ids)||StringUtils.isBlank(syscode)){
			responseVo.setErrcode(ErrorCodeEnum.PARAM_NULL_FAIL.getCode());
			responseVo.setErrmsg(ErrorCodeEnum.PARAM_NULL_FAIL.getExplain());
			logger.info("关联查询对象接口response:" + gson.toJson(responseVo));
			return gson.toJson(responseVo);
		}
		responseVo = queryObjectService.handleData(ids,syscode,type);
		return gson.toJson(responseVo);
	}

	/** 通过角色查询用户 参数 角色code 返回值 属于角色的用户list
	 * @param id
	 * @param syscode
	 * @return
	 */
	@GET
	@Path("/queryUserByRoleId/{syscode}/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String queryUserByRoleId(@PathParam("id") String id, @PathParam("syscode") String syscode) {
		logger.info("通过角色查询用户接口开始,id:"+id+",syscode:"+syscode);
		ResponseVo responseVo = new ResponseVo();
		Gson gson = new Gson();
		if(StringUtils.isBlank(id)||StringUtils.isBlank(syscode)){
			responseVo.setErrcode(ErrorCodeEnum.PARAM_NULL_FAIL.getCode());
			responseVo.setErrmsg(ErrorCodeEnum.PARAM_NULL_FAIL.getExplain());
			logger.info("通过角色查询用户response:" + gson.toJson(responseVo));
			return gson.toJson(responseVo);
		}
		responseVo = queryUserService.handleData(id, syscode);
		return gson.toJson(responseVo);
	}


}
