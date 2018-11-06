
package com.lzz.order.action.entering;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Transaction;

import com.lzz.order.action.LzzBaseManagerAction;
import com.lzz.order.factory.LzzFactory;
import com.lzz.order.mgr.LzzIDMgr;

import com.lzz.order.pojo.LzzAfterSale;
import com.lzz.order.service.LzzAfterSaleService;

import com.lzz.order.pojo.LzzCart;
import com.lzz.order.service.LzzCartService;

import com.lzz.order.pojo.LzzCoupon;
import com.lzz.order.service.LzzCouponService;

import com.lzz.order.pojo.LzzDictionary;
import com.lzz.order.service.LzzDictionaryService;

import com.lzz.order.pojo.LzzFileGroup;
import com.lzz.order.service.LzzFileService;

import com.lzz.order.pojo.LzzFileInfo;
import com.lzz.order.service.LzzFileService;

import com.lzz.order.pojo.LzzGood;
import com.lzz.order.service.LzzGoodService;

import com.lzz.order.pojo.LzzGoodCategory;
import com.lzz.order.service.LzzGoodService;

import com.lzz.order.pojo.LzzGoodEvaluate;
import com.lzz.order.service.LzzGoodService;

import com.lzz.order.pojo.LzzGoodFormat;
import com.lzz.order.service.LzzGoodService;

import com.lzz.order.pojo.LzzGoodTaste;
import com.lzz.order.service.LzzGoodService;

import com.lzz.order.pojo.LzzOrder;
import com.lzz.order.service.LzzOrderService;

import com.lzz.order.pojo.LzzOrderInfo;
import com.lzz.order.service.LzzOrderService;

import com.lzz.order.pojo.LzzOrderSendMode;
import com.lzz.order.service.LzzOrderService;

import com.lzz.order.pojo.LzzUser;
import com.lzz.order.service.LzzUserService;

import com.lzz.order.pojo.LzzUserAddress;
import com.lzz.order.service.LzzUserService;

import com.lzz.order.pojo.LzzUserCoupon;
import com.lzz.order.service.LzzUserService;

import com.lzz.order.pojo.LzzUserTaste;
import com.lzz.order.service.LzzUserService;

import com.lzz.order.pojo.LzzExpressCompany;
import com.lzz.order.service.LzzExpressCompanyService;

import com.lzz.order.pojo.LzzShopEvaluate;
import com.lzz.order.service.LzzShopService;

import com.lzz.order.pojo.LzzCategory;
import com.lzz.order.service.LzzGoodService;

import com.lzz.order.pojo.LzzUserGoodInterested;
import com.lzz.order.service.LzzUserService;

import com.lzz.order.pojo.LzzUserSession;
import com.lzz.order.service.LzzUserService;

import com.lzz.order.pojo.LzzUserGoodViewed;
import com.lzz.order.service.LzzUserService;

import com.lzz.order.pojo.LzzIndexDisplay;
import com.lzz.order.service.LzzShopService;


import com.lzz.order.utils.lzzClassUtils;

