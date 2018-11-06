package com.lzz.order.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.order.pojo.LzzCoupon;


import com.lzz.order.factory.LzzFactory;
import com.lzz.order.dao.LzzDao;
public class LzzCouponCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzCoupon> mLzzCoupons;
	private boolean mLzzCouponsLoaded;
	private Hashtable<String, LzzCoupon> mLzzCouponHash;


	// Singleton functions ( construction is private)
	private final static LzzCouponCacheMgr singleton = new LzzCouponCacheMgr ();	
	public static LzzCouponCacheMgr self(){
		return singleton;
	}
	public LzzCouponCacheMgr getSelf(){
		return self();
	}
	private LzzCouponCacheMgr (){
		dao = new LzzDao();
		mLzzCoupons = new Vector<LzzCoupon>();
		mLzzCouponsLoaded = false;
		mLzzCouponHash = new Hashtable<String,LzzCoupon>();

	}
	synchronized private boolean loadLzzCoupons(){

		if(!mLzzCouponsLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzCoupon> array = (List<LzzCoupon>)dao.queryAll("LzzCoupon");
			
			for(int i = 0;i < array.size();i++){
				mLzzCoupons.add(array.get(i));
				mLzzCouponHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzCouponsLoaded = true;
		}
		return true;
	}



	public boolean loadAllDB(){
		loadLzzCoupons();	

		return true;
	}

	public boolean clearCache(){
		mLzzCoupons.clear();
		mLzzCouponsLoaded = false;
		mLzzCouponHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzCoupon(Object obj) {
		loadLzzCoupons();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzCoupon obj2 = (LzzCoupon)obj;
		mLzzCoupons.add(obj2);
		mLzzCouponHash.put(obj2.getId(), obj2);
	}
	public void delLzzCoupon(Object obj) {
		loadLzzCoupons();

		LzzCoupon obj2 = (LzzCoupon)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzCouponHash.get(obj2.getId()));

		for(LzzCoupon msg : mLzzCoupons){
			if(msg.getId().equals(obj2.getId())){
				mLzzCoupons.remove(msg);
				break;
			}
		}
		mLzzCouponHash.remove(obj2.getId());
		
	}
	public void updateLzzCoupon(Object obj) {
		loadLzzCoupons();
		
		for(int i = 0; i < mLzzCoupons.size(); i++){
			if(mLzzCoupons.get(i).getId().equals(((LzzCoupon)obj).getId())){
					mLzzCoupons.set(i, (LzzCoupon) obj);
					break;
			}
		}
		LzzCoupon tmp = mLzzCouponHash.get(((LzzCoupon)obj).getId());
		tmp.constructWith((LzzCoupon)obj);
		
		//mLzzCouponHash.remove(((LzzCoupon)obj).getId());
		//mLzzCouponHash.put(((LzzCoupon)obj).getId(), (LzzCoupon)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzCoupon> getAllLzzCoupon() {
		loadLzzCoupons();
		
		List<LzzCoupon> objects = new ArrayList<LzzCoupon>();
		for(int i = 0;i < mLzzCoupons.size(); i++)
		{
			objects.add(mLzzCoupons.get(i).clone());
		}
		return objects;
	}

	public LzzCoupon getLzzCouponById(String id){
		if(null==id) return null;
		loadLzzCoupons();
		if(null==mLzzCouponHash.get(id)) return null;
		return mLzzCouponHash.get(id).clone();
	}


}
