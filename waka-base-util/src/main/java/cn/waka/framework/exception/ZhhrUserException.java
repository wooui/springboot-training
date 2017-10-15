package cn.waka.framework.exception;

/**
 * 后台用户及相关模块专用
 */
public class ZhhrUserException extends ZhhrException {

	private static final long serialVersionUID = 1L;

	public ZhhrUserException(String message) {
		super(message);
	}

	public ZhhrUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public ZhhrUserException(Throwable cause) {
		super(cause);
	}

}
