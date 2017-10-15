package cn.waka.framework.enumeration;

/**
 * 下载时使用的枚举类
 * 
 * @author k2
 *
 */
public enum ContentType {

	/** 文本 */
	TEXT("text/plain"),

	/** 图片 */
	IMAGE("image/jpeg"),

	/** 图片 */
	PDF("application/pdf"),

	/** 二进制数据 */
	STREAM("application/octet-stream"),

	/** 母鸡 */
	DOWNLOAD("application/x-msdownload");

	private String value;

	private ContentType(String type) {
		value = type;
	}

	public String getValue() {
		return value;
	}

}