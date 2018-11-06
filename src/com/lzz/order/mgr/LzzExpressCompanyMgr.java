package com.lzz.order.mgr;

import com.lzz.order.pojo.LzzExpressCompany;
import com.lzz.order.service.LzzExpressCompanyService;

public class LzzExpressCompanyMgr {
	/**
	 * 获取默认快递公司ID
	 * @return
	 */
	public static String getDefaultExpressCmp(){
		return "";
	}
	
	/**
	 * 获取默认快递公司code
	 * @return
	 */
	public static String getDefaultExpressCmpCode(){
		String default_id = getDefaultExpressCmp();
		if(null==default_id) return null;
		return null;
		//return LzzExpressCompanyService.self().getLzzExpressCompanyById(default_id).getCode();
	}
	
	/**
	 * 设置默认快递公司
	 * @param cmp_id
	 * @return 
	 */
	public static boolean setDefaultExpressCmp(String cmp_id){
		return true;
	}
	
	/**
	 * 增加新的快递公司
	 * @param name 快递公司名称
	 * @param code 快递公司编码
	 * @return
	 */
	public static String addNewExpressCmp(String name, String code){
		LzzExpressCompany cmp = new LzzExpressCompany();
		cmp.setId(LzzIDMgr.self().getID());
		cmp.setName(name);
		//cmp.setCode(code);
		LzzExpressCompanyService.self().saveLzzExpressCompany(cmp);
		
		return cmp.getId();
	}
}
