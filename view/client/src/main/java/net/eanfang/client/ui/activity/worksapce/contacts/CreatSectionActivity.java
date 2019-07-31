package net.eanfang.client.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.OrganizationBean;
import com.eanfang.biz.model.bean.SectionBean;
import com.eanfang.ui.activity.SelectOrganizationActivity;
import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreatSectionActivity extends BaseClientActivity {

    @BindView(R.id.tv_section_name)
    TextView tvSectionName;
    @BindView(R.id.et_new_name)
    EditText etNewName;

    private String parentOrgId;
    private String topCompanyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_creat_section);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("创建部门");
        setLeftBack();

    }

    @OnClick(R.id.ll_section)
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.ll_section:

                Intent intent = new Intent(this, SelectOrganizationActivity.class);
                intent.putExtra("isSection", "isRadio");//是否是选择部门单选
                startActivity(intent);

                break;
        }

    }


    private void created() {
        JSONObject object = new JSONObject();

//        if (TextUtils.isEmpty(tvSectionName.getText().toString().trim())) {
//            parentOrgId = String.valueOf(ClientApplication.get().getCompanyId());
//        }

        if (TextUtils.isEmpty(etNewName.getText().toString().trim())) {
            ToastUtil.get().showToast(CreatSectionActivity.this, "请输入部门名称");
            return;
        }
        try {
            object.put("topCompanyId", ClientApplication.get().getTopCompanyId());
            if (!TextUtils.isEmpty(tvSectionName.getText().toString().trim()) && TextUtils.isEmpty(topCompanyId)) {
                object.put("parentOrgId", parentOrgId);
            } else {
                object.put("parentOrgId", ClientApplication.get().getCompanyId());
            }
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
    }

    @Subscribe
    public void onEvent(Object o) {

        if (o instanceof OrganizationBean) {
            OrganizationBean organizationBean = (OrganizationBean) o;
            tvSectionName.setText(organizationBean.getOrgName());
            topCompanyId = organizationBean.getTopCompanyId();
            parentOrgId = organizationBean.getOrgId();

        } else if (o instanceof SectionBean) {
            SectionBean sectionBean = (SectionBean) o;
            tvSectionName.setText(sectionBean.getOrgName());
            parentOrgId = sectionBean.getOrgId();
        } else if (o instanceof SectionBean.ChildrenBean) {
            SectionBean.ChildrenBean childrenBean = (SectionBean.ChildrenBean) o;
            tvSectionName.setText(childrenBean.getOrgName());
            parentOrgId = childrenBean.getOrgId();
        }
    }

    @OnClick(R.id.tv_created)
    public void onViewClicked() {
        created();
    }
}
