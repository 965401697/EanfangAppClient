package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.CallUtils;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.BughandleConfirmEntity;
import com.yaf.base.entity.BughandleDetailEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.TroubleDetailAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  10:52
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class TroubleDetailActivity extends BaseWorkerActivity {

    private static final int REQUEST_CODE_CHOOSE_PHOTO_1 = 1;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_2 = 2;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_3 = 3;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_4 = 4;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_1 = 101;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_2 = 102;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_3 = 103;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_4 = 104;
    @BindView(R.id.iv_left)
    ImageView ivLeft;

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


    private TroubleDetailAdapter quotationDetailAdapter;
    private Long id;
    /**
     * 电视墙/操作台正面全貌 (3张)
     */
    private ArrayList<String> picList1 = new ArrayList<>();
    /**
     * 电视墙/操作台背面全照(3张)
     */
    private ArrayList<String> picList2 = new ArrayList<>();
    /**
     * 机柜正面/背面 (3张)
     */
    private ArrayList<String> picList3 = new ArrayList<>();
    /**
     * 单据照片 (3张)
     */
    private ArrayList<String> picList4 = new ArrayList<>();
    private int isPhonesolve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_fill_repair_info);
        ButterKnife.bind(this);

        id = getIntent().getLongExtra("orderId", 0);
        initView();
        setTitle("故障处理");
        initData();
    }

    private void initData() {
        EanfangHttp.get(RepairApi.GET_BUGHANDLE_DETAIL)
                .params("id", id)
                .execute(new EanfangCallback<BughandleConfirmEntity>(this, true, BughandleConfirmEntity.class, (bean) -> {
                    setData(bean);
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

        tv_complaint.setOnClickListener((v) -> {
            CallUtils.call(this, "010-5877-8731");
        });
        ivLeft.setOnClickListener((v) -> finishSelf());
    }

    private void setData(BughandleConfirmEntity bughandleConfirmEntity) {
        if (bughandleConfirmEntity != null) {
            if (bughandleConfirmEntity.getOverTime() != null) {
                tv_over_time.setText(GetDateUtils.dateToDateString(bughandleConfirmEntity.getOverTime()));
            }
            if (bughandleConfirmEntity.getWorkHour() != null) {
                tv_repair_time.setText(bughandleConfirmEntity.getWorkHour());
            }
            //录像机天数
            tv_store_time.setText(GetConstDataUtils.getStoreDayList().get(bughandleConfirmEntity.getStoreDays()));

            //报警打印功能
            if (bughandleConfirmEntity.getIsAlarmPrinter() != null) {
                tv_print_on_alarm.setText(GetConstDataUtils.getIsNormalList().get(bughandleConfirmEntity.getIsAlarmPrinter()));
            }
            //所有设备时间同步
            if (bughandleConfirmEntity.getIsTimeRight() != null) {
                tv_time_right.setText(GetConstDataUtils.getIsNormalList().get(bughandleConfirmEntity.getIsTimeRight()));
            }
            //各类设备数据远传功能
            if (bughandleConfirmEntity.getIsMachineDataRemote() != null) {
                tv_machine_data_remote.setText(GetConstDataUtils.getIsNormalList().get(bughandleConfirmEntity.getIsMachineDataRemote()));
            }
            //遗留问题
            if (bughandleConfirmEntity.getLeftoverProblem() != null) {
                tv_remain_question.setText(bughandleConfirmEntity.getLeftoverProblem());
            }
            //协作人员
            if (bughandleConfirmEntity.getTeamWorker() != null) {
                tv_team_worker.setText(bughandleConfirmEntity.getTeamWorker());
            }
            initImageList(bughandleConfirmEntity);
            initNinePhoto();
            initAdapter(bughandleConfirmEntity.getDetailEntityList());
        }
    }

    private void initImageList(BughandleConfirmEntity bughandleConfirmEntity) {

        if (StringUtils.isValid(bughandleConfirmEntity.getFrontPictures())) {
            String[] friontPic = bughandleConfirmEntity.getFrontPictures().split(",");
            picList1.addAll(Stream.of(Arrays.asList(friontPic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }
        if (StringUtils.isValid(bughandleConfirmEntity.getReverseSidePictures())) {
            String[] reversePic = bughandleConfirmEntity.getReverseSidePictures().split(",");
            picList2.addAll(Stream.of(Arrays.asList(reversePic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }

        if (StringUtils.isValid(bughandleConfirmEntity.getEquipmentCabinetPictures())) {
            String[] equipmentPic = bughandleConfirmEntity.getEquipmentCabinetPictures().split(",");
            picList3.addAll(Stream.of(Arrays.asList(equipmentPic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }

        if (StringUtils.isValid(bughandleConfirmEntity.getInvoicesPictures())) {
            String[] invoicesPic = bughandleConfirmEntity.getInvoicesPictures().split(",");
            picList4.addAll(Stream.of(Arrays.asList(invoicesPic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }
    }

    private void initAdapter(List<BughandleDetailEntity> mDataList) {
        quotationDetailAdapter = new TroubleDetailAdapter(R.layout.item_quotation_detail, mDataList);
        rv_trouble.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rv_trouble.setLayoutManager(new LinearLayoutManager(this));
        rv_trouble.setAdapter(quotationDetailAdapter);
        rv_trouble.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(TroubleDetailActivity.this, LookTroubleDetailActivity.class);
                intent.putExtra("id", mDataList.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void initNinePhoto() {
        snpl_moment_add_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
        snpl_monitor_add_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_2, REQUEST_CODE_PHOTO_PREVIEW_2));
        snpl_tools_package_add_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_3, REQUEST_CODE_PHOTO_PREVIEW_3));
        snpl_form_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_4, REQUEST_CODE_PHOTO_PREVIEW_4));

        snpl_moment_add_photos.setData(picList1);
        snpl_monitor_add_photos.setData(picList2);
        snpl_tools_package_add_photos.setData(picList3);
        snpl_form_photos.setData(picList4);

        snpl_moment_add_photos.setEditable(false);
        snpl_monitor_add_photos.setEditable(false);
        snpl_tools_package_add_photos.setEditable(false);
        snpl_form_photos.setEditable(false);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_PHOTO_1:
                snpl_moment_add_photos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_2:
                snpl_monitor_add_photos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_3:
                snpl_tools_package_add_photos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_4:
                snpl_form_photos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_1:
                snpl_moment_add_photos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_2:
                snpl_monitor_add_photos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_3:
                snpl_tools_package_add_photos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_4:
                snpl_form_photos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            default:
                break;
        }
    }

}
