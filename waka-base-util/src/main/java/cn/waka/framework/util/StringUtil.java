package cn.waka.framework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * 字符串工具类
 * 
 * @author Administrator
 * 
 */
public class StringUtil {

	/** 因为类必须为public，所以只能把构造函数给这样控制 */
	StringUtil() {

	}

	public boolean isEmpty(String s) {
		if (s == null || s.length() == 0 || s.trim().length() == 0) {
			return true;
		}
		return false;
	}

	/** 所有元素都不为空时，返回true，只要有一个为空，则是false，与isAllEmpty()反操作 */
	public boolean isAnyEmpty(String... arys) {
		if (arys == null || arys.length == 0) {
			return true;
		}

		for (String s : arys) {
			if (this.isEmpty(s)) {
				return true;
			}
		}
		return false;
	}

	/** 所有元素都为空时，返回true，只要有一个不为空，则是false,与isAnyEmpty()反操作 */
	public boolean isAllEmpty(String... arys) {
		if (arys == null || arys.length == 0) {
			return true;
		}

		for (String s : arys) {
			if (!this.isEmpty(s)) {
				return false;
			}
		}
		return true;
	}

	/** 所有元素都为空时，返回true，只要有一个不为空，则是false */
	public boolean isNotEmpty(String arys) {
		return !isEmpty(arys);
	}

	/**
	 * 把【result=0&message=短信发送成功&smsid=20150603124701591】这种格式的字符串转为Map
	 * 
	 * @param url
	 * @return
	 */
	public Map<String, String> urlToMap(String url) {
		Map<String, String> map = new HashMap<>();
		for (String str : url.split("&")) {
			String[] kvs = str.split("=");
			map.put(kvs[0], kvs[1]);
		}
		return map;
	}

	/**
	 * 把【result=0&message=短信发送成功&smsid=20150603124701591】这种格式的字符串转为对象
	 * 
	 * @param url
	 * @param cls
	 * @return
	 */
	public <T> T urlToObject(String url, Class<T> cls) {
		Map<String, String> map = this.urlToMap(url);
		return WakaUtils.bean.convertObject(map, cls);
	}

	/**
	 * 首字母大写
	 * 
	 * @param s
	 * @return
	 */
	public String firstUpper(String s) {
		if (isEmpty(s)) {
			return s;
		} else {
			return s.substring(0, 1).toUpperCase() + s.substring(1);
		}
	}

	/**
	 * 替换字符串(与jdk的区别是不使用正则表达式)
	 * 
	 * @param expression
	 *            需要替换的字符串
	 * @param find
	 *            需要被替换的串
	 * @param replace
	 *            用来替换的串
	 * @return 替换后的串
	 */
	public String replace(String expression, String find, String replace) {
		StringBuffer sb = new StringBuffer();
		String temp = "";

		temp = expression;
		while (temp.indexOf(find) >= 0) {
			sb.append(temp.substring(0, temp.indexOf(find)) + replace);
			temp = temp.substring(temp.indexOf(find) + find.length());
		}
		sb.append(temp);

		return sb.toString();
	}

	/**
	 * 按指定字符进行split，返回长整型的集合
	 * 
	 * @param str
	 * @param separator
	 *            分隔符
	 * @return
	 */
	public List<Long> splitToLongList(String str, char separator) {
		return this.split(str, separator, Convertors.string2long);
	}

	/**
	 * 按指定字符进行split，返回字符串的集合
	 * 
	 * @param str
	 * @param separator
	 *            分隔符
	 * @return
	 */
	public List<String> splitToStrList(String str, char separator) {
		return this.split(str, separator, null);
	}

	private <T> List<T> split(String str, char separator, TypeConvertor<T> convertor) {
		if (isEmpty(str)) {
			return null;
		}

		String[] strs = str.split(String.valueOf(separator));
		if (strs == null || strs.length == 0) {
			return null;
		}
		List<T> list = new ArrayList<T>(strs.length);
		for (String tmp : strs) {
			if (convertor != null) {
				T t = convertor.convert(tmp);
				if (t != null) {
					list.add(t);
				}
			} else {
				list.add((T) tmp);
			}
		}
		return list;
	}

	/**
	 * 将{a:1,b:'55'}转为a=1&b=55
	 * 
	 * @param map
	 * @return
	 */
	public String mapToUrl(Map<?, ?> map) {
		if (WakaUtils.map.isEmpty(map)) {
			return "";
		}
		StringBuilder url = new StringBuilder(map.size() * 100);
		for (Entry<?, ?> entry : map.entrySet()) {
			if (url.length() == 0) {
				url.append(entry.getKey()).append('=').append(entry.getValue());
			} else {
				url.append('&').append(entry.getKey()).append('=').append(entry.getValue());
			}
		}
		return url.toString();
	}

	private final String allChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";// 数字小写字母和大写字母

	/**
	 * 生成指定长度的随机字符串(包含数字和小写字母，可以包含大写字母)
	 * 
	 * @param size
	 *            字符串长度
	 * @param includeUpperCase
	 *            是否包含大写字母
	 * @return
	 */
	public String random(int size, boolean includeUpperCase) {
		StringBuilder rs = new StringBuilder(size);
		int bound = includeUpperCase ? 62 : 36;// 包含大写就有62种可能
		for (int i = 0; i < size; i++) {
			int num = new Random().nextInt(bound);
			rs.append(allChars.charAt(num));
		}
		return rs.toString();
	}

}
