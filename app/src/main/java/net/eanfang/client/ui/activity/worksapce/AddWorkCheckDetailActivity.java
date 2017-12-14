package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.bigkoo.pickerview.OptionsPickerView;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.util.PhotoUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.oss.OSSCallBack;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.BusinessOne;
import net.eanfang.client.ui.model.BusinessWorkBean;
import net.eanfang.client.ui.model.WorkAddCheckBean;
import net.eanfang.client.util.OSSUtils;
import net.eanfang.client.util.PickerSelectUtil;
import net.eanfang.client.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Mr.hou
 *
 * @on 2017/9/6  14:36
 * @email houzhongzhou@yeah.net
 * @desc 添加检查明细
 */

public class AddWorkCheckDetailActivity extends BaseActivity {
    @BindView(R.id.et_position)
    EditText etPosition;
    @BindView(R.id.tv_one_name)
    TextView tvOneName;
    @BindView(R.id.ll_one_type)
    LinearLayout llOneType;
    @BindView(R.id.tv_two_name)
    TextView tvTwoName;
    @BindView(R.id.ll_two_type)
    LinearLayout llTwoType;
    @BindView(R.id.tv_three_name)
    TextView tvThreeName;
    @BindView(R.id.ll_three_type)
    LinearLayout llThreeType;
    @BindView(R.id.et_input_check_content)
    EditText etInputCheckContent;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout mPhotosSnpl;
    @BindView(R.id.et_title)
    EditText etTitle;

    private WorkAddCheckBean.DetailsBean detailsBean;
    private int position;
    private int position2 = -1;
    private int position3 = -1;
    private String bugOneCode;
    private OptionsPickerView pvOptions_NoLink;
    private Map<String, String> uploadMap = new HashMap<>();
    private List<BusinessOne> businessOneList = new ArrayList<>();
    private BusinessWorkBean businessBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail);
        ButterKnife.bind(this);
        initData();
        initAdapter();

        setTitle("添加检查明细");
        setLeftBack();
        setRightTitle("完成");
    }

    /**
     * 获取系统类别
     */
    private void initData() {
//        businessOneList = Config.getConfig().getBusinessOneList();
    }

    private void initAdapter() {
        setRightTitleOnClickListener(new MultiClickListener(this, this::checkInfo, this::submit));
        //系统类别
        llOneType.setOnClickListener((v) -> {
            showBusinessOne();

        });
        //设备类别
        llTwoType.setOnClickListener((v) -> {
            showBusinessTwo();
        });

        //设备名称
        llThreeType.setOnClickListener((v) -> {
            showBusinessThree();
        });
        //上传图片
        mPhotosSnpl.setDelegate(new BGASortableDelegate(this));
    }

    private boolean checkInfo() {
        String title = etTitle.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            showToast("请填写明细标题");
            return false;
        }
        if (TextUtils.isEmpty(etPosition.getText().toString().trim())) {
            showToast("请填写区域位置");
            return false;
        }
        if (TextUtils.isEmpty(tvOneName.getText().toString().trim())) {
            showToast("请填写系统类别");
            return false;
        }
        if (TextUtils.isEmpty(tvTwoName.getText().toString().trim())) {
            showToast("请填写设备类别");
            return false;
        }

        if (TextUtils.isEmpty(tvThreeName.getText().toString().trim())) {
            showToast("请填写设备名称");
            return false;
        }
        if (TextUtils.isEmpty(etInputCheckContent.getText().toString().trim())) {
            showToast("请填写检查内容");
            return false;
        }
        return true;
    }

    private void submit() {
        detailsBean = new WorkAddCheckBean.DetailsBean();
        detailsBean.setTitle(etTitle.getText().toString().trim());
        detailsBean.setRegion(etPosition.getText().toString().trim());
        detailsBean.setBusinessOne(tvOneName.getText().toString().trim());
        detailsBean.setBusinessTwo(tvTwoName.getText().toString().trim());
        detailsBean.setBusinessThree(tvThreeName.getText().toString().trim());
        detailsBean.setInfo(etInputCheckContent.getText().toString().trim());

        List<String> urls = PhotoUtils.getPhotoUrl(mPhotosSnpl, uploadMap,true);
        detailsBean.setPic1(urls.get(0));
        detailsBean.setPic2(urls.get(1));
        detailsBean.setPic3(urls.get(2));

        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                        Intent intent = new Intent();
                        intent.putExtra("result", detailsBean);
                        setResult(10084, intent);
                        finish();
                    });
                }
            });
        } else {
            Intent intent = new Intent();
            intent.putExtra("result", detailsBean);
            setResult(10084, intent);
            finish();
        }
    }


    /**
     * 获取设备类型
     */
    private void getBusinessType() {
        EanfangHttp.get(ApiService.GET_BUSINESS_BY_CODE)
                .params("code", bugOneCode)
                .execute(new EanfangCallback<BusinessWorkBean>(this, true) {
                    @Override
                    public void onSuccess(BusinessWorkBean bean) {
                        businessBean = bean;
                    }

                    @Override
                    public void onError(String message) {
                    }
                });
    }

    /**
     * 系统类别
     */
    private void showBusinessOne() {
        List<String> opts = Stream.of(businessOneList).map(bus -> bus.getName()).toList();
        PickerSelectUtil.singleTextPicker(this, "业务类型", opts, (index, item) -> {
            position = index;
            bugOneCode = businessOneList.get(position).getCode();
            tvOneName.setText(businessOneList.get(position).getName());
            cleanBusiness("two");
            getBusinessType();
        });
    }

    /**
     * 设备类别选择器
     */
    private void showBusinessTwo() {
        if (StringUtils.isEmpty(tvOneName.getText().toString().trim())) {
            showToast("请先选择系统类别");
            return;
        }
        //非空判断
        if (businessBean == null || businessBean.getBusiness() == null) {
            return;
        }
        List<String> opts = Stream.of(businessBean.getBusiness().getTow()).map(bug -> bug.getName()).toList();
        PickerSelectUtil.singleTextPicker(this, "故障类别", opts, (index, item) -> {
            position2 = index;
            tvTwoName.setText(businessBean.getBusiness().getTow().get(position2).getName());
            cleanBusiness("three");
        });
    }

    /**
     * 设备名称选择器
     */
    private void showBusinessThree() {
        if (StringUtils.isEmpty(tvTwoName.getText().toString().trim())) {
            showToast("请先选择设备类型");
            return;
        }
        List<String> opts = Stream.of(businessBean.getBusiness().getTow().get(position2).getThree()).map(bug -> bug.getName()).toList();
        PickerSelectUtil.singleTextPicker(this,"故障设备", opts, (index, item) -> {
            position3 = index;
            tvThreeName.setText(businessBean.getBusiness().getTow().get(position2).getThree().get(position3).getName());
        });
    }

    /**
     * 清除业务类型联动
     *
     * @param type one一级  two二级 three三级
     */
    public void cleanBusiness(String type) {
        //一级变更 全部清空
        if ("two".equals(type)) {
            tvTwoName.setText("");
            tvThreeName.setText("");
        } else {
            tvThreeName.setText("");
        }
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
