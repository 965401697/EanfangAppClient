package net.eanfang.client.ui.activity.im;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 更新群名字
 */
public class GroupUpdataNameActivity extends BaseClientActivity {

    @BindView(R.id.et_name)
    EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_updata_name);
        ButterKnife.bind(this);
        setTitle("修改群组名称");
        setRightTitle("确定");
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
