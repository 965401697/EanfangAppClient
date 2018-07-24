package net.eanfang.worker.ui.activity.worksapce.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.eanfang.BuildConfig;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MaintenanceAddCheckResultShowActivity extends BaseWorkerActivity {

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

    private ShopMaintenanceExamResultEntity examResultEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_check_result_show);
        ButterKnife.bind(this);
        setTitle("检查结果");
        setLeftBack();

//        snplPhoto.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
        snplPhoto.setEditable(false);
        snplPhoto.setPlusEnable(false);
        examResultEntity = (ShopMaintenanceExamResultEntity) getIntent().getSerializableExtra("bean");
        initViews();
    }

    private void initViews() {
        if (examResultEntity != null) {
            etQuestion.setText(examResultEntity.getExistQuestions());
            etHandle.setText(examResultEntity.getDisposeCourse());
            etNotice.setText(examResultEntity.getInfo());
            tvConclusion.setText(GetConstDataUtils.getMaintainConditionList().get(examResultEntity.getStatus()));
            initImgUrlList();
            snplPhoto.setData(picList);
        }
    }

    /**
     * 初始化存储图片用的List集合
     */
    private void initImgUrlList() {
        //修改小bug 图片读取问题
        if (!StringUtils.isEmpty(examResultEntity.getPicture())) {
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
