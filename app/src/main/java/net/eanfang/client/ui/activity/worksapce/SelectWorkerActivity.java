package net.eanfang.client.ui.activity.worksapce;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.eanfang.util.ConnectivityChangeReceiver;
import com.yaf.model.LoginBean;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.fragment.WorkerListFragment;
import net.eanfang.client.ui.model.SelectWorkerListBean;
import net.eanfang.client.ui.model.ToRepairBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  15:04
 * @email houzhongzhou@yeah.net
 * @desc 技师列表
 */

public class SelectWorkerActivity extends BaseActivity implements View.OnClickListener{
    private Context mContext = this;
    private WorkerListFragment AllFragment;
    private WorkerListFragment ServicedFragment;
    private WorkerListFragment CollectionFragment;
    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;
    /**
     * 顶部三个按钮
     */
    private TextView tv_all;
    private TextView tv_serviced;
    private TextView tv_collection;
    private ToRepairBean toRepairBean;

    public ToRepairBean getToRepairBean() {
        return toRepairBean;
    }


    private SelectWorkerListBean allSelectWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_worker);
        initViews();
        initData();
        fragmentManager = getSupportFragmentManager();
//        setTabSelection(0);
    }


    private void initViews() {
        tv_all = (TextView) findViewById(R.id.tv_all);
        tv_serviced = (TextView) findViewById(R.id.tv_serviced);
        tv_collection = (TextView) findViewById(R.id.tv_collection);

        tv_all.setOnClickListener(this);
        tv_serviced.setOnClickListener(this);
        tv_collection.setOnClickListener(this);

        setTitle("选择技师");
        setLeftBack();

    }


    private void initData() {
        Intent intent = getIntent();
        toRepairBean = (ToRepairBean) intent.getSerializableExtra("bean");
        LoginBean user = EanfangApplication.getApplication().getUser();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("bugOneUid", toRepairBean.getBugOneUid());
            jsonObject.put("city", toRepairBean.getCity());
            jsonObject.put("zone", toRepairBean.getArea());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        doHttpGetWorker(jsonObject.toString());
    }

    private void doHttpGetWorker(String jsonString) {
        EanfangHttp.get(ApiService.REPAIR_SELECT_WORKER)
                .tag(this)
                .params("json", jsonString)
                .execute(new EanfangCallback<SelectWorkerListBean>(this, true) {
                    @Override
                    public void onSuccess(SelectWorkerListBean bean) {
                        if (bean.getAll1().isEmpty()) {
                            onNoData("哎呀，暂时没有可用的技师啦");
                            finishSelf();
                            return;
                        }
                        allSelectWorker = bean;
                        setTabSelection(0);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                if (ConnectivityChangeReceiver.isNetConnected(getApplicationContext()) == true) {
                    setTabSelection(0);
                } else {
                    showToast("网络异常，请检查网络");
                }

                break;
            case R.id.tv_serviced:
                if (ConnectivityChangeReceiver.isNetConnected(getApplicationContext()) == true) {
                    setTabSelection(1);
                } else {
                    showToast("网络异常，请检查网络");
                }
                break;
            case R.id.tv_collection:
                if (ConnectivityChangeReceiver.isNetConnected(getApplicationContext()) == true) {
                    setTabSelection(2);
                } else {
                    showToast("网络异常，请检查网络");
                }
                break;
            default:
                break;
        }
    }


    /**
     * 根据传入的index参数来设置选中的tab页。
     */
    @SuppressLint("NewApi")
    private void setTabSelection(int index) {
        // 重置按钮
        resetBtn();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                tv_all.findViewById(R.id.tv_all)
                        .setBackground(getResources().getDrawable(R.drawable.select_worker));
                ((TextView) tv_all.findViewById(R.id.tv_all))
                        .setTextColor(getResources().getColor(R.color.client));
                if (AllFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    AllFragment = WorkerListFragment.getInstance(allSelectWorker.getAll1());
                    transaction.add(R.id.fl_content, AllFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(AllFragment);
                }
                break;
            case 1:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                tv_serviced.findViewById(R.id.tv_serviced)
                        .setBackground(getResources().getDrawable(R.drawable.select_worker));
                ((TextView) tv_serviced.findViewById(R.id.tv_serviced))
                        .setTextColor(getResources().getColor(R.color.client));
                if (ServicedFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    ServicedFragment = WorkerListFragment.getInstance(allSelectWorker.getAll2());
                    transaction.add(R.id.fl_content, ServicedFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(ServicedFragment);
                }
                break;
            case 2:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                tv_collection.findViewById(R.id.tv_collection)
                        .setBackground(getResources().getDrawable(R.drawable.select_worker));
                ((TextView) tv_collection.findViewById(R.id.tv_collection))
                        .setTextColor(getResources().getColor(R.color.client));
                if (CollectionFragment == null) {
                    // 如果NewsFragment为空，则创建一个并添加到界面上
                    CollectionFragment = WorkerListFragment.getInstance(allSelectWorker.getAll3());
                    transaction.add(R.id.fl_content, CollectionFragment);
                } else {
                    // 如果NewsFragment不为空，则直接将它显示出来
                    transaction.show(CollectionFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void resetBtn() {
        tv_all.findViewById(R.id.tv_all)
                .setBackground(getResources().getDrawable(R.drawable.unselect_worker));
        ((TextView) tv_all.findViewById(R.id.tv_all))
                .setTextColor(getResources().getColor(R.color.font_color));

        tv_serviced.findViewById(R.id.tv_serviced)
                .setBackground(getResources().getDrawable(R.drawable.unselect_worker));
        ((TextView) tv_serviced.findViewById(R.id.tv_serviced))
                .setTextColor(getResources().getColor(R.color.font_color));

        tv_collection.findViewById(R.id.tv_collection)
                .setBackground(getResources().getDrawable(R.drawable.unselect_worker));
        ((TextView) tv_collection.findViewById(R.id.tv_collection))
                .setTextColor(getResources().getColor(R.color.font_color));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    @SuppressLint("NewApi")
    private void hideFragments(FragmentTransaction transaction) {
        if (AllFragment != null) {
            transaction.hide(AllFragment);
        }
        if (ServicedFragment != null) {
            transaction.hide(ServicedFragment);
        }
        if (CollectionFragment != null) {
            transaction.hide(CollectionFragment);
        }
    }
}
