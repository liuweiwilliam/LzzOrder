package com.lzz.order.utils;

import java.util.Map;

public class LzzWXSignUtil {
	public static String getSign(Map<String,String> param){  
        String StringA =  formatUrlMap(param);
        String stringSignTemp = LzzMD5.get(StringA+"&key="+LzzProperties.getPAYKEY()).toUpperCase();  
        return stringSignTemp;
	}
	
	/**
	 * 将Map格式的数据转成URL参数的格式
	 * @param param
	 * @return
	 */
	private static String formatUrlMap(Map<String,String> param){
		String rslt = "";
		if(null==param) return rslt;
		Map<String, String> resultMap = LzzMapUtil.sortMapByKey(param);
		int index = 0;
		for (Map.Entry<String,String> entry : resultMap.entrySet()) {
            if(index!=0){
            	rslt += "&";
            }
			String key = entry.getKey();
            String value = entry.getValue();
            
            rslt += key + "=" + value;
		}
		
		return rslt;
	}
}
