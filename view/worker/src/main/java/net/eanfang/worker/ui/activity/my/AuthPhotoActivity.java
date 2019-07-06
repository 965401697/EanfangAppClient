package net.eanfang.worker.ui.activity.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.eanfang.BuildConfig;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.picture.IPictureCallBack;
import com.eanfang.base.kit.picture.picture.PictureRecycleView;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.biz.model.WorkerInfoBean;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.luck.picture.lib.entity.LocalMedia;
import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkeActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/6/21  10:34
 * @decision 技师认证 上传照片
 */
@Deprecated
public class AuthPhotoActivity extends BaseWorkeActivity {


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
    /**
     * 保险照片
     * （3张）
     */
    @BindView(R.id.recycleview_accident)
    PictureRecycleView recycleviewAccident;
    private List<LocalMedia> picList_accident = new ArrayList<>();
    /**
     * 犯罪照片
     * （3张）
     */
    @BindView(R.id.recycleview_crim)
    PictureRecycleView recycleviewCrim;
    private List<LocalMedia> picList_crim = new ArrayList<>();
    private int mVerifyStatus = 100;
    private WorkerInfoBean workerInfoBean;

    private HashMap<String, String> uploadMap = new HashMap<>();

    // 是否
    private boolean isAdd = false;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_auth_photo);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initData();
        initListener();
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    @Override
    public void initView() {
        setTitle("身份信息");
        workerInfoBean = (WorkerInfoBean) getIntent().getSerializableExtra("workerInfoBean");
        isAdd = getIntent().getBooleanExtra("isAdd", false);
        isEdit = getIntent().getBooleanExtra("isEdit", false);
        mVerifyStatus = getIntent().getIntExtra("verifyStatus", 100);
    }

    private void initData() {
        initImgUrlList();

        if (!StringUtils.isEmpty(workerInfoBean.getIdCardFront())) {
            GlideUtil.intoImageView(this, Uri.parse(BuildConfig.OSS_SERVER + workerInfoBean.getIdCardFront()), ivIdCardFront);
        }
        if (!StringUtils.isEmpty(workerInfoBean.getIdCardSide())) {
            GlideUtil.intoImageView(this, Uri.parse(BuildConfig.OSS_SERVER + workerInfoBean.getIdCardSide()), ivIdCardBack);
        }
        if (!StringUtils.isEmpty(workerInfoBean.getIdCardHand())) {
            GlideUtil.intoImageView(this, Uri.parse(BuildConfig.OSS_SERVER + workerInfoBean.getIdCardHand()), ivIdCardInHand);
        }
    }

    private void initListener() {
        ivIdCardFront.setOnClickListener(v -> RxPerm.get(this).cameraPerm((isSuccess) -> frontImage()));
        ivIdCardBack.setOnClickListener(v -> RxPerm.get(this).cameraPerm((isSuccess) -> backImage()));
        ivIdCardInHand.setOnClickListener(v -> RxPerm.get(this).cameraPerm((isSuccess) -> inhandImage()));
        tvSave.setOnClickListener((v) -> {
            doSave();
        });

        // 已经认证成功/ 已经提交认证，正在认证中 无法点击操作
        if (mVerifyStatus == 2 || mVerifyStatus == 1) {
            ivIdCardFront.setEnabled(false);
            ivIdCardBack.setEnabled(false);
            ivIdCardInHand.setEnabled(false);
        }
        // 编辑
        if (isEdit) {
            ivIdCardFront.setEnabled(true);
            ivIdCardBack.setEnabled(true);
            ivIdCardInHand.setEnabled(true);
        }

    }

    private boolean front = false;
    private boolean back = false;
    private boolean inhand = false;

    private void frontImage() {
        front = true;
        SDKManager.getPicture().create(this).takePhoto(iPictureCallBack);
    }

    private void backImage() {
        back = true;
        SDKManager.getPicture().create(this).takePhoto(iPictureCallBack);
    }

    private void inhandImage() {
        inhand = true;
        SDKManager.getPicture().create(this).takePhoto(iPictureCallBack);
    }

    IPictureCallBack iPictureCallBack = new IPictureCallBack() {
        @Override
        public void onSuccess(List<LocalMedia> list) {
            String imgKey = UuidUtil.getUUID() + ".png";
            if (front) {
                workerInfoBean.setIdCardFront(imgKey);
                GlideUtil.intoImageView(AuthPhotoActivity.this, "file://" + list.get(0).getCutPath(), ivIdCardFront);
                front = false;
            }
            if (back) {
                workerInfoBean.setIdCardSide(imgKey);
                GlideUtil.intoImageView(AuthPhotoActivity.this, "file://" + list.get(0).getCutPath(), ivIdCardBack);
                back = false;
            }
            if (inhand) {
                workerInfoBean.setIdCardHand(imgKey);
                GlideUtil.intoImageView(AuthPhotoActivity.this, "file://" + list.get(0).getCutPath(), ivIdCardInHand);
                inhand = false;
            }
            SDKManager.ossKit(AuthPhotoActivity.this).asyncPutImage(imgKey, list.get(0).getCutPath(), (isSuccess) -> {
            });
        }
    };

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
        String accidentPic = PhotoUtils.getPhotoUrl("account/verify/", picList_accident, uploadMap, false);
//        if (StringUtils.isEmpty(accidentPic)) {
//            showToast("请添加保险照");
//            return;
//        }
        workerInfoBean.setAccidentPics(accidentPic);

        String crimePic = PhotoUtils.getPhotoUrl("account/verify/", picList_crim, uploadMap, false);
//        if (StringUtils.isEmpty(crimePic)) {
//            showToast("请添加犯罪照");
//            return;
//        }
        workerInfoBean.setCrimePic(crimePic);

        /**
         * 提交照片
         * */
        if (uploadMap.size() != 0) {
            SDKManager.ossKit(this).asyncPutImages(uploadMap, (isSuccess) -> {
                runOnUiThread(() -> {
                    Intent intent = new Intent();
                    intent.putExtra("photos", workerInfoBean);
                    setResult(RESULT_ADD_PHOTO, intent);
                    finish();
                });
            });
            return;
        } else if (isAdd) {
            Intent intent = new Intent();
            intent.putExtra("photos", workerInfoBean);
            setResult(RESULT_ADD_PHOTO, intent);
            finish();
        }

    }

    /**
     * 初始化存储图片用的List集合
     */
    private void initImgUrlList() {
        // 保险照
        if (!StringUtils.isEmpty(workerInfoBean.getAccidentPics())) {
            picList_accident = recycleviewAccident.setData(workerInfoBean.getAccidentPics());
            recycleviewAccident.showImagev(picList_accident, listener_accident);
            recycleviewAccident.isShow(true, picList_accident);
        }
        // 犯罪照
        if (!StringUtils.isEmpty(workerInfoBean.getCrimePic())) {
            picList_crim = recycleviewCrim.setData(workerInfoBean.getCrimePic());
            recycleviewCrim.showImagev(picList_crim, listener_crim);
            recycleviewCrim.isShow(true, picList_crim);
        }
    }

    PictureRecycleView.ImageListener listener_accident = list -> picList_accident = list;
    PictureRecycleView.ImageListener listener_crim = list -> picList_crim = list;
}
