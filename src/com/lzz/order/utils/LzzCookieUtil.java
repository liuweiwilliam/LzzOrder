package com.lzz.order.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LzzCookieUtil {
	/**
	 * 新建cookie记录
	 * @param name cookie名称
	 * @param value cookie值
	 * @param age 有效时间，单位秒
	 * @param is_per 是否长时间，默认七天
	 * @return
	 */
	public static Cookie createCookie(String name, String value, int age,
			boolean is_per) {
		Cookie userCookie=new Cookie(name, value); 
		if(is_per){
			userCookie.setMaxAge(7*24*30*60);   //存活期为七天7*24*60*60
		}else{
			userCookie.setMaxAge(1*1*30*60);   //存活期为半小时 1*1*30*60
		}
		userCookie.setPath("/");
		return userCookie;
	}
	
	/**
	 * h获取cookie中的值
	 * @param request request对象
	 * @param name 想要获取的值的名称
	 * @return
	 */
	public static String getFromCookie(HttpServletRequest request, String name){
		Cookie[] cookies = request.getCookies();
		if(null==cookies) return null;
	    for(Cookie cookie : cookies){
	        if(cookie.getName().equals(name)){
	            return cookie.getValue();
	        }
	    }
	    
	    return null;
	}
	
	/**
	 * 从cookie中删除某个值
	 * @param request
	 * @param name
	 */
	public static void removeCookie(HttpServletResponse response, String name){
		Cookie cookie = new Cookie(name, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}
