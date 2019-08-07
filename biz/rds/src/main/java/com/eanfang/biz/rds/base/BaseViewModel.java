package com.eanfang.biz.rds.base;


import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.network.event.BaseActionEvent;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
@Accessors(chain = true)
public class BaseViewModel extends ViewModel implements IViewModelAction {

    private MutableLiveData<BaseActionEvent> actionLiveData;
    private MutableLiveData<BaseActionEvent> errorLiveData;
    @Getter
    @Setter
    private boolean showError = true;

    protected LifecycleOwner lifecycleOwner;
    /**
     * 是否显示网络请求的loading加载框 默认显示 可以在网络请求前加上 showLoading=false 网络请求执行完会自动恢复成 true
     */
    @Setter
    protected boolean showLoading = true;

    public BaseViewModel() {
        actionLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
    }

    @Override
    public void startLoading() {
        startLoading(null);
    }

    @Override
    public void startLoading(String message) {
        if (!showLoading) {
            return;
        }
        BaseActionEvent baseActionEvent = new BaseActionEvent(BaseActionEvent.SHOW_LOADING_DIALOG);
        baseActionEvent.setMessage(message);
        actionLiveData.setValue(baseActionEvent);
    }

    @Override
    public void dismissLoading() {
        if (!showLoading) {
            showLoading = true;
            return;
        }
        actionLiveData.setValue(new BaseActionEvent(BaseActionEvent.DISMISS_LOADING_DIALOG));
    }

    @Override
    public void showToast(String message) {
        BaseActionEvent baseActionEvent = new BaseActionEvent(BaseActionEvent.SHOW_TOAST);
        baseActionEvent.setMessage(message);
        actionLiveData.setValue(baseActionEvent);
    }

    @Override
    public void finish() {
        actionLiveData.setValue(new BaseActionEvent(BaseActionEvent.FINISH));
    }

    @Override
    public void finishWithResultOk() {
        actionLiveData.setValue(new BaseActionEvent(BaseActionEvent.FINISH_WITH_RESULT_OK));
    }

    @Override
    public MutableLiveData<BaseActionEvent> getActionLiveData() {
        return actionLiveData;
    }

    @Override
    public MutableLiveData<BaseActionEvent> getErrorLiveData() {
        return errorLiveData;
    }


    void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

}