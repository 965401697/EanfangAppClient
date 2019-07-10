package net.eanfang.client.ui.activity.worksapce.datastatistics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.datastatistics.HomeDatastisticeBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.adapter.HomeDataAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
    @BindView(R.id.tv_reapir_total)
    TextView tvReapirTotal;
    @BindView(R.id.tv_install_total)
    TextView tvInstallTotal;
    @BindView(R.id.tv_desitn_total)
    TextView tvDesitnTotal;
    private HomeDataAdapter homeDataAdapter;
    private List<HomeDatastisticeBean.GroupBean> clientDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_data_staticstics_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        doHttpDatastatistics();
    }


    private void initView() {
        setLeftBack();
        setTitle("统计数据");
        //设置布局样式
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvData.setLayoutManager(gridLayoutManager);
        findViewById(R.id.ll_repair_datasticstics).setOnClickListener(v -> startActivity(new Intent(DataStaticsticsListActivity.this, DataStatisticsActivity.class)));
        findViewById(R.id.ll_repair_install).setOnClickListener(v -> startActivity(new Intent(DataStaticsticsListActivity.this, DataInstallActivity.class)));
        findViewById(R.id.ll_design).setOnClickListener(v -> startActivity(new Intent(DataStaticsticsListActivity.this, DataDesignActivity.class)));
        rvData.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(DataStaticsticsListActivity.this, DataStatisticsActivity.class));
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
                .execute(new EanfangCallback<HomeDatastisticeBean>(DataStaticsticsListActivity.this, false, HomeDatastisticeBean.class, bean -> {
                    initDatastatisticsData(bean);
                }));
    }

    private void initDatastatisticsData(HomeDatastisticeBean bean) {
        tvReapirTotal.setText(bean.getAll() + "");
        tvInstallTotal.setText(bean.getInstall().getNum() + "");
        tvDesitnTotal.setText(bean.getDesign().getNum() + "");
        clientDataList = bean.getGroup();
        homeDataAdapter = new HomeDataAdapter(R.layout.layout_data_item_list);
        rvData.setAdapter(homeDataAdapter);
        homeDataAdapter.bindToRecyclerView(rvData);
        homeDataAdapter.setNewData(clientDataList);
    }
}
