package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.BuildConfig;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.EanfangConst;
import com.eanfang.model.WorkCheckInfoBean;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.worktransfer.LookDealwithCheckInfoDetailActivity;
import net.eanfang.client.ui.adapter.AddCheckInfoDetailAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.fragment.WorkCheckListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LookWorkCheckInfoActivity extends BaseClientActivity {

    @BindView(R.id.ll_add_detail)
    RelativeLayout llAddDetail;
    @BindView(R.id.et_title)
    TextView etTitle;
    @BindView(R.id.et_position)
    TextView etPosition;
    @BindView(R.id.tv_one_name)
    TextView tvOneName;
    @BindView(R.id.tv_two_name)
    TextView tvTwoName;
    @BindView(R.id.tv_three_name)
    TextView tvThreeName;
    @BindView(R.id.et_input_check_content)
    TextView etInputCheckContent;
    @BindView(R.id.btn_add_detail)
    TextView btnAddDetail;
    @BindView(R.id.deal_list_add)
    RecyclerView dealListAdd;
    @BindView(R.id.iv_pic1)
    SimpleDraweeView ivPic1;
    @BindView(R.id.iv_pic2)
    SimpleDraweeView ivPic2;
    @BindView(R.id.iv_pic3)
    SimpleDraweeView ivPic3;
    @BindView(R.id.ll_add_deal)
    LinearLayout llAddDeal;
    private WorkCheckInfoBean infoBean;
    private WorkCheckInfoBean.WorkInspectDetailsBean detailsBean;

    private List<WorkCheckInfoBean.WorkInspectDetailsBean.WorkInspectDetailDisposesBean> beanList = new ArrayList<>();
    private AddCheckInfoDetailAdapter maintenanceDetailAdapter;
    private Long detailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_work_check_info);
        ButterKnife.bind(this);
        setTitle("检查明细");
        setLeftBack();

        infoBean = (WorkCheckInfoBean) getIntent().getSerializableExtra("infoBean");
        detailsBean = (WorkCheckInfoBean.WorkInspectDetailsBean) getIntent().getSerializableExtra("detailsBean");
        detailId = detailsBean.getId();
        initView();
    }

    private void initView() {

        Long uid = EanfangApplication.getApplication().getUserId();

//        if (uid.equals(infoBean.getCreateUserId())) {
//            llAddDetail.setVisibility(View.GONE);
//        } else {
//            llAddDetail.setVisibility(View.VISIBLE);
//        }


        if (uid.equals(infoBean.getAssigneeUserId()) && EanfangConst.WORK_INSPECT_STATUS_CREATE == detailsBean.getStatus() && (EanfangConst.WORK_INSPECT_STATUS_CREATE == infoBean.getStatus())) {
            llAddDetail.setVisibility(View.VISIBLE);
        } else {
            llAddDetail.setVisibility(View.GONE);
        }


        if (EanfangConst.WORK_INSPECT_STATUS_FINISH == infoBean.getStatus()) {
            btnAddDetail.setVisibility(View.GONE);
        }

        etTitle.setText(detailsBean.getTitle());
        etPosition.setText(detailsBean.getRegion());
        tvOneName.setText(Config.get().getBusinessNameByCode(detailsBean.getBusinessThreeCode(), 1));
        tvTwoName.setText(Config.get().getBusinessNameByCode(detailsBean.getBusinessThreeCode(), 2));
        tvThreeName.setText(Config.get().getBusinessNameByCode(detailsBean.getBusinessThreeCode(), 3));

        etInputCheckContent.setText(detailsBean.getInfo());

        if (!StringUtils.isEmpty(detailsBean.getPictures())) {
            String[] urls = detailsBean.getPictures().split(",");

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

        btnAddDetail.setOnClickListener((v) -> {
            Intent intent = getIntent();
            intent.setClass(this, AddDealwithInfoActivity.class);
            intent.putExtra("data", detailsBean);
            intent.putExtra("id", infoBean.getId());
            intent.putExtra("detailId", detailId);
            startActivityForResult(intent, WorkCheckListFragment.REQUST_REFRESH_CODE);
        });

        if (detailsBean.getWorkInspectDetailDisposes() != null) {
            beanList.addAll(detailsBean.getWorkInspectDetailDisposes());
            maintenanceDetailAdapter = new AddCheckInfoDetailAdapter(R.layout.item_quotation_detail, beanList);

            dealListAdd.setLayoutManager(new LinearLayoutManager(this));
            dealListAdd.setAdapter(maintenanceDetailAdapter);
            dealListAdd.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                    new LookDealwithCheckInfoDetailView(LookWorkCheckInfoActivity.this, true, beanList.get(position), infoBean).show();

//                    Intent intent = new Intent(LookWorkCheckInfoActivity.this, LookDealwithCheckInfoDetailActivity.class);
                    Intent intent = getIntent();
                    intent.setClass(LookWorkCheckInfoActivity.this, LookDealwithCheckInfoDetailActivity.class);
                    intent.putExtra("bean", beanList.get(position));
                    intent.putExtra("workCheckInfoBean", infoBean);
                    startActivityForResult(intent, WorkCheckListFragment.REQUST_REFRESH_CODE);
                }
            });
            maintenanceDetailAdapter.notifyDataSetChanged();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            if (data != null) {
                setResult(RESULT_OK, data);
            } else {
                setResult(RESULT_OK);
            }
            finishSelf();
        }
    }
}
