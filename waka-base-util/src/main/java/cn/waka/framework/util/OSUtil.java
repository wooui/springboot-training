package cn.waka.framework.util;

import cn.waka.framework.enumeration.SystemPlatform;

/**
 * 操作系统类： 获取System.getProperty("os.name")对应的操作系统
 * 
 * @author isea533
 */
public class OSUtil {

	private final SystemPlatform platform;

	/** 因为类必须为public，所以只能把构造函数给这样控制 */
	OSUtil() {
		platform = initPlatform();
	}

	/**
	 * 获取操作系统名字
	 * 
	 * @return 操作系统名
	 */
	private SystemPlatform initPlatform() {
		String OS = System.getProperty("os.name").toLowerCase();
		SystemPlatform pf;
		if (OS.indexOf("windows") >= 0) {
			pf = SystemPlatform.Windows;
		} else if (OS.indexOf("linux") >= 0) {
			pf = SystemPlatform.Linux;
		} else if (OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0) {
			pf = (OS.indexOf("x") < 0 ? SystemPlatform.Mac_OS : SystemPlatform.Mac_OS_X);
		} else if (OS.indexOf("aix") >= 0) {
			pf = SystemPlatform.AIX;
		} else if (OS.indexOf("digital") >= 0 && OS.indexOf("unix") > 0) {
			pf = SystemPlatform.Digital_Unix;
		} else if (OS.indexOf("freebsd") >= 0) {
			pf = SystemPlatform.FreeBSD;
		} else if (OS.indexOf("hp-ux") >= 0) {
			pf = SystemPlatform.HP_UX;
		} else if (OS.indexOf("irix") >= 0) {
			pf = SystemPlatform.Irix;
		} else if (OS.indexOf("mpe/ix") >= 0) {
			pf = SystemPlatform.MPEiX;
		} else if (OS.indexOf("netware") >= 0) {
			pf = SystemPlatform.NetWare_411;
		} else if (OS.indexOf("openvms") >= 0) {
			pf = SystemPlatform.OpenVMS;
		} else if (OS.indexOf("os/2") >= 0) {
			pf = SystemPlatform.OS2;
		} else if (OS.indexOf("os/390") >= 0) {
			pf = SystemPlatform.OS390;
		} else if (OS.indexOf("osf1") >= 0) {
			pf = SystemPlatform.OSF1;
		} else if (OS.indexOf("solaris") >= 0) {
			pf = SystemPlatform.Solaris;
		} else if (OS.indexOf("sunos") >= 0) {
			pf = SystemPlatform.SunOS;
		} else {
			pf = SystemPlatform.Others;
		}
		return pf;
	}

	public SystemPlatform getPlatform() {
		return this.platform;
	}

	/**
	 * @param args
	 */
	/*public static void main(String[] args) {
		System.out.println(new OSUtil().getPlatform());
		System.out.println(System.getProperty("os.name"));
		System.out.println(System.getProperty("os.version"));
		System.out.println(System.getProperty("os.arch"));
	}*/

}