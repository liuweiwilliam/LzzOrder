package com.lzz.order.mgr;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lzz.order.pojo.LzzCategory;
import com.lzz.order.pojo.LzzGood;
import com.lzz.order.pojo.LzzGoodCategory;
import com.lzz.order.pojo.LzzGoodEvaluate;
import com.lzz.order.pojo.LzzGoodFormat;
import com.lzz.order.pojo.LzzGoodTaste;
import com.lzz.order.pojo.LzzOrder;
import com.lzz.order.pojo.LzzOrderInfo;
import com.lzz.order.pojo.LzzUser;
import com.lzz.order.pojo.LzzUserGoodInterested;
import com.lzz.order.service.LzzGoodService;
import com.lzz.order.service.LzzOrderService;
import com.lzz.order.service.LzzUserService;
import com.lzz.order.utils.LzzDateUtil;

public class LzzGoodMgr {
	public static List<LzzCategory> getCategories(){
		return LzzGoodService.self().getAllValidLzzCategory();
	}
	
	/*
	 * 获取JSON格式的商品信息
	 */
	public static JSONObject getGoodJSONInfoById(String userid, String id){
		LzzGood good = LzzGoodService.self().getLzzGoodById(id);
		
		JSONObject rslt = new JSONObject();
		
		if(null!=good){
			rslt.put("id", good.getId());
			rslt.put("name", good.getName());
			rslt.put("price", getGoodPriceByFirstFormat(userid, id));
			rslt.put("isDiscount", goodHasDiscount(id));
			rslt.put("discountPrice", getGoodLowestDiscountPriece(id));
			rslt.put("curSelectFormat", getGoodFirstFormatId(id));
			rslt.put("goodEvaluateRate", good.getNiceEvaluateRate());
			if(null==good.getNiceEvaluateRate()
					|| "".equals(good.getNiceEvaluateRate())){
				rslt.put("goodEvaluateRate", "100");
			}
			rslt.put("saleCount", good.getSaleCount());
			rslt.put("createDate", good.getCreateTime().substring(0, 10));
			rslt.put("imgUrl", LzzFileMgr.getSingleFileUrlByFileGroupId(good.getTypicalImg()));
			
			rslt.put("formats", getGoodFormats(id));
			rslt.put("tastes", getGoodTastes(id));
			rslt.put("curSelectTaste", getGoodFirstTasteId(id));
			rslt.put("swiperImgs", LzzFileMgr.getGoodFileInfosByFileGroupId(good.getScrollImgs()));
			rslt.put("infoImgs", LzzFileMgr.getGoodFileInfosByFileGroupId(good.getDisplayImgs()));
			//获取商品最新一条评价
			rslt.put("evaluate", LzzGoodMgr.getLatestGoodEvaluate(good.getId()));
		}
		
		return rslt;
	}
	
	/**
	 * 获取商品最新一条评价信息
	 * @param id
	 * @return
	 */
	private static JSONObject getLatestGoodEvaluate(String id) {
		// TODO Auto-generated method stub
		List<LzzGoodEvaluate> ges = LzzGoodService.self().getLzzGoodEvaluateListByGoodId(id);
		if(null==ges || ges.size()==0) return new JSONObject();
		
		JSONObject rslt = new JSONObject();
		LzzGoodEvaluate ge = ges.get(ges.size()-1);
		String type = LzzOrderMgr.getFormatAndTasteByOrderInfoId(ge.getOrderInfoId());
		LzzUser user = LzzUserMgr.getLzzUserByOrderInfoId(ge.getOrderInfoId());
		rslt.put("nickName", user.getNick_name());
		rslt.put("levelImgUrl", LzzDictionaryMgr.getDicTypicalImgById(user.getLevel()));
		rslt.put("headImgUrl", user.getAvatarUrl());
		rslt.put("contents", ge.getContents());
		rslt.put("type", type);
		
		return rslt;
	}

	/**
	 * 通过商品ID查询商品名称
	 * @param good_id
	 * @return
	 */
	public static String getGoodNameById(String good_id){
		if(null==good_id
				|| "".equals(good_id)){
			return null;
		}
		
		LzzGood good = LzzGoodService.self().getLzzGoodById(good_id);
		if(null==good) return null;
		
		return good.getName();
	}
	
