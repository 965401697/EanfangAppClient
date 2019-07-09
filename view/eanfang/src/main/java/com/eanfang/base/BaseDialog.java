package com.eanfang.base;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

import com.eanfang.R;
import com.eanfang.base.kit.loading.LoadKit;
import com.eanfang.base.kit.loading.callback.EmptyCallback;
import com.eanfang.base.kit.loading.callback.ErrorCallback;
import com.eanfang.base.kit.loading.callback.NotFoundCallback;
import com.eanfang.base.kit.loading.callback.PermissionCallback;
import com.eanfang.base.kit.loading.callback.TimeoutCallback;
import com.eanfang.base.network.config.HttpConfig;
import com.eanfang.base.network.event.BaseActionEvent;
import com.eanfang.biz.rds.base.IViewModelAction;
import com.eanfang.sys.activity.LoginActivity;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.trello.rxlifecycle2.components.support.RxDialogFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public abstract class BaseDialog extends RxDialogFragment {

    protected View mRootView = null;
    protected FragmentActivity mActivity;

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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = initView(inflater, container);
        loadService = LoadSir.getDefault().register(mRootView, this::onNetReload);
        return loadService.getLoadLayout();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModelEvent();
        setLeftBack(true);
        setRightClick(false);
        mActivity = getActivity();
        initLoadSir();
        initStyle();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissLoading();
        // BaseApplication.get().closeActivity(this);
    }

    protected <T extends View> T findViewById(int id) {
        if (mRootView == null) {
            return null;
        }
        return (T) mRootView.findViewById(id);
    }

    //---------------------------------------------------init ----------------------------------------------------------------------------

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

    /**
     * 初始化页面风格样式方法
     */
    protected void initStyle() {
        if (isClient()) {
            findViewById(R.id.titles_bar).setBackgroundColor(ContextCompat.getColor(mActivity, R.color.colorPrimaryC));
        } else {
            findViewById(R.id.titles_bar).setBackgroundColor(ContextCompat.getColor(mActivity, R.color.colorPrimaryW));
        }
    }

    /**
     * 初始化viewModel
     *
     * @return ViewModel
     */
    protected abstract ViewModel initViewModel();


    protected List<ViewModel> initViewModelList() {
        return null;
    }

    /**
     * 初始化view方法
     */
    protected abstract View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

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

    //------------------------------------------------------------header bar-----------------------------------------------------

    /**
     * 设置标题
     *
     * @param id 文本id
     */
    public void setTitle(int id) {
        ((TextView) findViewById(R.id.tv_title)).setText(id);
    }

    /**
     * 设置标题
     *
     * @param txt 文字
     */
    public void setTitle(String txt) {
        ((TextView) findViewById(R.id.tv_title)).setText(txt);
    }

    /**
     * 设置返回按钮
     *
     * @param visibility visibility
     * @param listener   listener
     */
    private void setLeftBack(boolean visibility, View.OnClickListener listener) {
        ImageView iv_left = findViewById(R.id.iv_left);
        if (visibility) {
            iv_left.setVisibility(View.VISIBLE);
        } else {
            iv_left.setVisibility(View.GONE);
        }
        if (listener != null) {
            iv_left.setOnClickListener(listener);
        } else {
            iv_left.setOnClickListener(v -> dismiss());
        }
    }

    /**
     * 设置返回按钮
     *
     * @param listener listener
     */
    public void setLeftBack(View.OnClickListener listener) {
        setLeftBack(true, listener);
    }

    /**
     * 设置返回按钮
     *
     * @param visibility 是否可见
     */
    public void setLeftBack(boolean visibility) {
        setLeftBack(visibility, null);
    }

    /**
     * 设置返回按钮
     *
     * @param visibility visibility
     * @param listener   listener
     */
    private void setRightClick(boolean visibility, View.OnClickListener listener) {
        TextView iv_right = findViewById(R.id.tv_right);
        if (visibility) {
            iv_right.setVisibility(View.VISIBLE);
        } else {
            iv_right.setVisibility(View.GONE);
        }
        if (listener != null) {
            iv_right.setOnClickListener(listener);
        } else {
            iv_right.setOnClickListener(v -> dismiss());
        }
    }

    /**
     * 设置返回按钮
     *
     * @param listener listener
     */
    public void setRightClick(View.OnClickListener listener) {
        setRightClick(true, listener);
    }

    /**
     * 设置返回按钮
     *
     * @param visibility 是否可见
     */
    public void setRightClick(boolean visibility) {
        setRightClick(visibility, null);
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
                            case BaseActionEvent.FINISH: {
                                dismiss();
                                break;
                            }
                            case BaseActionEvent.FINISH_WITH_RESULT_OK: {
                                dismiss();
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
                            case BaseActionEvent.TOKEN_ERROR: {
                                dismiss();
                                startActivity(LoginActivity.class);
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


    protected void startLoading() {
        startLoading(null);
    }

    protected void startLoading(String message) {
        if (loadingDialog == null) {
            loadingDialog = LoadKit.dialog(mActivity, message);
            loadingDialog.setCancelable(false);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        loadingDialog.show();
    }

    protected void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            LoadKit.closeDialog(loadingDialog);
        }
    }

    protected void showToast(String message) {
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
    }

    protected void startActivity(Class cl) {
        startActivity(new Intent(mActivity, cl));
    }

    public void startActivityForResult(Class cl, int requestCode) {
        startActivityForResult(new Intent(mActivity, cl), requestCode);
    }

//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
//    protected boolean isFinishingOrDestroyed() {
//        return isFinishing() || isDestroyed();
//    }

    protected int getApp() {
        return HttpConfig.get().getApp();
    }

    protected boolean isClient() {
        return getApp() == 0;
    }

}