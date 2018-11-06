package com.lzz.order.localpojo;

import java.util.Date;

public class WeChatVerification {
	public String code;
	public int times;
	public Date valid_date;
	public Date create_date;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public Date getValid_date() {
		return valid_date;
	}
	public void setValid_date(Date valid_date) {
		this.valid_date = valid_date;
	}
	
	public boolean isValid(){
		return valid_date.after(new Date());
	}
}
