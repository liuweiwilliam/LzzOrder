package com.lzz.order.action.mgr;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Transaction;

import com.lzz.order.action.LzzBaseManagerAction;
import com.lzz.order.factory.LzzFactory;
import com.lzz.order.mgr.LzzCacheMgr;
import com.lzz.order.mgr.LzzCartMgr;

public class LzzAddNewGood extends LzzBaseManagerAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 834465877553779059L;

	public String addNewGood(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("utf-8");

		JSONObject rsl = new JSONObject();
		Transaction ts = LzzFactory.currentSession().beginTransaction();

		try {
			//商品基本信息
			//商品分类信息
			
			
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ts.rollback();
			LzzCacheMgr.reloadAllCache();
			rsl.put("iserror", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormgr", sw.toString());
		} finally {
			LzzFactory.closeSession();
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
}
