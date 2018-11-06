package com.lzz.order.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

public class LzzAccessToken {
	private static final String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
	
	private static String ACCESS_TOKEN = "";
	private static Date ACCESS_VALID_DATE = null;
	private static int ACCESS_VALID_MIN = 120;
	public static String getAccessToken(){
		
		if(!"".equals(ACCESS_TOKEN)){
			//check access token valid date
			if(!new Date().after(ACCESS_VALID_DATE)){
				System.out.println("access token is still valid, return directly.");
				return ACCESS_TOKEN;
			}
		}
		
		String access_token=ACCESS_TOKEN;
		String response = "";
		List<String> par_name = new ArrayList<String>();
		List<String> par_value = new ArrayList<String>();
		par_name.add("appid");
		par_value.add(LzzProperties.getAPPID());
		par_name.add("secret");
		par_value.add(LzzProperties.getAPP_SECRET());
		par_name.add("grant_type");
		par_value.add("client_credential");

		Date get_date = new Date();
		response = LzzSendByGet.sendByGet(accessTokenUrl, par_name, par_value);

		System.out.println("this is respons" + response);
		JSONObject obj = JSONObject.fromObject(response);
		if(!obj.containsKey("errcode")){
			System.out.println("get access_token success!");
			access_token = obj.getString("access_token");
			System.out.println("access_token is:" + access_token);
		}else{
			System.out.println("get access_token failed : " + obj.getString("errmsg"));
		}

		if(!ACCESS_TOKEN.equals(access_token)){
			ACCESS_VALID_DATE = LzzDateUtil.delayMinute(get_date, ACCESS_VALID_MIN);
			ACCESS_TOKEN = access_token;
		}
		return access_token;
	}
	
	public static void main(String[] args) {
		String token = getAccessToken();
		System.out.println(token);
	}
}