package com.lzz.order.mgr;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lzz.order.pojo.LzzDictionary;
import com.lzz.order.service.LzzDictionaryService;
import com.lzz.order.utils.LzzConstString;
import com.lzz.order.utils.LzzProperties;

public class LzzDictionaryMgr {
	
	/**
	 * 通过字典ID获取字典value
	 * @param dic_id
	 * @return
	 */
	public static String getValueById(String dic_id){
		if(null==dic_id
				|| "".equals(dic_id)){
			return null;
		}
		
		LzzDictionary dic = LzzDictionaryService.self().getLzzDictionaryById(dic_id);
		if(null==dic) return null;
		
		return dic.getValue();
	}
	
	/**
	 * 通过字典value和type获取ID
	 * @param dic_id
	 * @return
	 */
	public static String getIDByCategoryAndValue(String category, String value){
		if(null==category
				|| "".equals(category)
				|| null==value
				|| "".equals(value)){
			return null;
		}
		
		LzzDictionary dic = LzzDictionaryService.self().getLzzDictionaryByCategoryAndValue(category, value);
		if(null==dic) return null;
		
		return dic.getId();
	}
	
	/**
	 * 通过字典ID获取字典示例图片地址
	 * @param dic_id
	 * @return
	 */
	public static String getDicTypicalImgById(String dic_id){
		if(null==dic_id
				|| "".equals(dic_id)){
			return null;
		}
		
		LzzDictionary dic = LzzDictionaryService.self().getLzzDictionaryById(dic_id);
		if(null==dic) return null;
		
		String typical_id = dic.getTypicalImg();
		String url = LzzFileMgr.getSingleFileUrlByFileGroupId(typical_id);
		return url;
	}
	
	/**
	 * 通过类型获取字典信息
	 * @param categoty
	 * @return
	 */
	public static JSONObject getDicArrayByCategory(String categoty){
		JSONObject obj = new JSONObject();
		List<LzzDictionary> dics = LzzDictionaryService.self().getLzzDictionaryListByCategory(categoty);
		JSONArray ids = new JSONArray();
		JSONArray names = new JSONArray();
		for(LzzDictionary sin : dics){
			ids.add(sin.getId());
			names.add(sin.getValue());
		}
		obj.put("ids", ids);
		obj.put("names", names);
		
		return obj;
	}
	
	/**
	 * 获取该类型字典的第一个value
	 * @param categoty
	 * @return
	 */
	public static String getFirstValueByCategory(String categoty){
		List<LzzDictionary> dics = LzzDictionaryService.self().getLzzDictionaryListByCategory(categoty);
		for(LzzDictionary sin : dics){
			return sin.getValue();
		}
		
		return null;
	}
	
	/**
	 * 获取该类型字典的第一个ID
	 * @param categoty
	 * @return
	 */
	public static String getFirstIDByCategory(String categoty){
		List<LzzDictionary> dics = LzzDictionaryService.self().getLzzDictionaryListByCategory(categoty);
		for(LzzDictionary sin : dics){
			return sin.getId();
		}
		
		return null;
	}

	/**
	 * 获取最低客户等级
	 * @return
	 */
	public static String getDefaultUserLevel() {
		// TODO Auto-generated method stub
		LzzDictionary dic = LzzDictionaryService.self().getLzzDictionaryByCategoryAndValue(LzzConstString.smUserLevel, LzzConstString.smUserLevel_normal);
		return dic.getId();
	}
}
