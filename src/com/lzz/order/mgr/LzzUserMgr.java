package com.lzz.order.mgr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lzz.order.pojo.LzzDictionary;
import com.lzz.order.pojo.LzzOrder;
import com.lzz.order.pojo.LzzUser;
import com.lzz.order.pojo.LzzUserAddress;
import com.lzz.order.pojo.LzzUserGoodInterested;
import com.lzz.order.pojo.LzzUserGoodViewed;
import com.lzz.order.pojo.LzzUserSession;
import com.lzz.order.service.LzzDictionaryService;
import com.lzz.order.service.LzzOrderService;
import com.lzz.order.service.LzzUserService;
import com.lzz.order.utils.LzzConstString;
import com.lzz.order.utils.LzzProperties;

public class LzzUserMgr {
	/**
	 * 获取用户地址JSONArray
	 * @param userid
	 * @return 格式[{}]
	 */
	public static JSONArray getUserAddressList(String userid){
		JSONArray rslt = new JSONArray();
		List<LzzUserAddress> addresses = LzzUserService.self().getLzzUserAddressListByUserId(userid);
		
		for(LzzUserAddress sin : addresses){
			JSONObject obj = new JSONObject();
			obj.put("id", sin.getId());
			obj.put("name", sin.getPerson());
			obj.put("phone", sin.getPhone());
			obj.put("detail", sin.getDetail());
			obj.put("usualFlag", sin.getUsualFlag());
			obj.put("province", sin.getProvince());
			obj.put("city", sin.getCity());
			obj.put("county", sin.getCounty());
			rslt.add(obj);
		}
		
		return rslt;
	}
	
	/**
	 * 获取用户常用地址信息
	 * @param userid
	 * @return
	 */
	public static JSONObject getUserUsualAddress(String userid){
		JSONObject rslt = new JSONObject();
		LzzUserAddress address = LzzUserService.self().getLzzUserAddressByUserIdAndUsualFlag(userid, "1");
		
		if(null==address) return rslt;
		
		rslt.put("id", address.getId());
		rslt.put("name", address.getPerson());
		rslt.put("phone", address.getPhone());
		rslt.put("detail", address.getDetail());
		rslt.put("usualFlag", address.getUsualFlag());
		rslt.put("province", address.getProvince());
		rslt.put("city", address.getCity());
		rslt.put("county", address.getCounty());
		return rslt;
	}

	/**
	 * 新增或者更新地址
	 * @param userid
	 * @param id
	 * @param name
	 * @param phone
	 * @param area
	 * @param detail
	 * @param usualFlag
	 */
	public static void addOrUpdateAddress(String userid, String id, String name, String phone,
			JSONArray area, String detail, String usualFlag) {
		// TODO Auto-generated method stub
		LzzUserAddress addr = null;
		if(null==id
				|| "".equals(id)
				|| "null".equals(id)){
			addr = new LzzUserAddress();
			addr.setId(LzzIDMgr.self().getID());
		}else{
			addr = LzzUserService.self().getLzzUserAddressById(id);
		}
		
		addr.setUserId(userid);
		addr.setPerson(name);
		addr.setPhone(phone);
		
		addr.setProvince(area.getString(0));
		addr.setCity(area.getString(1));
		addr.setCounty(area.getString(2));
		String area_str = area.getString(0) + area.getString(1) + area.getString(2);
		addr.setDetail(area_str + detail);
		addr.setUsualFlag(usualFlag);
		
		if(usualFlag.equals("1")){
			LzzUserAddress usual_addr = LzzUserService.self().getLzzUserAddressByUserIdAndUsualFlag(userid, "1");
			if(null!=usual_addr
					&& !usual_addr.getId().equals(addr.getId())){
				usual_addr.setUsualFlag("0");
				LzzUserService.self().updateLzzUserAddress(usual_addr);
			}
		}
		
		if(null==id
				|| "".equals(id)
				|| "null".equals(id)){
			LzzUserService.self().saveLzzUserAddress(addr);
		}else{
			LzzUserService.self().updateLzzUserAddress(addr);
		}
	}
	
	/**
	 * 获取用户第一天地址记录
	 * @param userid
	 * @return
	 */
	public static LzzUserAddress getUserFirstAddress(String userid){
		List<LzzUserAddress> rslt = LzzUserService.self().getLzzUserAddressListByUserId(userid);
		if(rslt.size()==0) return null;
		return rslt.get(0);
	}

