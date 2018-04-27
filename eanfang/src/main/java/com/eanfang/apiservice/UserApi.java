package com.eanfang.apiservice;

import com.eanfang.BuildConfig;

import static com.eanfang.apiservice.BaseService.BASE_URL;

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
    String APP_LOGIN = BuildConfig.API_HOST + "/yaf_sys/sys/login";
    /**
     * 注册
     */
    String APP_REGISTER = BuildConfig.API_HOST + "/yaf_sys/account/register/";
    /**
     * 验证码登录
     */
    String APP_LOGIN_VERIFY = BuildConfig.API_HOST + "/yaf_sys/sys/login_verify";
    /**
     * 退出
     */
    String APP_LOGOUT = BuildConfig.API_HOST + "/yaf_sys/sys/logout";

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
    String GET_VERIFY_CODE = BuildConfig.API_HOST + "/yaf_sys/account/sendverify";

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
     * 搜索客户
     */
    String GET_WORKCOMPANY_LIST = BuildConfig.API_HOST + "/yaf_shop/workCompany/search";
    /**
     * 绑定公司
     */
    String GET_COOPERATION_CREATE = BuildConfig.API_HOST + "/yaf_shop/cooperation/create";
    /**
     * 签到
     */
    String GET_SIGN_ADD = BuildConfig.API_HOST + "/yaf_oa/sign/insert";
    /**
     * 签到次数，签退次数
     */
    String GET_SIGN_COUNT = BuildConfig.API_HOST + "/yaf_oa/sign/querySignCount";
    String GET_SIGN_LIST = BuildConfig.API_HOST + "/yaf_oa/sign/userList";
    String GET_SIGN_DETAIL = BuildConfig.API_HOST + "/yaf_oa/sign/detail";
    /**
     * 新增安防公司
     */
    String GET_ORGUNIT_SHOP_ADD = BuildConfig.API_HOST + "/yaf_sys/orgunit/shop/add";
    /**
     * 填写企业资料
     */
    String GET_ORGUNIT_SHOP_INSERT = BuildConfig.API_HOST + "/yaf_sys/orgunit/shop/insert";
    /**
     * 填写系统类别
     */

    String GET_ORGUNIT_SHOP_ADD_SYS = BuildConfig.API_HOST + "/yaf_sys/company2basedata/companysys/";
    /**
     * 业务类型
     */
    String GET_ORGUNIT_SHOP_ADD_BIZ = BuildConfig.API_HOST + "/yaf_sys/company2basedata/companybiz/";
    /**
     * 服务区域
     */
    String GET_ORGUNIT_SHOP_ADD_AREA = BuildConfig.API_HOST + "/yaf_sys/company2basedata/companyarea/";

    /**
     * 新增企业
     */
    String GET_ORGUNIT_ENT_ADD = BuildConfig.API_HOST + "/yaf_sys/orgunit/ent/add";

    /**
     * 填写企业资料
     */
    String GET_ORGUNIT_ENT_INSERT = BuildConfig.API_HOST + "/yaf_sys/orgunit/ent/insert";

    /**
     * 企业安防公司认证
     */
    String GET_ORGUNIT_SEND_VERIFY = BuildConfig.API_HOST + "/yaf_sys/orgunit/sendverify/";

    /**
     * 获取技师信息
     */
    String GET_WORKER_INFO = BuildConfig.API_HOST + "/yaf_sys/techworkerverify/loadprofile";
    /**
     * 填写技师信息
     */
    String GET_TECH_WORKER_ADD = BuildConfig.API_HOST + "/yaf_sys/techworkerverify/insert";
    /**
     * 加载系统类别
     */
    String GET_TECH_WORKER_SYS = BuildConfig.API_HOST + "/yaf_sys/basedata2user/list/";
    /**
     * 技师绑定系统类别
     */
    String POST_TECH_WORKER_SYS = BuildConfig.API_HOST + "/yaf_sys/basedata2user/workersys";
    String POST_TECH_WORKER_BIZ = BuildConfig.API_HOST + "/yaf_sys/basedata2user/workerbiz";
    String POST_TECH_WORKER_AREA = BuildConfig.API_HOST + "/yaf_sys/basedata2user/workerarea";
    String POST_TECH_WORKER_SEND_VERIFY = BuildConfig.API_HOST + "/yaf_sys/techworkerverify/sendverify";

    /**
     * 加载企业信息
     */
    String GET_COMPANY_ORG_INFO = BuildConfig.API_HOST + "/yaf_sys/orgunit/infobyorgid/";
    /**
     * 加载系统类别
     */
    String GET_COMPANY_ORG_SYS_INFO = BuildConfig.API_HOST + "/yaf_sys/company2basedata/list/";
    /**
     * 技师接单状态
     */
    String GET_WORKER_CHANGE = BuildConfig.API_HOST + "/yaf_shop/worker/change";

    /**
     * 根据手机号获取用户信息
     */
    String GET_USER_INFO_BY_PHONE = BuildConfig.API_HOST + "/yaf_sys/account/mobile/";
    /**
     * 技师上报位置
     */
    String POST_WORKER_SUBMIT_LOCATION = BuildConfig.API_HOST + "/yaf_shop/worker/submitLocation";

    /**
     * 客户端APP更新
     */
    String GET_CILENT_UPDATE_APP = BuildConfig.API_HOST + "/yaf_sys/update/updateClient";

    /**
     * 技师端APP更新
     */
    String GET_WORKER_UPDATE_APP = BuildConfig.API_HOST + "/yaf_sys/update/updateWork";

    /**
     * 获取好有列表
     */
    String POST_FRIENDS_LIST = BuildConfig.API_HOST + "/yaf_sys/outer2user/findFriends";

    /**
     * 获取好有列表
     */
    String POST_RONGY_TOKEN = BuildConfig.API_HOST + "/yaf_im/im/getToken";

    /**
     * 搜索好友
     */
    String POST_FIND_FRIEND = BuildConfig.API_HOST + "/yaf_sys/account/find";
    /**
     * 添加好友
     */
    String POST_ADD_FRIEND = BuildConfig.API_HOST + "/yaf_sys/outer2user/insert";
    /**
     * /**
     * 添加好友推送
     */
    String POST_ADD_FRIEND_PUSH = BuildConfig.API_HOST + "/yaf_im/im/friendPush";
    /**
     * 同意添加
     */
    String POST_ACCEPT_FRIEND = BuildConfig.API_HOST + "/yaf_sys/outer2user/accept";
    /**
     * 拒绝
     */
    String POST_REFUSE_FRIEND = BuildConfig.API_HOST + "/yaf_sys/outer2user/refuse";
    /**
     * 删除好友
     */
    String POST_DELETE_FRIEND = BuildConfig.API_HOST + "/yaf_sys/outer2user/delete";
    /**
     * 创建群组
     */
    String POST_CREAT_GROUP = BuildConfig.API_HOST + "/yaf_im/sysgroup/create";
    /**
     * 获取我的群组
     */
    String POST_GET_GROUP = BuildConfig.API_HOST + "/yaf_im/sysgroup/list";
    /**
     * 群成员列表
     */
    String POST_GROUP_NUM = BuildConfig.API_HOST + "/yaf_im/sysgroupuser/list";
    /**
     * 更新群组信息
     */
    String POST_UPDATA_GROUP = BuildConfig.API_HOST + "/yaf_im/sysgroup/update";
    /**
     * /**
     * 群组详情
     */
    String POST_GROUP_DETAIL = BuildConfig.API_HOST + "/yaf_im/sysgroup/detail";
    /**
     * 退出群组
     */
    String POST_GROUP_QUIT = BuildConfig.API_HOST + "/yaf_im/sysgroup/quit";
    /**
     * 销毁群组
     */
    String POST_GROUP_DELETE = BuildConfig.API_HOST + "/yaf_im/sysgroup/delete";

    /**
     * 群成员添加
     */
    String POST_GROUP_JOIN = BuildConfig.API_HOST + "/yaf_im/sysgroupuser/join";
    /**
     * 查询没有在群组的成员
     */
    String POST_GROUP_NOJOIN = BuildConfig.API_HOST + "/yaf_sys/outer2user/findJoin";
    /**
     * 全员禁言
     */
    String POST_GROUP_GAG = BuildConfig.API_HOST + "/yaf_im/sysgroup/gag";
    /**
     * 全员解禁
     */
    String POST_GROUP_NOGAG = BuildConfig.API_HOST + "/yaf_im/sysgroup/unGag";
    /**
     * 全员解禁
     */
    String POST_USER_INFO = BuildConfig.API_HOST + "/yaf_sys/account/info/";

}
