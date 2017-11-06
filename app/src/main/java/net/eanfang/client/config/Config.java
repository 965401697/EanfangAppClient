/*
 * Copyright (c) 2016. Huangrui All rights reserved.
 */

package net.eanfang.client.config;

import android.content.Context;

import com.eanfang.util.FileUtils;
import com.eanfang.util.SharePreferenceUtil;

import net.eanfang.client.util.ConfigUtils;


/**
 * @author wen
 *         Created at 2017/3/2
 * @desc app配置信息
 */
public class Config {
    private static Config config = null;


    /**
     * 获取实例
     *
     * @return
     */
    public static Config getConfig() {
        if (config == null) {
            config = new Config();
            config.init(EanfangApplication.getApplication().getBaseContext());
        }
        return config;
    }

    /**
     * 必须在 application 中需要做初始化
     *
     * @param context
     */

    public void init(Context context) {
        //初始化配置文件读取器
        ConfigUtils.initProperties(context);
        //文件初始化 toto
        FileUtils.init("yianfang");
        SharePreferenceUtil.get().init(context);
    }



}
