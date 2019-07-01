package net.eanfang.worker.ui.activity.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.biz.model.WorkerInfoBean;

import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/6/21  10:34
 * @decision 技师认证 上传照片
 */
@Deprecated
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
    ImageView ivIdCardFront;

    @BindView(R.id.iv_idCard_back)
    ImageView ivIdCardBack;

    @BindView(R.id.iv_idCard_inHand)
    ImageView ivIdCardInHand;

    @BindView(R.id.tv_save)
    TextView tvSave;

    private int mVerifyStatus = 100;

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
    private boolean isEdit = false;

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
        isEdit = getIntent().getBooleanExtra("isEdit", false);
        mVerifyStatus = getIntent().getIntExtra("verifyStatus", 100);
    }

    private void initData() {
        initImgUrlList();
        // 保险照
        snplMomentAccident.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_ACCIDENT, REQUEST_CODE_PHOTO_ACCIDENT));
        snplMomentAccident.setData(picList_accident);

        // 犯罪照
        snplMomentCrim.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_CRIM, REQUEST_CODE_PHOTO_CRIM));
        snplMomentCrim.setData(picList_crim);

        if (!StringUtils.isEmpty(workerInfoBean.getIdCardFront())) {
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + workerInfoBean.getIdCardFront()),ivIdCardFront);
        }
        if (!StringUtils.isEmpty(workerInfoBean.getIdCardSide())) {
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + workerInfoBean.getIdCardSide()),ivIdCardBack);
        }
        if (!StringUtils.isEmpty(workerInfoBean.getIdCardHand())) {
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + workerInfoBean.getIdCardHand()),ivIdCardInHand);
        }
    }

    private void initListener() {
        ivIdCardFront.setOnClickListener(v -> RxPerm.get(this).cameraPerm((isSuccess)-> takePhoto(AuthPhotoActivity.this, ID_CARD_FRONT)));
        ivIdCardBack.setOnClickListener(v -> RxPerm.get(this).cameraPerm((isSuccess)-> takePhoto(AuthPhotoActivity.this, ID_CARD_SIDE)));
        ivIdCardInHand.setOnClickListener(v -> RxPerm.get(this).cameraPerm((isSuccess)-> takePhoto(AuthPhotoActivity.this, ID_CARD_HAND)));
        tvSave.setOnClickListener((v) -> {
            doSave();
        });

        // 已经认证成功/ 已经提交认证，正在认证中 无法点击操作
        if (mVerifyStatus == 2 || mVerifyStatus == 1) {
            ivIdCardFront.setEnabled(false);
            ivIdCardBack.setEnabled(false);
            ivIdCardInHand.setEnabled(false);
            snplMomentAccident.setEditable(false);
            snplMomentCrim.setEditable(false);
        }
        // 编辑
        if (isEdit) {
            ivIdCardFront.setEnabled(true);
            ivIdCardBack.setEnabled(true);
            ivIdCardInHand.setEnabled(true);
            snplMomentAccident.setEditable(true);
            snplMomentCrim.setEditable(true);
        }

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
        String accidentPic = PhotoUtils.getPhotoUrl("account/verify/", snplMomentAccident, uploadMap, false);
//        if (StringUtils.isEmpty(accidentPic)) {
//            showToast("请添加保险照");
//            return;
//        }
        workerInfoBean.setAccidentPics(accidentPic);

        String crimePic = PhotoUtils.getPhotoUrl("account/verify/", snplMomentCrim, uploadMap, false);
//        if (StringUtils.isEmpty(crimePic)) {
//            showToast("请添加犯罪照");
//            return;
//        }
        workerInfoBean.setCrimePic(crimePic);

        /**
         * 提交照片
         * */
        if (uploadMap.size() != 0) {
            SDKManager.ossKit(this).asyncPutImages(uploadMap,(isSuccess) -> {
                runOnUiThread(() -> {
                    Intent intent = new Intent();
                    intent.putExtra("photos", workerInfoBean);
                    setResult(RESULT_ADD_PHOTO, intent);
                    finishSelf();
                });
            });
            return;
        } else if (isAdd) {
            Intent intent = new Intent();
            intent.putExtra("photos", workerInfoBean);
            setResult(RESULT_ADD_PHOTO, intent);
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
                GlideUtil.intoImageView(this,"file://" + image.getOriginalPath(),ivIdCardFront);
                break;
            // 反面
            case ID_CARD_SIDE:
                workerInfoBean.setIdCardSide(imgKey);
                GlideUtil.intoImageView(this,"file://" + image.getOriginalPath(),ivIdCardBack);
                break;
            // 手持
            case ID_CARD_HAND:
                workerInfoBean.setIdCardHand(imgKey);
                GlideUtil.intoImageView(this,"file://" + image.getOriginalPath(),ivIdCardInHand);
                break;
        }
        SDKManager.ossKit(this).asyncPutImage(imgKey, image.getOriginalPath(),(isSuccess) -> {});
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
