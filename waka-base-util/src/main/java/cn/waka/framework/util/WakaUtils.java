package cn.waka.framework.util;

import java.util.UUID;

/**
 * 总的工具类
 * 
 * @author Administrator
 * 
 */
public final class WakaUtils {

	/** 集合工具类 */
	public final static CollectionUtil collection = new CollectionUtil();

	/** IO工具类 */
	public final static IOUtil io = new IOUtil();

	/** json工具类 */
	public final static JsonUtil json = new JsonUtil();

	/** 数字型工具类 */
	public final static NumberUtil number = new NumberUtil();
	
	/** 数据类工具 */
	public final static ArrayUtil array = new ArrayUtil();

	/** 操作系统工具类 */
	public final static OSUtil os = new OSUtil();

	/** 字符串工具类 */
	public final static StringUtil string = new StringUtil();

	/** 线程工具类 */
	public final static ThreadUtil thread = new ThreadUtil();

	/** 二维码工具类 */
	public final static QRCodeUtil qrCode = new QRCodeUtil();


	// ===========上面是不依赖别人的工具类，下面是一级依赖的工具类=============

	/** Map工具类 */
	public final static MapUtil map = new MapUtil();

	/** MD5工具类 */
	public final static MD5Util md5 = new MD5Util();

	/** 编码工具类 */
	public final static EncodeUtil encode = new EncodeUtil();

	/** 文件环境工具类 */
	public final static FileEnvUtil env = new FileEnvUtil();

	/** 文件工具类 */
	public final static FileUtil file = new FileUtil();

	/** 图片工具类 */
	public final static ImageUtil image = new ImageUtil();

	/** 日期工具类 */
	public final static DateUtil date = new DateUtil();

	/** 对象拷贝工具类 */
	public final static BeanUtil bean = new BeanUtil();

	/** 拼音工具类 */
	public final static PinyinUtil pinyin = new PinyinUtil();

	/** HTTP工具类 */
	public final static HttpUtil http = new HttpUtil();

	/** XML工具类 */
	public final static XMLUtil xml = new XMLUtil();

	/** 金额工具类 */
	public final static MoneyUtil money = new MoneyUtil();

	// ===========下面是二级依赖的工具类=============

	/** 管理config.properties文件工具类 */
	//不再通过这种方式来管理，通过value注入的方式注入到java bean里面
//	public final static ConfigUtil config = new ConfigUtil();

	// ===========只有一个方法的工具类就直接把方法放出来=============

	/** 生成UUID */
	public final static String uuid() {
		return UUID.randomUUID().toString();
	}

}
