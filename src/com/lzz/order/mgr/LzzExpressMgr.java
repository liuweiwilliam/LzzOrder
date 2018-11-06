package com.lzz.order.mgr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import com.lzz.order.pojo.LzzExpressCompany;
import com.lzz.order.pojo.LzzOrder;
import com.lzz.order.pojo.LzzUserAddress;
import com.lzz.order.service.LzzOrderService;
import com.lzz.order.service.LzzUserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LzzExpressMgr {
	private static final String ExpressCompany_STO = "STO"; //申通公司编码
	private static final String ExpressCompany_YTO = "YTO"; //圆通公司编码
	private static final String ExpressCompany_ZTO = "ZTO"; //中通公司编码
	private static final String ExpressCompany_SF = "SF"; //顺丰公司编码
	private static final String EBusinessID = "1314704"; //快递鸟商家ID
	private static final String AppKey = "0f50483f-44d4-4cea-9a99-25b5b45cf031"; //快递鸟商家APPKEY
	private static final String ReqURLInTime="http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";	
	private static final String ReqURLWithCallBack="http://testapi.kdniao.cc:8081/api/dist";	
	private static final String ReqURLCreateOrder="http://testapi.kdniao.cc:8081/api/oorderservice";	
	
	
	/**
	 * 获取快递物流详情(即时信息查询)
	 * @param company_code 物流公司编码
	 * @param express_num 快递单号
	 * @return 格式[{"AcceptStation":"","AcceptTime":""}] 按照AcceptTime时间升序
	 * @throws Exception
	 */
	public static JSONArray queryExpressInfoInTime(String company_code, String express_num) throws Exception{
		String requestData= "{'OrderCode':'','ShipperCode':'" + company_code + "','LogisticCode':'" + express_num + "'}";
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("RequestData", LzzEncodeMgr.urlEncoder(requestData, "UTF-8"));
		params.put("EBusinessID", EBusinessID);
		params.put("RequestType", "1002");
		String dataSign=encrypt(requestData, AppKey, "UTF-8");
		params.put("DataSign", LzzEncodeMgr.urlEncoder(dataSign, "UTF-8"));
		params.put("DataType", "2");
		
		String result_str = sendPost(ReqURLInTime, params);	
		//根据业务处理返回的信息......
		if(null==result_str
				|| "".equals(result_str)){
			return new JSONArray();
		}
		JSONObject obj = JSONObject.fromObject(result_str);
		
		return obj.getJSONArray("Traces");
	}
	
	/**
	 * 物流追踪API
	 * @param company_code 物流公司编码
	 * @param express_num 快递单号
	 */
	public static JSONArray queryExpressInfoWithCallBackUrl(String company_code, String express_num) throws Exception{
		String requestData= "{'OrderCode':'','ShipperCode':'" + company_code + "','LogisticCode':'" + express_num + "'}";
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("RequestData", LzzEncodeMgr.urlEncoder(requestData, "UTF-8"));
		params.put("EBusinessID", EBusinessID);
		params.put("RequestType", "1008");
		String dataSign=encrypt(requestData, AppKey, "UTF-8");
		params.put("DataSign", LzzEncodeMgr.urlEncoder(dataSign, "UTF-8"));
		params.put("DataType", "2-json");
		
		String result_str = sendPost(ReqURLWithCallBack, params);
		//根据业务处理返回的信息......
		if(null==result_str
				|| "".equals(result_str)){
			return new JSONArray();
		}
		JSONObject obj = JSONObject.fromObject(result_str);
		
		return obj.getJSONArray("Traces");
	}
	
	public static boolean createExpressOrder(String order_id) throws Exception{
		LzzOrder order = LzzOrderService.self().getLzzOrderById(order_id);
		//String company_code = LzzExpressCompanyMgr.getDefaultExpressCmpCode();
		String company_code = ExpressCompany_SF;
		String send_company = "刘小五食品批发";
		String send_name = "刘小五";
		String send_tel = "13637182703";
		String send_province = "安徽省";
		String send_city = "宿州市";
		String send_area = "埇桥区";
		String send_address = "东仙桥";
		
		LzzUserAddress address = LzzOrderMgr.getOrderAddress(order_id);
		
		String receive_name = address.getPerson();
		String receive_tel = address.getPhone();
		String receive_province = address.getProvince();
		String receive_city = address.getCity();
		String receive_area = address.getCounty();
		String receive_address = address.getDetail();
		receive_address = "砀山县实验学校";
		
		String good_name = "111";
		String good_count = "1";
		String good_weight = "1";
		
		String total_count = "1";
		String total_weight = "1";
		String total_size = "1";
		
		return createOrderOnLine(order_id, company_code, send_company, send_name, send_tel, send_province, send_city, send_area, send_address, receive_name, receive_tel, receive_province, receive_city, receive_area, receive_address, good_name, good_count, good_weight, total_weight, total_count, total_size);
	}
	
	/**
	 * 预约取件API
	 * @param company_code 物流公司编码
	 * @param express_num 快递单号
	 */
	private static boolean createOrderOnLine(
			String order_id,
			String company_code, 
			String send_company,
			String send_name,
			String send_tel,
			String send_province,
			String send_city,
			String send_area,
			String send_address,
			String receive_name,
			String receive_tel,
			String receive_province,
			String receive_city,
			String receive_area,
			String receive_address,
			String good_name,
			String good_count,
			String good_weight,
			String total_weight,
			String total_count,
			String total_size) throws Exception{
		String requestData= "{'OrderCode':'" + order_id + "'," +
                "'ShipperCode':'"+ company_code +"'," +
                "'PayType':3," +
                "'ExpType':1," +
                "'Sender':" +
                "{" +
                "'Company':'" + send_company + "','Name':'" + send_name + "','Mobile':'" + send_tel + "','ProvinceName':'" + send_province + "','CityName':'" + send_city + "','ExpAreaName':'" + send_area + "','Address':'" + send_address + 
                "'}," +
                "'Receiver':" +
                "{" +
                "'Name':'" + receive_name + "','Mobile':'" + receive_tel + "','ProvinceName':'" + receive_province + "','CityName':'" + receive_city + "','ExpAreaName':'" + receive_area + "','Address':'" + receive_address + 
                "'}," +
                "'Commodity':" +
                "[{" +
                "'GoodsName':'" + good_name + "','Goodsquantity': " + good_count + " ,'GoodsWeight': " + good_weight + 
                "}]," +
                "'Weight':" + total_weight + "," +
                "'Quantity':" + total_count + "," +
                "'Volume':" + total_size + "," +
                "'Remark':'小心轻放'" +
                "}";

		Map<String, String> params = new HashMap<String, String>();
		params.put("RequestData", LzzEncodeMgr.urlEncoder(requestData, "UTF-8"));
		params.put("EBusinessID", EBusinessID);
		params.put("RequestType", "1001");
		String dataSign=encrypt(requestData, AppKey, "UTF-8");
		params.put("DataSign", LzzEncodeMgr.urlEncoder(dataSign, "UTF-8"));
		params.put("DataType", "2");
		
		String result_str = sendPost(ReqURLCreateOrder, params);
		System.out.println("result_str : " + result_str);
		//根据业务处理返回的信息......
		if(null==result_str
				|| "".equals(result_str)){
			return false;
		}
		try{
			JSONObject obj = JSONObject.fromObject(result_str);
			return obj.getBoolean("Success");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
     * 电商Sign签名生成
     * @param content 内容   
     * @param keyValue Appkey  
     * @param charset 编码方式
	 * @throws UnsupportedEncodingException ,Exception
	 * @return DataSign签名
     */
	private static String encrypt (String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception
	{
		if (keyValue != null)
		{
			return LzzEncodeMgr.base64(LzzEncodeMgr.MD5(content + keyValue, charset), charset);
		}
		return LzzEncodeMgr.base64(LzzEncodeMgr.MD5(content, charset), charset);
	}
	
	 /**
     * 向指定 URL 发送POST方法的请求     
     * @param url 发送请求的 URL    
     * @param params 请求的参数集合     
     * @return 远程资源的响应结果
     */
	private static String sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;        
        StringBuilder result = new StringBuilder(); 
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数            
            if (params != null) {
		          StringBuilder param = new StringBuilder(); 
		          for (Map.Entry<String, String> entry : params.entrySet()) {
		        	  if(param.length()>0){
		        		  param.append("&");
		        	  }	        	  
		        	  param.append(entry.getKey());
		        	  param.append("=");
		        	  param.append(entry.getValue());		        	  
		        	  //System.out.println(entry.getKey()+":"+entry.getValue());
		          }
		          //System.out.println("param:"+param.toString());
		          out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {            
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
	
	public static void main(String[] args) throws Exception {
		createExpressOrder("1010013");
	}
}
