package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.model.WorkAddReportBean;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.client.R;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Mr.hou
 *
 * @on 2017/9/4  11:46
 * @email houzhongzhou@yeah.net
 * @desc 添加后续计划
 */

public class AddReportPlanActivity extends BaseClientActivity implements View.OnClickListener {
    @BindView(R.id.et_input_content)
    EditText etInputContent;
    @BindView(R.id.et_input_jion)
    EditText etInputJion;
    @BindView(R.id.et_input_legacy)
    EditText etInputLegacy;
    @BindView(R.id.et_input_reason)
    EditText etInputReason;
    @BindView(R.id.tv_depend_person)
    TextView tvDependPerson;
    @BindView(R.id.ll_depend_person)
    LinearLayout llDependPerson;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;


    private WorkAddReportBean.WorkReportDetailsBean bean;
    private Map<String, String> uploadMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("工作计划");
        setRightTitle("提交");
        llDependPerson.setOnClickListener(this);
        bean = new WorkAddReportBean.WorkReportDetailsBean();
        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this));
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit() {
        bean.setType(2);

        //工作内容
        String content = etInputContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {

            showToast("请填写工作内容");
            return;
        }
        bean.setField1(content);

        //同事协同
        String join = etInputJion.getText().toString().trim();
        if (TextUtils.isEmpty(join)) {
            showToast("请填目的");
            return;
        }
        bean.setField2(join);

        //遗留问题
        String leave = etInputLegacy.getText().toString().trim();
        if (TextUtils.isEmpty(leave)) {
            showToast("请填写标准");
            return;
        }
        bean.setField3(leave);

        //未完成原因
        String reason = etInputReason.getText().toString().trim();
        if (TextUtils.isEmpty(reason)) {
            showToast("请填写协同人员");
            return;
        }
        bean.setField4(reason);

        //处理
        String handle = tvDependPerson.getText().toString().trim();
        if (TextUtils.isEmpty(handle)) {
            showToast("请填写期限");
            return;
        }
        bean.setField5(handle);
        String ursStr = PhotoUtils.getPhotoUrl(snplMomentAddPhotos, uploadMap, true);
        bean.setPictures(ursStr);
        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                        submitSuccess();
                    });

                }
            });
        } else {
            submitSuccess();
        }

    }

    private void submitSuccess() {
        Intent intent = new Intent();
        intent.putExtra("result", bean);
        setResult(3, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == BGASortableDelegate.REQUEST_CODE_CHOOSE_PHOTO) {
            snplMomentAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
        } else if (requestCode == BGASortableDelegate.REQUEST_CODE_PHOTO_PREVIEW) {
            snplMomentAddPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_depend_person:
                PickerSelectUtil.onUpYearMonthDayPicker(this, tvDependPerson);
                break;
            default:
                break;
        }
    }
}
