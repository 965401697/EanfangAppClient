package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.biz.model.entity.BughandleDetailEntity;
import com.eanfang.config.Constant;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.LookMaterialAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import cn.hutool.core.util.StrUtil;

/**
 * @author Guanluocang
 * @date on 2018/5/23  18:23
 * @decision 电话未解决 照片墙
 */

public class TroubleDetailLookPhotoActivity extends BaseActivity {

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
    BGASortableNinePhotoLayout snpl_moment_add_photos;
    @BindView(R.id.iv_thumbnail_moment)
    ImageView ivThumbnailMoment;
    @BindView(R.id.rl_thumbnail_moment)
    RelativeLayout rlThumbnailMoment;
    /**
     * 工具及蓝布 （3张）
     */
    @BindView(R.id.snpl_monitor_add_photos)
    BGASortableNinePhotoLayout snpl_monitor_add_photos;
    @BindView(R.id.iv_thumbnail_monitor)
    ImageView ivThumbnailMonitor;
    @BindView(R.id.rl_thumbnail_monitor)
    RelativeLayout rlThumbnailMonitor;
    /**
     * 故障点照片 （3张）
     */
    @BindView(R.id.snpl_tools_package_add_photos)
    BGASortableNinePhotoLayout snpl_tools_package_add_photos;
    @BindView(R.id.iv_thumbnail_tools_package)
    ImageView ivThumbnailToolsPackage;
    @BindView(R.id.rl_thumbnail_tools_package)
    RelativeLayout rlThumbnailToolsPackage;
    /**
     * 处理后现场 （3张）
     */
    @BindView(R.id.snpl_after_processing_locale)
    BGASortableNinePhotoLayout snpl_after_processing_locale;
    @BindView(R.id.iv_thumbnail_after)
    ImageView ivThumbnailAfter;
    @BindView(R.id.rl_thumbnail_after)
    RelativeLayout rlThumbnailAfter;
    /**
     * 设备回装 （3张）
     */
    @BindView(R.id.snpl_machine_fit_back)
    BGASortableNinePhotoLayout snpl_machine_fit_back;
    @BindView(R.id.iv_thumbnail_machine)
    ImageView ivThumbnailMachine;
    @BindView(R.id.rl_thumbnail_machine)
    RelativeLayout rlThumbnailMachine;
    /**
     * 故障恢复后表象 （3张）
     */
    @BindView(R.id.snpl_failure_recover_phenomena)
    BGASortableNinePhotoLayout snpl_failure_recover_phenomena;
    @BindView(R.id.iv_thumbnail_failure)
    ImageView ivThumbnailFailure;
    @BindView(R.id.rl_thumbnail_failure)
    RelativeLayout rlThumbnailFailure;

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

