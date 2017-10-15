package cn.waka.framework.enumeration;

/**
 * 性别的枚举类
 * 
 * @author k2
 *
 */
public enum Sex {

	male(1, "男"), female(2, "女");

	private Sex(int value, String name) {
		this.value = value;
		this.name = name;
	}

	private final int value;

	private final String name;

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	/**
	 * 是否男性
	 * 
	 * @return
	 */
	public boolean isMale() {
		return this.equals(Sex.male);
	}

	/**
	 * 是否女性
	 * 
	 * @return
	 */
	public boolean isFemale() {
		return this.equals(Sex.female);
	}

	/**
	 * 根据整型获取性别
	 * 
	 * @param value
	 * @return
	 */
	public static Sex getSex(int value) {
		for (Sex sex : Sex.values()) {
			if (sex.value == value) {
				return sex;
			}
		}
		return null;
	}

	/**
	 * 根据布尔型获取性别
	 * 
	 * @param value
	 * @return
	 */
	public static Sex getSex(boolean isMale) {
		return isMale ? Sex.male : Sex.female;
	}

}
