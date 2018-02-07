package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
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

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.LookMaterialAdapter;
import net.eanfang.client.ui.adapter.LookParamAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.widget.MateraInfoView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  11:05
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class LookTroubleDetailActivity extends BaseClientActivity /*implements View.OnClickListener */ {
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
    //2017年7月21日
    /**
     * 维修结果
     */
    private TextView tv_repair_conclusion;
    /**
     * 故障表象 （3张）
     */
    private ArrayList<String> picList1 = new ArrayList<>();
    /**
     * 工具及蓝布 （3张）
     */
    private ArrayList<String> picList2 = new ArrayList<>();
    /**
     * 故障点照片 （3张）
     */
    private ArrayList<String> picList3 = new ArrayList<>();
    //2017年7月21日
    /**
     * 处理后现场 （3张）
     */
    private ArrayList<String> picList4 = new ArrayList<>();
    /**
     * 设备回装 （3张）
     */
    private ArrayList<String> picList5 = new ArrayList<>();
    /**
     * 故障恢复后表象 （3张）
     */
    private ArrayList<String> picList6 = new ArrayList<>();

    private LookMaterialAdapter materialAdapter;
    private BughandleDetailEntity bughandleDetailEntity;
    private RelativeLayout rel_parame;
    /**
     * 2017年8月1日
     * 故障明细的id
     */
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

        //修改小bug 图片读取问题
        if (StringUtils.isValid(bughandleDetailEntity.getPresentationPictures())) {
            String[] prePic = bughandleDetailEntity.getPresentationPictures().split(Constant.IMG_SPLIT);
            Collections.addAll(picList1, prePic);
        }
        if (StringUtils.isValid(bughandleDetailEntity.getToolPictures())) {
            String[] toolPic = bughandleDetailEntity.getToolPictures().split(Constant.IMG_SPLIT);
            Collections.addAll(picList2, toolPic);
        }
        if (StringUtils.isValid(bughandleDetailEntity.getPointPictures())) {
            String[] ponitPic = bughandleDetailEntity.getPointPictures().split(Constant.IMG_SPLIT);
            Collections.addAll(picList3, ponitPic);
        }
        if (StringUtils.isValid(bughandleDetailEntity.getAfterHandlePictures())) {
            String[] afterHandlePic = bughandleDetailEntity.getAfterHandlePictures().split(Constant.IMG_SPLIT);
            Collections.addAll(picList4, afterHandlePic);
        }
        if (StringUtils.isValid(bughandleDetailEntity.getDeviceReturnInstallPictures())) {
            String[] returnInstallPic = bughandleDetailEntity.getDeviceReturnInstallPictures().split(Constant.IMG_SPLIT);
            Collections.addAll(picList5, returnInstallPic);
        }
        if (StringUtils.isValid(bughandleDetailEntity.getRestorePictures())) {
            String[] restorePic = bughandleDetailEntity.getRestorePictures().split(Constant.IMG_SPLIT);
            Collections.addAll(picList6, restorePic);
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

        if (bughandleDetailEntity.getFailureEntity().getBusinessThreeCode() != null) {
            tv_trouble_title.setText(Config.get().getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 3));
        }
//        //故障设备
//        if (StringUtils.isValid(bughandleDetailEntity.getInstrument())) {
//            tv_trouble_device.setText(bean.getInstrument());
//        }
//        //品牌型号
//        if (StringUtils.isValid(bean.getModelnum())) {
//            tv_brand_model.setText(bean.getModelnum());
//        }
//        //设备编号
//        if (StringUtils.isValid(bean.getEquipmentcode())) {
//            tv_device_no.setText(bean.getEquipmentcode());
//        }
        //故障位置
        if (StringUtils.isValid(bughandleDetailEntity.getFailureEntity().getBugPosition())) {
            tv_device_location.setText(bughandleDetailEntity.getFailureEntity().getBugPosition());
        }
        //故障描述
        if (StringUtils.isValid(bughandleDetailEntity.getFailureEntity().getBugDescription())) {
            et_trouble_desc.setText(bughandleDetailEntity.getFailureEntity().getBugDescription());
        }
        //检查方法
        if (StringUtils.isValid(bughandleDetailEntity.getCheckProcess())) {
            et_trouble_point.setText(bughandleDetailEntity.getCheckProcess());
        }
        //故障原因
        if (StringUtils.isValid(bughandleDetailEntity.getCause())) {
            et_trouble_reason.setText(bughandleDetailEntity.getCause());
        }
        //故障处理
        if (StringUtils.isValid(bughandleDetailEntity.getHandle())) {
            et_trouble_deal.setText(bughandleDetailEntity.getHandle());
        }
        //维修结论
        if (bughandleDetailEntity.getStatus() != null) {
            tv_repair_conclusion.setText(GetConstDataUtils.getBugDetailList().get(bughandleDetailEntity.getStatus()));
        }
    }


    private void initView() {
        id = getIntent().getLongExtra(Constant.ID, 0);
        rel_parame = findViewById(R.id.rel_parame);
        //add by hou on 2017.8.2
        tv_use_goods = findViewById(R.id.tv_use_goods);
        tv_trouble_device = findViewById(R.id.tv_trouble_device);
        tv_brand_model = findViewById(R.id.tv_brand_model);
        tv_device_no = findViewById(R.id.tv_device_no);
        tv_device_location = findViewById(R.id.tv_device_location);
        tv_add = findViewById(R.id.tv_add);
        rcy_parameter = findViewById(R.id.rcy_parameter);
        et_trouble_desc = findViewById(R.id.et_trouble_desc);
        et_trouble_point = findViewById(R.id.et_trouble_point);
        et_trouble_reason = findViewById(R.id.et_trouble_reason);
        et_trouble_deal = findViewById(R.id.et_trouble_deal);
        //2017年7月20日
        //维修结论
        tv_repair_conclusion = findViewById(R.id.tv_repair_conclusion);
        snpl_moment_add_photos = findViewById(R.id.snpl_moment_add_photos);
        snpl_monitor_add_photos = findViewById(R.id.snpl_monitor_add_photos);
        snpl_tools_package_add_photos = findViewById(R.id.snpl_tools_package_add_photos);
        //2017年7月21日
        snpl_after_processing_locale = findViewById(R.id.snpl_after_processing_locale);
        snpl_machine_fit_back = findViewById(R.id.snpl_machine_fit_back);
        snpl_failure_recover_phenomena = findViewById(R.id.snpl_failure_recover_phenomena);
        tv_add_consumable = findViewById(R.id.tv_add_consumable);
        rcy_consumable = findViewById(R.id.rcy_consumable);
        btn_add_trouble = findViewById(R.id.btn_add_trouble);
        tv_trouble_title = findViewById(R.id.tv_trouble_title);
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
