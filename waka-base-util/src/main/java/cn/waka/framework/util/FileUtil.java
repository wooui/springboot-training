package cn.waka.framework.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import cn.waka.framework.exception.ZhhrUtilException;


/**
 * 文件的工具类
 * 
 * @author Administrator
 * 
 */
public class FileUtil {

	/** 因为类必须为public，所以只能把构造函数给这样控制 */
	FileUtil() {

	}

	/**
	 * 写txt文件,方法不检查路径是否存在，请在外部保证路径的完整
	 * 
	 * @author 纪其俊 2006-8-23 10:22:02
	 * @param file
	 *            完整路径及文件名
	 * @param text
	 *            文本
	 */
	public void fileWriteText(String file, String text) {
		fileWriteText(file, text, "gbk");
	}

	/**
	 * 写txt文件,方法不检查路径是否存在，请在外部保证路径的完整
	 * 
	 * @author 纪其俊 2006-8-23 10:22:02
	 * @param file
	 *            完整路径及文件名
	 * @param text
	 *            文本
	 */
	public void fileWriteText(String file, String text, String charset) {
		try (OutputStream out = new FileOutputStream(new File(file))) {
			byte[] b = text.getBytes(charset);
			out.write(b);
			out.flush();
		} catch (IOException e) {
			throw new ZhhrUtilException(e.getMessage(), e);
		}
	}

	/**
	 * 递归删除目录
	 * 
	 * @param directory
	 */
	public void deleteDirectory(File directory) {
		if (directory.isDirectory()) {
			File[] fs = directory.listFiles();
			for (int i = 0; i < fs.length; i++) {
				deleteDirectory(fs[i]);
			}
		}
		directory.delete();
	}

	/**
	 * 获取文件的后缀名
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 */
	public String getFileNameExt(String fileName) {
		int pos = fileName.lastIndexOf(".");
		if (pos > -1) {
			return fileName.substring(pos + 1);
		} else {
			return "";
		}
	}

}
