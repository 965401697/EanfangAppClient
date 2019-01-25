package net.eanfang.worker.ui.activity.worksapce.online;

import android.os.Bundle;
import android.widget.EditText;

import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我来回答
 */
public class IAnswerActivity extends BaseWorkerActivity {

    @BindView(R.id.snpl_photos)
    BGASortableNinePhotoLayout snplPhotos;
    @BindView(R.id.et_answer)
    EditText etAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ianswer);
        ButterKnife.bind(this);
        setTitle("我来回答");
        setLeftBack();
    }
}
