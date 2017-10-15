package cn.waka.framework.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * 金额专用的工具类
 */
public class MoneyUtil {

	private final MathContext mc = new MathContext(2, RoundingMode.HALF_DOWN);

	/** 因为类必须为public，所以只能把构造函数给这样控制 */
	MoneyUtil() {

	}

	/**
	 * 判断两个金额是否相等
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean equals(Double a, Double b) {
		if (a == null && b == null) {// 两个都为空
			return true;
		} else if (a == null || b == null) {// 只有一个为空
			return false;
		} else {// 两个都不为空
			return toBigDecimal(a).compareTo(toBigDecimal(b)) == 0;
		}
	}

	/**
	 * 输出金额的字符串格式
	 * 
	 * @param money
	 * @return
	 */
	public String toString(Double money) {
		return NumberFormat.getCurrencyInstance().format(money);
	}

	/**
	 * 将字符串转为Double
	 * 
	 * @param money
	 * @return
	 */
	public Double toDouble(String money) {
		if (WakaUtils.string.isEmpty(money)) {
			return 0d;
		} else {
			return toDouble(Double.parseDouble(money));// TODO:这里以后要考虑超出精度的情况
		}
	}

	/**
	 * 加法
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public Double add(Double a, Double b) {
		if (a == null || b == null) {// 有一个为空
			return null;
		} else {// 两个都不为空
			return toDouble(toBigDecimal(a).add(toBigDecimal(b)));
		}
	}

	/**
	 * 减法
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public Double sub(Double a, Double b) {
		if (a == null || b == null) {// 有一个为空
			return null;
		} else {// 两个都不为空
			return toDouble(toBigDecimal(a).subtract(toBigDecimal(b)));
		}
	}

	/* 转成BigDecimal时保留两位小数 */
	private BigDecimal toBigDecimal(double d) {
		BigDecimal b = new BigDecimal(d);
		b.setScale(2, BigDecimal.ROUND_HALF_UP);// 只保留两位小数，才去四舍五入的方式
		return b;
	}

	/* 转成double时保留两位小数 */
	private double toDouble(double d) {
		BigDecimal b = toBigDecimal(d);
		return b.doubleValue();
	}

	/* 转成double时保留两位小数 */
	private double toDouble(BigDecimal b) {
		b.setScale(2, BigDecimal.ROUND_HALF_UP);// 只保留两位小数，才去四舍五入的方式
		return b.doubleValue();
	}

	/**
	 * 乘法
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public Double multi(Double a, Double b) {
		if (a == null || b == null) {// 有一个为空
			return null;
		} else {// 两个都不为空
			return toDouble(toBigDecimal(a).multiply(toBigDecimal(b)));
		}
	}

	/**
	 * 除法
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public Double div(Double a, Double b) {
		if (a == null || b == null) {// 有一个为空
			return null;
		} else {// 两个都不为空
			return toDouble(toBigDecimal(a).divide(toBigDecimal(b), mc));
		}
	}

	/**
	 * 计算折扣，不带小数点
	 * 
	 * @return
	 */
	public int discount(Double low, Double high) {
		Double rs = div(low * 100, high);
		return rs.intValue();
	}

	/**
	 * 比较两个金额的大小
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public int compare(Number a, Number b) {
		return Double.compare(a.doubleValue(), b.doubleValue());
	}

}
