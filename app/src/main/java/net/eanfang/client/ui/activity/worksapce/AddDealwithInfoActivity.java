package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.util.PhotoUtils;
import com.google.gson.Gson;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.oss.OSSCallBack;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.WorkCheckInfoBean;
import net.eanfang.client.ui.widget.WorkCheckInfoView;
import net.eanfang.client.util.OSSUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  14:25
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class AddDealwithInfoActivity extends BaseActivity {
    @BindView(R.id.et_title)
    TextView etTitle;
    @BindView(R.id.et_input_check_content)
    EditText etInputCheckContent;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout mPhotosSnpl;

    private Map<String, String> uploadMap = new HashMap<>();

    private WorkCheckInfoBean.BeanBean.DetailsBeanX detailsBeanX;
    private WorkCheckInfoBean.BeanBean.DetailsBeanX.DetailsBean bean = new WorkCheckInfoBean.BeanBean.DetailsBeanX.DetailsBean();
    private String inspectDetailUid;
    private String creatUser;
    private String creatCompanyId;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dealwith_info);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", id);
        detailsBeanX = (WorkCheckInfoBean.BeanBean.DetailsBeanX) getIntent().getSerializableExtra("data");
        initView();
        initAdapter();

        setTitle("添加处理明细");
        setLeftBack();
        setRightTitle("完成");
    }

    private void initView() {
        etTitle.setText(detailsBeanX.getTitle());
        inspectDetailUid = detailsBeanX.getUid();
        creatUser = detailsBeanX.getCreateUser();
        creatCompanyId = detailsBeanX.getCreateCompanyUid();

    }

    private void initAdapter() {

        setRightTitleOnClickListener(new MultiClickListener(this, this::checkInfo, this::submit));
        mPhotosSnpl.setDelegate(new BGASortableDelegate(this));
    }


    private boolean checkInfo() {
        if (TextUtils.isEmpty(etTitle.getText().toString().trim())) {
            showToast("请输入标题");
            return false;
        }
        if (TextUtils.isEmpty(etInputCheckContent.getText().toString().trim())) {
            showToast("请填写处理明细");
            return false;
        }
        return true;
    }

    private void submit() {
        bean.setInspectDetailTitle(etTitle.getText().toString().trim());
        bean.setInspectDetailUid(inspectDetailUid);
        bean.setCreateUser(creatUser);
        bean.setCreateCompanyUid(creatCompanyId);
        bean.setDisposeInfo(etInputCheckContent.getText().toString().trim());
        bean.setRemark(etRemark.getText().toString().trim());

        List<String> urls = PhotoUtils.getPhotoUrl(mPhotosSnpl, uploadMap, true);
        bean.setPic1(urls.get(0));
        bean.setPic2(urls.get(1));
        bean.setPic3(urls.get(2));

        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                        doHttp(new Gson().toJson(bean));
                    });
                }
            });
        } else {
            doHttp(new Gson().toJson(bean));
        }
    }

    private void doHttp(String jsonString) {
        EanfangHttp.post(ApiService.ADD_WORK_INSPECT_DETAIL_DISPOSE)
                .upJson(jsonString)
                .execute(new EanfangCallback(this, true) {
                    @Override
                    public void onSuccess(Object object) {
                        runOnUiThread(() -> {

                            new WorkCheckInfoView(AddDealwithInfoActivity.this, true, id).show();

                        });
                    }
                });

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
}
