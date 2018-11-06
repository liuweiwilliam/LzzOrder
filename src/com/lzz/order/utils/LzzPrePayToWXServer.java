package com.lzz.order.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LzzPrePayToWXServer {
	private static String prePayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	/**
	 * 提交微信统一下单
	 * @param description 支付描述
	 * @param out_trade_no 外部交易ID，例如订单ID
	 * @param total_fee 支付的钱数
	 * @return
	 */
	public static Map<String, String> subPrePayOrder(String description,String out_trade_no, double total_fee){
		Map<String, String> rslt = new HashMap<String, String>();
		String contents = WXParamGenerate(description, out_trade_no, total_fee);
		String rslt_str = LzzSendByPost.sendByPost(prePayUrl, null, null, contents);
		try {
			rslt = LzzMapUtil.xmlToMap(rslt_str);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return rslt;
	}
	
	/**
	 * 微信统一下单参数设置
	 * @param description 支付描述
	 * @param out_trade_no 外部交易ID，例如订单ID
	 * @param total_fee 支付的钱数
	 * @return
	 */
	public static String WXParamGenerate(String description,String out_trade_no,double total_fee){  
		int fee = (int)(total_fee * 100.00);  
		Map<String,String> param = new HashMap<String,String>();  
		param.put("appid", LzzProperties.getAPPID());  
		param.put("mch_id", LzzProperties.getMCHID()); 
		param.put("nonce_str", UUID.randomUUID().toString());  
		param.put("body", description);  
		param.put("out_trade_no", out_trade_no);
		param.put("total_fee", fee+"");  
		param.put("spbill_create_ip", LzzIPUtil.getIp());  
		param.put("notify_url", LzzProperties.getPAYNOTIFYURL());  
		param.put("trade_type", "JSAPI");
     
		String sign = LzzWXSignUtil.getSign(param);
   
		param.put("sign", sign);  
		return LzzMapUtil.mapToXML(param);
	}
}
