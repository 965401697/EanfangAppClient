package net.eanfang.worker.ui.activity.worksapce.online;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.QueryEntry;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.CooperationEntity;
import com.yaf.base.entity.RepairFailureEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFreeAskActivity extends BaseWorkerActivity implements View.OnClickListener {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.answer_content)
    EditText answerContent;
    @BindView(R.id.snpl_photos)
    BGASortableNinePhotoLayout snplPhotos;
    @BindView(R.id.tv_answer)
    TextView tvAnswer;
    private long questionId;
    private String questionUserId,questionCompanyId,questionTopCompanyId;
    private String answerContent1;
    private Map<String, String> uploadMap = new HashMap<>();
    private String urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_free_ask);
        ButterKnife.bind(this);
        tvTitle.setText("我来回答");
        ivLeft.setOnClickListener(this);
        snplPhotos.setDelegate(new BGASortableDelegate(this));
        Intent intent = getIntent();
        questionId = intent.getLongExtra("questionId", 0);
        questionUserId = intent.getStringExtra("questionUserId");
        questionCompanyId = intent.getStringExtra("questionCompanyId");
        questionTopCompanyId = intent.getStringExtra("questionTopCompanyId");
        //提交
        tvAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerContent1 = MyFreeAskActivity.this.answerContent.getText().toString();
                if (!TextUtils.isEmpty(answerContent1)){
                    urls = PhotoUtils.getPhotoUrl("/expert/questions",snplPhotos, uploadMap, true);
                    getData();
                }else {
                    Toast.makeText(MyFreeAskActivity.this,"內容不可为空",Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (uploadMap.size() != 0) {
            OSSUtils.initOSS(this).asyncPutImages(uploadMap, new OSSCallBack(this, true) {
                @Override
                public void onOssSuccess() {
                    runOnUiThread(() -> {
                    });
                }
            });
        }
    }

    //网络请求--我来回答----多图上传
    private void getData() {
        EanfangHttp.post(NewApiService.Answer_Add)
                .params("questionId", questionId)
                .params("questionUserId", questionUserId)
                .params("questionCompanyId", questionCompanyId)
                .params("answerPics", urls)
                .params("questionTopCompanyId", questionTopCompanyId)
                .params("answerContent", answerContent1)
                .execute(new EanfangCallback<AnswerAddBean>(this, true, AnswerAddBean.class) {
                    @Override
                    public void onSuccess(AnswerAddBean bean) {
                        Toast.makeText(MyFreeAskActivity.this,"成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onNoData(String message) {
                    }

                    @Override
                    public void onCommitAgain() {
                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == BGASortableDelegate.REQUEST_CODE_CHOOSE_PHOTO) {
            snplPhotos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
        } else if (requestCode == BGASortableDelegate.REQUEST_CODE_PHOTO_PREVIEW) {
            snplPhotos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }
    }


    @Override
    public void onClick(View v) {
        finish();
    }
}
