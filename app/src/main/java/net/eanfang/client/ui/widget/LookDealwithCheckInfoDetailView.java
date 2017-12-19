package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.base.BaseDialog;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.config.EanfangConst;
import net.eanfang.client.network.apiservice.NewApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.model.WorkCheckInfoBean;
import net.eanfang.client.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  17:36
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class LookDealwithCheckInfoDetailView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private Activity mContext;
    private WorkCheckInfoBean.WorkInspectDetailsBean.WorkInspectDetailDisposesBean bean;
    private WorkCheckInfoBean workCheckInfoBean;
    @BindView(R.id.et_title)
    TextView etTitle;
    @BindView(R.id.et_input_check_content)
    TextView etInputCheckContent;
    @BindView(R.id.et_remark)
    TextView etRemark;
    @BindView(R.id.iv_pic1)
    SimpleDraweeView ivPic1;
    @BindView(R.id.iv_pic2)
    SimpleDraweeView ivPic2;
    @BindView(R.id.iv_pic3)
    SimpleDraweeView ivPic3;
    @BindView(R.id.btn_reject)
    Button btnReject;
    @BindView(R.id.btn_adopt)
    Button btnAdopt;
    @BindView(R.id.ll_deal_with)
    LinearLayout llDealWith;

    public LookDealwithCheckInfoDetailView(Activity context, boolean isfull, WorkCheckInfoBean.WorkInspectDetailsBean.WorkInspectDetailDisposesBean bean,
                                           WorkCheckInfoBean workCheckInfoBean) {
        super(context, isfull);
        this.mContext = context;
        this.bean = bean;
        this.workCheckInfoBean = workCheckInfoBean;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_look_detail_check_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("处理结果");

        btnAdopt.setOnClickListener(v -> dealWith(1));
        btnReject.setOnClickListener(v -> dealWith(2));

        Long uid = EanfangApplication.getApplication().getUserId();
        if (uid.equals(workCheckInfoBean.getAssigneeUserId())) {
            if (EanfangConst.WORK_INSPECT_STATUS_FAIL == bean.getStatus() ||
                    EanfangConst.WORK_INSPECT_STATUS_FINISH == bean.getStatus() ||
                    EanfangConst.WORK_INSPECT_STATUS_REVIEW == bean.getStatus()) {
                llDealWith.setVisibility(View.GONE);
            }

        } else {
            if (EanfangConst.WORK_INSPECT_STATUS_FAIL == bean.getStatus() ||
                    EanfangConst.WORK_INSPECT_STATUS_FINISH == bean.getStatus()) {
                llDealWith.setVisibility(View.GONE);
            } else {
                llDealWith.setVisibility(View.VISIBLE);
            }
        }


//        etTitle.setText(bean.getInspectDetailTitle());
        etInputCheckContent.setText(bean.getDisposeInfo());
        etRemark.setText(bean.getRemarkInfo());
//
        if (!StringUtils.isEmpty(bean.getPictures())) {
            String[] urls = bean.getPictures().split(",");

            if (!TextUtils.isEmpty(urls[0])) {
                ivPic1.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[0]));
                ivPic1.setVisibility(View.VISIBLE);
            } else {
                ivPic1.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(urls[1])) {
                ivPic2.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[1]));
                ivPic2.setVisibility(View.VISIBLE);
            } else {
                ivPic2.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(urls[2])) {
                ivPic3.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[2]));
                ivPic3.setVisibility(View.VISIBLE);
            } else {
                ivPic3.setVisibility(View.GONE);
            }
        }
    }


    private void dealWith(int status) {
        EanfangHttp.post(NewApiService.GET_WORK_CHCEK_ADUIT)
                .params("status", status)
                .params("id", bean.getId())
                .execute(new EanfangCallback(mContext, true, JSONObject.class, (bean) -> {
                    new WorkCheckInfoView(mContext, true, workCheckInfoBean.getId()).show();
                }));
    }
}
