package com.lzz.order.cachemgr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import com.lzz.order.pojo.LzzGood;

import com.lzz.order.pojo.LzzGoodTaste;

import com.lzz.order.pojo.LzzGoodCategory;

import com.lzz.order.pojo.LzzGoodFormat;

import com.lzz.order.pojo.LzzGoodEvaluate;

import com.lzz.order.pojo.LzzCategory;


import com.lzz.order.factory.LzzFactory;
import com.lzz.order.dao.LzzDao;
public class LzzGoodCacheMgr{
  private Session session;
	private LzzDao dao;
	private Vector<LzzGood> mLzzGoods;
	private boolean mLzzGoodsLoaded;
	private Hashtable<String, LzzGood> mLzzGoodHash;

	private Vector<LzzGoodTaste> mLzzGoodTastes;
	private boolean mLzzGoodTastesLoaded;
	private Hashtable<String, LzzGoodTaste> mLzzGoodTasteHash;

	private Vector<LzzGoodCategory> mLzzGoodCategorys;
	private boolean mLzzGoodCategorysLoaded;
	private Hashtable<String, LzzGoodCategory> mLzzGoodCategoryHash;

	private Vector<LzzGoodFormat> mLzzGoodFormats;
	private boolean mLzzGoodFormatsLoaded;
	private Hashtable<String, LzzGoodFormat> mLzzGoodFormatHash;

	private Vector<LzzGoodEvaluate> mLzzGoodEvaluates;
	private boolean mLzzGoodEvaluatesLoaded;
	private Hashtable<String, LzzGoodEvaluate> mLzzGoodEvaluateHash;

	private Vector<LzzCategory> mLzzCategorys;
	private boolean mLzzCategorysLoaded;
	private Hashtable<String, LzzCategory> mLzzCategoryHash;


