package cn.waka.framework.enumeration.result;

public enum BaseResultEnum {
	UNKOWN_ERROR(-1, "未知错误"),
	SUCCESS(0,"成功"),
	MAX_100(100,"最大值不能超过100"),
	;
	private Integer code;
	private String message;
	BaseResultEnum(Integer code, String message){
		this.code = code;
		this.message = message;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
