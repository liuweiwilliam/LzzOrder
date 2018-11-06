package com.lzz.order.mgr;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.lzz.order.pojo.LzzUser;
import com.lzz.order.pojo.LzzUserSession;
import com.lzz.order.service.LzzDictionaryService;
import com.lzz.order.service.LzzUserService;
import com.lzz.order.utils.LzzConstString;
import com.lzz.order.utils.LzzProperties;
import com.lzz.order.utils.LzzSendByGet;
import com.lzz.order.utils.LzzSendByPost;

import net.sf.json.JSONObject;

public class LzzLoginMgr {
	private static String sessionKeyUrl = "https://api.weixin.qq.com/sns/jscode2session";
	
	public static JSONObject getUserOpenIdAndSessionKey(String code){
		List<String> par_names = new ArrayList<String>();
		List<String> par_vals = new ArrayList<String>();
		
		par_names.add("appid");
		par_vals.add(LzzProperties.getAPPID());
		par_names.add("secret");
		par_vals.add(LzzProperties.getAPP_SECRET());
		par_names.add("js_code");
		par_vals.add(code);
		par_names.add("grant_type");
		par_vals.add("authorization_code");
		
		JSONObject rslt = new JSONObject();
		String response = LzzSendByGet.sendByGet(sessionKeyUrl, par_names, par_vals);
		if("ERROR".equals(response)){
			return rslt;
		}
		
		rslt = JSONObject.fromObject(response);
		
		return rslt;
	}
	
	public static JSONObject userLogin(String code){
		JSONObject rslt = new JSONObject();
		JSONObject session_key = getUserOpenIdAndSessionKey(code);
		if(session_key.containsKey("errcode")){
			return session_key;
		}
		String openId = (String)session_key.get("openid");
		String sessionKey = (String)session_key.get("session_key");
		String unionId = (String)session_key.get("unionid");
		
		//查找该用户历史的登录信息并删除，新建新的登录信息
		LzzUser user = LzzUserService.self().getLzzUserByOpenId(openId);
		if(null==user){
			//未登录过，新用户，记录用户信息
			user = new LzzUser();
			user.setId(LzzIDMgr.self().getID());
			user.setOpenId(openId);
			user.setUnionId(unionId);
			user.setAvatarUrl(LzzProperties.getProjectUrl() + LzzProperties.getDefaultAvatarUrl());
			user.setNick_name(LzzConstString.smNickName_prefix + user.getId());
			user.setLevel(LzzDictionaryMgr.getDefaultUserLevel());
			user.setCur_integration("0");
			user.setTotal_integration("0");
			LzzUserService.self().saveLzzUser(user);
		}else{
			LzzUserSession user_session = LzzUserService.self().getLzzUserSessionByUserId(user.getId());
			if(null!=user_session){
				LzzUserService.self().delLzzUserSession(user_session);
			}
		}
		
		LzzUserSession us = LzzUserMgr.createNewUserSession(user.getId(), sessionKey);
		
		rslt.put("sessionId", us.getSessionId());
		
		return rslt;
	}

}
