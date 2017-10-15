package cn.waka.framework.exception;

public class ZhhrWeixinException extends ZhhrException {

	private static final long serialVersionUID = 1L;

	public ZhhrWeixinException(String message) {
		super(message);
	}

	public ZhhrWeixinException(String message, Throwable cause) {
		super(message, cause);
	}

	public ZhhrWeixinException(Throwable cause) {
		super(cause);
	}

}
