package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.OrganizationBean;
import com.eanfang.model.SectionBean;
import com.eanfang.model.TemplateBean;
import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreatSectionActivity extends BaseClientActivity {

    @BindView(R.id.tv_section_name)
    TextView tvSectionName;
    @BindView(R.id.et_new_name)
    EditText etNewName;
    @BindView(R.id.tv_created)
    TextView tvCreated;

    private String parentOrgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_section);
        ButterKnife.bind(this);
        setTitle("创建部门");
        setLeftBack();
    }

    @OnClick({R.id.tv_created, R.id.ll_section})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_created:

                if (TextUtils.isEmpty(etNewName.getText().toString().trim())) {
                    ToastUtil.get().showToast(CreatSectionActivity.this, "请输入部门名称");
                    return;
                }

                JSONObject object = new JSONObject();
                try {
                    object.put("parentOrgId", parentOrgId);
                    object.put("orgName", etNewName.getText().toString().trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                EanfangHttp.post(NewApiService.CREAT_SECTION)
                        .upJson(object)
                        .execute(new EanfangCallback<JSONObject>(CreatSectionActivity.this, true, JSONObject.class, (bean) -> {

                            ToastUtil.get().showToast(CreatSectionActivity.this, "创建成功");

                            finishSelf();

                        }));
                break;
            case R.id.ll_section:

                Intent intent = new Intent("com.eanfang.intent.action.ORG1");
                intent.putExtra("isRadio", "isRadio");//是否是单选
                startActivity(intent);

                break;
        }

    }

    @Subscribe
    public void onEvent(SectionBean sectionBean) {

        if (sectionBean != null) {
            tvCreated.setVisibility(View.VISIBLE);
            tvSectionName.setText(sectionBean.getOrgName());
            parentOrgId = sectionBean.getParentOrgId();
        }
    }
}