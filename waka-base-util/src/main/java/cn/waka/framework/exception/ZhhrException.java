package cn.waka.framework.exception;

import cn.waka.framework.enumeration.result.BaseResultEnum;

/**
 * 所有的异常都继承这个
 * 
 * Created by chenjs
 *
 */
public class ZhhrException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Integer code;

	public ZhhrException(BaseResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
	
	public ZhhrException(String message){
		super(message);
	}

    public ZhhrException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZhhrException(Throwable cause) {
        super(cause);
    }

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
    

}
