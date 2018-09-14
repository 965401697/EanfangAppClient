package net.eanfang.worker.ui.activity.worksapce.oa.check;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.model.AddWorkInspectDetailBean;
import com.eanfang.model.WorkCheckInfoBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.util.PhotoUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author guanluocang
 * @data 2018/9/14
 * @description 设备点检之 - 添加处理明细
 */

public class AddDealwithInfoActivity extends BaseWorkerActivity {
    @BindView(R.id.et_title)
    TextView etTitle;
    @BindView(R.id.et_input_check_content)
    EditText etInputCheckContent;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout mPhotosSnpl;

    private Map<String, String> uploadMap = new HashMap<>();
    private AddWorkInspectDetailBean addWorkInspectDetailBean = new AddWorkInspectDetailBean();

    private Long detailId, id;
    private WorkCheckInfoBean.WorkInspectDetailsBean detailsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dealwith_info);
        ButterKnife.bind(this);
        detailId = getIntent().getLongExtra("detailId", 0);
        id = getIntent().getLongExtra("id", 0);
        detailsBean = (WorkCheckInfoBean.WorkInspectDetailsBean) getIntent().getSerializableExtra("data");

        initAdapter();

        setTitle("添加处理明细");
        setLeftBack();
        setRightTitle("完成");
    }


    private void initAdapter() {
        etTitle.setText(detailsBean.getTitle());
        setRightTitleOnClickListener(new MultiClickListener(this, this::checkInfo, this::submit));
        mPhotosSnpl.setDelegate(new BGASortableDelegate(this));
    }


    private boolean checkInfo() {
//        if (TextUtils.isEmpty(etTitle.getText().toString().trim())) {
//            showToast("请输入标题");
//            return false;
//        }
        if (TextUtils.isEmpty(etInputCheckContent.getText().toString().trim())) {
            showToast("请填写处理明细");
            return false;
        }
        return true;
    }

    private void submit() {
        addWorkInspectDetailBean.setOaWorkInspectDetailId(detailId);
        addWorkInspectDetailBean.setDisposeInfo(etInputCheckContent.getText().toString().trim());
        addWorkInspectDetailBean.setRemarkInfo(etRemark.getText().toString().trim());
        String ursStr = PhotoUtils.getPhotoUrl(mPhotosSnpl, uploadMap, true);
        addWorkInspectDetailBean.setPictures(ursStr);
        addWorkInspectDetailBean.setOaWorkInspectId(id);

        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                        doHttp(JSON.toJSONString(addWorkInspectDetailBean));
                    });
                }
            });
        } else {
            doHttp(JSON.toJSONString(addWorkInspectDetailBean));
        }
    }

    private void doHttp(String jsonString) {
        EanfangHttp.post(NewApiService.ADD_WORK_CHECK_DETAIL)
                .upJson(jsonString)
                .execute(new EanfangCallback(this, true, JSONObject.class, (bean) -> {
//                    runOnUiThread(() -> {
//                    new WorkCheckInfoView(AddDealwithInfoActivity.this, true, id, false).show();
//                    });
                    closeBeforeActivity();
                }));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == BGASortableDelegate.REQUEST_CODE_CHOOSE_PHOTO) {
            mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
        } else if (requestCode == BGASortableDelegate.REQUEST_CODE_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }
    }

    public void closeBeforeActivity() {
        EanfangApplication.get().closeActivity(LookWorkCheckInfoActivity.class.getName());
        EanfangApplication.get().closeActivity(WorkCheckInfoActivity.class.getName());
        EventBus.getDefault().post("addDealWithInfoSuccess");
        finishSelf();
    }
}