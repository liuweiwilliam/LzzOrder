package com.lzz.order.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.order.pojo.LzzExpressCompany;


import com.lzz.order.factory.LzzFactory;
import com.lzz.order.dao.LzzDao;
public class LzzExpressCompanyCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzExpressCompany> mLzzExpressCompanys;
	private boolean mLzzExpressCompanysLoaded;
	private Hashtable<String, LzzExpressCompany> mLzzExpressCompanyHash;


	// Singleton functions ( construction is private)
	private final static LzzExpressCompanyCacheMgr singleton = new LzzExpressCompanyCacheMgr ();	
	public static LzzExpressCompanyCacheMgr self(){
		return singleton;
	}
	public LzzExpressCompanyCacheMgr getSelf(){
		return self();
	}
	private LzzExpressCompanyCacheMgr (){
		dao = new LzzDao();
		mLzzExpressCompanys = new Vector<LzzExpressCompany>();
		mLzzExpressCompanysLoaded = false;
		mLzzExpressCompanyHash = new Hashtable<String,LzzExpressCompany>();

	}
	synchronized private boolean loadLzzExpressCompanys(){

		if(!mLzzExpressCompanysLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzExpressCompany> array = (List<LzzExpressCompany>)dao.queryAll("LzzExpressCompany");
			
			for(int i = 0;i < array.size();i++){
				mLzzExpressCompanys.add(array.get(i));
				mLzzExpressCompanyHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzExpressCompanysLoaded = true;
		}
		return true;
	}



	public boolean loadAllDB(){
		loadLzzExpressCompanys();	

		return true;
	}

	public boolean clearCache(){
		mLzzExpressCompanys.clear();
		mLzzExpressCompanysLoaded = false;
		mLzzExpressCompanyHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzExpressCompany(Object obj) {
		loadLzzExpressCompanys();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzExpressCompany obj2 = (LzzExpressCompany)obj;
		mLzzExpressCompanys.add(obj2);
		mLzzExpressCompanyHash.put(obj2.getId(), obj2);
	}
	public void delLzzExpressCompany(Object obj) {
		loadLzzExpressCompanys();

		LzzExpressCompany obj2 = (LzzExpressCompany)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzExpressCompanyHash.get(obj2.getId()));

		for(LzzExpressCompany msg : mLzzExpressCompanys){
			if(msg.getId().equals(obj2.getId())){
				mLzzExpressCompanys.remove(msg);
				break;
			}
		}
		mLzzExpressCompanyHash.remove(obj2.getId());
		
	}
	public void updateLzzExpressCompany(Object obj) {
		loadLzzExpressCompanys();
		
		for(int i = 0; i < mLzzExpressCompanys.size(); i++){
			if(mLzzExpressCompanys.get(i).getId().equals(((LzzExpressCompany)obj).getId())){
					mLzzExpressCompanys.set(i, (LzzExpressCompany) obj);
					break;
			}
		}
		LzzExpressCompany tmp = mLzzExpressCompanyHash.get(((LzzExpressCompany)obj).getId());
		tmp.constructWith((LzzExpressCompany)obj);
		
		//mLzzExpressCompanyHash.remove(((LzzExpressCompany)obj).getId());
		//mLzzExpressCompanyHash.put(((LzzExpressCompany)obj).getId(), (LzzExpressCompany)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzExpressCompany> getAllLzzExpressCompany() {
		loadLzzExpressCompanys();
		
		List<LzzExpressCompany> objects = new ArrayList<LzzExpressCompany>();
		for(int i = 0;i < mLzzExpressCompanys.size(); i++)
		{
			objects.add(mLzzExpressCompanys.get(i).clone());
		}
		return objects;
	}

	public LzzExpressCompany getLzzExpressCompanyById(String id){
		if(null==id) return null;
		loadLzzExpressCompanys();
		if(null==mLzzExpressCompanyHash.get(id)) return null;
		return mLzzExpressCompanyHash.get(id).clone();
	}


}
