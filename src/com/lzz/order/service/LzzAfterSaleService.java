package com.lzz.order.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.order.utils.LzzDateUtil;

import com.lzz.order.cachemgr.*;
import com.lzz.order.pojo.LzzAfterSale;


public class LzzAfterSaleService{

	// Singleton functions ( construction is private)
	private static LzzAfterSaleService mSelf;	
	public static LzzAfterSaleService self(){
		if(null==mSelf) mSelf = new LzzAfterSaleService();
		
		return mSelf;
	}

	private LzzAfterSaleService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzAfterSale(Object obj) {
		((LzzAfterSale)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzAfterSale)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzAfterSaleCacheMgr.self().saveLzzAfterSale(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzAfterSale(Object obj) {
		LzzAfterSaleCacheMgr.self().delLzzAfterSale(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzAfterSaleById(String id) {
		LzzAfterSale obj = new LzzAfterSale();
		obj.setId(id);
		delLzzAfterSale(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzAfterSale(Object obj) {
		((LzzAfterSale)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzAfterSaleCacheMgr.self().updateLzzAfterSale(obj);
	}
	
	private List<LzzAfterSale> getAllLzzAfterSale() {
		return LzzAfterSaleCacheMgr.self().getAllLzzAfterSale();
	}
	
	public List<LzzAfterSale> getAllValidLzzAfterSale() {
		List<LzzAfterSale> array_all = getAllLzzAfterSale();
		List<LzzAfterSale> rslt = new ArrayList<LzzAfterSale>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzAfterSale> getAllLzzAfterSaleIgnoreDr() {
		return getAllLzzAfterSale();
	}
	
	public LzzAfterSale getLzzAfterSaleById(String id){
		return LzzAfterSaleCacheMgr.self().getLzzAfterSaleById(id);
	}








}



