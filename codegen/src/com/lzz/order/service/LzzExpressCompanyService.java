package com.lzz.order.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.order.utils.LzzDateUtil;

import com.lzz.order.cachemgr.*;
import com.lzz.order.pojo.LzzExpressCompany;


public class LzzExpressCompanyService{

	// Singleton functions ( construction is private)
	private static LzzExpressCompanyService mSelf;	
	public static LzzExpressCompanyService self(){
		if(null==mSelf) mSelf = new LzzExpressCompanyService();
		
		return mSelf;
	}

	private LzzExpressCompanyService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzExpressCompany(Object obj) {
		((LzzExpressCompany)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzExpressCompany)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzExpressCompanyCacheMgr.self().saveLzzExpressCompany(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzExpressCompany(Object obj) {
		LzzExpressCompanyCacheMgr.self().delLzzExpressCompany(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzExpressCompanyById(String id) {
		LzzExpressCompany obj = new LzzExpressCompany();
		obj.setId(id);
		delLzzExpressCompany(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzExpressCompany(Object obj) {
		((LzzExpressCompany)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzExpressCompanyCacheMgr.self().updateLzzExpressCompany(obj);
	}
	
	private List<LzzExpressCompany> getAllLzzExpressCompany() {
		return LzzExpressCompanyCacheMgr.self().getAllLzzExpressCompany();
	}
	
	public List<LzzExpressCompany> getAllValidLzzExpressCompany() {
		List<LzzExpressCompany> array_all = getAllLzzExpressCompany();
		List<LzzExpressCompany> rslt = new ArrayList<LzzExpressCompany>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzExpressCompany> getAllLzzExpressCompanyIgnoreDr() {
		return getAllLzzExpressCompany();
	}
	
	public LzzExpressCompany getLzzExpressCompanyById(String id){
		return LzzExpressCompanyCacheMgr.self().getLzzExpressCompanyById(id);
	}








}



