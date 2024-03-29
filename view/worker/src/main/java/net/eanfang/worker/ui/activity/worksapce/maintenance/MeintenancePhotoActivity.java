package net.eanfang.worker.ui.activity.worksapce.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.delegate.BGASortableDelegate;

import com.eanfang.util.PhotoUtils;
import com.eanfang.biz.model.entity.ShopBughandleMaintenanceConfirmEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import cn.hutool.core.util.StrUtil;

public class MeintenancePhotoActivity extends BaseWorkerActivity {


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

    private HashMap<String, String> uploadMap = new HashMap<>();

    @BindView(R.id.snpl_tv_photo)
    BGASortableNinePhotoLayout snplTvPhoto;
    @BindView(R.id.snpl_operating_photo)
    BGASortableNinePhotoLayout snplOperatingPhoto;
    @BindView(R.id.snpl_box_photo)
    BGASortableNinePhotoLayout snplBoxPhoto;
    @BindView(R.id.snpl_invoices_photo)
    BGASortableNinePhotoLayout snplInvoicesPhoto;

    private ArrayList<String> picList1 = new ArrayList<>();
    private ArrayList<String> picList2 = new ArrayList<>();
    private ArrayList<String> picList3 = new ArrayList<>();
    private ArrayList<String> picList4 = new ArrayList<>();
    private ShopBughandleMaintenanceConfirmEntity confirmEntity;
    private boolean isShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_meintenance_photo);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("照片信息");
        setLeftBack();


        confirmEntity = (ShopBughandleMaintenanceConfirmEntity) getIntent().getSerializableExtra("bean");
        isShow = getIntent().getBooleanExtra("isShow", false);
        if (confirmEntity != null) {
            initImgUrlList();
            snplTvPhoto.setData(picList1);
            snplOperatingPhoto.setData(picList2);
            snplBoxPhoto.setData(picList3);
            snplInvoicesPhoto.setData(picList4);

            if (isShow) {
                snplTvPhoto.setPlusEnable(false);
                snplTvPhoto.setEditable(false);

                snplOperatingPhoto.setPlusEnable(false);
                snplOperatingPhoto.setEditable(false);

                snplBoxPhoto.setPlusEnable(false);
                snplBoxPhoto.setEditable(false);

                snplInvoicesPhoto.setPlusEnable(false);
                snplInvoicesPhoto.setEditable(false);


                tvSave.setVisibility(View.GONE);

            } else {
                initNinePhoto();
            }

        } else {
            initNinePhoto();
        }

    }

    private void initNinePhoto() {
        snplTvPhoto.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
        snplOperatingPhoto.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_2, REQUEST_CODE_PHOTO_PREVIEW_2));
        snplBoxPhoto.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_3, REQUEST_CODE_PHOTO_PREVIEW_3));
        snplInvoicesPhoto.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_4, REQUEST_CODE_PHOTO_PREVIEW_4));
    }


    private void doSubmitData() {

        String tvPhoto = PhotoUtils.getPhotoUrl("biz/maintain/", snplTvPhoto, uploadMap, false);
        if (StrUtil.isEmpty(tvPhoto)) {
            showToast("请选择电视墙正面照片");
            return;
        }

        String operatingPhoto = PhotoUtils.getPhotoUrl("biz/maintain/", snplOperatingPhoto, uploadMap, false);
        if (StrUtil.isEmpty(operatingPhoto)) {
            showToast("请选操作台背面照片");
            return;
        }

        String boxPhoto = PhotoUtils.getPhotoUrl("biz/maintain/", snplBoxPhoto, uploadMap, false);
        if (StrUtil.isEmpty(boxPhoto)) {
            showToast("请选择机柜照片");
            return;
        }
        String invoicesPhoto = PhotoUtils.getPhotoUrl("biz/maintain/", snplInvoicesPhoto, uploadMap, false);
        if (StrUtil.isEmpty(invoicesPhoto)) {
            showToast("请选择单据照片");
            return;
        }

        if (confirmEntity == null) {
            confirmEntity = new ShopBughandleMaintenanceConfirmEntity();
        }
        confirmEntity.setFrontPictures(tvPhoto);
        confirmEntity.setReverseSidePictures(operatingPhoto);
        confirmEntity.setEquipmentCabinetPictures(boxPhoto);
        confirmEntity.setInvoicesPictures(invoicesPhoto);

        /**
         * 提交照片
         * */
        if (uploadMap.size() != 0) {
            SDKManager.ossKit(this).asyncPutImages(uploadMap,(isSuccess) -> {
                Intent intent = new Intent();
                intent.putExtra("bean", confirmEntity);
                setResult(RESULT_OK, intent);
                finishSelf();
            });
            return;
        }

        if (confirmEntity != null) {
            Intent intent = new Intent();
            intent.putExtra("bean", confirmEntity);
            setResult(RESULT_OK, intent);
            finishSelf();
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
                snplTvPhoto.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_2:
                snplOperatingPhoto.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_3:
                snplBoxPhoto.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_4:
                snplInvoicesPhoto.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                break;

            case REQUEST_CODE_PHOTO_PREVIEW_1:
                snplTvPhoto.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_2:
                snplOperatingPhoto.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_3:
                snplBoxPhoto.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_4:
                snplInvoicesPhoto.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
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
        if (!StrUtil.isEmpty(confirmEntity.getFrontPictures())) {
            String[] presentationPic = confirmEntity.getFrontPictures().split(",");
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

        if (!StrUtil.isEmpty(confirmEntity.getReverseSidePictures())) {
            String[] presentationPic = confirmEntity.getReverseSidePictures().split(",");
            if (presentationPic.length >= 1) {
                picList2.add(BuildConfig.OSS_SERVER + presentationPic[0]);
            }
            if (presentationPic.length >= 2) {
                picList2.add(BuildConfig.OSS_SERVER + presentationPic[1]);
            }
            if (presentationPic.length >= 3) {
                picList2.add(BuildConfig.OSS_SERVER + presentationPic[2]);
            }
        }

        if (!StrUtil.isEmpty(confirmEntity.getEquipmentCabinetPictures())) {
            String[] presentationPic = confirmEntity.getEquipmentCabinetPictures().split(",");
            if (presentationPic.length >= 1) {
                picList3.add(BuildConfig.OSS_SERVER + presentationPic[0]);
            }
            if (presentationPic.length >= 2) {
                picList3.add(BuildConfig.OSS_SERVER + presentationPic[1]);
            }
            if (presentationPic.length >= 3) {
                picList3.add(BuildConfig.OSS_SERVER + presentationPic[2]);
            }
        }
    }

    @OnClick(R.id.tv_save)
    public void onViewClicked() {
        doSubmitData();
    }
}
