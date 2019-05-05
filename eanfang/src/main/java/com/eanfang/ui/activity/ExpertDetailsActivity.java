package com.eanfang.ui.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.customview.SuperTextView;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.ExpertDetailsBean;
import com.eanfang.ui.adapter.ExpertDetailsAdapter;
import com.eanfang.ui.base.BaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author wq
 * @description 安防公司详情
 */

public class ExpertDetailsActivity extends BaseActivity {

    @BindView(R2.id.gs_log_sdv)
    SimpleDraweeView gsLogSdv;
    @BindView(R2.id.gs_xq_iv)
    ImageView gsXqIv;
    @BindView(R2.id.gs_name_tv)
    TextView gsNameTv;
    @BindView(R2.id.rz_tv)
    SuperTextView rzTv;
    @BindView(R2.id.xyz_s_tv)
    TextView xyzSTv;
    @BindView(R2.id.xyz_tv)
    TextView xyzTv;
    @BindView(R2.id.bfz_tv)
    TextView bfzTv;
    @BindView(R2.id.bq_stv)
    SuperTextView bqStv;
    @BindView(R2.id.sc_pp_tv)
    TextView scPpTv;
    @BindView(R2.id.rs_tv)
    TextView rsTv;
    @BindView(R2.id.sc_sb_lx_tv)
    TextView scSbLxTv;
    @BindView(R2.id.yx_a_stv)
    SuperTextView yxAStv;
    @BindView(R2.id.yx_b_stv)
    SuperTextView yxBStv;
    @BindView(R2.id.yx_c_stv)
    SuperTextView yxCStv;
    @BindView(R2.id.yx_d_stv)
    SuperTextView yxDStv;
    @BindView(R2.id.yx_e_stv)
    SuperTextView yxEStv;
    @BindView(R2.id.pj_rv)
    RecyclerView pjRv;
    @BindView(R2.id.gz_tv)
    TextView gzTv;
    @BindView(R2.id.tw_tv)
    TextView twTv;
    private ExpertDetailsBean expertDetailsBean = new ExpertDetailsBean();
    ExpertDetailsAdapter mAdapter;
    private boolean isGz = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_details);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("专家详情");
        setLeftBack();
        pjRv.setLayoutManager(new LinearLayoutManager(this));
        pjRv.setNestedScrollingEnabled(false);
        mAdapter = new ExpertDetailsAdapter(R.layout.item_expert_details);
        mAdapter.bindToRecyclerView(pjRv);
        isGz = expertDetailsBean.getFollow() != 1;
        gzTv.setText(isGz ? "取消关注" : "+ 关注");
        gzTv.setOnClickListener(view -> setGuanZhu(isGz));
        twTv.setOnClickListener(view -> {
            showToast("专家在线");
            //import net.eanfang.worker.ui.activity.worksapce.online.ExpertOnlineActivity;
            //startActivity(new Intent(this, ExpertOnlineActivity.class));
        });
        initData();
    }

    private void setGuanZhu(boolean isgz) {
        gzTv.setEnabled(false);
        EanfangHttp.post(UserApi.DJ_GZ).params("userId", "1082922712729456642").params("followStatus", isgz ? "1" : "0").execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
            gzTv.setEnabled(true);
            isGz=!isGz;
            gzTv.setText(isGz ? "取消关注" : "+ 关注");
        }));
    }


    private void initData() {
        EanfangHttp.post(UserApi.CK_ZJ_DLS).params("userId", "1082922712729456642").execute(new EanfangCallback<ExpertDetailsBean>(this, true, ExpertDetailsBean.class, (bean) -> {
            expertDetailsBean = bean;
            setData();
        }));
    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        gsLogSdv.setImageURI(com.eanfang.BuildConfig.OSS_SERVER + Uri.parse(expertDetailsBean.getAccount().getAvatar()));
        gsNameTv.setText(expertDetailsBean.getAccount().getRealName());
        if (expertDetailsBean.getExpert().getStatus() == 2) {
            rzTv.setVisibility(View.VISIBLE);
        } else {
            rzTv.setVisibility(View.GONE);
        }
        if (expertDetailsBean.getExpert().getTrainStatus() == 1) {
            bqStv.setVisibility(View.VISIBLE);
        } else {
            bqStv.setVisibility(View.GONE);
        }
        xyzSTv.setText(expertDetailsBean.getExpert().getGoodRate() + "");
        bfzTv.setText("回答问题：" + expertDetailsBean.getExpert().getAnswerCount());
        scPpTv.setText("擅长品牌：" + expertDetailsBean.getExpert().getResponsibleBrand());
        scSbLxTv.setText("认证板块：" + expertDetailsBean.getExpert().getSystemType());
        rsTv.setText("（" + expertDetailsBean.getEvaluateCount() + "人）");
        setXj(yxAStv, expertDetailsBean.getExpert().getItem1());
        setXj(yxBStv, expertDetailsBean.getExpert().getItem2());
        setXj(yxCStv, expertDetailsBean.getExpert().getItem3());
        setXj(yxDStv, expertDetailsBean.getExpert().getItem4());
        setXj(yxEStv, expertDetailsBean.getExpert().getItem5());
        mAdapter.setNewData(expertDetailsBean.getEvaluate());
    }

    private void setXj(SuperTextView xAStv, int item1) {
        switch (item1) {
            case 1:
                xAStv.setRightString("★");
                break;
            case 2:
                xAStv.setRightString("★★");
                break;
            case 3:
                xAStv.setRightString("★★★");
                break;
            case 4:
                xAStv.setRightString("★★★★");
                break;
            case 5:
                xAStv.setRightString("★★★★★");
                break;
            default:
        }
    }


}
