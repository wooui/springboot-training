package cn.waka.framework.enumeration;

/**
 * 删除状态位
 * 
 * @author k2
 *
 */
public enum DeleteStatus {

	/** 已删除 */
	deleted(-1),

	/** 未删除 */
	unDelete(1);

	private int value;

	private DeleteStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static DeleteStatus getMode(int value) {
		for (DeleteStatus mode : DeleteStatus.values()) {
			if (mode.value == value) {
				return mode;
			}
		}
		return null;
	}

}
