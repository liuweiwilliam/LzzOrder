package com.lzz.order.mgr;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lzz.order.pojo.LzzCart;
import com.lzz.order.pojo.LzzGoodFormat;
import com.lzz.order.pojo.LzzGoodTaste;
import com.lzz.order.pojo.LzzOrder;
import com.lzz.order.pojo.LzzOrderInfo;
import com.lzz.order.pojo.LzzUserAddress;
import com.lzz.order.service.LzzCartService;
import com.lzz.order.service.LzzGoodService;
import com.lzz.order.service.LzzOrderService;
import com.lzz.order.service.LzzUserService;
import com.lzz.order.utils.LzzConstString;
import com.lzz.order.utils.LzzDateUtil;

public class LzzOrderMgr {
	/**
	 * 通过orderInfo ID 获取用户ID
	 * @param order_info_id
	 * @return
	 */
	public static String getUserIdByOrderInfoId(String order_info_id){
		if(null==order_info_id
				|| "".equals(order_info_id)){
			return null;
		}
		
		LzzOrderInfo order_info = LzzOrderService.self().getLzzOrderInfoById(order_info_id);
		if(null==order_info) return null;
		
		String order_id = order_info.getOrderId();
		LzzOrder order = LzzOrderService.self().getLzzOrderById(order_id);
		if(null==order) return null;
		
		String user_id = order.getUserId();
		if("".equals(user_id)) return null;
		
		return user_id;
	}
	
	/**
	 * 获取订单中商品规格信息
	 * @param order_info_id
	 * @return
	 */
	public static String getGoodFormatByOrderInfoId(String order_info_id){
		if(null==order_info_id
				|| "".equals(order_info_id)){
			return null;
		}
		
		LzzOrderInfo order_info = LzzOrderService.self().getLzzOrderInfoById(order_info_id);
		if(null==order_info) return null;
		
		String format_id = order_info.getFormat();
		LzzGoodFormat format = LzzGoodService.self().getLzzGoodFormatById(format_id);
		if(null==format) return null;
		
		return format.getFormatName();
	}
	
	/**
	 * 通过orderinfoid获取商品口味信息
	 * @param order_info_id
	 * @return
	 */
	public static String getGoodTasteByOrderInfoId(String order_info_id){
		if(null==order_info_id
				|| "".equals(order_info_id)){
			return null;
		}
		
		LzzOrderInfo order_info = LzzOrderService.self().getLzzOrderInfoById(order_info_id);
		if(null==order_info) return null;
		
		String taste_id = order_info.getTaste();
		LzzGoodTaste taste = LzzGoodService.self().getLzzGoodTasteById(taste_id);
		if(null==taste) return null;
		
		return taste.getTasteName();
	}
	
	/**
	 * 通过购物车创建预订单信息
	 * @param userid
	 * @return
	 */
	public static String submitOrderFromCart(String userid){
		//创建订单信息
		String orderId = LzzIDMgr.self().getID();
		LzzOrder order = new LzzOrder();
		order.setId(orderId);
		order.setUserId(userid);
		order.setOrderNum(getOrderNum(userid));
		order.setFromWhere(LzzConstString.smOrderFrom_Cart);
		order.setDr("1");
		LzzOrderService.self().saveLzzOrder(order);
		
		List<LzzCart> carts = LzzCartService.self().getLzzCartListByUserId(userid);
		for(LzzCart sin : carts){
			if(null==sin.getCount()
					|| "".equals(sin.getCount())
					|| Integer.valueOf(sin.getCount())<=0
					|| null==sin.getIsSelected()
					|| !"1".equals(sin.getIsSelected())){
				continue;
			}
			LzzOrderInfo order_info = new LzzOrderInfo();
			order_info.setId(LzzIDMgr.self().getID());
			order_info.setOrderId(orderId);
			order_info.setGoodId(sin.getGoodId());
			String count = sin.getCount();
			order_info.setCount(count);
			order_info.setFormat(sin.getFormatId());
			order_info.setTaste(sin.getTasteId());
			String price = LzzGoodMgr.getPriceByGoodFormatId(userid, sin.getFormatId());
			order_info.setPrice(price);
			order_info.setTotalPrice((Float.valueOf(price)*Float.valueOf(count))+"");
			order_info.setDr("1");
			LzzOrderService.self().saveLzzOrderInfo(order_info);
		}
		
		return orderId;
	}
	
	/**
	 * 通过单个商品提交订单
	 * @param userid
	 * @param goodId
	 * @param formatId
	 * @param tasteId
	 * @return
	 */
	public static String submitOrderFromSingleGood(String userid, String goodId, String formatId, String tasteId){
		//创建订单信息
		String orderId = LzzIDMgr.self().getID();
		LzzOrder order = new LzzOrder();
		order.setId(orderId);
		order.setUserId(userid);
		order.setOrderNum(getOrderNum(userid));
		order.setFromWhere(LzzConstString.smOrderFrom_SingleGood);
		order.setDr("1");
		LzzOrderService.self().saveLzzOrder(order);
		
		LzzOrderInfo order_info = new LzzOrderInfo();
		order_info.setId(LzzIDMgr.self().getID());
		order_info.setOrderId(orderId);
		order_info.setGoodId(goodId);
		String count = "1";
		order_info.setCount(count);
		order_info.setFormat(formatId);
		order_info.setTaste(tasteId);
		String price = LzzGoodMgr.getPriceByGoodFormatId(userid, formatId);
		order_info.setPrice(price);
		order_info.setTotalPrice((Float.valueOf(price)*Float.valueOf(count))+"");
		order_info.setDr("1");
		LzzOrderService.self().saveLzzOrderInfo(order_info);
		
		return orderId;
	}
	
