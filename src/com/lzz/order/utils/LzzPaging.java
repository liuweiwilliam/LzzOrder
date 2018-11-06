package com.lzz.order.utils;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
/**
 *处理分页
 */
public class LzzPaging {
	/**
	 *获取分页的结果
	 *list：需要进行分页的数据
	 *pageNo：被获取的页码
	 *pageSize：页码长度
	 */
	public static List get(List list,int pageNo,int pageSize){
		
		if(list!=null){
			List rsl = new ArrayList();
			int start = (pageNo-1)*pageSize;
			int end  = pageNo*pageSize-1;
			for(int i=start;i<=end;i++){
				if(i>=0&&i<list.size()){
					rsl.add(list.get(i));
				}
			}
			return rsl;
		}
		return null;
	}
	
	/**
	 *获取分页的结果
	 *list：需要进行分页的数据
	 *pageNo：被获取的页码
	 *pageSize：页码长度
	 */
	public static JSONArray get(JSONArray list,int pageNo,int pageSize){
		
		if(list!=null){
			JSONArray rsl = new JSONArray();
			int start = (pageNo-1)*pageSize;
			int end  = pageNo*pageSize-1;
			for(int i=start;i<=end;i++){
				if(i>=0&&i<list.size()){
					rsl.add(list.get(i));
				}
			}
			return rsl;
		}
		return null;
	}
	
	
	/**
	 *获取数据的页数
	 *list:需要分页的数据
	 *pageSize：页码长度
	 */
	public static int getPageNum(List list,int pageSize){
		if(list==null || list.size()==0){
			return 1;
		}else{
			int size = list.size();
			double num = size*1.0/pageSize;
			int zhengshu = (int) num;
			if(num-zhengshu>0){
				zhengshu++;
			}
			return zhengshu;
		}
	}
}
