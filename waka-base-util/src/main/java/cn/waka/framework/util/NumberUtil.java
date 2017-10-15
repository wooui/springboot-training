package cn.waka.framework.util;

import java.math.BigDecimal;
import java.util.Random;

import cn.waka.framework.exception.ZhhrUtilException;


/**
 * 数字型的工具类
 * 
 * @author Administrator
 * 
 */
public class NumberUtil {

	private final int INTEGER = 1, FLOAT = 2;

	/** 因为类必须为public，所以只能把构造函数给这样控制 */
	NumberUtil() {

	}

	public boolean isFloat(Object val) {
		return isNumeric(val) == FLOAT;
	}

	public boolean isInt(Object val) {
		return isNumeric(val) == INTEGER;
	}

	/* 判断是否数字 */
	private int isNumeric(Object val) {
		if (val == null) {
			return -1;
		}
		String s = val.toString().trim();
		int size = s.length();
		if (size == 0) {
			return -1;
		}

		int result = 0;
		boolean doc = false;// 小数点
		for (int i = 0; i < s.length(); i++) {
			char x = s.charAt(i);

			// 负号
			if (i > 0 && (x == '-' || x == '+')) {
				result = -1;
				break;
			}

			// 不能出现两个小数点
			if (x == '.') {
				if (doc) {
					result = -1;
					break;
				} else
					doc = true;
			}

			if (x != '.' && x != '+' & x != '-' && (x < '0' || x > '9')) {
				result = -1;
				break;
			}
		}
		if (doc && result == 0) {
			return FLOAT;// 浮点
		} else if (result == 0) {
			return INTEGER;// 整型
		} else {
			return result;
		}
	}

	/**
	 * 对象转为Long
	 * 
	 * @param obj
	 * @return
	 */
	public Long toLong(Object obj) {
		if (obj == null) {
			return null;
		} else if (obj instanceof Boolean) {
			return ((Boolean) obj) ? 1L : 0L;
		} else if (obj instanceof Number) {
			return ((Number) obj).longValue();
		} else if (obj instanceof String) {
			String s = ((String) obj).trim();
			return Long.parseLong(s);
		} else {
			return null;
		}
	}

	/**
	 * 对象转为Integer
	 * 
	 * @param obj
	 * @return
	 */
	public Integer toInt(Object obj) {
		if (obj == null) {
			return null;
		} else if (obj instanceof Boolean) {
			return ((Boolean) obj) ? 1 : 0;
		} else if (obj instanceof Number) {
			return ((Number) obj).intValue();
		} else if (obj instanceof String) {
			String s = ((String) obj).trim();
			return Integer.parseInt(s);
		} else {
			return null;
		}
	}

	/**
	 * 判断为空或为0
	 * 
	 * @param number
	 * @return
	 */
	public boolean isEmpty(Number number) {
		return number == null || number.intValue() == 0;
	}

	/**
	 * 判断不为空或不为0
	 * 
	 * @param number
	 * @return
	 */
	public boolean isNotEmpty(Number number) {
		return !isEmpty(number);
	}

	/** 所有元素都不为空时，返回true，只要有一个为空，则是false，与isAllEmpty()反操作 */
	public boolean isAnyEmpty(Number... arys) {
		if (arys == null || arys.length == 0) {
			return true;
		}

		for (Number s : arys) {
			if (this.isEmpty(s)) {
				return true;
			}
		}
		return false;
	}

	/** 所有元素都为空时，返回true，只要有一个不为空，则是false,与isAnyEmpty()反操作 */
	public boolean isAllEmpty(Number... arys) {
		if (arys == null || arys.length == 0) {
			return true;
		}

		for (Number s : arys) {
			if (!this.isEmpty(s)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 四舍五入，保留指定的小数点
	 * 
	 * @param number
	 * @param dot
	 *            需要保留的小数位
	 * @return
	 */
	public Double roundNumber(Number number, int dot) {
		if (number == null) {
			return null;
		}
		BigDecimal bd = null;
		if (number instanceof Double) {
			bd = new BigDecimal(number.doubleValue());
		} else if (number instanceof Float) {
			bd = new BigDecimal(number.floatValue());
		} else if (number instanceof Long) {
			bd = new BigDecimal(number.longValue());
		} else if (number instanceof Short) {
			bd = new BigDecimal(number.shortValue());
		} else {
			bd = new BigDecimal(number.intValue());
		}
		return bd.setScale(dot, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 返回数字的绝对值
	 * 
	 * @param number
	 * @return
	 */
	public Number abs(Number number) {
		if (number == null) {
			return null;
		} else if (number instanceof Integer) {
			return Math.abs(number.intValue());
		} else if (number instanceof Long) {
			return Math.abs(number.longValue());
		} else if (number instanceof Float) {
			return Math.abs(number.floatValue());
		} else if (number instanceof Double) {
			return Math.abs(number.doubleValue());
		} else {
			throw new ZhhrUtilException("不支持的数据格式");
		}
	}

	/**
	 * 页码转为SQL中的limit的值
	 * 
	 * @param pageNum
	 *            页码
	 * @param pageNum
	 *            每页数据条数
	 * @return
	 */
	public Integer pagenum2offset(Integer pageNum, Integer pageSize) {
		if (isEmpty(pageNum) || isEmpty(pageSize)) {
			return null;
		} else {
			return (pageNum - 1) * pageSize;
		}
	}

	/**
	 * 生成一个指定位数的随机数
	 * 
	 * @param size
	 *            位数(如果是1000以内的随机数，则位数为3)
	 * @return
	 */
	public int random(int size) {
		if (size <= 0) {
			throw new ZhhrUtilException("不支持生成" + size + "位数的随机数");
		}
		return new Random().nextInt(10 * size);
	}

	/**
	 * 生成一个指定位数的随机数，不足位数前面补0
	 * 
	 * @param size
	 *            位数(如果是1000以内的随机数，则位数为3)
	 * @return
	 */
	public String randomStr(int size) {
		int num = this.random(size);
		return String.format("%0" + size + "d", num);
	}

}
