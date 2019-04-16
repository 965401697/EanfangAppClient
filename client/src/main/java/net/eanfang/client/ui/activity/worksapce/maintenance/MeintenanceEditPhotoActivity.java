package net.eanfang.client.ui.activity.worksapce.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.ShopBughandleMaintenanceDetailEntity;
import com.yaf.base.entity.ShopMaintenanceExamDeviceEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeintenanceEditPhotoActivity extends BaseClientActivity {


    private static final int REQUEST_CODE_CHOOSE_PHOTO_1 = 1;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_2 = 2;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_3 = 3;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_4 = 4;


    private static final int REQUEST_CODE_PHOTO_PREVIEW_1 = 101;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_2 = 102;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_3 = 103;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_4 = 104;
    @BindView(R.id.tv_save)
    TextView tvSave;


    /**
     * 前 （3张）
     */
    private ArrayList<String> picList1 = new ArrayList<>();
    /**
     * 处理后 （3张）
     */
    private ArrayList<String> picList2 = new ArrayList<>();
    /**
     * 维保 （3张）
     */
    private ArrayList<String> picList3 = new ArrayList<>();
    //2017年7月21日
    /**
     * 功能 （3张）
     */
    private ArrayList<String> picList4 = new ArrayList<>();

    @BindView(R.id.snpl_before_photo)
    BGASortableNinePhotoLayout snplBeforePhoto;
    @BindView(R.id.snpl_end_photo)
    BGASortableNinePhotoLayout snplEndPhoto;
    @BindView(R.id.snpl_photo)
    BGASortableNinePhotoLayout snplPhoto;
    @BindView(R.id.snpl_function_photo)
    BGASortableNinePhotoLayout snplFunctionPhoto;

    private HashMap<String, String> uploadMap = new HashMap<>();
    private ShopMaintenanceExamDeviceEntity deviceEntity;
    private boolean isShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meintenance_edit_photo);
        ButterKnife.bind(this);
        setTitle("照片信息");
        setLeftBack();

        deviceEntity = (ShopMaintenanceExamDeviceEntity) getIntent().getSerializableExtra("bean");
        isShow = getIntent().getBooleanExtra("isShow", false);

        if (deviceEntity.getMaintenanceDetailEntity() != null && !TextUtils.isEmpty(deviceEntity.getMaintenanceDetailEntity().getBeforePictures())) {
            initImgUrlList();
        }

        initNinePhoto();
    }

    private void initNinePhoto() {

        if (deviceEntity.getMaintenanceDetailEntity() != null && !TextUtils.isEmpty(deviceEntity.getMaintenanceDetailEntity().getBeforePictures())) {
            snplBeforePhoto.setData(picList1);
            snplEndPhoto.setData(picList2);
            snplPhoto.setData(picList3);
            snplFunctionPhoto.setData(picList4);

            if (isShow) {
                snplBeforePhoto.setPlusEnable(false);
                snplBeforePhoto.setEditable(false);

                snplEndPhoto.setPlusEnable(false);
                snplEndPhoto.setEditable(false);

                snplPhoto.setPlusEnable(false);
                snplPhoto.setEditable(false);

                snplFunctionPhoto.setPlusEnable(false);
                snplFunctionPhoto.setEditable(false);
                tvSave.setVisibility(View.GONE);

            }
        } else {
            snplBeforePhoto.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
            snplEndPhoto.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_2, REQUEST_CODE_PHOTO_PREVIEW_2));
            snplPhoto.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_3, REQUEST_CODE_PHOTO_PREVIEW_3));
            snplFunctionPhoto.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_4, REQUEST_CODE_PHOTO_PREVIEW_4));
        }
    }

    @OnClick({R.id.ll_before, R.id.ll_end, R.id.ll_photo, R.id.ll_funciton, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_before:
                break;
            case R.id.ll_end:
                break;
            case R.id.ll_photo:
                break;
            case R.id.ll_funciton:
                break;
            case R.id.tv_save:
                doSubmitData();
                break;
        }
    }

    private void doSubmitData() {
        //维保前 （3张）
        String before = PhotoUtils.getPhotoUrl("biz/maintain/",snplBeforePhoto, uploadMap, false);
        if (StringUtils.isEmpty(before)) {
            showToast("请选择维保前照片");
            return;
        }
        //处理后现场 （3张）
        String after = PhotoUtils.getPhotoUrl("biz/maintain/",snplEndPhoto, uploadMap, false);
        if (StringUtils.isEmpty(after)) {
            showToast("请选择处理后现场照片");
            return;
        }
        //维保后 （3张）
        String end = PhotoUtils.getPhotoUrl("biz/maintain/",snplPhoto, uploadMap, false);
        if (StringUtils.isEmpty(end)) {
            showToast("请选择维保后照片");
            return;
        }
        //功能正常照片
        String function = PhotoUtils.getPhotoUrl("biz/maintain/",snplFunctionPhoto, uploadMap, false);
        if (StringUtils.isEmpty(function)) {
            showToast("请选择功能正常照片");
            return;
        }

        ShopBughandleMaintenanceDetailEntity detailEntity = new ShopBughandleMaintenanceDetailEntity();
        detailEntity.setBeforePictures(before);
        detailEntity.setLocalePictures(after);
        detailEntity.setAfterPictures(end);
        detailEntity.setNormalPictures(function);

        deviceEntity.setMaintenanceDetailEntity(detailEntity);
        /**
         * 提交照片
         * */
        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    Intent intent = new Intent();
                    intent.putExtra("bean", deviceEntity);
                    setResult(RESULT_OK, intent);
                    finishSelf();
                }
            });
            return;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_PHOTO_1:
                snplBeforePhoto.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_2:
                snplEndPhoto.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_3:
                snplPhoto.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_4:
                snplFunctionPhoto.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;

            case REQUEST_CODE_PHOTO_PREVIEW_1:
                snplBeforePhoto.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_2:
                snplEndPhoto.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_3:
                snplPhoto.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_4:
                snplFunctionPhoto.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
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
        if (!StringUtils.isEmpty(deviceEntity.getMaintenanceDetailEntity().getBeforePictures())) {
            String[] presentationPic = deviceEntity.getMaintenanceDetailEntity().getBeforePictures().split(",");
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

        if (!StringUtils.isEmpty(deviceEntity.getMaintenanceDetailEntity().getLocalePictures())) {
            String[] toolPic = deviceEntity.getMaintenanceDetailEntity().getLocalePictures().split(",");
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
        if (!StringUtils.isEmpty(deviceEntity.getMaintenanceDetailEntity().getAfterPictures())) {
            String[] pointPic = deviceEntity.getMaintenanceDetailEntity().getAfterPictures().split(",");
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
        if (!StringUtils.isEmpty(deviceEntity.getMaintenanceDetailEntity().getNormalPictures())) {
            String[] pointPic = deviceEntity.getMaintenanceDetailEntity().getNormalPictures().split(",");
            if (pointPic.length >= 1) {
                picList4.add(BuildConfig.OSS_SERVER + pointPic[0]);
            }
            if (pointPic.length >= 2) {
                picList4.add(BuildConfig.OSS_SERVER + pointPic[1]);
            }
            if (pointPic.length >= 3) {
                picList4.add(BuildConfig.OSS_SERVER + pointPic[2]);
            }
        }
    }
}
