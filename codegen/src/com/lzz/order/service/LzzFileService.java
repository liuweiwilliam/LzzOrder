package com.lzz.order.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.order.utils.LzzDateUtil;

import com.lzz.order.cachemgr.*;
import com.lzz.order.pojo.LzzFileInfo;

import com.lzz.order.pojo.LzzFileGroup;


public class LzzFileService{

	// Singleton functions ( construction is private)
	private static LzzFileService mSelf;	
	public static LzzFileService self(){
		if(null==mSelf) mSelf = new LzzFileService();
		
		return mSelf;
	}

	private LzzFileService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzFileInfo(Object obj) {
		((LzzFileInfo)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzFileInfo)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzFileCacheMgr.self().saveLzzFileInfo(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzFileInfo(Object obj) {
		LzzFileCacheMgr.self().delLzzFileInfo(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzFileInfoById(String id) {
		LzzFileInfo obj = new LzzFileInfo();
		obj.setId(id);
		delLzzFileInfo(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzFileInfo(Object obj) {
		((LzzFileInfo)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzFileCacheMgr.self().updateLzzFileInfo(obj);
	}
	
	private List<LzzFileInfo> getAllLzzFileInfo() {
		return LzzFileCacheMgr.self().getAllLzzFileInfo();
	}
	
	public List<LzzFileInfo> getAllValidLzzFileInfo() {
		List<LzzFileInfo> array_all = getAllLzzFileInfo();
		List<LzzFileInfo> rslt = new ArrayList<LzzFileInfo>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzFileInfo> getAllLzzFileInfoIgnoreDr() {
		return getAllLzzFileInfo();
	}
	
	public LzzFileInfo getLzzFileInfoById(String id){
		return LzzFileCacheMgr.self().getLzzFileInfoById(id);
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzFileGroup(Object obj) {
		((LzzFileGroup)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzFileGroup)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzFileCacheMgr.self().saveLzzFileGroup(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzFileGroup(Object obj) {
		LzzFileCacheMgr.self().delLzzFileGroup(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzFileGroupById(String id) {
		LzzFileGroup obj = new LzzFileGroup();
		obj.setId(id);
		delLzzFileGroup(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzFileGroup(Object obj) {
		((LzzFileGroup)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzFileCacheMgr.self().updateLzzFileGroup(obj);
	}
	
	private List<LzzFileGroup> getAllLzzFileGroup() {
		return LzzFileCacheMgr.self().getAllLzzFileGroup();
	}
	
	public List<LzzFileGroup> getAllValidLzzFileGroup() {
		List<LzzFileGroup> array_all = getAllLzzFileGroup();
		List<LzzFileGroup> rslt = new ArrayList<LzzFileGroup>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzFileGroup> getAllLzzFileGroupIgnoreDr() {
		return getAllLzzFileGroup();
	}
	
	public LzzFileGroup getLzzFileGroupById(String id){
		return LzzFileCacheMgr.self().getLzzFileGroupById(id);
	}








}



