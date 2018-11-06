package com.lzz.order.mgr;


import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateUtils;

import com.lzz.order.localpojo.SMSparam;
import com.lzz.order.localpojo.WeChatVerification;
import com.lzz.order.utils.LzzDateUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumQueryRequest;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumQueryResponse;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class LzzSMSVerifyMgr {
    //app_key 和  app_secret 需要置换
	public static final String SMURL = "http://gw.api.taobao.com/router/rest";
	public static final String SMAPPKey = "24726682";
	public static final String SMAPPSecret = "bd5b736c272503fbfd836474bfb78e8d";
	public static final String SMSmsFreeSignName = "零食天空";
	public static final String SMSmsTemplateCode = "SMS_116505061";
    public static HashMap<String, WeChatVerification> smVerify = new HashMap<String, WeChatVerification>();
    public static int smValidDur = 10; // 单位分钟

    //请求参数
    //Extend:公共回传参数，在“消息返回”中会透传回该参数；举例：用户可以传入自己下级的会员ID，在消息返回时，该会员ID会包含在内，用户可以根据该会员ID识别是哪位会员使用了你的应用
    //SmsFreeSignName:短信签名，传入的短信签名必须是在阿里大于“管理中心-短信签名管理”中的可用签名。如“阿里大于”已在短信签名管理中通过审核，则可传入”阿里大于“（传参时去掉引号）作为短信签名。短信效果示例：【阿里大于】欢迎使用阿里大于服务。
    //tel:短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。群发短信需传入多个号码，以英文逗号分隔，一次调用最多传入200个号码。示例：18600000000,13911111111,13322222222
    //SmsTemplateCode:短信模板ID，传入的模板必须是在阿里大于“管理中心-短信模板管理”中的可用模板。示例：SMS_585014

    //以下两个参数的参数值
    //SmsFreeSignName：桃李;
    //SmsTemplateCode:SMS_36370157  验证码
    //                SMS_36265228  短信消息    ${name}用户你好，感谢你使用${product}.
    //                SMS_37685246  短信通知(验证中)    通知：${content}.

    public static String sendSMS(String Extend,String SmsFreeSignName,String tel,String SmsTemplateCode,List<SMSparam> SMSjson){

        updateVerifyHash();

        if(smVerify.containsKey(tel)){
            WeChatVerification ver = smVerify.get(tel);
            if(ver.isValid()){
                return "valid";
            }
            else{
                if(ver.getTimes() > 5){
                    return "timesover";
                }
            }
        }

        String json=null;
        String code = null;

        TaobaoClient client = new DefaultTaobaoClient(SMURL, SMAPPKey, SMAPPSecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();

        json ="{";
        for(SMSparam param:SMSjson){
            //判断是否为最后一句
            if("code".equals(param.getName())){
                code="{\"code\":\""+param.getValue()+"\",\"tel\":\""+tel+"\"}";
            }
        }
        json +="}";

        req.setExtend(Extend);
        req.setSmsType("normal");
        req.setSmsFreeSignName(SmsFreeSignName);
        req.setSmsParamString(json);
        req.setRecNum(tel);
        req.setSmsTemplateCode(SmsTemplateCode);
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        try {
            rsp = client.execute(req);
            System.out.println(rsp.getBody());
        } catch (ApiException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        //update smVerify info
        if(smVerify.containsKey(tel)){
            smVerify.get(tel).times++;
            smVerify.get(tel).code = code;
        }else{
            WeChatVerification ver = new WeChatVerification();
            ver.code = code;
            ver.times = 1;
            ver.valid_date = LzzDateUtil.delayMinute(new Date(), smValidDur);
        }

        return code;
    }

    public static String sendVerifyCode(String tel){
    	Date create_date = new Date();
        String code = "";
        
        if(smVerify.containsKey(tel)){
        	if(smVerify.get(tel).isValid()){
        		code = smVerify.get(tel).getCode();
        		smVerify.get(tel).times++;
        	}
        	
        	if(smVerify.get(tel).times>5){
        		if(DateUtils.isSameDay(create_date, smVerify.get(tel).create_date)){
        			return "timesover";
        		}
        		
        		smVerify.remove(tel);
        		code = getVerifyCode();
        	}
        }
        
        if(code.equals("")){
            code = getVerifyCode();
        }

        TaobaoClient client = new DefaultTaobaoClient(SMURL, SMAPPKey, SMAPPSecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName(SMSmsFreeSignName);
        req.setSmsParamString("{code:'" + code + "'}");
        req.setRecNum(tel);
        req.setSmsTemplateCode(SMSmsTemplateCode);
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        try {
            rsp = client.execute(req);
            System.out.println(rsp.getBody());
            JSONObject obj = JSONObject.fromObject(rsp.getBody());
            if(obj.containsKey("error_response")){
            	return (String) obj.getJSONObject("error_response").get("sub_code");
            }
        } catch (ApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //update smVerify info
        if(smVerify.containsKey(tel)){
            smVerify.get(tel).times++;
            smVerify.get(tel).code = code;
            if(!DateUtils.isSameDay(smVerify.get(tel).create_date, create_date)){
            	smVerify.get(tel).create_date = create_date;
            }
        }else{
            WeChatVerification ver = new WeChatVerification();
            ver.code = code;
            ver.times = 1;
            ver.valid_date = LzzDateUtil.delayMinute(create_date, smValidDur);
            ver.create_date = create_date;
            smVerify.put(tel, ver);
        }

        return "success";
    }
    //验证码验证
    //格式：{"code":"XXX","tel":"XXX"}
    public boolean check(String code,String reCode){
        return code.equals(reCode);
    }


    public static boolean checkVerify(String tel, String code){
        return smVerify.containsKey(tel) && smVerify.get(tel).isValid() && smVerify.get(tel).getCode().equals(code);
    }

    //参数说明
    //bizId:短信发送流水
    //recNum:短信接收号码
    //queryDate:短信发送日期，支持近30天记录查询，格式yyyyMMdd

    //查询信息记录
    public static String inquirySMS(String bizId,String recNum,String queryDate){
        String rslt="";
        TaobaoClient client = new DefaultTaobaoClient(SMURL, SMAPPKey, SMAPPSecret);
        AlibabaAliqinFcSmsNumQueryRequest req = new AlibabaAliqinFcSmsNumQueryRequest();
        req.setBizId("");
        req.setRecNum(recNum);
        req.setQueryDate(queryDate);
        req.setCurrentPage(1L);
        req.setPageSize(10L); 		//分页参数，每页数量。最大值50
        AlibabaAliqinFcSmsNumQueryResponse rsp = null;
        try {
            rsp = client.execute(req);
            rslt=rsp.getBody();
            System.out.println(rslt);
        } catch (ApiException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return rslt;
    }

    private static void updateVerifyHash(){
        if(smVerify.size()==0) return;

        Iterator<String> iter = smVerify.keySet().iterator();
        while(iter.hasNext()){
            String key = (String) iter.next();
            WeChatVerification ver = smVerify.get(key);
            if(!ver.isValid()){
                smVerify.remove(key);
            }
        }
    }

    public static String getVerifyCode(){
        int code = (int)((Math.random()*9+1)*100000);
        return Integer.toString(code);
    }
    
    public static void main(String[] args) {
    	sendVerifyCode("18501747274");
	}
}
