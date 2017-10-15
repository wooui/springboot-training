package cn.waka.framework.util;

/**
 * 线程工具类
 * 
 * @author Administrator
 * 
 */
public class ThreadUtil {

	/** 因为类必须为public，所以只能把构造函数给这样控制 */
	ThreadUtil() {

	}

	/**
	 * 功能: 等待一段时间
	 * 
	 * @author 2007-1-5 21:11:29
	 * @param time
	 */
	public void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
