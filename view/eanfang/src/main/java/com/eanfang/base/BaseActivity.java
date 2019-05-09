package com.eanfang.base;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModel;

import com.eanfang.R;
import com.eanfang.kit.loading.LoadKit;
import com.eanfang.network.config.HttpConfig;
import com.eanfang.network.event.BaseActionEvent;
import com.eanfang.rds.base.IViewModelAction;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;


/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    //    private ProgressDialog loadingDialog;
    private Dialog loadingDialog;
    private ImageView iv_left;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModelEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setLeftBack(true);
        initView();
        initStyle();
    }

    /**
     * 初始化页面风格样式方法
     */
    protected abstract void initStyle();

    /**
     * 初始化viewModel
     *
     * @return ViewModel
     */
    protected abstract ViewModel initViewModel();

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
//        briefnessor.
        ((TextView) findViewById(R.id.tv_title)).setText(txt);
    }

    /**
     * 设置返回按钮
     *
     * @param visibility visibility
     * @param listener   listener
     */
    private void setLeftBack(boolean visibility, View.OnClickListener listener) {
        iv_left = findViewById(R.id.iv_left);
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

    protected List<ViewModel> initViewModelList() {
        return null;
    }

    /**
     * 初始化view方法
     */
    protected abstract void initView();

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
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoading();
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected boolean isFinishingOrDestroyed() {
        return isFinishing() || isDestroyed();
    }

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