
package com.lzz.order.pojo;

/*
id     id

value     字面值

category     分类

description     描述

typicalImg     类型图片

innerLevel     类型内部等级

innerValue     类型内部值

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
public class LzzDictionary {

	private String id = "";

	private String value = "";

	private String category = "";

	private String description = "";

	private String typicalImg = "";

	private String innerLevel = "";

	private String innerValue = "";

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

	
	public LzzDictionary(){};
	
	public LzzDictionary(LzzDictionary obj){
	    id = obj.getId();

	    value = obj.getValue();

	    category = obj.getCategory();

	    description = obj.getDescription();

	    typicalImg = obj.getTypicalImg();

	    innerLevel = obj.getInnerLevel();

	    innerValue = obj.getInnerValue();

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

	public String getValue() {
	    return value;
	}
	public void setValue(String value) {
	    this.value = value;
	}

	public String getCategory() {
	    return category;
	}
	public void setCategory(String category) {
	    this.category = category;
	}

	public String getDescription() {
	    return description;
	}
	public void setDescription(String description) {
	    this.description = description;
	}

	public String getTypicalImg() {
	    return typicalImg;
	}
	public void setTypicalImg(String typicalImg) {
	    this.typicalImg = typicalImg;
	}

	public String getInnerLevel() {
	    return innerLevel;
	}
	public void setInnerLevel(String innerLevel) {
	    this.innerLevel = innerLevel;
	}

	public String getInnerValue() {
	    return innerValue;
	}
	public void setInnerValue(String innerValue) {
	    this.innerValue = innerValue;
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


	public LzzDictionary clone(){
		LzzDictionary rslt = new LzzDictionary();
		rslt.id = this.getId();

		rslt.value = this.getValue();

		rslt.category = this.getCategory();

		rslt.description = this.getDescription();

		rslt.typicalImg = this.getTypicalImg();

		rslt.innerLevel = this.getInnerLevel();

		rslt.innerValue = this.getInnerValue();

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
	public void constructWith(LzzDictionary pro) {
		// TODO Auto-generated method stub
		this.id  = pro.getId ();

		this.value  = pro.getValue ();

		this.category  = pro.getCategory ();

		this.description  = pro.getDescription ();

		this.typicalImg  = pro.getTypicalImg ();

		this.innerLevel  = pro.getInnerLevel ();

		this.innerValue  = pro.getInnerValue ();

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
