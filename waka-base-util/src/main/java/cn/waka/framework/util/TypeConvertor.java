package cn.waka.framework.util;

/**
 * 类型转换器
 * 
 * @author k2
 *
 */
interface TypeConvertor<T> {

	public T convert(String str);

}
