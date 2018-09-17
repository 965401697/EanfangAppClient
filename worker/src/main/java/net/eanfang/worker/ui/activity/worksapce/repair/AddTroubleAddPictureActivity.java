package net.eanfang.worker.ui.activity.worksapce.repair;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.eanfang.BuildConfig;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.BughandleDetailEntity;

import net.eanfang.worker.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/6/7  16:29
 * @decision 电话未解决 完善故障 增加照片
 */
public class AddTroubleAddPictureActivity extends BaseActivity {

    private static final int REQUEST_CODE_CHOOSE_PHOTO_1 = 1;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_2 = 2;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_3 = 3;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_4 = 4;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_5 = 5;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_6 = 6;

    private static final int REQUEST_CODE_PHOTO_PREVIEW_1 = 101;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_2 = 102;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_3 = 103;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_4 = 104;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_5 = 105;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_6 = 106;
    /**
     * 故障表象 （3张）
     */
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;
    /**
     * 工具及蓝布 （3张）
     */
    @BindView(R.id.snpl_monitor_add_photos)
    BGASortableNinePhotoLayout snplMonitorAddPhotos;
    /**
     * 故障点照片 （3张）
     */
    @BindView(R.id.snpl_tools_package_add_photos)
    BGASortableNinePhotoLayout snplToolsPackageAddPhotos;
    /**
     * 处理后现场 （3张）
     */
    @BindView(R.id.snpl_after_processing_locale)
    BGASortableNinePhotoLayout snplAfterProcessingLocale;
    /**
     * 设备回装 （3张）
     */
    @BindView(R.id.snpl_machine_fit_back)
    BGASortableNinePhotoLayout snplMachineFitBack;
    /**
     * 故障恢复后表象 （3张）
     */
    @BindView(R.id.snpl_failure_recover_phenomena)
    BGASortableNinePhotoLayout snplFailureRecoverPhenomena;
    @BindView(R.id.btn_add_pic)
    Button btnAddPic;

    private BughandleDetailEntity detailEntity;
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

    private HashMap<String, String> uploadMap = new HashMap<>();