	/**
	 * 通过规格ID查询商品ID
	 * @param format_id
	 * @return
	 */
	public static String getGoodIdByFormatId(String format_id){
		if(null==format_id
				|| "".equals(format_id)){
			return null;
		}
		
		LzzGoodFormat format = LzzGoodService.self().getLzzGoodFormatById(format_id);
		if(null==format) return null;
		
		return format.getGoodId();
	}
	
	/**
	 * 查询商品规格价格
	 * @param format_id
	 * @return
	 */
	public static String getPriceByGoodFormatId(String userid, String format_id){
		if(null==format_id
				|| "".equals(format_id)){
			return null;
		}
		
		LzzGoodFormat format = LzzGoodService.self().getLzzGoodFormatById(format_id);
		if(null==format) return null;
		
		String price = format.getPrice();
		//获取促销价
		if("1".equals(isFormatInDiscount(format_id))){
			price = format.getDiscountPrice();
		}
		
		//获取会员价
		if(null!=format.getVipPrice()
				&& !"".equals(format.getVipPrice())){
			JSONObject ps = JSONObject.fromObject(format.getVipPrice());
			String user_level = LzzUserMgr.getUserLevel(userid);
			String vip_price = (String)ps.get(user_level);
			if(null!=vip_price){
				if(Float.valueOf(price)<Float.valueOf(vip_price)){
					price = vip_price;
				}
			}
		}
		
		//返回最低价
		return price;
	}
	
	/**
	 * 查询商品规格名称
	 * @param format_id
	 * @return
	 */
	public static String getFormatNameByGoodFormatId(String format_id){
		if(null==format_id
				|| "".equals(format_id)){
			return null;
		}
		
		LzzGoodFormat format = LzzGoodService.self().getLzzGoodFormatById(format_id);
		if(null==format) return null;
		
		return format.getFormatName();
	}
	
	/**
	 * 查询商品口味名称
	 * @param taste_id
	 * @return
	 */
	public static String getTasteNameByGoodTasteId(String taste_id){
		if(null==taste_id
				|| "".equals(taste_id)){
			return null;
		}
		
		LzzGoodTaste taste = LzzGoodService.self().getLzzGoodTasteById(taste_id);
		if(null==taste) return null;
		
		return taste.getTasteName();
	}
	
