/*
 * Copyright (c) 2016. Huangrui All rights reserved.
 */

package com.eanfang.application;

import androidx.collection.ArrayMap;
import androidx.multidex.MultiDexApplication;

import com.camera.util.LogUtil;
import com.eanfang.kit.cache.CacheKit;
import com.eanfang.model.bean.LoginBean;
import com.eanfang.ui.base.IBase;
import com.eanfang.util.V;

import java.io.IOException;


/**
 * CustomeApplication
 *
 * @author hou
 * Created at 2016/12/1 13:58
 * @desc 程序入口
 */

/**
 * 已过期 请尽快更换 com.eanfang.base.BaseApplication
 */
@Deprecated
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
        // SharePreferenceUtil.get().set(key, value);
        CacheKit.get().put(key, value);

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
        String s = CacheKit.get().getStr(key);
        return s != null ? s : defaultValue.toString();
//        synchronized (this) {
//            try {
//                String jsonString = SharePreferenceUtil.get().get(key, defaultValue).toString();
//                if (StringUtils.isEmpty(jsonString)) {
//                    return null;
//                }
//                return jsonString;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
    }

    public <T> Object get(String key, Class<T> clazz) {
        return CacheKit.get().get(key, clazz);
//        String json = get(key, "");
//        if (StringUtils.isEmpty(json)) {
//            return null;
//        }
//        try {
//            return JSONObject.parseObject(json, clazz);
//        } catch (Exception e) {
//            return JSONObject.parseObject(json, clazz, Feature.DisableCircularReferenceDetect);
//        }
    }

//    public <T> List<T> getArr(String key, Class<T> clazz) {
//        String json = get(key, "");
//        if (StringUtils.isEmpty(json)) {
//            return null;
//        }
//        try {
//            return JSONArray.parseArray(json, clazz);
//        } catch (Exception e) {
//            return JSONArray.parseArray(json, clazz);
//        }
//    }


    public void remove(String key) {
        CacheKit.get().remove(key);
    }
//
//    public void saveUser(LoginBean user) {
//        set(LoginBean.class.getName(), JSONObject.toJSONString(user, FastjsonConfig.config));
//    }

    public LoginBean getUser() {
        return CacheKit.get().get(LoginBean.class.getName(), LoginBean.class);
//        String json = get(LoginBean.class.getName(), "");
//        if (StringUtils.isEmpty(json)) {
//            return null;
//        }
//        return JSONObject.parseObject(json, LoginBean.class, Feature.DisableCircularReferenceDetect);
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
