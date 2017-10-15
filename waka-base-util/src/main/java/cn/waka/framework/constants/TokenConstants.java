package cn.waka.framework.constants;

public class TokenConstants {
	
	private TokenConstants(){
		
	}
	
	public final static String ADMIN = "zhhr_admin";
	public final static String COMPANY = "zhhr_company";
	public final static String PERSON = "zhhr_person";
	
	/** 主键的名称 */
	public static String KEY_ID = "id";

	/** dubbo中使用的系统code */
	//public static String SEQUENCE_CODE = WakaUtils.config.system.getValue("sequence.system.code");

	/** 重定向的key */
	public final static String REDIRECT_URL = "redirectUrl";

	/** 配置文件里，[生产模式]配置项的key */
	public final static String CONF_WEBX_PRODUCTIONMODE = "webx.productionMode";

	/** 配置文件里，[后台首页链接]配置项的key */
	public final static String CONF_URI_HOMELINK_MANAGER = "uri.homeLink.manager";

	/** 手机验证码的最大长度 */
	public final static int MOBILE_VALIDATE_CODE_LENGTH_MAX = 10;

}
