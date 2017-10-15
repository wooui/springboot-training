package cn.waka.framework.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.waka.framework.exception.ZhhrUtilException;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanMap;

/**
 * 复制反射工具类
 * 
 * @author Administrator
 * 
 */
public class BeanUtil {

	private final Map<Class<?>, Map<Class<?>, BeanCopier>> src2dest2copier;

	/** 因为类必须为public，所以只能把构造函数给这样控制 */
	BeanUtil() {
		src2dest2copier = new HashMap<>();
	}

	private BeanCopier getCopier(Class<?> src, Class<?> dest) {
		Map<Class<?>, BeanCopier> dest2copier = src2dest2copier.get(src);
		if (dest2copier == null) {
			dest2copier = new HashMap<>();
			src2dest2copier.put(src, dest2copier);
		}

		BeanCopier copier = dest2copier.get(dest);
		if (copier == null) {
			copier = BeanCopier.create(src, dest, false);
			dest2copier.put(dest, copier);
		}
		return copier;
	}

	private <T> T getInstance(Class<T> cls) {
		try {
			return cls.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new ZhhrUtilException(e.getMessage(), e);
		}
	}

	/**
	 * 对象转换
	 * 
	 * @param obj
	 * @param cls
	 * @return
	 */
	public <T> T convertObject(Object obj, Class<T> cls) {
		if (obj == null) {
			return null;
		} else if (cls.isInstance(obj)) {
			return (T) obj;
		} else {
			return this.cloneObject(obj, cls);
		}
	}

	/**
	 * 对象拷贝(与对象转换不一样的是，这里返回的肯定是新的对象)
	 * 
	 * @param obj
	 * @param cls
	 * @return
	 */
	public <T> T cloneObject(Object obj, Class<T> cls) {
		if (obj == null) {
			return null;
		} else if (obj instanceof Map && cls.isAssignableFrom(Map.class)) {// Map转Map
			return (T) new HashMap<>((Map) obj);
		} else if (obj instanceof Map) {// Map转对象
			T dest = this.getInstance(cls);
			for (Entry<?, ?> entry : ((Map<?, ?>) obj).entrySet()) {
				if (entry.getKey() != null) {
					this.setProperty(dest, entry.getKey().toString(), entry.getValue());
				}
			}
			return dest;
		} else if (cls.isAssignableFrom(Map.class)) {// 对象转Map
			return (T) this.toMap(obj);
		} else {// 对象转对象
			T dest = this.getInstance(cls);
			this.getCopier(obj.getClass(), cls).copy(obj, dest, null);
			return dest;
		}
	}

	/**
	 * 对象批量转换
	 * 
	 * @param objs
	 * @param cls
	 * @return
	 */
	public <T> List<T> convertList(Collection<?> objs, Class<T> cls) {
		if (WakaUtils.collection.isEmpty(objs)) {
			return new ArrayList<>();
		} else {
			List<T> rs = new ArrayList<>(objs.size());
			for (Object obj : objs) {
				rs.add(convertObject(obj, cls));
			}
			return rs;
		}
	}

	/**
	 * 对象转为map
	 * 
	 * @param obj
	 * @return
	 */
	public Map<String, Object> toMap(Object obj) {
		return BeanMap.create(obj);
	}

	/**
	 * 对象批量转为map
	 * 
	 * @param objs
	 * @return
	 */
	public List<Map<String, Object>> toMaps(List<Object> objs) {
		if (WakaUtils.collection.isEmpty(objs)) {
			return new ArrayList<>();
		} else {
			List<Map<String, Object>> rs = new ArrayList<>(objs.size());
			for (Object obj : objs) {
				rs.add(toMap(obj));
			}
			return rs;
		}
	}

	/**
	 * 对象拷贝
	 * 
	 * @param src
	 *            源
	 * @param dest
	 *            目标
	 */
	public void copyObject(Object src, Object dest) {
		if (src == null || dest == null || src == dest) {
			return;
		}

		this.getCopier(src.getClass(), dest.getClass()).copy(src, dest, null);
	}

	// ==============================

	private final Map<Class<?>, Method[]> cacheClazzMethods = new HashMap<Class<?>, Method[]>();

