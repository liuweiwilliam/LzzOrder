package com.lzz.order.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class LzzProperties {
	private static String APPID = ""; //小程序 APP ID
	private static String APP_SECRET = ""; //小程序APP secret 
	private static String PAYKEY = ""; //
	private static String MCHID = ""; //商户号
	private static String PAYNOTIFYURL = ""; //微信支付成功回调地址
	private static String FILE_UPLOAD_PATH = ""; //文件上传路径
	private static String DOMAIN_NAME = ""; //项目域名
	private static String PROJECT_NAME = ""; //项目名称
	private static String LOGIN_URL = ""; //登陆页面地址
	
	private static String DefaultAvatarUrl = ""; //默认头像地址

	private static String Template_SubmitOrder = ""; //提交订单消息模板ID
	private static String Template_SubmitEvaluate = ""; //提交评价消息模板ID

	public static String getAPPID(){
		if (APPID.equals("")) initDDProperties();
		return APPID;
	}
	
	public static String getAPP_SECRET(){
		if (APP_SECRET.equals("")) initDDProperties();
		return APP_SECRET;
	}
	
	public static String getPAYKEY(){
		if (PAYKEY.equals("")) initDDProperties();
		return PAYKEY;
	}
	
	public static String getMCHID(){
		if (MCHID.equals("")) initDDProperties();
		return MCHID;
	}
	
	public static String getPAYNOTIFYURL(){
		if (PAYNOTIFYURL.equals("")) initDDProperties();
		return PAYNOTIFYURL;
	}
	
	public static String getFILE_UPLOAD_PATH(){
		if (FILE_UPLOAD_PATH.equals("")) initDDProperties();
		return FILE_UPLOAD_PATH;
	}
	
	public static String getDOMAIN_NAME(){
		if (DOMAIN_NAME.equals("")) initDDProperties();
		return DOMAIN_NAME;
	}
	
	public static String getProjectUrl(){
		return getDOMAIN_NAME() + "/" + getPROJECT_NAME() + "/";
	}
	
	public static String getPROJECT_NAME(){
		if (PROJECT_NAME.equals("")) initDDProperties();
		return PROJECT_NAME;
	}
	
	public static String getLOGIN_URL(){
		if (LOGIN_URL.equals("")) initDDProperties();
		return LOGIN_URL;
	}
	
	
	public static String getDefaultAvatarUrl(){
		if (DefaultAvatarUrl.equals("")) initDDProperties();
		return DefaultAvatarUrl;
	}
	
	
	public static String getTemplate_SubmitOrder(){
		if (Template_SubmitOrder.equals("")) initDDProperties();
		return Template_SubmitOrder;
	}
	
	public static String getTemplate_SubmitEvaluate(){
		if (Template_SubmitEvaluate.equals("")) initDDProperties();
		return Template_SubmitEvaluate;
	}
	private static void initDDProperties() {
		// TODO Auto-generated method stub
		Properties properties = new Properties();
		String path = Thread.currentThread().getContextClassLoader().getResource("lzz.properties").getPath();
		try {
			properties.load(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		APPID = properties.getProperty("APPID");
		APP_SECRET = properties.getProperty("APP_SECRET");
		PAYKEY = properties.getProperty("PAYKEY");
		MCHID = properties.getProperty("MCHID");
		PAYNOTIFYURL = properties.getProperty("PAYNOTIFYURL");
		FILE_UPLOAD_PATH = properties.getProperty("FILE_UPLOAD_PATH");
		DOMAIN_NAME = properties.getProperty("DOMAIN_NAME");
		PROJECT_NAME = properties.getProperty("PROJECT_NAME");
		LOGIN_URL = properties.getProperty("LOGIN_URL");
		DefaultAvatarUrl = properties.getProperty("DefaultAvatarUrl");
		Template_SubmitOrder = properties.getProperty("Template_SubmitOrder");
		Template_SubmitEvaluate = properties.getProperty("Template_SubmitEvaluate");
	}
}
