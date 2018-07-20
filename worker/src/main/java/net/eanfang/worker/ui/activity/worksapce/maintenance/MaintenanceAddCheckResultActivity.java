package net.eanfang.worker.ui.activity.worksapce.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.eanfang.config.Config;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.ShopMaintenanceExamResultEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Stream;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_check_result);
        ButterKnife.bind(this);
        setTitle("新增检查结果");
        setLeftBack();

        initViews();
        snplPhoto.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
    }

    private void initViews() {
//        snplPhoto.setData(picList);
    }

    @OnClick({R.id.tv_add, R.id.ll_conclusion})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add:

                if (!isCheck()) return;

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
                    OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                        @Override
                        public void onOssSuccess() {
                            Intent intent = new Intent();
                            intent.putExtra("bean", resultEntity);
                            setResult(RESULT_OK, intent);
                            finishSelf();
                        }
                    });
                    return;
                }


                break;

            case R.id.ll_conclusion:
                //系统类别

                PickerSelectUtil.singleTextPicker(this, "", tvConclusion, GetConstDataUtils.getMaintainConditionList());

                break;
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
                snplPhoto.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;

            case REQUEST_CODE_PHOTO_PREVIEW_1:
                snplPhoto.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
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
        checkResultPhoto = PhotoUtils.getPhotoUrl(snplPhoto, uploadMap, false);
        if (StringUtils.isEmpty(checkResultPhoto)) {
            showToast("请选择照片");
            return false;
        }


        return true;
    }
}
