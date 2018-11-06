package com.lzz.order.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.order.utils.LzzDateUtil;

import com.lzz.order.cachemgr.*;
import com.lzz.order.pojo.LzzCart;


public class LzzCartService{

	// Singleton functions ( construction is private)
	private static LzzCartService mSelf;	
	public static LzzCartService self(){
		if(null==mSelf) mSelf = new LzzCartService();
		
		return mSelf;
	}

	private LzzCartService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzCart(Object obj) {
		((LzzCart)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzCart)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzCartCacheMgr.self().saveLzzCart(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzCart(Object obj) {
		LzzCartCacheMgr.self().delLzzCart(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzCartById(String id) {
		LzzCart obj = new LzzCart();
		obj.setId(id);
		delLzzCart(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzCart(Object obj) {
		((LzzCart)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzCartCacheMgr.self().updateLzzCart(obj);
	}
	
	private List<LzzCart> getAllLzzCart() {
		return LzzCartCacheMgr.self().getAllLzzCart();
	}
	
	public List<LzzCart> getAllValidLzzCart() {
		List<LzzCart> array_all = getAllLzzCart();
		List<LzzCart> rslt = new ArrayList<LzzCart>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzCart> getAllLzzCartIgnoreDr() {
		return getAllLzzCart();
	}
	
	public LzzCart getLzzCartById(String id){
		return LzzCartCacheMgr.self().getLzzCartById(id);
	}




	public List<LzzCart> getLzzCartListByUserId(String userId){
		List<LzzCart> array_all = getAllValidLzzCart();
		List<LzzCart> rslt = new ArrayList<LzzCart>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getUserId()
				&& array_all.get(i).getUserId().equals(userId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}





	public LzzCart getLzzCartByUserIdAndGoodIdAndFormatIdAndFormatId(
											String userId, 
											String goodId,
											String formatId,
											String tasteId){
		List<LzzCart> array_all = getAllValidLzzCart();
		LzzCart rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getUserId()
				&& array_all.get(i).getUserId().equals(userId)
				&& null!=array_all.get(i).getGoodId()
				&& array_all.get(i).getGoodId().equals(goodId)
				&& null!=array_all.get(i).getFormatId()
				&& array_all.get(i).getFormatId().equals(formatId)
				&& null!=array_all.get(i).getTasteId()
				&& array_all.get(i).getTasteId().equals(tasteId)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}

}



