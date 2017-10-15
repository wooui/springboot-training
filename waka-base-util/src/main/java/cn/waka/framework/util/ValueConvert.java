package cn.waka.framework.util;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Calendar;

import cn.waka.framework.exception.ZhhrUtilException;


/**
 * 给BeanUtil用的
 * 
 * @author k2
 *
 */
class ValueConvert {

	private static ValueConvert vc;

	public static ValueConvert getInstance() {
		if (vc == null)
			vc = new ValueConvert();
		return vc;
	}

	/**
	 * 判断类型
	 * 
	 * @return
	 */
	private Object convertValue(Class<?> clazz, Object vals) {
		if (vals == null) {
			return null;
		}
		Class<?> valclz = vals.getClass();
		if (clazz == valclz || valclz.isAssignableFrom(clazz)) {// valclz
																// 是否从clazz派生
																// 2013-4-4
			return vals;
		}
		// ////////////////////////////////////

		if (clazz.isArray()) {
			clazz = (Class<?>) Array.get(clazz, 0);// 取第一个元素
		}
		if (vals.getClass().isArray()) {
			vals = Array.get(vals, 0);// 取第一个元素
		}

		Object result = null;
		if (clazz == vals.getClass()) {
			result = vals;
		} else if (clazz == String.class) {
			result = vals.toString();
		} else if (clazz == int.class || clazz == Integer.class) {
			result = convertNumber(vals, 1);
		} else if (clazz == long.class || clazz == Long.class) {
			result = convertNumber(vals, 2);
		} else if (clazz == float.class || clazz == Float.class) {
			result = convertNumber(vals, 3);
		} else if (clazz == double.class || clazz == Double.class) {
			result = convertNumber(vals, 4);
		} else if (clazz == BigDecimal.class) {
			result = convertNumber(vals, 5);
		} else if (clazz == boolean.class || clazz == Boolean.class) {
			if (vals instanceof Boolean) {
				result = vals;
			} else {
				result = Boolean.valueOf(vals.toString());
			}
		} else if (clazz == java.util.Date.class) {
			if (vals instanceof java.util.Date) {
				result = vals;
			} else {
				Calendar c = parseDate(vals.toString());
				if (c != null) {
					result = c.getTime();
				}
			}
		} else if (clazz == java.sql.Date.class) {
			if (vals instanceof java.sql.Date) {
				result = vals;
			} else if (vals instanceof java.util.Date) {
				java.util.Date d = (java.util.Date) vals;
				result = new java.sql.Date(d.getTime());
			} else {
				Calendar c = parseDate(vals.toString());
				if (c != null) {
					result = new java.sql.Date(c.getTimeInMillis());
				}
			}
		} else if (clazz == java.sql.Timestamp.class) {
			if (vals instanceof java.sql.Timestamp) {
				result = vals;
			} else if (vals instanceof java.util.Date) {
				java.util.Date d = (java.util.Date) vals;
				result = new java.sql.Timestamp(d.getTime());
			} else {
				Calendar c = parseDate(vals.toString());
				if (c != null) {
					result = new java.sql.Timestamp(c.getTimeInMillis());
				}
			}
		} else
			throw new ZhhrUtilException("不能转换类型:" + clazz.getName());

		return result;
	}

