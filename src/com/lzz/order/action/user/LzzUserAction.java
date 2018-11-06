package com.lzz.order.action.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Transaction;

import com.lzz.order.action.LzzBaseManagerAction;
import com.lzz.order.factory.LzzFactory;
import com.lzz.order.mgr.LzzCacheMgr;
import com.lzz.order.mgr.LzzDictionaryMgr;
import com.lzz.order.mgr.LzzFileMgr;
import com.lzz.order.mgr.LzzGoodMgr;
import com.lzz.order.mgr.LzzSMSVerifyMgr;
import com.lzz.order.mgr.LzzUserMgr;
import com.lzz.order.pojo.LzzGood;
import com.lzz.order.pojo.LzzOrderInfo;
import com.lzz.order.pojo.LzzUserAddress;
import com.lzz.order.pojo.LzzUserGoodInterested;
import com.lzz.order.pojo.LzzUserGoodViewed;
import com.lzz.order.service.LzzGoodService;
import com.lzz.order.service.LzzOrderService;
import com.lzz.order.service.LzzUserService;

public class LzzUserAction extends LzzBaseManagerAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1164649127751088344L;
	
	/**
	 * 获取用户地址列表
	 * @return
	 */
	public String getAddresses(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");
		
		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		
		try {
			JSONArray addresses = LzzUserMgr.getUserAddressList(curUserId);
			rsl.put("addressList", addresses);
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
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 * 新增或者修改地址信息
	 * @return
	 */
	public String subData;
	public String addOrUpdateAddress(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");
		
		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		
		try {
			subData = new String(subData.getBytes("iso-8859-1"),"utf-8");
			JSONObject data = JSONObject.fromObject(subData);
			String id = data.getString("id");
			String name = data.getString("name");
			String phone = data.getString("phone");
			JSONArray area = data.getJSONArray("area");
			String detail = data.getString("detail");
			String usualFlag = data.getString("usualFlag");
			
			LzzUserMgr.addOrUpdateAddress(curUserId, id, name, phone, area, detail, usualFlag);
			
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
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 * 删除地址记录
	 */
	public String addressId;
	public String deleteAddress(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");
		
		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		
		try {
			LzzUserMgr.deleteAddress(curUserId, addressId);
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
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public String getAddressInfo(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");
		
		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		
		try {
			LzzUserAddress address = LzzUserService.self().getLzzUserAddressById(addressId);
			JSONObject addr = new JSONObject();
			JSONArray area = new JSONArray();
			if(null!=address){
				addr.put("id", address.getId());
				addr.put("name", address.getPerson());
				addr.put("phone", address.getPhone());
				
				area.add(address.getProvince());
				area.add(address.getCity());
				area.add(address.getCounty());
				addr.put("area", area);
				String area_str = address.getProvince()+address.getCity()+address.getCounty();
				addr.put("detail", address.getDetail().replaceAll(area_str, ""));
				addr.put("usualFlag", address.getUsualFlag());
			}
			
			rsl.put("addressInfo", addr);
			
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
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public String setAddressUsual(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");
		
		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		
		try {
			LzzUserAddress address = LzzUserService.self().getLzzUserAddressById(addressId);
			LzzUserAddress usual = LzzUserService.self().getLzzUserAddressByUserIdAndUsualFlag(curUserId, "1");
			
			if(null!=usual 
					&& null!=address
					&& !usual.getId().equals(address.getId())){
				usual.setUsualFlag("0");
				LzzUserService.self().updateLzzUserAddress(usual);
			}
			
			if(null!=address){
				address.setUsualFlag("1");
				LzzUserService.self().updateLzzUserAddress(address);
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
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public String userInfo;
	/**
	 * 新增或者修改地址信息
	 * @return
	 */
	public String setUserInfo(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");
		
		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		
		try {
			userInfo = new String(userInfo.getBytes("iso-8859-1"),"utf-8");
			JSONObject info= JSONObject.fromObject(userInfo);
			String nickName = info.getString("nickName");
			String avatarUrl = info.getString("avatarUrl");
			curUser.setNick_name(nickName);
			curUser.setAvatarUrl(avatarUrl);
			LzzUserService.self().updateLzzUser(curUser);
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
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public String getMeInitInfo(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");
		
		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		
		try {
			JSONObject initInfo = new JSONObject();
			
			initInfo.put("avatarUrl", curUser.getAvatarUrl());
			String cur_int = curUser.getCur_integration();
			if(null==cur_int || "".equals(cur_int)){
				cur_int = "0";
			}
			String total_int = curUser.getTotal_integration();
			if(null==total_int || "".equals(total_int)){
				total_int = "0";
			}
			initInfo.put("curIntegration", cur_int);
			initInfo.put("curUserLevel", LzzDictionaryMgr.getValueById(curUser.getLevel()));
			String typical_img = LzzDictionaryMgr.getDicTypicalImgById(curUser.getLevel());
			initInfo.put("curUserLevelTypical", typical_img);
			initInfo.put("interestedCount", LzzUserMgr.getInterestedCount(curUserId));
			initInfo.put("viewGoodCount", LzzUserMgr.getViewGoodCount(curUserId));
			initInfo.put("validateFlag", curUser.getValidate_flag());
			String next_int = LzzUserMgr.getUserNextLevelIntegration(curUserId);
			if(null==next_int){
				initInfo.put("nextLevelIntegration",  "");
			}else{
				initInfo.put("nextLevelIntegration",  Integer.valueOf(next_int)-Integer.valueOf(total_int));
			}
			initInfo.put("nextLevelName", LzzUserMgr.getUserNextLevelName(curUserId));
			initInfo.put("phone", curUser.getPhone());
			
			JSONObject usualAddress = LzzUserMgr.getUserUsualAddress(curUserId);
			initInfo.put("usualAddress", usualAddress);
			
			rsl.put("initInfo", initInfo);
			
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
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public String sendVerifyCode(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");
		
		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		
		try {
			String rslt = LzzSMSVerifyMgr.sendVerifyCode(phone);
			if("timesover".equals(rslt)){
				rsl.put("iserror", true);
				rsl.put("errormsg", "今日获取次数用尽");
			}else if(!"success".equals(rslt)){
				rsl.put("iserror", true);
				rsl.put("errormsg", "请勿频繁获取");
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
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public String phone;
	public String code;
	public String subBind(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");
		
		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		
		try {
			System.out.println(phone + ":" + code);
			if(LzzSMSVerifyMgr.checkVerify(phone, code)){
				curUser.setPhone(phone);
				curUser.setValidate_flag("1");
				LzzUserService.self().updateLzzUser(curUser);
			}else{
				rsl.put("iserror", true);
				rsl.put("errormsg", "验证码校验失败！");
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
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public String getGoodsInterested() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzUserGoodInterested> ints = LzzUserService.self().getLzzUserGoodInterestedListByUserId(curUserId);
			
			JSONArray goods = new JSONArray();
			for(LzzUserGoodInterested sin : ints){
				JSONObject obj = LzzGoodMgr.getGoodJSONInfoById(curUserId, sin.getGoodId());
				goods.add(obj);
			}
			
			rsl.put("goods", goods);
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
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	public String getGoodsViewed() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzUserGoodViewed> views = LzzUserService.self().getLzzUserGoodViewedListByUserId(curUserId);
			
			JSONArray goods = new JSONArray();
			for(LzzUserGoodViewed sin : views){
				JSONObject obj = LzzGoodMgr.getGoodJSONInfoById(curUserId, sin.getGoodId());
				goods.add(obj);
			}
			
			rsl.put("goods", goods);
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
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
}
