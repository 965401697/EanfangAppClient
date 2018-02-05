package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.config.Config;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.model.WorkAddCheckBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;

import java.util.HashMap;
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

    private WorkAddCheckBean.WorkInspectDetailsBean detailsBean;

    private Map<String, String> uploadMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail);
        ButterKnife.bind(this);
//        initData();
        initAdapter();

        setTitle("添加检查明细");
        setLeftBack();
        setRightTitle("完成");
    }


    private void initAdapter() {
        setRightTitleOnClickListener(new MultiClickListener(this, this::checkInfo, this::submit));
        //系统类别
        llOneType.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tvOneName, Stream.of(Config.get().getBusinessList(1)).map(bus -> bus.getDataName()).toList());

        });
        //设备类别
        llTwoType.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tvOneName, Stream.of(Config.get().getBusinessList(2)).map(bus -> bus.getDataName()).toList());
        });

        //设备名称
        llThreeType.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tvOneName, Stream.of(Config.get().getBusinessList(3)).map(bus -> bus.getDataName()).toList());
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
//        if (TextUtils.isEmpty(tvOneName.getText().toString().trim())) {
//            showToast("请填写系统类别");
//            return false;
//        }
//        if (TextUtils.isEmpty(tvTwoName.getText().toString().trim())) {
//            showToast("请填写设备类别");
//            return false;
//        }
//
//        if (TextUtils.isEmpty(tvThreeName.getText().toString().trim())) {
//            showToast("请填写设备名称");
//            return false;
//        }
//        if (TextUtils.isEmpty(etInputCheckContent.getText().toString().trim())) {
//            showToast("请填写检查内容");
//            return false;
//        }
        return true;
    }

    private void submit() {
        detailsBean = new WorkAddCheckBean.WorkInspectDetailsBean();
        detailsBean.setTitle(etTitle.getText().toString().trim());
        detailsBean.setRegion(etPosition.getText().toString().trim());
        detailsBean.setBusinessThreeCode(Config.get().getBusinessCodeByName(tvOneName.getText().toString().trim(), 3));
        // TODO: 2017/12/18 业务类别，设备名称
//        detailsBean.setBusinessOne(tvOneName.getText().toString().trim());
//        detailsBean.setBusinessTwo(tvTwoName.getText().toString().trim());
//        detailsBean.setBusinessThree(tvThreeName.getText().toString().trim());
        detailsBean.setInfo(etInputCheckContent.getText().toString().trim());

        String ursStr = PhotoUtils.getPhotoUrl(mPhotosSnpl, uploadMap, true);
        detailsBean.setPictures(ursStr);

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
