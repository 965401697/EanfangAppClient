package net.eanfang.client.network.apiservice;

import net.eanfang.client.BuildConfig;

import static net.eanfang.client.network.apiservice.BaseService.BASE_URL;

/**
 * Created by MrHou
 *
 * @on 2017/11/6  16:43
 * @email houzhongzhou@yeah.net
 * @desc 用户
 */

public interface UserApi {
    /**
     * 登录
     */
    String APP_LOGIN = BuildConfig.HOST + "/yaf_sys/sys/login";
    /**
     * 退出
     */
    String APP_LOGOUT = BuildConfig.HOST + "/yaf_sys/sys/logout";

    /**
     * 客户端收到的评价
     */
    String GET_CILENT_EVALUATE_LIST = BuildConfig.API_HOST + "/yaf_ent/clientEvaluate/list";

    /**
     * 技师端收到的评价
     */
    String GET_WORKER_EVALUATE_LIST = BuildConfig.API_HOST + "/yaf_shop/workerEvaluate/list";

    /**
     * 获取验证码
     */
    String GET_VERIFY_CODE = BuildConfig.HOST + "appverifycode";

    /**
     * 个人资料修改
     * get
     */
    String USER_INFO_UPDATE = BASE_URL + "/yaf_sys/account/profile";

    /**
     * 查看修改后信息
     */
    String GET_USER_INFO = BASE_URL + "/yaf_sys/sys/userinfo";


    /**
     * 合作公司
     */
    String GET_COOPERATION_LIST = BuildConfig.API_HOST + "/yaf_shop/cooperation/list";
    /**
     * 审核合作业务
     */
    String GET_COOPERATION_AUDIT = BuildConfig.API_HOST + "/yaf_shop/cooperation/audit";
    /**
     * 组织结构
     */
    String GET_STAFFINCOMPANY_LISTTREE = BuildConfig.API_HOST + "/yaf_sys/org/staffincompany/listtree";
    /**
     * 分公司
     */
    String GET_BRANCH_OFFICE_LIST = BuildConfig.API_HOST + "/yaf_sys/org/company/listtopall";
    /**
     * 外协单位
     */
    String GET_STAFFTEMP_OUTER_LIST = BuildConfig.API_HOST + "/yaf_sys/stafftemp/load_outer_assist";
    /**
     * 外部联系人
     */
    String GET_STAFFTEMP_LIST = BuildConfig.API_HOST + "/yaf_sys/stafftemp/list";

    /**
     * 新增企业
     */
    String GET_ORGUNIT_SHOP_ADD = BuildConfig.API_HOST + "/yaf_sys/orgunit/ent/add";

    /**
     * 加载企业信息
     */
    String GET_COMPANY_ORG_INFO = BuildConfig.API_HOST + "/yaf_sys/orgunit/infobyorgid/";

    /**
     * 填写企业资料
     */
    String GET_ORGUNIT_SHOP_INSERT = BuildConfig.API_HOST + "/yaf_sys/orgunit/ent/insert";

    /**
     * 企业安防公司认证
     */
    String GET_ORGUNIT_SEND_VERIFY = BuildConfig.API_HOST + "/yaf_sys/orgunit/sendverify/";
}
