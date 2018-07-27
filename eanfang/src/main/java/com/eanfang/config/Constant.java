package com.eanfang.config;

/***
 * 常量
 *
 * @author 29698868
 * @email 29698868@qq.com
 * @date 2017年11月15日 下午1:23:52
 */
public interface Constant {
    /***
     * 发票费用
     */
    int INVOICE_FEE = 20;

    /***
     * 本公司的
     */
    int COMPANY_DATA_CODE = 0;
    /***
     * 我创建的
     */
    int CREATE_DATA_CODE = 1;
    /***
     * 我负责的
     */
    int ASSIGNEE_DATA_CODE = 2;

    String mapScope = "加油站|其它能源站|加气站|汽车养护/装饰|汽车配件销售|汽车租赁|" +
            "二手车交易|充电站|汽车销售|汽车维修|餐饮服务|购物服务|售票处|邮局|物流速递|电讯营业厅|人才市场|自来水营业厅|电力营业厅|体育休闲服务|医疗保健服务|住宿服务|风景名胜|" +
            "商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|金融保险服务|知名企业|地名地址信息|公共设施|通行设施";
    String ALL = "全部";

    /**
     * 无数据更新
     */
    String NO_UPDATE = "NO_UPDATE";


    /***
     * 创建公司 id
     */
    String CREATE_COMPANY_ID = "createCompanyId";
    /***
     * 客户端id
     */
    String OWNER_COMPANY_ID = "ownerCompanyId";
    /**
     * 技师端
     */
    String ASSIGNEE_COMPANY_ID = "assigneeCompanyId";
    /***
     * 创建人 id
     */
    String CREATE_USER_ID = "createUserId";
    /***
     * 受理人
     */
    String ASSIGNEE_USER_ID = "assigneeUserId";
    /***
     * id
     */
    String ID = "id";
    String IMG_SPLIT = ",";

    String RED_UN_READ = "RedUnRead";

    /**
     * 1.优先级
     */

    String INSTANCY_LEVEL = "instancyLevelType";
    /**
     * 2.首次响应
     */

    String FIRST_LOOK = "firstLookType";
    /**
     * 3.首次汇报时间
     */

    String FIRST_CALLBACK = "firstCallbackType";
    /**
     * 4.跟踪汇报时间
     */

    String THEN_CALLBACK = "thenCallbackType";
    /**
     * 5.汇报类型
     */

    String REPORTTYPE = "reportType";
    /**
     * 6.回复时限
     */

    String REVERT_TIME_LIMIT_TYPE = "RevertTimeLimitType";
    /**
     * 7.预计工期
     */

    String PREDICTTIME_TYPE = "PredictTimeType";
    /**
     * 8.预算费用范围
     */

    String BUDGET_LIMIT_TYPE = "BudgetLimitType";
    /**
     * 9.基础数据
     */

    String BASE_DATA_TYPE = "BaseDataType";
    /**
     * 10.到达时限
     */

    String ARRIVE_LIMIT = "ArriveLimit";
    /**
     * 11.从业等级
     */

    String WORKING_LEVEL = "WorkingLevel";
    /**
     * 12.从业年限
     */

    String WORKING_YEAR = "WorkingYear";
    /**
     * 13.维修结果
     */

    String BUGHANDLE_DETAIL_STATUS = "BughandleDetailStatus";

    /**
     * 故障明细状态 二级的key
     */
    String BUGHANDLE_DETAIL_STATUS_KEY = "BughandleDetailStatusKey";
    /**
     * 14.正常/不正常
     */

    String IS_NORMAL = "IsNormal";
    /**
     * 15.录制天数
     */

    String STORE_DAYS = "StoreDays";
    /**
     * 16.参数
     */

    String PARAM = "Param";
    /**
     * 17.挂单原因
     */

    String TRANSFER_CAUSE = "TransferCause";
    /**
     * 18.检查结果
     */

    String CHECK_RESULT = "CheckResult";
    /**
     * 19.保养周期
     */

    String CYCLE = "Cycle";
    /**
     * 20.维保标准
     */

    String MAINTAIN_LEVEL = "MaintainLevel";
    /**
     * 21.单位
     */

    String UNIT = "Unit";
    /**
     * 22.汇报给谁
     */

    String REPORT_TYPE = "ReportType";
    /**
     * 23.处理状态
     */

    String STATUS = "status";

