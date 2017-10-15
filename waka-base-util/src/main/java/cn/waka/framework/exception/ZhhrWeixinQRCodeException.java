package cn.waka.framework.exception;

public class ZhhrWeixinQRCodeException extends ZhhrWeixinException {

	private static final long serialVersionUID = 1L;

	public ZhhrWeixinQRCodeException(String message) {
		super(message);
	}

	public ZhhrWeixinQRCodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ZhhrWeixinQRCodeException(Throwable cause) {
		super(cause);
	}

}
