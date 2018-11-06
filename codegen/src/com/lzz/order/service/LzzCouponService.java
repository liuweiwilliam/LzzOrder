package com.lzz.order.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.order.utils.LzzDateUtil;

import com.lzz.order.cachemgr.*;
import com.lzz.order.pojo.LzzCoupon;


public class LzzCouponService{

	// Singleton functions ( construction is private)
	private static LzzCouponService mSelf;	
	public static LzzCouponService self(){
		if(null==mSelf) mSelf = new LzzCouponService();
		
		return mSelf;
	}

	private LzzCouponService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzCoupon(Object obj) {
		((LzzCoupon)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzCoupon)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzCouponCacheMgr.self().saveLzzCoupon(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzCoupon(Object obj) {
		LzzCouponCacheMgr.self().delLzzCoupon(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzCouponById(String id) {
		LzzCoupon obj = new LzzCoupon();
		obj.setId(id);
		delLzzCoupon(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzCoupon(Object obj) {
		((LzzCoupon)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzCouponCacheMgr.self().updateLzzCoupon(obj);
	}
	
	private List<LzzCoupon> getAllLzzCoupon() {
		return LzzCouponCacheMgr.self().getAllLzzCoupon();
	}
	
	public List<LzzCoupon> getAllValidLzzCoupon() {
		List<LzzCoupon> array_all = getAllLzzCoupon();
		List<LzzCoupon> rslt = new ArrayList<LzzCoupon>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzCoupon> getAllLzzCouponIgnoreDr() {
		return getAllLzzCoupon();
	}
	
	public LzzCoupon getLzzCouponById(String id){
		return LzzCouponCacheMgr.self().getLzzCouponById(id);
	}








}



