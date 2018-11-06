
package com.lzz.order.pojo;

/*
id     id

openId     本程序唯一ID

unionId     平台唯一ID

nick_name     昵称

avatarUrl     用户头像地址

sex     性别

phone     电话

pwd     密码

level     客户级别ID

isManager     是否是管理员

total_integration     用户总积分

cur_integration     用户当前积分

validate_flag     手机验证标识

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
public class LzzUser {

	private String id = "";

	private String openId = "";

	private String unionId = "";

	private String nick_name = "";

	private String avatarUrl = "";

	private String sex = "";

	private String phone = "";

	private String pwd = "";

	private String level = "";

	private String isManager = "";

	private String total_integration = "";

	private String cur_integration = "";

	private String validate_flag = "";

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

	
	public LzzUser(){};
	
	public LzzUser(LzzUser obj){
	    id = obj.getId();

	    openId = obj.getOpenId();

	    unionId = obj.getUnionId();

	    nick_name = obj.getNick_name();

	    avatarUrl = obj.getAvatarUrl();

	    sex = obj.getSex();

	    phone = obj.getPhone();

	    pwd = obj.getPwd();

	    level = obj.getLevel();

	    isManager = obj.getIsManager();

	    total_integration = obj.getTotal_integration();

	    cur_integration = obj.getCur_integration();

	    validate_flag = obj.getValidate_flag();

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

	public String getOpenId() {
	    return openId;
	}
	public void setOpenId(String openId) {
	    this.openId = openId;
	}

	public String getUnionId() {
	    return unionId;
	}
	public void setUnionId(String unionId) {
	    this.unionId = unionId;
	}

	public String getNick_name() {
	    return nick_name;
	}
	public void setNick_name(String nick_name) {
	    this.nick_name = nick_name;
	}

	public String getAvatarUrl() {
	    return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
	    this.avatarUrl = avatarUrl;
	}

	public String getSex() {
	    return sex;
	}
	public void setSex(String sex) {
	    this.sex = sex;
	}

	public String getPhone() {
	    return phone;
	}
	public void setPhone(String phone) {
	    this.phone = phone;
	}

	public String getPwd() {
	    return pwd;
	}
	public void setPwd(String pwd) {
	    this.pwd = pwd;
	}

	public String getLevel() {
	    return level;
	}
	public void setLevel(String level) {
	    this.level = level;
	}

	public String getIsManager() {
	    return isManager;
	}
	public void setIsManager(String isManager) {
	    this.isManager = isManager;
	}

	public String getTotal_integration() {
	    return total_integration;
	}
	public void setTotal_integration(String total_integration) {
	    this.total_integration = total_integration;
	}

	public String getCur_integration() {
	    return cur_integration;
	}
	public void setCur_integration(String cur_integration) {
	    this.cur_integration = cur_integration;
	}

	public String getValidate_flag() {
	    return validate_flag;
	}
	public void setValidate_flag(String validate_flag) {
	    this.validate_flag = validate_flag;
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


	public LzzUser clone(){
		LzzUser rslt = new LzzUser();
		rslt.id = this.getId();

		rslt.openId = this.getOpenId();

		rslt.unionId = this.getUnionId();

		rslt.nick_name = this.getNick_name();

		rslt.avatarUrl = this.getAvatarUrl();

		rslt.sex = this.getSex();

		rslt.phone = this.getPhone();

		rslt.pwd = this.getPwd();

		rslt.level = this.getLevel();

		rslt.isManager = this.getIsManager();

		rslt.total_integration = this.getTotal_integration();

		rslt.cur_integration = this.getCur_integration();

		rslt.validate_flag = this.getValidate_flag();

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
	public void constructWith(LzzUser pro) {
		// TODO Auto-generated method stub
		this.id  = pro.getId ();

		this.openId  = pro.getOpenId ();

		this.unionId  = pro.getUnionId ();

		this.nick_name  = pro.getNick_name ();

		this.avatarUrl  = pro.getAvatarUrl ();

		this.sex  = pro.getSex ();

		this.phone  = pro.getPhone ();

		this.pwd  = pro.getPwd ();

		this.level  = pro.getLevel ();

		this.isManager  = pro.getIsManager ();

		this.total_integration  = pro.getTotal_integration ();

		this.cur_integration  = pro.getCur_integration ();

		this.validate_flag  = pro.getValidate_flag ();

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
