package net.eanfang.client.ui.activity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.base.widget.customview.SuperTextView;
import com.eanfang.base.widget.customview.TwoTextPicText;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.TechnicianDetailsBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.repair.RepairTypeActivity;
import net.eanfang.client.ui.adapter.TechnicianDetailsaAdapter;
import net.eanfang.client.ui.adapter.TechnicianDetailsdAdapter;
import net.eanfang.client.ui.adapter.TechnicianDetailsfAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author wq
 * @description 技师详情
 */
public class TechnicianDetailsActivity extends BaseActivity {

    @BindView(R.id.gs_log_sdv)
    CircleImageView gsLogSdv;
    @BindView(R.id.gs_xq_iv)
    ImageView gsXqIv;
    @BindView(R.id.gs_name_tv)
    TextView gsNameTv;
    @BindView(R.id.xyz_s_tv)
    TextView xyzSTv;
    @BindView(R.id.xyz_tv)
    TextView xyzTv;
    @BindView(R.id.bfz_tv)
    TextView bfzTv;
    @BindView(R.id.bq_stv)
    SuperTextView bqStv;
    @BindView(R.id.rv_a)
    RecyclerView rvA;
    @BindView(R.id.rv_b)
    RecyclerView rvB;
    @BindView(R.id.rv_c)
    RecyclerView rvC;
    @BindView(R.id.yx_a_stv)
    SuperTextView yxAStv;
    @BindView(R.id.yx_b_stv)
    SuperTextView yxBStv;
    @BindView(R.id.yx_c_stv)
    SuperTextView yxCStv;
    @BindView(R.id.yx_d_stv)
    SuperTextView yxDStv;
    @BindView(R.id.yx_e_stv)
    SuperTextView yxEStv;
    @BindView(R.id.rv_d)
    RecyclerView rvD;
    @BindView(R.id.rv_e)
    RecyclerView rvE;
    @BindView(R.id.rv_f)
    RecyclerView rvF;
    @BindView(R.id.sj_dd_iv)
    TwoTextPicText sjDdIv;
    @BindView(R.id.wx_dd_iv)
    TwoTextPicText wxDdIv;
    @BindView(R.id.sg_dd_iv)
    TwoTextPicText sgDdIv;
    @BindView(R.id.pl_iv)
    TwoTextPicText plIv;
    @BindView(R.id.bx_tv)
    TextView bxTv;
    private TechnicianDetailsBean technicianDetailsBean = new TechnicianDetailsBean();
    private TechnicianDetailsaAdapter adapterA;
    private TechnicianDetailsaAdapter adapterB;
    private TechnicianDetailsaAdapter adapterC;
    private TechnicianDetailsdAdapter adapterD;
    private TechnicianDetailsaAdapter adapterE;
    private TechnicianDetailsfAdapter adapterF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_details);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("技师详情");
        initData();
    }

    private void initData() {
        EanfangHttp.get(RepairApi.CK_JS_DETAIL).params("userId", "979993412327751682").params("accId", "979993411866378241").execute(new EanfangCallback<TechnicianDetailsBean>(this, true, TechnicianDetailsBean.class, (bean) -> {
            technicianDetailsBean = bean;
            setData();
        }));
    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        GlideUtil.intoImageView(this,BuildConfig.OSS_SERVER + Uri.parse(technicianDetailsBean.getAccount().getAvatar()),gsLogSdv);
        gsNameTv.setText(technicianDetailsBean.getAccount().getRealName());
        if (technicianDetailsBean.getEvaluate().getTrainStatus() == 1) {
            bqStv.setVisibility(View.VISIBLE);
        } else {
            bqStv.setVisibility(View.GONE);
        }
        xyzSTv.setText(technicianDetailsBean.getEvaluate().getPublicPraise() + "");
        bfzTv.setText("好评率：" + technicianDetailsBean.getEvaluate().getGoodRate() + "%");
        sjDdIv.setTestBstring(technicianDetailsBean.getEvaluate().getDesignNum() + "");
        wxDdIv.setTestBstring(technicianDetailsBean.getEvaluate().getRepairCount() + "");
        sgDdIv.setTestBstring(technicianDetailsBean.getEvaluate().getInstallNum() + "");
        plIv.setTestBstring(technicianDetailsBean.getEvaluate().getEvaluateNum() + "");
        setXj(yxAStv, technicianDetailsBean.getEvaluate().getItem1());
        setXj(yxBStv, technicianDetailsBean.getEvaluate().getItem2());
        setXj(yxCStv, technicianDetailsBean.getEvaluate().getItem3());
        setXj(yxDStv, technicianDetailsBean.getEvaluate().getItem4());
        setXj(yxEStv, technicianDetailsBean.getEvaluate().getItem5());
        initRecyclerViewAndA(technicianDetailsBean.getSysList());
        initRecyclerViewAndB(technicianDetailsBean.getBizList());
        initRecyclerViewAndC(technicianDetailsBean.getAreaList());
        initRecyclerViewAndD(technicianDetailsBean.getQualificationList());
        initRecyclerViewAndE(technicianDetailsBean.getTagList());
        initRecyclerViewAndF(technicianDetailsBean.getCases());
    }

    private void initRecyclerViewAndA(List<String> stringList) {
        rvA.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        rvA.setNestedScrollingEnabled(false);
        adapterA = new TechnicianDetailsaAdapter(true);
        adapterA.bindToRecyclerView(rvA);
        adapterA.setNewData(stringList);
    }

    private void initRecyclerViewAndB(List<String> bizList) {
        rvB.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        rvB.setNestedScrollingEnabled(false);
        adapterB = new TechnicianDetailsaAdapter(true);
        adapterB.bindToRecyclerView(rvB);
        adapterB.setNewData(bizList);
    }

    private void initRecyclerViewAndC(List<String> areaList) {
        rvC.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        rvC.setNestedScrollingEnabled(false);
        adapterC = new TechnicianDetailsaAdapter(true);
        adapterC.bindToRecyclerView(rvC);
        adapterC.setNewData(areaList);
    }

    private void initRecyclerViewAndD(List<TechnicianDetailsBean.QualificationListBean> qualificationList) {
        rvD.setLayoutManager(new LinearLayoutManager(this));
        rvD.setNestedScrollingEnabled(false);
        adapterD = new TechnicianDetailsdAdapter(true);
        adapterD.bindToRecyclerView(rvD);
        adapterD.setNewData(qualificationList);
    }

    private void initRecyclerViewAndE(List<String> tagList) {
        rvE.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        rvE.setNestedScrollingEnabled(false);
        adapterE = new TechnicianDetailsaAdapter(true);
        adapterE.bindToRecyclerView(rvE);
        adapterE.setNewData(tagList);
    }

    private void initRecyclerViewAndF(List<TechnicianDetailsBean.CasesBean> cases) {
        rvF.setLayoutManager(new LinearLayoutManager(this));
        rvF.setNestedScrollingEnabled(false);
        adapterF = new TechnicianDetailsfAdapter(true);
        adapterF.bindToRecyclerView(rvF);
        adapterF.setNewData(cases);
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

    @OnClick(R.id.bx_tv)
    public void onClick() {
        JumpItent.jump(this, RepairTypeActivity.class);
        finish();
    }
}
