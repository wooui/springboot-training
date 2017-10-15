package cn.waka.framework.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import cn.waka.framework.exception.ZhhrUtilException;


/**
 * 日期工具类
 * 
 * @author Administrator
 */
public class DateUtil {

	/** 因为类必须为public，所以只能把构造函数给这样控制 */
	DateUtil() {

	}

	private final String format = "yyyy-MM-dd HH:mm:ss";

	/** 获取日期+时间的格式 */
	public DateFormat getDateTimeFormat(int timeZone) {
		return this.getFormat(format, timeZone);
	}

	/** 获取日期的格式 */
	public DateFormat getDateFormat(int timeZone) {
		return this.getFormat("yyyy-MM-dd", timeZone);
	}

	/** 获取时间的格式 */
	public DateFormat getTimeFormat(int timeZone) {
		return this.getFormat("HH:mm:ss", timeZone);
	}

	/**
	 * 获取指定时区的格式
	 * 
	 * @return
	 */
	public DateFormat getFormat(String format, int timeZone) {
		DateFormat sdf = new SimpleDateFormat(format);
		TimeZone zone = TimeZone.getTimeZone(timeZone < 0 ? "GMT" + timeZone : "GMT+" + timeZone);
		sdf.setTimeZone(zone);
		return sdf;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return GMT时间对象
	 */
	public Date getNow() {
		return new Date();
	}

	/**
	 * 获取当前时区的时间字符串
	 * 
	 * @return 当前时区的时间字符串
	 */
	public String getNowStr() {
		return new SimpleDateFormat(format).format(getNow());
	}

	/**
	 * 获取指定时区的指定时间的年月日字符串
	 * 
	 * @return
	 */
	public String getDateStr(int timeZone, Date date) {
		return this.getDateFormat(timeZone).format(date);
	}

	/**
	 * 获取指定时区的当前时间的年月日字符串
	 * 
	 * @return
	 */
	public String getNowDateStr(int timeZone) {
		return this.getDateFormat(timeZone).format(this.getNow());
	}

	/**
	 * 获取GMT时区的时间字符串
	 * 
	 * @return GMT时区的时间字符串
	 */
	public String getGMTNowStr() {
		return this.getDateTimeFormat(0).format(getNow());
	}

	/**
	 * 获取当天的第一秒
	 * 
	 * @param timeZone
	 *            时区
	 * @return GMT时间对象
	 */
	public Date getTodayFirstSecond(int timeZone) {
		// 当前的GMT时间
		Date now = getNow();

		// 获取本地的年月日
		String ymd = this.getDateFormat(timeZone).format(now);

		// 获取本地的当天的第一秒，换算成GMT的时间对象
		try {
			return this.getDateTimeFormat(timeZone).parse(ymd + " 00:00:00");
		} catch (ParseException e) {
			throw new ZhhrUtilException(e.getMessage(), e);
		}
	}

	/**
	 * 获取指定日的第一秒
	 * 
	 * @param timeZone
	 *            时区
	 * @param yyyy_MM_dd
	 *            年月日
	 * @return GMT时间对象
	 */
	public Date getFirstSecond(int timeZone, String yyyy_MM_dd) {
		// 获取本地的指定日的第一秒，换算成GMT的时间对象
		try {
			return this.getDateTimeFormat(timeZone).parse(yyyy_MM_dd + " 00:00:00");
		} catch (ParseException e) {
			throw new ZhhrUtilException(e.getMessage(), e);
		}
	}

	/**
	 * 获取当天的最后一秒
	 * 
	 * @param timeZone
	 *            时区
	 * @return GMT时间对象
	 */
	public Date getTodayLastSecond(int timeZone) {
		// 当前的GMT时间
		Date now = getNow();

		// 获取本地的年月日
		String ymd = this.getDateFormat(timeZone).format(now);

		// 获取本地的当天的最后一秒，换算成GMT的时间对象
		try {
			return this.getDateTimeFormat(timeZone).parse(ymd + " 23:59:59");
		} catch (ParseException e) {
			throw new ZhhrUtilException(e.getMessage(), e);
		}
	}

	/**
	 * 获取指定日的最后一秒
	 * 
	 * @param timeZone
	 *            时区
	 * @param yyyy_MM_dd
	 *            年月日
	 * @return GMT时间对象
	 */
	public Date getLastSecond(int timeZone, String yyyy_MM_dd) {
		// 获取本地的指定日的最后一秒，换算成GMT的时间对象
		try {
			return this.getDateTimeFormat(timeZone).parse(yyyy_MM_dd + " 23:59:59");
		} catch (ParseException e) {
			throw new ZhhrUtilException(e.getMessage(), e);
		}
	}

	/**
	 * 获取指定日的前一天
	 * 
	 * @return
	 */
	public Date getPrevDay(Date date) {
		long time = date.getTime();
		return new Date(time - 3600 * 24 * 1000);
	}

}