    private boolean isLoad = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trouble_add_picture);
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initView();
        initListener();
    }


    private void initView() {
        setTitle("添加照片");
        setLeftBack();
        isLoad = getIntent().getBooleanExtra("isLoad", false);
        uploadMap = (HashMap<String, String>) getIntent().getSerializableExtra("mUploadMapPicture");
        //是否加载记录
        if (isLoad) {
            detailEntity = (BughandleDetailEntity) getIntent().getSerializableExtra("detailEntity");
        } else {
            if (uploadMap.size() > 0) {
                detailEntity = (BughandleDetailEntity) getIntent().getSerializableExtra("detailEntity");
            } else {
                detailEntity = new BughandleDetailEntity();
            }
        }
        initImgUrlList();
        initNinePhoto();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void initListener() {
        btnAddPic.setOnClickListener((v) -> {
            doSubmitData();
        });
    }

    private void doSubmitData() {
        //故障表象 （3张）
        String presentationPic = PhotoUtils.getPhotoUrl(snplMomentAddPhotos, uploadMap, false);
        if (StringUtils.isEmpty(presentationPic)) {
            showToast("请选择故障表象照片");
            return;
        }
        detailEntity.setPresentationPictures(presentationPic);
        //故障点照片 （3张）
        String pointPic = PhotoUtils.getPhotoUrl(snplToolsPackageAddPhotos, uploadMap, false);
        if (StringUtils.isEmpty(pointPic)) {
            showToast("请选择故障点照片");
            return;
        }
        detailEntity.setPointPictures(pointPic);
        //恢复后表象 （3张）
        String restorePic = PhotoUtils.getPhotoUrl(snplFailureRecoverPhenomena, uploadMap, false);
        if (StringUtils.isEmpty(restorePic)) {
            showToast("请选择恢复后表象照片");
            return;
        }
        detailEntity.setRestorePictures(restorePic);
        //工具及蓝布 （3张）
        String toolPic = PhotoUtils.getPhotoUrl(snplMonitorAddPhotos, uploadMap, false);
        detailEntity.setToolPictures(toolPic);

        //处理后现场 （3张）
        String afterHandlePic = PhotoUtils.getPhotoUrl(snplAfterProcessingLocale, uploadMap, false);
        detailEntity.setAfterHandlePictures(afterHandlePic);
        //设备回装 （3张）
        String deviceReturnInstallPic = PhotoUtils.getPhotoUrl(snplMachineFitBack, uploadMap, false);
        detailEntity.setDeviceReturnInstallPictures(deviceReturnInstallPic);

        /**
         * 提交照片
         * */
        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                        EventBus.getDefault().post(detailEntity);
                        EventBus.getDefault().post(uploadMap);
                        finishSelf();
                    });
                }
            });
            return;
        } else if (isLoad) {
            finishSelf();
        }

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
        snplAfterProcessingLocale.setData(picList4);
        snplMachineFitBack.setData(picList5);
        snplFailureRecoverPhenomena.setData(picList6);
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
            default:
                break;
        }
    }

    /**
     * 初始化存储图片用的List集合
     */
    private void initImgUrlList() {
        //修改小bug 图片读取问题
        if (!StringUtils.isEmpty(detailEntity.getPresentationPictures())) {
            String[] presentationPic = detailEntity.getPresentationPictures().split(",");
            if (presentationPic.length >= 1) {
                picList1.add(BuildConfig.OSS_SERVER + presentationPic[0]);
            }
            if (presentationPic.length >= 2) {
                picList1.add(BuildConfig.OSS_SERVER + presentationPic[1]);
            }
            if (presentationPic.length >= 3) {
                picList1.add(BuildConfig.OSS_SERVER + presentationPic[2]);
            }
        }

        if (!StringUtils.isEmpty(detailEntity.getToolPictures())) {
            String[] toolPic = detailEntity.getToolPictures().split(",");
            if (toolPic.length >= 1) {
                picList2.add(BuildConfig.OSS_SERVER + toolPic[0]);
            }
            if (toolPic.length >= 2) {
                picList2.add(BuildConfig.OSS_SERVER + toolPic[1]);
            }
            if (toolPic.length >= 3) {
                picList2.add(BuildConfig.OSS_SERVER + toolPic[2]);
            }
        }
        if (!StringUtils.isEmpty(detailEntity.getPointPictures())) {
            String[] pointPic = detailEntity.getPointPictures().split(",");
            if (pointPic.length >= 1) {
                picList3.add(BuildConfig.OSS_SERVER + pointPic[0]);
            }
            if (pointPic.length >= 2) {
                picList3.add(BuildConfig.OSS_SERVER + pointPic[1]);
            }
            if (pointPic.length >= 3) {
                picList3.add(BuildConfig.OSS_SERVER + pointPic[2]);
            }
        }
        if (!StringUtils.isEmpty(detailEntity.getAfterHandlePictures())) {
            String[] afterHandlePic = detailEntity.getAfterHandlePictures().split(",");
            if (afterHandlePic.length >= 1) {
                picList4.add(BuildConfig.OSS_SERVER + afterHandlePic[0]);
            }
            if (afterHandlePic.length >= 2) {
                picList4.add(BuildConfig.OSS_SERVER + afterHandlePic[1]);
            }
            if (afterHandlePic.length >= 3) {
                picList4.add(BuildConfig.OSS_SERVER + afterHandlePic[2]);
            }
        }
        if (!StringUtils.isEmpty(detailEntity.getDeviceReturnInstallPictures())) {
            String[] deviceReturnInstallPic = detailEntity.getDeviceReturnInstallPictures().split(",");
            if (deviceReturnInstallPic.length >= 1) {
                picList5.add(BuildConfig.OSS_SERVER + deviceReturnInstallPic[0]);
            }
            if (deviceReturnInstallPic.length >= 2) {
                picList5.add(BuildConfig.OSS_SERVER + deviceReturnInstallPic[1]);
            }
            if (deviceReturnInstallPic.length >= 3) {
                picList5.add(BuildConfig.OSS_SERVER + deviceReturnInstallPic[2]);
            }
        }
        if (!StringUtils.isEmpty(detailEntity.getRestorePictures())) {
            String[] restorePic = detailEntity.getRestorePictures().split(",");
            if (restorePic.length >= 1) {
                picList6.add(BuildConfig.OSS_SERVER + restorePic[0]);
            }
            if (restorePic.length >= 2) {
                picList6.add(BuildConfig.OSS_SERVER + restorePic[1]);
            }
            if (restorePic.length >= 3) {
                picList6.add(BuildConfig.OSS_SERVER + restorePic[2]);
            }
        }

    }


}
