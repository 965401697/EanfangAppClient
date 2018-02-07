package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.MainHistoryDetailBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.MaintenanceHistoryDetailAdapter;
import net.eanfang.worker.ui.widget.MeintenanceDetailDialog;

import java.util.List;


/**
 * Created by wen on 2017/4/13.
 */

public class MaintenanceHistoryDetailActivity extends BaseActivity {

    public static final String TAG = MaintenanceHistoryDetailActivity.class.getSimpleName();
    private TextView tv_maintenance_circle;
    private LinearLayout ll_maintenance_circle;
    private TextView tv_add;
    private RecyclerView rcv_detail;
    private EditText et_contract;
    private EditText et_contract_phone;
    private TextView tv_commit;
    private EditText et_client_company_name;

    private MaintenanceHistoryDetailAdapter maintenanceDetailAdapter;
    private MainHistoryDetailBean mainHistoryDetailBean;
    private List<MainHistoryDetailBean.MaintainDetailsBean> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        initView();
        supprotToolbar();
        setTitle("维修保养");
        getData();

    }


    private void initView() {
        tv_maintenance_circle = (TextView) findViewById(R.id.tv_maintenance_circle);
        ll_maintenance_circle = (LinearLayout) findViewById(R.id.ll_maintenance_circle);
        tv_add = (TextView) findViewById(R.id.tv_add);
        rcv_detail = (RecyclerView) findViewById(R.id.rcv_detail);
        et_contract = (EditText) findViewById(R.id.et_contract);
        et_contract.setFocusable(false);
        et_contract_phone = (EditText) findViewById(R.id.et_contract_phone);
        et_contract_phone.setFocusable(false);
        et_client_company_name = (EditText) findViewById(R.id.et_client_company_name);
        et_client_company_name.setFocusable(false);

        tv_commit = (TextView) findViewById(R.id.tv_commit);
        tv_commit.setVisibility(View.GONE);


    }

    private void getData() {
        Long maintianId = getIntent().getLongExtra("maintianId", 0);
        EanfangHttp.get(NewApiService.MAINTENANCE_HISTORY_DETAIL)
                .params("id", maintianId)
                .execute(new EanfangCallback<MainHistoryDetailBean>(MaintenanceHistoryDetailActivity.this, false, MainHistoryDetailBean.class, (bean) -> {
                    mainHistoryDetailBean = bean;
                    initAdapter();
                }));
    }

    private void initAdapter() {
        mDataList = mainHistoryDetailBean.getMaintainDetails();
        maintenanceDetailAdapter = new MaintenanceHistoryDetailAdapter(mDataList);
        rcv_detail.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcv_detail.setLayoutManager(new LinearLayoutManager(this));
        rcv_detail.setAdapter(maintenanceDetailAdapter);

        maintenanceDetailAdapter.notifyDataSetChanged();
        maintenanceDetailAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.rl_item_detail:
                        MeintenanceDetailDialog dialog = new MeintenanceDetailDialog(MaintenanceHistoryDetailActivity.this, mDataList.get(position));
                        dialog.show();
                        break;
                    case R.id.tv_delete:
                        mainHistoryDetailBean.getMaintainDetails().remove(position);
                        maintenanceDetailAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        });
        tv_maintenance_circle.setText(GetConstDataUtils.getCycleList().get(mainHistoryDetailBean.getCycle()));
        et_contract.setText(mainHistoryDetailBean.getClientUserName());
        et_contract_phone.setText(mainHistoryDetailBean.getClientUserPhone());
        et_client_company_name.setText(mainHistoryDetailBean.getClientCompanyName());
    }

}