package com.lzz.order.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.order.pojo.LzzAfterSale;


import com.lzz.order.factory.LzzFactory;
import com.lzz.order.dao.LzzDao;
public class LzzAfterSaleCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzAfterSale> mLzzAfterSales;
	private boolean mLzzAfterSalesLoaded;
	private Hashtable<String, LzzAfterSale> mLzzAfterSaleHash;


	// Singleton functions ( construction is private)
	private final static LzzAfterSaleCacheMgr singleton = new LzzAfterSaleCacheMgr ();	
	public static LzzAfterSaleCacheMgr self(){
		return singleton;
	}
	public LzzAfterSaleCacheMgr getSelf(){
		return self();
	}
	private LzzAfterSaleCacheMgr (){
		dao = new LzzDao();
		mLzzAfterSales = new Vector<LzzAfterSale>();
		mLzzAfterSalesLoaded = false;
		mLzzAfterSaleHash = new Hashtable<String,LzzAfterSale>();

	}
	synchronized private boolean loadLzzAfterSales(){

		if(!mLzzAfterSalesLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzAfterSale> array = (List<LzzAfterSale>)dao.queryAll("LzzAfterSale");
			
			for(int i = 0;i < array.size();i++){
				mLzzAfterSales.add(array.get(i));
				mLzzAfterSaleHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzAfterSalesLoaded = true;
		}
		return true;
	}



	public boolean loadAllDB(){
		loadLzzAfterSales();	

		return true;
	}

	public boolean clearCache(){
		mLzzAfterSales.clear();
		mLzzAfterSalesLoaded = false;
		mLzzAfterSaleHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzAfterSale(Object obj) {
		loadLzzAfterSales();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzAfterSale obj2 = (LzzAfterSale)obj;
		mLzzAfterSales.add(obj2);
		mLzzAfterSaleHash.put(obj2.getId(), obj2);
	}
	public void delLzzAfterSale(Object obj) {
		loadLzzAfterSales();

		LzzAfterSale obj2 = (LzzAfterSale)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzAfterSaleHash.get(obj2.getId()));

		for(LzzAfterSale msg : mLzzAfterSales){
			if(msg.getId().equals(obj2.getId())){
				mLzzAfterSales.remove(msg);
				break;
			}
		}
		mLzzAfterSaleHash.remove(obj2.getId());
		
	}
	public void updateLzzAfterSale(Object obj) {
		loadLzzAfterSales();
		
		for(int i = 0; i < mLzzAfterSales.size(); i++){
			if(mLzzAfterSales.get(i).getId().equals(((LzzAfterSale)obj).getId())){
					mLzzAfterSales.set(i, (LzzAfterSale) obj);
					break;
			}
		}
		LzzAfterSale tmp = mLzzAfterSaleHash.get(((LzzAfterSale)obj).getId());
		tmp.constructWith((LzzAfterSale)obj);
		
		//mLzzAfterSaleHash.remove(((LzzAfterSale)obj).getId());
		//mLzzAfterSaleHash.put(((LzzAfterSale)obj).getId(), (LzzAfterSale)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzAfterSale> getAllLzzAfterSale() {
		loadLzzAfterSales();
		
		List<LzzAfterSale> objects = new ArrayList<LzzAfterSale>();
		for(int i = 0;i < mLzzAfterSales.size(); i++)
		{
			objects.add(mLzzAfterSales.get(i).clone());
		}
		return objects;
	}

	public LzzAfterSale getLzzAfterSaleById(String id){
		if(null==id) return null;
		loadLzzAfterSales();
		if(null==mLzzAfterSaleHash.get(id)) return null;
		return mLzzAfterSaleHash.get(id).clone();
	}


}
