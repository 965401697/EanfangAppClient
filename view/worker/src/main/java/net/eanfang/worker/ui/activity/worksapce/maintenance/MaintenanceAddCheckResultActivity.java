package net.eanfang.worker.ui.activity.worksapce.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.eanfang.BuildConfig;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.delegate.BGASortableDelegate;

import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.ToastUtil;
import com.eanfang.biz.model.entity.ShopMaintenanceExamDeviceEntity;
import com.eanfang.biz.model.entity.ShopMaintenanceExamResultEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import cn.hutool.core.util.StrUtil;

public class MaintenanceAddCheckResultActivity extends BaseWorkerActivity {

    @BindView(R.id.et_question)
    EditText etQuestion;
    @BindView(R.id.et_handle)
    EditText etHandle;
    @BindView(R.id.et_notice)
    EditText etNotice;
    @BindView(R.id.ll_conclusion)
    LinearLayout llConclusion;
    @BindView(R.id.snpl_photo)
    BGASortableNinePhotoLayout snplPhoto;
    @BindView(R.id.tv_conclusion)
    EditText tvConclusion;

    private static final int REQUEST_CODE_CHOOSE_PHOTO_1 = 1;

    private static final int REQUEST_CODE_PHOTO_PREVIEW_1 = 101;
    /**
     * （3张）
     */
    private ArrayList<String> picList = new ArrayList<>();

    private HashMap<String, String> uploadMap = new HashMap<>();
    private String checkResultPhoto;
    private ShopMaintenanceExamResultEntity examResultEntity;


    /**
     * 扫码看设备
     */
    private String mType = "";
    private long mId;
    private ArrayList<ShopMaintenanceExamDeviceEntity> examResultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_check_result);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("新增检查结果");
        setLeftBack();


        initViews();
    }

    private void initViews() {
        mId = getIntent().getLongExtra("orderId", 0);
        examResultList = (ArrayList<ShopMaintenanceExamDeviceEntity>) getIntent().getSerializableExtra("list");
        mType = getIntent().getStringExtra("type");

        snplPhoto.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
        examResultEntity = (ShopMaintenanceExamResultEntity) getIntent().getSerializableExtra("bean");

        if (examResultEntity != null) {
            etQuestion.setText(examResultEntity.getExistQuestions());
            etHandle.setText(examResultEntity.getDisposeCourse());
            etNotice.setText(examResultEntity.getInfo());
            tvConclusion.setText(GetConstDataUtils.getMaintainConditionList().get(examResultEntity.getStatus()));
            initImgUrlList();
            snplPhoto.setData(picList);
        }

    }

    @OnClick({R.id.tv_add, R.id.ll_conclusion, R.id.tv_conclusion})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add:

                if (!isCheck()) {
                    return;
                }

                ShopMaintenanceExamResultEntity resultEntity = new ShopMaintenanceExamResultEntity();
                resultEntity.setExistQuestions(etQuestion.getText().toString().trim());
                resultEntity.setDisposeCourse(etHandle.getText().toString().trim());
                resultEntity.setInfo(etNotice.getText().toString().trim());
                resultEntity.setTime(new Date(System.currentTimeMillis()));
                resultEntity.setStatus(GetConstDataUtils.getMaintainConditionList().indexOf(tvConclusion.getText().toString().trim()));
                resultEntity.setPicture(checkResultPhoto);
                /**
                 * 提交照片
                 * */
                if (uploadMap.size() != 0) {
                    SDKManager.ossKit(this).asyncPutImages(uploadMap,(isSuccess) -> {
                        if (mType != null && mType.equals("scanDevice")) {
                            doPassTwo(resultEntity);
                        } else {
                            doPassOne(resultEntity);
                        }
                    });
                    return;
                }
                if (mType != null &&mType.equals("scanDevice")) {
                    doPassTwo(resultEntity);
                } else {
                    if (examResultEntity != null) {
                        doPassOne(resultEntity);
                    }

                }

                break;

            case R.id.ll_conclusion:
            case R.id.tv_conclusion:
                //系统类别

                PickerSelectUtil.singleTextPicker(this, "", tvConclusion, GetConstDataUtils.getMaintainConditionList());

                break;
            default:
                break;
        }
    }

    private void doPassOne(ShopMaintenanceExamResultEntity resultEntity) {
        Intent intent = new Intent();
        intent.putExtra("bean", resultEntity);
        setResult(RESULT_OK, intent);
        finishSelf();
    }

    private void doPassTwo(ShopMaintenanceExamResultEntity resultEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", resultEntity);
        bundle.putLong("orderId", mId);
        bundle.putSerializable("list", examResultList);
        bundle.putString("type", "scanDevice");
        JumpItent.jump(MaintenanceAddCheckResultActivity.this, MaintenanceHandleActivity.class, bundle);
        finishSelf();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_PHOTO_1:
                snplPhoto.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
                break;

            case REQUEST_CODE_PHOTO_PREVIEW_1:
                snplPhoto.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
                break;

            default:
                break;
        }
    }

    private boolean isCheck() {
        if (TextUtils.isEmpty(etQuestion.getText().toString().trim())) {
            ToastUtil.get().showToast(this, "请输入存在问题");
            return false;
        }
        if (TextUtils.isEmpty(etHandle.getText().toString().trim())) {
            ToastUtil.get().showToast(this, "请输入处理过程");
            return false;
        }
        if (TextUtils.isEmpty(etNotice.getText().toString().trim())) {
            ToastUtil.get().showToast(this, "请输入备注信息");
            return false;
        }
        if (TextUtils.isEmpty(tvConclusion.getText().toString().trim())) {
            ToastUtil.get().showToast(this, "请选择处理结论");
            return false;
        }
        checkResultPhoto = PhotoUtils.getPhotoUrl("biz/maintain/", snplPhoto, uploadMap, false);
        if (StrUtil.isEmpty(checkResultPhoto)) {
            showToast("请选择照片");
            return false;
        }


        return true;
    }

    /**
     * 初始化存储图片用的List集合
     */
    private void initImgUrlList() {
        //修改小bug 图片读取问题
        if (!StrUtil.isEmpty(examResultEntity.getPicture())) {
            String[] presentationPic = examResultEntity.getPicture().split(",");
            if (presentationPic.length >= 1) {
                picList.add(BuildConfig.OSS_SERVER + presentationPic[0]);
            }
            if (presentationPic.length >= 2) {
                picList.add(BuildConfig.OSS_SERVER + presentationPic[1]);
            }
            if (presentationPic.length >= 3) {
                picList.add(BuildConfig.OSS_SERVER + presentationPic[2]);
            }
        }
    }
}