	/**
	 * 赋值
	 * 
	 * @param bean
	 * @param propertyName
	 * @param value
	 * @return 没有找到对应的属性则为false，找到了赋值失败就抛异常
	 */
	public boolean setProperty(Object bean, String propertyName, Object value) {
		if (bean == null || propertyName == null || propertyName.trim().length() == 0) {
			return false;
		}
		Method[] methods = cacheClazzMethods.get(bean.getClass());
		if (methods == null) {
			methods = bean.getClass().getMethods();
			cacheClazzMethods.put(bean.getClass(), methods);
		}

		Method method = null;
		Class<?>[] clz = null;
		String propertyNameLower = propertyName.toLowerCase();

		for (Method m : methods) {
			String name = m.getName();
			if (name.startsWith("set") && propertyNameLower.equals(name.toLowerCase().substring(3))) {
				clz = m.getParameterTypes();
				if (clz.length == 1) {
					method = m;
					break;
				}
			}
		}

		if (method == null) {
			// throw new WakaUtilException(bean.getClass().getName() +
			// "不存在方法set" + propertyName + ",或者方法的参数不为1");
			return false;
		}

		ValueConvert vc = ValueConvert.getInstance();
		Object v = vc.convert(clz[0], value);
		Object[] args = { v };
		try {
			method.invoke(bean, args);
			return true;
		} catch (Exception e) {
			String msg = bean.getClass().getName() + "." + (method != null ? method.getName() : propertyName);
			throw new ZhhrUtilException(msg + "=[" + value + "]赋值出错," + e.getMessage());
		}
	}

	/**
	 * 取值
	 * 
	 * @param bean
	 *            对象
	 * @param propertyName
	 *            属性名
	 * @return
	 */
	public <T> T getProperty(Object bean, String propertyName) {
		if (bean == null || propertyName == null || propertyName.length() == 0) {
			throw new ZhhrUtilException("bean或方法名为空,赋值不成功!");
		}

		Method[] methods = cacheClazzMethods.get(bean.getClass());
		if (methods == null) {
			methods = bean.getClass().getMethods();
			cacheClazzMethods.put(bean.getClass(), methods);
		}

		Method method = null;
		String propertyNameLower = propertyName.toLowerCase();
		for (Method m : methods) {
			String name = m.getName();
			if (name.startsWith("get") && propertyNameLower.equals(name.toLowerCase().substring(3))) {
				Class<?>[] clz = m.getParameterTypes();
				if (clz.length == 0) {
					method = m;
					break;
				}
			}
		}

		if (method == null) {
			throw new ZhhrUtilException(bean.getClass().getName() + "不存在方法get" + propertyName + ",或者方法的参数不为0");
		}

		Object[] args = {};
		try {
			Object rs = method.invoke(bean, args);
			return (T) rs;
		} catch (Exception e) {
			throw new ZhhrUtilException(bean.getClass().getName() + ".get" + propertyName + "取值出错," + e.getMessage());
		}
	}

	// ==============================

	/**
	 * 分页对象批量转换
	 * 
	 * @param page
	 * @param cls
	 * @return
	 */
	/*public <T> PageDO<T> convertPage(PageDO<?> page, Class<T> cls) {
		if (page == null) {
			return null;
		} else if (page.getTotal() <= 0) {
			return new PageDO<>();
		} else {
			PageDO<T> rs = new PageDO<>(page.getPageNum(), page.getPageSize(), page.getTotal());
			List<T> list = this.convertList(page.getRows(), cls);
			rs.setRows(list);
			return rs;
		}
	}*/

	// ==============================

	/*public static void main(String[] a) {
		BeanUtil util = new BeanUtil();

		TestBO x = new TestBO();
		x.setRoleName(new Date());

		TestBO y = util.cloneObject(x, TestBO.class);
		System.out.println(y);
		Map<String, Object> z = util.toMap(x);
		System.out.println(z);

		// Map<String, Object> x = new HashMap<>();
		// x.put("roleName", new Date());
		// TestBO y = util.cloneObject(x, TestBO.class);
		// System.out.println(y);
	}*/

}

class TestBO {

	private Date roleName;

	public Date getRoleName() {
		return roleName;
	}

	public void setRoleName(Date roleName) {
		this.roleName = roleName;
	}

}
