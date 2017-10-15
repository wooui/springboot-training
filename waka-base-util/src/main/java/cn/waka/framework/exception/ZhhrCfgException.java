package cn.waka.framework.exception;

import cn.waka.framework.enumeration.result.BaseResultEnum;

/**
 * 配置类专用
 */
public class ZhhrCfgException extends ZhhrException {

	private static final long serialVersionUID = 1L;

	public ZhhrCfgException(BaseResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.setCode(resultEnum.getCode());
    }
	
	//TODO 暂时保留这个构造函数，后面有空要去掉，全部改用ResultEnum
	public ZhhrCfgException(String message) {
		super(message);
	}

	public ZhhrCfgException(String message, Throwable cause) {
		super(message, cause);
	}

	public ZhhrCfgException(Throwable cause) {
		super(cause);
	}

}
