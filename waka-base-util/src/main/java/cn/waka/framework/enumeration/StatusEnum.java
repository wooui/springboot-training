package cn.waka.framework.enumeration;

/**
 * 状态枚举类(各项目尽量在这上面扩展)
 * 
 * @author lujx
 *
 */
public enum StatusEnum {

	/** 待审核 */
	auditing("待审核", 1),

	/** 审核不通过 */
	auditFail("审核不通过", 4),

	/** 启用 */
	enable("启用", 8),

	/** 禁用 */
	disable("禁用", 2);

	private StatusEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}

	private final String name;

	private final int value;

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	/**
	 * 根据整型获取状态
	 * 
	 * @param value
	 * @return
	 */
	public static StatusEnum getStatus(int value) {
		for (StatusEnum e : StatusEnum.values()) {
			if (e.value == value) {
				return e;
			}
		}
		return null;
	}

}
