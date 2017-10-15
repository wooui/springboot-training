package cn.waka.framework.enumeration;

/**
 * 布尔类型的字段统一使用的枚举类
 * 
 * @author k2
 *
 */
public enum BooleanEnum {

	trueValue(1, "是"), falseValue(-1, "否");

	private final int value;

	private final String name;

	private BooleanEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public boolean getBoolean() {
		return value == trueValue.value;
	}

	public static BooleanEnum getBooleanEnum(int value) {
		return value == 1 ? trueValue : falseValue;
	}

	public static BooleanEnum getBooleanEnum(boolean value) {
		return value ? trueValue : falseValue;
	}

}
