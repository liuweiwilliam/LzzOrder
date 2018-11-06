
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