	public static void deleteAddress(String userid, String addressId) {
		// TODO Auto-generated method stub
		LzzUserAddress addr = LzzUserService.self().getLzzUserAddressById(addressId);
		LzzUserAddress usual = LzzUserService.self().getLzzUserAddressByUserIdAndUsualFlag(userid, "1");
		LzzUserService.self().delLzzUserAddress(addr);
		if(usual.getId().equals(addr.getId())){
			//删除的是常用地址
			LzzUserAddress first = getUserFirstAddress(userid);
			if(null!=first){
				first.setUsualFlag("1");
				LzzUserService.self().updateLzzUserAddress(first);
			}
		}
	}

	/**
	 * 通过orderinfoid获取用户对象
	 * @param orderInfoId
	 * @return
	 */
	public static LzzUser getLzzUserByOrderInfoId(String orderInfoId) {
		// TODO Auto-generated method stub
		if(null==orderInfoId || "".equals(orderInfoId)) return null;
		
		String userid = LzzOrderMgr.getUserIdByOrderInfoId(orderInfoId);
		LzzUser user = LzzUserService.self().getLzzUserById(userid);
		
		return user;
	}
	
	/**
	 * 获取用户昵称
	 * @param userid
	 * @return
	 */
	public static String getUserNicknameByUserId(String userid){
		LzzUser user = LzzUserService.self().getLzzUserById(userid);
		if(null==user) return null;
		return user.getNick_name(); 
	}
	
	/**
	 * 获取用户联系电话
	 * @param userid
	 * @return
	 */
	public static String getUserPhoneByUserId(String userid){
		LzzUser user = LzzUserService.self().getLzzUserById(userid);
		if(null==user) return null;
		if(null==user.getPhone() || "".equals(user.getPhone())){
			//通过地址信息获取
			JSONObject obj = getUserUsualAddress(userid);
			return obj.getString("phone");
		}
		
		return user.getPhone();
	}
	
	/**
	 * 获取用户头像地址，如果用户头像地址不存在，返回默认头像地址
	 * @param userid
	 * @return
	 */
	public static String getUserAvatarUrl(String userid){
		LzzUser user = LzzUserService.self().getLzzUserById(userid);
		if(null==user) return null;
		
		String url = user.getAvatarUrl();
		if(null==url
				|| "".equals(url)){
			url = LzzProperties.getProjectUrl() + LzzProperties.getDefaultAvatarUrl();
		}
		
		return url;
	}
	
	/**
	 * 获取用户等级
	 * @param userid
	 * @return
	 */
	public static String getUserLevel(String userid){
		LzzUser user = LzzUserService.self().getLzzUserById(userid);
		if(null==user) return null;
		
		return user.getLevel();
	}
	
	/**
	 * 用户增加积分
	 * @param userid
	 * @param integration
	 */
	public static void addUserIntegration(String userid, int integration){
		LzzUser user = LzzUserService.self().getLzzUserById(userid);
		String inte = user.getTotal_integration();
		if(null==inte
				|| "".equals(inte)){
			inte = "0";
		}
		String cur_inte = user.getCur_integration();
		if(null==cur_inte
				|| "".equals(cur_inte)){
			cur_inte = "0";
		}
		
		inte = (Integer.valueOf(inte) + integration) + "";
		cur_inte = (Integer.valueOf(cur_inte) + integration) + "";
		user.setCur_integration(cur_inte);
		user.setTotal_integration(inte);
		String level = getUserLevelByIntegration(userid, inte);
		user.setLevel(level);
		LzzUserService.self().updateLzzUser(user);
	}
	
	/**
	 * 通过积分值获取积分值所处用户等级
	 * @param inte
	 * @return
	 */
	public static String getUserLevelByIntegration(String userid, String inte){
		String pifa_level = LzzDictionaryService.self().getLzzDictionaryByCategoryAndValue(LzzConstString.smUserLevel, LzzConstString.smUserLevel_wholeSaler).getId();
		if(pifa_level.equals(getUserLevel(userid))){
			return pifa_level;
		}
		
		String cur_level = "1";
		LzzDictionary level = LzzDictionaryService.self().getLzzDictionaryByCategoryAndInnerLevel(LzzConstString.smUserLevel, cur_level);
		while(null!=level){
			LzzDictionary next_level = LzzDictionaryService.self().getLzzDictionaryByCategoryAndInnerLevel(LzzConstString.smUserLevel, (Integer.valueOf(cur_level)+1)+"");
			if(null==next_level){
				return level.getId();
			}
			
			if(Float.valueOf(inte) > Float.valueOf(level.getInnerValue())
					&& Float.valueOf(inte) < Float.valueOf(next_level.getInnerValue())){
				return level.getId();
			}
			level = next_level;
			cur_level = level.getInnerLevel();
		}
		
		String max_level = LzzDictionaryService.self().getLzzDictionaryByCategoryAndValue(LzzConstString.smUserLevel, LzzConstString.smUserLevel_diamond).getId();
		return max_level;
	}
	