	/**
	 * 获取订单编号
	 * @param userid
	 * @return
	 */
	public static String getOrderNum(String userid){
		String date = LzzDateUtil.getNow("d2");
		return date+userid+LzzIDMgr.self().getID();
	}
	
	/**
	 * 获取订单中商品信息
	 * @param order_id
	 * @return
	 */
	public static JSONArray getOrderGoodsInfo(String order_id){
		JSONArray rslt = new JSONArray();
		
		List<LzzOrderInfo> infos = getLzzOrderInfoListByOrderIdIgnoreDr(order_id);
		for(LzzOrderInfo sin : infos){
			JSONObject obj = new JSONObject();
			obj.put("id", sin.getId());
			obj.put("imgUrl", LzzGoodMgr.getGoodTypicalImgUrl(sin.getGoodId()));
			obj.put("name", LzzGoodMgr.getGoodNameById(sin.getGoodId()));
			obj.put("format", LzzGoodMgr.getFormatNameByGoodFormatId(sin.getFormat()));
			obj.put("taste", LzzGoodMgr.getTasteNameByGoodTasteId(sin.getTaste()));
			String count = sin.getCount();
			obj.put("count", count);
			String price = sin.getPrice();
			obj.put("price", price);
			rslt.add(obj);
		}
		
		return rslt;
	}
	
	/**
	 * 获取订单基础信息
	 * @param order_id
	 * @return
	 */
	public static JSONObject getOrderInfo(String order_id){
		JSONObject rslt = new JSONObject();
		if(null==order_id) return rslt;
		
		LzzOrder sin = LzzOrderService.self().getLzzOrderById(order_id);
		if(null==sin) return rslt;
		
		rslt.put("id", sin.getId());
		rslt.put("orderNum", sin.getOrderNum());
		rslt.put("userName", LzzUserMgr.getUserNicknameByUserId(sin.getUserId()));
		rslt.put("phone", LzzUserMgr.getUserPhoneByUserId(sin.getUserId()));
		rslt.put("createTime", sin.getCreateTime());
		
		return rslt;
	}
	
	/**
	 * 获取订单总价格
	 * @param order_id
	 * @return
	 */
	public static String getOrderTotalPrice(String order_id){
		List<LzzOrderInfo> infos = getLzzOrderInfoListByOrderIdIgnoreDr(order_id);
		Float total_price = 0f;
		for(LzzOrderInfo sin : infos){
			String count = sin.getCount();
			
			String price = sin.getPrice();
			if(null!=sin.getIsDiscount()
					&& "1".equals(sin.getIsDiscount())
					&& null!=sin.getDiscountPrice()
					&& !"".equals(sin.getDiscountPrice())){
				price = sin.getDiscountPrice();
			}
			
			total_price += Float.valueOf(price) * Integer.valueOf(count);
		}
		
		return total_price+"";
	}
	/**
	 * 根据订单ID获取订单内容
	 * @param orderId
	 * @return
	 */
	public static List<LzzOrderInfo> getLzzOrderInfoListByOrderIdIgnoreDr(String orderId){
		List<LzzOrderInfo> array_all = LzzOrderService.self().getAllLzzOrderInfoIgnoreDr();
		List<LzzOrderInfo> rslt = new ArrayList<LzzOrderInfo>();
		
		for(int i=0;i<array_all.size();i++){
			if(array_all.get(i).getOrderId().equals(orderId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	/**
	 * 通过orderinfoid获取商品规格和口味信息
	 * @param orderInfoId
	 * @return
	 */
	public static String getFormatAndTasteByOrderInfoId(String orderInfoId) {
		// TODO Auto-generated method stub
		if(null==orderInfoId || "".equals(orderInfoId)) return null;
		
		String format = LzzOrderMgr.getGoodFormatByOrderInfoId(orderInfoId);
		String taste = LzzOrderMgr.getGoodTasteByOrderInfoId(orderInfoId);
		
		return format + "-" + taste;
	}
	
	/**
	 * 获取订单的地址信息
	 * @param order_id
	 * @return
	 */
	public static LzzUserAddress getOrderAddress(String order_id){
		LzzOrder order = LzzOrderService.self().getLzzOrderById(order_id);
		if(null==order){
			return null;
		}
		
		if(null==order.getAddressMod()
				|| "".equals(order.getAddressMod())){
			String address_id = order.getAddress();
			return LzzUserService.self().getLzzUserAddressById(address_id);
		}else{
			LzzUserAddress address = new LzzUserAddress();
			JSONObject obj = JSONObject.fromObject(order.getAddressMod());
			address.setPerson(obj.getString("person"));
			address.setPhone(obj.getString("phone"));
			address.setProvince(obj.getString("province"));
			address.setCity(obj.getString("city"));
			address.setCounty(obj.getString("county"));
			return address;
		}
	}
}
