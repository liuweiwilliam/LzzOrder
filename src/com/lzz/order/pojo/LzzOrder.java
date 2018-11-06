
package com.lzz.order.pojo;

/*
id     id

userId     用户ID

orderNum     订单号

payTime     付款时间

address     地址信息

addressMod     修改的地址信息JSON格式

expressCompany     快递公司ID

expressNum     运单号

expressFee     运费

status     订单状态

evaluateTime     评价时间

finishTime     订单完成时间

couponFlag     是否使用优惠卷

couponId     优惠卷ID

couponPrice     优惠卷金额

fromWhere     说明是来自购物车还是直接从商品详情提交

comment     订单备注

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
public class LzzOrder {

	private String id = "";

	private String userId = "";

	private String orderNum = "";

	private String payTime = "";

	private String address = "";

	private String addressMod = "";

	private String expressCompany = "";

	private String expressNum = "";

	private String expressFee = "";

	private String status = "";

	private String evaluateTime = "";

	private String finishTime = "";

	private String couponFlag = "";

	private String couponId = "";

	private String couponPrice = "";

	private String fromWhere = "";

	private String comment = "";

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

	
	public LzzOrder(){};
	
	public LzzOrder(LzzOrder obj){
	    id = obj.getId();

	    userId = obj.getUserId();

	    orderNum = obj.getOrderNum();

	    payTime = obj.getPayTime();

	    address = obj.getAddress();

	    addressMod = obj.getAddressMod();

	    expressCompany = obj.getExpressCompany();

	    expressNum = obj.getExpressNum();

	    expressFee = obj.getExpressFee();

	    status = obj.getStatus();

	    evaluateTime = obj.getEvaluateTime();

	    finishTime = obj.getFinishTime();

	    couponFlag = obj.getCouponFlag();

	    couponId = obj.getCouponId();

	    couponPrice = obj.getCouponPrice();

	    fromWhere = obj.getFromWhere();

	    comment = obj.getComment();

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

	public String getUserId() {
	    return userId;
	}
	public void setUserId(String userId) {
	    this.userId = userId;
	}

	public String getOrderNum() {
	    return orderNum;
	}
	public void setOrderNum(String orderNum) {
	    this.orderNum = orderNum;
	}

	public String getPayTime() {
	    return payTime;
	}
	public void setPayTime(String payTime) {
	    this.payTime = payTime;
	}

	public String getAddress() {
	    return address;
	}
	public void setAddress(String address) {
	    this.address = address;
	}

	public String getAddressMod() {
	    return addressMod;
	}
	public void setAddressMod(String addressMod) {
	    this.addressMod = addressMod;
	}

	public String getExpressCompany() {
	    return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
	    this.expressCompany = expressCompany;
	}

	public String getExpressNum() {
	    return expressNum;
	}
	public void setExpressNum(String expressNum) {
	    this.expressNum = expressNum;
	}

	public String getExpressFee() {
	    return expressFee;
	}
	public void setExpressFee(String expressFee) {
	    this.expressFee = expressFee;
	}

	public String getStatus() {
	    return status;
	}
	public void setStatus(String status) {
	    this.status = status;
	}

	public String getEvaluateTime() {
	    return evaluateTime;
	}
	public void setEvaluateTime(String evaluateTime) {
	    this.evaluateTime = evaluateTime;
	}

	public String getFinishTime() {
	    return finishTime;
	}
	public void setFinishTime(String finishTime) {
	    this.finishTime = finishTime;
	}

	public String getCouponFlag() {
	    return couponFlag;
	}
	public void setCouponFlag(String couponFlag) {
	    this.couponFlag = couponFlag;
	}

	public String getCouponId() {
	    return couponId;
	}
	public void setCouponId(String couponId) {
	    this.couponId = couponId;
	}

	public String getCouponPrice() {
	    return couponPrice;
	}
	public void setCouponPrice(String couponPrice) {
	    this.couponPrice = couponPrice;
	}

	public String getFromWhere() {
	    return fromWhere;
	}
	public void setFromWhere(String fromWhere) {
	    this.fromWhere = fromWhere;
	}

	public String getComment() {
	    return comment;
	}
	public void setComment(String comment) {
	    this.comment = comment;
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


	public LzzOrder clone(){
		LzzOrder rslt = new LzzOrder();
		rslt.id = this.getId();

		rslt.userId = this.getUserId();

		rslt.orderNum = this.getOrderNum();

		rslt.payTime = this.getPayTime();

		rslt.address = this.getAddress();

		rslt.addressMod = this.getAddressMod();

		rslt.expressCompany = this.getExpressCompany();

		rslt.expressNum = this.getExpressNum();

		rslt.expressFee = this.getExpressFee();

		rslt.status = this.getStatus();

		rslt.evaluateTime = this.getEvaluateTime();

		rslt.finishTime = this.getFinishTime();

		rslt.couponFlag = this.getCouponFlag();

		rslt.couponId = this.getCouponId();

		rslt.couponPrice = this.getCouponPrice();

		rslt.fromWhere = this.getFromWhere();

		rslt.comment = this.getComment();

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
	public void constructWith(LzzOrder pro) {
		// TODO Auto-generated method stub
		this.id  = pro.getId ();

		this.userId  = pro.getUserId ();

		this.orderNum  = pro.getOrderNum ();

		this.payTime  = pro.getPayTime ();

		this.address  = pro.getAddress ();

		this.addressMod  = pro.getAddressMod ();

		this.expressCompany  = pro.getExpressCompany ();

		this.expressNum  = pro.getExpressNum ();

		this.expressFee  = pro.getExpressFee ();

		this.status  = pro.getStatus ();

		this.evaluateTime  = pro.getEvaluateTime ();

		this.finishTime  = pro.getFinishTime ();

		this.couponFlag  = pro.getCouponFlag ();

		this.couponId  = pro.getCouponId ();

		this.couponPrice  = pro.getCouponPrice ();

		this.fromWhere  = pro.getFromWhere ();

		this.comment  = pro.getComment ();

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
