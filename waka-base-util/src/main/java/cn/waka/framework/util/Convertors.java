package cn.waka.framework.util;

/**
 * 常用的转换器
 * 
 * @author k2
 *
 */
enum Convertors implements TypeConvertor {

	string2long {

		@Override
		public Long convert(String str) {
			return WakaUtils.string.isEmpty(str) ? null : Long.parseLong(str);
		}
	}

}
