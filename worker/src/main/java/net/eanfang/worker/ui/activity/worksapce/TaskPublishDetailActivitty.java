package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.model.MineTaskListBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskPublishDetailActivitty extends BaseWorkerActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_task_company)
    TextView etTaskCompany;
    @BindView(R.id.et_task_uname)
    TextView etTaskUname;
    @BindView(R.id.et_task_phone)
    TextView etTaskPhone;
    @BindView(R.id.tv_project_info)
    TextView tvProjectInfo;
    @BindView(R.id.et_project_company)
    TextView etProjectCompany;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_detail_address)
    TextView etDetailAddress;
    @BindView(R.id.tv_project_type)
    TextView tvProjectType;
    @BindView(R.id.tv_business_type)
    TextView tvBusinessType;
    @BindView(R.id.tv_project_time)
    TextView tvProjectTime;
    @BindView(R.id.tv_budget)
    TextView tvBudget;
    @BindView(R.id.tv_login_time)
    TextView tvLoginTime;
    @BindView(R.id.et_desc)
    TextView etDesc;
    @BindView(R.id.iv_pic1)
    SimpleDraweeView ivPic1;
    @BindView(R.id.iv_pic2)
    SimpleDraweeView ivPic2;
    @BindView(R.id.iv_pic3)
    SimpleDraweeView ivPic3;
    @BindView(R.id.tv_ok)
    TextView tvOk;

    private MineTaskListBean.ListBean listBean;
    private boolean isTakePackpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_publish_detail_activitty);
        ButterKnife.bind(this);
        setTitle("项目详情");
        setLeftBack();
        listBean = (MineTaskListBean.ListBean) getIntent().getSerializableExtra("bean");
        isTakePackpage = getIntent().getBooleanExtra("isTakePackpage", false);
        initView();
    }

    private void initView() {
        if (isTakePackpage == false) {
            tvOk.setVisibility(View.GONE);
        } else {
            tvOk.setVisibility(View.VISIBLE);
        }
        tvOk.setOnClickListener(v -> tvOk());

        etTaskCompany.setText(listBean.getPublishCompanyName());
        etTaskUname.setText(listBean.getContacts());
        etTaskPhone.setText(listBean.getContactsPhone());
        etProjectCompany.setText(listBean.getProjectCompanyName());
        tvAddress.setText(Config.get().getAddressByCode(listBean.getZoneCode()));
        etDetailAddress.setText(listBean.getDetailPlace());
        tvProjectType.setText(GetConstDataUtils.getCooperationTypeList().get(listBean.getType()));
        tvBusinessType.setText(Config.get().getBusinessNameByCode(listBean.getBusinessOneCode(), 1));
        tvProjectTime.setText(GetConstDataUtils.getPredictList().get(listBean.getPredicttime()));
        tvBudget.setText(GetConstDataUtils.getBudgetList().get(listBean.getBudget()));
        tvLoginTime.setText(listBean.getToDoorTime());
        etDesc.setText(listBean.getDescription());
        if (!StringUtils.isEmpty(listBean.getPictures())) {
            String[] urls = listBean.getPictures().split(",");

            if (urls.length >= 1) {
                ivPic1.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[0]));
                ivPic1.setVisibility(View.VISIBLE);
            } else {
                ivPic1.setVisibility(View.GONE);
                ivPic2.setVisibility(View.GONE);
                ivPic3.setVisibility(View.GONE);
            }

            if (urls.length >= 2) {
                ivPic2.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[1]));
                ivPic2.setVisibility(View.VISIBLE);
            } else {
                ivPic2.setVisibility(View.GONE);
                ivPic3.setVisibility(View.GONE);
            }
            if (urls.length >= 3) {
                ivPic3.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[2]));
                ivPic3.setVisibility(View.VISIBLE);
            } else {
                ivPic3.setVisibility(View.GONE);
            }
        }

    }

    private void tvOk() {
        Intent intent = new Intent();
        intent.setClass(this, TakeApplyAddActivity.class);
        intent.putExtra("entTaskPublishId", listBean.getId());
        intent.putExtra("makeTime", listBean.getToDoorTime());
        startActivityForResult(intent, 101);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 101) {
            setResult(RESULT_OK);
            finishSelf();
        }
    }

}
