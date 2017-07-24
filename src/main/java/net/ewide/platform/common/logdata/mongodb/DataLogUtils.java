package net.ewide.platform.common.logdata.mongodb;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import net.ewide.platform.common.logdata.model.Log;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class DataLogUtils {
	private static Logger logs = Logger.getLogger(DataLogUtils.class);
	private static final String COLLECTION_NAME = "datalogs";
	private static Mongo m = null;
	private static DB db = null;

	/**
	 * 将日志对象添加到MongoDB数据库中
	 * @param bean
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void mongoDbLog(Log bean) throws IllegalArgumentException, IllegalAccessException {
		Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String json = g.toJson(bean);
		Map<String,Object> map = new HashMap<String, Object>();
		map = (HashMap<String, Object>)g.fromJson(json, HashMap.class);
		BasicDBObject basic = new BasicDBObject();
		basic.putAll(map);
		logs.info(basic);
		System.out.println("日志添加成功");
	}
	/**
	 * 获取mongodb数据库连接
	 */
	public void startMongoDBConn() {
		try {
			// Mongo(p1, p2):p1=>IP地址 p2=>端口
			m = new Mongo("127.0.0.1", 27017);
			// 选择数据库，如果没有这个数据库的话，会自动建立
			db = m.getDB("mongodb");
			System.out.println("连接MongoDB数据库,校验成功！");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 关闭mongodb数据库连接
	 */
	public void stopMondoDBConn() {
		if (null != m) {
			if (null != db) {
				// 结束Mongo数据库的事务请求
				try {
					db.requestDone();
					System.out.println("关闭MongoDB数据库连接成功！");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				m.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			m = null;
			db = null;
		}
	}
	/**
	 * 模糊查询（参数：系统code、操作对象主键）
	 */
	public void queryAll(Log log) {
		startMongoDBConn();
		Pattern p=null;
		BasicDBObject query = new BasicDBObject();
		if(log!=null && log.getSysCode()!=null && !log.getSysCode().equals(""))
		{
			p=Pattern.compile("^.*" + log.getSysCode()+ ".*$" , Pattern.CASE_INSENSITIVE);
			query.put("message.sysCode",p );
		}else if(log!=null && log.getObjectPk()!=null && !log.getObjectPk().equals("")){
			p = Pattern.compile("^.*" + log.getObjectPk() + ".*$", Pattern.CASE_INSENSITIVE);
			query.put("message.objectPk", p);
		}
		// 建立一个集合，和数据库一样，如果没有，会自动建立
		DBCollection dbCol = db.getCollection(COLLECTION_NAME);
		DBCursor ret = dbCol.find(query);
		
		while (ret.hasNext()) {
			BasicDBObject bdbObj = (BasicDBObject) ret.next();
			System.out.println(bdbObj);
		}
		stopMondoDBConn();
	}
}
