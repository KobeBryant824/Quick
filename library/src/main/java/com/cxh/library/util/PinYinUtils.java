package com.cxh.library.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 将汉字转换为拼音
 * Created by Hai (haigod7@gmail.com) on 2017/3/6 10:51.
 */
public class PinYinUtils {
	/**
	 * 不应该被频繁调用，它消耗一定内存
	 * @param hanzi
	 * @return
	 */
	public static String getPinYin(String hanzi){
		String pinyin = "";
		
		//嘿码 -> HEIMA heima
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();//控制转换是否大小写，是否带音标
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//大写
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		
		//由于不能直接对多个汉字转换，只能对单个汉字转换
		char[] arr = hanzi.toCharArray();
		//嘿   码 -> HEIMA
		//嘿@%&ada码 -> HEI@%&adaMA
		for (int i = 0; i < arr.length; i++) {
			if(Character.isWhitespace(arr[i]))continue;//如果是空格，则不处理，进行下次遍历
			
			//汉字是2个字节存储，肯定大于127，所以大于127就可以当为汉字转换
			if(arr[i]>127){
				try {
					//由于多音字的存在，单 dan shan
					String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(arr[i], format);
					
					if(pinyinArr!=null){
						pinyin += pinyinArr[0];
					}else {
						pinyin += arr[i];
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
					//不是正确的汉字
					pinyin += arr[i];
				}
			}else {
				//不是汉字，
				pinyin += arr[i];
			}
		}
		
		return pinyin;
	}
}
