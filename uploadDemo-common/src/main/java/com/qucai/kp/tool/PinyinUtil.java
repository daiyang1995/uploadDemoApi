package com.qucai.kp.tool;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang3.StringUtils;

public class PinyinUtil {
	/**
	 * 汉字转换位汉语拼音首字母，英文字符不变
	 * 
	 * @param chinese
	 *            汉字
	 * @return 拼音
	 */
	public static String converterToFirstSpell(String chinese) {
		String pinyinName = "";
		char[] nameChar = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat)[0].charAt(0);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}

	/**
	 * 汉字转换位汉语拼音，英文字符不变
	 * 
	 * @param chinese
	 *            汉字
	 * @return 拼音
	 */
	public static String converterToSpell(String chinese) {
		String pinyinName = "";
		char[] nameChar = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat)[0];
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}
	
	public static String getInitial(String chinese){
		String initial = "";
		
		if(StringUtils.isNotBlank(chinese)){
			initial = chinese.toLowerCase().substring(0,1);
			
			try{
				initial = converterToFirstSpell(initial);
			} catch(Exception e){
				initial = "#";
				return initial;
			}
			
			switch (initial){
			case "0":
				initial = "l";
				break;
			case "1":
				initial = "y";
				break;
			case "2":
				initial = "e";
				break;
			case "3":
				initial = "s";
				break;
			case "4":
				initial = "s";
				break;
			case "5":
				initial = "w";
				break;
			case "6":
				initial = "l";
				break;
			case "7":
				initial = "q";
				break;
			case "8":
				initial = "b";
				break;
			case "9":
				initial = "j";
				break;
			}
			
			if(!("abcdefghijklmnopqrstuvwxyz").contains(initial)){
				initial = "#";
			}
		} else {
			initial = "#";
		}
		return initial;
	}
}
