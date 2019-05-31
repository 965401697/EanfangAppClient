package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.TaskPubApplyListDetailBean;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/18  15:22
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class TaskPubApplyListDetailView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_apply_company)
    TextView etApplyCompany;
    @BindView(R.id.et_contract)
    TextView etContract;
    @BindView(R.id.tv_contract_phone)
    TextView tvContractPhone;
    @BindView(R.id.et_contract_phone)
    TextView etContractPhone;
    @BindView(R.id.tv_doorTime)
    TextView tvDoorTime;
    @BindView(R.id.et_time_limit)
    TextView etTimeLimit;
    @BindView(R.id.tv_budget)
    TextView tvBudget;
    @BindView(R.id.et_need_desc)
    TextView etNeedDesc;
    @BindView(R.id.iv_pic1)
    ImageView ivPic1;
    @BindView(R.id.iv_pic2)
    ImageView ivPic2;
    @BindView(R.id.iv_pic3)
    ImageView ivPic3;
    private Activity mContex;
    private Long mid;

    public TaskPubApplyListDetailView(Activity context, boolean isfull, Long id) {
        super(context, isfull);
        this.mContex = context;
        this.mid = id;

    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_task_apply_list_detail);
        ButterKnife.bind(this);
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("申请详情");
        initData();
    }

    private void initData() {
        EanfangHttp.get(NewApiService.TASK_APPLY_LIST_DETAIL)
                .params("id", mid)
                .execute(new EanfangCallback<TaskPubApplyListDetailBean>(mContex, true, TaskPubApplyListDetailBean.class, (bean) -> {
                    setData(bean);
                }));
    }

    private void setData(TaskPubApplyListDetailBean bean) {
        etApplyCompany.setText(bean.getApplyCompanyName());
        etContract.setText(bean.getApplyContacts());
        etContractPhone.setText(bean.getApplyConstactsPhone());
        tvDoorTime.setText(bean.getToDoorTime());
        etTimeLimit.setText(bean.getPredictTime()+"");
        tvBudget.setText(bean.getProjectQuote() + "");
        etNeedDesc.setText(bean.getDescription());
        if (!StringUtils.isEmpty(bean.getPictures())) {
            String[] urls = bean.getPictures().split(",");

            if (urls.length>=1) {
                GlideUtil.intoImageView(mContex,BuildConfig.OSS_SERVER + Uri.parse(urls[0]),ivPic1);
                ivPic1.setVisibility(View.VISIBLE);
            } else {
                ivPic1.setVisibility(View.GONE);
            }

            if (urls.length>=2) {
                GlideUtil.intoImageView(mContex,BuildConfig.OSS_SERVER + Uri.parse(urls[1]),ivPic2);
                ivPic2.setVisibility(View.VISIBLE);
            } else {
                ivPic2.setVisibility(View.GONE);
            }
            if (urls.length>=3) {
                GlideUtil.intoImageView(mContex,BuildConfig.OSS_SERVER + Uri.parse(urls[2]),ivPic3);
                ivPic3.setVisibility(View.VISIBLE);
            } else {
                ivPic3.setVisibility(View.GONE);
            }
        }


    }
}
