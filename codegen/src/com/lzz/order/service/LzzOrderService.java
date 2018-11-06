package com.lzz.order.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.order.utils.LzzDateUtil;

import com.lzz.order.cachemgr.*;
import com.lzz.order.pojo.LzzOrder;

import com.lzz.order.pojo.LzzOrderInfo;

import com.lzz.order.pojo.LzzOrderSendMode;


public class LzzOrderService{

	// Singleton functions ( construction is private)
	private static LzzOrderService mSelf;	
	public static LzzOrderService self(){
		if(null==mSelf) mSelf = new LzzOrderService();
		
		return mSelf;
	}

	private LzzOrderService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzOrder(Object obj) {
		((LzzOrder)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzOrder)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzOrderCacheMgr.self().saveLzzOrder(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzOrder(Object obj) {
		LzzOrderCacheMgr.self().delLzzOrder(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzOrderById(String id) {
		LzzOrder obj = new LzzOrder();
		obj.setId(id);
		delLzzOrder(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzOrder(Object obj) {
		((LzzOrder)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzOrderCacheMgr.self().updateLzzOrder(obj);
	}
	
	private List<LzzOrder> getAllLzzOrder() {
		return LzzOrderCacheMgr.self().getAllLzzOrder();
	}
	
	public List<LzzOrder> getAllValidLzzOrder() {
		List<LzzOrder> array_all = getAllLzzOrder();
		List<LzzOrder> rslt = new ArrayList<LzzOrder>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzOrder> getAllLzzOrderIgnoreDr() {
		return getAllLzzOrder();
	}
	
	public LzzOrder getLzzOrderById(String id){
		return LzzOrderCacheMgr.self().getLzzOrderById(id);
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzOrderInfo(Object obj) {
		((LzzOrderInfo)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzOrderInfo)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzOrderCacheMgr.self().saveLzzOrderInfo(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzOrderInfo(Object obj) {
		LzzOrderCacheMgr.self().delLzzOrderInfo(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzOrderInfoById(String id) {
		LzzOrderInfo obj = new LzzOrderInfo();
		obj.setId(id);
		delLzzOrderInfo(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzOrderInfo(Object obj) {
		((LzzOrderInfo)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzOrderCacheMgr.self().updateLzzOrderInfo(obj);
	}
	
	private List<LzzOrderInfo> getAllLzzOrderInfo() {
		return LzzOrderCacheMgr.self().getAllLzzOrderInfo();
	}
	
	public List<LzzOrderInfo> getAllValidLzzOrderInfo() {
		List<LzzOrderInfo> array_all = getAllLzzOrderInfo();
		List<LzzOrderInfo> rslt = new ArrayList<LzzOrderInfo>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzOrderInfo> getAllLzzOrderInfoIgnoreDr() {
		return getAllLzzOrderInfo();
	}
	
	public LzzOrderInfo getLzzOrderInfoById(String id){
		return LzzOrderCacheMgr.self().getLzzOrderInfoById(id);
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzOrderSendMode(Object obj) {
		((LzzOrderSendMode)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzOrderSendMode)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzOrderCacheMgr.self().saveLzzOrderSendMode(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzOrderSendMode(Object obj) {
		LzzOrderCacheMgr.self().delLzzOrderSendMode(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzOrderSendModeById(String id) {
		LzzOrderSendMode obj = new LzzOrderSendMode();
		obj.setId(id);
		delLzzOrderSendMode(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzOrderSendMode(Object obj) {
		((LzzOrderSendMode)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzOrderCacheMgr.self().updateLzzOrderSendMode(obj);
	}
	
	private List<LzzOrderSendMode> getAllLzzOrderSendMode() {
		return LzzOrderCacheMgr.self().getAllLzzOrderSendMode();
	}
	
	public List<LzzOrderSendMode> getAllValidLzzOrderSendMode() {
		List<LzzOrderSendMode> array_all = getAllLzzOrderSendMode();
		List<LzzOrderSendMode> rslt = new ArrayList<LzzOrderSendMode>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzOrderSendMode> getAllLzzOrderSendModeIgnoreDr() {
		return getAllLzzOrderSendMode();
	}
	
	public LzzOrderSendMode getLzzOrderSendModeById(String id){
		return LzzOrderCacheMgr.self().getLzzOrderSendModeById(id);
	}




	public List<LzzOrder> getLzzOrderListByUserId(String userId){
		List<LzzOrder> array_all = getAllValidLzzOrder();
		List<LzzOrder> rslt = new ArrayList<LzzOrder>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getUserId()
				&& array_all.get(i).getUserId().equals(userId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzOrderInfo> getLzzOrderInfoListByOrderId(String orderId){
		List<LzzOrderInfo> array_all = getAllValidLzzOrderInfo();
		List<LzzOrderInfo> rslt = new ArrayList<LzzOrderInfo>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getOrderId()
				&& array_all.get(i).getOrderId().equals(orderId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzOrder> getLzzOrderListByStatus(String status){
		List<LzzOrder> array_all = getAllValidLzzOrder();
		List<LzzOrder> rslt = new ArrayList<LzzOrder>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getStatus()
				&& array_all.get(i).getStatus().equals(status)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}



	public List<LzzOrder> getLzzOrderListByUserIdAndStatus(String userId, String status){
		List<LzzOrder> array_all = getAllValidLzzOrder();
		List<LzzOrder> rslt = new ArrayList<LzzOrder>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getUserId()
				&& array_all.get(i).getUserId().equals(userId)
				&& null!=array_all.get(i).getStatus()
				&& array_all.get(i).getStatus().equals(status)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}



}



