package com.eanfang.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.R;
import com.eanfang.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;


/**
 * Created by wen on 2017/3/16.
 *
 * @http://blog.csdn.net/q649381130/article/details/51534479
 */

public abstract class BaseFragment extends Fragment implements IBase {
    /**
     * 贴附的activity
     */
    protected BaseActivity mActivity;

    /**
     * 根view
     */
    protected View mRootView;

    /**
     * 是否对用户可见
     */
    protected boolean mIsVisible;
    /**
     * 是否加载完成
     * 当执行完oncreatview,View的初始化方法后方法后即为true
     */
    protected boolean mIsPrepare;


    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayoutResouceId(), container, false);
        ButterKnife.bind(this, mRootView);
        initData(getArguments());
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        initView();

        mIsPrepare = true;

//        onLazyLoad();

        setListener();
        return mRootView;
    }


    @Override
    public void finishSelf() {
        getActivity().finish();
    }

    @Override
    public boolean isFinishing() {
        return false;
    }

    @Override
    public void showToast(int res) {
        ToastUtil.get().showToast(getActivity(), res);
    }

    @Override
    public void showToast(String message) {
        ToastUtil.get().showToast(getActivity(), message);

    }

    /**
     * 设置根布局资源id
     *
     * @return
     * @author 漆可
     * @date 2016-5-26 下午3:57:09
     */
    protected abstract int setLayoutResouceId();

    /**
     * 初始化数据
     *
     * @param arguments 接收到的从其他地方传递过来的参数
     * @author 漆可
     * @date 2016-5-26 下午3:57:48
     */
    protected abstract void initData(Bundle arguments);

    /**
     * 初始化View
     *
     * @author 漆可
     * @date 2016-5-26 下午3:58:49
     */
    protected abstract void initView();

    /**
     * 设置监听事件
     *
     * @author 漆可
     * @date 2016-5-26 下午3:59:36
     */
    protected abstract void setListener();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        this.mIsVisible = isVisibleToUser;

        if (isVisibleToUser) {
            onVisibleToUser();
        }
    }

    /**
     * 用户可见时执行的操作
     *
     * @author 漆可
     * @date 2016-5-26 下午4:09:39
     */
    protected void onVisibleToUser() {
        if (mIsPrepare && mIsVisible) {
            onLazyLoad();
        }
    }

    /**
     * 懒加载，仅当用户可见切view初始化结束后才会执行
     *
     * @author 漆可
     * @date 2016-5-26 下午4:10:20
     */
    protected void onLazyLoad() {

    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(int id) {
        if (mRootView == null) {
            return null;
        }

        return (T) mRootView.findViewById(id);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageReceived(BaseEvent baseEvent) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) getActivity();
    }

    @Override
    public void onDestroyView() {
        this.mActivity = null;
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    public void setTitle(int id) {
        ((TextView) findViewById(R.id.tv_title)).setText(id);
    }

    public void setTitle(String id) {
        ((TextView) findViewById(R.id.tv_title)).setText(id);
    }

    public void setLeftVisible(int visible) {
        ((ImageView) findViewById(R.id.iv_left)).setVisibility(visible);
    }

}
