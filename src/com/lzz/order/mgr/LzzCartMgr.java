package com.lzz.order.mgr;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lzz.order.pojo.LzzCart;
import com.lzz.order.service.LzzCartService;

public class LzzCartMgr {
	/**
	 * 通过用户ID  商品ID  规格ID  口味ID  查询购物车记录
	 * @param userid
	 * @param goodId
	 * @param formatId
	 * @param tasteId
	 */
	public static void addGoodToCart(String userid, String goodId, String formatId, String tasteId){
		LzzCart cart = LzzCartService.self().getLzzCartByUserIdAndGoodIdAndFormatIdAndFormatId(userid, goodId, formatId, tasteId);
		if(null==cart){
			//购物车中没有这件商品
			cart = new LzzCart();
			cart.setId(LzzIDMgr.self().getID());
			cart.setUserId(userid);
			cart.setGoodId(goodId);
			cart.setFormatId(formatId);
			cart.setTasteId(tasteId);
			cart.setCount("1");
			LzzCartService.self().saveLzzCart(cart);
		}else{
			int count = Integer.valueOf(cart.getCount());
			cart.setCount((count+1)+"");
			LzzCartService.self().updateLzzCart(cart);
		}
	}
	
	/**
	 * 将商品从购物车中移除
	 * @param userid
	 * @param goodId
	 * @param formatId
	 * @param tasteId
	 */
	public static void removeGoodFromCart(String userid, String goodId, String formatId, String tasteId){
		LzzCart cart = LzzCartService.self().getLzzCartByUserIdAndGoodIdAndFormatIdAndFormatId(userid, goodId, formatId, tasteId);
		if(null!=cart){
			LzzCartService.self().delLzzCart(cart);
		}
	}
	
	/**
	 * 获取购物车商品列表
	 * @param userid
	 * @return
	 */
	public static JSONObject getCartInfo(String userid){
		JSONObject rslt = new JSONObject();
		List<LzzCart> list = LzzCartService.self().getLzzCartListByUserId(userid);
		
		Float total_price = 0f;
		JSONArray cart = new JSONArray();
		for(LzzCart sin : list){
			JSONObject obj = new JSONObject();
			obj.put("id", sin.getId());
			obj.put("name", LzzGoodMgr.getGoodNameById(sin.getGoodId()));
			String price = LzzGoodMgr.getPriceByGoodFormatId(userid, sin.getFormatId());
			obj.put("price", price);
			String count = sin.getCount();
			obj.put("count", count);
			Float sin_total_price = Float.valueOf(count)*Float.valueOf(price);
			obj.put("totalPrice", sin_total_price);
			obj.put("formatId", sin.getFormatId());
			obj.put("format", LzzGoodMgr.getFormatNameByGoodFormatId(sin.getFormatId()));
			obj.put("taste", LzzGoodMgr.getTasteNameByGoodTasteId(sin.getTasteId()));
			obj.put("tasteId", sin.getTasteId());
			obj.put("imgUrl", LzzGoodMgr.getGoodTypicalImgUrl(sin.getGoodId()));
			obj.put("checked", sin.getIsSelected());
			
			if("1".equals(sin.getIsSelected())){
				total_price += sin_total_price;
			}
			cart.add(obj);
		}
		rslt.put("cartList", cart);
		rslt.put("totalPrice", total_price);
		return rslt;
	}
	
	public static void checkAllCartItem(String userid, String checkAll){
		List<LzzCart> list = LzzCartService.self().getLzzCartListByUserId(userid);
		for(LzzCart sin : list){
			sin.setIsSelected(checkAll);
			LzzCartService.self().updateLzzCart(sin);
		}
	}

	public static void addCartItemCount(String cartId) {
		// TODO Auto-generated method stub
		LzzCart cart = LzzCartService.self().getLzzCartById(cartId);
		String count = cart.getCount();
		if(null==count
				|| "".equals(count)){
			count = "1";
		}
		cart.setCount((Integer.valueOf(count)+1)+"");
		LzzCartService.self().updateLzzCart(cart);
	}
	
	public static void setCartItemCount(String cartId, String count) {
		// TODO Auto-generated method stub
		LzzCart cart = LzzCartService.self().getLzzCartById(cartId);
		if(null==count
				|| "".equals(count)){
			count = "1";
		}
		cart.setCount(count);
		LzzCartService.self().updateLzzCart(cart);
	}

	public static void descCartItemCount(String cartId) {
		// TODO Auto-generated method stub
		LzzCart cart = LzzCartService.self().getLzzCartById(cartId);
		String count = cart.getCount();
		if(null==count
				|| "".equals(count)
				|| "0".equals(count)) return;
		cart.setCount((Integer.valueOf(count)-1)+"");
		LzzCartService.self().updateLzzCart(cart);
	}
}
