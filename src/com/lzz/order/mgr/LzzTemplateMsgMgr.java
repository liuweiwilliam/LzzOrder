package com.lzz.order.mgr;

import java.util.ArrayList;
import java.util.List;

import com.lzz.order.utils.LzzAccessToken;
import com.lzz.order.utils.LzzProperties;
import com.lzz.order.utils.LzzSendByPost;

import net.sf.json.JSONObject;

public class LzzTemplateMsgMgr {
	public static final String templateMsgSendUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send";
	
	/**
	 * 发送订单提交成功消息
	 * @param toUser
	 * @param orderNum
	 * @param formId
	 */
	public static void sendSubmitOrderSuccessMsg(String toUser, String orderNum, String formId){
		JSONObject msg = new JSONObject();
		msg.put("touser", toUser);
		msg.put("template_id", LzzProperties.getTemplate_SubmitOrder());
		msg.put("page", "");
		msg.put("form_id", formId);
		
		JSONObject data = new JSONObject();
		JSONObject keyword1 = new JSONObject();
		keyword1.put("value", orderNum);
		keyword1.put("color", "#173177");
		data.put("keyword1", keyword1);
		
		msg.put("data", data);
		
		List<String> par_names = new ArrayList<String>();
		List<String> par_vals = new ArrayList<String>();
		par_names.add("access_token");
		par_vals.add(LzzAccessToken.getAccessToken());
		String response = LzzSendByPost.sendByPost(templateMsgSendUrl, par_names, par_vals, msg.toString());
		if("ERROR".equals(response)
				|| JSONObject.fromObject(response).getInt("errcode")!=0){
			System.out.println("send msg failed : " + response);
		}
	}
	
	
}
