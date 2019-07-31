package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.MainHistoryDetailBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.MaintenanceHistoryDetailAdapter;
import net.eanfang.worker.ui.widget.MeintenanceDetailDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by wen on 2017/4/13.
 */

public class MaintenanceHistoryDetailActivity extends BaseActivity {

    public static final String TAG = MaintenanceHistoryDetailActivity.class.getSimpleName();
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    private TextView tv_maintenance_circle;
    private LinearLayout ll_maintenance_circle;
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
        setContentView(R.layout.activity_maintenance);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        setTitle("维修保养");
        getData();

    }


    private void initView() {
        tv_maintenance_circle = findViewById(R.id.tv_maintenance_circle);
        ll_maintenance_circle = findViewById(R.id.ll_maintenance_circle);
        rcv_detail = findViewById(R.id.rcv_detail);
        et_contract = findViewById(R.id.et_contract);
        et_contract.setFocusable(false);
        et_contract_phone = findViewById(R.id.et_contract_phone);
        et_contract_phone.setFocusable(false);
        et_client_company_name = findViewById(R.id.et_client_company_name);
        et_client_company_name.setFocusable(false);
        tv_commit = findViewById(R.id.tv_commit);
        tv_commit.setVisibility(View.GONE);
        ivLeft.setOnClickListener((v) -> finishSelf());


    }

    private void getData() {
        Long maintianId = getIntent().getLongExtra("maintianId", 0);
        EanfangHttp.get(NewApiService.MAINTENANCE_HISTORY_DETAIL)
                .params("id", maintianId)
                .execute(new EanfangCallback<MainHistoryDetailBean>(MaintenanceHistoryDetailActivity.this, true, MainHistoryDetailBean.class, (bean) -> {
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
        maintenanceDetailAdapter.setOnItemChildClickListener((adapter, view, position) -> {
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
        });
        tv_maintenance_circle.setText(GetConstDataUtils.getCycleList().get(mainHistoryDetailBean.getCycle()));
        et_contract.setText(mainHistoryDetailBean.getClientUserName());
        et_contract_phone.setText(mainHistoryDetailBean.getClientUserPhone());
        et_client_company_name.setText(mainHistoryDetailBean.getClientCompanyName());
    }

}
