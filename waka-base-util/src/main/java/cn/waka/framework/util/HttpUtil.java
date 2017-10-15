package cn.waka.framework.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.logging.Logger;

import cn.waka.framework.exception.ZhhrUtilException;

/**
 * 发送HTTP请求的工具类
 * 
 * @author k2
 *
 */
public class HttpUtil {

	/** 因为类必须为public，所以只能把构造函数给这样控制 */
	HttpUtil() {

	}

	public String get(String url) {
		return get(url, "");
	}

	public String get(String url, Map<?, ?> params) {
		String p = WakaUtils.string.mapToUrl(params);
		return get(url, p);
	}

	public String get(String url, String params) {
		String result = "";
		try {
			if (WakaUtils.string.isNotEmpty(params)) {
				url += '?' + params;
			}
//			URL realUrl = new URL(url + '?' + params); //这样会导致很多连接以？结尾
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			// connection.setRequestProperty("Content-Type",
			// "text/plain;charset=utf-8");
			// connection.setRequestProperty("user-agent", "Mozilla/4.0
			// (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			// Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			// for (String key : map.keySet()) {
			// System.out.println(key + "--->" + map.get(key));
			// }
			// 定义 BufferedReader输入流来读取URL的响应
			try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			}
		} catch (Exception e) {
			throw new ZhhrUtilException("发送GET请求出现异常！原因：" + e.getMessage(), e);
		}
		return result;
	}

	public String post(String url) {
		return post(url, "");
	}

	public String post(String url, Map<?, ?> params) {
		String p = WakaUtils.string.mapToUrl(params);
		return post(url, p);
	}

	public String post(String url, String params) {
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			// conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;
			// MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			// 获取URLConnection对象对应的输出流
			try (PrintWriter out = new PrintWriter(conn.getOutputStream())) {
				// 发送请求参数
				out.print(params);
				// flush输出流的缓冲
				out.flush();
			}

			// 定义BufferedReader输入流来读取URL的响应
			try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			}
		} catch (Exception e) {
			throw new ZhhrUtilException("发送POST请求出现异常！原因：" + e.getMessage());
		}
		return result;
	}

	/*public static void main(String[] ar) throws Exception {
		String url = "http://222.73.117.158/msg/HttpBatchSendSM?account=%s&pswd=%s&mobile=%s&msg=%s&needstatus=false";
		url = String.format(url, "jialicm", "Jialicm001", "13417715300", "【佳丽】您的注册验证码是：1610");
		String rs = new HttpUtil().get(url);

		System.out.println(rs);
	}*/

}
