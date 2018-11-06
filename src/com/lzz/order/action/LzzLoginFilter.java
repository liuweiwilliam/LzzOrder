package com.lzz.order.action;

//created by huangwei on 2017/7/25
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lzz.order.pojo.LzzUserSession;
import com.lzz.order.service.LzzUserService;
import com.lzz.order.utils.LzzCookieUtil;

//登录过滤器，系统必须先登录成功才能进入主页，但在浏览器里直接输入主页地址，发现也能进入，
//这个肯定不好，毫无安全性可言，登录过滤器可以避免未经登录即可进入主页

public class LzzLoginFilter implements Filter {
	//无需过滤的url放在该ArrayList里面
	List<String> donotfilterurl = new ArrayList<>();

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain filterchain) throws IOException, ServletException {
		System.out.println("orig hash : " + arg0.hashCode());
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		
		System.out.println("request url : " + request.getRequestURI() + ",request hash : " + request.hashCode());
		if(request.getRequestURI().equals("/LZZOrder/")
			|| (request.getRequestURI().indexOf(".jpg") != -1)
			|| (request.getRequestURI().indexOf(".JPG") != -1)
			|| (request.getRequestURI().indexOf(".png") != -1) 
			|| (request.getRequestURI().indexOf(".PNG") != -1) 
			|| (request.getRequestURI().indexOf(".bmp") != -1) 
			|| (request.getRequestURI().indexOf(".gif") != -1) 
			|| (request.getRequestURI().indexOf(".ico") != -1) 
			|| (request.getRequestURI().indexOf(".jpeg") != -1) 
			|| (request.getRequestURI().indexOf(".JPEG") != -1) 
			|| (request.getRequestURI().indexOf(".js") != -1 && request.getRequestURI().indexOf(".jsp") == -1) 
			|| (request.getRequestURI().indexOf(".css") != -1)
			|| (request.getRequestURI().indexOf(".woff2") != -1)
			|| (request.getRequestURI().indexOf("fonts/icomoon.ttf") != -1)
			|| (request.getRequestURI().indexOf("login.html") != -1)
			|| (request.getRequestURI().indexOf("login.action") != -1)
			|| (request.getRequestURI().indexOf("loginForLzzManager.action") != -1)){
			
			filterchain.doFilter(arg0, arg1);
			return;
		} 
		
		HttpSession session = request.getSession();
		String session_id = request.getHeader("sessionId");
		LzzUserSession us = LzzUserService.self().getLzzUserSessionBySessionId(session_id);
		if(null==session_id
				|| null==us){
			session_id = (String) LzzCookieUtil.getFromCookie(request, "sessionId");
			us = LzzUserService.self().getLzzUserSessionBySessionId(session_id);
			if(null==session_id
					|| null==us){
				LzzCookieUtil.removeCookie(response, "sessionId");
				LzzGotoLoginPage.go(response);
				return;
			}else{
				//检查session中的sessionId是否正确，如果正确跳过，不正确则清除之后跳过
				if(!sessionIsValid(request, session_id)){
					LzzCookieUtil.removeCookie(response, "sessionId");
				}
			}
		}else{
			if(!sessionIsValid(request, session_id)){
				LzzCookieUtil.removeCookie(response, "sessionId");
			}
		}
		
		filterchain.doFilter(arg0, arg1);
	}

	private boolean sessionIsValid(HttpServletRequest request, String session_id) {
		// TODO Auto-generated method stub
		if(null==request || null==session_id || "".equals(session_id)){
			return false;
		}
		
		LzzUserSession us = LzzUserService.self().getLzzUserSessionBySessionId(session_id);
		if(null==us) return false;
		
		return true;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
