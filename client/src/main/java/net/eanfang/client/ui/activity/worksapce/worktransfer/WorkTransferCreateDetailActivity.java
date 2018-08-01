package net.eanfang.client.ui.activity.worksapce.worktransfer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.client.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Guanluocang
 * @date on 2018/7/27  15:45
 * @decision 创建交接班详情
 */
public class WorkTransferCreateDetailActivity extends BaseActivity {


    private String mSwitch = "";
    private static final int REQUEST_CODE_CHOOSE_PHOTO_1 = 1;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_1 = 101;
    private int RESQULT_CODE = 0;

    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.et_input_content)
    EditText etInputContent;
    @BindView(R.id.rl_select_status)
    RelativeLayout rlSelectStatus;
    @BindView(R.id.et_input_describe)
    EditText etInputDescribe;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snplMomentAddPhotos;
    @BindView(R.id.et_input_note)
    EditText etInputNote;
    @BindView(R.id.rl_confirm)
    RelativeLayout rlConfirm;

    private HashMap<String, String> uploadMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_transfer_create_detail);
        ButterKnife.bind(this);
        initView();
        initPhoto();
    }

    private void initPhoto() {
        snplMomentAddPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
    }

    private void initView() {
        setLeftBack();
        setTitle("交接班");
        mSwitch = getIntent().getStringExtra("switch");
        if ("ADD_HAND".equals(mSwitch)) {
            RESQULT_CODE = 1000;
        } else if ("ADD_FINISH_WORK".equals(mSwitch)) {
            RESQULT_CODE = 2000;
        } else if ("ADD_UNFINISH_THINGS".equals(mSwitch)) {
            RESQULT_CODE = 3000;
        } else if ("ADD_FLOW_THINGS".equals(mSwitch)) {
            RESQULT_CODE = 4000;
        } else if ("ADD_ATTENTION_THINGS".equals(mSwitch)) {
            RESQULT_CODE = 5000;
        }
    }

    @OnClick({R.id.rl_select_status, R.id.rl_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 选择状态
            case R.id.rl_select_status:
                break;
            // 确定提交
            case R.id.rl_confirm:
                doSubmit();
                break;
        }
    }

    /**
     * 提交
     */
    private void doSubmit() {
        // 交接内容
        String mContent = etInputContent.getText().toString().trim();
        // 当前状态
        String mStatus = tvStatus.getText().toString().trim();
        // 详细说明
        String mDescribe = etInputDescribe.getText().toString().trim();
        // 备注信息
        String mNote = etInputNote.getText().toString().trim();
        if (StringUtils.isEmpty(mContent)) {
            showToast("请输入交接内容");
            return;
        }
        if (StringUtils.isEmpty(mStatus)) {
            showToast("请输入当前状态");
            return;
        }
        if (StringUtils.isEmpty(mDescribe)) {
            showToast("请输入详细说明");
            return;
        }
        if (StringUtils.isEmpty(mNote)) {
            showToast("请输入备注信息");
            return;
        }

        if (uploadMap.size() != 0) {
//            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
//                @Override
//                public void onOssSuccess() {
//                    runOnUiThread(() -> {
//                        //TODO 进行网络请求
//                    });
//                }
//            });
        }
        Intent intent = new Intent();
        intent.putExtra("whichOne", "111");
        setResult(RESQULT_CODE, intent);
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
                snplMomentAddPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_1:
                snplMomentAddPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
        }
        String presentationPic = PhotoUtils.getPhotoUrl(snplMomentAddPhotos, uploadMap, true);
        // 添加照片
//        bughandleConfirmEntity.setFrontPictures(presentationPic);
    }

    //提交完工
    private void submit() {

    }
}
