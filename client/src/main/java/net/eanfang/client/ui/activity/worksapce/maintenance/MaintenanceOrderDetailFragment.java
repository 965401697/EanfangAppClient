package net.eanfang.client.ui.activity.worksapce.maintenance;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.V;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.ShopMaintenanceOrderEntity;


import net.eanfang.client.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by O u r on 2018/7/16.
 */

public class MaintenanceOrderDetailFragment extends BaseFragment {

    @BindView(R.id.iv_report_header)
    SimpleDraweeView ivReportHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_contact_phone)
    TextView tvContactPhone;
    @BindView(R.id.tv_period_type)
    TextView tvPeriodType;
    @BindView(R.id.tv_plan_start)
    TextView tvPlanStart;
    @BindView(R.id.tv_plan_end)
    TextView tvPlanEnd;
    @BindView(R.id.tv_standard)
    TextView tvStandard;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.tv_worker_company)
    TextView tvWorkerCompany;
    @BindView(R.id.tv_worker_name)
    TextView tvWorkerName;
    @BindView(R.id.tv_worker_phone)
    TextView tvWorkerPhone;
    @BindView(R.id.tv_os_type)
    TextView tvOsType;
    @BindView(R.id.rv_device_list)
    RecyclerView rvDeviceList;
    @BindView(R.id.rv_device_test)
    RecyclerView rvDeviceTest;
    @BindView(R.id.iv_device_list)
    ImageView ivDeviceList;
    @BindView(R.id.iv_device_test)
    ImageView ivDeviceTest;

    private MaintenanceOrderDetailEmphasisDeviceAdapter emphasisDeviceAdapter;
    private MaintenanceOrderDetailDeviceListAdapter deviceListAdapter;

    private List<String> test = new ArrayList<>();
    private long mId;
    private ShopMaintenanceOrderEntity orderEntity;


    public static MaintenanceOrderDetailFragment newInstance(long id) {
        MaintenanceOrderDetailFragment f = new MaintenanceOrderDetailFragment();
        f.mId = id;
        return f;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_maintenance_order_detail;
    }

    @Override
    protected void initData(Bundle arguments) {
        test.add("");
        test.add("");
        test.add("");
        test.add("");
        getData();
    }

    @Override
    protected void initView() {

        rvDeviceList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDeviceTest.setLayoutManager(new LinearLayoutManager(getContext()));

        deviceListAdapter = new MaintenanceOrderDetailDeviceListAdapter(R.layout.item_maintenance_order_detail_device_list);
        deviceListAdapter.bindToRecyclerView(rvDeviceList);
        emphasisDeviceAdapter = new MaintenanceOrderDetailEmphasisDeviceAdapter(R.layout.item_maintenance_order_detail_empasis_device);
        emphasisDeviceAdapter.bindToRecyclerView(rvDeviceTest);


        emphasisDeviceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                startActivity(new Intent(getActivity(), MaintenanceHandleActivity.class));
            }
        });
    }

    @Override
    protected void setListener() {

    }

    private void getData() {
        EanfangHttp.get(NewApiService.MAINTENANCE_GET_DETAIL)
                .params("id", mId)
                .execute(new EanfangCallback<ShopMaintenanceOrderEntity>(getActivity(), true, ShopMaintenanceOrderEntity.class, (bean) -> {
                    Log.e("zzw", bean.toString());
                    orderEntity = bean;
                    initViews();
                }));
    }

    private void initViews() {
        ivReportHeader.setImageURI(BuildConfig.OSS_SERVER + orderEntity.getOwnerUserEntity().getAccountEntity().getAvatar());
        tvName.setText(orderEntity.getAssigneeUserEntity().getAccountEntity().getRealName() + "(" + (orderEntity.getAssigneeUserEntity().getAccountEntity().getGender() == 1 ? "先生" : "女士") + ")");
        tvCompanyName.setText(orderEntity.getOwnerUserEntity().getCompanyEntity().getOrgName());
        tvAddress.setText(Config.get().getAddressByCode(orderEntity.getOwnerUserEntity().getAccountEntity().getAreaCode()) + "" + orderEntity.getOwnerUserEntity().getAccountEntity().getAddress());
        tvPhone.setText(orderEntity.getOwnerUserEntity().getAccountEntity().getMobile());


        tvOrderNum.setText("订单编号：" + orderEntity.getOrderNum());
        tvContact.setText("现场联系人：" + orderEntity.getRepairContacts());
        tvContactPhone.setText("联系电话：" + orderEntity.getRepairContactPhone());
        if (orderEntity.getPeriod() == 0) {
            tvPeriodType.setText("维保类型：周检");
        } else if (orderEntity.getPeriod() == 1) {
            tvPeriodType.setText("维保类型：月检");
        } else if (orderEntity.getPeriod() == 2) {
            tvPeriodType.setText("维保类型：季检");
        } else if (orderEntity.getPeriod() == 3) {
            tvPeriodType.setText("维保类型：年检");
        }

        tvPlanStart.setText("计划开始时间：" + GetDateUtils.dateToDateTimeString(orderEntity.getBeginTime()));
        tvPlanEnd.setText("计划结束时间：" + GetDateUtils.dateToDateTimeString(orderEntity.getEndTime()));


        tvStandard.setText("维保标准：\r\n\t" + orderEntity.getStandard());
        tvNotice.setText("备注信息：\r\n\t" + orderEntity.getContext());


        tvWorkerCompany.setText("维保单位：" + orderEntity.getAssigneeOrgEntity().getOrgName());
        tvWorkerName.setText("负责技师：" + orderEntity.getAssigneeUserEntity().getAccountEntity().getRealName());
        tvWorkerPhone.setText("联系电话：" + orderEntity.getAssigneeUserEntity().getAccountEntity().getMobile());

        String bizStr = "";
        String[] bizOneArr = V.v(() -> orderEntity.getBusinessOneCode().split(","));
        List<String> bizList = Stream.of(Arrays.asList(bizOneArr)).map(biz -> Config.get().getBusinessNameByCode(biz, 1)).toList();

        for (int i = 0; i < bizList.size(); i++) {
            bizStr = bizStr + "  " + bizList.get(i);
        }
        tvOsType.setText("系统类别：" + bizStr);

        emphasisDeviceAdapter.setNewData(orderEntity.getExamDeviceEntityList());
        deviceListAdapter.setNewData(orderEntity.getDeviceEntityList());

    }

    @OnClick({R.id.ll_device_list, R.id.ll_device_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_device_list:
                if (rvDeviceList.getVisibility() == View.VISIBLE) {
                    rvDeviceList.setVisibility(View.GONE);
                } else {
                    rvDeviceList.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_device_test:
                if (rvDeviceTest.getVisibility() == View.VISIBLE) {
                    rvDeviceTest.setVisibility(View.GONE);
                } else {
                    rvDeviceTest.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
