package com.cutter.point.blog.utils;

/**
 * 驼峰转下划线，或者下划线转驼峰
 * @author xiaof
 *
 */
public class NameChangeUtil {
	public static final char UNDER_LINE = '_';
	
	/**
	 * 驼峰转下划线
	 * @param param
	 * @return
	 */
	public static String camelToUnderLine(String param) {
		
		if(param == null || "".equals(param)) {
			return "";
		}
		
		//1、遍历整字符串
		StringBuffer result = new StringBuffer();
		for(int i = 0; i < param.length(); ++i) {

			//2、判定获取到大写的字符的时候，就先加一个下划线然后再添加当前字段，否则直接加上当前字符
			char currentChar = param.charAt(i);
			if(Character.isUpperCase(currentChar) && i != 0) {
				//如果是大写的，先加一个下划线，然后再加一个小写当前字符
				result.append(UNDER_LINE).append(Character.toLowerCase(currentChar));
			} else {
				result.append(currentChar);
			}
		}
		
		return result.toString().toUpperCase();
	}
	
	public static String UnderLineToCamel(String param) {
		
		if(param == null || "".equals(param)) {
			return "";
		}

		param = param.toLowerCase();
		
		boolean needUpperCase = false; //判定是否需要大写
		StringBuffer result = new StringBuffer();
		//1、遍历整个字符串，判断当前是否是下划线
		for(int i = 0; i < param.length(); ++i) {
			//2、如果当前字符是下划线的话，那么跳过当前字符，开始下一个字符添加并下一个要大写
			char currentChar = param.charAt(i);
			if(currentChar == UNDER_LINE) {
				needUpperCase = true;
			} else {
				if(needUpperCase) {
					result.append(Character.toUpperCase(currentChar));
					//驼峰只需要变成一个大写，下次重新判断
					needUpperCase = false;
				} else {
					result.append(currentChar);
				}
			}
		}
		
		return result.toString();
		
	}
	
	public static void main(String[] args) {
		//测试
		String p = "TFileStore";
		System.out.println(NameChangeUtil.camelToUnderLine(p));

	}
}
