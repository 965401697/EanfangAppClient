package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.eanfang.delegate.BGASortableDelegate;
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
 * @on 2017/9/4  11:45
 * @email houzhongzhou@yeah.net
 * @desc 添加发现问题
 */

public class AddReportFindActivity extends BaseActivity {
    @BindView(R.id.et_input_content)
    EditText etInputContent;
    @BindView(R.id.et_input_jion)
    EditText etInputJion;
    @BindView(R.id.et_input_handle)
    EditText etInputHandle;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;

    private WorkAddReportBean.DetailsBean bean;
    private Map<String, String> uploadMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_find_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("发现问题");
        setRightTitle("完成");
        bean = new WorkAddReportBean.DetailsBean();
        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this));
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

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
        if (TextUtils.isEmpty(etInputHandle.getText().toString().trim())) {
            showToast("请填写处理措施");
            return false;
        }
        return true;
    }

    private void submit() {
        bean.setType(EanfangConst.TYPE_REPORT_DETAIL_FIND);
        //工作内容
        bean.setField1(etInputContent.getText().toString().trim());
        //同事协同
        bean.setField2(etInputJion.getText().toString().trim());
        //处理
        bean.setField3(etInputHandle.getText().toString().trim());

        List<String> urls = PhotoUtils.getPhotoUrl(snplMomentAddPhotos, uploadMap,true);
        bean.setPic1(urls.get(0));
        bean.setPic2(urls.get(1));
        bean.setPic3(urls.get(2));

        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                        Intent intent = new Intent();
                        intent.putExtra("result", bean);
                        setResult(10081, intent);
                        finish();
                    });

                }
            });
        } else {
            Intent intent = new Intent();
            intent.putExtra("result", bean);
            setResult(10082, intent);
            finish();
        }
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
