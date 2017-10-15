package cn.waka.framework.util;

/**
 * 配置文件的工具类
 * 
 * @author k2
 *
 */
public class ConfigUtil {

	/** 因为类必须为public，所以只能把构造函数给这样控制 */
	ConfigUtil() {

	}

	/** 系统用的配置文件 */
	public final ConfigItem system = new ConfigItem("/conf/config.properties");

	/** 中文的配置文件 */
	// public final ConfigItem cn = new ConfigItem("/lang/cn.properties");

	/** 俄文的配置文件 */
	// public final ConfigItem ru = new ConfigItem("/lang/ru.properties");

	/** 英文的配置文件 */
	// public final ConfigItem en = new ConfigItem("/lang/en.properties");

}
