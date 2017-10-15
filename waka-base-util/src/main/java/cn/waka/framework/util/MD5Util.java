package cn.waka.framework.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cn.waka.framework.exception.ZhhrUtilException;


/**
 * @author Administrator
 * 
 */
public class MD5Util {

	/** 因为类必须为public，所以只能把构造函数给这样控制 */
	MD5Util() {

	}

	private final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * SHA1加密
	 * 
	 * @param str
	 * @return
	 */
	public String sha1(String str) {
		if (str == null) {
			return null;
		}
		return getFormattedText(str.getBytes(), "SHA1");
	}

	/**
	 * MD5加密
	 * 
	 * @param str
	 * @return
	 */
	public String md5(String str) {
		if (str == null) {
			return null;
		}
		return getFormattedText(str.getBytes(), "MD5");
	}

	/**
	 * MD5加密
	 * 
	 * @param str
	 * @return
	 */
	public String md5(byte[] data) {
		if (data == null) {
			return null;
		}
		return getFormattedText(data, "MD5");
	}

	private String getFormattedText(byte[] data, String algorithm) {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new ZhhrUtilException(e);
		}
		messageDigest.update(data);
		byte[] bytes = messageDigest.digest();

		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString().toUpperCase();
	}

}
