package com.lzz.order.mgr;

import com.lzz.order.cachemgr.LzzAfterSaleCacheMgr;
import com.lzz.order.cachemgr.LzzCartCacheMgr;
import com.lzz.order.cachemgr.LzzCouponCacheMgr;
import com.lzz.order.cachemgr.LzzDictionaryCacheMgr;
import com.lzz.order.cachemgr.LzzGoodCacheMgr;
import com.lzz.order.cachemgr.LzzIDCacheMgr;
import com.lzz.order.cachemgr.LzzOrderCacheMgr;
import com.lzz.order.cachemgr.LzzUserCacheMgr;

public class LzzCacheMgr {
	public static void reloadAllCache(){
		LzzAfterSaleCacheMgr.self().reloadCache();
		LzzCartCacheMgr.self().reloadCache();
		LzzCouponCacheMgr.self().reloadCache();
		LzzDictionaryCacheMgr.self().reloadCache();
		LzzGoodCacheMgr.self().reloadCache();
		LzzIDCacheMgr.self().reloadCache();
		LzzOrderCacheMgr.self().reloadCache();
		LzzUserCacheMgr.self().reloadCache();
	}
}