	private int parseInt(String s) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return -99;
		}
	}

	/**
	 * 把字符串转换日期
	 * 
	 * @param date
	 *            字符串,支持的格式为:yyyy/MM/dd 或 yyyy/MM/dd hh:mm:ss
	 * @return 如果格式不符,则返回null
	 */
	public Calendar parseDate(String date) {
		if (WakaUtils.string.isEmpty(date)) {
			return null;
		}

		// 字符格式 yyyy/MM/dd 或 yyyy/MM/dd hh:mm:ss
		boolean format = false;
		int year = 1970, month = 0, day = 1, hrs = 0, min = 0, sec = 0;

		date = date.trim().replace('.', '-').replace('/', '-');
		int i = date.indexOf(' ');
		String time = null;
		if (i > 0) {
			time = date.substring(i + 1);
			date = date.substring(0, i);
		} else if (date.indexOf(':') > 0) {
			time = date;
			date = null;
		}

		// 日期
		if (date != null) {
			String[] d = date.split("-");
			if (format = (d.length == 3)) {
				int y = parseInt(d[0]);
				if (y != -99) {
					year = y;
				} else {
					format = false;
				}

				if (format) {
					int m = parseInt(d[1]);
					if (m != -99)
						month = m;
					else
						format = false;
				}
				if (format) {
					int dx = parseInt(d[2]);
					if (dx != -99)
						day = dx;
					else
						format = false;
				}
			}
		}

		// 时间
		if (time != null) {
			String[] t = time.split(":");
			if (t.length == 3) {
				format = true;
				int x = parseInt(t[0]);
				if (x != -99) {
					hrs = x;
				} else {
					format = false;
				}
				if (format) {
					int dx = parseInt(t[1]);
					if (dx != -99)
						min = dx;
					else
						format = false;
				}

				if (format) {
					String s = t[2];
					int idx = s.indexOf('-');
					if (idx > 0) {
						s = s.substring(0, idx);
					}
					x = parseInt(s);
					if (x != -99) {
						sec = Integer.parseInt(s);
					} else {
						format = false;
					}
				}
			}
		}

		if (format) {
			Calendar gdate = Calendar.getInstance();
			gdate.set(year, month - 1, day, hrs, min, sec);
			gdate.set(Calendar.MILLISECOND, 0);
			return gdate;
		} else {
			return null;
		}
	}

	private Number convertNumber(Object v, int type) {
		if (v == null) {
			return null;
		}
		Number rs = null;
		switch (type) {
		case 1:// int
			if (v instanceof Integer) {
				rs = (Integer) v;
			} else if (v instanceof Number) {
				rs = Integer.valueOf(((Number) v).intValue());
			} else {
				String s = v.toString();
				if (WakaUtils.number.isInt(s)) {
					rs = Integer.valueOf(s);
				}
			}
			break;

		case 2:// long
			if (v instanceof Long) {
				rs = (Long) v;
			} else if (v instanceof Number) {
				rs = Long.valueOf(((Number) v).longValue());
			} else {
				String s = v.toString();
				rs = Long.valueOf(s);
			}
			break;
		case 3:// float
			if (v instanceof Float) {
				rs = (Float) v;
			} else if (v instanceof Number) {
				rs = Float.valueOf(((Number) v).floatValue());
			} else {
				String s = v.toString();
				rs = Float.valueOf(s);
			}
			break;
		case 4:// double
			if (v instanceof Double) {
				rs = (Double) v;
			} else if (v instanceof Number) {
				rs = Double.valueOf(((Number) v).doubleValue());
			} else {
				String s = v.toString();
				rs = Double.valueOf(s);
			}
			break;
		case 5:// BigDecimal
			if (v instanceof BigDecimal) {
				rs = (BigDecimal) v;
			} else if (v instanceof Number) {
				rs = BigDecimal.valueOf(((Number) v).doubleValue());
			} else {
				double d = Double.parseDouble(v.toString());
				rs = BigDecimal.valueOf(d);
			}
		}
		return rs;
	}

	/**
	 * 自动赋值时,进行类型转换:把vals(String)转换为clazz类型
	 * 
	 * @param clazz
	 * @param vals
	 * @return
	 */
	public Object convert(Class<?> clazz, Object value) {
		Object result = null;
		if (clazz.isArray() && value.getClass().isArray()) {
			result = converArrayBaseType(clazz, value); // 基础类型数组
			if (result == null)
				result = converArray(clazz, value);// 扩展类型
		} else
			result = convertValue(clazz, value);
		return result;
	}

	/**
	 * 扩展类型数组
	 * 
	 * @param clazz
	 * @param value
	 * @return
	 */
	private Object converArray(Class<?> clazz, Object value) {
		Class<?> componentType = clazz.getComponentType();
		Object result = Array.newInstance(componentType, Array.getLength(value));
		for (int i = 0, icount = Array.getLength(value); i < icount; i++) {
			Object o = convertValue(componentType, Array.get(value, i));
			Array.set(result, i, o);
		}
		return result;
	}

	/**
	 * 基础类型数组
	 * 
	 * @param clazz
	 * @param value
	 * @return
	 */
	private Object converArrayBaseType(Class<?> clazz, Object value) {
		int size = Array.getLength(value);
		Object result = null;
		if (clazz == int[].class) {
			int[] r = new int[size];
			for (int x = 0; x < size; x++) {
				Object o = Array.get(value, x);
				r[x] = (o == null ? 0 : Integer.parseInt(o.toString()));
			}
			result = r;
		}

		else if (clazz == long[].class) {
			long[] r = new long[size];
			for (int x = 0; x < size; x++) {
				Object o = Array.get(value, x);
				r[x] = (o == null ? 0 : Long.parseLong(o.toString()));
			}
			result = r;
		} else if (clazz == float[].class) {
			float[] r = new float[size];
			for (int x = 0; x < size; x++) {
				Object o = Array.get(value, x);
				r[x] = (o == null ? 0 : Float.parseFloat(o.toString()));
			}
			result = r;
		} else if (clazz == double[].class) {
			double[] r = new double[size];
			for (int x = 0; x < size; x++) {
				Object o = Array.get(value, x);
				r[x] = (o == null ? 0 : Double.parseDouble(o.toString()));
			}
			result = r;
		} else if (clazz == boolean[].class) {
			boolean[] r = new boolean[size];
			for (int x = 0; x < size; x++) {
				Object o = Array.get(value, x);
				r[x] = (o == null ? false : Boolean.parseBoolean(o.toString()));
			}
			result = r;
		}

		return result;
	}

}
