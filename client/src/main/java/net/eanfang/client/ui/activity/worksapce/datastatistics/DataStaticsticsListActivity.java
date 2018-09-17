package net.eanfang.client.ui.activity.worksapce.datastatistics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.datastatistics.HomeDatastisticeBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.HomeDataAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/5/10  17:06
 * @decision 统计数据列表
 */

public class DataStaticsticsListActivity extends BaseActivity {

    @BindView(R.id.rv_reapir_data)
    RecyclerView rvData;
    private HomeDataAdapter homeDataAdapter;
    private List<HomeDatastisticeBean.GroupBean> clientDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_staticstics_detail);
        ButterKnife.bind(this);
        initView();
        doHttpDatastatistics();
    }


    private void initView() {
        setLeftBack();
        setTitle("统计数据");
        //设置布局样式
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        rvData.setLayoutManager(gridLayoutManager);
        findViewById(R.id.ll_repair_datasticstics).setOnClickListener(v -> startActivity(new Intent(DataStaticsticsListActivity.this, DataStatisticsActivity.class)));

    }

    /**
     * 获取统计数据
     */
    private void doHttpDatastatistics() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("companyId", EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgId() + "");
        EanfangHttp.post(NewApiService.HOME_DATASTASTISTICS)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<HomeDatastisticeBean>(DataStaticsticsListActivity.this, false, HomeDatastisticeBean.class, bean -> {
                    initDatastatisticsData(bean);
                }));
    }

    private void initDatastatisticsData(HomeDatastisticeBean bean) {
        clientDataList = bean.getGroup();
        homeDataAdapter = new HomeDataAdapter(R.layout.layout_data_item_list);
        rvData.setAdapter(homeDataAdapter);
        homeDataAdapter.bindToRecyclerView(rvData);
        homeDataAdapter.setNewData(clientDataList);
    }
}
