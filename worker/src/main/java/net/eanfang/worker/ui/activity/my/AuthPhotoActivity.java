package net.eanfang.worker.ui.activity.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.model.WorkerInfoBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/6/21  10:34
 * @decision 技师认证 上传照片
 */
public class AuthPhotoActivity extends BaseActivityWithTakePhoto {


    private final static int RESULT_ADD_PHOTO = 200;

    //意外保险
    private static final int REQUEST_CODE_CHOOSE_ACCIDENT = 6;
    private static final int REQUEST_CODE_PHOTO_ACCIDENT = 106;
    //有无犯罪记录
    private static final int REQUEST_CODE_CHOOSE_CRIM = 1;
    private static final int REQUEST_CODE_PHOTO_CRIM = 101;
    //身份证正面
    private final int ID_CARD_FRONT = 101;
    // 身份证反面
    private final int ID_CARD_SIDE = 102;
    //身份证手持
    private final int ID_CARD_HAND = 103;

    @BindView(R.id.iv_idCard_front)
    SimpleDraweeView ivIdCardFront;

    @BindView(R.id.iv_idCard_back)
    SimpleDraweeView ivIdCardBack;

    @BindView(R.id.iv_idCard_inHand)
    SimpleDraweeView ivIdCardInHand;

    @BindView(R.id.tv_save)
    TextView tvSave;

    /**
     * 保险照片
     * （3张）
     */
    @BindView(R.id.snpl_moment_accident)
    BGASortableNinePhotoLayout snplMomentAccident;
    private ArrayList<String> picList_accident = new ArrayList<>();

    /**
     * 犯罪照片
     * （3张）
     */
    @BindView(R.id.snpl_moment_crim)
    BGASortableNinePhotoLayout snplMomentCrim;
    private ArrayList<String> picList_crim = new ArrayList<>();


    private WorkerInfoBean workerInfoBean;

    private HashMap<String, String> uploadMap = new HashMap<>();

    // 是否
    private boolean isAdd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_photo);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        setTitle("身份信息");
        setLeftBack();
        workerInfoBean = (WorkerInfoBean) getIntent().getSerializableExtra("workerInfoBean");
        isAdd = getIntent().getBooleanExtra("isAdd", false);
    }

    private void initData() {
        // 保险照
        snplMomentAccident.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_ACCIDENT, REQUEST_CODE_PHOTO_ACCIDENT));
        snplMomentAccident.setData(picList_accident);

        // 犯罪照
        snplMomentCrim.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_CRIM, REQUEST_CODE_PHOTO_CRIM));
        snplMomentCrim.setData(picList_crim);

        if (!StringUtils.isEmpty(workerInfoBean.getIdCardFront())) {
            ivIdCardFront.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + workerInfoBean.getIdCardFront()));
        }
        if (!StringUtils.isEmpty(workerInfoBean.getIdCardSide())) {
            ivIdCardBack.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + workerInfoBean.getIdCardSide()));
        }
        if (!StringUtils.isEmpty(workerInfoBean.getIdCardHand())) {
            ivIdCardInHand.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + workerInfoBean.getIdCardHand()));
        }
        initImgUrlList();
    }

    private void initListener() {
        ivIdCardFront.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(AuthPhotoActivity.this, ID_CARD_FRONT)));
        ivIdCardBack.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(AuthPhotoActivity.this, ID_CARD_SIDE)));
        ivIdCardInHand.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(AuthPhotoActivity.this, ID_CARD_HAND)));
        tvSave.setOnClickListener((v) -> {
            doSave();
        });
    }

    /**
     * 保存照片
     */
    private void doSave() {
        if (StringUtils.isEmpty(workerInfoBean.getIdCardFront())) {
            showToast("请添加身份证正面照");
            return;
        }
        if (StringUtils.isEmpty(workerInfoBean.getIdCardHand())) {
            showToast("请添加手持身份证照片");
            return;
        }
        if (StringUtils.isEmpty(workerInfoBean.getIdCardSide())) {
            showToast("请添加身份证反面照");
            return;
        }
        String accidentPic = PhotoUtils.getPhotoUrl(snplMomentAccident, uploadMap, false);
//        if (StringUtils.isEmpty(accidentPic)) {
//            showToast("请添加保险照");
//            return;
//        }
        workerInfoBean.setAccidentPics(accidentPic);

        String crimePic = PhotoUtils.getPhotoUrl(snplMomentCrim, uploadMap, false);
//        if (StringUtils.isEmpty(crimePic)) {
//            showToast("请添加犯罪照");
//            return;
//        }
        workerInfoBean.setCrimePic(crimePic);

        /**
         * 提交照片
         * */
        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                        Intent intent = new Intent();
                        intent.putExtra("photos", workerInfoBean);
                        setResult(RESULT_ADD_PHOTO, intent);
                        finishSelf();
                    });
                }
            });
            return;
        } else if (isAdd) {
            finishSelf();
        }

    }

    @Override
    public void takeSuccess(TResult result, int resultCode) {
        super.takeSuccess(result, resultCode);
        if (result == null || result.getImage() == null) {
            return;
        }
        TImage image = result.getImage();
        String imgKey = UuidUtil.getUUID() + ".png";
        switch (resultCode) {
            // 身份证正面
            case ID_CARD_FRONT:
                workerInfoBean.setIdCardFront(imgKey);
                ivIdCardFront.setImageURI("file://" + image.getOriginalPath());
                break;
            // 反面
            case ID_CARD_SIDE:
                workerInfoBean.setIdCardSide(imgKey);
                ivIdCardBack.setImageURI("file://" + image.getOriginalPath());
                break;
            // 手持
            case ID_CARD_HAND:
                workerInfoBean.setIdCardHand(imgKey);
                ivIdCardInHand.setImageURI("file://" + image.getOriginalPath());
                break;
        }
        OSSUtils.initOSS(this).asyncPutImage(imgKey, image.getOriginalPath(), new OSSCallBack(this, true) {
        });

//        ivIdCardFront.setEnabled(false);
//        ivIdCardBack.setEnabled(false);
//        ivIdCardInHand.setEnabled(false);
//        ivAccidentPics.setEnabled(false);
//        ivCrimePic.setEnabled(false);
    }

    /**
     * 初始化存储图片用的List集合
     */
    private void initImgUrlList() {
        // 保险照
        if (!StringUtils.isEmpty(workerInfoBean.getAccidentPics())) {
            String[] presentationPic = workerInfoBean.getAccidentPics().split(",");
            if (presentationPic.length >= 1) {
                picList_accident.add(BuildConfig.OSS_SERVER + presentationPic[0]);
            }
            if (presentationPic.length >= 2) {
                picList_accident.add(BuildConfig.OSS_SERVER + presentationPic[1]);
            }
            if (presentationPic.length >= 3) {
                picList_accident.add(BuildConfig.OSS_SERVER + presentationPic[2]);
            }
        }
        // 犯罪照
        if (!StringUtils.isEmpty(workerInfoBean.getCrimePic())) {
            String[] presentationPic = workerInfoBean.getCrimePic().split(",");
            if (presentationPic.length >= 1) {
                picList_crim.add(BuildConfig.OSS_SERVER + presentationPic[0]);
            }
            if (presentationPic.length >= 2) {
                picList_crim.add(BuildConfig.OSS_SERVER + presentationPic[1]);
            }
            if (presentationPic.length >= 3) {
                picList_crim.add(BuildConfig.OSS_SERVER + presentationPic[2]);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_ACCIDENT:
                snplMomentAccident.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_CRIM:
                snplMomentCrim.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
        }
    }
}
