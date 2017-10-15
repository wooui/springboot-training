package cn.waka.framework.util;

import java.io.Serializable;

public class ArrayUtil {
	public String join(String seperator, Object[] array){
		StringBuilder ret = new StringBuilder();
		for(int x=0; x < array.length; x++){
			if(x != array.length - 1){
				ret.append(array[x]);
				ret.append(seperator);
			}else{
				ret.append(array[x]);
			}
		}
		return ret.toString();
	}
	
	public <T> String join(String seperator, Iterable<T> collection){
		StringBuilder ret = new StringBuilder();
		for(T obj : collection){
			ret.append(obj);
			ret.append(seperator);
		}
		ret.setLength(ret.length() - 1);
		return ret.toString();
	}

}
