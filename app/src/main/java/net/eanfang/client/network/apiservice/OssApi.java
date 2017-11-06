package net.eanfang.client.network.apiservice;

import net.eanfang.client.BuildConfig;

/**
 * Created by MrHou
 *
 * @on 2017/11/6  16:39
 * @email houzhongzhou@yeah.net
 * @desc 阿里云oss
 */

public interface OssApi {
    /**
     * oss 的sts生成器url
     */
    String STS_URL = BuildConfig.API_HOST + "/stsgen";
}
