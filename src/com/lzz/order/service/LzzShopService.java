package com.lzz.order.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.order.utils.LzzDateUtil;

import com.lzz.order.cachemgr.*;
import com.lzz.order.pojo.LzzShopEvaluate;

import com.lzz.order.pojo.LzzIndexDisplay;


public class LzzShopService{

	// Singleton functions ( construction is private)
	private static LzzShopService mSelf;	
	public static LzzShopService self(){
		if(null==mSelf) mSelf = new LzzShopService();
		
		return mSelf;
	}

	private LzzShopService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzShopEvaluate(Object obj) {
		((LzzShopEvaluate)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzShopEvaluate)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzShopCacheMgr.self().saveLzzShopEvaluate(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzShopEvaluate(Object obj) {
		LzzShopCacheMgr.self().delLzzShopEvaluate(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzShopEvaluateById(String id) {
		LzzShopEvaluate obj = new LzzShopEvaluate();
		obj.setId(id);
		delLzzShopEvaluate(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzShopEvaluate(Object obj) {
		((LzzShopEvaluate)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzShopCacheMgr.self().updateLzzShopEvaluate(obj);
	}
	
	private List<LzzShopEvaluate> getAllLzzShopEvaluate() {
		return LzzShopCacheMgr.self().getAllLzzShopEvaluate();
	}
	
	public List<LzzShopEvaluate> getAllValidLzzShopEvaluate() {
		List<LzzShopEvaluate> array_all = getAllLzzShopEvaluate();
		List<LzzShopEvaluate> rslt = new ArrayList<LzzShopEvaluate>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzShopEvaluate> getAllLzzShopEvaluateIgnoreDr() {
		return getAllLzzShopEvaluate();
	}
	
	public LzzShopEvaluate getLzzShopEvaluateById(String id){
		return LzzShopCacheMgr.self().getLzzShopEvaluateById(id);
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzIndexDisplay(Object obj) {
		((LzzIndexDisplay)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzIndexDisplay)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzShopCacheMgr.self().saveLzzIndexDisplay(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzIndexDisplay(Object obj) {
		LzzShopCacheMgr.self().delLzzIndexDisplay(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzIndexDisplayById(String id) {
		LzzIndexDisplay obj = new LzzIndexDisplay();
		obj.setId(id);
		delLzzIndexDisplay(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzIndexDisplay(Object obj) {
		((LzzIndexDisplay)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzShopCacheMgr.self().updateLzzIndexDisplay(obj);
	}
	
	private List<LzzIndexDisplay> getAllLzzIndexDisplay() {
		return LzzShopCacheMgr.self().getAllLzzIndexDisplay();
	}
	
	public List<LzzIndexDisplay> getAllValidLzzIndexDisplay() {
		List<LzzIndexDisplay> array_all = getAllLzzIndexDisplay();
		List<LzzIndexDisplay> rslt = new ArrayList<LzzIndexDisplay>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzIndexDisplay> getAllLzzIndexDisplayIgnoreDr() {
		return getAllLzzIndexDisplay();
	}
	
	public LzzIndexDisplay getLzzIndexDisplayById(String id){
		return LzzShopCacheMgr.self().getLzzIndexDisplayById(id);
	}




	public List<LzzShopEvaluate> getLzzShopEvaluateListByOrderId(String orderId){
		List<LzzShopEvaluate> array_all = getAllValidLzzShopEvaluate();
		List<LzzShopEvaluate> rslt = new ArrayList<LzzShopEvaluate>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getOrderId()
				&& array_all.get(i).getOrderId().equals(orderId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzShopEvaluate> getLzzShopEvaluateListByEvaluateLevel(String evaluateLevel){
		List<LzzShopEvaluate> array_all = getAllValidLzzShopEvaluate();
		List<LzzShopEvaluate> rslt = new ArrayList<LzzShopEvaluate>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getEvaluateLevel()
				&& array_all.get(i).getEvaluateLevel().equals(evaluateLevel)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzIndexDisplay> getLzzIndexDisplayListByType(String type){
		List<LzzIndexDisplay> array_all = getAllValidLzzIndexDisplay();
		List<LzzIndexDisplay> rslt = new ArrayList<LzzIndexDisplay>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getType()
				&& array_all.get(i).getType().equals(type)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}





}



