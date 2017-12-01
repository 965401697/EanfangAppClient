package net.eanfang.client.config;

/**
 * 常量
 *
 * @author 29698868
 * @email 29698868@qq.com
 * @date 2017年11月15日 下午1:23:52
 */
public class Constant {

    public static final Long COMPANY_NULL_ID = 0L;
    public static final String COMMA = ",";
    public static final String DOT = ".";
    public static final String DEFAULT_AVATAR = "0";

    public static final String YAF_TOKEN_KEY = "YAF-Token";
    public static final String YAF_SUBSYS_KEY = "YAF-SubSys";
    public static final String YAF_REQUEST_FROM_KEY = "Request-From";

    /**
     * 记录状态：记录删除禁用状态
     */
    public enum RecordStatus {

        /**
         * 正常
         */
        STATUS_NORMAL(0),

        /**
         * 禁用
         */
        STATUS_DISABLE(1),

        /**
         * 删除
         */
        STATUS_DEL(2),

        /**
         * 禁用且删除
         */
        STATUS_DISABLE_DEL(3);


        private int value;

        private RecordStatus(int value) {
            this.value = value;
        }

        public int val() {
            return value;
        }
    }

    /**
     * 菜单类型
     */
    public enum ResourceType {
        /**
         * 子系统
         */
        SUBSYSTEM(0),

        /**
         * 模块
         */
        MODULE(1),
        /**
         * 菜单
         */
        MENU(2),
        /**
         * 按钮
         */
        BUTTON(3);

        private int value;

        private ResourceType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 用户类型：来自哪里
     */
    public enum RegType {
        /**
         * 系统用户
         */
        SYS(0),

        /**
         * 个人用户
         */
        PERSON(1),

        /**
         * 技师用户
         */
        TECH(2);

        private int value;

        private RegType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 账号类型：取菜单是否需要查授权表
     */
    public enum AccountType {

        /**
         * 系统管理员
         */
        SYS_ADMIN(0),

        /**
         * 总平台管理员
         */
        TOP_ADMIN(1),

        /**
         * 城市平台管理员
         */
        CITY_ADMIN(2),

        /**
         * 企业管理员
         */
        ENT_ANDMIN(3),

        /**
         * 普通用户
         */
        COMMON_USER(4);


        private int value;

        private AccountType(int value) {
            this.value = value;
        }

        public int val() {
            return value;
        }
    }

    /**
     * 子系统类型：通过int值表示的子系统
     */
    public enum SubSystem {

        /**
         * 系统管理员
         */
        SYS(0),

        /**
         * 总平台管理员
         */
        TOP(1),

        /**
         * 城市平台管理员
         */
        CITY(2),

        /**
         * 企业管理员
         */
        ENT(3),

        /**
         * 普通用户
         */
        TECH(4);


        private int value;

        private SubSystem(int value) {
            this.value = value;
        }

        public int val() {
            return value;
        }
    }

    /**
     * 组织机构类型
     */

    public enum OrgType {

        /**
         * 总公司
         */
        TOP_COMPANY(0),

        /**
         * 分子公司
         */
        SUB_COMPANY(1),

        /**
         * 部门
         */
        DEPARTMENT(2);

        private int value;

        private OrgType(int value) {
            this.value = value;
        }

        public int val() {
            return value;
        }
    }

    /**
     * 请求来源
     */
    public enum RequestFrom {
        /**
         * 系统请求
         */
        SYSTEM(0),
        /**
         * 易安防总平台的请求
         */
        YAF(1),
        /**
         * 城市运营平台的请求
         */
        CITY(2),
        /**
         * 客户端WEB的请求
         */
        ENT(3),
        /**
         * 客户端app的请求
         */
        CLIENT(4),
        /**
         * 安防公司WEB的请求
         */
        SHOP(5),
        /**
         * 技师端app的请求
         */
        WORKER(6);


        private Integer value;

        private RequestFrom(Integer value) {
            this.value = value;
        }

        public Integer val() {
            return value;
        }
    }


    /*
     *====================================================================================================================================
     *---------------------------------------------------------------华丽的分割线-----------------------------------------------------------
     *====================================================================================================================================
     * 定义业务系统所需的 通用常量
     * 模块内常量请单独创建类
     *
     */


    /**
     * 订单类型
     */
    public enum OrderType {
        /**
         * 报修单 订单编号的前缀
         */
        REPAIR("MO"),
        /**
         * 包装单 订单编号的前缀
         */
        INSTALL("EO"),
    	/**
    	 * 报价单 订单编号的前缀
    	 */
        QUOTE("PO");

        private String value;

        private OrderType(String value) {
            this.value = value;
        }

        public String val() {
            return value;
        }


    }


    /**
     * 推送的类型
     */
    public enum PushType {

        /**
         * 报修成功
         */
        REPAIR_SUCCESS("报修成功", 1),
        /**
         * 预约成功
         */
        BOOK_SUCCESS("预约成功", 2),
        /**
         * 待上们提醒
         */
        BEFORE_VISIT_IN("上门提醒", 3),
        /**
         * 改约提醒
         */
        ALTER_BOOK("改约提醒", 4),
        /**
         * 签到提醒
         */
        VISIT_IN("签到提醒", 5),
        /**
         * 完工提醒
         */
        FINISH_WORK("完工提醒", 6),
        /**
         * 确认完工
         */
        COMMIT_WORK("确认完工", 7),
        /**
         * 报价提醒
         */
        QUOTE_SUCCESS("报价提醒", 8),
        /**
         * 报价付款成功
         */
        QUOTE_PAY_SUCCESS("报价支付", 9),
        /**
         * 报装提醒
         */
        INSTALL_SUCCESS("报装提醒", 10),
        /**
         * 接包申请
         */
        TASK_APPLY_SUCCESS("接包申请", 11);


        private String title;
        private Integer value;

        private PushType(String title, Integer value) {
            this.value = value;
            this.title = title;
        }

        public Integer val() {
            return value;
        }

        public String getTitle() {
            return this.title;
        }
    }

    /**
     * 到达时限
     */
    public enum ArriveLimit {
        /**
         * 两小时内
         */
        TWO_HOUR(0),
        /**
         * 四小时内
         */
        FOUR_HOUR(1),
        /**
         * 当天
         */
        ONE_DAY(2),
        /**
         * 三天内
         */
        THREE_DAY(3),
        /**
         * 一周内
         */
        ONE_WEEK(4),
        /**
         * 半个月
         */
        HALF_MONTH(5),
        /**
         * 一月内
         */
        ONE_MONTH(6),
        /**
         * 三个月内
         */
        THREE_MONTH(7),
        /**
         * 半年内
         */
        HALF_YEAR(8),
        /**
         * 一年
         */
        ONE_YEAR(9);


        private Integer value;

        private ArriveLimit(Integer value) {
            this.value = value;
        }

        public Integer val() {
            return value;
        }
    }


    /**
     * 角色类型
     */
    public enum RoleType {
        /**
         * 内置角色
         */
        SYS(0),
        /**
         * 自定义
         */
        USERDEF(1);

        private int value;

        private RoleType(int value) {
            this.value = value;
        }

        public int val() {
            return value;
        }

    }
    
    /**
     * 基础数据类型，顶级编号
     */
    public enum BaseDataType {

        /**
         * 系统类别
         */
        SYS_TYPE(1),

        /**
         * 业务类型
         */
        BIZ_TYPE(2),

        /**
         * 地区
         */
        AREA(3);

        private int value;

        private BaseDataType(int value) {
            this.value = value;
        }

        public int val() {
            return value;
        }
    }
}
