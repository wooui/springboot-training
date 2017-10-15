package cn.waka.framework.util;

import java.io.File;

import cn.waka.framework.exception.ZhhrUtilException;


/**
 * 文件环境工具类
 * 
 * @author Administrator
 * 
 */
public class FileEnvUtil {

	/** 因为类必须为public，所以只能把构造函数给这样控制 */
	FileEnvUtil() {

	}

	/** 应用服务器的根目录 */
	private String rootPath;

	/** 是否UI应用，与rootPath配合使用 */
	private boolean isGUI;

	/**
	 * 读应用服务器的根目录路径,带盘符的<br>
	 * 如是是GUI程序(appliction):目录为类的根目录 如是是Web程序:目录为发布目录（webRoot）,最后一字符是"/"
	 * 
	 * @param classpath
	 *            web类路径(true),取类路径(false)
	 */
	public String getRootPath() {
		if (rootPath == null) {
			throw new ZhhrUtilException("请使用主类初始化：getRootPath(boolean,class)根目录");
		}
		return rootPath;
	}

	/**
	 * web系统在CWListener implements ServletContextListener初始,swing在application中初始
	 */
	public String getRootPath(boolean webPath, Class<?> clz) {
		String showMessage = "根目录已初始化getRootPath(boolean,class)";
		try {
			if (rootPath == null) {
				showMessage = "ROOT第一次初始化";
				String path = getRootPath(clz);
				if (webPath && !isGUI)
					rootPath = path + "WEB-INF/classes/";
				else
					rootPath = path;
			}
			return rootPath;
		} finally {
			// YMException e = new YMException(showMessage);
			StackTraceElement[] es = Thread.currentThread().getStackTrace();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 3 && i < es.length; i++) {
				sb.append(es[i].getFileName()).append('(').append(es[i].getLineNumber()).append(")\t>").append(es[i].getClassName()).append('.')
						.append(es[i].getMethodName()).append("()");
				if (i == 0) {
					sb.append(':').append(showMessage);
				}
				sb.append('\n');
			}
			System.out.println(sb.toString());
		}
	}

	/**
	 * 取类路径
	 * 
	 * @return
	 */
	private synchronized String getRootPath(Class<?> clz) {
		String p = clz.getProtectionDomain().getCodeSource().getLocation().getFile();
		p = p.replace('\\', '/');
		// p = p.toLowerCase();
		// web应用的class文件方式
		int i = p.toUpperCase().indexOf("/WEB-INF/");
		if (i > 0) {
			isGUI = false;
			rootPath = p.substring(0, i + 1);
			File f = new File(rootPath);
			if (!f.exists())
				f.mkdirs();
		} else {
			// 桌面GUI方式
			isGUI = true;
			i = p.lastIndexOf("/");
			if (i > 0 && i + 1 < p.length())
				rootPath = p.substring(0, i + 1);
			else
				rootPath = p;
		}
		/*
		 * System.out.println("=================================");
		 * System.out.println("类路径:" + p); System.out.println("根目录路径:" +
		 * rootPath); System.out.println("isGUI = " + isGUI);
		 * System.out.println("getRootPath(true) = " + getRootPath(true));
		 * System.out.println("=================================");
		 */
		return rootPath;
	}

}
