package com.lzz.order.action.goods;

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
import com.lzz.order.mgr.LzzIDMgr;
import com.lzz.order.mgr.LzzOrderMgr;
import com.lzz.order.mgr.LzzShopMgr;
import com.lzz.order.mgr.LzzUserMgr;
import com.lzz.order.pojo.LzzCategory;
import com.lzz.order.pojo.LzzGood;
import com.lzz.order.pojo.LzzGoodCategory;
import com.lzz.order.pojo.LzzGoodEvaluate;
import com.lzz.order.pojo.LzzOrder;
import com.lzz.order.pojo.LzzOrderInfo;
import com.lzz.order.pojo.LzzShopEvaluate;
import com.lzz.order.pojo.LzzUser;
import com.lzz.order.pojo.LzzUserGoodInterested;
import com.lzz.order.pojo.LzzUserGoodViewed;
import com.lzz.order.service.LzzDictionaryService;
import com.lzz.order.service.LzzGoodService;
import com.lzz.order.service.LzzOrderService;
import com.lzz.order.service.LzzShopService;
import com.lzz.order.service.LzzUserService;
import com.lzz.order.utils.LzzConstString;
import com.lzz.order.utils.LzzPaging;

public class LzzGoodsAction extends LzzBaseManagerAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4523470215330043696L;

	public String goodId;
	/**
	 * 获取商品详情
	 * @return
	 */
	public String getGoodInfo() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONObject good_info = LzzGoodMgr.getGoodJSONInfoById(curUserId, goodId);
			good_info.put("isInterest", LzzGoodMgr.isGoodInterested(curUserId, goodId));
			
			JSONObject coupons = new JSONObject();
			
			rsl.put("goodInfo", good_info);
			rsl.put("coupons", coupons);
			
			LzzUserMgr.addUserGoodView(curUserId, goodId);
			
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
	 * 获取所有商品
	 * @return
	 */
	public String getAllGoods() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzGood> goods_list = LzzGoodService.self().getAllValidLzzGood();
			
			JSONArray goods = new JSONArray();
			for(LzzGood sin : goods_list){
				JSONObject obj = LzzGoodMgr.getGoodJSONInfoById(curUserId, sin.getId());
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
	
	/**
	 * 获取所有商品分类
	 * @return
	 */
	public String getLzzCategories() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			rsl.put("catList", LzzGoodMgr.getCategritiesInfo());
			
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
	 * 添加或更新商品分类
	 * @return
	 */
	public String addOrUpdateLzzCategories() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			LzzCategory cat = null;
			if("-1".equals(catId)){
				cat = new LzzCategory();
				cat.setId(LzzIDMgr.self().getID());
			}else{
				cat = LzzGoodService.self().getLzzCategoryById(catId);
			}
			
			subData = new String(subData.getBytes("iso-8859-1"),"utf-8");
			System.out.println(subData);
			JSONObject subObj = JSONObject.fromObject(subData);
			cat.setName(subObj.getString("name"));
			cat.setDescription(subObj.getString("description"));
			cat.setIsHot(subObj.getString("isHot"));
			cat.setSaleCount(subObj.getString("saleCount"));
			
			JSONArray typicalImgs = subObj.getJSONArray("displayImgs");
			String typicalImgs_id = LzzFileMgr.createFileGroupByFileIds(typicalImgs);
			cat.setTypicalImg(typicalImgs_id);
			
			if("-1".equals(catId)){
				LzzGoodService.self().saveLzzCategory(cat);
			}else{
				LzzGoodService.self().updateLzzCategory(cat);
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
	
	/**
	 * 获取最新上架商品
	 * @return
	 */
	public String getNewGoods() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzGood> goods_list = LzzGoodService.self().getLzzGoodListByIsNew("1");
			
			JSONArray goods = new JSONArray();
			for(LzzGood sin : goods_list){
				JSONObject obj = LzzGoodMgr.getGoodJSONInfoById(curUserId, sin.getId());
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
	
	/**
	 * 获取热销商品
	 * @return
	 */
	public String getHotGoods() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzGood> goods_list = LzzGoodService.self().getLzzGoodListByIsHot("1");
			
			JSONArray goods = new JSONArray();
			for(LzzGood sin : goods_list){
				JSONObject obj = LzzGoodMgr.getGoodJSONInfoById(curUserId, sin.getId());
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
	
	public String catId;
	/**
	 * 获取指定分类的所有商品
	 * @return
	 */
	public String getGoodsByCategory() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			List<LzzGoodCategory> good_cats = LzzGoodService.self().getLzzGoodCategoryListByCategoryId(catId);
			
			JSONArray goods = new JSONArray();
			for(LzzGoodCategory sin : good_cats){
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
	
	
	public String isInterested;
	/**
	 * 商品收藏和取消收藏
	 * @return
	 */
	public String setGoodInterested() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			LzzUserGoodInterested gi = LzzUserService.self().getLzzUserGoodInterestedByUserIdAndGoodId(curUserId, goodId);
			if(null==gi){
				gi = new LzzUserGoodInterested();
				gi.setId(LzzIDMgr.self().getID());
				gi.setGoodId(goodId);
				gi.setUserId(curUserId);
				LzzUserService.self().saveLzzUserGoodInterested(gi);
			}else{
				LzzUserService.self().delLzzUserGoodInterested(gi);
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
	
	
	/**
	 * 获取商品评价信息
	 * @return
	 */
	public String pageSize;
	public String pageNum;
	public String getGoodEvaluates() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			if(null==pageSize || "".equals(pageSize)) pageSize = "20";
			if(null==pageNum || "".equals(pageNum)) pageSize = "1";
			
			List<LzzGoodEvaluate> list = LzzGoodService.self().getLzzGoodEvaluateListByGoodId(goodId);
			rsl.put("totalPage", LzzPaging.getPageNum(list, Integer.valueOf(pageSize)));
			list = LzzPaging.get(list, Integer.valueOf(pageNum), Integer.valueOf(pageSize));
			JSONArray array = new JSONArray();
			for(LzzGoodEvaluate sin : list){
				JSONObject obj = new JSONObject();
				String order_info_id = sin.getOrderInfoId();
				String user_id = LzzOrderMgr.getUserIdByOrderInfoId(order_info_id);
				LzzUser user = LzzUserService.self().getLzzUserById(user_id);
				obj.put("uname", user.getNick_name());
				obj.put("imgUrl", user.getAvatarUrl());
				obj.put("userLevel", LzzDictionaryMgr.getValueById(user.getLevel()));
				obj.put("levelImgUrl", LzzDictionaryMgr.getDicTypicalImgById(user.getLevel()));
				obj.put("format", LzzOrderMgr.getFormatAndTasteByOrderInfoId(order_info_id));
				obj.put("date", sin.getCreateTime().substring(0, 10));
				obj.put("evaluateLevel", sin.getEvaluateLevel());
				obj.put("contents", sin.getContents());
				obj.put("contentImgs", LzzFileMgr.getUrlArraysByFileGroupIdForEvaluate(sin.getPicsId()));
				
				array.add(obj);
			}
			
			rsl.put("evaluateList", array);
			
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
	 * 获取要评价的订单信息
	 */
	public String orderId;
	public String getOrderInfoToEvaluate(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			LzzOrder order = LzzOrderService.self().getLzzOrderById(orderId);
			String toe_id = LzzDictionaryMgr.getIDByCategoryAndValue(LzzConstString.smOrderStatus, LzzConstString.smOrderStatus_ToEvaluate);
			if(toe_id.equals(order.getStatus())){
				List<LzzOrderInfo> infos = LzzOrderService.self().getLzzOrderInfoListByOrderId(orderId);
				JSONArray array = new JSONArray();
				for(LzzOrderInfo sin : infos){
					JSONObject obj = new JSONObject();
					obj.put("orderInfoId", sin.getId());
					obj.put("goodId", sin.getGoodId());
					obj.put("goodName", LzzGoodMgr.getGoodNameById(sin.getGoodId()));
					obj.put("goodImgUrl", LzzGoodMgr.getGoodTypicalImgUrl(sin.getGoodId()));
					obj.put("format", LzzGoodMgr.getFormatNameByGoodFormatId(sin.getFormat()));
					obj.put("taste", LzzGoodMgr.getTasteNameByGoodTasteId(sin.getTaste()));
					obj.put("evaluateLevel", "5");
					obj.put("evaluateImgs", new JSONArray());
					
					array.add(obj);
				}
				
				rsl.put("orderInfo", array);
			}else{
				rsl.put("iserror", true);
				rsl.put("errormgr", "请勿重复评价");
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
	
	/**
	 * 提交评价信息
	 */
	public String evaluateData;
	public String contents;
	public String submitEvaluate(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			evaluateData = new String(evaluateData.getBytes("iso-8859-1"),"utf-8");
			contents = new String(contents.getBytes("iso-8859-1"),"utf-8");
			JSONObject data_obj = JSONObject.fromObject(evaluateData);
			JSONObject contents_obj = JSONObject.fromObject(contents);
			
			//存储商品评价信息
			JSONArray infos = data_obj.getJSONArray("orderInfo");
			for(int i=0; i<infos.size(); i++){
				JSONObject info = infos.getJSONObject(i);
				String info_id = info.getString("orderInfoId");
				String contents = contents_obj.getString("evaluateContent-" + info_id);
				if(null==contents || "".equals(contents)){
					contents = "该用户未填写评价内容";
				}
				String good_id = info.getString("goodId");
				String level = info.getString("evaluateLevel");
				JSONArray evaluateImgs = info.getJSONArray("evaluateImgs");
				String g_id = LzzFileMgr.createFileGroupByJSONArray(evaluateImgs);
				
				LzzGoodEvaluate evaluate = new LzzGoodEvaluate();
				evaluate.setId(LzzIDMgr.self().getID());
				evaluate.setOrderInfoId(info_id);
				evaluate.setGoodId(good_id);
				evaluate.setContents(contents);
				evaluate.setEvaluateLevel(level);
				evaluate.setPicsId(g_id);
				LzzGoodService.self().saveLzzGoodEvaluate(evaluate);
			}
			
			//存储店铺评价
			String evaluateContentShop = "";
			if(contents_obj.containsKey("evaluateContentShop")){
				evaluateContentShop = contents_obj.getString("evaluateContentShop");
			}
			String shop_level = data_obj.getString("shopEvaluateLevel");
			String order_id = data_obj.getString("orderId");
			LzzShopEvaluate s_e = new LzzShopEvaluate();
			s_e.setId(LzzIDMgr.self().getID());
			s_e.setContents(order_id);
			s_e.setContents(evaluateContentShop);
			s_e.setEvaluateLevel(shop_level);
			LzzShopService.self().saveLzzShopEvaluate(s_e);
			
			//更新订单状态
			LzzOrder order = LzzOrderService.self().getLzzOrderById(order_id);
			order.setStatus(LzzDictionaryMgr.getIDByCategoryAndValue(LzzConstString.smOrderStatus, LzzConstString.smOrderStatus_Finished));
			LzzOrderService.self().updateLzzOrder(order);
			
			//给用户添加积分
			String inte = LzzOrderMgr.getOrderTotalPrice(order_id);
			LzzUserMgr.addUserIntegration(curUserId, Float.valueOf(inte).intValue());
			
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
	
	public String searchName;
	public String searchGoodByName() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");
		
		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		
		try {
			searchName = new String(searchName.getBytes("iso-8859-1"),"utf-8");
			List<LzzGood> goods_list = LzzGoodService.self().getAllValidLzzGood();
			JSONArray goods = new JSONArray();
			for(LzzGood sin : goods_list){
				if(sin.getName().contains(searchName)){
					JSONObject obj = LzzGoodMgr.getGoodJSONInfoById(curUserId, sin.getId());
					goods.add(obj);
				}
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
	
	public String qrCode;
	public String searchGoodQRCode() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");
		
		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		
		try {
			qrCode = new String(qrCode.getBytes("iso-8859-1"),"utf-8");
			List<LzzGood> goods_list = LzzGoodService.self().getAllValidLzzGood();
			JSONArray goods = new JSONArray();
			for(LzzGood sin : goods_list){
				if(sin.getName().contains(qrCode)){
					JSONObject obj = LzzGoodMgr.getGoodJSONInfoById(curUserId, sin.getId());
					goods.add(obj);
				}
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
	
	public String getIndexInitInfo() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");
		
		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		
		try {
			JSONArray swipers = LzzShopMgr.getShopSwipersInfo();
			JSONArray menus = LzzShopMgr.getShopMenusInfo();
			JSONArray newGoods = LzzGoodMgr.getNewGoods(curUserId);
			JSONArray hotCategories = LzzGoodMgr.getHotCategories();
			JSONArray hotGoods = LzzGoodMgr.getHostGoods(curUserId);
			
			rsl.put("swipers", swipers);
			rsl.put("menus", menus);
			rsl.put("newGoods", newGoods);
			rsl.put("hotCategories", hotCategories);
			rsl.put("hotGoods", hotGoods);
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
	 * 上架新商品
	 * @return
	 */
	public String subData;
	public String createNewGood() {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");
		
		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		
		try {
			subData = new String(subData.getBytes("iso-8859-1"),"utf-8");
			System.out.println(subData);
			JSONObject subObj = JSONObject.fromObject(subData);
			//基础信息
			LzzGood good = new LzzGood();
			good.setId(LzzIDMgr.self().getID());
			good.setName(subObj.getString("name"));
			good.setCode(subObj.getString("code"));
			good.setSaleCount(subObj.getString("saleCount"));
			good.setIsHot(subObj.getString("isHot"));
			good.setIsNew(subObj.getString("isNew"));
			
			//信息图片
			JSONArray displayImgs = subObj.getJSONArray("displayImgs");
			String displayImgs_id = LzzFileMgr.createFileGroupByFileIds(displayImgs);
			good.setDisplayImgs(displayImgs_id);
			
			//滚动图片
			JSONArray scrollImgs = subObj.getJSONArray("scrollImgs");
			String scrollImgs_id = LzzFileMgr.createFileGroupByFileIds(scrollImgs);
			good.setScrollImgs(scrollImgs_id);
			
			//示例图片
			JSONArray typicalImg = subObj.getJSONArray("typicalImg");
			String typicalImg_id = LzzFileMgr.createFileGroupByFileIds(typicalImg);
			good.setTypicalImg(typicalImg_id);
			LzzGoodService.self().saveLzzGood(good);
			
			//商品分类
			JSONArray cats = subObj.getJSONArray("cats");
			for(int i=0; i<cats.size(); i++){
				LzzGoodMgr.addGoodCategory(good.getId(), cats.getString(i));
			}
			
			//规格及价格
			JSONArray formatPrice = subObj.getJSONArray("formatPrice");
			for(int i=0; i<formatPrice.size(); i++){
				JSONObject obj = formatPrice.getJSONObject(i);
				LzzGoodMgr.addFormatPrice(good.getId(), obj.getString("format"), obj.getString("price"));
			}
			
			//口味
			JSONArray taste = subObj.getJSONArray("taste");
			for(int i=0; i<taste.size(); i++){
				LzzGoodMgr.addGoodTaste(good.getId(), taste.getString(i));
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
}
