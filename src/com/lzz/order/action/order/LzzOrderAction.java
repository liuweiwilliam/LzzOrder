package com.lzz.order.action.order;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Transaction;

import com.lzz.order.action.LzzBaseManagerAction;
import com.lzz.order.factory.LzzFactory;
import com.lzz.order.mgr.LzzCacheMgr;
import com.lzz.order.mgr.LzzCartMgr;
import com.lzz.order.mgr.LzzDictionaryMgr;
import com.lzz.order.mgr.LzzGoodMgr;
import com.lzz.order.mgr.LzzIDMgr;
import com.lzz.order.mgr.LzzOrderMgr;
import com.lzz.order.mgr.LzzUserMgr;
import com.lzz.order.pojo.LzzCart;
import com.lzz.order.pojo.LzzOrder;
import com.lzz.order.pojo.LzzOrderInfo;
import com.lzz.order.service.LzzCartService;
import com.lzz.order.service.LzzOrderService;
import com.lzz.order.utils.LzzConstString;
import com.lzz.order.utils.LzzPaging;

public class LzzOrderAction extends LzzBaseManagerAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8660301458085809266L;
	public String cartList;
	public String submitOrderFromCart(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			String orderId = LzzOrderMgr.submitOrderFromCart(curUserId);
			rsl.put("orderId", orderId);
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
	
	public String goodId;
	public String formatId;
	public String tasteId;
	public String submitOrderFromSingleGood(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			String orderId = LzzOrderMgr.submitOrderFromSingleGood(curUserId, goodId, formatId, tasteId);
			rsl.put("orderId", orderId);
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
	
	public String orig;
	public String carInfo;
	/**
	 * 获取订单详情
	 * @return
	 */
	public String getOrderInfo(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONArray infos = new JSONArray();
			String total_price = "";
			if(LzzConstString.smOrderFrom_Cart.equals(orig)){
				carInfo = new String(carInfo.getBytes("iso-8859-1"),"utf-8");
				//JSONObject cart_info = LzzCartMgr.getCartInfo(curUserId);
				JSONObject cart_info = getCartCheckedInfo(carInfo);
				
				infos = cart_info.getJSONArray("cartList");
				
				/*for(int i=0; i<infos.size(); i++){
					if(!"1".equals(infos.getJSONObject(i).getString("checked"))){
						infos.remove(i);
						i--;
					}
				}*/
				
				total_price = cart_info.getString("totalPrice");
			}
			
			if(LzzConstString.smOrderFrom_SingleGood.equals(orig)){
				String price = LzzGoodMgr.getPriceByGoodFormatId(curUserId, formatId);
				JSONObject obj = new JSONObject();
				obj.put("id", LzzIDMgr.self().getID());
				obj.put("imgUrl", LzzGoodMgr.getGoodTypicalImgUrl(goodId));
				obj.put("name", LzzGoodMgr.getGoodNameById(goodId));
				obj.put("formatId", formatId);
				obj.put("format", LzzGoodMgr.getFormatNameByGoodFormatId(formatId));
				obj.put("taste", LzzGoodMgr.getTasteNameByGoodTasteId(tasteId));
				obj.put("tasteId", tasteId);
				obj.put("count", "1");
				obj.put("price", price);
				total_price = price;
				infos.add(obj);
			}
			rsl.put("orderInfoList", infos);
			rsl.put("totalPrice", total_price);
			
			JSONObject usual_address = LzzUserMgr.getUserUsualAddress(curUserId);
			rsl.put("address", usual_address);
			
			rsl.put("expressList", LzzDictionaryMgr.getDicArrayByCategory(LzzConstString.smExpressMode));
			rsl.put("expressName", LzzDictionaryMgr.getFirstValueByCategory(LzzConstString.smExpressMode));
			rsl.put("expressId", LzzDictionaryMgr.getFirstIDByCategory(LzzConstString.smExpressMode));
			
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
	
	private JSONObject getCartCheckedInfo(String carInfo2) {
		// TODO Auto-generated method stub
		JSONArray array = JSONArray.fromObject(carInfo2);
		JSONArray cartList = new JSONArray();
		JSONObject infos = new JSONObject();
		Float total_price = 0F;
		for(int i=0; i<array.size(); i++){
			JSONObject sin = (JSONObject) array.get(i);
			String checked = sin.getString("checked");
			if(!"1".equals(checked)){
				continue;
			}
			String cart_id = sin.getString("id");
			LzzCart cart = LzzCartService.self().getLzzCartById(cart_id);
			
			JSONObject obj = new JSONObject();
			String good_id = cart.getGoodId();
			obj.put("id", LzzIDMgr.self().getID());
			String format_id = cart.getFormatId();
			String price = LzzGoodMgr.getPriceByGoodFormatId(curUserId, format_id);
			String count = sin.getString("count");
			String taste_id = cart.getTasteId();
			obj.put("imgUrl", LzzGoodMgr.getGoodTypicalImgUrl(good_id));
			obj.put("name", LzzGoodMgr.getGoodNameById(good_id));
			obj.put("formatId", format_id);
			obj.put("format", LzzGoodMgr.getFormatNameByGoodFormatId(format_id));
			obj.put("taste", LzzGoodMgr.getTasteNameByGoodTasteId(taste_id));
			obj.put("tasteId", taste_id);
			obj.put("count", count);
			obj.put("price", price);
			total_price += Float.valueOf(price) * Float.valueOf(count);
			cartList.add(obj);
		}
		infos.put("cartList", cartList);
		infos.put("totalPrice", total_price+"");
		
		return infos;
	}

	public String orderId;
	/**
	 * 获取单个订单详情
	 * @return
	 */
	public String getSingleOrder(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONArray infos = LzzOrderMgr.getOrderGoodsInfo(orderId);
			rsl.put("orderInfo", infos);
			
			LzzOrder order = LzzOrderService.self().getLzzOrderById(orderId);
			rsl.put("expressList", LzzDictionaryMgr.getDicArrayByCategory(LzzConstString.smExpressMode));
			rsl.put("expressId", order.getExpressCompany());
			
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
	
	
	public String expressCmp;
	public String expressNum;
	/**
	 * 订单发货
	 * @return
	 */
	public String express(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			LzzOrder order = LzzOrderService.self().getLzzOrderById(orderId);
			order.setStatus(LzzDictionaryMgr.getIDByCategoryAndValue(LzzConstString.smOrderStatus, LzzConstString.smOrderStatus_ToReceive));
			LzzOrderService.self().updateLzzOrder(order);
			
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
	 * 确认收货
	 * @return
	 */
	public String confiemReceived(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");
		
		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		
		try {
			LzzOrder order = LzzOrderService.self().getLzzOrderById(orderId);
			order.setStatus(LzzDictionaryMgr.getIDByCategoryAndValue(LzzConstString.smOrderStatus, LzzConstString.smOrderStatus_ToEvaluate));
			LzzOrderService.self().updateLzzOrder(order);
			
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
	
	public String address;
	public String orderInfoList;
	public String expressId;
	public String comment;
	public String submitOrder(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			orderInfoList = new String(orderInfoList.getBytes("iso-8859-1"),"utf-8");
			address = new String(address.getBytes("iso-8859-1"),"utf-8");
			comment = new String(comment.getBytes("iso-8859-1"),"utf-8");
			JSONArray infos = JSONArray.fromObject(orderInfoList);
			JSONObject addr = JSONObject.fromObject(address);
			
			String orderId = LzzIDMgr.self().getID();
			LzzOrder order = new LzzOrder();
			order.setId(orderId);
			order.setUserId(curUserId);
			order.setOrderNum(LzzOrderMgr.getOrderNum(curUserId));
			order.setFromWhere(orig);
			order.setAddress(addr.getString("id"));
			order.setComment(comment);
			order.setStatus(LzzDictionaryMgr.getIDByCategoryAndValue(LzzConstString.smOrderStatus, LzzConstString.smOrderStatus_ToExpress));
			order.setExpressCompany(expressId);
			LzzOrderService.self().saveLzzOrder(order);
			
			for(int i=0; i<infos.size(); i++){
				JSONObject sin = infos.getJSONObject(i);
				LzzOrderInfo order_info = new LzzOrderInfo();
				order_info.setId(LzzIDMgr.self().getID());
				order_info.setOrderId(orderId);
				String format_id = sin.getString("formatId");
				order_info.setGoodId(LzzGoodMgr.getGoodIdByFormatId(format_id));
				String count = sin.getString("count");
				order_info.setCount(count);
				order_info.setFormat(format_id);
				String taste_id = sin.getString("tasteId");
				order_info.setTaste(taste_id);
				String price = LzzGoodMgr.getPriceByGoodFormatId(curUserId, format_id);
				order_info.setPrice(price);
				order_info.setTotalPrice((Float.valueOf(price)*Float.valueOf(count))+"");
				String is_discount = LzzGoodMgr.isFormatInDiscount(format_id);
				order_info.setIsDiscount(is_discount);
				order_info.setDiscountPrice("1".equals(is_discount)?LzzGoodMgr.getFormatDiscountPrice(format_id):price);
				LzzOrderService.self().saveLzzOrderInfo(order_info);
				
				/**
				 * 从购物车中提价的订单，需要将购物车中对应物品移除
				 */
				if(LzzConstString.smOrderFrom_Cart.equals(orig)){
					LzzCartMgr.removeGoodFromCart(curUserId, LzzGoodMgr.getGoodIdByFormatId(format_id), format_id, taste_id);
				}
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
	
	public String status;
	public String filterPar;
	/**
	 * 根据订单状态获取订单列表
	 * @return
	 */
	public String getOrders(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			status = new String(status.getBytes("iso-8859-1"),"utf-8");
			JSONArray orderList = new JSONArray();
			
			List<LzzOrder> orders = new ArrayList<LzzOrder>();
			if(LzzConstString.smOrderStatus_All.equals(status)){
				orders = LzzOrderService.self().getLzzOrderListByUserId(curUserId);
			}else{
				String status_id = LzzDictionaryMgr.getIDByCategoryAndValue(LzzConstString.smOrderStatus, status);
				orders = LzzOrderService.self().getLzzOrderListByUserIdAndStatus(curUserId, status_id);
			}
			
			for(LzzOrder sin : orders){
				JSONObject order_json = new JSONObject();
				order_json.put("id", sin.getId());
				order_json.put("status", LzzDictionaryMgr.getValueById(sin.getStatus()));
				order_json.put("freight", sin.getExpressFee());
				order_json.put("totalPrice", LzzOrderMgr.getOrderTotalPrice(sin.getId()));
				List<LzzOrderInfo> infos = LzzOrderService.self().getLzzOrderInfoListByOrderId(sin.getId());
				JSONArray goodList = new JSONArray();
				for(LzzOrderInfo sin_info : infos){
					JSONObject info_json = new JSONObject();
					info_json.put("id", sin_info.getId());
					info_json.put("name", LzzGoodMgr.getGoodNameById(sin_info.getGoodId()));
					info_json.put("typicalImg", LzzGoodMgr.getGoodTypicalImgUrl(sin_info.getGoodId()));
					String format_id = sin_info.getFormat();
					info_json.put("format", LzzGoodMgr.getFormatNameByGoodFormatId(format_id));
					String taste_id = sin_info.getTaste();
					info_json.put("taste", LzzGoodMgr.getTasteNameByGoodTasteId(taste_id));
					info_json.put("count", sin_info.getCount());
					info_json.put("price", sin_info.getPrice());
					info_json.put("isDiscount", sin_info.getIsDiscount());
					info_json.put("discountPrice", sin_info.getDiscountPrice());
					
					goodList.add(info_json);
				}
				order_json.put("goodList", goodList);
				
				orderList.add(order_json);
			}
			
			rsl.put("orderList", orderList);
			rsl.put("currentTab", status);
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
	
	public String pageSize;
	public String curPage;
	/**
	 * 获取待发货订单
	 * @return
	 */
	public String getOrderToExpress(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONArray orderList = new JSONArray();
			
			String status_id = LzzDictionaryMgr.getIDByCategoryAndValue(LzzConstString.smOrderStatus, LzzConstString.smOrderStatus_ToExpress);
			List<LzzOrder> orders = LzzOrderService.self().getLzzOrderListByStatus(status_id);
			int start = Integer.valueOf(pageSize) * (Integer.valueOf(curPage)-1);
			int end = start+Integer.valueOf(pageSize);
			for(int i=0; i<orders.size(); i++){
				if(i<start
						|| i>end){
					continue;
				}
				JSONObject order_json = new JSONObject();
				LzzOrder sin = orders.get(i);
				order_json.put("id", sin.getId());
				order_json.put("orderNum", sin.getOrderNum());
				order_json.put("userName", LzzUserMgr.getUserNicknameByUserId(sin.getUserId()));
				order_json.put("phone", LzzUserMgr.getUserPhoneByUserId(sin.getUserId()));
				order_json.put("createTime", sin.getCreateTime());
				orderList.add(order_json);
			}
			
			rsl.put("maxPage", orders.size()/Integer.valueOf(pageSize)+1);
			rsl.put("totalSize", orders.size());
			rsl.put("orderList", orderList);
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
	 * 获取待发货订单
	 * @return
	 */
	public String getOrdersByStatus(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			JSONArray orderList = new JSONArray();
			List<LzzOrder> orders = new ArrayList<LzzOrder>();
			if(LzzConstString.smOrderStatus_All.equals(status)){
				orders = LzzOrderService.self().getAllValidLzzOrder();
			}else{
				String status_id = LzzDictionaryMgr.getIDByCategoryAndValue(LzzConstString.smOrderStatus, status);
				orders = LzzOrderService.self().getLzzOrderListByStatus(status_id);
			}
			
			for(int i=0; i<orders.size(); i++){
				LzzOrder sin = orders.get(i);
				JSONObject order_json = LzzOrderMgr.getOrderInfo(sin.getId());
				orderList.add(order_json);
			}
			
			orderList = LzzPaging.get(orderList, Integer.valueOf(curPage), Integer.valueOf(pageSize));
			
			rsl.put("maxPage", orders.size()/Integer.valueOf(pageSize)+1);
			rsl.put("totalSize", orders.size());
			rsl.put("orderList", orderList);
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
