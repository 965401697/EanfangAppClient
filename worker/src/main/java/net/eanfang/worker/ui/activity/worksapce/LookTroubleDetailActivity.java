package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Config;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.BughandleDetailEntity;
import com.yaf.base.entity.BughandleParamEntity;
import com.yaf.base.entity.BughandleUseDeviceEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.LookMaterialAdapter;
import net.eanfang.worker.ui.adapter.LookParamAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.ui.widget.MateraInfoView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  11:05
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class LookTroubleDetailActivity extends BaseWorkerActivity /*implements View.OnClickListener */ {
    public static final String TAG = LookTroubleDetailActivity.class.getSimpleName();

    private static final int REQUEST_CODE_CHOOSE_PHOTO_1 = 1;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_2 = 2;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_3 = 3;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_4 = 4;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_5 = 5;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_6 = 6;

    private static final int REQUEST_CODE_PHOTO_PREVIEW_1 = 7;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_2 = 8;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_3 = 9;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_4 = 10;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_5 = 11;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_6 = 12;

    private TextView tv_trouble_device;
    private TextView tv_brand_model;
    private TextView tv_device_no;
    private TextView tv_device_location;
    private TextView tv_add;
    private RecyclerView rcy_parameter;
    private TextView et_trouble_desc;
    private TextView et_trouble_point;
    private TextView et_trouble_reason;
    private TextView et_trouble_deal;
    private TextView tv_use_goods;//耗用材料
    /**
     * 故障表象 （3张）
     */
    private BGASortableNinePhotoLayout snpl_moment_add_photos;
    /**
     * 工具及蓝布 （3张）
     */
    private BGASortableNinePhotoLayout snpl_monitor_add_photos;
    /**
     * 故障点照片 （3张）
     */
    private BGASortableNinePhotoLayout snpl_tools_package_add_photos;
    /**
     * 处理后现场 （3张）
     */
    private BGASortableNinePhotoLayout snpl_after_processing_locale;
    /**
     * 设备回装 （3张）
     */
    private BGASortableNinePhotoLayout snpl_machine_fit_back;
    /**
     * 故障恢复后表象 （3张）
     */
    private BGASortableNinePhotoLayout snpl_failure_recover_phenomena;

    private TextView tv_add_consumable;
    private RecyclerView rcy_consumable;
    private Button btn_add_trouble;
    private TextView tv_trouble_title;
    private List<BughandleParamEntity> mDataList;
    private List<BughandleUseDeviceEntity> mDataList_2;
    private LookParamAdapter paramAdapter;
    private HashMap<String, String> uploadMap;
    //2017年7月21日
    /**
     * 维修结果
     */
    private TextView tv_repair_conclusion;
    /**
     * 故障表象 （3张）
     */
    private ArrayList<String> picList1;
    /**
     * 工具及蓝布 （3张）
     */
    private ArrayList<String> picList2;
    /**
     * 故障点照片 （3张）
     */
    private ArrayList<String> picList3;
    //2017年7月21日
    /**
     * 处理后现场 （3张）
     */
    private ArrayList<String> picList4;
    /**
     * 设备回装 （3张）
     */
    private ArrayList<String> picList5;
    /**
     * 故障恢复后表象 （3张）
     */
    private ArrayList<String> picList6;
    private int position;
    private LookMaterialAdapter materialAdapter;
    private BughandleDetailEntity bughandleDetailEntity;
    private RelativeLayout rel_parame;
    /**
     * 2017年8月1日
     * 故障明细的id
     */
    private Integer detailId;
    private Long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_trouble_detail);
        initView();
        getData();
    }

    /**
     * 初始化存储图片用的List集合
     */
    private void initImgUrlList() {
        uploadMap = new HashMap<>();
        picList1 = new ArrayList<>();
        picList2 = new ArrayList<>();
        picList3 = new ArrayList<>();
        //2017年7月21日
        picList4 = new ArrayList<>();
        picList5 = new ArrayList<>();
        picList6 = new ArrayList<>();
        //修改小bug 图片读取问题
        if (StringUtils.isValid(bughandleDetailEntity.getPresentationPictures())) {
            String[] prePic = bughandleDetailEntity.getPresentationPictures().split(",");
            picList1.addAll(Stream.of(Arrays.asList(prePic)).map(url -> (BuildConfig.OSS_BUCKET + url).toString()).toList());

        }
        if (StringUtils.isValid(bughandleDetailEntity.getToolPictures())) {
            String[] toolPic = bughandleDetailEntity.getToolPictures().split(",");
            picList2.addAll(Stream.of(Arrays.asList(toolPic)).map(url -> (BuildConfig.OSS_BUCKET + url).toString()).toList());
        }
        if (StringUtils.isValid(bughandleDetailEntity.getPointPictures())) {
            String[] ponitPic = bughandleDetailEntity.getPointPictures().split(",");
            picList3.addAll(Stream.of(Arrays.asList(ponitPic)).map(url -> (BuildConfig.OSS_BUCKET + url).toString()).toList());
        }
        if (StringUtils.isValid(bughandleDetailEntity.getAfterHandlePictures())) {
            String[] afterHandlePic = bughandleDetailEntity.getAfterHandlePictures().split(",");
            picList4.addAll(Stream.of(Arrays.asList(afterHandlePic)).map(url -> (BuildConfig.OSS_BUCKET + url).toString()).toList());
        }
        if (StringUtils.isValid(bughandleDetailEntity.getDeviceReturnInstallPictures())) {
            String[] returnInstallPic = bughandleDetailEntity.getDeviceReturnInstallPictures().split(",");
            picList5.addAll(Stream.of(Arrays.asList(returnInstallPic)).map(url -> (BuildConfig.OSS_BUCKET + url).toString()).toList());
        }
        if (StringUtils.isValid(bughandleDetailEntity.getRestorePictures())) {
            String[] restorePic = bughandleDetailEntity.getRestorePictures().split(",");
            picList6.addAll(Stream.of(Arrays.asList(restorePic)).map(url -> (BuildConfig.OSS_BUCKET + url).toString()).toList());
        }

    }

    private void getData() {
        EanfangHttp.get(RepairApi.GET_BUGHANDLE_DETAIL_INFO)
                .params("id", id)
                .execute(new EanfangCallback<BughandleDetailEntity>(this, true, BughandleDetailEntity.class, (bean) -> {
                    bughandleDetailEntity = bean;
                    initData(bean);
                }));

    }


    public void initData(BughandleDetailEntity bughandleDetailEntity) {
        mDataList = bughandleDetailEntity.getParamEntityList();
        mDataList_2 = bughandleDetailEntity.getUseDeviceEntityList();
        initImgUrlList();
        initNinePhoto();
        initAdapter();

        if (mDataList.size() != 0) {
            rcy_parameter.setAdapter(paramAdapter);
        } else {
            rcy_parameter.setVisibility(View.GONE);
            rel_parame.setVisibility(View.GONE);
        }
        if (mDataList_2.size() != 0) {
            rcy_consumable.setAdapter(materialAdapter);
        } else {
            //无数据时隐藏
            rcy_consumable.setVisibility(View.GONE);
            tv_use_goods.setVisibility(View.GONE);
        }


        if (bughandleDetailEntity.getFailureEntity() != null) {
            if (StringUtils.isValid(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode())) {
                String bugOne = Config.get().getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 1);
                String bugTwo = Config.get().getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 2);
                String bugThree = Config.get().getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 3);
                tv_trouble_title.setText(bugOne + "-" + bugTwo + "-" + bugThree);
            } else {
                tv_trouble_title.setText("");
            }

            tv_trouble_device.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getDeviceName()).orElse(""));

            tv_brand_model.setText(Optional.ofNullable(Config.get().getModelNameByCode(bughandleDetailEntity.getFailureEntity().getModelCode(), 2)).orElse(""));

            tv_device_no.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getDeviceNo()).orElse(""));

            tv_device_location.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getBugPosition()).orElse(""));

            et_trouble_desc.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getBugDescription()).orElse(""));
        }
        et_trouble_point.setText(Optional.ofNullable(bughandleDetailEntity.getCheckProcess()).orElse(""));
        et_trouble_reason.setText(Optional.ofNullable(bughandleDetailEntity.getCause()).orElse(""));
        et_trouble_deal.setText(Optional.ofNullable(bughandleDetailEntity.getHandle()).orElse(""));
        if (bughandleDetailEntity.getStatus() != null) {
            tv_repair_conclusion.setText(Optional.ofNullable(GetConstDataUtils.getBugDetailList().get(bughandleDetailEntity.getStatus())).orElse(""));
        }
    }


    private void initView() {
        id = getIntent().getLongExtra("id", 0);
        rel_parame = (RelativeLayout) findViewById(R.id.rel_parame);
        //add by hou on 2017.8.2
        tv_use_goods = (TextView) findViewById(R.id.tv_use_goods);
        tv_trouble_device = (TextView) findViewById(R.id.tv_trouble_device);
        tv_brand_model = (TextView) findViewById(R.id.tv_brand_model);
        tv_device_no = (TextView) findViewById(R.id.tv_device_no);
        tv_device_location = (TextView) findViewById(R.id.tv_device_location);
        tv_add = (TextView) findViewById(R.id.tv_add);
        rcy_parameter = (RecyclerView) findViewById(R.id.rcy_parameter);
        et_trouble_desc = (TextView) findViewById(R.id.et_trouble_desc);
        et_trouble_point = (TextView) findViewById(R.id.et_trouble_point);
        et_trouble_reason = (TextView) findViewById(R.id.et_trouble_reason);
        et_trouble_deal = (TextView) findViewById(R.id.et_trouble_deal);
        //2017年7月20日
        //维修结论
        tv_repair_conclusion = (TextView) findViewById(R.id.tv_repair_conclusion);
        snpl_moment_add_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_moment_add_photos);
        snpl_monitor_add_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_monitor_add_photos);
        snpl_tools_package_add_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_tools_package_add_photos);
        //2017年7月21日
        snpl_after_processing_locale = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_after_processing_locale);
        snpl_machine_fit_back = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_machine_fit_back);
        snpl_failure_recover_phenomena = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_failure_recover_phenomena);
        tv_add_consumable = (TextView) findViewById(R.id.tv_add_consumable);
        rcy_consumable = (RecyclerView) findViewById(R.id.rcy_consumable);
        btn_add_trouble = (Button) findViewById(R.id.btn_add_trouble);
        tv_trouble_title = (TextView) findViewById(R.id.tv_trouble_title);
        tv_add.setVisibility(View.INVISIBLE);
        tv_add_consumable.setVisibility(View.INVISIBLE);
        btn_add_trouble.setVisibility(View.INVISIBLE);
        supprotToolbar();
        setTitle("故障明细");
    }

    private void initAdapter() {
        materialAdapter = new LookMaterialAdapter(R.layout.item_quotation_detail, mDataList_2);
        rcy_consumable.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcy_consumable.setLayoutManager(new LinearLayoutManager(this));
        rcy_consumable.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                new MateraInfoView(LookTroubleDetailActivity.this, true,
                        mDataList_2.get(position)).show();
            }
        });


        paramAdapter = new LookParamAdapter(R.layout.item_look_parm, (ArrayList) mDataList);
        rcy_parameter.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcy_parameter.setLayoutManager(new LinearLayoutManager(this));
        rcy_parameter.setAdapter(paramAdapter);
    }

    private void initNinePhoto() {
        snpl_moment_add_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
        snpl_monitor_add_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_2, REQUEST_CODE_PHOTO_PREVIEW_2));
        snpl_tools_package_add_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_3, REQUEST_CODE_PHOTO_PREVIEW_3));
        snpl_after_processing_locale.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_4, REQUEST_CODE_PHOTO_PREVIEW_4));
        snpl_machine_fit_back.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_5, REQUEST_CODE_PHOTO_PREVIEW_5));
        snpl_failure_recover_phenomena.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_6, REQUEST_CODE_PHOTO_PREVIEW_6));

        snpl_moment_add_photos.setData(picList1);
        snpl_monitor_add_photos.setData(picList2);
        snpl_tools_package_add_photos.setData(picList3);
        //2017年7月21日
        snpl_after_processing_locale.setData(picList4);
        snpl_machine_fit_back.setData(picList5);
        snpl_failure_recover_phenomena.setData(picList6);

        snpl_moment_add_photos.setEditable(false);
        snpl_monitor_add_photos.setEditable(false);
        snpl_tools_package_add_photos.setEditable(false);
        //2017年7月21日
        snpl_after_processing_locale.setEditable(false);
        snpl_machine_fit_back.setEditable(false);
        snpl_failure_recover_phenomena.setEditable(false);


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
            //2017年7月21日
            case REQUEST_CODE_CHOOSE_PHOTO_4:
                snpl_after_processing_locale.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_5:
                snpl_machine_fit_back.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_6:
                snpl_failure_recover_phenomena.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
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
                snpl_after_processing_locale.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_5:
                snpl_machine_fit_back.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_6:
                snpl_failure_recover_phenomena.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
//            case 10009:
//                FillRepairInfoBean.BughandledetaillistBean.BughandledetailmateriallistBean bugBean = (FillRepairInfoBean.BughandledetaillistBean.BughandledetailmateriallistBean) data.getSerializableExtra("bean");
////                bean.getBughandledetailmateriallist().add(bugBean);
//                materialAdapter.notifyDataSetChanged();
//                break;
            default:
                break;
        }
    }

}

