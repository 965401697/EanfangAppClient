package com.eanfang.biz.rds.base;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.base.network.event.BaseActionEvent;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class BaseRepo<T extends BaseRemoteDataSource> {

    protected T remoteDataSource;

    public BaseRepo(T remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }


    public MutableLiveData<BaseActionEvent> onError(String methodName) {
        return onError(methodName, true);
    }

    /**
     * 监听网络消息回调
     *
     * @param methodName 需要监听的方法名（针对同时调用多个方法避免无法区分问题）
     * @param showError  是否显示错误消息 true显示默认  false不显示
     * @return MutableLiveData<BaseActionEvent>
     */
    public MutableLiveData<BaseActionEvent> onError(String methodName, boolean showError) {
        String methodNameTmp = new Exception().getStackTrace()[1].getMethodName();
        if (methodName.equals(methodNameTmp)) {
            return remoteDataSource.errorLiveData();
        }
        return new MutableLiveData<>();
    }
}