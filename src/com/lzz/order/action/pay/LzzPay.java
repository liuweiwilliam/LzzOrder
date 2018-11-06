package com.lzz.order.action.pay;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Transaction;

import com.lzz.order.action.LzzBaseManagerAction;
import com.lzz.order.factory.LzzFactory;
import com.lzz.order.mgr.LzzCacheMgr;
import com.lzz.order.mgr.LzzCartMgr;
import com.lzz.order.utils.LzzException;
import com.lzz.order.utils.LzzMD5;
import com.lzz.order.utils.LzzPrePayToWXServer;
import com.lzz.order.utils.LzzProperties;

public class LzzPay extends LzzBaseManagerAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4156910417402153186L;
	
	/**
	 * 获取微信支付之前的签名
	 * @return
	 */
	public String getPrePaySign(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();

		try {
			String appId = LzzProperties.getAPPID();
			String nonceStr = UUID.randomUUID().toString();
			String timeStamp = Long.toString(System.currentTimeMillis()/1000);
			String paykey = LzzProperties.getPAYKEY();
			Map<String, String> pre_rslt = LzzPrePayToWXServer.subPrePayOrder("这是订单描述", "订单ID", 100);
			if(null==pre_rslt){
				throw new LzzException("微信统一下单提交失败");
			}
			String prepayId = pre_rslt.get("prepay_id");
			String stringA = "appId=" + appId
					+ "&nonceStr=" + nonceStr
					+ "&package=prepay_id=" + prepayId
					+ "&signType=MD5"
					+ "&timeStamp=" + timeStamp
					+ "&key=" + paykey;
			String paySign = LzzMD5.get(stringA);
			rsl.put("appId", appId);
			rsl.put("nonceStr", nonceStr);
			rsl.put("timeStamp", timeStamp);
			rsl.put("paykey", paykey);
			rsl.put("paySign", paySign);
			rsl.put("iserror", false);
		} catch (Exception e) {
			e.printStackTrace();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
}
