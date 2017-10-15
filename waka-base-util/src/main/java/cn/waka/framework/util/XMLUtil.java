package cn.waka.framework.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import cn.waka.framework.constants.I18NConstants;
import cn.waka.framework.exception.ZhhrException;

/**
 * 处理xml的工具类
 * 
 * @author k2
 *
 */
public class XMLUtil {

	private final Map<Class<?>, Unmarshaller> UNMARSHALLERS = new ConcurrentHashMap<Class<?>, Unmarshaller>();

	private final Map<Class<?>, Marshaller> MARSHALLERS = new ConcurrentHashMap<Class<?>, Marshaller>();

	/** 因为类必须为public，所以只能把构造函数给这样控制 */
	XMLUtil() {
	}

	/**
	 * 对象转为xml字符串
	 * 
	 * @param obj
	 * @param isFormat
	 *            true即按标签自动换行，false即是一行的xml
	 * @param includeHead
	 *            true则包含xm头声明信息，false则不包含
	 * @return
	 */
	public String obj2xml(Object obj, boolean isFormat, boolean includeHead) {
		try (StringWriter writer = new StringWriter()) {
			Marshaller m = MARSHALLERS.get(obj.getClass());
			if (m == null) {
				m = JAXBContext.newInstance(obj.getClass()).createMarshaller();
				m.setProperty(Marshaller.JAXB_ENCODING, I18NConstants.DEFAULT_CHARSET);
			}
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, isFormat);
			m.setProperty(Marshaller.JAXB_FRAGMENT, !includeHead);// 是否省略xm头声明信息
			m.marshal(obj, writer);
			return writer.toString();
		} catch (Exception e) {
			throw new ZhhrException(e.getMessage(), e);
		}
	}

	/**
	 * xml字符串转化为对象
	 * 
	 * @param xml
	 * @param cls
	 * @return
	 */
	public <T> T xml2obj(String xml, Class<T> cls) {
		try {
			Unmarshaller u = UNMARSHALLERS.get(cls);
			if (u == null) {
				u = JAXBContext.newInstance(cls).createUnmarshaller();
				UNMARSHALLERS.put(cls, u);
			}
			return (T) u.unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			throw new ZhhrException(e.getMessage(), e);
		}
	}

}
