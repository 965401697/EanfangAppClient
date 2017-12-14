package net.eanfang.client.config;

/**
 * 常量
 *
 * @author 29698868
 * @email 29698868@qq.com
 * @date 2017年11月15日 下午1:23:52
 */
public class Constant {
    /*1.优先级*/
    public static final String INSTANCY_LEVEL = "instancyLevelType";
    /*2.首次响应*/
    public static final String FIRST_LOOK = "firstLookType";
    /*3.首次汇报时间*/
    public static final String FIRST_CALLBACK = "firstCallbackType";
    /*4.跟踪汇报时间*/
    public static final String THEN_CALLBACK = "thenCallbackType";
    /*5.汇报类型*/
    public static final String REPORTTYPE = "reportType";
    /*6.回复时限*/
    public static final String REVERT_TIME_LIMIT_TYPE = "RevertTimeLimitType";
    /*7.预计工期*/
    public static final String PREDICTTIME_TYPE = "PredicttimeType";
    /*8.预算费用范围*/
    public static final String BUDGET_LIMIT_TYPE = "BudgetLimitType";
    /*9.基础数据*/
    public static final String BASE_DATA_TYPE = "BaseDataType";

    /*
     * 未知类别
     */
    public static final int UNKNOWN_TYPE = 0;
    /*
     * 系统类别
     */
    public static final int SYS_TYPE = 1;
    /*
     * 业务类型
     */
    public static final int BIZ_TYPE = 2;
    /*
     * 地区
     */
    public static final int AREA = 3;

}
