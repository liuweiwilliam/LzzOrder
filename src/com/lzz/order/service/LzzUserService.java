package com.lzz.order.service;


import java.util.ArrayList;
import java.util.List;
import com.lzz.order.utils.LzzDateUtil;

import com.lzz.order.cachemgr.*;
import com.lzz.order.pojo.LzzUser;

import com.lzz.order.pojo.LzzUserTaste;

import com.lzz.order.pojo.LzzUserAddress;

import com.lzz.order.pojo.LzzUserCoupon;

import com.lzz.order.pojo.LzzUserGoodInterested;

import com.lzz.order.pojo.LzzUserSession;

import com.lzz.order.pojo.LzzUserGoodViewed;


public class LzzUserService{

	// Singleton functions ( construction is private)
	private static LzzUserService mSelf;	
	public static LzzUserService self(){
		if(null==mSelf) mSelf = new LzzUserService();
		
		return mSelf;
	}

	private LzzUserService (){
	}
	
	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzUser(Object obj) {
		((LzzUser)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzUser)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().saveLzzUser(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzUser(Object obj) {
		LzzUserCacheMgr.self().delLzzUser(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzUserById(String id) {
		LzzUser obj = new LzzUser();
		obj.setId(id);
		delLzzUser(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzUser(Object obj) {
		((LzzUser)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().updateLzzUser(obj);
	}
	
	private List<LzzUser> getAllLzzUser() {
		return LzzUserCacheMgr.self().getAllLzzUser();
	}
	
	public List<LzzUser> getAllValidLzzUser() {
		List<LzzUser> array_all = getAllLzzUser();
		List<LzzUser> rslt = new ArrayList<LzzUser>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzUser> getAllLzzUserIgnoreDr() {
		return getAllLzzUser();
	}
	
	public LzzUser getLzzUserById(String id){
		return LzzUserCacheMgr.self().getLzzUserById(id);
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzUserTaste(Object obj) {
		((LzzUserTaste)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzUserTaste)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().saveLzzUserTaste(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzUserTaste(Object obj) {
		LzzUserCacheMgr.self().delLzzUserTaste(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzUserTasteById(String id) {
		LzzUserTaste obj = new LzzUserTaste();
		obj.setId(id);
		delLzzUserTaste(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzUserTaste(Object obj) {
		((LzzUserTaste)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().updateLzzUserTaste(obj);
	}
	
	private List<LzzUserTaste> getAllLzzUserTaste() {
		return LzzUserCacheMgr.self().getAllLzzUserTaste();
	}
	
	public List<LzzUserTaste> getAllValidLzzUserTaste() {
		List<LzzUserTaste> array_all = getAllLzzUserTaste();
		List<LzzUserTaste> rslt = new ArrayList<LzzUserTaste>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzUserTaste> getAllLzzUserTasteIgnoreDr() {
		return getAllLzzUserTaste();
	}
	
	public LzzUserTaste getLzzUserTasteById(String id){
		return LzzUserCacheMgr.self().getLzzUserTasteById(id);
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzUserAddress(Object obj) {
		((LzzUserAddress)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzUserAddress)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().saveLzzUserAddress(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzUserAddress(Object obj) {
		LzzUserCacheMgr.self().delLzzUserAddress(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzUserAddressById(String id) {
		LzzUserAddress obj = new LzzUserAddress();
		obj.setId(id);
		delLzzUserAddress(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzUserAddress(Object obj) {
		((LzzUserAddress)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().updateLzzUserAddress(obj);
	}
	
	private List<LzzUserAddress> getAllLzzUserAddress() {
		return LzzUserCacheMgr.self().getAllLzzUserAddress();
	}
	
	public List<LzzUserAddress> getAllValidLzzUserAddress() {
		List<LzzUserAddress> array_all = getAllLzzUserAddress();
		List<LzzUserAddress> rslt = new ArrayList<LzzUserAddress>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzUserAddress> getAllLzzUserAddressIgnoreDr() {
		return getAllLzzUserAddress();
	}
	
	public LzzUserAddress getLzzUserAddressById(String id){
		return LzzUserCacheMgr.self().getLzzUserAddressById(id);
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzUserCoupon(Object obj) {
		((LzzUserCoupon)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzUserCoupon)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().saveLzzUserCoupon(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzUserCoupon(Object obj) {
		LzzUserCacheMgr.self().delLzzUserCoupon(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzUserCouponById(String id) {
		LzzUserCoupon obj = new LzzUserCoupon();
		obj.setId(id);
		delLzzUserCoupon(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzUserCoupon(Object obj) {
		((LzzUserCoupon)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().updateLzzUserCoupon(obj);
	}
	
	private List<LzzUserCoupon> getAllLzzUserCoupon() {
		return LzzUserCacheMgr.self().getAllLzzUserCoupon();
	}
	
	public List<LzzUserCoupon> getAllValidLzzUserCoupon() {
		List<LzzUserCoupon> array_all = getAllLzzUserCoupon();
		List<LzzUserCoupon> rslt = new ArrayList<LzzUserCoupon>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzUserCoupon> getAllLzzUserCouponIgnoreDr() {
		return getAllLzzUserCoupon();
	}
	
	public LzzUserCoupon getLzzUserCouponById(String id){
		return LzzUserCacheMgr.self().getLzzUserCouponById(id);
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzUserGoodInterested(Object obj) {
		((LzzUserGoodInterested)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzUserGoodInterested)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().saveLzzUserGoodInterested(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzUserGoodInterested(Object obj) {
		LzzUserCacheMgr.self().delLzzUserGoodInterested(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzUserGoodInterestedById(String id) {
		LzzUserGoodInterested obj = new LzzUserGoodInterested();
		obj.setId(id);
		delLzzUserGoodInterested(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzUserGoodInterested(Object obj) {
		((LzzUserGoodInterested)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().updateLzzUserGoodInterested(obj);
	}
	
	private List<LzzUserGoodInterested> getAllLzzUserGoodInterested() {
		return LzzUserCacheMgr.self().getAllLzzUserGoodInterested();
	}
	
	public List<LzzUserGoodInterested> getAllValidLzzUserGoodInterested() {
		List<LzzUserGoodInterested> array_all = getAllLzzUserGoodInterested();
		List<LzzUserGoodInterested> rslt = new ArrayList<LzzUserGoodInterested>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzUserGoodInterested> getAllLzzUserGoodInterestedIgnoreDr() {
		return getAllLzzUserGoodInterested();
	}
	
	public LzzUserGoodInterested getLzzUserGoodInterestedById(String id){
		return LzzUserCacheMgr.self().getLzzUserGoodInterestedById(id);
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzUserSession(Object obj) {
		((LzzUserSession)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzUserSession)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().saveLzzUserSession(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzUserSession(Object obj) {
		LzzUserCacheMgr.self().delLzzUserSession(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzUserSessionById(String id) {
		LzzUserSession obj = new LzzUserSession();
		obj.setId(id);
		delLzzUserSession(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzUserSession(Object obj) {
		((LzzUserSession)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().updateLzzUserSession(obj);
	}
	
	private List<LzzUserSession> getAllLzzUserSession() {
		return LzzUserCacheMgr.self().getAllLzzUserSession();
	}
	
	public List<LzzUserSession> getAllValidLzzUserSession() {
		List<LzzUserSession> array_all = getAllLzzUserSession();
		List<LzzUserSession> rslt = new ArrayList<LzzUserSession>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzUserSession> getAllLzzUserSessionIgnoreDr() {
		return getAllLzzUserSession();
	}
	
	public LzzUserSession getLzzUserSessionById(String id){
		return LzzUserCacheMgr.self().getLzzUserSessionById(id);
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzUserGoodViewed(Object obj) {
		((LzzUserGoodViewed)obj).setCreateTime(LzzDateUtil.getNow("s"));
		((LzzUserGoodViewed)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().saveLzzUserGoodViewed(obj);
	}
    // delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzUserGoodViewed(Object obj) {
		LzzUserCacheMgr.self().delLzzUserGoodViewed(obj);
	}
	// delete new object(if you want to change an exist object, please call other functions.)
	public void delLzzUserGoodViewedById(String id) {
		LzzUserGoodViewed obj = new LzzUserGoodViewed();
		obj.setId(id);
		delLzzUserGoodViewed(obj);
	}
	// update new object(if you want to change an exist object, please call other functions.)
	public void updateLzzUserGoodViewed(Object obj) {
		((LzzUserGoodViewed)obj).setModifyTime(LzzDateUtil.getNow("s"));
		LzzUserCacheMgr.self().updateLzzUserGoodViewed(obj);
	}
	
	private List<LzzUserGoodViewed> getAllLzzUserGoodViewed() {
		return LzzUserCacheMgr.self().getAllLzzUserGoodViewed();
	}
	
	public List<LzzUserGoodViewed> getAllValidLzzUserGoodViewed() {
		List<LzzUserGoodViewed> array_all = getAllLzzUserGoodViewed();
		List<LzzUserGoodViewed> rslt = new ArrayList<LzzUserGoodViewed>();
		
		for(int i=0;i<array_all.size();i++){
			if(null==array_all.get(i).getDr()
				|| !array_all.get(i).getDr().equals("1")){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}
	
	public List<LzzUserGoodViewed> getAllLzzUserGoodViewedIgnoreDr() {
		return getAllLzzUserGoodViewed();
	}
	
	public LzzUserGoodViewed getLzzUserGoodViewedById(String id){
		return LzzUserCacheMgr.self().getLzzUserGoodViewedById(id);
	}



	public LzzUserSession getLzzUserSessionBySessionId(String sessionId){
		List<LzzUserSession> array_all = getAllValidLzzUserSession();
		LzzUserSession rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getSessionId()
				&& array_all.get(i).getSessionId().equals(sessionId)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}

	public LzzUserSession getLzzUserSessionByUserId(String userId){
		List<LzzUserSession> array_all = getAllValidLzzUserSession();
		LzzUserSession rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getUserId()
				&& array_all.get(i).getUserId().equals(userId)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}

	public LzzUser getLzzUserByOpenId(String openId){
		List<LzzUser> array_all = getAllValidLzzUser();
		LzzUser rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getOpenId()
				&& array_all.get(i).getOpenId().equals(openId)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}

	public LzzUser getLzzUserByUnionId(String unionId){
		List<LzzUser> array_all = getAllValidLzzUser();
		LzzUser rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getUnionId()
				&& array_all.get(i).getUnionId().equals(unionId)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}

	public LzzUser getLzzUserByPhone(String phone){
		List<LzzUser> array_all = getAllValidLzzUser();
		LzzUser rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getPhone()
				&& array_all.get(i).getPhone().equals(phone)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}


	public List<LzzUserTaste> getLzzUserTasteListByUserId(String userId){
		List<LzzUserTaste> array_all = getAllValidLzzUserTaste();
		List<LzzUserTaste> rslt = new ArrayList<LzzUserTaste>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getUserId()
				&& array_all.get(i).getUserId().equals(userId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzUserAddress> getLzzUserAddressListByUserId(String userId){
		List<LzzUserAddress> array_all = getAllValidLzzUserAddress();
		List<LzzUserAddress> rslt = new ArrayList<LzzUserAddress>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getUserId()
				&& array_all.get(i).getUserId().equals(userId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzUserCoupon> getLzzUserCouponListByUserId(String userId){
		List<LzzUserCoupon> array_all = getAllValidLzzUserCoupon();
		List<LzzUserCoupon> rslt = new ArrayList<LzzUserCoupon>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getUserId()
				&& array_all.get(i).getUserId().equals(userId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzUserGoodInterested> getLzzUserGoodInterestedListByUserId(String userId){
		List<LzzUserGoodInterested> array_all = getAllValidLzzUserGoodInterested();
		List<LzzUserGoodInterested> rslt = new ArrayList<LzzUserGoodInterested>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getUserId()
				&& array_all.get(i).getUserId().equals(userId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzUserGoodInterested> getLzzUserGoodInterestedListByGoodId(String goodId){
		List<LzzUserGoodInterested> array_all = getAllValidLzzUserGoodInterested();
		List<LzzUserGoodInterested> rslt = new ArrayList<LzzUserGoodInterested>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getGoodId()
				&& array_all.get(i).getGoodId().equals(goodId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}

	public List<LzzUserGoodViewed> getLzzUserGoodViewedListByUserId(String userId){
		List<LzzUserGoodViewed> array_all = getAllValidLzzUserGoodViewed();
		List<LzzUserGoodViewed> rslt = new ArrayList<LzzUserGoodViewed>();
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getUserId()
				&& array_all.get(i).getUserId().equals(userId)){
				rslt.add(array_all.get(i));
			}
		}
		
		return rslt;
	}


	public LzzUserGoodInterested getLzzUserGoodInterestedByUserIdAndGoodId(String userId, String goodId){
		List<LzzUserGoodInterested> array_all = getAllValidLzzUserGoodInterested();
		LzzUserGoodInterested rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getUserId()
				&& array_all.get(i).getUserId().equals(userId)
				&& null!=array_all.get(i).getGoodId()
				&& array_all.get(i).getGoodId().equals(goodId)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}

	public LzzUserAddress getLzzUserAddressByUserIdAndUsualFlag(String userId, String usualFlag){
		List<LzzUserAddress> array_all = getAllValidLzzUserAddress();
		LzzUserAddress rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getUserId()
				&& array_all.get(i).getUserId().equals(userId)
				&& null!=array_all.get(i).getUsualFlag()
				&& array_all.get(i).getUsualFlag().equals(usualFlag)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}

	public LzzUserGoodViewed getLzzUserGoodViewedByUserIdAndGoodId(String userId, String goodId){
		List<LzzUserGoodViewed> array_all = getAllValidLzzUserGoodViewed();
		LzzUserGoodViewed rslt = null;
		
		for(int i=0;i<array_all.size();i++){
			if(null!=array_all.get(i).getUserId()
				&& array_all.get(i).getUserId().equals(userId)
				&& null!=array_all.get(i).getGoodId()
				&& array_all.get(i).getGoodId().equals(goodId)){
				return array_all.get(i);
			}
		}
		
		return rslt;
	}




}