    String CUST_STATUS = "CustStatus";
    /**
     * 24.ApplyStatus
     */
    String APPLY_STATUS = "ApplyStatus";
    /**
     * 25.PublishStatus
     */
    String PUBLISH_STATUS = "PublishStatus";
    /**
     * 26.status
     */
    String TASK_PUB_STATUS = "status";
    /**
     * 27.项目类型
     */
    String COOPERATION_TYPE = "CooperationType";
    /**
     * 28.绑定结果
     */
    String COOPERATION_STATUS = "CooperationStatus";
    /**
     * 29.公司规模
     */
    String ORG_UNIT_SCALE = "OrgUnitScale";
    /**
     * 30.技师工作状态
     */
    String WORK_STATUS = "WorkStatus";
    /**
     * 31.误报的报警原因
     */
    String FALSE_CAUSE = "FalseCause";
    /**
     * 32.旁路的报警原因
     */
    String BYPASS_CAUSE = "BypassCause";
    /**
     * 33.闯防的报警原因
     */
    String THROUGH_CAUSE = "ThroughCause";
    /**
     * 34.推送类型
     */
    String PUSH_TYPE = "PushType";

    /**
     * 35.报修统计设备状况图片后缀
     */
    String DEVICE_PIC_FONT = "biz/statistics/icon_";

    String PAY_TYPE = "PayType";

    String NOTICE_TYPE = "NoticeType";

    /**
     * 35.维保状态
     */

    String MAINTAIN_CONSTANT_STATUS = "statusTwo";
    /**
     * 36，处理的结果
     */

    String MAINTAIN_ADD_CHECK_CONDITION = "existResult";

    /**
     * 37，系统运行结论
     */

    String MAINTAIN_OS_RUNTIME = "confirmStatus";

    /**
     * 38.面谈员工
     */
    String WORKTALK_STATUS = "FaceToWorkerStatus";
    /**
     * 39.交接班
     */
    String WORKTRANSFER_STATUS = "ExchangeStatus";

    //业务通知
    int NOTICE_BUSINESS = 1;

    // 系统通知
    int NOTICE_SYSTEM = 0;
    /**
     * 发包类型
     */
    String TASK_PUB_TYPE = "Type";


    /**
     * 未知类别
     */
    int UNKNOWN_TYPE = 0;
    /**
     * 系统类别
     */
    int SYS_TYPE = 1;
    /**
     * 服务类型
     */
    int BIZ_TYPE = 2;
    /**
     * 地区
     */
    int AREA = 3;
    /***
     *
     */
    int INDUSTRY = 4;
    /**
     * 品牌型号
     */
    int MODEL = 5;

    /***
     * 订单类型
     */
    enum OrderType implements Constant {
        /***
         * 报修单 订单编号的前缀
         */
        REPAIR("报修单", "MO"),
        /***
         * 报装单 订单编号的前缀
         */
        INSTALL("报装单", "EO"),
        /***
         * 报价单 订单编号的前缀
         */
        QUOTE("报价单", "PO"),
        /***
         * 设备编号的前缀
         */
        DEVICE("设备", "DO"),
        /***
         * 发包
         */
        PUBLISH("发包", "fb");


        public String v;
        private String title;

        OrderType(String title, String value) {
            this.title = title;
            this.v = value;
        }

        public String v() {
            return v;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    /***
     * 支付类型
     */
    enum PayType implements Constant {

        /***
         * 支付宝
         */
        ALI_PAY("支付宝", 0),
        /***
         * 微信
         */
        WX_PAY("微信", 1),
        /***
         * 银行卡
         */
        BANK("银行卡", 2);

        public int v;
        private String title;

        PayType(String title, int value) {
            this.title = title;
            this.v = value;
        }

        public int v() {
            return v;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    /***
     * 报修状态
     */
    enum RepairStatus implements Constant {
        /***
         * 创建成功，待客户付款
         */
        CREATED("创建成功，待客户付款", 0),
        /***
         * 支付成功，待回电
         */
        PAID("支付成功，待回电", 1),
        /***
         * 电话回访成功，待上门签到
         */
        CALLED("电话回访成功，待上门签到", 2),
        /***
         * 上门签到成功，待完工
         */
        VISITED("上门签到成功，待完工", 3),
        /***
         * 完工提交，待客户确认
         */
        WORKED("完工提交，待客户确认", 4),
        /***
         * 客户确认，订单完成
         */
        FINISHED("客户确认，订单完成", 5);

        public int v;
        private String title;

        RepairStatus(String title, int value) {
            this.title = title;
            this.v = value;
        }

        public int v() {
            return v;
        }

        @Override
        public String toString() {
            return title;
        }
    }

}
