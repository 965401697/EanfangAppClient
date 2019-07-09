package net.eanfang.worker.ui.activity.worksapce.repair;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.BughandleDetailEntity;

import net.eanfang.worker.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/6/11  16:03
 * @decision 故障处理查看照片
 */
public class SeeTroubleDetailPhotoActivity extends BaseActivity {

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

    /**
     * 故障表象 （3张）
     */
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;
    @BindView(R.id.iv_thumbnail_moment)
    ImageView ivThumbnailMoment;
    @BindView(R.id.rl_thumbnail_moment)
    RelativeLayout rlThumbnailMoment;
    /**
     * 工具及蓝布 （3张）
     */
    @BindView(R.id.snpl_monitor_add_photos)
    BGASortableNinePhotoLayout snplMonitorAddPhotos;
    @BindView(R.id.iv_thumbnail_monitor)
    ImageView ivThumbnailMonitor;
    @BindView(R.id.rl_thumbnail_monitor)
    RelativeLayout rlThumbnailMonitor;
    /**
     * 故障点照片 （3张）
     */
    @BindView(R.id.snpl_tools_package_add_photos)
    BGASortableNinePhotoLayout snplToolsPackageAddPhotos;
    @BindView(R.id.iv_thumbnail_tools_package)
    ImageView ivThumbnailToolsPackage;
    @BindView(R.id.rl_thumbnail_tools_package)
    RelativeLayout rlThumbnailToolsPackage;
    /**
     * 处理后现场 （3张）
     */
    @BindView(R.id.snpl_after_processing_locale)
    BGASortableNinePhotoLayout snplAfterProcessingLocale;
    @BindView(R.id.iv_thumbnail_after)
    ImageView ivThumbnailAfter;
    @BindView(R.id.rl_thumbnail_after)
    RelativeLayout rlThumbnailAfter;
    /**
     * 设备回装 （3张）
     */
    @BindView(R.id.snpl_machine_fit_back)
    BGASortableNinePhotoLayout snplMachineFitBack;
    @BindView(R.id.iv_thumbnail_machine)
    ImageView ivThumbnailMachine;
    @BindView(R.id.rl_thumbnail_machine)
    RelativeLayout rlThumbnailMachine;
    /**
     * 故障恢复后表象 （3张）
     */
    @BindView(R.id.snpl_failure_recover_phenomena)
    BGASortableNinePhotoLayout snplFailureRecoverPhenomena;
    @BindView(R.id.iv_thumbnail_failure)
    ImageView ivThumbnailFailure;
    @BindView(R.id.rl_thumbnail_failure)
    RelativeLayout rlThumbnailFailure;
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


