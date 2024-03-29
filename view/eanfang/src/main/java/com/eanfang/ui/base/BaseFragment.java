package com.eanfang.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.eanfang.R;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.util.ToastUtil;
import com.luck.picture.lib.config.PictureConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


/**
 * Created by wen on 2017/3/16.
 *
 * @http://blog.csdn.net/q649381130/article/details/51534479
 */
@Deprecated
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
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            return mRootView;

        }
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

        Log.e("zzw", "BaseFragment 执行onCreateView = " + mIsPrepare);


        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsPrepare = true;
        onVisibleToUser();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pictureSelect(requestCode,resultCode,data);
    }

    /**
     * 图片选择
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void pictureSelect(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                SDKManager.getPicture().create(this).onActivityResult(requestCode, resultCode, data);
            }
        }
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
        Log.e("zzw", "BaseFragment setUserVisibleHint = " + isVisibleToUser);
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
        Log.e("zzw", "BaseFragment onVisibleToUser = " + mIsPrepare);
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
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroyView();
    }

    public void setTitle(int id) {
        ((TextView) findViewById(R.id.tv_title)).setText(id);
    }

    public void setTitle(String id) {
        ((TextView) findViewById(R.id.tv_title)).setText(id);
    }

    public void setLeftVisible(int visible) {
        findViewById(R.id.iv_left).setVisibility(visible);
    }

    /**
     * 判断技师是否认证
     *
     * @return
     */
    public boolean workerApprove() {

//        if (EanfangApplication.get().getUser().getAccount().getAccountExtInfo() == null) {
//
//            WorkerInfoBean workerInfoBean = new WorkerInfoBean();
//
//            workerInfoBean.setAccId(EanfangApplication.get().getAccId());
//            workerInfoBean.setUserId(EanfangApplication.get().getUserId());
//
//            new TrueFalseDialog(getActivity(), "提示", "你还没有通过技师认证，认证通过后才能使用此功能？", () -> {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("workerInfoBean", workerInfoBean);
//                Intent intent = new Intent("net.eanfang.worker.action.AUTH");
//                intent.putExtras(bundle);
//                getActivity().startActivity(intent);
//            }).showDialog();
//            return false;
//        } else {
//            return true;
//        }

        return true;
    }

    public void headViewSize(CircleImageView circleImageView, int size) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) circleImageView.getLayoutParams();
        layoutParams.width = size;
        layoutParams.height = size;
    }

}
