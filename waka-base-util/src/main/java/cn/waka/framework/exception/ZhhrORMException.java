package cn.waka.framework.exception;

/**
 * ORM专用
 */
public class ZhhrORMException extends ZhhrException {

	private static final long serialVersionUID = 1L;

	public ZhhrORMException(String message) {
		super(message);
	}

	public ZhhrORMException(String message, Throwable cause) {
		super(message, cause);
	}

	public ZhhrORMException(Throwable cause) {
		super(cause);
	}

}
