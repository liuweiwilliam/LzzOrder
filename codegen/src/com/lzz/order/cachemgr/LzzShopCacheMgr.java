package com.lzz.order.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.order.pojo.LzzShopEvaluate;

import com.lzz.order.pojo.LzzIndexDisplay;


import com.lzz.order.factory.LzzFactory;
import com.lzz.order.dao.LzzDao;
public class LzzShopCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzShopEvaluate> mLzzShopEvaluates;
	private boolean mLzzShopEvaluatesLoaded;
	private Hashtable<String, LzzShopEvaluate> mLzzShopEvaluateHash;

	private Vector<LzzIndexDisplay> mLzzIndexDisplays;
	private boolean mLzzIndexDisplaysLoaded;
	private Hashtable<String, LzzIndexDisplay> mLzzIndexDisplayHash;


	// Singleton functions ( construction is private)
	private final static LzzShopCacheMgr singleton = new LzzShopCacheMgr ();	
	public static LzzShopCacheMgr self(){
		return singleton;
	}
	public LzzShopCacheMgr getSelf(){
		return self();
	}
	private LzzShopCacheMgr (){
		dao = new LzzDao();
		mLzzShopEvaluates = new Vector<LzzShopEvaluate>();
		mLzzShopEvaluatesLoaded = false;
		mLzzShopEvaluateHash = new Hashtable<String,LzzShopEvaluate>();

		mLzzIndexDisplays = new Vector<LzzIndexDisplay>();
		mLzzIndexDisplaysLoaded = false;
		mLzzIndexDisplayHash = new Hashtable<String,LzzIndexDisplay>();

	}
	synchronized private boolean loadLzzShopEvaluates(){

		if(!mLzzShopEvaluatesLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzShopEvaluate> array = (List<LzzShopEvaluate>)dao.queryAll("LzzShopEvaluate");
			
			for(int i = 0;i < array.size();i++){
				mLzzShopEvaluates.add(array.get(i));
				mLzzShopEvaluateHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzShopEvaluatesLoaded = true;
		}
		return true;
	}


	synchronized private boolean loadLzzIndexDisplays(){

		if(!mLzzIndexDisplaysLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzIndexDisplay> array = (List<LzzIndexDisplay>)dao.queryAll("LzzIndexDisplay");
			
			for(int i = 0;i < array.size();i++){
				mLzzIndexDisplays.add(array.get(i));
				mLzzIndexDisplayHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzIndexDisplaysLoaded = true;
		}
		return true;
	}



	public boolean loadAllDB(){
		loadLzzShopEvaluates();	

		loadLzzIndexDisplays();	

		return true;
	}

	public boolean clearCache(){
		mLzzShopEvaluates.clear();
		mLzzShopEvaluatesLoaded = false;
		mLzzShopEvaluateHash.clear();

		mLzzIndexDisplays.clear();
		mLzzIndexDisplaysLoaded = false;
		mLzzIndexDisplayHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzShopEvaluate(Object obj) {
		loadLzzShopEvaluates();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzShopEvaluate obj2 = (LzzShopEvaluate)obj;
		mLzzShopEvaluates.add(obj2);
		mLzzShopEvaluateHash.put(obj2.getId(), obj2);
	}
	public void delLzzShopEvaluate(Object obj) {
		loadLzzShopEvaluates();

		LzzShopEvaluate obj2 = (LzzShopEvaluate)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzShopEvaluateHash.get(obj2.getId()));

		for(LzzShopEvaluate msg : mLzzShopEvaluates){
			if(msg.getId().equals(obj2.getId())){
				mLzzShopEvaluates.remove(msg);
				break;
			}
		}
		mLzzShopEvaluateHash.remove(obj2.getId());
		
	}
	public void updateLzzShopEvaluate(Object obj) {
		loadLzzShopEvaluates();
		
		for(int i = 0; i < mLzzShopEvaluates.size(); i++){
			if(mLzzShopEvaluates.get(i).getId().equals(((LzzShopEvaluate)obj).getId())){
					mLzzShopEvaluates.set(i, (LzzShopEvaluate) obj);
					break;
			}
		}
		LzzShopEvaluate tmp = mLzzShopEvaluateHash.get(((LzzShopEvaluate)obj).getId());
		tmp.constructWith((LzzShopEvaluate)obj);
		
		//mLzzShopEvaluateHash.remove(((LzzShopEvaluate)obj).getId());
		//mLzzShopEvaluateHash.put(((LzzShopEvaluate)obj).getId(), (LzzShopEvaluate)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzShopEvaluate> getAllLzzShopEvaluate() {
		loadLzzShopEvaluates();
		
		List<LzzShopEvaluate> objects = new ArrayList<LzzShopEvaluate>();
		for(int i = 0;i < mLzzShopEvaluates.size(); i++)
		{
			objects.add(mLzzShopEvaluates.get(i).clone());
		}
		return objects;
	}

	public LzzShopEvaluate getLzzShopEvaluateById(String id){
		if(null==id) return null;
		loadLzzShopEvaluates();
		if(null==mLzzShopEvaluateHash.get(id)) return null;
		return mLzzShopEvaluateHash.get(id).clone();
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzIndexDisplay(Object obj) {
		loadLzzIndexDisplays();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzIndexDisplay obj2 = (LzzIndexDisplay)obj;
		mLzzIndexDisplays.add(obj2);
		mLzzIndexDisplayHash.put(obj2.getId(), obj2);
	}
	public void delLzzIndexDisplay(Object obj) {
		loadLzzIndexDisplays();

		LzzIndexDisplay obj2 = (LzzIndexDisplay)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzIndexDisplayHash.get(obj2.getId()));

		for(LzzIndexDisplay msg : mLzzIndexDisplays){
			if(msg.getId().equals(obj2.getId())){
				mLzzIndexDisplays.remove(msg);
				break;
			}
		}
		mLzzIndexDisplayHash.remove(obj2.getId());
		
	}
	public void updateLzzIndexDisplay(Object obj) {
		loadLzzIndexDisplays();
		
		for(int i = 0; i < mLzzIndexDisplays.size(); i++){
			if(mLzzIndexDisplays.get(i).getId().equals(((LzzIndexDisplay)obj).getId())){
					mLzzIndexDisplays.set(i, (LzzIndexDisplay) obj);
					break;
			}
		}
		LzzIndexDisplay tmp = mLzzIndexDisplayHash.get(((LzzIndexDisplay)obj).getId());
		tmp.constructWith((LzzIndexDisplay)obj);
		
		//mLzzIndexDisplayHash.remove(((LzzIndexDisplay)obj).getId());
		//mLzzIndexDisplayHash.put(((LzzIndexDisplay)obj).getId(), (LzzIndexDisplay)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzIndexDisplay> getAllLzzIndexDisplay() {
		loadLzzIndexDisplays();
		
		List<LzzIndexDisplay> objects = new ArrayList<LzzIndexDisplay>();
		for(int i = 0;i < mLzzIndexDisplays.size(); i++)
		{
			objects.add(mLzzIndexDisplays.get(i).clone());
		}
		return objects;
	}

	public LzzIndexDisplay getLzzIndexDisplayById(String id){
		if(null==id) return null;
		loadLzzIndexDisplays();
		if(null==mLzzIndexDisplayHash.get(id)) return null;
		return mLzzIndexDisplayHash.get(id).clone();
	}


}
