package net.eanfang.client.ui.activity.worksapce.repair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2019/4/24
 * @description 报修输入项目
 */


public class RepairAddProjectAcitivity extends BaseActivity {
    @BindView(R.id.et_input_projectName)
    EditText etInputProjectName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_repair_add_project);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("添加项目");
        setLeftBack();

        findViewById(R.id.tv_add).setOnClickListener(v -> {
            String projectName = etInputProjectName.getText().toString().trim();
            if (!StringUtils.isEmpty(projectName)) {
                Intent intent = new Intent();
                intent.putExtra("projectName", projectName);
                setResult(Activity.RESULT_OK, intent);
                finishSelf();
            } else {
                showToast("请输入");
            }
        });
    }

}
