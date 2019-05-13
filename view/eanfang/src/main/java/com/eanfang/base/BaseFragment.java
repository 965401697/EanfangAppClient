package com.eanfang.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

import com.eanfang.kit.loading.LoadKit;
import com.eanfang.kit.loading.callback.EmptyCallback;
import com.eanfang.kit.loading.callback.ErrorCallback;
import com.eanfang.kit.loading.callback.NotFoundCallback;
import com.eanfang.kit.loading.callback.PermissionCallback;
import com.eanfang.kit.loading.callback.TimeoutCallback;
import com.eanfang.network.config.HttpConfig;
import com.eanfang.network.event.BaseActionEvent;
import com.eanfang.rds.base.IViewModelAction;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public abstract class BaseFragment extends RxFragment {

    protected LoadService loadService;
    //是否启用服务器错误回调页
    protected boolean errorBack = false;
    //是否启用空数据回调页
    protected boolean emptyBack = false;
    //是否启用超时回调页
    protected boolean timeoutBack = false;
    //是否启用无权限回调页
    protected boolean permissionBack = false;
    //是否启用未找到回调页
    private boolean notFoundBack = false;
    private Dialog loadingDialog;
    protected FragmentActivity mActivity;
    protected View mRootView;
    protected boolean mIsVisible;
    protected boolean mIsPrepare;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = initView(inflater, container);
        loadService = LoadSir.getDefault().register(mRootView, this::onNetReload);
        mIsPrepare = true;
        return loadService.getLoadLayout();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLoadSir();
    }

    /**
     * 初始化loadSir默认打开页面
     */
    protected void initLoadSir() {
        loadService.showSuccess();
    }

    /**
     * 加载失败点击重试 的方法
     */
    protected void onNetReload(View view) {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.mIsVisible = isVisibleToUser;
        if (isVisibleToUser) {
            onVisibleToUser();
        }
    }

    protected void onVisibleToUser() {
        if (mIsPrepare && mIsVisible) {
            onLazyLoad();
        }
    }

    protected void onLazyLoad() {
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(int id) {
        if (mRootView == null) {
            return null;
        }
        return (T) mRootView.findViewById(id);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModelEvent();

    }

    protected abstract ViewModel initViewModel();

    protected List<ViewModel> initViewModelList() {
        return null;
    }

    /**
     * 初始化view方法
     *
     * @param inflater
     * @param container
     */
    protected abstract View initView(LayoutInflater inflater, ViewGroup container);

    private void initViewModelEvent() {
        List<ViewModel> viewModelList = initViewModelList();
        if (viewModelList != null && viewModelList.size() > 0) {
            observeEvent(viewModelList);
        } else {
            ViewModel viewModel = initViewModel();
            if (viewModel != null) {
                List<ViewModel> modelList = new ArrayList<>();
                modelList.add(viewModel);
                observeEvent(modelList);
            }
        }
    }

    private void observeEvent(List<ViewModel> viewModelList) {
        for (ViewModel viewModel : viewModelList) {
            if (viewModel instanceof IViewModelAction) {
                IViewModelAction viewModelAction = (IViewModelAction) viewModel;
                viewModelAction.getActionLiveData().observe(this, baseActionEvent -> {
                    if (baseActionEvent != null) {
                        switch (baseActionEvent.getAction()) {
                            case BaseActionEvent.SHOW_LOADING_DIALOG: {
                                startLoading(baseActionEvent.getMessage());
                                break;
                            }
                            case BaseActionEvent.DISMISS_LOADING_DIALOG: {
                                dismissLoading();
                                break;
                            }
                            case BaseActionEvent.SHOW_TOAST: {
                                showToast(baseActionEvent.getMessage());
                                //释放消息  不继续传递
                                viewModelAction.getActionLiveData().setValue(new BaseActionEvent(BaseActionEvent.DEFAULT));
                                break;
                            }
                            case BaseActionEvent.SUCCESS: {
                                loadService.showSuccess();
                                break;
                            }
                            case BaseActionEvent.EMPTY_DATA: {
                                if (emptyBack) {
                                    loadService.showCallback(EmptyCallback.class);
                                }
                                break;
                            }
                            case BaseActionEvent.PERMISSION_ERROR: {
                                if (permissionBack) {
                                    loadService.showCallback(PermissionCallback.class);
                                }
                                break;
                            }
                            case BaseActionEvent.NOT_FOUND: {
                                if (notFoundBack) {
                                    loadService.showCallback(NotFoundCallback.class);
                                }
                                break;
                            }
                            case BaseActionEvent.TIME_OUT: {
                                if (timeoutBack) {
                                    loadService.showCallback(TimeoutCallback.class);
                                }
                                break;
                            }
                            case BaseActionEvent.SERVER_ERROR: {
                                if (errorBack) {
                                    loadService.showCallback(ErrorCallback.class);
                                }
                                break;
                            }
                        }
                    }
                });
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissLoading();
    }

    protected void startLoading() {
        startLoading(null);
    }

    private void startLoading(String message) {
        if (loadingDialog == null) {
            loadingDialog = LoadKit.dialog(getContext(), message);
            loadingDialog.setCancelable(false);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        loadingDialog.show();
    }

    private void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            LoadKit.closeDialog(loadingDialog);
        }
    }

    protected void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int resId) {
        Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    protected int getApp() {
        return HttpConfig.get().getApp();
    }

    protected boolean isClient() {
        return getApp() == 1;
    }

}