package net.eanfang.worker.ui.activity.worksapce.repair.seefaultdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.BughandleConfirmEntity;
import com.yaf.base.entity.BughandleDetailEntity;
import com.yaf.base.entity.TransferLogEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.im.SelectIMContactActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.seefaultdetail.faultdetail.LookTroubleDetailActivity;
import net.eanfang.worker.ui.adapter.FillTroubleDetailAdapter;
import net.eanfang.worker.ui.adapter.repair.TroubleHangListAdapter;
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
 * @desc 电话未解决
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
    //录像机天数
    @BindView(R.id.tv_videoStorage)
    TextView tvVideoStorage;
    //报警打印功能
    @BindView(R.id.tv_deviceTime)
    TextView tvDeviceTime;
    //所有设备时间同步
    @BindView(R.id.tv_policePriter)
    TextView tvPolicePriter;
    //各类设备数据远传功能
    @BindView(R.id.tv_policeDeliver)
    TextView tvPoliceDeliver;
    @BindView(R.id.rv_trouble)
    RecyclerView rvTrouble;
    //遗留问题
    @BindView(R.id.tv_remain_question)
    TextView tvRemainQuestion;
    /**
     * 电视墙/操作台正面全貌 (3张)
     */
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;
    /**
     * 电视墙/操作台背面全照(3张)
     */
    @BindView(R.id.snpl_monitor_add_photos)
    BGASortableNinePhotoLayout snplMonitorAddPhotos;
    /**
     * 机柜正面/背面 (3张)
     */
    @BindView(R.id.snpl_tools_package_add_photos)
    BGASortableNinePhotoLayout snplToolsPackageAddPhotos;
    /**
     * 单据照片 (3张)
     */
    @BindView(R.id.snpl_form_photos)
    BGASortableNinePhotoLayout snplFormPhotos;
    //协助人员
    @BindView(R.id.tv_team_worker)
    TextView tvTeamWorker;
    // 挂单
    @BindView(R.id.rv_hang_list)
    RecyclerView rvHangList;

    private FillTroubleDetailAdapter quotationDetailAdapter;
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

    // 挂单List
    private List<TransferLogEntity> transferLogEntityList = new ArrayList<>();
    private TroubleHangListAdapter troubleHangListAdapter;

    private BughandleConfirmEntity bughandleConfirmEntity;

    //聊天分享的必要参数
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_fill_repair_info);
        ButterKnife.bind(this);
        initView();
        initData();

        if (!getIntent().getBooleanExtra("isVisible", false)) {
            setRightTitle("分享");
            setRightTitleOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(TroubleDetailActivity.this, SelectIMContactActivity.class);

                    bundle.putString("id", String.valueOf(bughandleConfirmEntity.getBusRepairOrderId()));
                    bundle.putString("orderNum", (String) bughandleConfirmEntity.getDetailEntityList().get(0).getFailureEntity().getDeviceName());
                    if (bughandleConfirmEntity.getDetailEntityList() != null && bughandleConfirmEntity.getDetailEntityList().size() > 0) {
                        bundle.putString("picUrl", bughandleConfirmEntity.getDetailEntityList().get(0).getFailureEntity().getPictures().split(",")[0]);
                    }
                    bundle.putString("creatTime", bughandleConfirmEntity.getDetailEntityList().get(0).getFailureEntity().getBugPosition());
                    if (bughandleConfirmEntity.getDetailEntityList().get(0).getFailureEntity().getRepairCount() != null) {
                        bundle.putString("workerName", String.valueOf(bughandleConfirmEntity.getDetailEntityList().get(0).getFailureEntity().getRepairCount()));
                    }
//                    if (bughandleConfirmEntity.getDetailEntityList().get(0).getFailureEntity().getMaintenanceStatus() == 0) {
//                        bundle.putString("status", "保内");
//                    } else {
//                        bundle.putString("status", "保外");
//                    }
                    bundle.putString("status", String.valueOf(0));//电话未解决
                    bundle.putString("shareType", "2");

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

    }

    private void initData() {
        EanfangHttp.get(RepairApi.GET_BUGHANDLE_DETAIL)
                .params("id", id)
                .execute(new EanfangCallback<BughandleConfirmEntity>(this, true, BughandleConfirmEntity.class, (bean) -> {
                    bughandleConfirmEntity = bean;
                    setData(bean);
                }));
    }

    private void initView() {

        setTitle("故障处理");
        setLeftBack();
        id = getIntent().getLongExtra("orderId", 0);

    }

    private void setData(BughandleConfirmEntity bughandleConfirmEntity) {
        if (bughandleConfirmEntity != null) {
            //录像机天数
            if ("".equals(bughandleConfirmEntity.getStoreDays())) {
                tvVideoStorage.setBackgroundResource(R.drawable.bg_trouble_type);
                tvVideoStorage.setTextColor(ContextCompat.getColor(this, R.color.color_client_neworder));
            } else {
                tvVideoStorage.setBackgroundResource(R.drawable.bg_trouble_type_pressed);
                tvVideoStorage.setTextColor(ContextCompat.getColor(this, R.color.color_white));
            }
            //报警打印功能
            if (bughandleConfirmEntity.getIsAlarmPrinter() == null) {
                tvPolicePriter.setBackgroundResource(R.drawable.bg_trouble_type);
                tvPolicePriter.setTextColor(ContextCompat.getColor(this, R.color.color_client_neworder));
            } else {
                tvPolicePriter.setBackgroundResource(R.drawable.bg_trouble_type_pressed);
                tvPolicePriter.setTextColor(ContextCompat.getColor(this, R.color.color_white));
            }
            //所有设备时间同步
            if (bughandleConfirmEntity.getIsTimeRight() == null) {
                tvDeviceTime.setBackgroundResource(R.drawable.bg_trouble_type);
                tvDeviceTime.setTextColor(ContextCompat.getColor(this, R.color.color_client_neworder));
            } else {
                tvDeviceTime.setBackgroundResource(R.drawable.bg_trouble_type_pressed);
                tvDeviceTime.setTextColor(ContextCompat.getColor(this, R.color.color_white));
            }
            //各类设备数据远传功能
            if (bughandleConfirmEntity.getIsMachineDataRemote() == null) {
                tvPoliceDeliver.setBackgroundResource(R.drawable.bg_trouble_type);
                tvPoliceDeliver.setTextColor(ContextCompat.getColor(this, R.color.color_client_neworder));
            } else {
                tvPoliceDeliver.setBackgroundResource(R.drawable.bg_trouble_type_pressed);
                tvPoliceDeliver.setTextColor(ContextCompat.getColor(this, R.color.color_white));
            }
            //遗留问题
            if (bughandleConfirmEntity.getLeftoverProblem() != null) {
                tvRemainQuestion.setText(bughandleConfirmEntity.getLeftoverProblem());
            }

            //协作人员

            if (bughandleConfirmEntity.getTeamWorker() != null) {
                tvTeamWorker.setText(bughandleConfirmEntity.getTeamWorker());
            }

            initImageList(bughandleConfirmEntity);
            initNinePhoto();
            // 转单记录
            if (bughandleConfirmEntity.getTransferLogEntityList() != null) {
                transferLogEntityList = bughandleConfirmEntity.getTransferLogEntityList();
            }
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
        quotationDetailAdapter = new FillTroubleDetailAdapter(R.layout.layout_trouble_detail, mDataList);
        rvTrouble.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rvTrouble.setLayoutManager(new LinearLayoutManager(this));
        rvTrouble.setAdapter(quotationDetailAdapter);
        rvTrouble.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(TroubleDetailActivity.this, LookTroubleDetailActivity.class);
                intent.putExtra("id", mDataList.get(position).getId());
                startActivity(intent);
            }
        });
        // 转单
        troubleHangListAdapter = new TroubleHangListAdapter(R.layout.layout_trouble_hanglist_item, transferLogEntityList);
        rvHangList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rvHangList.setLayoutManager(new LinearLayoutManager(this));
        rvHangList.setAdapter(troubleHangListAdapter);
    }

    private void initNinePhoto() {
        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
        snplMonitorAddPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_2, REQUEST_CODE_PHOTO_PREVIEW_2));
        snplToolsPackageAddPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_3, REQUEST_CODE_PHOTO_PREVIEW_3));
        snplFormPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_4, REQUEST_CODE_PHOTO_PREVIEW_4));

        snplMomentAddPhotos.setData(picList1);
        snplMonitorAddPhotos.setData(picList2);
        snplToolsPackageAddPhotos.setData(picList3);
        snplFormPhotos.setData(picList4);

        snplMomentAddPhotos.setEditable(false);
        snplMonitorAddPhotos.setEditable(false);
        snplToolsPackageAddPhotos.setEditable(false);
        snplFormPhotos.setEditable(false);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_PHOTO_1:
                snplMomentAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_2:
                snplMonitorAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_3:
                snplToolsPackageAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_4:
                snplFormPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_1:
                snplMomentAddPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_2:
                snplMonitorAddPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_3:
                snplToolsPackageAddPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_4:
                snplFormPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            default:
                break;
        }
    }

}
