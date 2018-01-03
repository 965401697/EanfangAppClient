package net.eanfang.client.ui.activity.worksapce;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.fragment.CompanyListFragment;
import net.eanfang.client.ui.model.InstallOrderConfirmBean;
import net.eanfang.client.ui.model.SelectCompanyBean;
import net.eanfang.client.ui.model.SelectWorkerBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  19:28
 * @email houzhongzhou@yeah.net
 * @desc 公司列表
 */
// TODO: 2017/12/21 使用待定
public class SelectCompanyActivity extends BaseActivity implements View.OnClickListener {
    private CompanyListFragment AllFragment;
    private CompanyListFragment ServicedFragment;
    private CompanyListFragment CollectionFragment;
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

    private InstallOrderConfirmBean installOrderConfirmBean;

    public InstallOrderConfirmBean getInstallOrderConfirmBean() {
        return installOrderConfirmBean;
    }


    private SelectCompanyBean allSelectWorker;
    // 公司对比选中的公司列表
    private List<SelectCompanyBean.All1Bean> compareList = new ArrayList<>();

    public List<SelectCompanyBean.All1Bean> getCompareList() {
        return compareList;
    }

    private ArrayList<String> companyUidList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_worker);
        getData();
        initViews();
        initData();
        fragmentManager = getSupportFragmentManager();

        setTitle("选择公司");
        setLeftBack();
        setRightTitle("对比");
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (compareList.size() == 0) {
                    showToast("还没有公司添加到公司对比");
                    return;
                }
                Intent intent = new Intent(SelectCompanyActivity.this, CompanyCompareActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", (Serializable) compareList);
                intent.putExtras(bundle);
                startActivityForResult(intent, 16374);
            }
        });
    }

    private void initData() {
        SelectWorkerBean bean = new SelectWorkerBean();
//        bean.setBugOneUid(installOrderConfirmBean.getBugone());
//        bean.setCity(installOrderConfirmBean.getCity());
//        bean.setZone(installOrderConfirmBean.getZone());
        Gson gson = new Gson();
        String json = gson.toJson(bean);
        EanfangHttp.get(ApiService.SELECT_COMPANY)
                .tag(this)
                .params("json", json.toString())
                .execute(new EanfangCallback<SelectCompanyBean>(SelectCompanyActivity.this, false) {
                    @Override
                    public void onSuccess(SelectCompanyBean bean) {
                        super.onSuccess(bean);
                        allSelectWorker = bean;
                        setTabSelection(0);
                    }
                });
    }

    private void getData() {
        Intent intent = getIntent();
        installOrderConfirmBean = (InstallOrderConfirmBean) intent.getSerializableExtra("bean");
    }

    private void initViews() {

        tv_all = (TextView) findViewById(R.id.tv_all);
        tv_serviced = (TextView) findViewById(R.id.tv_serviced);
        tv_collection = (TextView) findViewById(R.id.tv_collection);

        tv_all.setOnClickListener(this);
        tv_serviced.setOnClickListener(this);
        tv_collection.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                setTabSelection(0);
                break;
            case R.id.tv_serviced:
                setTabSelection(1);
                break;
            case R.id.tv_collection:
                setTabSelection(2);
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
                    AllFragment = CompanyListFragment.getInstance(allSelectWorker.getAll1());
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
                    ServicedFragment = CompanyListFragment.getInstance(allSelectWorker.getAll2());
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
                    CollectionFragment = CompanyListFragment.getInstance(allSelectWorker.getAll3());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 32989) {
            companyUidList = data.getStringArrayListExtra("deleteList");
            for (int i = 0; i < companyUidList.size(); i++) {
                for (int j = 0; j < compareList.size(); j++) {
                    if (compareList.get(j).getCompanyuid().equals(companyUidList.get(i))) {
                        compareList.remove(j);
                    }
                }
            }
        }
    }
}
