package net.ewide.platform.interfaces.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.ewide.platform.interfaces.dao.IofficeDao;
import net.ewide.platform.interfaces.dao.OfficeVo;

/**
 * @author wanghaozhe
 *
 */
@Component
public class VirtualDataGenerator {
	//@Autowired RedisClientTemplate redisClientTemplate;
	@Autowired IofficeDao iofficeDao;
	public  List  getVirtualResult(String id) {      
		  List<OfficeVo> list = iofficeDao.findList(id);
//		  dataRecord1.put("id", "112000");  
//		  dataRecord1.put("text", "易维银行解放道支行");  
//		  dataRecord1.put("parentId", "110000");  
		  return list;  
		 }  
}
