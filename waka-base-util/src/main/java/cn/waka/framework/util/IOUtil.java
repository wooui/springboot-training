package cn.waka.framework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.waka.framework.exception.ZhhrUtilException;


/**
 * IO工具类
 * 
 * @author Administrator
 * 
 */
public class IOUtil {

	/** 因为类必须为public，所以只能把构造函数给这样控制 */
	IOUtil() {

	}

	public byte[] is2bytes(InputStream uploadedInputStream) {
		try (ByteArrayOutputStream output = new ByteArrayOutputStream(1024)) {
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				output.write(bytes, 0, read);
			}
			return output.toByteArray();
		} catch (IOException e) {
			throw new ZhhrUtilException(e.getMessage(), e);
		}
	}

	public InputStream bytes2is(byte[] content) {
		return new ByteArrayInputStream(content);
	}

}
