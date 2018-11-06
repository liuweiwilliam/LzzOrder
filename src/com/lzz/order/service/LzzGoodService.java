package com.lzz.order.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.order.utils.LzzDateUtil;

import com.lzz.order.cachemgr.*;
import com.lzz.order.pojo.LzzGood;

import com.lzz.order.pojo.LzzGoodTaste;

import com.lzz.order.pojo.LzzGoodEvaluate;

import com.lzz.order.pojo.LzzGoodCategory;

import com.lzz.order.pojo.LzzGoodFormat;

import com.lzz.order.pojo.LzzCategory;


public class LzzGoodService{

	// Singleton functions ( construction is private)
	private static LzzGoodService mSelf;	
	public static LzzGoodService self(){
		if(null==mSelf) mSelf = new LzzGoodService();
		
		return mSelf;
	}

	private LzzGoodService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzGood(Object obj) {
		((LzzGood)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzGood)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzGoodCacheMgr.self().saveLzzGood(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzGood(Object obj) {
		LzzGoodCacheMgr.self().delLzzGood(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzGoodById(String id) {
		LzzGood obj = new LzzGood();
		obj.setId(id);
		delLzzGood(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzGood(Object obj) {
		((LzzGood)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzGoodCacheMgr.self().updateLzzGood(obj);
	}
	
	private List<LzzGood> getAllLzzGood() {
		return LzzGoodCacheMgr.self().getAllLzzGood();
	}
	
	public List<LzzGood> getAllValidLzzGood() {
		List<LzzGood> array_all = getAllLzzGood();
		List<LzzGood> rslt = new ArrayList<LzzGood>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzGood> getAllLzzGoodIgnoreDr() {
		return getAllLzzGood();
	}
	
	public LzzGood getLzzGoodById(String id){
		return LzzGoodCacheMgr.self().getLzzGoodById(id);
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzGoodTaste(Object obj) {
		((LzzGoodTaste)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzGoodTaste)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzGoodCacheMgr.self().saveLzzGoodTaste(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzGoodTaste(Object obj) {
		LzzGoodCacheMgr.self().delLzzGoodTaste(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzGoodTasteById(String id) {
		LzzGoodTaste obj = new LzzGoodTaste();
		obj.setId(id);
		delLzzGoodTaste(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzGoodTaste(Object obj) {
		((LzzGoodTaste)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzGoodCacheMgr.self().updateLzzGoodTaste(obj);
	}
	
	private List<LzzGoodTaste> getAllLzzGoodTaste() {
		return LzzGoodCacheMgr.self().getAllLzzGoodTaste();
	}
	
	public List<LzzGoodTaste> getAllValidLzzGoodTaste() {
		List<LzzGoodTaste> array_all = getAllLzzGoodTaste();
		List<LzzGoodTaste> rslt = new ArrayList<LzzGoodTaste>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzGoodTaste> getAllLzzGoodTasteIgnoreDr() {
		return getAllLzzGoodTaste();
	}
	
	public LzzGoodTaste getLzzGoodTasteById(String id){
		return LzzGoodCacheMgr.self().getLzzGoodTasteById(id);
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzGoodEvaluate(Object obj) {
		((LzzGoodEvaluate)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzGoodEvaluate)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzGoodCacheMgr.self().saveLzzGoodEvaluate(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzGoodEvaluate(Object obj) {
		LzzGoodCacheMgr.self().delLzzGoodEvaluate(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzGoodEvaluateById(String id) {
		LzzGoodEvaluate obj = new LzzGoodEvaluate();
		obj.setId(id);
		delLzzGoodEvaluate(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzGoodEvaluate(Object obj) {
		((LzzGoodEvaluate)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzGoodCacheMgr.self().updateLzzGoodEvaluate(obj);
	}
	
	private List<LzzGoodEvaluate> getAllLzzGoodEvaluate() {
		return LzzGoodCacheMgr.self().getAllLzzGoodEvaluate();
	}
	
	public List<LzzGoodEvaluate> getAllValidLzzGoodEvaluate() {
		List<LzzGoodEvaluate> array_all = getAllLzzGoodEvaluate();
		List<LzzGoodEvaluate> rslt = new ArrayList<LzzGoodEvaluate>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzGoodEvaluate> getAllLzzGoodEvaluateIgnoreDr() {
		return getAllLzzGoodEvaluate();
	}
	
	public LzzGoodEvaluate getLzzGoodEvaluateById(String id){
		return LzzGoodCacheMgr.self().getLzzGoodEvaluateById(id);
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzGoodCategory(Object obj) {
		((LzzGoodCategory)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzGoodCategory)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzGoodCacheMgr.self().saveLzzGoodCategory(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzGoodCategory(Object obj) {
		LzzGoodCacheMgr.self().delLzzGoodCategory(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzGoodCategoryById(String id) {
		LzzGoodCategory obj = new LzzGoodCategory();
		obj.setId(id);
		delLzzGoodCategory(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzGoodCategory(Object obj) {
		((LzzGoodCategory)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzGoodCacheMgr.self().updateLzzGoodCategory(obj);
	}
	
	private List<LzzGoodCategory> getAllLzzGoodCategory() {
		return LzzGoodCacheMgr.self().getAllLzzGoodCategory();
	}
	
	public List<LzzGoodCategory> getAllValidLzzGoodCategory() {
		List<LzzGoodCategory> array_all = getAllLzzGoodCategory();
		List<LzzGoodCategory> rslt = new ArrayList<LzzGoodCategory>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzGoodCategory> getAllLzzGoodCategoryIgnoreDr() {
		return getAllLzzGoodCategory();
	}
	
	public LzzGoodCategory getLzzGoodCategoryById(String id){
		return LzzGoodCacheMgr.self().getLzzGoodCategoryById(id);
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzGoodFormat(Object obj) {
		((LzzGoodFormat)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzGoodFormat)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzGoodCacheMgr.self().saveLzzGoodFormat(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzGoodFormat(Object obj) {
		LzzGoodCacheMgr.self().delLzzGoodFormat(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzGoodFormatById(String id) {
		LzzGoodFormat obj = new LzzGoodFormat();
		obj.setId(id);
		delLzzGoodFormat(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzGoodFormat(Object obj) {
		((LzzGoodFormat)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzGoodCacheMgr.self().updateLzzGoodFormat(obj);
	}
	
	private List<LzzGoodFormat> getAllLzzGoodFormat() {
		return LzzGoodCacheMgr.self().getAllLzzGoodFormat();
	}
	
	public List<LzzGoodFormat> getAllValidLzzGoodFormat() {
		List<LzzGoodFormat> array_all = getAllLzzGoodFormat();
		List<LzzGoodFormat> rslt = new ArrayList<LzzGoodFormat>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzGoodFormat> getAllLzzGoodFormatIgnoreDr() {
		return getAllLzzGoodFormat();
	}
	
	public LzzGoodFormat getLzzGoodFormatById(String id){
		return LzzGoodCacheMgr.self().getLzzGoodFormatById(id);
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzCategory(Object obj) {
		((LzzCategory)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzCategory)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzGoodCacheMgr.self().saveLzzCategory(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzCategory(Object obj) {
		LzzGoodCacheMgr.self().delLzzCategory(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzCategoryById(String id) {
		LzzCategory obj = new LzzCategory();
		obj.setId(id);
		delLzzCategory(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzCategory(Object obj) {
		((LzzCategory)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzGoodCacheMgr.self().updateLzzCategory(obj);
	}
	
	private List<LzzCategory> getAllLzzCategory() {
		return LzzGoodCacheMgr.self().getAllLzzCategory();
	}
	
	public List<LzzCategory> getAllValidLzzCategory() {
		List<LzzCategory> array_all = getAllLzzCategory();
		List<LzzCategory> rslt = new ArrayList<LzzCategory>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzCategory> getAllLzzCategoryIgnoreDr() {
		return getAllLzzCategory();
	}
	
	public LzzCategory getLzzCategoryById(String id){
		return LzzGoodCacheMgr.self().getLzzCategoryById(id);
	}




	public List<LzzGoodTaste> getLzzGoodTasteListByGoodId(String goodId){
		List<LzzGoodTaste> array_all = getAllValidLzzGoodTaste();
		List<LzzGoodTaste> rslt = new ArrayList<LzzGoodTaste>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getGoodId()
				&& array_all.get(i).getGoodId().equals(goodId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzGoodTaste> getLzzGoodTasteListByTasteName(String tasteName){
		List<LzzGoodTaste> array_all = getAllValidLzzGoodTaste();
		List<LzzGoodTaste> rslt = new ArrayList<LzzGoodTaste>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getTasteName()
				&& array_all.get(i).getTasteName().equals(tasteName)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzGoodCategory> getLzzGoodCategoryListByGoodId(String goodId){
		List<LzzGoodCategory> array_all = getAllValidLzzGoodCategory();
		List<LzzGoodCategory> rslt = new ArrayList<LzzGoodCategory>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getGoodId()
				&& array_all.get(i).getGoodId().equals(goodId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzGoodCategory> getLzzGoodCategoryListByCategoryId(String categoryId){
		List<LzzGoodCategory> array_all = getAllValidLzzGoodCategory();
		List<LzzGoodCategory> rslt = new ArrayList<LzzGoodCategory>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getCategoryId()
				&& array_all.get(i).getCategoryId().equals(categoryId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzGoodFormat> getLzzGoodFormatListByGoodId(String goodId){
		List<LzzGoodFormat> array_all = getAllValidLzzGoodFormat();
		List<LzzGoodFormat> rslt = new ArrayList<LzzGoodFormat>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getGoodId()
				&& array_all.get(i).getGoodId().equals(goodId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzGoodEvaluate> getLzzGoodEvaluateListByGoodId(String goodId){
		List<LzzGoodEvaluate> array_all = getAllValidLzzGoodEvaluate();
		List<LzzGoodEvaluate> rslt = new ArrayList<LzzGoodEvaluate>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getGoodId()
				&& array_all.get(i).getGoodId().equals(goodId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzCategory> getLzzCategoryListByIsHot(String isHot){
		List<LzzCategory> array_all = getAllValidLzzCategory();
		List<LzzCategory> rslt = new ArrayList<LzzCategory>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getIsHot()
				&& array_all.get(i).getIsHot().equals(isHot)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzGood> getLzzGoodListByIsHot(String isHot){
		List<LzzGood> array_all = getAllValidLzzGood();
		List<LzzGood> rslt = new ArrayList<LzzGood>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getIsHot()
				&& array_all.get(i).getIsHot().equals(isHot)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzGood> getLzzGoodListByIsNew(String isNew){
		List<LzzGood> array_all = getAllValidLzzGood();
		List<LzzGood> rslt = new ArrayList<LzzGood>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getIsNew()
				&& array_all.get(i).getIsNew().equals(isNew)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}





}



