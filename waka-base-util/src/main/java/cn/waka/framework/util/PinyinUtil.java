package cn.waka.framework.util;

/**
 * @author lujx 2015年8月11日
 */
public class PinyinUtil {

	PinyinUtil() {

	}

	/*public String toPinyinLowercase(String chinese) {
		return this.toPinyin(chinese, HanyuPinyinCaseType.LOWERCASE);
	}

	public String toPinyinUppercase(String chinese) {
		return this.toPinyin(chinese, HanyuPinyinCaseType.UPPERCASE);
	}

	private String toPinyin(String chinese, HanyuPinyinCaseType caseType) {
		if (WakaUtils.string.isEmpty(chinese)) {
			return null;
		}
		StringBuilder rs = new StringBuilder(chinese.length());
		for (char c : chinese.toCharArray()) {
			Character character = null;
			try {
				character = this.toPinyinUppercase(c, caseType);
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				throw new ZhhrUtilException("错误的拼音输出格式" + e.getMessage(), e);
			}
			if (character != null) {
				rs.append((char) character);
			}
		}
		return rs.toString();
	}

	private Character toPinyinUppercase(char c, HanyuPinyinCaseType caseType) throws BadHanyuPinyinOutputFormatCombination {
		HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
		outputFormat.setCaseType(caseType);
		outputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
		outputFormat.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);

		String[] rs = PinyinHelper.toHanyuPinyinStringArray(c, outputFormat);
		if (WakaUtils.collection.isEmpty(rs) || WakaUtils.string.isEmpty(rs[0])) {
			return null;
		}
		return rs[0].charAt(0);
	}*/

}
