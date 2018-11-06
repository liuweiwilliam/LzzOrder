package com.lzz.order.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;

import com.lzz.order.pojo.LzzUser;
import com.lzz.order.pojo.LzzUserSession;
import com.lzz.order.service.LzzUserService;
import com.lzz.order.utils.LzzCookieUtil;
import com.opensymphony.xwork2.ActionSupport;

public class LzzBaseManagerAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, ServletContextAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LzzUser curUser = null;
	public String curUserId = null;

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected ServletContext application;

	@Override
	public void setServletContext(ServletContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response = arg0;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.request = arg0;
		this.session = request.getSession();
		String session_id = request.getHeader("sessionId");
		
		if(null!=session_id
				&& !"".equals(session_id)){
			LzzUserSession us = LzzUserService.self().getLzzUserSessionBySessionId(session_id);
			curUserId = us.getUserId();
			curUser = LzzUserService.self().getLzzUserById(curUserId);
		}else{
			session_id = LzzCookieUtil.getFromCookie(request, "sessionId");
			if(null!=session_id
					&& !"".equals(session_id)){
				LzzUserSession us = LzzUserService.self().getLzzUserSessionBySessionId(session_id);
				curUserId = us.getUserId();
				curUser = LzzUserService.self().getLzzUserById(curUserId);
			}
		}
	}
}
