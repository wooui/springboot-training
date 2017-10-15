package cn.waka.framework.exception;

import cn.waka.framework.enumeration.result.BaseResultEnum;

/**
 * 工具类专用
 */
public class ZhhrUtilException extends ZhhrException {

	private static final long serialVersionUID = 1L;

	public ZhhrUtilException(BaseResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.setCode(resultEnum.getCode());
    }
	
	//TODO 暂时保留这个构造函数，后面有空要去掉，全部改用ResultEnum
	public ZhhrUtilException(String message) {
		super(message);
	}

	public ZhhrUtilException(String message, Throwable cause) {
		super(message, cause);
	}

	public ZhhrUtilException(Throwable cause) {
		super(cause);
	}

}
