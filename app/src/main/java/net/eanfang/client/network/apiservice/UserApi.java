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
     * 获取验证码
     */
    String GET_VERIFY_CODE = BuildConfig.HOST + "appverifycode";

    /**
     * 检查token
     */

    String CHECK_TOKEN = BASE_URL + "/getInfoBytoken";

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
     * 检测是否开通服务区域
     */
    String CHECK_SERVICE_REGION = BASE_URL + "/checkServiceRegion";
}
