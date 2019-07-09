package net.eanfang.client.ui.activity.worksapce;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.TemplateBean;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.util.CallUtils;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.BughandleConfirmEntity;
import com.yaf.base.entity.BughandleDetailEntity;
import com.yaf.base.entity.TransferLogEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.im.SelectIMContactActivity;
import net.eanfang.client.ui.activity.worksapce.maintenance.MaintenanceTeamAdapter;
import net.eanfang.client.ui.adapter.TroubleDetailAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  10:52
 * @email houzhongzhou@yeah.net
 * @desc 电话未解决
 */

public class TroubleDetailActivity extends BaseClientActivity {

    private static final int REQUEST_CODE_CHOOSE_PHOTO_1 = 1;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_2 = 2;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_3 = 3;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_4 = 4;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_1 = 101;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_2 = 102;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_3 = 103;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_4 = 104;
    // 录像机存储时限
    @BindView(R.id.tv_videoStorage)
    TextView tvVideoStorage;
    //设备时间同步
    @BindView(R.id.tv_deviceTime)
    TextView tvDeviceTime;
    // 报警打印机
    @BindView(R.id.tv_policePriter)
    TextView tvPolicePriter;
    // 远传功能
    @BindView(R.id.tv_policeDeliver)
    TextView tvPoliceDeliver;
    // 协同人员
    @BindView(R.id.rv_teamwork)
    RecyclerView rvTeamwork;


