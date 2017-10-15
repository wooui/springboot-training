package cn.waka.framework.exception;

/**
 * 权限专用
 */
public class ZhhrRoleException extends ZhhrException {

	private static final long serialVersionUID = 1L;

	public ZhhrRoleException(String message) {
		super(message);
	}

	public ZhhrRoleException(String message, Throwable cause) {
		super(message, cause);
	}

	public ZhhrRoleException(Throwable cause) {
		super(cause);
	}

}
