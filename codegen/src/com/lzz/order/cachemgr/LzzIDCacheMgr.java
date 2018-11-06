package com.lzz.order.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.order.pojo.LzzID;


import com.lzz.order.factory.LzzFactory;
import com.lzz.order.dao.LzzDao;
public class LzzIDCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzID> mLzzIDs;
	private boolean mLzzIDsLoaded;
	private Hashtable<String, LzzID> mLzzIDHash;


	// Singleton functions ( construction is private)
	private final static LzzIDCacheMgr singleton = new LzzIDCacheMgr ();	
	public static LzzIDCacheMgr self(){
		return singleton;
	}
	public LzzIDCacheMgr getSelf(){
		return self();
	}
	private LzzIDCacheMgr (){
		dao = new LzzDao();
		mLzzIDs = new Vector<LzzID>();
		mLzzIDsLoaded = false;
		mLzzIDHash = new Hashtable<String,LzzID>();

	}
	synchronized private boolean loadLzzIDs(){

		if(!mLzzIDsLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzID> array = (List<LzzID>)dao.queryAll("LzzID");
			
			for(int i = 0;i < array.size();i++){
				mLzzIDs.add(array.get(i));
				mLzzIDHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzIDsLoaded = true;
		}
		return true;
	}



	public boolean loadAllDB(){
		loadLzzIDs();	

		return true;
	}

	public boolean clearCache(){
		mLzzIDs.clear();
		mLzzIDsLoaded = false;
		mLzzIDHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzID(Object obj) {
		loadLzzIDs();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzID obj2 = (LzzID)obj;
		mLzzIDs.add(obj2);
		mLzzIDHash.put(obj2.getId(), obj2);
	}
	public void delLzzID(Object obj) {
		loadLzzIDs();

		LzzID obj2 = (LzzID)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzIDHash.get(obj2.getId()));

		for(LzzID msg : mLzzIDs){
			if(msg.getId().equals(obj2.getId())){
				mLzzIDs.remove(msg);
				break;
			}
		}
		mLzzIDHash.remove(obj2.getId());
		
	}
	public void updateLzzID(Object obj) {
		loadLzzIDs();
		
		for(int i = 0; i < mLzzIDs.size(); i++){
			if(mLzzIDs.get(i).getId().equals(((LzzID)obj).getId())){
					mLzzIDs.set(i, (LzzID) obj);
					break;
			}
		}
		LzzID tmp = mLzzIDHash.get(((LzzID)obj).getId());
		tmp.constructWith((LzzID)obj);
		
		//mLzzIDHash.remove(((LzzID)obj).getId());
		//mLzzIDHash.put(((LzzID)obj).getId(), (LzzID)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzID> getAllLzzID() {
		loadLzzIDs();
		
		List<LzzID> objects = new ArrayList<LzzID>();
		for(int i = 0;i < mLzzIDs.size(); i++)
		{
			objects.add(mLzzIDs.get(i).clone());
		}
		return objects;
	}

	public LzzID getLzzIDById(String id){
		if(null==id) return null;
		loadLzzIDs();
		if(null==mLzzIDHash.get(id)) return null;
		return mLzzIDHash.get(id).clone();
	}


}
