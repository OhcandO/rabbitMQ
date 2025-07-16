package com.koji.rabbitmq1.vo;

/**
 * CamelCase 변환
 * 
 * @author 오세진 UnderScore 기준
 */
public class CamelCaseUtil {
	/**
	 * CamelCase적용
	 * 
	 * @param underScore
	 * @return
	 */
	public static String convertToCalmelCase(String underScore) {
		if ((underScore.indexOf(95) < 0) && Character.isLowerCase(underScore.charAt(0)))
			return underScore;
		
		StringBuilder sb = new StringBuilder();
		boolean nextUpper = false;
		
		int len = underScore.length();
		
		for (int i = 0; i < len; i++) {
			char currChar = underScore.charAt(i);
			if (currChar == '_') {
				nextUpper = true;
			} else if (nextUpper) {
				sb.append(Character.toUpperCase(currChar));
				nextUpper = false;
			} else {
				sb.append(Character.toLowerCase(currChar));
			}
		}
		return sb.toString();
	}
	
	/**
	 * CamelCase 해제
	 * 
	 * @param upperScore
	 * @return
	 */
	public static String restoreCamelCase(String upperScore) {
		if ((Character.isUpperCase(upperScore.charAt(0)))) {
			StringBuilder sb = new StringBuilder("_");
			
			int len = upperScore.length();
			
			for (int i = 0; i < len; i++) {
				char currChar = upperScore.charAt(i);
				if (Character.isUpperCase(currChar))
					sb.append(Character.toLowerCase(currChar));
			}
			return sb.toString();
		}
		return upperScore;
	}
	
	/**
	 * Underscore to CamelCase
	 * 
	 * @param str
	 * @return
	 */
	public static String camelToUnder(String str) {
		
		String result = "";
		char c = str.charAt(0);
		result = result + Character.toLowerCase(c);
		
		for (int i = 1; i < str.length(); i++) {
			
			char ch = str.charAt(i);
			
			if (Character.isUpperCase(ch)) {
				result = result + '_';
				result = result + Character.toLowerCase(ch);
			}
			
			else
				result = result + ch;
		}
		return result;
	}

	public static String convertToPascalCase(String text) {
		String camelCase = convertToCalmelCase(text);
		return camelCase.substring(0, 1).toUpperCase() + camelCase.substring(1);
	}
}
