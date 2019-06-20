package net.eanfang.client.ui.activity.worksapce.online;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;

import com.eanfang.util.PhotoUtils;
import com.eanfang.util.contentsafe.ContentDefaultAuditing;
import com.eanfang.util.contentsafe.ContentSecurityAuditUtil;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFreeAskActivity extends BaseClientActivity implements View.OnClickListener {

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
                    ContentSecurityAuditUtil.getInstance().toAuditing(answerContent1, new ContentDefaultAuditing(MyFreeAskActivity.this) {
                        @Override
                        public void auditingSuccess() {
                            urls = PhotoUtils.getPhotoUrl("online/",snplPhotos, uploadMap, true);
                            getData();
                        }
                    });
                } else {
                    Toast.makeText(MyFreeAskActivity.this,"內容不可为空",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    //网络请求--我来回答----多图上传
    private void getData() {

        if (uploadMap.size() != 0) {
            SDKManager.ossKit(this).asyncPutImages(uploadMap,(isSuccess) -> {
                runOnUiThread(() -> {
                    Intent intent = new Intent();
                    intent.putExtra("resultTwo", urls);
                    setResult( 101, intent);
                    finish();
                });
            });;
        }else {
            Intent intentk = new Intent();
            intentk.putExtra("resultTwo", urls);
            setResult(101, intentk);
            finish();
        }

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