    /**
     * 故障明细的id
     */
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_trouble_detail_look_photo);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        setLeftBack();
        setTitle("现场照片");
        id = getIntent().getLongExtra(Constant.ID, 0);
    }

    private void initData() {
        EanfangHttp.get(RepairApi.GET_BUGHANDLE_DETAIL_INFO)
                .params("id", id)
                .execute(new EanfangCallback<BughandleDetailEntity>(this, true, BughandleDetailEntity.class, (bean) -> {
                    bughandleDetailEntity = bean;
                    doData();
//                    initData(bean);
                    initImgUrlList();
                    initNinePhoto();
                    initListener();
                }));

    }


    private void doData() {
        /**
         * 故障表象 （3张）
         */
        if (!StrUtil.isEmpty(bughandleDetailEntity.getPresentation_mp4_path())) {
            rlThumbnailMoment.setVisibility(View.VISIBLE);
            GlideUtil.intoImageView(this, Uri.parse(BuildConfig.OSS_SERVER + bughandleDetailEntity.getPresentation_mp4_path() + ".jpg"), ivThumbnailMoment);
        }
        /**
         * 工具及蓝布 （3张）
         */
        if (!StrUtil.isEmpty(bughandleDetailEntity.getTool_mp4_path())) {
            rlThumbnailMonitor.setVisibility(View.VISIBLE);
            GlideUtil.intoImageView(this, Uri.parse(BuildConfig.OSS_SERVER + bughandleDetailEntity.getTool_mp4_path() + ".jpg"), ivThumbnailMonitor);
        }
        /**
         * 故障点照片 （3张）
         */
        if (!StrUtil.isEmpty(bughandleDetailEntity.getPoint_mp4_path())) {

            {
                rlThumbnailToolsPackage.setVisibility(View.VISIBLE);
                GlideUtil.intoImageView(this, Uri.parse(BuildConfig.OSS_SERVER + bughandleDetailEntity.getPoint_mp4_path() + ".jpg"), ivThumbnailToolsPackage);
            }
            /**
             * 处理后现场 （3张）
             */
            if (!StrUtil.isEmpty(bughandleDetailEntity.getAfter_handle_mp4_path())) {
                rlThumbnailAfter.setVisibility(View.VISIBLE);
                GlideUtil.intoImageView(this, Uri.parse(BuildConfig.OSS_SERVER + bughandleDetailEntity.getAfter_handle_mp4_path() + ".jpg"), ivThumbnailAfter);
            }
            /**
             * 设备回装 （3张）
             */
            if (!StrUtil.isEmpty(bughandleDetailEntity.getDevice_return_install_mp4_path())) {
                rlThumbnailMachine.setVisibility(View.VISIBLE);
                GlideUtil.intoImageView(this, Uri.parse(BuildConfig.OSS_SERVER + bughandleDetailEntity.getDevice_return_install_mp4_path() + ".jpg"), ivThumbnailMachine);
            }
            /**
             * 故障恢复后表象 （3张）
             */
            if (!StrUtil.isEmpty(bughandleDetailEntity.getRestore_mp4_path())) {
                rlThumbnailFailure.setVisibility(View.VISIBLE);
                GlideUtil.intoImageView(this, Uri.parse(BuildConfig.OSS_SERVER + bughandleDetailEntity.getRestore_mp4_path() + ".jpg"), ivThumbnailFailure);

            }

        }
    }

    private void initListener() {
        /**
         * 故障表象 （3张）
         */
        rlThumbnailMoment.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", BuildConfig.OSS_SERVER + bughandleDetailEntity.getPresentation_mp4_path() + ".mp4");
            JumpItent.jump(TroubleDetailLookPhotoActivity.this, PlayVideoActivity.class, bundle);
        });
        /**
         * 工具及蓝布 （3张）
         */
        ivThumbnailMonitor.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", BuildConfig.OSS_SERVER + bughandleDetailEntity.getTool_mp4_path() + ".mp4");
            JumpItent.jump(TroubleDetailLookPhotoActivity.this, PlayVideoActivity.class, bundle);
        });
        /**
         * 故障点照片 （3张）
         */
        ivThumbnailToolsPackage.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", BuildConfig.OSS_SERVER + bughandleDetailEntity.getPoint_mp4_path() + ".mp4");
            JumpItent.jump(TroubleDetailLookPhotoActivity.this, PlayVideoActivity.class, bundle);
        });
        /**
         * 处理后现场 （3张）
         */
        ivThumbnailAfter.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", BuildConfig.OSS_SERVER + bughandleDetailEntity.getAfter_handle_mp4_path() + ".mp4");
            JumpItent.jump(TroubleDetailLookPhotoActivity.this, PlayVideoActivity.class, bundle);
        });
        /**
         * 设备回装 （3张）
         */
        ivThumbnailMachine.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", BuildConfig.OSS_SERVER + bughandleDetailEntity.getDevice_return_install_mp4_path() + ".mp4");
            JumpItent.jump(TroubleDetailLookPhotoActivity.this, PlayVideoActivity.class, bundle);
        });
        /**
         * 故障恢复后表象 （3张）
         */
        ivThumbnailFailure.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", BuildConfig.OSS_SERVER + bughandleDetailEntity.getRestore_mp4_path() + ".mp4");
            JumpItent.jump(TroubleDetailLookPhotoActivity.this, PlayVideoActivity.class, bundle);
        });
    }

    /**
     * 初始化存储图片用的List集合
     */
    private void initImgUrlList() {

        //故障表象照片
        if (StrUtil.isNotBlank(bughandleDetailEntity.getPresentationPictures())) {
            snpl_moment_add_photos.setVisibility(View.VISIBLE);
            String[] prePic = bughandleDetailEntity.getPresentationPictures().split(Constant.IMG_SPLIT);
            picList1.addAll(Stream.of(Arrays.asList(prePic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
        }
        //工具及蓝布照片
        if (StrUtil.isNotBlank(bughandleDetailEntity.getToolPictures())) {
            snpl_monitor_add_photos.setVisibility(View.VISIBLE);
            String[] toolPic = bughandleDetailEntity.getToolPictures().split(Constant.IMG_SPLIT);
            picList2.addAll(Stream.of(Arrays.asList(toolPic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
        }
        //故障点照片
        if (StrUtil.isNotBlank(bughandleDetailEntity.getPointPictures())) {
            snpl_tools_package_add_photos.setVisibility(View.VISIBLE);
            String[] ponitPic = bughandleDetailEntity.getPointPictures().split(Constant.IMG_SPLIT);
            picList3.addAll(Stream.of(Arrays.asList(ponitPic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
        }
        //处理后现场照片
        if (StrUtil.isNotBlank(bughandleDetailEntity.getAfterHandlePictures())) {
            snpl_after_processing_locale.setVisibility(View.VISIBLE);
            String[] afterHandlePic = bughandleDetailEntity.getAfterHandlePictures().split(Constant.IMG_SPLIT);
            picList4.addAll(Stream.of(Arrays.asList(afterHandlePic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
        }
        //设备回装照片
        if (StrUtil.isNotBlank(bughandleDetailEntity.getDeviceReturnInstallPictures())) {
            snpl_machine_fit_back.setVisibility(View.VISIBLE);
            String[] returnInstallPic = bughandleDetailEntity.getDeviceReturnInstallPictures().split(Constant.IMG_SPLIT);
            picList5.addAll(Stream.of(Arrays.asList(returnInstallPic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
        }
        //故障恢复后表象照片
        if (StrUtil.isNotBlank(bughandleDetailEntity.getRestorePictures())) {
            snpl_failure_recover_phenomena.setVisibility(View.VISIBLE);
            String[] restorePic = bughandleDetailEntity.getRestorePictures().split(Constant.IMG_SPLIT);
            picList6.addAll(Stream.of(Arrays.asList(restorePic)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
        }

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
        snpl_after_processing_locale.setData(picList4);
        snpl_machine_fit_back.setData(picList5);
        snpl_failure_recover_phenomena.setData(picList6);

        snpl_moment_add_photos.setEditable(false);
        snpl_monitor_add_photos.setEditable(false);
        snpl_tools_package_add_photos.setEditable(false);
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
                snpl_moment_add_photos.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_2:
                snpl_monitor_add_photos.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_3:
                snpl_tools_package_add_photos.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                break;
            //2017年7月21日
            case REQUEST_CODE_CHOOSE_PHOTO_4:
                snpl_after_processing_locale.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_5:
                snpl_machine_fit_back.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_6:
                snpl_failure_recover_phenomena.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_1:
                snpl_moment_add_photos.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_2:
                snpl_monitor_add_photos.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_3:
                snpl_tools_package_add_photos.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_4:
                snpl_after_processing_locale.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_5:
                snpl_machine_fit_back.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_6:
                snpl_failure_recover_phenomena.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
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
