package cn.waka.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import cn.waka.framework.exception.ZhhrException;


/**
 * 一个配置文件对应的对象
 * 
 * @author k2
 *
 */
public class ConfigItem {


	/**
	 * 取默认的配置文件
	 */
	ConfigItem(String file) {
		try (InputStream is = ConfigUtil.class.getResourceAsStream(file)) {
			Properties p = new Properties();
			p.load(is);
			this.pro = p;
		} catch (IOException e) {
			throw new ZhhrException("加载配置信息出错:" + e.getMessage());
		}
	}

	private final Properties pro;

	/**
	 * 取得配置值
	 * 
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		Object v = pro.get(key);
		return v == null ? null : v.toString();
	}

}
