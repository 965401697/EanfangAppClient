package com.eanfang.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
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
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;


/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public abstract class BaseActivity extends RxAppCompatActivity {

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

    @Deprecated
    public final static ArrayList<Activity> transactionActivities = new ArrayList<>();

    /**
     * 重写方法时，请先进行 dataBinding初始化 之后再调用 supper
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //始终竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        // BaseApplication.get().addActivity(this);
        initViewModelEvent();
        initView();
        initStyle();


        loadService = LoadSir.getDefault().register(this, this::onNetReload);
        initLoadSir();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoading();
        // BaseApplication.get().closeActivity(this);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
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
            findViewById(R.id.titles_bar).setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryC));
        } else {
            findViewById(R.id.titles_bar).setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryW));
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
    protected void initView() {
        setLeftBack(true);
        setRightBack(false);
    }

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
    @Override
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
            iv_left.setOnClickListener(v -> finishWithResultOk());
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
    private void setRightBack(boolean visibility, View.OnClickListener listener) {
        TextView iv_right = findViewById(R.id.tv_right);
        if (visibility) {
            iv_right.setVisibility(View.VISIBLE);
        } else {
            iv_right.setVisibility(View.GONE);
        }
        if (listener != null) {
            iv_right.setOnClickListener(listener);
        } else {
            iv_right.setOnClickListener(v -> finishWithResultOk());
        }
    }

    /**
     * 设置返回按钮
     *
     * @param listener listener
     */
    public void setRightBack(View.OnClickListener listener) {
        setLeftBack(true, listener);
    }

    /**
     * 设置返回按钮
     *
     * @param visibility 是否可见
     */
    public void setRightBack(boolean visibility) {
        setLeftBack(visibility, null);
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
                                finish();
                                break;
                            }
                            case BaseActionEvent.FINISH_WITH_RESULT_OK: {
                                setResult(RESULT_OK);
                                finish();
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
                                finish();
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
            loadingDialog = LoadKit.dialog(this, message);
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void finishWithResultOk() {
        setResult(RESULT_OK);
        finish();
    }

    protected BaseActivity getContext() {
        return BaseActivity.this;
    }

    protected void startActivity(Class cl) {
        startActivity(new Intent(this, cl));
    }

    public void startActivityForResult(Class cl, int requestCode) {
        startActivityForResult(new Intent(this, cl), requestCode);
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


    /**
     * viewpager Adapter
     */
    protected class MyPagerAdapter extends FragmentPagerAdapter {
        @Getter
        private String[] titles;
        @Getter
        private ArrayList<Fragment> fragments;

        public MyPagerAdapter(FragmentManager fm, String[] mTitles) {
            super(fm);
            fragments = new ArrayList<>();
            this.titles = mTitles;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }


}