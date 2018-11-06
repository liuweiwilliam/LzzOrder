package com.lzz.order.utils;

import java.lang.reflect.Method;
import java.util.List;

public class LzzListSort {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List get(List list) throws Exception{
		if(list!=null && list.size()>0){
			String getmethodname = "getChangetime";
			Class c = list.get(0).getClass();
			Method m = c.getDeclaredMethod(getmethodname);
			for(int i = 0;i<list.size();i++){
				for(int j = i+1;j<list.size();j++){
					Object obji = list.get(i);
					Object objj = list.get(j);
					String si = m.invoke(obji).toString();
					String sj = m.invoke(objj).toString();
					if(si.compareTo(sj)<0){
						list.remove(i);
						list.add(i, objj);
						
						list.remove(j);
						list.add(j, obji);
					}
				}
			}
			
		}
		return list;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getSortByMethodname(List list, String getmethodname) throws Exception{
		if(list!=null && list.size()>0){
			Class c = list.get(0).getClass();
			Method m = c.getDeclaredMethod(getmethodname);
			for(int i = 0;i<list.size();i++){
				for(int j = i+1;j<list.size();j++){
					Object obji = list.get(i);
					Object objj = list.get(j);
					String si = m.invoke(obji).toString();
					String sj = m.invoke(objj).toString();
					if(si.compareTo(sj)<0){
						list.remove(i);
						list.add(i, objj);
						
						list.remove(j);
						list.add(j, obji);
					}
				}
			}
			
		}
		return list;
	}
	
	
}
