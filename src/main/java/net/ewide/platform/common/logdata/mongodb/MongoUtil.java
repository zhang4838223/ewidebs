package net.ewide.platform.common.logdata.mongodb;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.ewide.platform.common.logdata.model.Log;
import net.ewide.platform.common.utils.PropertiesLoader;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

/**
 * 日志管理
 * @author wangtao
 * @version 2016-3-29 17:13:48
 */
@Component
public class MongoUtil {
	private static Logger logs = Logger.getLogger(MongoUtil.class);
	private static PropertiesLoader loader = new PropertiesLoader("ewidebs.properties");
	private static Mongo mongo;
	private static DBCollection coll;
	private static DB db;
	static {
		try {
			MongoOptions options = new MongoOptions();
			// 控制系统在发生连接错误时是否重试 ，默认为false --boolean
			options.autoConnectRetry = true;
			// 每个主机允许的连接数（每个主机的连接池大小），当连接池被用光时，会被阻塞住 ，默认为10 --int
			options.connectionsPerHost = 1000;
			// 被阻塞线程从连接池获取连接的最长等待时间（ms） --int
			options.maxWaitTime = 5000;
			// 套接字超时时间;该值会被传递给Socket.setSoTimeout(int)。默认为0（无限） --int
			options.socketTimeout = 0;
			// 连接超时，推荐>3000毫秒
			options.connectTimeout = 15000;
			// 线程队列数，如果连接线程排满了队列就会抛出“Out of semaphores to get db”错误。
			options.threadsAllowedToBlockForConnectionMultiplier = 5000;
			// 事实上，Mongo实例代表了一个数据库连接池，即使在多线程的环境中，一个Mongo实例对我们来说已经足够了
			Integer port = Integer.parseInt(loader.getProperty("mongodb.port"));
			mongo = new Mongo(new ServerAddress(loader.getProperty("mongodb.server"), port), options);
			// 注意Mongo已经实现了连接池，并且是线程安全的。
			// 大部分用户使用mongodb都在安全内网下，但如果将mongodb设为安全验证模式，就需要在客户端提供用户名和密码：
			// boolean auth = db.authenticate(myUserName, myPassword);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public static DB getDB() {
		if (db == null) {
			db = mongo.getDB(loader.getProperty("mongodb.DB"));
		}
		return db;
	}
	public static DBCollection getColl(String collname) {
		return getDB().getCollection(collname);
	}
	/**
	 * 模糊查询（参数：系统code、操作对象主键）
	 * @param log
	 * @return json类型字符串
	 */
	public static String getAllDocuments(Log log) {
		Pattern p = null;
		BasicDBObject query = new BasicDBObject();
		if (log != null && log.getSysCode() != null && !log.getSysCode().equals("")) {
			p = Pattern.compile("^.*" + log.getSysCode() + ".*$", Pattern.CASE_INSENSITIVE);
			query.put("message.sysCode", p);
		} else if (log != null && log.getObjectPk() != null && !log.getObjectPk().equals("")) {
			p = Pattern.compile("^.*" + log.getObjectPk() + ".*$", Pattern.CASE_INSENSITIVE);
			query.put("message.objectPk", p);
		}
		DBCursor cursor = null;
		try {
			// 建立一个集合，和数据库一样，如果没有，会自动建立
			cursor = MongoUtil.getColl(loader.getProperty("mongodb.collections")).find(query);
			List<DBObject> list = cursor.toArray();
			Gson g = new Gson();
			return g.toJson(list);
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			cursor.close();
		}
		return null;
	}
	/**
	 * 将日志对象添加到MongoDB数据库中
	 * @param bean
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void mongoDbLog(Log bean) throws IllegalArgumentException, IllegalAccessException {
		Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String json = g.toJson(bean);
		Map<String, Object> map = new HashMap<String, Object>();
		map = (HashMap<String, Object>) g.fromJson(json, HashMap.class);
		BasicDBObject basic = new BasicDBObject();
		basic.putAll(map);
		logs.info(basic);
		System.out.println("日志添加成功");
	}
	
	@GET
	@Path("/service")
	@Produces(MediaType.TEXT_PLAIN)
	public static String service(){
		
		return "Hello world";
	}
}
