package com.lzz.order.utils;

import java.security.MessageDigest;

public class LzzMD5 {
	public static String get(String value){
		char md5String[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
                'A', 'B', 'C', 'D', 'E', 'F' }; 
		try{
			if(value==null){
				return null;
			}
			byte[] inputString = value.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(inputString);
			
			byte[] result = md.digest();
			int j = result.length;
			char[] str = new char[j*2];
			
            int k = 0;  
            for (int i = 0; i < j; i++) {   //  i = 0  
                byte byte0 = result[i];  //95  
                str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5   
                str[k++] = md5String[byte0 & 0xf];   //   F  
            }  
			
			return new String(str);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public static void main(String[] args){
		System.out.println(get("123456"));
	}
}
