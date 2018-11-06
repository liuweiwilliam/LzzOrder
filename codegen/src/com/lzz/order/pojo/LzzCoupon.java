
package com.lzz.order.pojo;

/*
id     id

price     优惠卷金额

limitPrice     优惠卷使用金额条件

totalCount     优惠卷总数量

curCount     当前剩余数量

validSDate     有效开始日期

validEDate     有效结束日期

createTime     创建时间

modifyTime     修改时间

def1     备用字段1-10

def2      

def3      

def4      

def5      

def6      

def7      

def8      

def9      

def10      

dr     删除标志

*/
public class LzzCoupon {

	private String id = "";

	private String price = "";

	private String limitPrice = "";

	private String totalCount = "";

	private String curCount = "";

	private String validSDate = "";

	private String validEDate = "";

	private String createTime = "";

	private String modifyTime = "";

	private String def1 = "";

	private String def2 = "";

	private String def3 = "";

	private String def4 = "";

	private String def5 = "";

	private String def6 = "";

	private String def7 = "";

	private String def8 = "";

	private String def9 = "";

	private String def10 = "";

	private String dr = "0";

	
	public LzzCoupon(){};
	
	public LzzCoupon(LzzCoupon obj){
	    id = obj.getId();

	    price = obj.getPrice();

	    limitPrice = obj.getLimitPrice();

	    totalCount = obj.getTotalCount();

	    curCount = obj.getCurCount();

	    validSDate = obj.getValidSDate();

	    validEDate = obj.getValidEDate();

	    createTime = obj.getCreateTime();

	    modifyTime = obj.getModifyTime();

	    def1 = obj.getDef1();

	    def2 = obj.getDef2();

	    def3 = obj.getDef3();

	    def4 = obj.getDef4();

	    def5 = obj.getDef5();

	    def6 = obj.getDef6();

	    def7 = obj.getDef7();

	    def8 = obj.getDef8();

	    def9 = obj.getDef9();

	    def10 = obj.getDef10();

	    dr = obj.getDr();

	}

	public String getId() {
	    return id;
	}
	public void setId(String id) {
	    this.id = id;
	}

	public String getPrice() {
	    return price;
	}
	public void setPrice(String price) {
	    this.price = price;
	}

	public String getLimitPrice() {
	    return limitPrice;
	}
	public void setLimitPrice(String limitPrice) {
	    this.limitPrice = limitPrice;
	}

	public String getTotalCount() {
	    return totalCount;
	}
	public void setTotalCount(String totalCount) {
	    this.totalCount = totalCount;
	}

	public String getCurCount() {
	    return curCount;
	}
	public void setCurCount(String curCount) {
	    this.curCount = curCount;
	}

	public String getValidSDate() {
	    return validSDate;
	}
	public void setValidSDate(String validSDate) {
	    this.validSDate = validSDate;
	}

	public String getValidEDate() {
	    return validEDate;
	}
	public void setValidEDate(String validEDate) {
	    this.validEDate = validEDate;
	}

	public String getCreateTime() {
	    return createTime;
	}
	public void setCreateTime(String createTime) {
	    this.createTime = createTime;
	}

	public String getModifyTime() {
	    return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
	    this.modifyTime = modifyTime;
	}

	public String getDef1() {
	    return def1;
	}
	public void setDef1(String def1) {
	    this.def1 = def1;
	}

	public String getDef2() {
	    return def2;
	}
	public void setDef2(String def2) {
	    this.def2 = def2;
	}

	public String getDef3() {
	    return def3;
	}
	public void setDef3(String def3) {
	    this.def3 = def3;
	}

	public String getDef4() {
	    return def4;
	}
	public void setDef4(String def4) {
	    this.def4 = def4;
	}

	public String getDef5() {
	    return def5;
	}
	public void setDef5(String def5) {
	    this.def5 = def5;
	}

	public String getDef6() {
	    return def6;
	}
	public void setDef6(String def6) {
	    this.def6 = def6;
	}

	public String getDef7() {
	    return def7;
	}
	public void setDef7(String def7) {
	    this.def7 = def7;
	}

	public String getDef8() {
	    return def8;
	}
	public void setDef8(String def8) {
	    this.def8 = def8;
	}

	public String getDef9() {
	    return def9;
	}
	public void setDef9(String def9) {
	    this.def9 = def9;
	}

	public String getDef10() {
	    return def10;
	}
	public void setDef10(String def10) {
	    this.def10 = def10;
	}

	public String getDr() {
	    return dr;
	}
	public void setDr(String dr) {
	    this.dr = dr;
	}


	public LzzCoupon clone(){
		LzzCoupon rslt = new LzzCoupon();
		rslt.id = this.getId();

		rslt.price = this.getPrice();

		rslt.limitPrice = this.getLimitPrice();

		rslt.totalCount = this.getTotalCount();

		rslt.curCount = this.getCurCount();

		rslt.validSDate = this.getValidSDate();

		rslt.validEDate = this.getValidEDate();

		rslt.createTime = this.getCreateTime();

		rslt.modifyTime = this.getModifyTime();

		rslt.def1 = this.getDef1();

		rslt.def2 = this.getDef2();

		rslt.def3 = this.getDef3();

		rslt.def4 = this.getDef4();

		rslt.def5 = this.getDef5();

		rslt.def6 = this.getDef6();

		rslt.def7 = this.getDef7();

		rslt.def8 = this.getDef8();

		rslt.def9 = this.getDef9();

		rslt.def10 = this.getDef10();

		rslt.dr = this.getDr();

		return rslt;
	}
	public void constructWith(LzzCoupon pro) {
		// TODO Auto-generated method stub
		this.id  = pro.getId ();

		this.price  = pro.getPrice ();

		this.limitPrice  = pro.getLimitPrice ();

		this.totalCount  = pro.getTotalCount ();

		this.curCount  = pro.getCurCount ();

		this.validSDate  = pro.getValidSDate ();

		this.validEDate  = pro.getValidEDate ();

		this.createTime  = pro.getCreateTime ();

		this.modifyTime  = pro.getModifyTime ();

		this.def1  = pro.getDef1 ();

		this.def2  = pro.getDef2 ();

		this.def3  = pro.getDef3 ();

		this.def4  = pro.getDef4 ();

		this.def5  = pro.getDef5 ();

		this.def6  = pro.getDef6 ();

		this.def7  = pro.getDef7 ();

		this.def8  = pro.getDef8 ();

		this.def9  = pro.getDef9 ();

		this.def10  = pro.getDef10 ();

		this.dr  = pro.getDr ();

	}
}
