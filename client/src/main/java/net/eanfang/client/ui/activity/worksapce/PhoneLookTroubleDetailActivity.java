package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.BuildConfig;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  11:24
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class PhoneLookTroubleDetailActivity extends BaseClientActivity /*implements View.OnClickListener */ {
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
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_title)
    ImageView ivTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_trouble_title)
    TextView tvTroubleTitle;
    @BindView(R.id.tv_trouble_device)
    TextView tvTroubleDevice;
    @BindView(R.id.rl_trouble_device)
    RelativeLayout rlTroubleDevice;
    @BindView(R.id.tv_brand_model)
    TextView tvBrandModel;
    @BindView(R.id.rl_brand_model)
    RelativeLayout rlBrandModel;
    @BindView(R.id.tv_device_no)
    TextView tvDeviceNo;
    @BindView(R.id.rl_device_no)
    RelativeLayout rlDeviceNo;
    @BindView(R.id.tv_device_location)
    TextView tvDeviceLocation;
    @BindView(R.id.rl_device_location)
    RelativeLayout rlDeviceLocation;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.rel_parame)
    RelativeLayout relParame;
    @BindView(R.id.rcy_parameter)
    RecyclerView rcyParameter;
    @BindView(R.id.rel_result)
    RelativeLayout relResult;
    @BindView(R.id.et_trouble_desc)
    TextView etTroubleDesc;
    @BindView(R.id.ll_trouble_desc)
    LinearLayout llTroubleDesc;
    @BindView(R.id.et_trouble_point)
    TextView etTroublePoint;
    @BindView(R.id.ll_trouble_point)
    LinearLayout llTroublePoint;
    @BindView(R.id.et_trouble_reason)
    TextView etTroubleReason;
    @BindView(R.id.ll_trouble_reason)
    LinearLayout llTroubleReason;
    @BindView(R.id.et_trouble_deal)
    TextView etTroubleDeal;
    @BindView(R.id.ll_trouble_deal)
    LinearLayout llTroubleDeal;
    @BindView(R.id.tv_repair_conclusion)
    TextView tvRepairConclusion;
    @BindView(R.id.ll_repair_conclusion)
    LinearLayout llRepairConclusion;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;
    @BindView(R.id.snpl_monitor_add_photos)
    BGASortableNinePhotoLayout snplMonitorAddPhotos;
    @BindView(R.id.snpl_tools_package_add_photos)
    BGASortableNinePhotoLayout snplToolsPackageAddPhotos;
    @BindView(R.id.snpl_after_processing_locale)
    BGASortableNinePhotoLayout snplAfterProcessingLocale;
    @BindView(R.id.snpl_machine_fit_back)
    BGASortableNinePhotoLayout snplMachineFitBack;
    @BindView(R.id.snpl_failure_recover_phenomena)
    BGASortableNinePhotoLayout snplFailureRecoverPhenomena;
    @BindView(R.id.tv_use_goods)
    TextView tvUseGoods;
    @BindView(R.id.tv_add_consumable)
    TextView tvAddConsumable;
    @BindView(R.id.rcy_consumable)
    RecyclerView rcyConsumable;
    @BindView(R.id.btn_add_trouble)
    Button btnAddTrouble;
    @BindView(R.id.ll_gone_c)
    LinearLayout llGoneC;

    private List<BughandleParamEntity> mDataList;
    private List<BughandleUseDeviceEntity> mDataList_2;
    private LookParamAdapter paramAdapter;
    private HashMap<String, String> uploadMap = new HashMap<>();
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
        ButterKnife.bind(this);
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
            picList1.addAll(Stream.of(Arrays.asList(prePic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }
        if (StringUtils.isValid(bughandleDetailEntity.getToolPictures())) {
            String[] toolPic = bughandleDetailEntity.getToolPictures().split(Constant.IMG_SPLIT);
            picList2.addAll(Stream.of(Arrays.asList(toolPic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }
        if (StringUtils.isValid(bughandleDetailEntity.getPointPictures())) {
            String[] ponitPic = bughandleDetailEntity.getPointPictures().split(Constant.IMG_SPLIT);
            picList3.addAll(Stream.of(Arrays.asList(ponitPic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }
        if (StringUtils.isValid(bughandleDetailEntity.getAfterHandlePictures())) {
            String[] afterHandlePic = bughandleDetailEntity.getAfterHandlePictures().split(Constant.IMG_SPLIT);
            picList4.addAll(Stream.of(Arrays.asList(afterHandlePic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }
        if (StringUtils.isValid(bughandleDetailEntity.getDeviceReturnInstallPictures())) {
            String[] returnInstallPic = bughandleDetailEntity.getDeviceReturnInstallPictures().split(Constant.IMG_SPLIT);
            picList5.addAll(Stream.of(Arrays.asList(returnInstallPic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }
        if (StringUtils.isValid(bughandleDetailEntity.getRestorePictures())) {
            String[] restorePic = bughandleDetailEntity.getRestorePictures().split(Constant.IMG_SPLIT);
            picList6.addAll(Stream.of(Arrays.asList(restorePic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
            Collections.addAll(picList6, restorePic);
        }
    }

    private void getData() {
        EanfangHttp.get(RepairApi.GET_BUGHANDLE_DETAIL_INFO)
                .params(Constant.ID, id)
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
            rcyParameter.setAdapter(paramAdapter);
        } else {
            rcyParameter.setVisibility(View.GONE);
            relParame.setVisibility(View.GONE);
        }
        if (mDataList_2.size() != 0) {
            rcyConsumable.setAdapter(materialAdapter);
        } else {
            //无数据时隐藏
            rcyConsumable.setVisibility(View.GONE);
            tvUseGoods.setVisibility(View.GONE);
        }


        if (bughandleDetailEntity.getFailureEntity().getBusinessThreeCode() != null) {
            tvTroubleTitle.setText(config.getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 3));
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
            tvDeviceLocation.setText(bughandleDetailEntity.getFailureEntity().getBugPosition());
        }
        //故障描述
        if (StringUtils.isValid(bughandleDetailEntity.getFailureEntity().getBugDescription())) {
            etTroubleDesc.setText(bughandleDetailEntity.getFailureEntity().getBugDescription());
        }
        //检查方法
        if (StringUtils.isValid(bughandleDetailEntity.getCheckProcess())) {
            etTroublePoint.setText(bughandleDetailEntity.getCheckProcess());
        }
        //故障原因
        if (StringUtils.isValid(bughandleDetailEntity.getCause())) {
            etTroubleReason.setText(bughandleDetailEntity.getCause());
        }
        //故障处理
        if (StringUtils.isValid(bughandleDetailEntity.getHandle())) {
            etTroubleDeal.setText(bughandleDetailEntity.getHandle());
        }
        //维修结论
        if (bughandleDetailEntity.getStatus() != null) {
            tvRepairConclusion.setText(constDataUtils.getBugDetailList().get(bughandleDetailEntity.getStatus()));
        }
    }


    private void initView() {
        id = getIntent().getLongExtra(Constant.ID, 0);
        tvAdd.setVisibility(View.INVISIBLE);
        tvAddConsumable.setVisibility(View.INVISIBLE);
        btnAddTrouble.setVisibility(View.INVISIBLE);
        supprotToolbar();
        setTitle("故障明细");
    }

    private void initAdapter() {
        materialAdapter = new LookMaterialAdapter(R.layout.item_quotation_detail, mDataList_2);
        rcyConsumable.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcyConsumable.setLayoutManager(new LinearLayoutManager(this));
        rcyConsumable.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                new MateraInfoView(PhoneLookTroubleDetailActivity.this, true,
                        mDataList_2.get(position)).show();
            }
        });


        paramAdapter = new LookParamAdapter(R.layout.item_look_parm, (ArrayList) mDataList);
        rcyParameter.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcyParameter.setLayoutManager(new LinearLayoutManager(this));
        rcyParameter.setAdapter(paramAdapter);
    }

    private void initNinePhoto() {
        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
        snplMonitorAddPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_2, REQUEST_CODE_PHOTO_PREVIEW_2));
        snplToolsPackageAddPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_3, REQUEST_CODE_PHOTO_PREVIEW_3));
        snplAfterProcessingLocale.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_4, REQUEST_CODE_PHOTO_PREVIEW_4));
        snplMachineFitBack.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_5, REQUEST_CODE_PHOTO_PREVIEW_5));
        snplFailureRecoverPhenomena.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_6, REQUEST_CODE_PHOTO_PREVIEW_6));

        snplMomentAddPhotos.setData(picList1);
        snplMonitorAddPhotos.setData(picList2);
        snplToolsPackageAddPhotos.setData(picList3);
        //2017年7月21日
        snplAfterProcessingLocale.setData(picList4);
        snplMachineFitBack.setData(picList5);
        snplFailureRecoverPhenomena.setData(picList6);

        snplMomentAddPhotos.setEditable(false);
        snplMonitorAddPhotos.setEditable(false);
        snplToolsPackageAddPhotos.setEditable(false);
        //2017年7月21日
        snplAfterProcessingLocale.setEditable(false);
        snplMachineFitBack.setEditable(false);
        snplFailureRecoverPhenomena.setEditable(false);


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
            //2017年7月21日
            case REQUEST_CODE_CHOOSE_PHOTO_4:
                snplAfterProcessingLocale.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_5:
                snplMachineFitBack.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_6:
                snplFailureRecoverPhenomena.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
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
                snplAfterProcessingLocale.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_5:
                snplMachineFitBack.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_6:
                snplFailureRecoverPhenomena.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
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

