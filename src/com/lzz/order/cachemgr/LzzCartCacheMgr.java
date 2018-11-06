package com.lzz.order.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.order.pojo.LzzCart;


import com.lzz.order.factory.LzzFactory;
import com.lzz.order.dao.LzzDao;
public class LzzCartCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzCart> mLzzCarts;
	private boolean mLzzCartsLoaded;
	private Hashtable<String, LzzCart> mLzzCartHash;


	// Singleton functions ( construction is private)
	private final static LzzCartCacheMgr singleton = new LzzCartCacheMgr ();	
	public static LzzCartCacheMgr self(){
		return singleton;
	}
	public LzzCartCacheMgr getSelf(){
		return self();
	}
	private LzzCartCacheMgr (){
		dao = new LzzDao();
		mLzzCarts = new Vector<LzzCart>();
		mLzzCartsLoaded = false;
		mLzzCartHash = new Hashtable<String,LzzCart>();

	}
	synchronized private boolean loadLzzCarts(){

		if(!mLzzCartsLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzCart> array = (List<LzzCart>)dao.queryAll("LzzCart");
			
			for(int i = 0;i < array.size();i++){
				mLzzCarts.add(array.get(i));
				mLzzCartHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzCartsLoaded = true;
		}
		return true;
	}



	public boolean loadAllDB(){
		loadLzzCarts();	

		return true;
	}

	public boolean clearCache(){
		mLzzCarts.clear();
		mLzzCartsLoaded = false;
		mLzzCartHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzCart(Object obj) {
		loadLzzCarts();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzCart obj2 = (LzzCart)obj;
		mLzzCarts.add(obj2);
		mLzzCartHash.put(obj2.getId(), obj2);
	}
	public void delLzzCart(Object obj) {
		loadLzzCarts();

		LzzCart obj2 = (LzzCart)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzCartHash.get(obj2.getId()));

		for(LzzCart msg : mLzzCarts){
			if(msg.getId().equals(obj2.getId())){
				mLzzCarts.remove(msg);
				break;
			}
		}
		mLzzCartHash.remove(obj2.getId());
		
	}
	public void updateLzzCart(Object obj) {
		loadLzzCarts();
		
		for(int i = 0; i < mLzzCarts.size(); i++){
			if(mLzzCarts.get(i).getId().equals(((LzzCart)obj).getId())){
					mLzzCarts.set(i, (LzzCart) obj);
					break;
			}
		}
		LzzCart tmp = mLzzCartHash.get(((LzzCart)obj).getId());
		tmp.constructWith((LzzCart)obj);
		
		//mLzzCartHash.remove(((LzzCart)obj).getId());
		//mLzzCartHash.put(((LzzCart)obj).getId(), (LzzCart)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzCart> getAllLzzCart() {
		loadLzzCarts();
		
		List<LzzCart> objects = new ArrayList<LzzCart>();
		for(int i = 0;i < mLzzCarts.size(); i++)
		{
			objects.add(mLzzCarts.get(i).clone());
		}
		return objects;
	}

	public LzzCart getLzzCartById(String id){
		if(null==id) return null;
		loadLzzCarts();
		if(null==mLzzCartHash.get(id)) return null;
		return mLzzCartHash.get(id).clone();
	}


}