    private RecyclerView rv_trouble;
    /**
     * 电视墙/操作台正面全貌 (3张)
     */
    private BGASortableNinePhotoLayout snpl_moment_add_photos;
    @BindView(R.id.iv_thumbnail_moment)
    ImageView ivThumbnailMoment;
    @BindView(R.id.rl_thumbnail_moment)
    RelativeLayout rlThumbnailMoment;
    /**
     * 电视墙/操作台背面全照(3张)
     */
    private BGASortableNinePhotoLayout snpl_monitor_add_photos;
    @BindView(R.id.iv_thumbnail_monitor)
    ImageView ivThumbnailMonitor;
    @BindView(R.id.rl_thumbnail_monitor)
    RelativeLayout rlThumbnailMonitor;
    /**
     * 机柜正面/背面 (3张)
     */
    private BGASortableNinePhotoLayout snpl_tools_package_add_photos;
    @BindView(R.id.iv_thumbnail_tools_package)
    ImageView ivThumbnailToolsPackage;
    @BindView(R.id.rl_thumbnail_tools_package)
    RelativeLayout rlThumbnailToolsPackage;
    /**
     * 单据照片 (3张)
     */
    private BGASortableNinePhotoLayout snpl_form_photos;
    /**
     * 转单
     */
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_order_reason)
    TextView tvOrderReason;
    @BindView(R.id.ll_hang)
    LinearLayout llHang;
    @BindView(R.id.tv_no_history)
    TextView tvNoHistory;

    private TextView tv_complete;
    private TextView tv_complaint;
    //2017年7月21日

    private List<BughandleDetailEntity> mDataList;
    private TroubleDetailAdapter quotationDetailAdapter;
    private BughandleConfirmEntity bughandleConfirmEntity;
    private Long id, repairOrderId;
    private String status;

    // 挂单List
    private TransferLogEntity transferLogEntityHistory = new TransferLogEntity();
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

    //聊天分享的必要参数
    Bundle bundle = new Bundle();

    /**
     * 协同人员
     */
    private MaintenanceTeamAdapter teamAdapter;
    private List<TemplateBean.Preson> presonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_client_fill_repair_info);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        id = getIntent().getLongExtra("orderId", 0);
        repairOrderId = getIntent().getLongExtra("repairOrderId", 0);
        status = getIntent().getStringExtra("status");
        initView();
        initData();
        if (!getIntent().getBooleanExtra("isVisible", false)) {
            setRightTitle("分享");
            setRightTitleOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(TroubleDetailActivity.this, SelectIMContactActivity.class);

                    bundle.putString("id", String.valueOf(repairOrderId));
                    bundle.putString("orderNum", bughandleConfirmEntity.getDetailEntityList().get(0).getFailureEntity().getDeviceName());
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
                    setData();
                }));
    }

    private void initView() {
        setTitle("完工报告");
        setLeftBack();
        rv_trouble = findViewById(R.id.rv_trouble);
        snpl_moment_add_photos = findViewById(R.id.snpl_moment_add_photos);
        snpl_monitor_add_photos = findViewById(R.id.snpl_monitor_add_photos);
        snpl_tools_package_add_photos = findViewById(R.id.snpl_tools_package_add_photos);
        snpl_form_photos = findViewById(R.id.snpl_form_photos);
        //两个操作按钮
        tv_complete = findViewById(R.id.tv_complete);
        tv_complaint = findViewById(R.id.tv_complaint);


        //非单确认状态 隐藏确认按钮
        if (!status.equals("待确认")) {
            findViewById(R.id.rl_client_option).setVisibility(View.GONE);
        }
        tv_complete.setOnClickListener(v -> flowConfirm());
        tv_complaint.setOnClickListener((v) -> {
            CallUtils.call(this, "400-890-9280");
        });
        //协作人员
        GridLayoutManager layoutManage = new GridLayoutManager(this, 5);
        rvTeamwork.setLayoutManager(layoutManage);
        teamAdapter = new MaintenanceTeamAdapter();
        teamAdapter.bindToRecyclerView(rvTeamwork);
    }

    private void flowConfirm() {
        EanfangHttp.post(RepairApi.GET_FLOW_CONFIRE)
                .params("id", repairOrderId)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    showToast("确认成功");
                    finish();
                }));
    }

    @SuppressLint("ResourceAsColor")
    private void setData() {
        //录像机天数
        if ("".equals(bughandleConfirmEntity.getStoreDays())) {
            tvVideoStorage.setBackgroundResource(R.drawable.bg_client_trouble_type);
            tvVideoStorage.setTextColor(ContextCompat.getColor(this, R.color.color_client_neworder));
        } else {
            tvVideoStorage.setBackgroundResource(R.drawable.bg_client_trouble_type_pressed);
            tvVideoStorage.setTextColor(ContextCompat.getColor(this, R.color.color_white));
        }
        //报警打印功能
        if (bughandleConfirmEntity.getIsAlarmPrinter() == null) {
            tvPolicePriter.setBackgroundResource(R.drawable.bg_client_trouble_type);
            tvPolicePriter.setTextColor(ContextCompat.getColor(this, R.color.color_client_neworder));
        } else {
            tvPolicePriter.setBackgroundResource(R.drawable.bg_client_trouble_type_pressed);
            tvPolicePriter.setTextColor(ContextCompat.getColor(this, R.color.color_white));
        }
        //所有设备时间同步
        if (bughandleConfirmEntity.getIsTimeRight() == null) {
            tvDeviceTime.setBackgroundResource(R.drawable.bg_client_trouble_type);
            tvDeviceTime.setTextColor(ContextCompat.getColor(this, R.color.color_client_neworder));
        } else {
            tvDeviceTime.setBackgroundResource(R.drawable.bg_client_trouble_type_pressed);
            tvDeviceTime.setTextColor(ContextCompat.getColor(this, R.color.color_white));
        }
        //各类设备数据远传功能
        if (bughandleConfirmEntity.getIsMachineDataRemote() == null) {
            tvPoliceDeliver.setBackgroundResource(R.drawable.bg_client_trouble_type);
            tvPoliceDeliver.setTextColor(ContextCompat.getColor(this, R.color.color_client_neworder));
        } else {
            tvPoliceDeliver.setBackgroundResource(R.drawable.bg_client_trouble_type_pressed);
            tvPoliceDeliver.setTextColor(ContextCompat.getColor(this, R.color.color_white));
        }
        //协作人员
        if (bughandleConfirmEntity.getTeamWorker() != null && bughandleConfirmEntity.getTeamWorker().contains("-")) {
            String[] info = bughandleConfirmEntity.getTeamWorker().split(",");
            if (info.length > 0) {
                //多条
                for (int i = 0; i < info.length; i++) {
                    String s = info[i];
                    String headPortrait = s.split("-")[0];
                    String name = s.split("-")[1];

                    TemplateBean.Preson preson = new TemplateBean.Preson();
                    preson.setProtraivat(headPortrait);
                    preson.setName(name);
                    presonList.add(preson);
                }
            } else {
                //一条
                String headPortrait = bughandleConfirmEntity.getTeamWorker().split("-")[0];
                String name = bughandleConfirmEntity.getTeamWorker().split("-")[1];

                TemplateBean.Preson preson = new TemplateBean.Preson();
                preson.setProtraivat(headPortrait);
                preson.setName(name);
                presonList.add(preson);

            }
            teamAdapter.setNewData(presonList);
        }

        /**
         *电视机墙正面照 拍摄视频
         * */
        if (!StringUtils.isEmpty(bughandleConfirmEntity.getFront_mp4_path())) {
            rlThumbnailMoment.setVisibility(View.VISIBLE);
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + bughandleConfirmEntity.getFront_mp4_path() + ".jpg"),ivThumbnailMoment);
            if (!StringUtils.isValid(bughandleConfirmEntity.getFrontPictures())) {
                snpl_moment_add_photos.setVisibility(View.GONE);
            }
        } else {
            rlThumbnailMoment.setVisibility(View.GONE);
        }
        ivThumbnailMoment.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", BuildConfig.OSS_SERVER + bughandleConfirmEntity.getFront_mp4_path() + ".mp4");
            JumpItent.jump(TroubleDetailActivity.this, PlayVideoActivity.class, bundle);
        });

        /**
         *电视机墙背面照 拍摄视频
         * */
        if (!StringUtils.isEmpty(bughandleConfirmEntity.getReverse_side_mp4_path())) {
            rlThumbnailMonitor.setVisibility(View.VISIBLE);
            if (!StringUtils.isValid(bughandleConfirmEntity.getReverseSidePictures())) {
                snpl_monitor_add_photos.setVisibility(View.GONE);
            }
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + bughandleConfirmEntity.getReverse_side_mp4_path() + ".jpg"),ivThumbnailMonitor);
        } else {
            rlThumbnailMonitor.setVisibility(View.GONE);
        }
        ivThumbnailMonitor.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", BuildConfig.OSS_SERVER + bughandleConfirmEntity.getReverse_side_mp4_path() + ".mp4");
            JumpItent.jump(TroubleDetailActivity.this, PlayVideoActivity.class, bundle);
        });

        /**
         *机柜照 拍摄视频
         * */
        if (!StringUtils.isEmpty(bughandleConfirmEntity.getEquipment_cabinet_mp4_path())) {
            rlThumbnailToolsPackage.setVisibility(View.VISIBLE);
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + bughandleConfirmEntity.getEquipment_cabinet_mp4_path() + ".jpg"),ivThumbnailToolsPackage);
            if (!StringUtils.isValid(bughandleConfirmEntity.getEquipmentCabinetPictures())) {
                snpl_tools_package_add_photos.setVisibility(View.GONE);
            }
        } else {
            rlThumbnailToolsPackage.setVisibility(View.GONE);
        }
        ivThumbnailToolsPackage.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", BuildConfig.OSS_SERVER + bughandleConfirmEntity.getEquipment_cabinet_mp4_path() + ".mp4");
            JumpItent.jump(TroubleDetailActivity.this, PlayVideoActivity.class, bundle);
        });


        initAdapter();

        if (bughandleConfirmEntity.getFrontPictures() != null) {
            String[] friontPic = bughandleConfirmEntity.getFrontPictures().split(",");
            picList1.addAll(Stream.of(Arrays.asList(friontPic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
        }

        if (bughandleConfirmEntity.getReverseSidePictures() != null) {
            String[] reversePic = bughandleConfirmEntity.getReverseSidePictures().split(",");
            picList2.addAll(Stream.of(Arrays.asList(reversePic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
        }

        if (bughandleConfirmEntity.getEquipmentCabinetPictures() != null) {
            String[] equipmentPic = bughandleConfirmEntity.getEquipmentCabinetPictures().split(",");
            picList3.addAll(Stream.of(Arrays.asList(equipmentPic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
        }

        if (bughandleConfirmEntity.getInvoicesPictures() != null) {
            String[] invoicesPic = bughandleConfirmEntity.getInvoicesPictures().split(",");
            picList4.addAll(Stream.of(Arrays.asList(invoicesPic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
        }
        initNinePhoto();
        // 转单记录
        transferLogEntityHistory = bughandleConfirmEntity.getTransferLogEntity();
        getHistory(transferLogEntityHistory);

    }

    private void initAdapter() {
        mDataList = bughandleConfirmEntity.getDetailEntityList();
        quotationDetailAdapter = new TroubleDetailAdapter(R.layout.layout_trouble_adapter_item, mDataList);
        rv_trouble.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rv_trouble.setLayoutManager(new LinearLayoutManager(this));
        rv_trouble.setAdapter(quotationDetailAdapter);
        rv_trouble.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(TroubleDetailActivity.this, LookTroubleDetailActivity.class);
                intent.putExtra("id", bughandleConfirmEntity.getDetailEntityList().get(position).getId());
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

    /**
     * 转单
     */
    public void getHistory(TransferLogEntity transferLogEntity) {
        if (transferLogEntity == null) {
            llHang.setVisibility(View.GONE);
            tvNoHistory.setVisibility(View.VISIBLE);
            return;
        }
        llHang.setVisibility(View.VISIBLE);
        tvNoHistory.setVisibility(View.GONE);
        GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + transferLogEntity.getOriginalUserEntity().getAccountEntity().getAvatar()),ivHeader);
        tvOrderNum.setText(transferLogEntity.getOrderNum() + "");
        tvOrderTime.setText(GetDateUtils.dateToDateTimeString(transferLogEntity.getCreateTime()));
        tvOrderReason.setText(GetConstDataUtils.getTransferCauseList().get(transferLogEntity.getCause()));
    }
}
