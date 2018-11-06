package com.lzz.order.utils;

import java.net.InetAddress;

public class LzzIPUtil {
	/**
	 * 获取本机IP
	 * @return
	 */
	public static String getIp() {  
        InetAddress ia=null; 
        try {  
            ia=InetAddress.getLocalHost();  
            String localip=ia.getHostAddress();
            return localip;  
        } catch (Exception e) {  
            return null;  
        }  
    }
}
