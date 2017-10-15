package cn.waka.framework.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.waka.framework.constants.TokenConstants;

/**
 * 集合工具类
 * 
 * @author Administrator
 * 
 */
public class CollectionUtil {

	/** 因为类必须为public，所以只能把构造函数给这样控制 */
	CollectionUtil() {

	}

	public boolean isEmpty(Collection<?> ars) {
		return ars == null || ars.isEmpty();
	}

	public boolean isNotEmpty(Collection<?> ars) {
		return !isEmpty(ars);
	}

	public boolean isEmpty(Object[] ars) {
		return ars == null || ars.length == 0;
	}

	public boolean isNotEmpty(Object[] ars) {
		return !isEmpty(ars);
	}

	public <T> List<T> arrayToList(T... array) {
		return Arrays.asList(array);
	}

	public <T> List<T> collectionToList(Collection<T> collection) {
		if (collection == null) {
			return null;
		} else if (collection instanceof List) {
			return (List<T>) collection;
		} else if (collection.isEmpty()) {
			return new ArrayList<>();
		} else {
			return new ArrayList<>(collection);
		}
	}

	public <T> T[] listToArray(List<T> list) {
		return (T[]) list.toArray();
	}

	public String join(Collection<?> collection, String separator) {
		if (isEmpty(collection)) {
			return "";
		} else if (collection.size() == 1) {
			Object obj = collection.iterator().next();
			return obj == null ? "" : obj.toString();
		} else {
			StringBuilder str = new StringBuilder(collection.size() * 50);
			for (Iterator<?> itr = collection.iterator(); itr.hasNext();) {
				Object obj = itr.next();
				if (obj != null) {
					if (itr.hasNext()) {
						str.append(obj.toString()).append(separator);
					} else {
						str.append(obj.toString());
					}
				}
			}
			return str.toString();
		}
	}

	public String join(Collection<?> collection, char separator) {
		if (isEmpty(collection)) {
			return "";
		} else if (collection.size() == 1) {
			Object obj = collection.iterator().next();
			return obj == null ? "" : obj.toString();
		} else {
			StringBuilder str = new StringBuilder(collection.size() * 50);
			for (Object obj : collection) {
				if (obj != null) {
					str.append(obj.toString()).append(separator);
				}
			}
			if (str.length() > 0) {
				return str.substring(0, str.length() - 1);
			}
			return "";
		}
	}


	public List<Long> getIdsFromMaps(Collection<Map<String, Object>> maps) {
		if (maps == null) {
			return null;
		}
		List<Long> ids = new ArrayList<>(maps.size());
		for (Map<String, Object> map : maps) {
			ids.add((Long) map.get(TokenConstants.KEY_ID));
		}
		return ids;
	}

	public <T> List<T> getKeysFromMaps(Collection<Map<?, T>> maps, Object key) {
		if (maps == null) {
			return null;
		}
		List<T> ids = new ArrayList<>(maps.size());
		for (Map<?, T> map : maps) {
			ids.add(map.get(key));
		}
		return ids;
	}


	public <T> T[] collectionToArray(Collection<T> collection) {
		return (T[]) collection.toArray();
	}

}
