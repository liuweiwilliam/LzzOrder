package com.lzz.order.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.order.pojo.LzzOrder;

import com.lzz.order.pojo.LzzOrderInfo;

import com.lzz.order.pojo.LzzOrderSendMode;


import com.lzz.order.factory.LzzFactory;
import com.lzz.order.dao.LzzDao;
public class LzzOrderCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzOrder> mLzzOrders;
	private boolean mLzzOrdersLoaded;
	private Hashtable<String, LzzOrder> mLzzOrderHash;

	private Vector<LzzOrderInfo> mLzzOrderInfos;
	private boolean mLzzOrderInfosLoaded;
	private Hashtable<String, LzzOrderInfo> mLzzOrderInfoHash;

	private Vector<LzzOrderSendMode> mLzzOrderSendModes;
	private boolean mLzzOrderSendModesLoaded;
	private Hashtable<String, LzzOrderSendMode> mLzzOrderSendModeHash;


	// Singleton functions ( construction is private)
	private final static LzzOrderCacheMgr singleton = new LzzOrderCacheMgr ();	
	public static LzzOrderCacheMgr self(){
		return singleton;
	}
	public LzzOrderCacheMgr getSelf(){
		return self();
	}
	private LzzOrderCacheMgr (){
		dao = new LzzDao();
		mLzzOrders = new Vector<LzzOrder>();
		mLzzOrdersLoaded = false;
		mLzzOrderHash = new Hashtable<String,LzzOrder>();

		mLzzOrderInfos = new Vector<LzzOrderInfo>();
		mLzzOrderInfosLoaded = false;
		mLzzOrderInfoHash = new Hashtable<String,LzzOrderInfo>();

		mLzzOrderSendModes = new Vector<LzzOrderSendMode>();
		mLzzOrderSendModesLoaded = false;
		mLzzOrderSendModeHash = new Hashtable<String,LzzOrderSendMode>();

	}
	synchronized private boolean loadLzzOrders(){

		if(!mLzzOrdersLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzOrder> array = (List<LzzOrder>)dao.queryAll("LzzOrder");
			
			for(int i = 0;i < array.size();i++){
				mLzzOrders.add(array.get(i));
				mLzzOrderHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzOrdersLoaded = true;
		}
		return true;
	}


	synchronized private boolean loadLzzOrderInfos(){

		if(!mLzzOrderInfosLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzOrderInfo> array = (List<LzzOrderInfo>)dao.queryAll("LzzOrderInfo");
			
			for(int i = 0;i < array.size();i++){
				mLzzOrderInfos.add(array.get(i));
				mLzzOrderInfoHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzOrderInfosLoaded = true;
		}
		return true;
	}


	synchronized private boolean loadLzzOrderSendModes(){

		if(!mLzzOrderSendModesLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzOrderSendMode> array = (List<LzzOrderSendMode>)dao.queryAll("LzzOrderSendMode");
			
			for(int i = 0;i < array.size();i++){
				mLzzOrderSendModes.add(array.get(i));
				mLzzOrderSendModeHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzOrderSendModesLoaded = true;
		}
		return true;
	}



	public boolean loadAllDB(){
		loadLzzOrders();	

		loadLzzOrderInfos();	

		loadLzzOrderSendModes();	

		return true;
	}

	public boolean clearCache(){
		mLzzOrders.clear();
		mLzzOrdersLoaded = false;
		mLzzOrderHash.clear();

		mLzzOrderInfos.clear();
		mLzzOrderInfosLoaded = false;
		mLzzOrderInfoHash.clear();

		mLzzOrderSendModes.clear();
		mLzzOrderSendModesLoaded = false;
		mLzzOrderSendModeHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzOrder(Object obj) {
		loadLzzOrders();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzOrder obj2 = (LzzOrder)obj;
		mLzzOrders.add(obj2);
		mLzzOrderHash.put(obj2.getId(), obj2);
	}
	public void delLzzOrder(Object obj) {
		loadLzzOrders();

		LzzOrder obj2 = (LzzOrder)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzOrderHash.get(obj2.getId()));

		for(LzzOrder msg : mLzzOrders){
			if(msg.getId().equals(obj2.getId())){
				mLzzOrders.remove(msg);
				break;
			}
		}
		mLzzOrderHash.remove(obj2.getId());
		
	}
	public void updateLzzOrder(Object obj) {
		loadLzzOrders();
		
		for(int i = 0; i < mLzzOrders.size(); i++){
			if(mLzzOrders.get(i).getId().equals(((LzzOrder)obj).getId())){
					mLzzOrders.set(i, (LzzOrder) obj);
					break;
			}
		}
		LzzOrder tmp = mLzzOrderHash.get(((LzzOrder)obj).getId());
		tmp.constructWith((LzzOrder)obj);
		
		//mLzzOrderHash.remove(((LzzOrder)obj).getId());
		//mLzzOrderHash.put(((LzzOrder)obj).getId(), (LzzOrder)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzOrder> getAllLzzOrder() {
		loadLzzOrders();
		
		List<LzzOrder> objects = new ArrayList<LzzOrder>();
		for(int i = 0;i < mLzzOrders.size(); i++)
		{
			objects.add(mLzzOrders.get(i).clone());
		}
		return objects;
	}

	public LzzOrder getLzzOrderById(String id){
		if(null==id) return null;
		loadLzzOrders();
		if(null==mLzzOrderHash.get(id)) return null;
		return mLzzOrderHash.get(id).clone();
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzOrderInfo(Object obj) {
		loadLzzOrderInfos();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzOrderInfo obj2 = (LzzOrderInfo)obj;
		mLzzOrderInfos.add(obj2);
		mLzzOrderInfoHash.put(obj2.getId(), obj2);
	}
	public void delLzzOrderInfo(Object obj) {
		loadLzzOrderInfos();

		LzzOrderInfo obj2 = (LzzOrderInfo)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzOrderInfoHash.get(obj2.getId()));

		for(LzzOrderInfo msg : mLzzOrderInfos){
			if(msg.getId().equals(obj2.getId())){
				mLzzOrderInfos.remove(msg);
				break;
			}
		}
		mLzzOrderInfoHash.remove(obj2.getId());
		
	}
	public void updateLzzOrderInfo(Object obj) {
		loadLzzOrderInfos();
		
		for(int i = 0; i < mLzzOrderInfos.size(); i++){
			if(mLzzOrderInfos.get(i).getId().equals(((LzzOrderInfo)obj).getId())){
					mLzzOrderInfos.set(i, (LzzOrderInfo) obj);
					break;
			}
		}
		LzzOrderInfo tmp = mLzzOrderInfoHash.get(((LzzOrderInfo)obj).getId());
		tmp.constructWith((LzzOrderInfo)obj);
		
		//mLzzOrderInfoHash.remove(((LzzOrderInfo)obj).getId());
		//mLzzOrderInfoHash.put(((LzzOrderInfo)obj).getId(), (LzzOrderInfo)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzOrderInfo> getAllLzzOrderInfo() {
		loadLzzOrderInfos();
		
		List<LzzOrderInfo> objects = new ArrayList<LzzOrderInfo>();
		for(int i = 0;i < mLzzOrderInfos.size(); i++)
		{
			objects.add(mLzzOrderInfos.get(i).clone());
		}
		return objects;
	}

	public LzzOrderInfo getLzzOrderInfoById(String id){
		if(null==id) return null;
		loadLzzOrderInfos();
		if(null==mLzzOrderInfoHash.get(id)) return null;
		return mLzzOrderInfoHash.get(id).clone();
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzOrderSendMode(Object obj) {
		loadLzzOrderSendModes();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzOrderSendMode obj2 = (LzzOrderSendMode)obj;
		mLzzOrderSendModes.add(obj2);
		mLzzOrderSendModeHash.put(obj2.getId(), obj2);
	}
	public void delLzzOrderSendMode(Object obj) {
		loadLzzOrderSendModes();

		LzzOrderSendMode obj2 = (LzzOrderSendMode)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzOrderSendModeHash.get(obj2.getId()));

		for(LzzOrderSendMode msg : mLzzOrderSendModes){
			if(msg.getId().equals(obj2.getId())){
				mLzzOrderSendModes.remove(msg);
				break;
			}
		}
		mLzzOrderSendModeHash.remove(obj2.getId());
		
	}
	public void updateLzzOrderSendMode(Object obj) {
		loadLzzOrderSendModes();
		
		for(int i = 0; i < mLzzOrderSendModes.size(); i++){
			if(mLzzOrderSendModes.get(i).getId().equals(((LzzOrderSendMode)obj).getId())){
					mLzzOrderSendModes.set(i, (LzzOrderSendMode) obj);
					break;
			}
		}
		LzzOrderSendMode tmp = mLzzOrderSendModeHash.get(((LzzOrderSendMode)obj).getId());
		tmp.constructWith((LzzOrderSendMode)obj);
		
		//mLzzOrderSendModeHash.remove(((LzzOrderSendMode)obj).getId());
		//mLzzOrderSendModeHash.put(((LzzOrderSendMode)obj).getId(), (LzzOrderSendMode)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzOrderSendMode> getAllLzzOrderSendMode() {
		loadLzzOrderSendModes();
		
		List<LzzOrderSendMode> objects = new ArrayList<LzzOrderSendMode>();
		for(int i = 0;i < mLzzOrderSendModes.size(); i++)
		{
			objects.add(mLzzOrderSendModes.get(i).clone());
		}
		return objects;
	}

	public LzzOrderSendMode getLzzOrderSendModeById(String id){
		if(null==id) return null;
		loadLzzOrderSendModes();
		if(null==mLzzOrderSendModeHash.get(id)) return null;
		return mLzzOrderSendModeHash.get(id).clone();
	}


}
