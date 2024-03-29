/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package net.ewide.platform.modules.sys.utils;

import java.util.List;
import java.util.Map;

import net.ewide.platform.common.mapper.JsonMapper;
import net.ewide.platform.common.utils.CacheUtils;
import net.ewide.platform.common.utils.SpringContextHolder;
import net.ewide.platform.modules.sys.dao.DictDao;
import net.ewide.platform.modules.sys.entity.Dict;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 字典工具类
 * 
 * @author ThinkGem
 * @version 2013-5-29
 */
public class DictUtils {

	private static DictDao dictsDao = SpringContextHolder
			.getBean(DictDao.class);

	public static final String CACHE_DICT_MAP = "dictMap";

	public static String getDictLabel(String value, String type,
			String defaultValue) {
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)) {
			List<Dict> dict = getDictList(type);
			for (int i = 0; i < dict.size(); i++) {
				if (value.equals(dict.get(i).getValue())) {
					return dict.get(i).getLabel();
				}
			}
		}
		return defaultValue;
	}

	public static String getDictLabels(String values, String type,
			String defaultValue) {
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)) {
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(values, ",")) {
				valueList.add(getDictLabel(value, type, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}

	public static String getDictValue(String label, String type,
			String defaultLabel) {
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)) {
			for (Dict dict : getDictList(type)) {
				if (label.equals(dict.getLabel())) {
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}

	public static List<Dict> getDictList(String type) {
		Dict dicts = new Dict();
		dicts.setValue(type);
		@SuppressWarnings("unchecked")
		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>) CacheUtils.get(CACHE_DICT_MAP);
		if (dictMap == null || dictMap.get(type) == null) {
			if (dictMap == null) {
				dictMap = Maps.newHashMap();
			}
			for (Dict dict : dictsDao.findTypeList(dicts)) {
				List<Dict> dictList = dictMap.get(type);
				if (dictList != null) {
					dictList.add(dict);
				} else {
					dictMap.put(type, Lists.newArrayList(dict));
				}
			}
			CacheUtils.put(CACHE_DICT_MAP, dictMap);
		}
		List<Dict> dictList = dictMap.get(type);
		if (dictList == null) {
			dictList = Lists.newArrayList();
		}
		return dictList;
	}

	/**
	 * 返回字典列表（JSON）
	 * 
	 * @param type
	 * @return
	 */
	public static String getDictListJson(String type) {
		return JsonMapper.toJsonString(getDictList(type));
	}

}
