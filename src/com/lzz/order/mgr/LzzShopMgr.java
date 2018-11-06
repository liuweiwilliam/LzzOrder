package com.lzz.order.mgr;

import java.util.List;

import com.lzz.order.pojo.LzzIndexDisplay;
import com.lzz.order.service.LzzShopService;
import com.lzz.order.utils.LzzConstString;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LzzShopMgr {

	/**
	 * 获取首页展示信息的滚动图片信息
	 * @return
	 */
	public static JSONArray getShopSwipersInfo() {
		// TODO Auto-generated method stub
		JSONArray rslt = new JSONArray();
		List<LzzIndexDisplay> diss = LzzShopService.self().getLzzIndexDisplayListByType(LzzConstString.smIndexDisplayType_Scroll);
		for(LzzIndexDisplay sin : diss){
			JSONObject obj = new JSONObject();
			obj.put("image", LzzFileMgr.getSingleFileUrlByFileGroupId(sin.getImgUrl()));
			obj.put("url", sin.getUrl());
			rslt.add(obj);
		}
		
		return rslt;
	}
	
	/**
	 * 获取首页展示的菜单按钮信息
	 * @return
	 */
	public static JSONArray getShopMenusInfo() {
		// TODO Auto-generated method stub
		JSONArray rslt = new JSONArray();
		List<LzzIndexDisplay> diss = LzzShopService.self().getLzzIndexDisplayListByType(LzzConstString.smIndexDisplayType_Menu);
		for(LzzIndexDisplay sin : diss){
			JSONObject obj = new JSONObject();
			obj.put("image", LzzFileMgr.getSingleFileUrlByFileGroupId(sin.getImgUrl()));
			obj.put("url", sin.getUrl());
			obj.put("title", sin.getName());
			rslt.add(obj);
		}
		
		return rslt;
	}

}
