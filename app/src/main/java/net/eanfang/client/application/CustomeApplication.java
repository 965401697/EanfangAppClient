/*
 * Copyright (c) 2016. Huangrui All rights reserved.
 */

package net.eanfang.client.application;

import android.support.multidex.MultiDexApplication;
import android.support.v4.util.ArrayMap;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.base.IBase;
import com.eanfang.util.AssetUtil;
import com.eanfang.util.SharePreferenceUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilderSupplier;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.model.LoginBean;

import net.eanfang.client.config.FastjsonConfig;
import net.eanfang.client.util.FrecsoImagePipelineUtil;
import net.eanfang.client.util.message.J_MessageVerify;

import java.io.IOException;


/**
 * CustomeApplication
 *
 * @author hou
 *         Created at 2016/12/1 13:58
 * @desc 程序入口
 */

public abstract class CustomeApplication extends MultiDexApplication {

    ArrayMap<String, IBase> mJIBaseArrayMap = new ArrayMap<>();
    static CustomeApplication mJCustomeApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mJCustomeApplication = this;
    }

    public void initConfig() {
        AssetUtil.get().init(getApplicationContext());
        J_MessageVerify.get().init(60);
        Fresco.initialize(this, FrecsoImagePipelineUtil.getImagePipelineConfig(getApplicationContext()));
        SimpleDraweeView.initialize(new PipelineDraweeControllerBuilderSupplier(this));
    }

    public static CustomeApplication get() {
        return mJCustomeApplication;
    }

    public void push(IBase j_iBase) {
        mJIBaseArrayMap.put(j_iBase.getClass().getName(), j_iBase);
    }

    public void pull(IBase j_iBase) {
        if (mJIBaseArrayMap.containsKey(j_iBase.getClass().getName())) {
            IBase j = mJIBaseArrayMap.get(j_iBase.getClass().getName());
            j.finishSelf();
        }
    }


    /**
     * sharepreference
     *
     * @param key
     * @param value
     * @throws IOException
     */
    public void set(String key, Object value) {
        synchronized (this) {
            try {
                SharePreferenceUtil.get().set(key, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * sharepreference
     *
     * @param key
     * @throws IOException
     */
    public JSONObject get(String key) {
        synchronized (this) {
            try {
                String jsonString = SharePreferenceUtil.get().get(key, "").toString();
                if (jsonString.length() <= 0) {
                    return new JSONObject();
                }

                return JSONObject.parseObject(jsonString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public JSONObject get(String key, Object defaultValue) {
        synchronized (this) {
            try {
                String jsonString = SharePreferenceUtil.get().get(key, defaultValue).toString();
                if (jsonString.length() <= 0) {
                    return new JSONObject();
                }

                return JSONObject.parseObject(jsonString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void remove(String key) {
        SharePreferenceUtil.get().remove(key);
    }

    public void saveUser(LoginBean user) {
        set(LoginBean.class.getName(), JSONObject.toJSONString(user, FastjsonConfig.config));
    }

    public LoginBean getUser() {
        JSONObject jsonObject = get(LoginBean.class.getName(), "");
        return (JSONObject.toJavaObject(jsonObject, LoginBean.class));
    }

    public Long getUserId() {
        return getUser().getAccount().getDefaultUser().getUserId();
    }

    public Long getCompanyId() {
        return getUser().getAccount().getDefaultUser().getCompanyId();
    }

    public Long getAccId() {
        return getUser().getAccount().getDefaultUser().getAccId();
    }

    public void closeActivity(String... cls) {
        for (String str : cls) {
            if (mJIBaseArrayMap.containsKey(str)) {
                Object object = mJIBaseArrayMap.get(str);
                if (object instanceof IBase) {
                    IBase base = (IBase) object;
                    base.finishSelf();
                }
            }
        }
    }


}
