package com.lzz.order.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.order.utils.LzzDateUtil;

import com.lzz.order.cachemgr.*;
import com.lzz.order.pojo.LzzID;


public class LzzIDService{

	// Singleton functions ( construction is private)
	private static LzzIDService mSelf;	
	public static LzzIDService self(){
		if(null==mSelf) mSelf = new LzzIDService();
		
		return mSelf;
	}

	private LzzIDService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzID(Object obj) {
		((LzzID)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzID)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzIDCacheMgr.self().saveLzzID(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzID(Object obj) {
		LzzIDCacheMgr.self().delLzzID(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzIDById(String id) {
		LzzID obj = new LzzID();
		obj.setId(id);
		delLzzID(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzID(Object obj) {
		((LzzID)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzIDCacheMgr.self().updateLzzID(obj);
	}
	
	private List<LzzID> getAllLzzID() {
		return LzzIDCacheMgr.self().getAllLzzID();
	}
	
	public List<LzzID> getAllValidLzzID() {
		List<LzzID> array_all = getAllLzzID();
		List<LzzID> rslt = new ArrayList<LzzID>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzID> getAllLzzIDIgnoreDr() {
		return getAllLzzID();
	}
	
	public LzzID getLzzIDById(String id){
		return LzzIDCacheMgr.self().getLzzIDById(id);
	}








}



