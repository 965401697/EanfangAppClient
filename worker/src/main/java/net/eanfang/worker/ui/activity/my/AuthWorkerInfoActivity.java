package net.eanfang.worker.ui.activity.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkerInfoBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/30  20:31
 * @email houzhongzhou@yeah.net
 * @desc 技师认证
 */
public class AuthWorkerInfoActivity extends BaseActivityWithTakePhoto {
    @BindView(R.id.et_contactName)
    EditText etContactName;
    @BindView(R.id.et_contactPhone)
    EditText etContactPhone;
    @BindView(R.id.tv_workingLevel)
    TextView tvWorkingLevel;
    @BindView(R.id.ll_workingLevel)
    LinearLayout llWorkingLevel;
    @BindView(R.id.tv_workingYear)
    TextView tvWorkingYear;
    @BindView(R.id.ll_workingYear)
    LinearLayout llWorkingYear;
    @BindView(R.id.tv_payType)
    TextView tvPayType;
    @BindView(R.id.ll_payType)
    LinearLayout llPayType;
    @BindView(R.id.et_payAccount)
    EditText etPayAccount;
    @BindView(R.id.iv_idCardFront)
    SimpleDraweeView ivIdCardFront;
    @BindView(R.id.iv_idCardSide)
    SimpleDraweeView ivIdCardSide;
    @BindView(R.id.iv_idCardHand)
    SimpleDraweeView ivIdCardHand;
    @BindView(R.id.iv_accidentPics)
    SimpleDraweeView ivAccidentPics;
    @BindView(R.id.iv_crimePic)
    SimpleDraweeView ivCrimePic;
    @BindView(R.id.et_intro)
    EditText etIntro;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;
    @BindView(R.id.iv_honor1)
    SimpleDraweeView ivHonor1;
    @BindView(R.id.iv_honor2)
    SimpleDraweeView ivHonor2;
    @BindView(R.id.iv_honor3)
    SimpleDraweeView ivHonor3;
    @BindView(R.id.iv_honor4)
    SimpleDraweeView ivHonor4;
    @BindView(R.id.ll_show_horpic)
    LinearLayout llShowHorpic;
    @BindView(R.id.iv_header)
    SimpleDraweeView ivHeader;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    private WorkerInfoBean workerInfoBean;
    private WorkerInfoBean setWorkerInfoBean = new WorkerInfoBean();
    private Map<String, String> uploadMap = new HashMap<>();
    private ArrayList<String> honorList = new ArrayList<>();
    private static final int REQUEST_CODE_CHOOSE_PHOTO_1 = 1;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_1 = 7;


    //身份证正面
    private final int ID_CARD_FRONT = 101;
    // 身份证反面
    private final int ID_CARD_SIDE = 102;
    //身份证手持
    private final int ID_CARD_HAND = 103;
    //意外保险
    private final int ACCIDENT_PIC = 104;
    //荣誉证书
    private final int HONOR_PIC = 105;
    //有无犯罪记录
    private final int CRIME_PIC = 106;

