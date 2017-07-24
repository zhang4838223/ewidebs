package net.ewide.platform.interfaces.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.ewide.platform.interfaces.dao.OfficeVo;
import net.ewide.platform.interfaces.utils.Node;
import net.ewide.platform.interfaces.utils.VirtualDataGenerator;
import net.ewide.platform.interfaces.vo.ErrorCodeEnum;
import net.ewide.platform.interfaces.vo.ResponseVo;
import net.ewide.platform.modules.redis.RedisClientTemplate;

/**
 * @author wanghaozhe
 *
 */
@Component
public class QueryOfficesService {
	protected Logger logger = Logger.getLogger(QueryOfficesService.class);
	
	@Autowired
	RedisClientTemplate redisClientTemplate;
	@Autowired
	VirtualDataGenerator virtualDataGenerator;
	/**
	 * @param id
	 * @return
	 */
	public ResponseVo handleData(String id,String syscode) {
		ResponseVo responseVo = new ResponseVo();
		//todo
		if (!redisClientTemplate.exists("subsystemSystemCode:" + syscode)) {
			responseVo.setErrcode(ErrorCodeEnum.SYSCODE_NOT_EXIST.getCode());
			responseVo.setErrmsg(ErrorCodeEnum.SYSCODE_NOT_EXIST.getExplain());
		}	
		// 读取层次数据结果集列表
		List dataList = virtualDataGenerator.getVirtualResult(id);
		// 节点列表（散列表，用于临时存储节点对象）
		HashMap nodeList = new HashMap();
		// 根节点
		Node root = null;
		// 根据结果集构造节点列表（存入散列表）
		for (Iterator it = dataList.iterator(); it.hasNext();) {
			OfficeVo officeVo = (OfficeVo) it.next();
			Node node = new Node();
			node.setId((String) officeVo.getId());
			node.setName((String) officeVo.getText());
			node.setParentId((String) officeVo.getParentId());
			nodeList.put(node.getId(), node);
		}
		// 构造无序的多叉树
		Set entrySet = nodeList.entrySet();
		for (Iterator it = entrySet.iterator(); it.hasNext();) {
			Node node = (Node) ((Map.Entry) it.next()).getValue();
			if (node.getParentId() == null || node.getParentId().equals("") || node.getParentId().equals("0")) {
				root = node;
			} else {
				((Node) nodeList.get(node.getParentId())).addChild(node);
			}
		}
		// 对多叉树进行横向排序
		root.sortChildren();
		
		responseVo.setData(root);
		responseVo.setErrcode(ErrorCodeEnum.SUCCESS.getCode());
		responseVo.setErrmsg(ErrorCodeEnum.SUCCESS.getExplain());
		return responseVo;
	}
}
