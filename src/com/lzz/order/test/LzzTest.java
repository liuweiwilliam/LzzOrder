package com.lzz.order.test;

import com.lzz.order.mgr.LzzIDMgr;

public class LzzTest {
	public static void main(String[] args) {
		String id = LzzIDMgr.self().getID();
		System.out.println(id);
		System.out.println(LzzIDMgr.self().getID());
		System.out.println(LzzIDMgr.self().getID());
		System.out.println(LzzIDMgr.self().getID());
		System.out.println(LzzIDMgr.self().getID());
		System.out.println(LzzIDMgr.self().getID());
		System.out.println(LzzIDMgr.self().getID());
		System.out.println(LzzIDMgr.self().getID());
		System.out.println(LzzIDMgr.self().getID());
	}
}