    private final int HEADER_PIC = 107;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_worker);
        ButterKnife.bind(this);
        initView();
        setOnClick();

    }

    private void setOnClick() {
        llWorkingLevel.setOnClickListener((v) -> PickerSelectUtil.singleTextPicker(this, "", tvWorkingLevel, constDataUtils.getWorkingLevelList()));
        llWorkingYear.setOnClickListener((v) -> PickerSelectUtil.singleTextPicker(this, "", tvWorkingYear, constDataUtils.getWorkingYearList()));
        llPayType.setOnClickListener((v) -> PickerSelectUtil.singleTextPicker(this, "", tvPayType, constDataUtils.getPayTypeList()));

        ivIdCardFront.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(ID_CARD_FRONT)));
        ivIdCardSide.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(ID_CARD_SIDE)));
        ivIdCardHand.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(ID_CARD_HAND)));
        ivCrimePic.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(CRIME_PIC)));
        ivAccidentPics.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(ACCIDENT_PIC)));
        ivHeader.setOnClickListener(v -> PermissionUtils.get(this).getCameraPermission(() -> takePhoto(HEADER_PIC)));

        setRightTitleOnClickListener(v -> setData());
        setRightTitleOnClickListener((v) -> {
            if (workerInfoBean.getStatus() == 0 || workerInfoBean.getStatus() == 3) {
                setData();
            } else {
                jump();
            }
        });
    }

    /**
     * 图片选择 回调
     *
     * @param result
     * @param resultCode
     */
    @Override
    public void takeSuccess(TResult result, int resultCode) {
        super.takeSuccess(result, resultCode);
        if (result == null || result.getImage() == null) {
            return;
        }
        TImage image = result.getImage();
        String imgKey = UuidUtil.getUUID() + ".png";
        switch (resultCode) {
            case ID_CARD_FRONT:
                setWorkerInfoBean.setIdCardFront(imgKey);
                ivIdCardFront.setImageURI("file://" + image.getOriginalPath());
                break;
            case ID_CARD_SIDE:
                setWorkerInfoBean.setIdCardSide(imgKey);
                ivIdCardSide.setImageURI("file://" + image.getOriginalPath());
                break;
            case ID_CARD_HAND:
                setWorkerInfoBean.setIdCardHand(imgKey);
                ivIdCardHand.setImageURI("file://" + image.getOriginalPath());
                break;
            case CRIME_PIC:
                setWorkerInfoBean.setCrimePic(imgKey);
                ivCrimePic.setImageURI("file://" + image.getOriginalPath());
                break;
            case ACCIDENT_PIC:
                setWorkerInfoBean.setAccidentPics(imgKey);
                ivAccidentPics.setImageURI("file://" + image.getOriginalPath());
                break;
            case HEADER_PIC:
                setWorkerInfoBean.setAvatarPhoto(imgKey);
                ivHeader.setImageURI("file://" + image.getOriginalPath());
                break;
            default:
                break;
        }
        OSSUtils.initOSS(this).asyncPutImage(imgKey, image.getOriginalPath(), new OSSCallBack(this, true) {
        });

    }

    private void initView() {
        setTitle("填写技师资料");
        setLeftBack();
        setRightTitle("下一步");
        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this));
        workerInfoBean = (WorkerInfoBean) getIntent().getSerializableExtra("bean");
        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
        fillData();
    }

    private void fillData() {
        if (workerInfoBean != null) {
            if (workerInfoBean.getContactName() != null) {
                etContactName.setText(workerInfoBean.getContactName());
            }
            if (workerInfoBean.getContactPhone() != null) {
                etContactPhone.setText(workerInfoBean.getContactPhone());
            }
            if (workerInfoBean.getWorkingLevel() >= 0) {
                tvWorkingLevel.setText(constDataUtils.getWorkingLevelList().get(workerInfoBean.getWorkingLevel()));
            }
            if (workerInfoBean.getWorkingYear() >= 0) {
                tvWorkingYear.setText(constDataUtils.getWorkingYearList().get(workerInfoBean.getWorkingYear()));
            }
            if (workerInfoBean.getPayType() >= 0) {
                tvPayType.setText(constDataUtils.getPayTypeList().get(workerInfoBean.getPayType()));
            }
            if (workerInfoBean.getPayAccount() != null) {
                etPayAccount.setText(workerInfoBean.getPayAccount());
            }
            if (workerInfoBean.getAccidentPics() != null) {
                ivAccidentPics.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + workerInfoBean.getAccidentPics()));
            }
            if (workerInfoBean.getIdCardFront() != null) {
                ivIdCardFront.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + workerInfoBean.getIdCardFront()));
            }
            if (workerInfoBean.getContactPhone() != null) {
                ivIdCardSide.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + workerInfoBean.getIdCardSide()));
            }
            if (workerInfoBean.getIdCardHand() != null) {
                ivIdCardHand.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + workerInfoBean.getIdCardHand()));
            }
            if (workerInfoBean.getAvatarPhoto() != null) {
                ivHeader.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + workerInfoBean.getAvatarPhoto()));
                tvUserName.setText(workerInfoBean.getContactName());

            }

            if (workerInfoBean.getCrimePic() != null) {
                ivCrimePic.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + workerInfoBean.getCrimePic()));
            }

            if (workerInfoBean.getHonorPics()!=null) {
                String[] prePic = workerInfoBean.getHonorPics().split(",");
                honorList.addAll(Stream.of(Arrays.asList(prePic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
                snplMomentAddPhotos.setEditable(false);
                snplMomentAddPhotos.setData(honorList);
            }


            if (workerInfoBean.getIntro() != null) {
                etIntro.setText(workerInfoBean.getIntro());
            }
        }
    }

    private void setData() {
        setWorkerInfoBean.setContactName(etContactName.getText().toString().trim());
        setWorkerInfoBean.setContactPhone(etContactPhone.getText().toString().trim());
        setWorkerInfoBean.setContactName(etContactName.getText().toString().trim());
        setWorkerInfoBean.setPayType(constDataUtils.getPayTypeList().indexOf(tvPayType.getText().toString().trim()));
        setWorkerInfoBean.setWorkingLevel(constDataUtils.getWorkingLevelList().indexOf(tvWorkingLevel.getText().toString().trim()));
        setWorkerInfoBean.setWorkingYear(constDataUtils.getWorkingYearList().indexOf(tvWorkingYear.getText().toString().trim()));
        setWorkerInfoBean.setPayAccount(etPayAccount.getText().toString().trim());
        setWorkerInfoBean.setAccId(workerInfoBean.getAccId());
        setWorkerInfoBean.setUserId(EanfangApplication.getApplication().getUser().getAccount().getNullUser());
        setWorkerInfoBean.setId(workerInfoBean.getId());
        setWorkerInfoBean.setIntro(etIntro.getText().toString().trim());
        setWorkerInfoBean.setStatus(0);
        String ursStr = PhotoUtils.getPhotoUrl(snplMomentAddPhotos, uploadMap, true);
        setWorkerInfoBean.setHonorPics(ursStr);
        String json = JSONObject.toJSONString(setWorkerInfoBean);
        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> submitSuccess(json));
                }
            });
        }
        submitSuccess(json);

    }

    private void submitSuccess(String json) {
        EanfangHttp.post(UserApi.GET_TECH_WORKER_ADD)
                .upJson(json)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    jump();
                }));
    }

    private void jump() {
        startActivity(new Intent(AuthWorkerInfoActivity.this,
                AuthWorkerSysTypeActivity.class).putExtra("status", workerInfoBean.getStatus()));
    }

    /**
     * 初始化存储图片用的List集合
     */
    private void initImgUrlList() {

        //修改小bug 图片读取问题
        if (StringUtils.isValid(setWorkerInfoBean.getHonorPics())) {
            String[] prePic = setWorkerInfoBean.getHonorPics().split(",");
            honorList.addAll(Stream.of(Arrays.asList(prePic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }

    }

    private void initNinePhoto() {
        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
        snplMomentAddPhotos.setData(honorList);
        snplMomentAddPhotos.setEditable(false);


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
            case REQUEST_CODE_PHOTO_PREVIEW_1:
                snplMomentAddPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            default:
                break;
        }

    }
}
