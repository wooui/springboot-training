package cn.waka.framework.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * Map工具类
 * 
 * @author Administrator
 * 
 */
public class MapUtil {

	/** 因为类必须为public，所以只能把构造函数给这样控制 */
	MapUtil() {

	}

	public boolean isEmpty(Map<?, ?> ars) {
		return ars == null || ars.isEmpty();
	}

	public boolean isNotEmpty(Map<?, ?> ars) {
		return !isEmpty(ars);
	}

	/**
	 * 构造map
	 * 
	 * @param kvs
	 *            传入的数组按照键值对来插入map
	 * @return
	 */
	public <T> Map<T, Object> createMap(Object... kvs) {
		Map<T, Object> map = new HashMap<>();
		if (WakaUtils.collection.isNotEmpty(kvs)) {
			for (int i = 0; i < kvs.length; i++) {
				Object k = kvs[i];
				i++;
				Object v = kvs.length > i ? kvs[i] : null;
				map.put((T) k, v);
			}
		}
		return map;
	}

}
