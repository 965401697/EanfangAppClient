package net.eanfang.client.ui.activity.worksapce.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetConstDataUtils;
import com.yaf.base.entity.ShopMaintenanceExamDeviceEntity;
import com.yaf.base.entity.ShopMaintenanceExamResultEntity;
import com.yaf.base.entity.ShopMaintenanceOrderEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 维保处理界面
 */
public class MaintenanceHandleShowActivity extends BaseClientActivity {


    @BindView(R.id.rv_check_result)
    RecyclerView rvCheckResult;

    @BindView(R.id.ll_device_handle)
    LinearLayout llDeviceHandle;
    @BindView(R.id.rv_device_handle)
    RecyclerView rvDeviceHandle;
    @BindView(R.id.tv_conclusion)
    TextView tvConclusion;
    @BindView(R.id.tv_team)
    TextView tvTeam;
    @BindView(R.id.et_suggest)
    EditText etSuggest;
    @BindView(R.id.et_question)
    EditText etQuestion;
    @BindView(R.id.ll_photo)
    LinearLayout llPhoto;
    @BindView(R.id.cb_video)
    CheckBox cbVideo;
    @BindView(R.id.cb_time)
    CheckBox cbTime;
    @BindView(R.id.cb_print)
    CheckBox cbPrint;
    @BindView(R.id.cb_host)
    CheckBox cbHost;
    @BindView(R.id.iv_device_handle)
    ImageView ivDeviceHandle;
    @BindView(R.id.tv_device_handle)
    TextView tvDeviceHandle;


    private long mId;
    private MaintenanceHandeCheckAdapter maintenanceHandeCheckAdapter;
    private ShopMaintenanceExamDeviceEntity examDeviceEntity;
    private MaintenanceHandleEditAdapter handleEditAdapter;
    private ShopMaintenanceOrderEntity orderEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_handle_show);
        ButterKnife.bind(this);
        setTitle("维保处理");
        setLeftBack();

        initData();

    }

    private void initData() {
        mId = getIntent().getLongExtra("orderId", 0);


        EanfangHttp.post(NewApiService.MAINTENANCE_DETAIL_DISPOSE)
                .params("id", mId)
                .execute(new EanfangCallback<ShopMaintenanceOrderEntity>(this, true, ShopMaintenanceOrderEntity.class, (bean) -> {
                    orderEntity = bean;
                    initViews();
                }));
    }

    private void initViews() {
        rvCheckResult.setLayoutManager(new LinearLayoutManager(this));
        rvDeviceHandle.setLayoutManager(new LinearLayoutManager(this));


        handleEditAdapter = new MaintenanceHandleEditAdapter(R.layout.item_maintenance_empasis_device_handle);
        handleEditAdapter.bindToRecyclerView(rvDeviceHandle);
        handleEditAdapter.setNewData(orderEntity.getExamDeviceEntityList());
        handleEditAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                examDeviceEntity = (ShopMaintenanceExamDeviceEntity) adapter.getData().get(position);
                Intent intent = new Intent(MaintenanceHandleShowActivity.this, MaintenanceHandleEditShowActivity.class);
                intent.putExtra("bean", examDeviceEntity);
//                intent.putExtra("isShow", true);//是否仅展示 不可编辑
                startActivity(intent);
            }
        });


        maintenanceHandeCheckAdapter = new MaintenanceHandeCheckAdapter(R.layout.item_maintenance_check_add, 1);
        maintenanceHandeCheckAdapter.bindToRecyclerView(rvCheckResult);

        if (orderEntity.getExamResultEntityList() != null && orderEntity.getExamResultEntityList().size() > 0) {
            maintenanceHandeCheckAdapter.setNewData(orderEntity.getExamResultEntityList());
            maintenanceHandeCheckAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent(MaintenanceHandleShowActivity.this, MaintenanceAddCheckResultShowActivity.class);
                    intent.putExtra("bean", (ShopMaintenanceExamResultEntity) adapter.getData().get(position));
                    startActivity(intent);
                }
            });
        } else {
            tvDeviceHandle.setVisibility(View.VISIBLE);
            ivDeviceHandle.setVisibility(View.GONE);
        }


        setChecked(cbVideo, orderEntity.getConfirmEntity().getIsVcrStoreDayNormal());
        setChecked(cbTime, orderEntity.getConfirmEntity().getIsTimeRight());
        setChecked(cbPrint, orderEntity.getConfirmEntity().getIsAlarmPrinter());
        setChecked(cbHost, orderEntity.getConfirmEntity().getIsMachineDataRemote());

        tvConclusion.setText(GetConstDataUtils.getMaintainOsRuntimeList().get(orderEntity.getConfirmEntity().getStatus()));
        etSuggest.setText(orderEntity.getConfirmEntity().getMaintenanceSuggest());
        etQuestion.setText(orderEntity.getConfirmEntity().getLeftoverProblem());

        tvTeam.setText(orderEntity.getConfirmEntity().getTeamWorker());
    }


    private void setChecked(CheckBox checkBox, int status) {
        if (status != 0) {
            checkBox.setChecked(true);
        }
    }


    @OnClick({R.id.ll_device_handle, R.id.ll_photo,})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_device_handle:
                if (rvDeviceHandle.getVisibility() == View.GONE) {
                    rvDeviceHandle.setVisibility(View.VISIBLE);
                } else {
                    rvDeviceHandle.setVisibility(View.GONE);
                }
                break;

            case R.id.ll_photo:


                Intent intent = new Intent(MaintenanceHandleShowActivity.this, MeintenancePhotoActivity.class);
                intent.putExtra("bean", orderEntity.getConfirmEntity());
                intent.putExtra("isShow", true);
                startActivity(intent);

                break;

        }
    }


}
