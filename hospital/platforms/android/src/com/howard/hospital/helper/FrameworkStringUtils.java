package com.howard.hospital.helper;

/**
 * 
 * @ClassName:  FrameworkStringUtil   
 * @Description:TODO    
 * @author: mayijie
 * @date:   2017年2月9日 上午11:10:18   
 *     
 * @Copyright: 2017 https://github.com/majieHoward Inc. All rights reserved.
 */
public class FrameworkStringUtils {
	/**
	 * 
	 * @Title: isEmpty   
	 * @Description: TODO 
	 * @param: @param cs
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public static boolean isEmpty(final CharSequence parameter) {
        return parameter == null || parameter.length() == 0;
    }
	/**
	 * 
	 * @Title: asString   
	 * @Description: TODO 
	 * @param: @param obj
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public static String asString(final Object obj) {
		return ((obj != null) ? obj.toString() : "");
	}
    /**
     * 
     * @Title: isNullOfString   
     * @Description: TODO 判断一个字符串对象是否为空或值为空   
     * @param: @param parameter
     * @param: @return      
     * @return: boolean      
     * @throws
     */
	public static boolean isNullOfString(final String parameter) {
		return "".equals(asString(parameter));
	}
    /**
     * 
     * @Title: splitString   
     * @Description: TODO 截取字符串
     * @param: @param originalStr
     * @param: @param beginIndex
     * @param: @param endIndex
     * @param: @return      
     * @return: String      
     * @throws
     */
	public static String splitString(final String originalStr, final int beginIndex,
			final int endIndex) {
		if (!"".equals(asString(originalStr))) {
			if (beginIndex >= 0 && endIndex > 0 && beginIndex < endIndex
					&& endIndex <= originalStr.length()) {
				return new String(originalStr.substring(beginIndex, endIndex));
			}

		}
		return asString(null);
	}

}