    private BughandleDetailEntity bughandleDetailEntity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_see_trouble_detail_photo);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        setLeftBack();
        setTitle("查看照片");

    }


    private void initData() {
        bughandleDetailEntity = (BughandleDetailEntity) getIntent().getSerializableExtra("bughandleDetailEntity");
        doDate();
        initImgUrlList();
        initNinePhoto();
        initListener();
    }


    private void doDate() {
        /**
         * 故障表象 （3张）
         */
        if (!StringUtils.isEmpty(bughandleDetailEntity.getPresentation_mp4_path())) {
            rlThumbnailMoment.setVisibility(View.VISIBLE);
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + bughandleDetailEntity.getPresentation_mp4_path() + ".jpg"),ivThumbnailMoment);
        }
        /**
         * 工具及蓝布 （3张）
         */
        if (!StringUtils.isEmpty(bughandleDetailEntity.getTool_mp4_path())) {
            rlThumbnailMonitor.setVisibility(View.VISIBLE);
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + bughandleDetailEntity.getTool_mp4_path() + ".jpg"),ivThumbnailMonitor);
        }
        /**
         * 故障点照片 （3张）
         */
        if (!StringUtils.isEmpty(bughandleDetailEntity.getPoint_mp4_path())) {
            rlThumbnailToolsPackage.setVisibility(View.VISIBLE);
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + bughandleDetailEntity.getPoint_mp4_path() + ".jpg"),ivThumbnailToolsPackage);
        }
        /**
         * 处理后现场 （3张）
         */
        if (!StringUtils.isEmpty(bughandleDetailEntity.getAfter_handle_mp4_path())) {
            rlThumbnailAfter.setVisibility(View.VISIBLE);
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + bughandleDetailEntity.getAfter_handle_mp4_path() + ".jpg"),ivThumbnailAfter);
        }
        /**
         * 设备回装 （3张）
         */
        if (!StringUtils.isEmpty(bughandleDetailEntity.getDevice_return_install_mp4_path())) {
            rlThumbnailMachine.setVisibility(View.VISIBLE);
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + bughandleDetailEntity.getDevice_return_install_mp4_path() + ".jpg"),ivThumbnailMachine);
        }
        /**
         * 故障恢复后表象 （3张）
         */
        if (!StringUtils.isEmpty(bughandleDetailEntity.getRestore_mp4_path())) {
            rlThumbnailFailure.setVisibility(View.VISIBLE);
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + bughandleDetailEntity.getRestore_mp4_path() + ".jpg"),ivThumbnailFailure);
        }
    }

    private void initListener() {
        /**
         * 故障表象 （3张）
         */
        rlThumbnailMoment.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", BuildConfig.OSS_SERVER + bughandleDetailEntity.getPresentation_mp4_path() + ".mp4");
            JumpItent.jump(SeeTroubleDetailPhotoActivity.this, PlayVideoActivity.class, bundle);
        });
        /**
         * 工具及蓝布 （3张）
         */
        ivThumbnailMonitor.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", BuildConfig.OSS_SERVER + bughandleDetailEntity.getTool_mp4_path() + ".mp4");
            JumpItent.jump(SeeTroubleDetailPhotoActivity.this, PlayVideoActivity.class, bundle);
        });
        /**
         * 故障点照片 （3张）
         */
        ivThumbnailToolsPackage.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", BuildConfig.OSS_SERVER + bughandleDetailEntity.getPoint_mp4_path() + ".mp4");
            JumpItent.jump(SeeTroubleDetailPhotoActivity.this, PlayVideoActivity.class, bundle);
        });
        /**
         * 处理后现场 （3张）
         */
        ivThumbnailAfter.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", BuildConfig.OSS_SERVER + bughandleDetailEntity.getAfter_handle_mp4_path() + ".mp4");
            JumpItent.jump(SeeTroubleDetailPhotoActivity.this, PlayVideoActivity.class, bundle);
        });
        /**
         * 设备回装 （3张）
         */
        ivThumbnailMachine.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", BuildConfig.OSS_SERVER + bughandleDetailEntity.getDevice_return_install_mp4_path() + ".mp4");
            JumpItent.jump(SeeTroubleDetailPhotoActivity.this, PlayVideoActivity.class, bundle);
        });
        /**
         * 故障恢复后表象 （3张）
         */
        ivThumbnailFailure.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", BuildConfig.OSS_SERVER + bughandleDetailEntity.getRestore_mp4_path() + ".mp4");
            JumpItent.jump(SeeTroubleDetailPhotoActivity.this, PlayVideoActivity.class, bundle);
        });
    }

    /**
     * 初始化存储图片用的List集合
     */
    private void initImgUrlList() {

        //故障表象照片
        if (StringUtils.isValid(bughandleDetailEntity.getPresentationPictures())) {
            snplMomentAddPhotos.setVisibility(View.VISIBLE);
            String[] prePic = bughandleDetailEntity.getPresentationPictures().split(",");
            picList1.addAll(Stream.of(Arrays.asList(prePic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
        }
        //工具及蓝布照片
        if (StringUtils.isValid(bughandleDetailEntity.getToolPictures())) {
            snplMonitorAddPhotos.setVisibility(View.VISIBLE);
            String[] toolPic = bughandleDetailEntity.getToolPictures().split(",");
            picList2.addAll(Stream.of(Arrays.asList(toolPic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
        }
        //故障点照片
        if (StringUtils.isValid(bughandleDetailEntity.getPointPictures())) {
            snplToolsPackageAddPhotos.setVisibility(View.VISIBLE);
            String[] ponitPic = bughandleDetailEntity.getPointPictures().split(",");
            picList3.addAll(Stream.of(Arrays.asList(ponitPic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
        }
        //处理后现场照片
        if (StringUtils.isValid(bughandleDetailEntity.getAfterHandlePictures())) {
            snplAfterProcessingLocale.setVisibility(View.VISIBLE);
            String[] afterHandlePic = bughandleDetailEntity.getAfterHandlePictures().split(",");
            picList4.addAll(Stream.of(Arrays.asList(afterHandlePic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
        }
        //设备回装照片
        if (StringUtils.isValid(bughandleDetailEntity.getDeviceReturnInstallPictures())) {
            snplMachineFitBack.setVisibility(View.VISIBLE);
            String[] returnInstallPic = bughandleDetailEntity.getDeviceReturnInstallPictures().split(",");
            picList5.addAll(Stream.of(Arrays.asList(returnInstallPic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
        }
        //故障恢复后表象照片
        if (StringUtils.isValid(bughandleDetailEntity.getRestorePictures())) {
            snplFailureRecoverPhenomena.setVisibility(View.VISIBLE);
            String[] restorePic = bughandleDetailEntity.getRestorePictures().split(",");
            picList6.addAll(Stream.of(Arrays.asList(restorePic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
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

        snplMomentAddPhotos.setEditable(false);
        snplMonitorAddPhotos.setEditable(false);
        snplToolsPackageAddPhotos.setEditable(false);
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
            default:
                break;
        }
    }
}