	/**
	 * 获取用户收藏的商品数
	 * @param curUserId
	 * @return
	 */
	public static String getInterestedCount(String curUserId) {
		// TODO Auto-generated method stub
		List<LzzUserGoodInterested> rslt = LzzUserService.self().getLzzUserGoodInterestedListByUserId(curUserId);
		return rslt.size()+"";
	}

	/**
	 * 获取用户查看过的商品数量
	 * @param curUserId
	 * @return
	 */
	public static Object getViewGoodCount(String curUserId) {
		// TODO Auto-generated method stub
		List<LzzUserGoodViewed> rslt = LzzUserService.self().getLzzUserGoodViewedListByUserId(curUserId);
		return rslt.size()+"";
	}

	/**
	 * 获取客户下一等级ID
	 * @param curUserId
	 * @return 如果为null，说明用户等级为批发商或者最高等级
	 */
	public static String getUserNextLevel(String curUserId) {
		// TODO Auto-generated method stub
		String level = getUserLevel(curUserId);
		LzzDictionary dic = LzzDictionaryService.self().getLzzDictionaryById(level);
		String inner_level = dic.getInnerLevel();
		if(null==inner_level){
			//是批发商
			return null;
		}
		
		LzzDictionary next_dic = LzzDictionaryService.self().getLzzDictionaryByCategoryAndInnerLevel(dic.getCategory(), (Integer.valueOf(inner_level)+1)+"");
		if(null==next_dic){
			return null;
		}
		
		return next_dic.getId();
	}

	/**
	 * 获取客户下一等级积分
	 * @param curUserId
	 * @return 如果为null，说名为批发商或者最高等级，正常返回表示下一等级积分
	 */
	public static String getUserNextLevelIntegration(String curUserId) {
		// TODO Auto-generated method stub
		String next_level = getUserNextLevel(curUserId);
		if(null==next_level) return null;
		
		return LzzDictionaryService.self().getLzzDictionaryById(next_level).getInnerValue();
	}

	/**
	 * 获取客户下一等级名称
	 * @param curUserId
	 * @return 如果为null，说明是最高等级或者批发商，正常返回表示下一等级名称
	 */
	public static Object getUserNextLevelName(String curUserId) {
		// TODO Auto-generated method stub
		String next_level = getUserNextLevel(curUserId);
		if(null==next_level) return null;
		
		return LzzDictionaryService.self().getLzzDictionaryById(next_level).getValue();
	}

	/**
	 * 增加用户商品足迹
	 * @param curUserId
	 * @param goodId
	 * @throws ParseException 
	 */
	public static void addUserGoodView(String curUserId, String goodId) throws ParseException {
		// TODO Auto-generated method stub
		LzzUserGoodViewed view = LzzUserService.self().getLzzUserGoodViewedByUserIdAndGoodId(curUserId, goodId);
		if(null==view){
			List<LzzUserGoodViewed> views = LzzUserService.self().getLzzUserGoodViewedListByUserId(curUserId);
			view = new LzzUserGoodViewed();
			view.setId(LzzIDMgr.self().getID());
			view.setUserId(curUserId);
			view.setGoodId(goodId);
			LzzUserService.self().saveLzzUserGoodViewed(view);
			if(views.size()>30){
				//删除最老的一条
				Date min_date =  new Date();
				String min_id = null;
				for(LzzUserGoodViewed sin : views){
					String modi_time_str = sin.getModifyTime();
					
					if(null==modi_time_str || "".equals(modi_time_str)) continue;
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date modi_time = sdf.parse(modi_time_str);
					if(min_date.after(modi_time)){
						min_date = modi_time;
						min_id = sin.getId();
					}
				}
				
				if(null!=min_id){
					LzzUserService.self().delLzzUserGoodViewedById(min_id);
				}
			}
		}else{
			LzzUserService.self().updateLzzUserGoodViewed(view);
		}
	}

	/**
	 * 创建新的用户会话记录
	 * @param userid
	 * @param sessionKey
	 * @return
	 */
	public static LzzUserSession createNewUserSession(String userid, String sessionKey) {
		// TODO Auto-generated method stub
		LzzUserSession new_session = new LzzUserSession();
		new_session.setId(LzzIDMgr.self().getID());
		new_session.setSession_key(sessionKey);
		new_session.setUserId(userid);
		String sessionId = getSessionId();
		new_session.setSessionId(sessionId);
		LzzUserService.self().saveLzzUserSession(new_session);
		
		return new_session;
	}
	
	private static String getSessionId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}

}
