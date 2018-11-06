package com.lzz.order.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.time.DateUtils;

public class XtlBaseTaskManager implements ServletContextListener {
	 /**  
	  * 每天的毫秒数  
	  */  
	public static final long PERIOD_DAY = DateUtils.MILLIS_PER_DAY;
	/**  
	  * 一周内的毫秒数  
	  */  
	public static final long PERIOD_WEEK = PERIOD_DAY * 7;  
	 
	 /**  
	  * 无延迟  
	  */  
	public static final long NO_DELAY = 0;  
	
	private Timer timer;  

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		if(null!=timer){
			timer.cancel();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
//		timer.schedule(new XtlLoginMainServer(), NO_DELAY, 1000*60*4);
	}
}