	// Singleton functions ( construction is private)
	private final static LzzGoodCacheMgr singleton = new LzzGoodCacheMgr ();	
	public static LzzGoodCacheMgr self(){
		return singleton;
	}
	public LzzGoodCacheMgr getSelf(){
		return self();
	}
	private LzzGoodCacheMgr (){
		dao = new LzzDao();
		mLzzGoods = new Vector<LzzGood>();
		mLzzGoodsLoaded = false;
		mLzzGoodHash = new Hashtable<String,LzzGood>();

		mLzzGoodTastes = new Vector<LzzGoodTaste>();
		mLzzGoodTastesLoaded = false;
		mLzzGoodTasteHash = new Hashtable<String,LzzGoodTaste>();

		mLzzGoodCategorys = new Vector<LzzGoodCategory>();
		mLzzGoodCategorysLoaded = false;
		mLzzGoodCategoryHash = new Hashtable<String,LzzGoodCategory>();

		mLzzGoodFormats = new Vector<LzzGoodFormat>();
		mLzzGoodFormatsLoaded = false;
		mLzzGoodFormatHash = new Hashtable<String,LzzGoodFormat>();

		mLzzGoodEvaluates = new Vector<LzzGoodEvaluate>();
		mLzzGoodEvaluatesLoaded = false;
		mLzzGoodEvaluateHash = new Hashtable<String,LzzGoodEvaluate>();

		mLzzCategorys = new Vector<LzzCategory>();
		mLzzCategorysLoaded = false;
		mLzzCategoryHash = new Hashtable<String,LzzCategory>();

	}
	synchronized private boolean loadLzzGoods(){

		if(!mLzzGoodsLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzGood> array = (List<LzzGood>)dao.queryAll("LzzGood");
			
			for(int i = 0;i < array.size();i++){
				mLzzGoods.add(array.get(i));
				mLzzGoodHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzGoodsLoaded = true;
		}
		return true;
	}


	synchronized private boolean loadLzzGoodTastes(){

		if(!mLzzGoodTastesLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzGoodTaste> array = (List<LzzGoodTaste>)dao.queryAll("LzzGoodTaste");
			
			for(int i = 0;i < array.size();i++){
				mLzzGoodTastes.add(array.get(i));
				mLzzGoodTasteHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzGoodTastesLoaded = true;
		}
		return true;
	}


	synchronized private boolean loadLzzGoodCategorys(){

		if(!mLzzGoodCategorysLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzGoodCategory> array = (List<LzzGoodCategory>)dao.queryAll("LzzGoodCategory");
			
			for(int i = 0;i < array.size();i++){
				mLzzGoodCategorys.add(array.get(i));
				mLzzGoodCategoryHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzGoodCategorysLoaded = true;
		}
		return true;
	}


	synchronized private boolean loadLzzGoodFormats(){

		if(!mLzzGoodFormatsLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzGoodFormat> array = (List<LzzGoodFormat>)dao.queryAll("LzzGoodFormat");
			
			for(int i = 0;i < array.size();i++){
				mLzzGoodFormats.add(array.get(i));
				mLzzGoodFormatHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzGoodFormatsLoaded = true;
		}
		return true;
	}


	synchronized private boolean loadLzzGoodEvaluates(){

		if(!mLzzGoodEvaluatesLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzGoodEvaluate> array = (List<LzzGoodEvaluate>)dao.queryAll("LzzGoodEvaluate");
			
			for(int i = 0;i < array.size();i++){
				mLzzGoodEvaluates.add(array.get(i));
				mLzzGoodEvaluateHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzGoodEvaluatesLoaded = true;
		}
		return true;
	}


	synchronized private boolean loadLzzCategorys(){

		if(!mLzzCategorysLoaded){
			session = LzzFactory.currentSession();
			dao.setSession(session);
			List<LzzCategory> array = (List<LzzCategory>)dao.queryAll("LzzCategory");
			
			for(int i = 0;i < array.size();i++){
				mLzzCategorys.add(array.get(i));
				mLzzCategoryHash.put(array.get(i).getId(), array.get(i));
			}
			mLzzCategorysLoaded = true;
		}
		return true;
	}



	public boolean loadAllDB(){
		loadLzzGoods();	

		loadLzzGoodTastes();	

		loadLzzGoodCategorys();	

		loadLzzGoodFormats();	

		loadLzzGoodEvaluates();	

		loadLzzCategorys();	

		return true;
	}

	public boolean clearCache(){
		mLzzGoods.clear();
		mLzzGoodsLoaded = false;
		mLzzGoodHash.clear();

		mLzzGoodTastes.clear();
		mLzzGoodTastesLoaded = false;
		mLzzGoodTasteHash.clear();

		mLzzGoodCategorys.clear();
		mLzzGoodCategorysLoaded = false;
		mLzzGoodCategoryHash.clear();

		mLzzGoodFormats.clear();
		mLzzGoodFormatsLoaded = false;
		mLzzGoodFormatHash.clear();

		mLzzGoodEvaluates.clear();
		mLzzGoodEvaluatesLoaded = false;
		mLzzGoodEvaluateHash.clear();

		mLzzCategorys.clear();
		mLzzCategorysLoaded = false;
		mLzzCategoryHash.clear();

		return true;
	}

	public boolean reloadCache(){
		clearCache();
		loadAllDB();
		return true;
	}
	

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzGood(Object obj) {
		loadLzzGoods();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzGood obj2 = (LzzGood)obj;
		mLzzGoods.add(obj2);
		mLzzGoodHash.put(obj2.getId(), obj2);
	}
	public void delLzzGood(Object obj) {
		loadLzzGoods();

		LzzGood obj2 = (LzzGood)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzGoodHash.get(obj2.getId()));

		for(LzzGood msg : mLzzGoods){
			if(msg.getId().equals(obj2.getId())){
				mLzzGoods.remove(msg);
				break;
			}
		}
		mLzzGoodHash.remove(obj2.getId());
		
	}
	public void updateLzzGood(Object obj) {
		loadLzzGoods();
		
		for(int i = 0; i < mLzzGoods.size(); i++){
			if(mLzzGoods.get(i).getId().equals(((LzzGood)obj).getId())){
					mLzzGoods.set(i, (LzzGood) obj);
					break;
			}
		}
		LzzGood tmp = mLzzGoodHash.get(((LzzGood)obj).getId());
		tmp.constructWith((LzzGood)obj);
		
		//mLzzGoodHash.remove(((LzzGood)obj).getId());
		//mLzzGoodHash.put(((LzzGood)obj).getId(), (LzzGood)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzGood> getAllLzzGood() {
		loadLzzGoods();
		
		List<LzzGood> objects = new ArrayList<LzzGood>();
		for(int i = 0;i < mLzzGoods.size(); i++)
		{
			objects.add(mLzzGoods.get(i).clone());
		}
		return objects;
	}

	public LzzGood getLzzGoodById(String id){
		if(null==id) return null;
		loadLzzGoods();
		if(null==mLzzGoodHash.get(id)) return null;
		return mLzzGoodHash.get(id).clone();
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzGoodTaste(Object obj) {
		loadLzzGoodTastes();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzGoodTaste obj2 = (LzzGoodTaste)obj;
		mLzzGoodTastes.add(obj2);
		mLzzGoodTasteHash.put(obj2.getId(), obj2);
	}
	public void delLzzGoodTaste(Object obj) {
		loadLzzGoodTastes();

		LzzGoodTaste obj2 = (LzzGoodTaste)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzGoodTasteHash.get(obj2.getId()));

		for(LzzGoodTaste msg : mLzzGoodTastes){
			if(msg.getId().equals(obj2.getId())){
				mLzzGoodTastes.remove(msg);
				break;
			}
		}
		mLzzGoodTasteHash.remove(obj2.getId());
		
	}
	public void updateLzzGoodTaste(Object obj) {
		loadLzzGoodTastes();
		
		for(int i = 0; i < mLzzGoodTastes.size(); i++){
			if(mLzzGoodTastes.get(i).getId().equals(((LzzGoodTaste)obj).getId())){
					mLzzGoodTastes.set(i, (LzzGoodTaste) obj);
					break;
			}
		}
		LzzGoodTaste tmp = mLzzGoodTasteHash.get(((LzzGoodTaste)obj).getId());
		tmp.constructWith((LzzGoodTaste)obj);
		
		//mLzzGoodTasteHash.remove(((LzzGoodTaste)obj).getId());
		//mLzzGoodTasteHash.put(((LzzGoodTaste)obj).getId(), (LzzGoodTaste)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzGoodTaste> getAllLzzGoodTaste() {
		loadLzzGoodTastes();
		
		List<LzzGoodTaste> objects = new ArrayList<LzzGoodTaste>();
		for(int i = 0;i < mLzzGoodTastes.size(); i++)
		{
			objects.add(mLzzGoodTastes.get(i).clone());
		}
		return objects;
	}

	public LzzGoodTaste getLzzGoodTasteById(String id){
		if(null==id) return null;
		loadLzzGoodTastes();
		if(null==mLzzGoodTasteHash.get(id)) return null;
		return mLzzGoodTasteHash.get(id).clone();
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzGoodCategory(Object obj) {
		loadLzzGoodCategorys();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzGoodCategory obj2 = (LzzGoodCategory)obj;
		mLzzGoodCategorys.add(obj2);
		mLzzGoodCategoryHash.put(obj2.getId(), obj2);
	}
	public void delLzzGoodCategory(Object obj) {
		loadLzzGoodCategorys();

		LzzGoodCategory obj2 = (LzzGoodCategory)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzGoodCategoryHash.get(obj2.getId()));

		for(LzzGoodCategory msg : mLzzGoodCategorys){
			if(msg.getId().equals(obj2.getId())){
				mLzzGoodCategorys.remove(msg);
				break;
			}
		}
		mLzzGoodCategoryHash.remove(obj2.getId());
		
	}
	public void updateLzzGoodCategory(Object obj) {
		loadLzzGoodCategorys();
		
		for(int i = 0; i < mLzzGoodCategorys.size(); i++){
			if(mLzzGoodCategorys.get(i).getId().equals(((LzzGoodCategory)obj).getId())){
					mLzzGoodCategorys.set(i, (LzzGoodCategory) obj);
					break;
			}
		}
		LzzGoodCategory tmp = mLzzGoodCategoryHash.get(((LzzGoodCategory)obj).getId());
		tmp.constructWith((LzzGoodCategory)obj);
		
		//mLzzGoodCategoryHash.remove(((LzzGoodCategory)obj).getId());
		//mLzzGoodCategoryHash.put(((LzzGoodCategory)obj).getId(), (LzzGoodCategory)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzGoodCategory> getAllLzzGoodCategory() {
		loadLzzGoodCategorys();
		
		List<LzzGoodCategory> objects = new ArrayList<LzzGoodCategory>();
		for(int i = 0;i < mLzzGoodCategorys.size(); i++)
		{
			objects.add(mLzzGoodCategorys.get(i).clone());
		}
		return objects;
	}

	public LzzGoodCategory getLzzGoodCategoryById(String id){
		if(null==id) return null;
		loadLzzGoodCategorys();
		if(null==mLzzGoodCategoryHash.get(id)) return null;
		return mLzzGoodCategoryHash.get(id).clone();
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzGoodFormat(Object obj) {
		loadLzzGoodFormats();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzGoodFormat obj2 = (LzzGoodFormat)obj;
		mLzzGoodFormats.add(obj2);
		mLzzGoodFormatHash.put(obj2.getId(), obj2);
	}
	public void delLzzGoodFormat(Object obj) {
		loadLzzGoodFormats();

		LzzGoodFormat obj2 = (LzzGoodFormat)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzGoodFormatHash.get(obj2.getId()));

		for(LzzGoodFormat msg : mLzzGoodFormats){
			if(msg.getId().equals(obj2.getId())){
				mLzzGoodFormats.remove(msg);
				break;
			}
		}
		mLzzGoodFormatHash.remove(obj2.getId());
		
	}
	public void updateLzzGoodFormat(Object obj) {
		loadLzzGoodFormats();
		
		for(int i = 0; i < mLzzGoodFormats.size(); i++){
			if(mLzzGoodFormats.get(i).getId().equals(((LzzGoodFormat)obj).getId())){
					mLzzGoodFormats.set(i, (LzzGoodFormat) obj);
					break;
			}
		}
		LzzGoodFormat tmp = mLzzGoodFormatHash.get(((LzzGoodFormat)obj).getId());
		tmp.constructWith((LzzGoodFormat)obj);
		
		//mLzzGoodFormatHash.remove(((LzzGoodFormat)obj).getId());
		//mLzzGoodFormatHash.put(((LzzGoodFormat)obj).getId(), (LzzGoodFormat)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzGoodFormat> getAllLzzGoodFormat() {
		loadLzzGoodFormats();
		
		List<LzzGoodFormat> objects = new ArrayList<LzzGoodFormat>();
		for(int i = 0;i < mLzzGoodFormats.size(); i++)
		{
			objects.add(mLzzGoodFormats.get(i).clone());
		}
		return objects;
	}

	public LzzGoodFormat getLzzGoodFormatById(String id){
		if(null==id) return null;
		loadLzzGoodFormats();
		if(null==mLzzGoodFormatHash.get(id)) return null;
		return mLzzGoodFormatHash.get(id).clone();
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzGoodEvaluate(Object obj) {
		loadLzzGoodEvaluates();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzGoodEvaluate obj2 = (LzzGoodEvaluate)obj;
		mLzzGoodEvaluates.add(obj2);
		mLzzGoodEvaluateHash.put(obj2.getId(), obj2);
	}
	public void delLzzGoodEvaluate(Object obj) {
		loadLzzGoodEvaluates();

		LzzGoodEvaluate obj2 = (LzzGoodEvaluate)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzGoodEvaluateHash.get(obj2.getId()));

		for(LzzGoodEvaluate msg : mLzzGoodEvaluates){
			if(msg.getId().equals(obj2.getId())){
				mLzzGoodEvaluates.remove(msg);
				break;
			}
		}
		mLzzGoodEvaluateHash.remove(obj2.getId());
		
	}
	public void updateLzzGoodEvaluate(Object obj) {
		loadLzzGoodEvaluates();
		
		for(int i = 0; i < mLzzGoodEvaluates.size(); i++){
			if(mLzzGoodEvaluates.get(i).getId().equals(((LzzGoodEvaluate)obj).getId())){
					mLzzGoodEvaluates.set(i, (LzzGoodEvaluate) obj);
					break;
			}
		}
		LzzGoodEvaluate tmp = mLzzGoodEvaluateHash.get(((LzzGoodEvaluate)obj).getId());
		tmp.constructWith((LzzGoodEvaluate)obj);
		
		//mLzzGoodEvaluateHash.remove(((LzzGoodEvaluate)obj).getId());
		//mLzzGoodEvaluateHash.put(((LzzGoodEvaluate)obj).getId(), (LzzGoodEvaluate)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzGoodEvaluate> getAllLzzGoodEvaluate() {
		loadLzzGoodEvaluates();
		
		List<LzzGoodEvaluate> objects = new ArrayList<LzzGoodEvaluate>();
		for(int i = 0;i < mLzzGoodEvaluates.size(); i++)
		{
			objects.add(mLzzGoodEvaluates.get(i).clone());
		}
		return objects;
	}

	public LzzGoodEvaluate getLzzGoodEvaluateById(String id){
		if(null==id) return null;
		loadLzzGoodEvaluates();
		if(null==mLzzGoodEvaluateHash.get(id)) return null;
		return mLzzGoodEvaluateHash.get(id).clone();
	}

	// save new object(if you want to change an exist object, please call other functions.)
	public void saveLzzCategory(Object obj) {
		loadLzzCategorys();
		session = LzzFactory.currentSession();
		dao.setSession(session);
		dao.save(obj);
		LzzCategory obj2 = (LzzCategory)obj;
		mLzzCategorys.add(obj2);
		mLzzCategoryHash.put(obj2.getId(), obj2);
	}
	public void delLzzCategory(Object obj) {
		loadLzzCategorys();

		LzzCategory obj2 = (LzzCategory)obj;
		
		session = LzzFactory.currentSession();
		dao.setSession(session);
		
		dao.delete(mLzzCategoryHash.get(obj2.getId()));

		for(LzzCategory msg : mLzzCategorys){
			if(msg.getId().equals(obj2.getId())){
				mLzzCategorys.remove(msg);
				break;
			}
		}
		mLzzCategoryHash.remove(obj2.getId());
		
	}
	public void updateLzzCategory(Object obj) {
		loadLzzCategorys();
		
		for(int i = 0; i < mLzzCategorys.size(); i++){
			if(mLzzCategorys.get(i).getId().equals(((LzzCategory)obj).getId())){
					mLzzCategorys.set(i, (LzzCategory) obj);
					break;
			}
		}
		LzzCategory tmp = mLzzCategoryHash.get(((LzzCategory)obj).getId());
		tmp.constructWith((LzzCategory)obj);
		
		//mLzzCategoryHash.remove(((LzzCategory)obj).getId());
		//mLzzCategoryHash.put(((LzzCategory)obj).getId(), (LzzCategory)obj);
		session = LzzFactory.currentSession();
		dao.setSession(session);
		//dao.update(obj);
		dao.update(tmp);
	}

	public List<LzzCategory> getAllLzzCategory() {
		loadLzzCategorys();
		
		List<LzzCategory> objects = new ArrayList<LzzCategory>();
		for(int i = 0;i < mLzzCategorys.size(); i++)
		{
			objects.add(mLzzCategorys.get(i).clone());
		}
		return objects;
	}

	public LzzCategory getLzzCategoryById(String id){
		if(null==id) return null;
		loadLzzCategorys();
		if(null==mLzzCategoryHash.get(id)) return null;
		return mLzzCategoryHash.get(id).clone();
	}


}
