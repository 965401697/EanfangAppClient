package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.util.PhotoUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.client.R;
import net.eanfang.client.config.EanfangConst;
import net.eanfang.client.oss.OSSCallBack;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.WorkAddReportBean;
import net.eanfang.client.util.OSSUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.hou
 *
 * @on 2017/9/4  11:44
 * @email houzhongzhou@yeah.net
 * @desc 添加完成工作
 */

public class AddReportCompleteActivity extends BaseActivity {

    @BindView(R.id.et_input_content)
    EditText etInputContent;
    @BindView(R.id.et_input_jion)
    EditText etInputJion;
    @BindView(R.id.et_input_legacy)
    EditText etInputLegacy;
    @BindView(R.id.et_input_reason)
    EditText etInputReason;
    @BindView(R.id.et_input_handle)
    EditText etInputHandle;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;

    private WorkAddReportBean.DetailsBean bean;
    private Map<String, String> uploadMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complete_info);
        ButterKnife.bind(this);
        initData();
        setLeftBack();
        setTitle("完成工作");
        setRightTitle("完成");
    }

    private void initData() {
        bean = new WorkAddReportBean.DetailsBean();
        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this));
        setRightTitleOnClickListener(new MultiClickListener(this, this::checkInfo, this::submit));


    }

    private boolean checkInfo() {
        if (TextUtils.isEmpty(etInputContent.getText().toString().trim())) {

            showToast("请填写工作内容");
            return false;
        }
        if (TextUtils.isEmpty(etInputJion.getText().toString().trim())) {
            showToast("请填写协同人员");
            return false;
        }
        if (TextUtils.isEmpty(etInputLegacy.getText().toString().trim())) {
            showToast("请填写遗留问题");
            return false;
        }
        if (TextUtils.isEmpty(etInputReason.getText().toString().trim())) {
            showToast("请填写原因");
            return false;
        }
        if (TextUtils.isEmpty(etInputHandle.getText().toString().trim())) {
            showToast("请填写处理措施");
            return false;
        }

        return true;
    }

    private void submit() {

        bean.setType(EanfangConst.TYPE_REPORT_DETAIL_FINISH);

        //工作内容
        bean.setField1(etInputContent.getText().toString().trim());

        //同事协同
        bean.setField2(etInputJion.getText().toString().trim());

        //遗留问题
        bean.setField3(etInputLegacy.getText().toString().trim());

        //未完成原因
        bean.setField4(etInputReason.getText().toString().trim());

        //处理
        bean.setField5(etInputHandle.getText().toString().trim());

        List<String> urls = PhotoUtils.getPhotoUrl(snplMomentAddPhotos, uploadMap,true);
        bean.setPic1(urls.get(0));
        bean.setPic2(urls.get(1));
        bean.setPic3(urls.get(2));

        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    submitSuccess();
                }
            });
        } else {
            submitSuccess();
        }
    }

    private void submitSuccess() {
        Intent intent = new Intent();
        intent.putExtra("result", bean);
        setResult(10081, intent);
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
}
