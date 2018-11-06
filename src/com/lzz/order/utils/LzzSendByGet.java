package com.lzz.order.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LzzSendByGet {
	public static String sendByGet(String url, List<String> par_name, List<String> par_value)
	{
		String response = "";
		if(null==par_name) par_name = new ArrayList<String>();
		BufferedReader in = null;
//		System.out.println("get from " + url);
		
		String complete_url = url;
		assert par_name.size()==par_value.size();
		
		for(int i=0; i<par_name.size(); i++)
		{
			if(i==0)
			{
				complete_url += "?";
			}
			else
			{
				complete_url += "&";
			}
			complete_url += par_name.get(i);
			complete_url += "=";
			complete_url += par_value.get(i);
		}
		
		System.out.println("complete_url:" + complete_url);
		try{
			URL realUrl = new URL(complete_url);
			// 打开�?  URL 之间的连�?
			URLConnection connection = realUrl.openConnection();
			
			// 设置通用的请求属�?
			connection.setRequestProperty("accept", "*/*");
	        connection.setRequestProperty("connection", "Keep-Alive");
	        connection.setRequestProperty("user-agent",
	                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	
	        // 建立实际的链接
	        connection.connect();
	        
	        // 获取响应头字段
	        Map<String, List<String>> map = connection.getHeaderFields();
	        // 遍历�?有的响应头字�?
//	        for (String key : map.keySet()) 
//	        {
//	            System.out.println(key + "--->" + map.get(key));
//	        }
	        
	        // 定义 BufferedReader输入流来读取URL的响�?
	        //in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
	
	        String line;
	        while ((line = in.readLine()) != null) 
	        {
	            response += line;
	        }
		}catch(Exception e){
			System.out.println("发送get请求异常" + e);
			response = "ERROR";
            e.printStackTrace();
		}finally{
			try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
		}
		
		return response;
	}
}
