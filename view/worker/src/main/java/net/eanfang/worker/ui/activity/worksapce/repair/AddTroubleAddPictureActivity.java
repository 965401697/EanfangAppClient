package net.eanfang.worker.ui.activity.worksapce.repair;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.delegate.BGASortableDelegate;

import com.eanfang.takevideo.TakeVdideoMode;
import com.eanfang.takevideo.TakeVideoActivity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.BughandleDetailEntity;

import net.eanfang.worker.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.MediaStore.Video.Thumbnails.MINI_KIND;

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
    @BindView(R.id.tv_addVideo_moment)
    TextView tvAddVideoMoment;
    @BindView(R.id.iv_thumbnail_moment)
    ImageView ivThumbnailMoment;
    @BindView(R.id.rl_thumbnail_moment)
    RelativeLayout rlThumbnailMoment;
    /**
     * 视频上传路径
     */
    private String mUploadKey_moment = "";
    /**
     * 工具及蓝布 （3张）
     */
    @BindView(R.id.snpl_monitor_add_photos)
    BGASortableNinePhotoLayout snplMonitorAddPhotos;
    @BindView(R.id.tv_addVideo_monitor)
    TextView tvAddVideoMonitor;
    @BindView(R.id.iv_thumbnail_monitor)
    ImageView ivThumbnailMonitor;
    @BindView(R.id.rl_thumbnail_monitor)
    RelativeLayout rlThumbnailMonitor;
    /**
     * 视频上传路径
     */
    private String mUploadKey_monitor = "";
    /**
     * 故障点照片 （3张）
     */
    @BindView(R.id.snpl_tools_package_add_photos)
    BGASortableNinePhotoLayout snplToolsPackageAddPhotos;
    @BindView(R.id.tv_addVideo_tools_package)
    TextView tvAddVideoToolsPackage;
    @BindView(R.id.iv_thumbnail_tools_package)
    ImageView ivThumbnailToolsPackage;
    @BindView(R.id.rl_thumbnail_tools_package)
    RelativeLayout rlThumbnailToolsPackage;
    /**
     * 视频上传路径
     */
    private String mUploadKey_tools_package = "";
    /**
     * 处理后现场 （3张）
     */
    @BindView(R.id.snpl_after_processing_locale)
    BGASortableNinePhotoLayout snplAfterProcessingLocale;
    @BindView(R.id.tv_addViedo_after)
    TextView tvAddViedoAfter;
    @BindView(R.id.iv_thumbnail_after)
    ImageView ivThumbnailAfter;
    @BindView(R.id.rl_thumbnail_after)
    RelativeLayout rlThumbnailAfter;
    /**
     * 视频上传路径
     */
    private String mUploadKey_after = "";
    /**
     * 设备回装 （3张）
     */
    @BindView(R.id.snpl_machine_fit_back)
    BGASortableNinePhotoLayout snplMachineFitBack;
    @BindView(R.id.tv_addVideo_machine)
    TextView tvAddVideoMachine;
    @BindView(R.id.iv_thumbnail_machine)
    ImageView ivThumbnailMachine;
    @BindView(R.id.rl_thumbnail_machine)
    RelativeLayout rlThumbnailMachine;
    /**
     * 视频上传路径
     */
    private String mUploadKey_machine = "";
    /**
     * 故障恢复后表象 （3张）
     */
    @BindView(R.id.snpl_failure_recover_phenomena)
    BGASortableNinePhotoLayout snplFailureRecoverPhenomena;
    @BindView(R.id.btn_add_pic)
    Button btnAddPic;
    @BindView(R.id.tv_addVideo_failure)
    TextView tvAddVideoFailure;
    @BindView(R.id.iv_thumbnail_failure)
    ImageView ivThumbnailFailure;
    @BindView(R.id.rl_thumbnail_failure)
    RelativeLayout rlThumbnailFailure;
    /**
     * 视频上传路径
     */
    private String mUploadKey_failure = "";


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
            //回显视频
            doShowVideo(detailEntity);
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

    //回显视频
    private void doShowVideo(BughandleDetailEntity detailEntity) {
        //故障表象
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
        //故障表象
        tvAddVideoMoment.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            bundle.putString("worker_add", "moment_addpicture");
            JumpItent.jump(AddTroubleAddPictureActivity.this, TakeVideoActivity.class, bundle);
        });
        //故障点照片
        tvAddVideoToolsPackage.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            bundle.putString("worker_add", "toolspackage");
            JumpItent.jump(AddTroubleAddPictureActivity.this, TakeVideoActivity.class, bundle);
        });
        //恢复后表象
        tvAddVideoFailure.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            bundle.putString("worker_add", "failure");
            JumpItent.jump(AddTroubleAddPictureActivity.this, TakeVideoActivity.class, bundle);
        });
        //工具及蓝布
        tvAddVideoMonitor.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            bundle.putString("worker_add", "monitor_addpicture");
            JumpItent.jump(AddTroubleAddPictureActivity.this, TakeVideoActivity.class, bundle);
        });
        //处理后现场
        tvAddViedoAfter.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            bundle.putString("worker_add", "after");
            JumpItent.jump(AddTroubleAddPictureActivity.this, TakeVideoActivity.class, bundle);
        });
        //设备回装
        tvAddVideoMachine.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", "addtrouble_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            bundle.putString("worker_add", "machine");
            JumpItent.jump(AddTroubleAddPictureActivity.this, TakeVideoActivity.class, bundle);
        });
    }

    private void doSubmitData() {
        //故障表象 （3张）
        String presentationPic = PhotoUtils.getPhotoUrl("biz/repair/bughandle/", snplMomentAddPhotos, uploadMap, false);
        if (StringUtils.isEmpty(presentationPic)) {
            showToast("请选择故障表象照片");
            return;
        }
        detailEntity.setPresentationPictures(presentationPic);
        detailEntity.setPresentation_mp4_path(mUploadKey_moment);
        //故障点照片 （3张）
        String pointPic = PhotoUtils.getPhotoUrl("biz/repair/bughandle/", snplToolsPackageAddPhotos, uploadMap, false);
        if (StringUtils.isEmpty(pointPic)) {
            showToast("请选择故障点照片");
            return;
        }
        detailEntity.setPointPictures(pointPic);
        detailEntity.setPoint_mp4_path(mUploadKey_tools_package);
        //恢复后表象 （3张）
        String restorePic = PhotoUtils.getPhotoUrl("biz/repair/bughandle/", snplFailureRecoverPhenomena, uploadMap, false);
        if (StringUtils.isEmpty(restorePic)) {
            showToast("请选择恢复后表象照片");
            return;
        }
        detailEntity.setRestorePictures(restorePic);
        detailEntity.setRestore_mp4_path(mUploadKey_failure);
        //工具及蓝布 （3张）
        String toolPic = PhotoUtils.getPhotoUrl("biz/repair/bughandle/", snplMonitorAddPhotos, uploadMap, false);
        detailEntity.setToolPictures(toolPic);
        detailEntity.setTool_mp4_path(mUploadKey_monitor);
        //处理后现场 （3张）
        String afterHandlePic = PhotoUtils.getPhotoUrl("biz/repair/bughandle/", snplAfterProcessingLocale, uploadMap, false);
        detailEntity.setAfterHandlePictures(afterHandlePic);
        detailEntity.setAfter_handle_mp4_path(mUploadKey_after);
        //设备回装 （3张）
        String deviceReturnInstallPic = PhotoUtils.getPhotoUrl("biz/repair/bughandle/", snplMachineFitBack, uploadMap, false);
        detailEntity.setDeviceReturnInstallPictures(deviceReturnInstallPic);
        detailEntity.setDevice_return_install_mp4_path(mUploadKey_machine);
        /**
         * 提交照片
         * */
        if (uploadMap.size() != 0) {
            SDKManager.ossKit(this).asyncPutImages(uploadMap,(isSuccess) -> {
                runOnUiThread(() -> {
                    EventBus.getDefault().post(detailEntity);
                    EventBus.getDefault().post(uploadMap);
                    finishSelf();
                });
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

    @Subscribe()//MAIN代表主线程
    public void receivePath(TakeVdideoMode takeVdideoMode) {
        if (takeVdideoMode != null) {
            String image = takeVdideoMode.getMImagePath();
            Bitmap mBitMap = PhotoUtils.getVideoThumbnail(image, 100, 100, MINI_KIND);
            if (takeVdideoMode.getMType().equals("moment_addpicture")) {// //故障表象
                rlThumbnailMoment.setVisibility(View.VISIBLE);
                mUploadKey_moment = takeVdideoMode.getMKey();
                ivThumbnailMoment.setImageBitmap(mBitMap);
            } else if (takeVdideoMode.getMType().equals("toolspackage")) {// 故障点照片
                rlThumbnailToolsPackage.setVisibility(View.VISIBLE);
                mUploadKey_tools_package = takeVdideoMode.getMKey();
                ivThumbnailToolsPackage.setImageBitmap(mBitMap);
            } else if (takeVdideoMode.getMType().equals("failure")) {//恢复后表象
                rlThumbnailFailure.setVisibility(View.VISIBLE);
                mUploadKey_failure = takeVdideoMode.getMKey();
                ivThumbnailFailure.setImageBitmap(mBitMap);
            } else if (takeVdideoMode.getMType().equals("monitor_addpicture")) {  //工具及蓝布
                rlThumbnailMonitor.setVisibility(View.VISIBLE);
                mUploadKey_monitor = takeVdideoMode.getMKey();
                ivThumbnailMonitor.setImageBitmap(mBitMap);
            } else if (takeVdideoMode.getMType().equals("after")) {     //处理后现场
                rlThumbnailAfter.setVisibility(View.VISIBLE);
                mUploadKey_after = takeVdideoMode.getMKey();
                ivThumbnailAfter.setImageBitmap(mBitMap);
            } else if (takeVdideoMode.getMType().equals("machine")) { //设备回装
                rlThumbnailMachine.setVisibility(View.VISIBLE);
                mUploadKey_machine = takeVdideoMode.getMKey();
                ivThumbnailMachine.setImageBitmap(mBitMap);
            }

        }
    }

}
