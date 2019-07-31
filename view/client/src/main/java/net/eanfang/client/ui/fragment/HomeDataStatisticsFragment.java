package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.biz.model.bean.datastatistics.HomeDatastisticeBean;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JsonUtils;
import com.eanfang.biz.model.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.worksapce.datastatistics.DataStatisticsActivity;
import net.eanfang.client.ui.adapter.HomeDataAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guanluocang
 * @data 2019/1/23
 * @description 首页统计数据
 */
public class HomeDataStatisticsFragment extends BaseFragment {

    private RecyclerView rvData;
    private String mTitle;
    private int mType;

    private List<HomeDatastisticeBean.GroupBean> clientDataList = new ArrayList<>();
    private HomeDataAdapter homeDataAdapter;

    //数量
    TextView tvReapirTotal;

    public static HomeDataStatisticsFragment getInstance(String title, int type) {
        HomeDataStatisticsFragment homeDataStatisticsFragment = new HomeDataStatisticsFragment();
        homeDataStatisticsFragment.mTitle = title;
        homeDataStatisticsFragment.mType = type;
        return homeDataStatisticsFragment;
    }

    public String getmTitle() {
        return mTitle;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_home_data_statistics;
    }

    @Override
    protected void initData(Bundle arguments) {
    }

    @Override
    protected void initView() {
        rvData = findViewById(R.id.rv_data);
        tvReapirTotal = findViewById(R.id.tv_allcount);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rvData.setLayoutManager(gridLayoutManager);
        homeDataAdapter = new HomeDataAdapter(R.layout.layout_home_data);
        rvData.setAdapter(homeDataAdapter);
        homeDataAdapter.bindToRecyclerView(rvData);
        rvData.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), DataStatisticsActivity.class));
            }
        });
    }


    /**
     * 获取统计数据
     */
    private void doHttpDatastatistics() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("companyId", ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyEntity().getOrgId() + "");
        EanfangHttp.post(NewApiService.HOME_DATASTASTISTICS)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<HomeDatastisticeBean>(getActivity(), false, HomeDatastisticeBean.class, bean -> {
                    initDatastatisticsData(bean);
                }));
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        doHttpDatastatistics();
    }

    /**
     * 填充
     */
    private void initDatastatisticsData(HomeDatastisticeBean bean) {
        clientDataList = bean.getGroup();
        // 维修
        if (mType == 1) {
            tvReapirTotal.setText("当日维修总数：" + bean.getAll() + "");
            homeDataAdapter.setNewData(clientDataList);
            //报装
        } else if (mType == 2) {
            rvData.setVisibility(View.GONE);
            tvReapirTotal.setText("当日报装总数：" + bean.getInstall().getNum() + "");
            // 设计
        } else {
            rvData.setVisibility(View.GONE);
            tvReapirTotal.setText("当日设计总数：" + bean.getDesign().getNum() + "");
        }
    }

    @Override
    protected void setListener() {

    }
}
