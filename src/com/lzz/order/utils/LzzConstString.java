package com.lzz.order.utils;

/**
 * @author	LiuWei
 * @date	2071-07-10
 * @describe	用于存放常量字符串
 */
public class LzzConstString {
	/**
	 * 1、变量命名以"sm" 打头(static member)
	 * 2、变量命中尽量包含模块以及变量含义（Test 模块中的 Example 含义的变量，命名为  "smTestExample"）
	 * 		并标明变量含义
	 * 3、添加新变量时请搜索此类中是否已存在相同的字符串，并判断应用场景是否相同
	 * 		如果相同，则可直接使用；
	 * 		若不相同，可以直接新建（不推荐），和历史存在的作者进行沟通
	 */
	
	//LiuWei zone begin
	// Test模块 示例
	final public static String smSex_male = "男";
	final public static String smSex_female = "女";
	final public static String smNickName_prefix = "用户-";
	
	//user_level 客户等级
	final public static String smUserLevel = "user_level";
	final public static String smUserLevel_normal = "普通会员";
	final public static String smUserLevel_bronze = "青铜会员";
	final public static String smUserLevel_silver = "白银会员";
	final public static String smUserLevel_gold = "黄金会员";
	final public static String smUserLevel_platinum = "铂金会员";
	final public static String smUserLevel_diamond = "钻石会员";
	final public static String smUserLevel_wholeSaler = "批发商";
	
	//taste 口味
	final public static String smTaste = "taste";
	final public static String smTaste_sour = "酸";
	final public static String smTaste_sweet = "甜";
	final public static String smTaste_bitter = "苦";
	final public static String smTaste_hot = "辣";
	
	final public static String smFileType_image = "file_type_image";
	final public static String smFileType_audio = "file_type_audio";
	final public static String smFileType_video = "file_type_video";
	final public static String smFileType_normal = "file_type_normal";
	
	//系统路径分隔符
	//public final static String smSystem_sepa = java.io.File.separator;
	final public static String smSystem_sepa = "/";
	
	//订单来源
	final public static String smOrderFrom_Cart = "fromCart";
	final public static String smOrderFrom_SingleGood = "fromSingleGood";
	
	//快递方式
	final public static String smExpressMode = "express_mode";
	final public static String smExpressMode_Self = "到店自取"; //自取
	final public static String smExpressMode_Express = "快递"; //快递
	final public static String smExpressMode_Logistical = "物流配送（货款到付）"; //物流配送（货款到付）
	
	//订单状态
	final public static String smOrderStatus = "order_status"; //
	final public static String smOrderStatus_ToPay = "待付款"; //待付款
	final public static String smOrderStatus_ToExpress = "待发货"; //待发货
	final public static String smOrderStatus_ToReceive = "待收货"; //待收货
	final public static String smOrderStatus_ToEvaluate = "待评价"; //待评价
	final public static String smOrderStatus_Finished = "已完成"; //已完成
	final public static String smOrderStatus_All = "全部"; //已完成
	
	//首页菜单类型
	final public static String smIndexDisplayType_Menu = "menu"; //功能菜单
	final public static String smIndexDisplayType_Scroll = "scroll"; //滚动图
	
}