	/**
	 * 判断商品当前是否促销
	 * @param id 商品ID
	 * @return
	 */
	public static boolean goodHasDiscount(String id){
		List<LzzGoodFormat> good_fmts = LzzGoodService.self().getLzzGoodFormatListByGoodId(id);
		for(LzzGoodFormat sin : good_fmts){
			if(null!=sin.getDiscountPrice()
					&& !"".equals(sin.getDiscountPrice())
					&& LzzDateUtil.isBetween(LzzDateUtil.getNow("s"), sin.getDiscountSTime(), sin.getDiscountETime())){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 获取商品的第一个规格的价格
	 * @param id 商品ID
	 * @return 商品价格字符串
	 */
	public static String getGoodPriceByFirstFormat(String userid, String id){
		List<LzzGoodFormat> good_fmts = LzzGoodService.self().getLzzGoodFormatListByGoodId(id);
		
		String rslt = "";
		for(LzzGoodFormat sin : good_fmts){
			return getPriceByGoodFormatId(userid, sin.getId());
		}
			
		return rslt;
	}
	
	/**
	 * 获取商品第一个规格ID
	 * @param id 商品ID
	 * @return 规格ID
	 */
	public static String getGoodFirstFormatId(String id){
		List<LzzGoodFormat> good_fmts = LzzGoodService.self().getLzzGoodFormatListByGoodId(id);
		
		String rslt = "";
		for(LzzGoodFormat sin : good_fmts){
			return sin.getId();
		}
			
		return rslt;
	}
	
	public static String getGoodFirstTasteId(String id){
		List<LzzGoodTaste> gts = LzzGoodService.self().getLzzGoodTasteListByGoodId(id);
		String rslt = "";
		for(LzzGoodTaste sin : gts){
			return sin.getId();
		}
		
		return rslt;
	}
	
	/**
	 * 获取商品规格信息
	 * @param id 商品ID
	 * @return 规格的JSON数组
	 */
	public static JSONArray getGoodFormats(String id){
		JSONArray rslt = new JSONArray();
		List<LzzGoodFormat> good_fmts = LzzGoodService.self().getLzzGoodFormatListByGoodId(id);
		
		for(LzzGoodFormat sin : good_fmts){
			JSONObject obj = new JSONObject();
			obj.put("id", sin.getId());
			obj.put("name", sin.getFormatName());
			obj.put("price", sin.getPrice());
			obj.put("isDiscount", goodHasDiscount(id));
			obj.put("discountPrice", sin.getDiscountPrice());
			rslt.add(obj);
		}
			
		return rslt;
	}
	
	/**
	 * 获取商品口味信息
	 * @param id 商品ID
	 * @return 口味JSON数组
	 */
	public static JSONArray getGoodTastes(String id){
		JSONArray rslt = new JSONArray();
		List<LzzGoodTaste> good_tastes = LzzGoodService.self().getLzzGoodTasteListByGoodId(id);
		
		for(LzzGoodTaste sin : good_tastes){
			JSONObject obj = new JSONObject();
			obj.put("id", sin.getId());
			obj.put("name", sin.getTasteName());
			rslt.add(obj);
		}
			
		return rslt;
	}
	
	/**
	 * 获取商品最低促销价格
	 * @param id 商品ID
	 * @return 最低促销价格字符串，若当前没有促销返回""
	 */
	public static String getGoodLowestDiscountPriece(String id){
		List<LzzGoodFormat> good_fmts = LzzGoodService.self().getLzzGoodFormatListByGoodId(id);
		
		String lowest_priece = "";
		
		for(LzzGoodFormat sin : good_fmts){
			if(null!=sin.getDiscountPrice()
					&& !"".equals(sin.getDiscountPrice())
					&& LzzDateUtil.isBetween(LzzDateUtil.getNow("s"), sin.getDiscountSTime(), sin.getDiscountETime())){
				if("".equals(lowest_priece)
						|| Float.valueOf(lowest_priece)>Float.valueOf(sin.getDiscountPrice())){
					lowest_priece = sin.getDiscountPrice();
				}
			}
		}
		
		return lowest_priece;
	}

	/**
	 * 判断用户是否收藏该商品
	 * @param userId
	 * @param goodId
	 * @return
	 */
	public static String isGoodInterested(String userId, String goodId) {
		// TODO Auto-generated method stub
		LzzUserGoodInterested gi = LzzUserService.self().getLzzUserGoodInterestedByUserIdAndGoodId(userId, goodId);
		return null==gi?"0":"1";
	}
	
	/**
	 * 获取商品示例图片地址
	 * @param good_id
	 * @return
	 */
	public static String getGoodTypicalImgUrl(String good_id){
		LzzGood good = LzzGoodService.self().getLzzGoodById(good_id);
		return LzzFileMgr.getSingleFileUrlByFileGroupId(good.getTypicalImg());
	}
	
	/**
	 * 判断该规格商品是否在特价
	 * @param format_id
	 * @return
	 */
	public static String isFormatInDiscount(String format_id){
		LzzGoodFormat sin = LzzGoodService.self().getLzzGoodFormatById(format_id);
		if(null!=sin.getDiscountPrice()
				&& !"".equals(sin.getDiscountPrice())
				&& LzzDateUtil.isBetween(LzzDateUtil.getNow("s"), sin.getDiscountSTime(), sin.getDiscountETime())){
			return "1";
		}
		
		return "0";
	}
	
	/**
	 * 判断该规格折扣价
	 * @param format_id
	 * @return
	 */
	public static String getFormatDiscountPrice(String format_id){
		LzzGoodFormat sin = LzzGoodService.self().getLzzGoodFormatById(format_id);
		if(null!=sin.getDiscountPrice()
				&& !"".equals(sin.getDiscountPrice())
				&& LzzDateUtil.isBetween(LzzDateUtil.getNow("s"), sin.getDiscountSTime(), sin.getDiscountETime())){
			return sin.getDiscountPrice();
		}
		
		return null;
	}

	/**
	 * 获取新商品的JSON信息
	 * @param userid
	 * @return
	 */
	public static JSONArray getNewGoods(String userid) {
		// TODO Auto-generated method stub
		JSONArray rslt = new JSONArray();
		List<LzzGood> goods = LzzGoodService.self().getLzzGoodListByIsNew("1");
		for(LzzGood sin : goods){
			JSONObject obj = getGoodJSONInfoById(userid, sin.getId());
			rslt.add(obj);
		}
		
		return rslt;
	}
	
	/**
	 * 获取热销分类的JSON信息
	 * @return
	 */
	public static JSONArray getHotCategories() {
		// TODO Auto-generated method stub
		JSONArray rslt = new JSONArray();
		List<LzzCategory> cats = LzzGoodService.self().getLzzCategoryListByIsHot("1");
		for(LzzCategory sin : cats){
			JSONObject obj = getCategoryJSONInfoById(sin.getId());
			rslt.add(obj);
		}
		
		return rslt;
	}
	
	/**
	 * 获取分类的JSON格式信息
	 * @param id
	 * @return
	 */
	private static JSONObject getCategoryJSONInfoById(String id) {
		// TODO Auto-generated method stub
		LzzCategory cat = LzzGoodService.self().getLzzCategoryById(id);
		JSONObject obj = new JSONObject();
		if(null==cat) return obj;
		obj.put("id", cat.getId());
		obj.put("name", cat.getName());
		obj.put("typicalImg", LzzFileMgr.getSingleFileUrlByFileGroupId(cat.getTypicalImg()));
		obj.put("description", cat.getDescription());
		obj.put("saleCount", cat.getSaleCount());
		
		return obj;
	}

	/**
	 * 获取热销商品的JSON信息
	 * @param userid
	 * @return
	 */
	public static JSONArray getHostGoods(String userid) {
		// TODO Auto-generated method stub
		JSONArray rslt = new JSONArray();
		List<LzzGood> goods = LzzGoodService.self().getLzzGoodListByIsHot("1");
		for(LzzGood sin : goods){
			JSONObject obj = getGoodJSONInfoById(userid, sin.getId());
			rslt.add(obj);
		}
		
		return rslt;
	}

	/**
	 * 商品添加分类
	 * @param good_id
	 * @param cat_id
	 */
	public static void addGoodCategory(String good_id, String cat_id) {
		// TODO Auto-generated method stub
		LzzGoodCategory gc = new LzzGoodCategory();
		gc.setId(LzzIDMgr.self().getID());
		gc.setGoodId(good_id);
		gc.setCategoryId(cat_id);
		LzzGoodService.self().saveLzzGoodCategory(gc);
	}

	/**
	 * 添加商品规格价格
	 * @param good_id
	 * @param format
	 * @param price
	 */
	public static void addFormatPrice(String good_id, String format, String price) {
		// TODO Auto-generated method stub
		//创建规格
		LzzGoodFormat ft = new LzzGoodFormat();
		ft.setId(LzzIDMgr.self().getID());
		ft.setGoodId(good_id);
		ft.setFormatName(format);
		ft.setPrice(price);
		LzzGoodService.self().saveLzzGoodFormat(ft);
	}
	
	/**
	 * 添加商品口味
	 * @param good_id
	 * @param taste
	 */
	public static void addGoodTaste(String good_id, String taste) {
		// TODO Auto-generated method stub
		//创建规格
		LzzGoodTaste tas = new LzzGoodTaste();
		tas.setId(LzzIDMgr.self().getID());
		tas.setGoodId(good_id);
		tas.setTasteName(taste);
		LzzGoodService.self().saveLzzGoodTaste(tas);
	}

	/**
	 * 获取分类的列表信息
	 * @return
	 */
	public static JSONArray getCategritiesInfo(){
		List<LzzCategory> list = LzzGoodService.self().getAllValidLzzCategory();
		JSONArray array = new JSONArray();
		for(LzzCategory sin : list){
			JSONObject obj = new JSONObject();
			obj.put("id", sin.getId());
			obj.put("name", sin.getName());
			obj.put("description", sin.getDescription());
			
			String url = LzzFileMgr.getSingleFileUrlByFileGroupId(sin.getTypicalImg());
			obj.put("imgUrl", url);
			array.add(obj);
		}
		
		return array;
	}
}
