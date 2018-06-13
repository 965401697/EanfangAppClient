/*
 * Copyright (c) 2016. Huangrui All rights reserved.
 */

package com.eanfang.application;

import android.support.multidex.MultiDexApplication;
import android.support.v4.util.ArrayMap;
import android.view.WindowManager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.camera.util.LogUtil;
import com.eanfang.config.Constant;
import com.eanfang.config.EanfangConst;
import com.eanfang.config.FastjsonConfig;
import com.eanfang.model.LoginBean;
import com.eanfang.ui.base.IBase;
import com.eanfang.util.FrecsoImagePipelineUtil;
import com.eanfang.util.SharePreferenceUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.util.V;
import com.eanfang.util.message.J_MessageVerify;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilderSupplier;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.util.List;


/**
 * CustomeApplication
 *
 * @author hou
 * Created at 2016/12/1 13:58
 * @desc 程序入口
 */

public abstract class CustomeApplication extends MultiDexApplication {

    static CustomeApplication mJCustomeApplication;
    ArrayMap<String, IBase> mJIBaseArrayMap = new ArrayMap<>();

    public static CustomeApplication get() {
        return mJCustomeApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mJCustomeApplication = this;
    }

    public void initConfig() {
        J_MessageVerify.get().init(60);
        Fresco.initialize(this, FrecsoImagePipelineUtil.getImagePipelineConfig(getApplicationContext()));
        SimpleDraweeView.initialize(new PipelineDraweeControllerBuilderSupplier(this));
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

//    /**
//     * sharepreference
//     *
//     * @param key
//     * @throws IOException
//     */
//    public JSONObject get(String key) {
//        synchronized (this) {
//            try {
//                String jsonString = SharePreferenceUtil.get().get(key, "").toString();
//                if (jsonString.length() <= 0) {
//                    return new JSONObject();
//                }
//
//                return JSONObject.parseObject(jsonString);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }

    public Long getAccId() {
        return getUser().getAccount().getDefaultUser().getAccId();
    }

    public String get(String key, Object defaultValue) {
        synchronized (this) {
            try {
                String jsonString = SharePreferenceUtil.get().get(key, defaultValue).toString();
                if (StringUtils.isEmpty(jsonString)) {
                    return null;
                }
                return jsonString;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public <T> Object get(String key, Class<T> clazz) {
        String json = get(key, "");
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return JSONObject.parseObject(json, clazz);
        } catch (Exception e) {
            return JSONObject.parseObject(json, clazz, Feature.DisableCircularReferenceDetect);
        }
    }

    public <T> List<T> getArr(String key, Class<T> clazz) {
        String json = get(key, "");
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return JSONArray.parseArray(json, clazz);
        } catch (Exception e) {
            return JSONArray.parseArray(json, clazz);
        }
    }


    public void remove(String key) {
        SharePreferenceUtil.get().remove(key);
    }

    public void saveUser(LoginBean user) {
        set(LoginBean.class.getName(), JSONObject.toJSONString(user, FastjsonConfig.config));
    }

    public LoginBean getUser() {
        String json = get(LoginBean.class.getName(), "");
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return JSONObject.parseObject(json, LoginBean.class, Feature.DisableCircularReferenceDetect);
    }

    public Long getUserId() {
        return getUser().getAccount().getDefaultUser().getUserId();
    }

    public Long getCompanyId() {
        return getUser().getAccount().getDefaultUser().getCompanyId();
    }

    public Long getTopCompanyId() {
        return getUser().getAccount().getDefaultUser().getTopCompanyId();
    }

    public String getOrgCode() {
        return V.v(() -> getUser().getAccount().getDefaultUser().getDepartmentEntity().getOrgCode());
    }

    public void closeActivity(String... cls) {
        for (String str : cls) {
            if (mJIBaseArrayMap.containsKey(str)) {
                Object object = mJIBaseArrayMap.get(str);
                if (object instanceof IBase) {
                    IBase base = (IBase) object;
                    if (!base.isFinishing()) {
                        try {
                            base.finishSelf();
                        } catch (Exception e) {
                            LogUtil.e("close cathc", e.getMessage());
                        }
                    }
                }
            }
        }
    }

    public void closeAll() {
        for (String str : mJIBaseArrayMap.keySet()) {
            Object object = mJIBaseArrayMap.get(str);
            if (object instanceof IBase) {
                IBase base = (IBase) object;
                if (!base.isFinishing()) {
                    try {
                        base.finishSelf();
                    } catch (Exception e) {
                        LogUtil.e("close cathc", e.getMessage());
                    }
                }
            }
        }
    }


}
