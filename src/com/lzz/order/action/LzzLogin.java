package com.lzz.order.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Transaction;

import net.sf.json.JSONObject;

import com.lzz.order.factory.LzzFactory;
import com.lzz.order.mgr.LzzCacheMgr;
import com.lzz.order.mgr.LzzEncodeMgr;
import com.lzz.order.mgr.LzzLoginMgr;
import com.lzz.order.mgr.LzzUserMgr;
import com.lzz.order.pojo.LzzUser;
import com.lzz.order.pojo.LzzUserSession;
import com.lzz.order.service.LzzUserService;
import com.lzz.order.utils.LzzCookieUtil;
import com.opensymphony.xwork2.ActionSupport;

public class LzzLogin extends LzzBaseManagerAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 588222189991029989L;
	
	public String code;
	public String login() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			rsl = LzzLoginMgr.userLogin(code);
			if(rsl.containsKey("errcode")){
				rsl.put("iserror", true);
			}
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			LzzCacheMgr.reloadAllCache();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = resp.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String phone;
	public String pwd;
	public String loginForLzzManager(){
		HttpServletRequest reqs = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			rsl.put("success", false);
			LzzUser user = LzzUserService.self().getLzzUserByPhone(phone);
			if(LzzEncodeMgr.MD5(pwd, "utf-8").equals(user.getPwd())){
				curUserId = user.getId();
				curUser = user;
				LzzUserSession us = LzzUserService.self().getLzzUserSessionByUserId(curUserId);
				boolean is_per = false;
				int age = 1*1*30*60;
				if(null==us){
					us = LzzUserMgr.createNewUserSession(curUserId, null);
				}
				Cookie userCookie = LzzCookieUtil.createCookie("sessionId", us.getSessionId(), age, is_per);
				resp.addCookie(userCookie);
				rsl.put("success", true);
			}
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			LzzCacheMgr.reloadAllCache();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = resp.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
}
