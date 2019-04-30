package com.eanfang.base;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class BaseApplication extends MultiDexApplication {

    private static Application appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }

    public static Application getAppContext() {
        return appContext;
    }

}