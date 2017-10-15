package cn.waka.framework.enumeration.result;

public enum UtilResultEnum {
	UNKOWN_ERROR(-1, "未知错误"),
	SUCCESS(0,"成功"),
	
	;
	private Integer code;
	private String message;
	UtilResultEnum(Integer code, String message){
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
