package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkCheckInfoBean;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LookDealwithCheckInfoDetailActivity extends BaseWorkerActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
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
    private WorkCheckInfoBean.WorkInspectDetailsBean.WorkInspectDetailDisposesBean bean;
    private WorkCheckInfoBean workCheckInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_dealwith_check_info_detail);
        ButterKnife.bind(this);
        setTitle("处理结果");
        setLeftBack();
        bean = (WorkCheckInfoBean.WorkInspectDetailsBean.WorkInspectDetailDisposesBean) getIntent().getSerializableExtra("bean");
        workCheckInfoBean = (WorkCheckInfoBean) getIntent().getSerializableExtra("workCheckInfoBean");
        initView();
    }

    private void initView() {
        btnAdopt.setOnClickListener(v -> dealWith(1));
        btnReject.setOnClickListener(v -> dealWith(2));

        Long uid = EanfangApplication.getApplication().getUserId();
        if (uid.equals(workCheckInfoBean.getAssigneeUserId()) && (EanfangConst.WORK_INSPECT_STATUS_REVIEW == workCheckInfoBean.getStatus())) {
//            if (EanfangConst.WORK_INSPECT_STATUS_FAIL == bean.getStatus() ||EanfangConst.WORK_INSPECT_STATUS_FINISH == bean.getStatus() ||EanfangConst.WORK_INSPECT_STATUS_REVIEW == bean.getStatus()) {
//                llDealWith.setVisibility(View.GONE);
//            }
            llDealWith.setVisibility(View.VISIBLE);
        } else {
//            if (EanfangConst.WORK_INSPECT_STATUS_FAIL == bean.getStatus() || EanfangConst.WORK_INSPECT_STATUS_FINISH == bean.getStatus()) {
//                llDealWith.setVisibility(View.GONE);
//            } else {
//                llDealWith.setVisibility(View.VISIBLE);
//            }
            llDealWith.setVisibility(View.GONE);
        }


        etTitle.setText(workCheckInfoBean.getTitle());
        etInputCheckContent.setText(bean.getDisposeInfo());
        etRemark.setText(bean.getRemarkInfo());
//
        if (!StringUtils.isEmpty(bean.getPictures())) {
            String[] urls = bean.getPictures().split(",");

            if (urls.length >= 1) {
                ivPic1.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[0]));
                ivPic1.setVisibility(View.VISIBLE);
            } else {
                ivPic1.setVisibility(View.GONE);
            }

            if (urls.length >= 2) {
                ivPic2.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[1]));
                ivPic2.setVisibility(View.VISIBLE);
            } else {
                ivPic2.setVisibility(View.GONE);
            }
            if (urls.length >= 3) {
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
                .execute(new EanfangCallback(this, true, JSONObject.class, (bean) -> {
//                    new WorkCheckInfoView(this, true, workCheckInfoBean.getId(), false).show();
//                    EventBus.getDefault().post(new BaseEvent());

                    Intent intent = new Intent();
                    intent.putExtra("type", status);
                    setResult(RESULT_OK, intent);
                    finishSelf();
                }));
    }
}
