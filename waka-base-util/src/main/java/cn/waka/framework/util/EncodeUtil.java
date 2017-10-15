package cn.waka.framework.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import cn.waka.framework.constants.I18NConstants;
import cn.waka.framework.exception.ZhhrUtilException;

/**
 * 编码工具类
 * 
 * @author Administrator
 * 
 */
public class EncodeUtil {

	public final static String ISO8859 = "ISO-8859-1";

	/** 因为类必须为public，所以只能把构造函数给这样控制 */
	EncodeUtil() {

	}

	/**
	 * ISO-8859-1转换为GBK
	 * 
	 * @param s
	 *            以ISO-8859-1编码的字符串
	 * @return 以GBK编码的字符串
	 */
	public String iso2gbk(String s) {
		return convertEncoding(s, "GBK");
	}

	/* 转换函数 */
	private String convertEncoding(String s, String encode) {
		if (s == null || s.length() == 0)
			return "";
		boolean isISO = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c > 127 && c < 255) {
				isISO = true;
				break;
			}
		}
		if (!isISO)
			return s;

		try {
			byte[] bytes = s.getBytes(ISO8859);
			return new String(bytes, encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return s;
		}
	}

	/** 解码:将 BASE64 编码的字符串 s 进行解码 :utf-8 */
	public String base64Encode(String s) {
		return base64Encode(s, Charset.forName(I18NConstants.DEFAULT_CHARSET));
	}

	/** 加密:将 s 进行 BASE64 编码 :GBK */
	@SuppressWarnings("restriction")
	public String base64Encode(String s, Charset charset) {
		if (s == null || s.length() == 0)
			return "";
		byte[] bys = s.getBytes(Charset.forName("GBK"));
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		String rs = encoder.encode(bys);
		return rs;
	}

	/** 解码:将 BASE64 编码的字符串 s 进行解码 :GBK */
	public String base64Decoder(String s) {
		return base64Decoder(s, Charset.forName("GBK"));
	}

	/** 解码:将 BASE64 编码的字符串 s 进行解码 :GBK */
	@SuppressWarnings("restriction")
	public String base64Decoder(String s, Charset charset) {
		try {
			if (s == null || s.length() == 0)
				return "";
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			byte[] bys = decoder.decodeBuffer(s);
			String rs = new String(bys, charset);
			return rs;
		} catch (IOException e) {
			throw new RuntimeException("BASE64解码出错:" + e.getMessage());
		}
	}

	/**
	 * URL编码
	 * 
	 * @param s
	 * @return
	 */
	public String urlEncode(String s) {
		if (WakaUtils.string.isEmpty(s)) {
			return "";
		}
		try {
			return URLEncoder.encode(s, I18NConstants.DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw new ZhhrUtilException(e.getMessage(), e);
		}
	}

	/**
	 * URL编码
	 * 
	 * @param s
	 * @return
	 */
	public String urlDecode(String s) {
		if (WakaUtils.string.isEmpty(s)) {
			return "";
		}
		try {
			return URLDecoder.decode(s, I18NConstants.DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw new ZhhrUtilException(e.getMessage(), e);
		}
	}

}
