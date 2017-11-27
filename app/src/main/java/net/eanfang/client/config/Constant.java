package net.eanfang.client.config;

/**
 * 常量
 * 
 * @author 29698868
 * @email 29698868@qq.com
 * @date 2017年11月15日 下午1:23:52
 */
public class Constant {
	
	public static final Long COMPANY_NULL_ID=0L;
	public static final String COMMA=",";
	public static final String DOT=".";
	public static final String DEFAULT_AVATAR="0";
	
	public static final String YAF_TOKEN_KEY="YAF-Token";
	public static final String YAF_SUBSYS_KEY="YAF-SubSys";	
	public static final String YAF_REQUEST_FROM_KEY="Request-From";
	
	/**
	 * 记录状态：记录删除禁用状态
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
	 */
    public enum SubSystem {
    	
    	/**
         * 系统管理
         */
    	SYS(0),

    	/**
         * 总平台管理
         */
    	TOP(1),
    	
        /**
         * 城市平台管理
         */
        CITY(2),
        
        /**
         * 企业管理
         */
        ENT(3),
    	
        /**
         * 技师
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
   
    public enum OrderType {
        /**
         * 报修单 订单编号的前缀
         */
        REPAIR("MO" ),
        /**
         * 包装单 订单编号的前缀
         */
        INSTALL("EO");


        private String value;

        private OrderType(String value) {
            this.value = value;
        }

        public String val() {
            return value;
        }


    }


    /**
     * 报修单 订单状态
     */
    public enum RepairStatus {
        /**
         * 创建成功，待客户付款
         */
        CREATED(0),
        /**
         * 支付成功，待回电
         */
        PAID(1),
        /**
         * 电话回访成功，待上门签到
         */
        CALLED(2),
        /**
         * 上门签到成功，待完工
         */
        VISITED(3),
        /**
         * 完工提交，待客户确认
         */
        WORKED(4),
        /**
         * 客户确认，订单完成
         */
        FINISHED(5);


        private Integer value;

        private RepairStatus(Integer value) {
            this.value = value;
        }

        public Integer val() {
            return value;
        }
    }

    /**
     * 请求来源
     */
    public enum ReqFrom {
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

        private ReqFrom(Integer value) {
            this.value = value;
        }

        public Integer val() {
            return value;
        }
    }
    
	/**
	 * 记录状态：记录删除禁用状态
	 * 
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

}
