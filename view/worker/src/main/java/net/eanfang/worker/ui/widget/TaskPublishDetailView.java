package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.biz.model.bean.MineTaskListBean;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.TakeApplyAddActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.util.StrUtil;

/**
 * Created by MrHou
 *
 * @on 2018/1/18  10:59
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class TaskPublishDetailView extends BaseDialog {
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
    ImageView ivPic1;
    @BindView(R.id.iv_pic2)
    ImageView ivPic2;
    @BindView(R.id.iv_pic3)
    ImageView ivPic3;
    @BindView(R.id.tv_ok)
    TextView tvOk;

    private Activity mContext;
    private MineTaskListBean.ListBean listBean;
    private boolean isTakePackpage;


    public TaskPublishDetailView(Activity context, boolean isfull, MineTaskListBean.ListBean listBean, boolean isTakePackpage) {
        super(context, isfull);
        this.mContext = context;
        this.listBean = listBean;
        this.isTakePackpage = isTakePackpage;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_task_publish_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        if (isTakePackpage == false) {
            tvOk.setVisibility(View.GONE);
        } else {
            tvOk.setVisibility(View.VISIBLE);
        }
        tvOk.setOnClickListener(v -> tvOk());

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tvTitle.setText("项目详情");
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
        if (!StrUtil.isEmpty(listBean.getPictures())) {
            String[] urls = listBean.getPictures().split(",");

            if (urls.length >= 1) {
                GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + Uri.parse(urls[0]),ivPic1);
                ivPic1.setVisibility(View.VISIBLE);
            } else {
                ivPic1.setVisibility(View.GONE);
                ivPic2.setVisibility(View.GONE);
                ivPic3.setVisibility(View.GONE);
            }

            if (urls.length >= 2) {
                GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + Uri.parse(urls[1]),ivPic2);
                ivPic2.setVisibility(View.VISIBLE);
            } else {
                ivPic2.setVisibility(View.GONE);
                ivPic3.setVisibility(View.GONE);
            }
            if (urls.length >= 3) {
                GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + Uri.parse(urls[2]),ivPic3);
                ivPic3.setVisibility(View.VISIBLE);
            } else {
                ivPic3.setVisibility(View.GONE);
            }
        }

    }

    private void tvOk() {
        Intent intent = new Intent(mContext, TakeApplyAddActivity.class);
        intent.putExtra("entTaskPublishId", listBean.getId());
        intent.putExtra("makeTime", listBean.getToDoorTime());
        mContext.startActivity(intent);
    }

}
