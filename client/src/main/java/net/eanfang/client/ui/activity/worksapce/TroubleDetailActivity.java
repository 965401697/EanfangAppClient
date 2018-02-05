package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.CallUtils;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.BughandleConfirmEntity;
import com.yaf.base.entity.BughandleDetailEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.TroubleDetailAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  10:52
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class TroubleDetailActivity extends BaseClientActivity {

    private RecyclerView rv_trouble;
    private TextView tv_over_time;
    private TextView tv_repair_time;
    /**
     * 电视墙/操作台正面全貌 (3张)
     */
    private BGASortableNinePhotoLayout snpl_moment_add_photos;
    /**
     * 电视墙/操作台背面全照(3张)
     */
    private BGASortableNinePhotoLayout snpl_monitor_add_photos;
    /**
     * 机柜正面/背面 (3张)
     */
    private BGASortableNinePhotoLayout snpl_tools_package_add_photos;
    /**
     * 单据照片 (3张)
     */
    private BGASortableNinePhotoLayout snpl_form_photos;


    private TextView tv_complete;
    private TextView tv_complaint;
    //录像机天数
    private TextView tv_store_time;
    //报警打印功能
    private TextView tv_print_on_alarm;
    //所有设备时间同步
    private TextView tv_time_right;
    //各类设备数据远传功能
    private TextView tv_machine_data_remote;
    //遗留问题
    private TextView tv_remain_question;
    //2017年7月21日
    //协助人员
    private TextView tv_team_worker;


    private List<BughandleDetailEntity> mDataList;
    private TroubleDetailAdapter quotationDetailAdapter;
    private BughandleConfirmEntity bughandleConfirmEntity;
    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            Intent intent = new Intent(TroubleDetailActivity.this, LookTroubleDetailActivity.class);
            intent.putExtra("id", bughandleConfirmEntity.getDetailEntityList().get(position).getId());
            startActivity(intent);
        }
    };
    private Long id, repairOrderId;
    private String status;
    /**
     * 电视墙/操作台正面全貌 (3张)
     */
    private ArrayList<String> picList1;
    /**
     * 电视墙/操作台背面全照(3张)
     */
    private ArrayList<String> picList2;
    /**
     * 机柜正面/背面 (3张)
     */
    private ArrayList<String> picList3;
    /**
     * 单据照片 (3张)
     */
    private ArrayList<String> picList4;
    private int isPhonesolve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_fill_repair_info);

        id = getIntent().getLongExtra("orderId", 0);
        repairOrderId = getIntent().getLongExtra("repairOrderId", 0);
        status = getIntent().getStringExtra("status");
        initView();
        setTitle("故障处理");
        initData();
    }

    private void initData() {
        EanfangHttp.get(RepairApi.GET_BUGHANDLE_DETAIL)
                .params("id", id)
                .execute(new EanfangCallback<BughandleConfirmEntity>(this, true, BughandleConfirmEntity.class, (bean) -> {
                    bughandleConfirmEntity = bean;
                    setData();
                }));
    }

    private void initView() {
        rv_trouble = (RecyclerView) findViewById(R.id.rv_trouble);
        tv_over_time = (TextView) findViewById(R.id.tv_over_time);
        tv_repair_time = (TextView) findViewById(R.id.tv_repair_time);
        snpl_moment_add_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_moment_add_photos);
        snpl_monitor_add_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_monitor_add_photos);
        snpl_tools_package_add_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_tools_package_add_photos);
        snpl_form_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_form_photos);
        //2017年7月20日
        //两个操作按钮
        tv_complete = (TextView) findViewById(R.id.tv_complete);
        tv_complaint = (TextView) findViewById(R.id.tv_complaint);
        //录像机天数
        tv_store_time = (TextView) findViewById(R.id.tv_store_time);
        //报警打印功能
        tv_print_on_alarm = (TextView) findViewById(R.id.tv_print_on_alarm);
        //所有设备时间同步
        tv_time_right = (TextView) findViewById(R.id.tv_time_right);
        //各类设备数据远传功能
        tv_machine_data_remote = (TextView) findViewById(R.id.tv_machine_data_remote);
        //遗留问题
        tv_remain_question = (TextView) findViewById(R.id.tv_remain_question);
        //协作人员
        tv_team_worker = (TextView) findViewById(R.id.tv_team_worker);


        //非单确认状态 隐藏确认按钮
        if (!status.equals("待确认")) {
            findViewById(R.id.rl_client_option).setVisibility(View.GONE);
        }
        tv_complete.setOnClickListener(v -> flowConfirm());
        tv_complaint.setOnClickListener((v) -> {
            CallUtils.call(this, "010-5877-8731");
        });
    }

    private void flowConfirm() {
        EanfangHttp.post(RepairApi.GET_FLOW_CONFIRE)
                .params("id", repairOrderId)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    showToast("确认成功");
                    finish();
                }));
    }

    private void setData() {
        tv_over_time.setText(GetDateUtils.dateToDateString(bughandleConfirmEntity.getOverTime()));
        tv_repair_time.setText(bughandleConfirmEntity.getWorkHour());
        //录像机天数
        tv_store_time.setText(GetConstDataUtils.getStoreDayList().get(bughandleConfirmEntity.getStoreDays()));
        //报警打印功能
        tv_print_on_alarm.setText(GetConstDataUtils.getIsNormalList().get(bughandleConfirmEntity.getIsAlarmPrinter()));
        //所有设备时间同步
        tv_time_right.setText(GetConstDataUtils.getIsNormalList().get(bughandleConfirmEntity.getIsTimeRight()));
        //各类设备数据远传功能
        tv_machine_data_remote.setText(GetConstDataUtils.getIsNormalList().get(bughandleConfirmEntity.getIsMachineDataRemote()));
        //遗留问题
        tv_remain_question.setText(bughandleConfirmEntity.getLeftoverProblem());
        //协作人员
        tv_team_worker.setText(bughandleConfirmEntity.getTeamWorker());
        initAdapter();
        initNinePhoto();
        picList1 = new ArrayList<>();
        picList2 = new ArrayList<>();
        picList3 = new ArrayList<>();
        picList4 = new ArrayList<>();
        if (bughandleConfirmEntity.getFrontPictures() != null) {
            String[] friontPic = bughandleConfirmEntity.getFrontPictures().split(",");
            Collections.addAll(picList1, friontPic);
        }

        if (bughandleConfirmEntity.getReverseSidePictures() != null) {
            String[] reversePic = bughandleConfirmEntity.getReverseSidePictures().split(",");
            Collections.addAll(picList2, reversePic);
        }

        if (bughandleConfirmEntity.getEquipmentCabinetPictures() != null) {
            String[] equipmentPic = bughandleConfirmEntity.getEquipmentCabinetPictures().split(",");
            Collections.addAll(picList3, equipmentPic);
        }

        if (bughandleConfirmEntity.getInvoicesPictures() != null) {
            String[] invoicesPic = bughandleConfirmEntity.getInvoicesPictures().split(",");
            Collections.addAll(picList4, invoicesPic);
        }


    }

    private void initAdapter() {
        mDataList = bughandleConfirmEntity.getDetailEntityList();
        quotationDetailAdapter = new TroubleDetailAdapter(R.layout.item_quotation_detail, mDataList);
        rv_trouble.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rv_trouble.setLayoutManager(new LinearLayoutManager(this));
        rv_trouble.setAdapter(quotationDetailAdapter);
        rv_trouble.addOnItemTouchListener(onItemClickListener);
    }

    private void initNinePhoto() {
        snpl_moment_add_photos.setData(picList1);
        snpl_monitor_add_photos.setData(picList2);
        snpl_tools_package_add_photos.setData(picList3);
        snpl_form_photos.setData(picList4);

        snpl_moment_add_photos.setEditable(false);
        snpl_monitor_add_photos.setEditable(false);
        snpl_tools_package_add_photos.setEditable(false);
        snpl_form_photos.setEditable(false);

        snpl_moment_add_photos.setDelegate(new BGASortableDelegate(this));

        snpl_monitor_add_photos.setDelegate(new BGASortableDelegate(this));

        snpl_tools_package_add_photos.setDelegate(new BGASortableDelegate(this));
        snpl_form_photos.setDelegate(new BGASortableDelegate(this));

    }
}
