package mongodbtest;

import java.util.Date;

import net.ewide.platform.common.logdata.model.Log;
import net.ewide.platform.common.logdata.mongodb.DataLogUtils;
import net.ewide.platform.common.logdata.mongodb.MongoUtil;

import org.junit.Test;

import com.google.gson.Gson;

public class Logtest {
	DataLogUtils log = new DataLogUtils();

	@Test
	public void info() throws IllegalArgumentException, IllegalAccessException {
		Log o = new Log();
		o.setSysCode("Sw");
		o.setMenuId("1");
		o.setTaker("wangtao");
		o.setTakerDate(new Date());
		o.setExplain("测试");
		o.setType("1");
		o.setData("无");
		o.setNewData("有");
		o.setObjectPk("11");
		log.mongoDbLog(o);
	}
	@Test
	public void queryAll() {
		Log o = new Log();
		o.setSysCode("Sy");
		o.setObjectPk("11");
		log.queryAll(o);
	}
	
	@Test
	public void getAllDocuments() throws IllegalArgumentException, IllegalAccessException {
		Log o = new Log();
		o.setSysCode("S");
		o.setObjectPk("11");
		
		String s=MongoUtil.getAllDocuments(o);
		
		
	}
	 
}
