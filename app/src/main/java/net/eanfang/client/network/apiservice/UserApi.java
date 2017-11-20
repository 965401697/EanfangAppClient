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
    String APP_LOGIN = BuildConfig.HOST + BuildConfig.TYPE + "/" + "applogin";

    /**
     * 获取验证码
     */
    String GET_VERIFY_CODE = BuildConfig.HOST + "appverifycode";

    /**
     * 检查token
     */

    String CHECK_TOKEN = BASE_URL + "/getInfoBytoken";

    /**
     * 个人资料
     * get
     */
    String INFO_BACK = BASE_URL + "/myinfo/getinfo";
    /**
     * 个人头像上传
     * post
     */
    String INFO_HEAD_UPDATE = BASE_URL + "/myinfo/updateinfo";

    /**
     * 检测是否开通服务区域
     */
    String CHECK_SERVICE_REGION = BASE_URL + "/checkServiceRegion";
}
