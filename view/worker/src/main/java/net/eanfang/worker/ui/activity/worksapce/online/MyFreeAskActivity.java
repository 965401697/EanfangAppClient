package net.eanfang.worker.ui.activity.worksapce.online;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.picture.picture.PictureRecycleView;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.contentsafe.ContentDefaultAuditing;
import com.eanfang.util.contentsafe.ContentSecurityAuditUtil;
import com.luck.picture.lib.entity.LocalMedia;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkeActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFreeAskActivity extends BaseWorkeActivity implements View.OnClickListener {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.answer_content)
    EditText answerContent;
    @BindView(R.id.tv_answer)
    TextView tvAnswer;
    @BindView(R.id.picture_recycler)
    PictureRecycleView pictureRecycler;
    private long questionId;
    private String questionUserId, questionCompanyId, questionTopCompanyId;
    private String answerContent1;
    private Map<String, String> uploadMap = new HashMap<>();
    private String urls;
    private List<LocalMedia> selectList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_free_ask);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        tvTitle.setText("我来回答");
        ivLeft.setOnClickListener(this);
        pictureRecycler.addImagev(listener);
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
                if (!TextUtils.isEmpty(answerContent1)) {
                    ContentSecurityAuditUtil.getInstance().toAuditing(answerContent1, new ContentDefaultAuditing(MyFreeAskActivity.this) {
                        @Override
                        public void auditingSuccess() {
                            urls = PhotoUtils.getPhotoUrl("online/", selectList, uploadMap, true);
                            getData();
                        }
                    });
                } else {
                    Toast.makeText(MyFreeAskActivity.this, "內容不可为空", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    PictureRecycleView.ImageListener listener = list -> selectList = list;
    //网络请求--我来回答----多图上传
    private void getData() {

        if (uploadMap.size() != 0) {
            SDKManager.ossKit(this).asyncPutImages(uploadMap, (isSuccess) -> {
                runOnUiThread(() -> {
                    Intent intent = new Intent();
                    intent.putExtra("resultTwo", urls);
                    setResult(101, intent);
                    finish();
                });
            });
        } else {
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
                        Toast.makeText(MyFreeAskActivity.this, "成功", Toast.LENGTH_SHORT).show();
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
    public void onClick(View v) {
        finish();
    }
}
