package net.eanfang.worker.ui.activity.worksapce.online;

import android.os.Bundle;
import android.widget.EditText;

import com.eanfang.base.kit.SDKManager;
import com.eanfang.delegate.BGASortableDelegate;

import com.eanfang.util.PhotoUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import cn.hutool.core.util.StrUtil;

/**
 * 我来回答
 */
public class IAnswerActivity extends BaseWorkerActivity {

    @BindView(R.id.snpl_photos)
    BGASortableNinePhotoLayout snplPhotos;
    @BindView(R.id.et_answer)
    EditText etAnswer;

    private static final int REQUEST_CODE_CHOOSE_ANSWER = 1;
    private static final int REQUEST_CODE_PHOTO_ANSWER = 2;
    private Map<String, String> uploadMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ianswer);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("我来回答");
        setLeftBack();
        initViews();
    }

    private void initViews() {
        snplPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_ANSWER, REQUEST_CODE_PHOTO_ANSWER));
    }


    private void sub() {
        String pic = PhotoUtils.getPhotoUrl("", snplPhotos, uploadMap, false);
        if (StrUtil.isEmpty(pic)) {
            showToast("请添加名牌或者工牌照片");
        }
        SDKManager.ossKit(this).asyncPutImages(uploadMap,(isSuccess) -> {
//            runOnUiThread(() -> {

//                    EanfangHttp.post(url)
//                            .upJson(JSONObject.toJSONString(entity))
//                            .execute(new EanfangCallback<JSONObject>(IAnswerActivity.this, true, JSONObject.class, (bean) -> {
//                                setResult(RESULT_OK);
//                                finish();
//                            }));


//            });
        });
    }
}
