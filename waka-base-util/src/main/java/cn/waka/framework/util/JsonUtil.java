package cn.waka.framework.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * json工具类(使用alibaba的fastjson) com.alibaba.fastjson.JSON;
 */
public class JsonUtil {

	/** 因为类必须为public，所以只能把构造函数给这样控制 */
	JsonUtil() {

	}

	public Map<String, Object> toMap(String json) {
		if (WakaUtils.string.isEmpty(json)) {
			return new HashMap<String, Object>();
		} else {
			return JSON.parseObject(json);
		}
	}

	public <T> T toObject(String json, Class<T> cls) {
		if (WakaUtils.string.isEmpty(json)) {
			return null;
		}
		return JSON.parseObject(json, cls);
	}

	public <T> List<T> toList(String json, Class<T> cls) {
		if (WakaUtils.string.isEmpty(json)) {
			return null;
		}
		return JSON.parseArray(json, cls);
	}

	/** 使用alibaba的fastjson */
	public String toJson(Object obj) {
		return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);// 这里加上这个参数，fastjson就不会生成带引用的json串，但是就要求对象里不能出现循环引用
	}

}
