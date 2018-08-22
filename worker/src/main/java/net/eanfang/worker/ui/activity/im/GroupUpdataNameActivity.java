package net.eanfang.worker.ui.activity.im;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.eanfang.util.ToastUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 更新群名字
 */
public class GroupUpdataNameActivity extends BaseWorkerActivity {

    @BindView(R.id.et_name)
    EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_updata_name);
        ButterKnife.bind(this);
        setTitle("修改群组名称");
        setRightTitle("确定");
        setLeftBack();
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(etName.getText().toString().trim())) {
                    Intent intent = new Intent();
                    intent.putExtra("updata_Name", etName.getText().toString().trim());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtil.get().showToast(GroupUpdataNameActivity.this, "名称不能为空");
                }

            }
        });
    }
}
