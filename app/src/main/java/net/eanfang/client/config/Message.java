package net.eanfang.client.config;

/**
 * 常量
 * 
 *
 * @author 29698868
 * @email 29698868@qq.com
 * @date 2017年11月15日 下午1:23:52
 */
public class Message {
	
    public final static int SUCESS_CODE = 20000;

    //50000-59999范围的错误编码为系统模块保留
	public final static int ERR_UNKNOW=50000; 
	
	public final static int ERR_TOKEN_CDOE=50010;
	public final static String ERR_TOKEN="非法的token";

	public final static int ERR_TOKEN_EXPIRED_CODE=50011;
	public final static String ERR_TOKEN_EXPIRED="Token 过期了";
	
	public final static int ERR_ANOTHER_LOGIN_COEE=50012;
	public final static String ERR_ANOTHER_LOGIN="其他客户端登录了";

	public final static int ERR_ACCOUNT_OR_PASSWD_CODE=50013; 
	public final static String ERR_ACCOUNT_OR_PASSWD="账号或密码不正确"; 
	
	public final static int UNAUTHOR_CODE=50014; 
	public final static String UNAUTHOR="需要登录"; 
	
	public final static int ERR_CAPTCHA_CODE=50015; 
	public final static String ERR_CAPTCHA="验证码不正确"; 
	
	public final static int ERR_ACCOUNT_CODE=50016; 
	public final static String ERR_ACCOUNT="账户验证失败"; 
	
	public final static int NONEED_CHANGE_COMPANY_CODE=50017; 
	public final static String NONEED_CHANGE_COMPANY="无需切换公司"; 
	
	public final static int ERR_NO_PERMISSION_CODE =50018; 
	public final static String ERR_NO_PERMISSION="没有权限，请联系管理员授权"; 	

	
	public final static int ERR_PK_DUPLICATE_CODE =50019; 
	public final static String ERR_PK_DUPLICATE="数据库中已存在该记录"; 	
	
	public final static int ERR_ACCOUNT_LOCKED_CODE =50020; 
	public final static String ERR_ACCOUNT_LOCKED="账号被冻结"; 	
	
	public final static int ERR_NO_PERMISSIOM_CODE =50021; 
	public final static String ERR_NO_PERMISSIOM="无权访问此功能"; 	
	
	
    public static final int MISSING_PARAMETER = 50022;
    public static final String MISSING_PARAMETER_STR = "缺少请求参数";


    public static final int REQUEST_NO_DATA = 50023;
    public static final String REQUEST_NO_DATA_STR = "暂无更多数据";

    public static final int REQUEST_FAIL = 50024;
    public static final String REQUEST_FAIL_STR = "请求失败";

	public final static int ERR_COMPANY_USER_CODE =50025; 
	public final static String ERR_COMPANY_USER="该公司没有此用户";
	
	public final static int ERR_ORG_TYPE_CODE =50026; 
	public final static String ERR_ORG_TYPE="错误的组织机构类别"; 
	
	public static final int MISSING_BUS_COMPANY_NULL_CODE = 50027;
	public static final String MISSING_BUS_COMPANY_NULL_STR = "未找到公司信息";
	    
	public static final int MISSING_OWNER_USER_NULL_CODE = 50028;
	public static final String MISSING_OWNER_USER_NULL_STR = "未找到归属人";
	
	public static final int ERR_INSERT_CODE = 50029;
	public static final String ERR_INSERT_STR = "新增数据失败";
	
	public static final int ERR_USER_MESSAGE_CODE = 50030;
	public static final String ERR_USER_MESSAGE_STR = "获取登录人相关信息失败";

}
