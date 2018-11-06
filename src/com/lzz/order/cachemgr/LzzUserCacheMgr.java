package com.lzz.order.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.order.pojo.LzzUser;

import com.lzz.order.pojo.LzzUserTaste;

import com.lzz.order.pojo.LzzUserAddress;

import com.lzz.order.pojo.LzzUserCoupon;

import com.lzz.order.pojo.LzzUserGoodInterested;

import com.lzz.order.pojo.LzzUserGoodViewed;

import com.lzz.order.pojo.LzzUserSession;


import com.lzz.order.factory.LzzFactory;
import com.lzz.order.dao.LzzDao;
public class LzzUserCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzUser> mLzzUsers;
	private boolean mLzzUsersLoaded;
	private Hashtable<String, LzzUser> mLzzUserHash;

	private Vector<LzzUserTaste> mLzzUserTastes;
	private boolean mLzzUserTastesLoaded;
	private Hashtable<String, LzzUserTaste> mLzzUserTasteHash;

	private Vector<LzzUserAddress> mLzzUserAddresss;
	private boolean mLzzUserAddresssLoaded;
	private Hashtable<String, LzzUserAddress> mLzzUserAddressHash;

	private Vector<LzzUserCoupon> mLzzUserCoupons;
	private boolean mLzzUserCouponsLoaded;
	private Hashtable<String, LzzUserCoupon> mLzzUserCouponHash;

	private Vector<LzzUserGoodInterested> mLzzUserGoodInteresteds;
	private boolean mLzzUserGoodInterestedsLoaded;
	private Hashtable<String, LzzUserGoodInterested> mLzzUserGoodInterestedHash;

	private Vector<LzzUserGoodViewed> mLzzUserGoodVieweds;
	private boolean mLzzUserGoodViewedsLoaded;
	private Hashtable<String, LzzUserGoodViewed> mLzzUserGoodViewedHash;

	private Vector<LzzUserSession> mLzzUserSessions;
	private boolean mLzzUserSessionsLoaded;
	private Hashtable<String, LzzUserSession> mLzzUserSessionHash;


	// Singleton functions ( construction is private)
	private final static LzzUserCacheMgr singleton = new LzzUserCacheMgr ();	
	public static LzzUserCacheMgr self(){
		return singleton;
	}
	public LzzUserCacheMgr getSelf(){
		return self();
	}
	private LzzUserCacheMgr (){
		dao = new LzzDao();
		mLzzUsers = new Vector<LzzUser>();
		mLzzUsersLoaded = false;
		mLzzUserHash = new Hashtable<String,LzzUser>();

		mLzzUserTastes = new Vector<LzzUserTaste>();
		mLzzUserTastesLoaded = false;
		mLzzUserTasteHash = new Hashtable<String,LzzUserTaste>();

		mLzzUserAddresss = new Vector<LzzUserAddress>();
		mLzzUserAddresssLoaded = false;
		mLzzUserAddressHash = new Hashtable<String,LzzUserAddress>();

		mLzzUserCoupons = new Vector<LzzUserCoupon>();
		mLzzUserCouponsLoaded = false;
		mLzzUserCouponHash = new Hashtable<String,LzzUserCoupon>();

		mLzzUserGoodInteresteds = new Vector<LzzUserGoodInterested>();
		mLzzUserGoodInterestedsLoaded = false;
		mLzzUserGoodInterestedHash = new Hashtable<String,LzzUserGoodInterested>();

		mLzzUserGoodVieweds = new Vector<LzzUserGoodViewed>();
		mLzzUserGoodViewedsLoaded = false;
		mLzzUserGoodViewedHash = new Hashtable<String,LzzUserGoodViewed>();

		mLzzUserSessions = new Vector<LzzUserSession>();
		mLzzUserSessionsLoaded = false;
		mLzzUserSessionHash = new Hashtable<String,LzzUserSession>();

	}
	synchronized private boolean loadLzzUsers(){

		if(!mLzzUsersLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzUser> array = (List<LzzUser>)dao.queryAll("LzzUser");
			
			for(int i = 0;i < array.size();i++){
				mLzzUsers.add(array.get(i));
				mLzzUserHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzUsersLoaded = true;
		}
		return true;
	}


	synchronized private boolean loadLzzUserTastes(){

		if(!mLzzUserTastesLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzUserTaste> array = (List<LzzUserTaste>)dao.queryAll("LzzUserTaste");
			
			for(int i = 0;i < array.size();i++){
				mLzzUserTastes.add(array.get(i));
				mLzzUserTasteHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzUserTastesLoaded = true;
		}
		return true;
	}


	synchronized private boolean loadLzzUserAddresss(){

		if(!mLzzUserAddresssLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzUserAddress> array = (List<LzzUserAddress>)dao.queryAll("LzzUserAddress");
			
			for(int i = 0;i < array.size();i++){
				mLzzUserAddresss.add(array.get(i));
				mLzzUserAddressHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzUserAddresssLoaded = true;
		}
		return true;
	}


	synchronized private boolean loadLzzUserCoupons(){

		if(!mLzzUserCouponsLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzUserCoupon> array = (List<LzzUserCoupon>)dao.queryAll("LzzUserCoupon");
			
			for(int i = 0;i < array.size();i++){
				mLzzUserCoupons.add(array.get(i));
				mLzzUserCouponHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzUserCouponsLoaded = true;
		}
		return true;
	}


	synchronized private boolean loadLzzUserGoodInteresteds(){

		if(!mLzzUserGoodInterestedsLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzUserGoodInterested> array = (List<LzzUserGoodInterested>)dao.queryAll("LzzUserGoodInterested");
			
			for(int i = 0;i < array.size();i++){
				mLzzUserGoodInteresteds.add(array.get(i));
				mLzzUserGoodInterestedHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzUserGoodInterestedsLoaded = true;
		}
		return true;
	}


	synchronized private boolean loadLzzUserGoodVieweds(){

		if(!mLzzUserGoodViewedsLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzUserGoodViewed> array = (List<LzzUserGoodViewed>)dao.queryAll("LzzUserGoodViewed");
			
			for(int i = 0;i < array.size();i++){
				mLzzUserGoodVieweds.add(array.get(i));
				mLzzUserGoodViewedHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzUserGoodViewedsLoaded = true;
		}
		return true;
	}


	synchronized private boolean loadLzzUserSessions(){

		if(!mLzzUserSessionsLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzUserSession> array = (List<LzzUserSession>)dao.queryAll("LzzUserSession");
			
			for(int i = 0;i < array.size();i++){
				mLzzUserSessions.add(array.get(i));
				mLzzUserSessionHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzUserSessionsLoaded = true;
		}
		return true;
	}



	public boolean loadAllDB(){
		loadLzzUsers();	

		loadLzzUserTastes();	

		loadLzzUserAddresss();	

		loadLzzUserCoupons();	

		loadLzzUserGoodInteresteds();	

		loadLzzUserGoodVieweds();	

		loadLzzUserSessions();	

		return true;
	}

	public boolean clearCache(){
		mLzzUsers.clear();
		mLzzUsersLoaded = false;
		mLzzUserHash.clear();

		mLzzUserTastes.clear();
		mLzzUserTastesLoaded = false;
		mLzzUserTasteHash.clear();

		mLzzUserAddresss.clear();
		mLzzUserAddresssLoaded = false;
		mLzzUserAddressHash.clear();

		mLzzUserCoupons.clear();
		mLzzUserCouponsLoaded = false;
		mLzzUserCouponHash.clear();

		mLzzUserGoodInteresteds.clear();
		mLzzUserGoodInterestedsLoaded = false;
		mLzzUserGoodInterestedHash.clear();

		mLzzUserGoodVieweds.clear();
		mLzzUserGoodViewedsLoaded = false;
		mLzzUserGoodViewedHash.clear();

		mLzzUserSessions.clear();
		mLzzUserSessionsLoaded = false;
		mLzzUserSessionHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzUser(Object obj) {
		loadLzzUsers();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzUser obj2 = (LzzUser)obj;
		mLzzUsers.add(obj2);
		mLzzUserHash.put(obj2.getId(), obj2);
	}
	public void delLzzUser(Object obj) {
		loadLzzUsers();

		LzzUser obj2 = (LzzUser)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzUserHash.get(obj2.getId()));

		for(LzzUser msg : mLzzUsers){
			if(msg.getId().equals(obj2.getId())){
				mLzzUsers.remove(msg);
				break;
			}
		}
		mLzzUserHash.remove(obj2.getId());
		
	}
	public void updateLzzUser(Object obj) {
		loadLzzUsers();
		
		for(int i = 0; i < mLzzUsers.size(); i++){
			if(mLzzUsers.get(i).getId().equals(((LzzUser)obj).getId())){
					mLzzUsers.set(i, (LzzUser) obj);
					break;
			}
		}
		LzzUser tmp = mLzzUserHash.get(((LzzUser)obj).getId());
		tmp.constructWith((LzzUser)obj);
		
		//mLzzUserHash.remove(((LzzUser)obj).getId());
		//mLzzUserHash.put(((LzzUser)obj).getId(), (LzzUser)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzUser> getAllLzzUser() {
		loadLzzUsers();
		
		List<LzzUser> objects = new ArrayList<LzzUser>();
		for(int i = 0;i < mLzzUsers.size(); i++)
		{
			objects.add(mLzzUsers.get(i).clone());
		}
		return objects;
	}

	public LzzUser getLzzUserById(String id){
		if(null==id) return null;
		loadLzzUsers();
		if(null==mLzzUserHash.get(id)) return null;
		return mLzzUserHash.get(id).clone();
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzUserTaste(Object obj) {
		loadLzzUserTastes();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzUserTaste obj2 = (LzzUserTaste)obj;
		mLzzUserTastes.add(obj2);
		mLzzUserTasteHash.put(obj2.getId(), obj2);
	}
	public void delLzzUserTaste(Object obj) {
		loadLzzUserTastes();

		LzzUserTaste obj2 = (LzzUserTaste)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzUserTasteHash.get(obj2.getId()));

		for(LzzUserTaste msg : mLzzUserTastes){
			if(msg.getId().equals(obj2.getId())){
				mLzzUserTastes.remove(msg);
				break;
			}
		}
		mLzzUserTasteHash.remove(obj2.getId());
		
	}
	public void updateLzzUserTaste(Object obj) {
		loadLzzUserTastes();
		
		for(int i = 0; i < mLzzUserTastes.size(); i++){
			if(mLzzUserTastes.get(i).getId().equals(((LzzUserTaste)obj).getId())){
					mLzzUserTastes.set(i, (LzzUserTaste) obj);
					break;
			}
		}
		LzzUserTaste tmp = mLzzUserTasteHash.get(((LzzUserTaste)obj).getId());
		tmp.constructWith((LzzUserTaste)obj);
		
		//mLzzUserTasteHash.remove(((LzzUserTaste)obj).getId());
		//mLzzUserTasteHash.put(((LzzUserTaste)obj).getId(), (LzzUserTaste)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzUserTaste> getAllLzzUserTaste() {
		loadLzzUserTastes();
		
		List<LzzUserTaste> objects = new ArrayList<LzzUserTaste>();
		for(int i = 0;i < mLzzUserTastes.size(); i++)
		{
			objects.add(mLzzUserTastes.get(i).clone());
		}
		return objects;
	}

	public LzzUserTaste getLzzUserTasteById(String id){
		if(null==id) return null;
		loadLzzUserTastes();
		if(null==mLzzUserTasteHash.get(id)) return null;
		return mLzzUserTasteHash.get(id).clone();
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzUserAddress(Object obj) {
		loadLzzUserAddresss();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzUserAddress obj2 = (LzzUserAddress)obj;
		mLzzUserAddresss.add(obj2);
		mLzzUserAddressHash.put(obj2.getId(), obj2);
	}
	public void delLzzUserAddress(Object obj) {
		loadLzzUserAddresss();

		LzzUserAddress obj2 = (LzzUserAddress)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzUserAddressHash.get(obj2.getId()));

		for(LzzUserAddress msg : mLzzUserAddresss){
			if(msg.getId().equals(obj2.getId())){
				mLzzUserAddresss.remove(msg);
				break;
			}
		}
		mLzzUserAddressHash.remove(obj2.getId());
		
	}
	public void updateLzzUserAddress(Object obj) {
		loadLzzUserAddresss();
		
		for(int i = 0; i < mLzzUserAddresss.size(); i++){
			if(mLzzUserAddresss.get(i).getId().equals(((LzzUserAddress)obj).getId())){
					mLzzUserAddresss.set(i, (LzzUserAddress) obj);
					break;
			}
		}
		LzzUserAddress tmp = mLzzUserAddressHash.get(((LzzUserAddress)obj).getId());
		tmp.constructWith((LzzUserAddress)obj);
		
		//mLzzUserAddressHash.remove(((LzzUserAddress)obj).getId());
		//mLzzUserAddressHash.put(((LzzUserAddress)obj).getId(), (LzzUserAddress)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzUserAddress> getAllLzzUserAddress() {
		loadLzzUserAddresss();
		
		List<LzzUserAddress> objects = new ArrayList<LzzUserAddress>();
		for(int i = 0;i < mLzzUserAddresss.size(); i++)
		{
			objects.add(mLzzUserAddresss.get(i).clone());
		}
		return objects;
	}

	public LzzUserAddress getLzzUserAddressById(String id){
		if(null==id) return null;
		loadLzzUserAddresss();
		if(null==mLzzUserAddressHash.get(id)) return null;
		return mLzzUserAddressHash.get(id).clone();
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzUserCoupon(Object obj) {
		loadLzzUserCoupons();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzUserCoupon obj2 = (LzzUserCoupon)obj;
		mLzzUserCoupons.add(obj2);
		mLzzUserCouponHash.put(obj2.getId(), obj2);
	}
	public void delLzzUserCoupon(Object obj) {
		loadLzzUserCoupons();

		LzzUserCoupon obj2 = (LzzUserCoupon)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzUserCouponHash.get(obj2.getId()));

		for(LzzUserCoupon msg : mLzzUserCoupons){
			if(msg.getId().equals(obj2.getId())){
				mLzzUserCoupons.remove(msg);
				break;
			}
		}
		mLzzUserCouponHash.remove(obj2.getId());
		
	}
	public void updateLzzUserCoupon(Object obj) {
		loadLzzUserCoupons();
		
		for(int i = 0; i < mLzzUserCoupons.size(); i++){
			if(mLzzUserCoupons.get(i).getId().equals(((LzzUserCoupon)obj).getId())){
					mLzzUserCoupons.set(i, (LzzUserCoupon) obj);
					break;
			}
		}
		LzzUserCoupon tmp = mLzzUserCouponHash.get(((LzzUserCoupon)obj).getId());
		tmp.constructWith((LzzUserCoupon)obj);
		
		//mLzzUserCouponHash.remove(((LzzUserCoupon)obj).getId());
		//mLzzUserCouponHash.put(((LzzUserCoupon)obj).getId(), (LzzUserCoupon)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzUserCoupon> getAllLzzUserCoupon() {
		loadLzzUserCoupons();
		
		List<LzzUserCoupon> objects = new ArrayList<LzzUserCoupon>();
		for(int i = 0;i < mLzzUserCoupons.size(); i++)
		{
			objects.add(mLzzUserCoupons.get(i).clone());
		}
		return objects;
	}

	public LzzUserCoupon getLzzUserCouponById(String id){
		if(null==id) return null;
		loadLzzUserCoupons();
		if(null==mLzzUserCouponHash.get(id)) return null;
		return mLzzUserCouponHash.get(id).clone();
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzUserGoodInterested(Object obj) {
		loadLzzUserGoodInteresteds();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzUserGoodInterested obj2 = (LzzUserGoodInterested)obj;
		mLzzUserGoodInteresteds.add(obj2);
		mLzzUserGoodInterestedHash.put(obj2.getId(), obj2);
	}
	public void delLzzUserGoodInterested(Object obj) {
		loadLzzUserGoodInteresteds();

		LzzUserGoodInterested obj2 = (LzzUserGoodInterested)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzUserGoodInterestedHash.get(obj2.getId()));

		for(LzzUserGoodInterested msg : mLzzUserGoodInteresteds){
			if(msg.getId().equals(obj2.getId())){
				mLzzUserGoodInteresteds.remove(msg);
				break;
			}
		}
		mLzzUserGoodInterestedHash.remove(obj2.getId());
		
	}
	public void updateLzzUserGoodInterested(Object obj) {
		loadLzzUserGoodInteresteds();
		
		for(int i = 0; i < mLzzUserGoodInteresteds.size(); i++){
			if(mLzzUserGoodInteresteds.get(i).getId().equals(((LzzUserGoodInterested)obj).getId())){
					mLzzUserGoodInteresteds.set(i, (LzzUserGoodInterested) obj);
					break;
			}
		}
		LzzUserGoodInterested tmp = mLzzUserGoodInterestedHash.get(((LzzUserGoodInterested)obj).getId());
		tmp.constructWith((LzzUserGoodInterested)obj);
		
		//mLzzUserGoodInterestedHash.remove(((LzzUserGoodInterested)obj).getId());
		//mLzzUserGoodInterestedHash.put(((LzzUserGoodInterested)obj).getId(), (LzzUserGoodInterested)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzUserGoodInterested> getAllLzzUserGoodInterested() {
		loadLzzUserGoodInteresteds();
		
		List<LzzUserGoodInterested> objects = new ArrayList<LzzUserGoodInterested>();
		for(int i = 0;i < mLzzUserGoodInteresteds.size(); i++)
		{
			objects.add(mLzzUserGoodInteresteds.get(i).clone());
		}
		return objects;
	}

	public LzzUserGoodInterested getLzzUserGoodInterestedById(String id){
		if(null==id) return null;
		loadLzzUserGoodInteresteds();
		if(null==mLzzUserGoodInterestedHash.get(id)) return null;
		return mLzzUserGoodInterestedHash.get(id).clone();
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzUserGoodViewed(Object obj) {
		loadLzzUserGoodVieweds();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzUserGoodViewed obj2 = (LzzUserGoodViewed)obj;
		mLzzUserGoodVieweds.add(obj2);
		mLzzUserGoodViewedHash.put(obj2.getId(), obj2);
	}
	public void delLzzUserGoodViewed(Object obj) {
		loadLzzUserGoodVieweds();

		LzzUserGoodViewed obj2 = (LzzUserGoodViewed)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzUserGoodViewedHash.get(obj2.getId()));

		for(LzzUserGoodViewed msg : mLzzUserGoodVieweds){
			if(msg.getId().equals(obj2.getId())){
				mLzzUserGoodVieweds.remove(msg);
				break;
			}
		}
		mLzzUserGoodViewedHash.remove(obj2.getId());
		
	}
	public void updateLzzUserGoodViewed(Object obj) {
		loadLzzUserGoodVieweds();
		
		for(int i = 0; i < mLzzUserGoodVieweds.size(); i++){
			if(mLzzUserGoodVieweds.get(i).getId().equals(((LzzUserGoodViewed)obj).getId())){
					mLzzUserGoodVieweds.set(i, (LzzUserGoodViewed) obj);
					break;
			}
		}
		LzzUserGoodViewed tmp = mLzzUserGoodViewedHash.get(((LzzUserGoodViewed)obj).getId());
		tmp.constructWith((LzzUserGoodViewed)obj);
		
		//mLzzUserGoodViewedHash.remove(((LzzUserGoodViewed)obj).getId());
		//mLzzUserGoodViewedHash.put(((LzzUserGoodViewed)obj).getId(), (LzzUserGoodViewed)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzUserGoodViewed> getAllLzzUserGoodViewed() {
		loadLzzUserGoodVieweds();
		
		List<LzzUserGoodViewed> objects = new ArrayList<LzzUserGoodViewed>();
		for(int i = 0;i < mLzzUserGoodVieweds.size(); i++)
		{
			objects.add(mLzzUserGoodVieweds.get(i).clone());
		}
		return objects;
	}

	public LzzUserGoodViewed getLzzUserGoodViewedById(String id){
		if(null==id) return null;
		loadLzzUserGoodVieweds();
		if(null==mLzzUserGoodViewedHash.get(id)) return null;
		return mLzzUserGoodViewedHash.get(id).clone();
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzUserSession(Object obj) {
		loadLzzUserSessions();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzUserSession obj2 = (LzzUserSession)obj;
		mLzzUserSessions.add(obj2);
		mLzzUserSessionHash.put(obj2.getId(), obj2);
	}
	public void delLzzUserSession(Object obj) {
		loadLzzUserSessions();

		LzzUserSession obj2 = (LzzUserSession)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzUserSessionHash.get(obj2.getId()));

		for(LzzUserSession msg : mLzzUserSessions){
			if(msg.getId().equals(obj2.getId())){
				mLzzUserSessions.remove(msg);
				break;
			}
		}
		mLzzUserSessionHash.remove(obj2.getId());
		
	}
	public void updateLzzUserSession(Object obj) {
		loadLzzUserSessions();
		
		for(int i = 0; i < mLzzUserSessions.size(); i++){
			if(mLzzUserSessions.get(i).getId().equals(((LzzUserSession)obj).getId())){
					mLzzUserSessions.set(i, (LzzUserSession) obj);
					break;
			}
		}
		LzzUserSession tmp = mLzzUserSessionHash.get(((LzzUserSession)obj).getId());
		tmp.constructWith((LzzUserSession)obj);
		
		//mLzzUserSessionHash.remove(((LzzUserSession)obj).getId());
		//mLzzUserSessionHash.put(((LzzUserSession)obj).getId(), (LzzUserSession)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzUserSession> getAllLzzUserSession() {
		loadLzzUserSessions();
		
		List<LzzUserSession> objects = new ArrayList<LzzUserSession>();
		for(int i = 0;i < mLzzUserSessions.size(); i++)
		{
			objects.add(mLzzUserSessions.get(i).clone());
		}
		return objects;
	}

	public LzzUserSession getLzzUserSessionById(String id){
		if(null==id) return null;
		loadLzzUserSessions();
		if(null==mLzzUserSessionHash.get(id)) return null;
		return mLzzUserSessionHash.get(id).clone();
	}


}