public class LzzEntering extends LzzBaseManagerAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String subData;
	public String id;
	public String fieldName;
	public String searchValue;
	public String addOrUpdateLzzAfterSale() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzAfterSaleData_obj = JSONObject.fromObject(subData);
			LzzAfterSale obj = null;
			if(LzzAfterSaleData_obj.getString("id").equals("")){
				obj = new LzzAfterSale();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzAfterSaleService.self().getLzzAfterSaleById(LzzAfterSaleData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzAfterSaleData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzAfterSaleData_obj.getString("id").equals("")){
				LzzAfterSaleService.self().saveLzzAfterSale(obj);
			}else{
				LzzAfterSaleService.self().updateLzzAfterSale(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzAfterSale(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzAfterSaleService.self().delLzzAfterSaleById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzAfterSale(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzAfterSale());
			}else{
				LzzAfterSale sin = LzzAfterSaleService.self().getLzzAfterSaleById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzAfterSale.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzAfterSale() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzAfterSale> all = LzzAfterSaleService.self().getAllLzzAfterSaleIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzAfterSale.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzCart() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzCartData_obj = JSONObject.fromObject(subData);
			LzzCart obj = null;
			if(LzzCartData_obj.getString("id").equals("")){
				obj = new LzzCart();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzCartService.self().getLzzCartById(LzzCartData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzCartData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzCartData_obj.getString("id").equals("")){
				LzzCartService.self().saveLzzCart(obj);
			}else{
				LzzCartService.self().updateLzzCart(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzCart(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzCartService.self().delLzzCartById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzCart(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzCart());
			}else{
				LzzCart sin = LzzCartService.self().getLzzCartById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzCart.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzCart() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzCart> all = LzzCartService.self().getAllLzzCartIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzCart.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzCoupon() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzCouponData_obj = JSONObject.fromObject(subData);
			LzzCoupon obj = null;
			if(LzzCouponData_obj.getString("id").equals("")){
				obj = new LzzCoupon();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzCouponService.self().getLzzCouponById(LzzCouponData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzCouponData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzCouponData_obj.getString("id").equals("")){
				LzzCouponService.self().saveLzzCoupon(obj);
			}else{
				LzzCouponService.self().updateLzzCoupon(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzCoupon(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzCouponService.self().delLzzCouponById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzCoupon(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzCoupon());
			}else{
				LzzCoupon sin = LzzCouponService.self().getLzzCouponById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzCoupon.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzCoupon() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzCoupon> all = LzzCouponService.self().getAllLzzCouponIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzCoupon.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzDictionary() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzDictionaryData_obj = JSONObject.fromObject(subData);
			LzzDictionary obj = null;
			if(LzzDictionaryData_obj.getString("id").equals("")){
				obj = new LzzDictionary();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzDictionaryService.self().getLzzDictionaryById(LzzDictionaryData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzDictionaryData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzDictionaryData_obj.getString("id").equals("")){
				LzzDictionaryService.self().saveLzzDictionary(obj);
			}else{
				LzzDictionaryService.self().updateLzzDictionary(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzDictionary(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzDictionaryService.self().delLzzDictionaryById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzDictionary(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzDictionary());
			}else{
				LzzDictionary sin = LzzDictionaryService.self().getLzzDictionaryById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzDictionary.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzDictionary() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzDictionary> all = LzzDictionaryService.self().getAllLzzDictionaryIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzDictionary.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzFileGroup() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzFileGroupData_obj = JSONObject.fromObject(subData);
			LzzFileGroup obj = null;
			if(LzzFileGroupData_obj.getString("id").equals("")){
				obj = new LzzFileGroup();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzFileService.self().getLzzFileGroupById(LzzFileGroupData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzFileGroupData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzFileGroupData_obj.getString("id").equals("")){
				LzzFileService.self().saveLzzFileGroup(obj);
			}else{
				LzzFileService.self().updateLzzFileGroup(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzFileGroup(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzFileService.self().delLzzFileGroupById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzFileGroup(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzFileGroup());
			}else{
				LzzFileGroup sin = LzzFileService.self().getLzzFileGroupById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzFileGroup.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzFileGroup() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzFileGroup> all = LzzFileService.self().getAllLzzFileGroupIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzFileGroup.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzFileInfo() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzFileInfoData_obj = JSONObject.fromObject(subData);
			LzzFileInfo obj = null;
			if(LzzFileInfoData_obj.getString("id").equals("")){
				obj = new LzzFileInfo();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzFileService.self().getLzzFileInfoById(LzzFileInfoData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzFileInfoData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzFileInfoData_obj.getString("id").equals("")){
				LzzFileService.self().saveLzzFileInfo(obj);
			}else{
				LzzFileService.self().updateLzzFileInfo(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzFileInfo(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzFileService.self().delLzzFileInfoById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzFileInfo(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzFileInfo());
			}else{
				LzzFileInfo sin = LzzFileService.self().getLzzFileInfoById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzFileInfo.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzFileInfo() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzFileInfo> all = LzzFileService.self().getAllLzzFileInfoIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzFileInfo.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzGood() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzGoodData_obj = JSONObject.fromObject(subData);
			LzzGood obj = null;
			if(LzzGoodData_obj.getString("id").equals("")){
				obj = new LzzGood();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzGoodService.self().getLzzGoodById(LzzGoodData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzGoodData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzGoodData_obj.getString("id").equals("")){
				LzzGoodService.self().saveLzzGood(obj);
			}else{
				LzzGoodService.self().updateLzzGood(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzGood(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzGoodService.self().delLzzGoodById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzGood(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzGood());
			}else{
				LzzGood sin = LzzGoodService.self().getLzzGoodById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzGood.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzGood() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzGood> all = LzzGoodService.self().getAllLzzGoodIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzGood.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzGoodCategory() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzGoodCategoryData_obj = JSONObject.fromObject(subData);
			LzzGoodCategory obj = null;
			if(LzzGoodCategoryData_obj.getString("id").equals("")){
				obj = new LzzGoodCategory();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzGoodService.self().getLzzGoodCategoryById(LzzGoodCategoryData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzGoodCategoryData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzGoodCategoryData_obj.getString("id").equals("")){
				LzzGoodService.self().saveLzzGoodCategory(obj);
			}else{
				LzzGoodService.self().updateLzzGoodCategory(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzGoodCategory(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzGoodService.self().delLzzGoodCategoryById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzGoodCategory(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzGoodCategory());
			}else{
				LzzGoodCategory sin = LzzGoodService.self().getLzzGoodCategoryById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzGoodCategory.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzGoodCategory() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzGoodCategory> all = LzzGoodService.self().getAllLzzGoodCategoryIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzGoodCategory.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzGoodEvaluate() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzGoodEvaluateData_obj = JSONObject.fromObject(subData);
			LzzGoodEvaluate obj = null;
			if(LzzGoodEvaluateData_obj.getString("id").equals("")){
				obj = new LzzGoodEvaluate();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzGoodService.self().getLzzGoodEvaluateById(LzzGoodEvaluateData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzGoodEvaluateData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzGoodEvaluateData_obj.getString("id").equals("")){
				LzzGoodService.self().saveLzzGoodEvaluate(obj);
			}else{
				LzzGoodService.self().updateLzzGoodEvaluate(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzGoodEvaluate(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzGoodService.self().delLzzGoodEvaluateById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzGoodEvaluate(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzGoodEvaluate());
			}else{
				LzzGoodEvaluate sin = LzzGoodService.self().getLzzGoodEvaluateById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzGoodEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzGoodEvaluate() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzGoodEvaluate> all = LzzGoodService.self().getAllLzzGoodEvaluateIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzGoodEvaluate.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzGoodFormat() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzGoodFormatData_obj = JSONObject.fromObject(subData);
			LzzGoodFormat obj = null;
			if(LzzGoodFormatData_obj.getString("id").equals("")){
				obj = new LzzGoodFormat();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzGoodService.self().getLzzGoodFormatById(LzzGoodFormatData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzGoodFormatData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzGoodFormatData_obj.getString("id").equals("")){
				LzzGoodService.self().saveLzzGoodFormat(obj);
			}else{
				LzzGoodService.self().updateLzzGoodFormat(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzGoodFormat(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzGoodService.self().delLzzGoodFormatById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzGoodFormat(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzGoodFormat());
			}else{
				LzzGoodFormat sin = LzzGoodService.self().getLzzGoodFormatById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzGoodFormat.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzGoodFormat() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzGoodFormat> all = LzzGoodService.self().getAllLzzGoodFormatIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzGoodFormat.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzGoodTaste() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzGoodTasteData_obj = JSONObject.fromObject(subData);
			LzzGoodTaste obj = null;
			if(LzzGoodTasteData_obj.getString("id").equals("")){
				obj = new LzzGoodTaste();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzGoodService.self().getLzzGoodTasteById(LzzGoodTasteData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzGoodTasteData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzGoodTasteData_obj.getString("id").equals("")){
				LzzGoodService.self().saveLzzGoodTaste(obj);
			}else{
				LzzGoodService.self().updateLzzGoodTaste(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzGoodTaste(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzGoodService.self().delLzzGoodTasteById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzGoodTaste(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzGoodTaste());
			}else{
				LzzGoodTaste sin = LzzGoodService.self().getLzzGoodTasteById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzGoodTaste.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzGoodTaste() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzGoodTaste> all = LzzGoodService.self().getAllLzzGoodTasteIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzGoodTaste.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzOrder() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzOrderData_obj = JSONObject.fromObject(subData);
			LzzOrder obj = null;
			if(LzzOrderData_obj.getString("id").equals("")){
				obj = new LzzOrder();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzOrderService.self().getLzzOrderById(LzzOrderData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzOrderData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzOrderData_obj.getString("id").equals("")){
				LzzOrderService.self().saveLzzOrder(obj);
			}else{
				LzzOrderService.self().updateLzzOrder(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzOrder(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzOrderService.self().delLzzOrderById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzOrder(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzOrder());
			}else{
				LzzOrder sin = LzzOrderService.self().getLzzOrderById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzOrder.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzOrder() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzOrder> all = LzzOrderService.self().getAllLzzOrderIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzOrder.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzOrderInfo() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzOrderInfoData_obj = JSONObject.fromObject(subData);
			LzzOrderInfo obj = null;
			if(LzzOrderInfoData_obj.getString("id").equals("")){
				obj = new LzzOrderInfo();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzOrderService.self().getLzzOrderInfoById(LzzOrderInfoData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzOrderInfoData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzOrderInfoData_obj.getString("id").equals("")){
				LzzOrderService.self().saveLzzOrderInfo(obj);
			}else{
				LzzOrderService.self().updateLzzOrderInfo(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzOrderInfo(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzOrderService.self().delLzzOrderInfoById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzOrderInfo(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzOrderInfo());
			}else{
				LzzOrderInfo sin = LzzOrderService.self().getLzzOrderInfoById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzOrderInfo.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzOrderInfo() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzOrderInfo> all = LzzOrderService.self().getAllLzzOrderInfoIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzOrderInfo.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzOrderSendMode() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzOrderSendModeData_obj = JSONObject.fromObject(subData);
			LzzOrderSendMode obj = null;
			if(LzzOrderSendModeData_obj.getString("id").equals("")){
				obj = new LzzOrderSendMode();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzOrderService.self().getLzzOrderSendModeById(LzzOrderSendModeData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzOrderSendModeData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzOrderSendModeData_obj.getString("id").equals("")){
				LzzOrderService.self().saveLzzOrderSendMode(obj);
			}else{
				LzzOrderService.self().updateLzzOrderSendMode(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzOrderSendMode(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzOrderService.self().delLzzOrderSendModeById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzOrderSendMode(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzOrderSendMode());
			}else{
				LzzOrderSendMode sin = LzzOrderService.self().getLzzOrderSendModeById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzOrderSendMode.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzOrderSendMode() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzOrderSendMode> all = LzzOrderService.self().getAllLzzOrderSendModeIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzOrderSendMode.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzUser() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzUserData_obj = JSONObject.fromObject(subData);
			LzzUser obj = null;
			if(LzzUserData_obj.getString("id").equals("")){
				obj = new LzzUser();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzUserService.self().getLzzUserById(LzzUserData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzUserData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzUserData_obj.getString("id").equals("")){
				LzzUserService.self().saveLzzUser(obj);
			}else{
				LzzUserService.self().updateLzzUser(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzUser(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzUserService.self().delLzzUserById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzUser(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzUser());
			}else{
				LzzUser sin = LzzUserService.self().getLzzUserById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzUser.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzUser() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzUser> all = LzzUserService.self().getAllLzzUserIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzUser.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzUserAddress() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzUserAddressData_obj = JSONObject.fromObject(subData);
			LzzUserAddress obj = null;
			if(LzzUserAddressData_obj.getString("id").equals("")){
				obj = new LzzUserAddress();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzUserService.self().getLzzUserAddressById(LzzUserAddressData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzUserAddressData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzUserAddressData_obj.getString("id").equals("")){
				LzzUserService.self().saveLzzUserAddress(obj);
			}else{
				LzzUserService.self().updateLzzUserAddress(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzUserAddress(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzUserService.self().delLzzUserAddressById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzUserAddress(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzUserAddress());
			}else{
				LzzUserAddress sin = LzzUserService.self().getLzzUserAddressById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzUserAddress.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzUserAddress() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzUserAddress> all = LzzUserService.self().getAllLzzUserAddressIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzUserAddress.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzUserCoupon() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzUserCouponData_obj = JSONObject.fromObject(subData);
			LzzUserCoupon obj = null;
			if(LzzUserCouponData_obj.getString("id").equals("")){
				obj = new LzzUserCoupon();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzUserService.self().getLzzUserCouponById(LzzUserCouponData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzUserCouponData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzUserCouponData_obj.getString("id").equals("")){
				LzzUserService.self().saveLzzUserCoupon(obj);
			}else{
				LzzUserService.self().updateLzzUserCoupon(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzUserCoupon(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzUserService.self().delLzzUserCouponById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzUserCoupon(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzUserCoupon());
			}else{
				LzzUserCoupon sin = LzzUserService.self().getLzzUserCouponById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzUserCoupon.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzUserCoupon() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzUserCoupon> all = LzzUserService.self().getAllLzzUserCouponIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzUserCoupon.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzUserTaste() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzUserTasteData_obj = JSONObject.fromObject(subData);
			LzzUserTaste obj = null;
			if(LzzUserTasteData_obj.getString("id").equals("")){
				obj = new LzzUserTaste();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzUserService.self().getLzzUserTasteById(LzzUserTasteData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzUserTasteData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzUserTasteData_obj.getString("id").equals("")){
				LzzUserService.self().saveLzzUserTaste(obj);
			}else{
				LzzUserService.self().updateLzzUserTaste(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzUserTaste(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzUserService.self().delLzzUserTasteById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzUserTaste(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzUserTaste());
			}else{
				LzzUserTaste sin = LzzUserService.self().getLzzUserTasteById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzUserTaste.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzUserTaste() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzUserTaste> all = LzzUserService.self().getAllLzzUserTasteIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzUserTaste.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzExpressCompany() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzExpressCompanyData_obj = JSONObject.fromObject(subData);
			LzzExpressCompany obj = null;
			if(LzzExpressCompanyData_obj.getString("id").equals("")){
				obj = new LzzExpressCompany();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzExpressCompanyService.self().getLzzExpressCompanyById(LzzExpressCompanyData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzExpressCompanyData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzExpressCompanyData_obj.getString("id").equals("")){
				LzzExpressCompanyService.self().saveLzzExpressCompany(obj);
			}else{
				LzzExpressCompanyService.self().updateLzzExpressCompany(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzExpressCompany(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzExpressCompanyService.self().delLzzExpressCompanyById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzExpressCompany(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzExpressCompany());
			}else{
				LzzExpressCompany sin = LzzExpressCompanyService.self().getLzzExpressCompanyById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzExpressCompany.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzExpressCompany() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzExpressCompany> all = LzzExpressCompanyService.self().getAllLzzExpressCompanyIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzExpressCompany.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzShopEvaluate() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzShopEvaluateData_obj = JSONObject.fromObject(subData);
			LzzShopEvaluate obj = null;
			if(LzzShopEvaluateData_obj.getString("id").equals("")){
				obj = new LzzShopEvaluate();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzShopService.self().getLzzShopEvaluateById(LzzShopEvaluateData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzShopEvaluateData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzShopEvaluateData_obj.getString("id").equals("")){
				LzzShopService.self().saveLzzShopEvaluate(obj);
			}else{
				LzzShopService.self().updateLzzShopEvaluate(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzShopEvaluate(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzShopService.self().delLzzShopEvaluateById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzShopEvaluate(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzShopEvaluate());
			}else{
				LzzShopEvaluate sin = LzzShopService.self().getLzzShopEvaluateById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzShopEvaluate() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzShopEvaluate> all = LzzShopService.self().getAllLzzShopEvaluateIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzCategory() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzCategoryData_obj = JSONObject.fromObject(subData);
			LzzCategory obj = null;
			if(LzzCategoryData_obj.getString("id").equals("")){
				obj = new LzzCategory();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzGoodService.self().getLzzCategoryById(LzzCategoryData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzCategoryData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzCategoryData_obj.getString("id").equals("")){
				LzzGoodService.self().saveLzzCategory(obj);
			}else{
				LzzGoodService.self().updateLzzCategory(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzCategory(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzGoodService.self().delLzzCategoryById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzCategory(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzCategory());
			}else{
				LzzCategory sin = LzzGoodService.self().getLzzCategoryById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzCategory.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzCategory() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzCategory> all = LzzGoodService.self().getAllLzzCategoryIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzCategory.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzUserGoodInterested() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzUserGoodInterestedData_obj = JSONObject.fromObject(subData);
			LzzUserGoodInterested obj = null;
			if(LzzUserGoodInterestedData_obj.getString("id").equals("")){
				obj = new LzzUserGoodInterested();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzUserService.self().getLzzUserGoodInterestedById(LzzUserGoodInterestedData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzUserGoodInterestedData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzUserGoodInterestedData_obj.getString("id").equals("")){
				LzzUserService.self().saveLzzUserGoodInterested(obj);
			}else{
				LzzUserService.self().updateLzzUserGoodInterested(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzUserGoodInterested(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzUserService.self().delLzzUserGoodInterestedById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzUserGoodInterested(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzUserGoodInterested());
			}else{
				LzzUserGoodInterested sin = LzzUserService.self().getLzzUserGoodInterestedById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzUserGoodInterested.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzUserGoodInterested() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzUserGoodInterested> all = LzzUserService.self().getAllLzzUserGoodInterestedIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzUserGoodInterested.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzUserSession() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzUserSessionData_obj = JSONObject.fromObject(subData);
			LzzUserSession obj = null;
			if(LzzUserSessionData_obj.getString("id").equals("")){
				obj = new LzzUserSession();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzUserService.self().getLzzUserSessionById(LzzUserSessionData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzUserSessionData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzUserSessionData_obj.getString("id").equals("")){
				LzzUserService.self().saveLzzUserSession(obj);
			}else{
				LzzUserService.self().updateLzzUserSession(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzUserSession(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzUserService.self().delLzzUserSessionById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzUserSession(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzUserSession());
			}else{
				LzzUserSession sin = LzzUserService.self().getLzzUserSessionById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzUserSession.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzUserSession() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzUserSession> all = LzzUserService.self().getAllLzzUserSessionIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzUserSession.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzUserGoodViewed() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzUserGoodViewedData_obj = JSONObject.fromObject(subData);
			LzzUserGoodViewed obj = null;
			if(LzzUserGoodViewedData_obj.getString("id").equals("")){
				obj = new LzzUserGoodViewed();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzUserService.self().getLzzUserGoodViewedById(LzzUserGoodViewedData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzUserGoodViewedData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzUserGoodViewedData_obj.getString("id").equals("")){
				LzzUserService.self().saveLzzUserGoodViewed(obj);
			}else{
				LzzUserService.self().updateLzzUserGoodViewed(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzUserGoodViewed(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzUserService.self().delLzzUserGoodViewedById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzUserGoodViewed(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzUserGoodViewed());
			}else{
				LzzUserGoodViewed sin = LzzUserService.self().getLzzUserGoodViewedById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzUserGoodViewed.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzUserGoodViewed() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzUserGoodViewed> all = LzzUserService.self().getAllLzzUserGoodViewedIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzUserGoodViewed.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	public String addOrUpdateLzzIndexDisplay() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject LzzIndexDisplayData_obj = JSONObject.fromObject(subData);
			LzzIndexDisplay obj = null;
			if(LzzIndexDisplayData_obj.getString("id").equals("")){
				obj = new LzzIndexDisplay();
				obj.setId(LzzIDMgr.self().getID());
			}else{
				obj = LzzShopService.self().getLzzIndexDisplayById(LzzIndexDisplayData_obj.getString("id"));
			}
			
			Class clazz = obj.getClass();
			JSONArray field_array = lzzClassUtils.getClassFields(clazz);
			for(int i=0; i<field_array.size(); i++){
				
				if(field_array.getString(i).equals("id")) continue;
				
				String name = field_array.getString(i).substring(0, 1).toUpperCase() + field_array.getString(i).substring(1);
				Method set_method = clazz.getDeclaredMethod("set" + name, String.class);
				set_method.invoke(obj, LzzIndexDisplayData_obj.getString(field_array.getString(i)));
			}
			
			if(LzzIndexDisplayData_obj.getString("id").equals("")){
				LzzShopService.self().saveLzzIndexDisplay(obj);
			}else{
				LzzShopService.self().updateLzzIndexDisplay(obj);
			}
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String removeSingleLzzIndexDisplay(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null!=id
					&& !"".equals(id)
					&& !"-1".equals(id)){
				LzzShopService.self().delLzzIndexDisplayById(id);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzShopEvaluate.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getSingleLzzIndexDisplay(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(id.equals("-1")){
				rsl = JSONObject.fromObject(new LzzIndexDisplay());
			}else{
				LzzIndexDisplay sin = LzzShopService.self().getLzzIndexDisplayById(id);
				rsl = JSONObject.fromObject(sin);
			}
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzIndexDisplay.class));
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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
	
	public String getAllLzzIndexDisplay() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzIndexDisplay> all = LzzShopService.self().getAllLzzIndexDisplayIgnoreDr();
			Collections.reverse(all);
			filterList(all);
			JSONArray all_array = JSONArray.fromObject(all);
			
			rsl.put("fieldOrder", lzzClassUtils.getClassFields(LzzIndexDisplay.class));
			
			rsl.put("allList", all_array);
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
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

	private List filterList(List orig) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if(null!=fieldName
				&& !"".equals(fieldName)
				&& orig.size()>0){
			Class clazz = orig.get(0).getClass();
			String name = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Method get_method = clazz.getDeclaredMethod("get" + name);
			
			for(int i=0; i<orig.size(); i++){
				String field_value = (String) get_method.invoke(orig.get(i));
				if(!field_value.contains(searchValue)){
					orig.remove(i);
					i--;
				}
			}
		}
		
		return orig;
	}
}


<action name="addOrUpdateLzzAfterSale" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzAfterSale"></action>
<action name="removeSingleLzzAfterSale" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzAfterSale"></action>
<action name="getAllLzzAfterSale" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzAfterSale"></action>
<action name="getSingleLzzAfterSale" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzAfterSale"></action>

<action name="addOrUpdateLzzCart" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzCart"></action>
<action name="removeSingleLzzCart" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzCart"></action>
<action name="getAllLzzCart" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzCart"></action>
<action name="getSingleLzzCart" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzCart"></action>

<action name="addOrUpdateLzzCoupon" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzCoupon"></action>
<action name="removeSingleLzzCoupon" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzCoupon"></action>
<action name="getAllLzzCoupon" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzCoupon"></action>
<action name="getSingleLzzCoupon" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzCoupon"></action>

<action name="addOrUpdateLzzDictionary" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzDictionary"></action>
<action name="removeSingleLzzDictionary" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzDictionary"></action>
<action name="getAllLzzDictionary" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzDictionary"></action>
<action name="getSingleLzzDictionary" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzDictionary"></action>

<action name="addOrUpdateLzzFileGroup" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzFileGroup"></action>
<action name="removeSingleLzzFileGroup" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzFileGroup"></action>
<action name="getAllLzzFileGroup" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzFileGroup"></action>
<action name="getSingleLzzFileGroup" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzFileGroup"></action>

<action name="addOrUpdateLzzFileInfo" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzFileInfo"></action>
<action name="removeSingleLzzFileInfo" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzFileInfo"></action>
<action name="getAllLzzFileInfo" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzFileInfo"></action>
<action name="getSingleLzzFileInfo" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzFileInfo"></action>

<action name="addOrUpdateLzzGood" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzGood"></action>
<action name="removeSingleLzzGood" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzGood"></action>
<action name="getAllLzzGood" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzGood"></action>
<action name="getSingleLzzGood" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzGood"></action>

<action name="addOrUpdateLzzGoodCategory" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzGoodCategory"></action>
<action name="removeSingleLzzGoodCategory" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzGoodCategory"></action>
<action name="getAllLzzGoodCategory" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzGoodCategory"></action>
<action name="getSingleLzzGoodCategory" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzGoodCategory"></action>

<action name="addOrUpdateLzzGoodEvaluate" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzGoodEvaluate"></action>
<action name="removeSingleLzzGoodEvaluate" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzGoodEvaluate"></action>
<action name="getAllLzzGoodEvaluate" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzGoodEvaluate"></action>
<action name="getSingleLzzGoodEvaluate" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzGoodEvaluate"></action>

<action name="addOrUpdateLzzGoodFormat" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzGoodFormat"></action>
<action name="removeSingleLzzGoodFormat" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzGoodFormat"></action>
<action name="getAllLzzGoodFormat" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzGoodFormat"></action>
<action name="getSingleLzzGoodFormat" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzGoodFormat"></action>

<action name="addOrUpdateLzzGoodTaste" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzGoodTaste"></action>
<action name="removeSingleLzzGoodTaste" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzGoodTaste"></action>
<action name="getAllLzzGoodTaste" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzGoodTaste"></action>
<action name="getSingleLzzGoodTaste" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzGoodTaste"></action>

<action name="addOrUpdateLzzOrder" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzOrder"></action>
<action name="removeSingleLzzOrder" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzOrder"></action>
<action name="getAllLzzOrder" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzOrder"></action>
<action name="getSingleLzzOrder" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzOrder"></action>

<action name="addOrUpdateLzzOrderInfo" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzOrderInfo"></action>
<action name="removeSingleLzzOrderInfo" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzOrderInfo"></action>
<action name="getAllLzzOrderInfo" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzOrderInfo"></action>
<action name="getSingleLzzOrderInfo" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzOrderInfo"></action>

<action name="addOrUpdateLzzOrderSendMode" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzOrderSendMode"></action>
<action name="removeSingleLzzOrderSendMode" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzOrderSendMode"></action>
<action name="getAllLzzOrderSendMode" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzOrderSendMode"></action>
<action name="getSingleLzzOrderSendMode" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzOrderSendMode"></action>

<action name="addOrUpdateLzzUser" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzUser"></action>
<action name="removeSingleLzzUser" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzUser"></action>
<action name="getAllLzzUser" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzUser"></action>
<action name="getSingleLzzUser" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzUser"></action>

<action name="addOrUpdateLzzUserAddress" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzUserAddress"></action>
<action name="removeSingleLzzUserAddress" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzUserAddress"></action>
<action name="getAllLzzUserAddress" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzUserAddress"></action>
<action name="getSingleLzzUserAddress" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzUserAddress"></action>

<action name="addOrUpdateLzzUserCoupon" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzUserCoupon"></action>
<action name="removeSingleLzzUserCoupon" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzUserCoupon"></action>
<action name="getAllLzzUserCoupon" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzUserCoupon"></action>
<action name="getSingleLzzUserCoupon" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzUserCoupon"></action>

<action name="addOrUpdateLzzUserTaste" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzUserTaste"></action>
<action name="removeSingleLzzUserTaste" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzUserTaste"></action>
<action name="getAllLzzUserTaste" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzUserTaste"></action>
<action name="getSingleLzzUserTaste" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzUserTaste"></action>

<action name="addOrUpdateLzzExpressCompany" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzExpressCompany"></action>
<action name="removeSingleLzzExpressCompany" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzExpressCompany"></action>
<action name="getAllLzzExpressCompany" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzExpressCompany"></action>
<action name="getSingleLzzExpressCompany" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzExpressCompany"></action>

<action name="addOrUpdateLzzShopEvaluate" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzShopEvaluate"></action>
<action name="removeSingleLzzShopEvaluate" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzShopEvaluate"></action>
<action name="getAllLzzShopEvaluate" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzShopEvaluate"></action>
<action name="getSingleLzzShopEvaluate" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzShopEvaluate"></action>

<action name="addOrUpdateLzzCategory" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzCategory"></action>
<action name="removeSingleLzzCategory" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzCategory"></action>
<action name="getAllLzzCategory" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzCategory"></action>
<action name="getSingleLzzCategory" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzCategory"></action>

<action name="addOrUpdateLzzUserGoodInterested" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzUserGoodInterested"></action>
<action name="removeSingleLzzUserGoodInterested" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzUserGoodInterested"></action>
<action name="getAllLzzUserGoodInterested" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzUserGoodInterested"></action>
<action name="getSingleLzzUserGoodInterested" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzUserGoodInterested"></action>

<action name="addOrUpdateLzzUserSession" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzUserSession"></action>
<action name="removeSingleLzzUserSession" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzUserSession"></action>
<action name="getAllLzzUserSession" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzUserSession"></action>
<action name="getSingleLzzUserSession" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzUserSession"></action>

<action name="addOrUpdateLzzUserGoodViewed" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzUserGoodViewed"></action>
<action name="removeSingleLzzUserGoodViewed" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzUserGoodViewed"></action>
<action name="getAllLzzUserGoodViewed" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzUserGoodViewed"></action>
<action name="getSingleLzzUserGoodViewed" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzUserGoodViewed"></action>

<action name="addOrUpdateLzzIndexDisplay" class="com.lzz.order.action.entering.LzzEntering" method="addOrUpdateLzzIndexDisplay"></action>
<action name="removeSingleLzzIndexDisplay" class="com.lzz.order.action.entering.LzzEntering" method="removeSingleLzzIndexDisplay"></action>
<action name="getAllLzzIndexDisplay" class="com.lzz.order.action.entering.LzzEntering" method="getAllLzzIndexDisplay"></action>
<action name="getSingleLzzIndexDisplay" class="com.lzz.order.action.entering.LzzEntering" method="getSingleLzzIndexDisplay"></action>


<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzAfterSale&getSingleAction=getSingleLzzAfterSale&addOrUpdateAction=addOrUpdateLzzAfterSale&removeAction=removeSingleLzzAfterSale">LzzAfterSale</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzCart&getSingleAction=getSingleLzzCart&addOrUpdateAction=addOrUpdateLzzCart&removeAction=removeSingleLzzCart">LzzCart</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzCoupon&getSingleAction=getSingleLzzCoupon&addOrUpdateAction=addOrUpdateLzzCoupon&removeAction=removeSingleLzzCoupon">LzzCoupon</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzDictionary&getSingleAction=getSingleLzzDictionary&addOrUpdateAction=addOrUpdateLzzDictionary&removeAction=removeSingleLzzDictionary">LzzDictionary</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzFileGroup&getSingleAction=getSingleLzzFileGroup&addOrUpdateAction=addOrUpdateLzzFileGroup&removeAction=removeSingleLzzFileGroup">LzzFileGroup</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzFileInfo&getSingleAction=getSingleLzzFileInfo&addOrUpdateAction=addOrUpdateLzzFileInfo&removeAction=removeSingleLzzFileInfo">LzzFileInfo</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzGood&getSingleAction=getSingleLzzGood&addOrUpdateAction=addOrUpdateLzzGood&removeAction=removeSingleLzzGood">LzzGood</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzGoodCategory&getSingleAction=getSingleLzzGoodCategory&addOrUpdateAction=addOrUpdateLzzGoodCategory&removeAction=removeSingleLzzGoodCategory">LzzGoodCategory</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzGoodEvaluate&getSingleAction=getSingleLzzGoodEvaluate&addOrUpdateAction=addOrUpdateLzzGoodEvaluate&removeAction=removeSingleLzzGoodEvaluate">LzzGoodEvaluate</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzGoodFormat&getSingleAction=getSingleLzzGoodFormat&addOrUpdateAction=addOrUpdateLzzGoodFormat&removeAction=removeSingleLzzGoodFormat">LzzGoodFormat</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzGoodTaste&getSingleAction=getSingleLzzGoodTaste&addOrUpdateAction=addOrUpdateLzzGoodTaste&removeAction=removeSingleLzzGoodTaste">LzzGoodTaste</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzOrder&getSingleAction=getSingleLzzOrder&addOrUpdateAction=addOrUpdateLzzOrder&removeAction=removeSingleLzzOrder">LzzOrder</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzOrderInfo&getSingleAction=getSingleLzzOrderInfo&addOrUpdateAction=addOrUpdateLzzOrderInfo&removeAction=removeSingleLzzOrderInfo">LzzOrderInfo</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzOrderSendMode&getSingleAction=getSingleLzzOrderSendMode&addOrUpdateAction=addOrUpdateLzzOrderSendMode&removeAction=removeSingleLzzOrderSendMode">LzzOrderSendMode</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzUser&getSingleAction=getSingleLzzUser&addOrUpdateAction=addOrUpdateLzzUser&removeAction=removeSingleLzzUser">LzzUser</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzUserAddress&getSingleAction=getSingleLzzUserAddress&addOrUpdateAction=addOrUpdateLzzUserAddress&removeAction=removeSingleLzzUserAddress">LzzUserAddress</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzUserCoupon&getSingleAction=getSingleLzzUserCoupon&addOrUpdateAction=addOrUpdateLzzUserCoupon&removeAction=removeSingleLzzUserCoupon">LzzUserCoupon</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzUserTaste&getSingleAction=getSingleLzzUserTaste&addOrUpdateAction=addOrUpdateLzzUserTaste&removeAction=removeSingleLzzUserTaste">LzzUserTaste</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzExpressCompany&getSingleAction=getSingleLzzExpressCompany&addOrUpdateAction=addOrUpdateLzzExpressCompany&removeAction=removeSingleLzzExpressCompany">LzzExpressCompany</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzShopEvaluate&getSingleAction=getSingleLzzShopEvaluate&addOrUpdateAction=addOrUpdateLzzShopEvaluate&removeAction=removeSingleLzzShopEvaluate">LzzShopEvaluate</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzCategory&getSingleAction=getSingleLzzCategory&addOrUpdateAction=addOrUpdateLzzCategory&removeAction=removeSingleLzzCategory">LzzCategory</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzUserGoodInterested&getSingleAction=getSingleLzzUserGoodInterested&addOrUpdateAction=addOrUpdateLzzUserGoodInterested&removeAction=removeSingleLzzUserGoodInterested">LzzUserGoodInterested</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzUserSession&getSingleAction=getSingleLzzUserSession&addOrUpdateAction=addOrUpdateLzzUserSession&removeAction=removeSingleLzzUserSession">LzzUserSession</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzUserGoodViewed&getSingleAction=getSingleLzzUserGoodViewed&addOrUpdateAction=addOrUpdateLzzUserGoodViewed&removeAction=removeSingleLzzUserGoodViewed">LzzUserGoodViewed</a>

<a class="btn btn-default btn-lg btn-block" target="_blank" href="LzzPojoEntering.html?getAllAction=getAllLzzIndexDisplay&getSingleAction=getSingleLzzIndexDisplay&addOrUpdateAction=addOrUpdateLzzIndexDisplay&removeAction=removeSingleLzzIndexDisplay">LzzIndexDisplay</a>


<mapping resource="com/lzz/order/pojo/LzzAfterSale.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzCart.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzCoupon.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzDictionary.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzFileGroup.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzFileInfo.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzGood.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzGoodCategory.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzGoodEvaluate.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzGoodFormat.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzGoodTaste.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzOrder.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzOrderInfo.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzOrderSendMode.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzUser.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzUserAddress.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzUserCoupon.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzUserTaste.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzExpressCompany.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzShopEvaluate.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzCategory.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzUserGoodInterested.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzUserSession.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzUserGoodViewed.hbm.xml"/>

<mapping resource="com/lzz/order/pojo/LzzIndexDisplay.hbm.xml"/>


