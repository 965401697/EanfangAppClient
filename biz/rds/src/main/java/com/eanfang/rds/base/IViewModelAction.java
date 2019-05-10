package com.eanfang.rds.base;


import androidx.lifecycle.MutableLiveData;

import com.eanfang.network.event.BaseActionEvent;


/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public interface IViewModelAction {

    void startLoading();

    void startLoading(String message);

    void dismissLoading();

    void showToast(String message);

    void finish();

    void finishWithResultOk();

    MutableLiveData<BaseActionEvent> getActionLiveData();

}