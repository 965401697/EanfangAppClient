package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
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
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.AddDealwithInfoActivity;
import net.eanfang.client.ui.adapter.AddCheckInfoDetailAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  17:10
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class LookWorkCheckInfoView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
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
    private Activity mContext;
    private WorkCheckInfoBean infoBean;
    private WorkCheckInfoBean.WorkInspectDetailsBean detailsBean;

    private List<WorkCheckInfoBean.WorkInspectDetailsBean.WorkInspectDetailDisposesBean> beanList = new ArrayList<>();
    private AddCheckInfoDetailAdapter maintenanceDetailAdapter;
    private Long detailId;

    public LookWorkCheckInfoView(Activity context, boolean isfull, WorkCheckInfoBean data, WorkCheckInfoBean.WorkInspectDetailsBean detailsBean,
                                 Long id) {
        super(context, isfull);
        this.mContext = context;
        this.infoBean = data;
        this.detailsBean = detailsBean;
        this.detailId = id;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_look_check_info_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("检查明细");
        ivLeft.setOnClickListener(v -> dismiss());

        Long uid = EanfangApplication.getApplication().getUserId();
        if (uid.equals(infoBean.getAssigneeUserId())) {
            llAddDetail.setVisibility(View.VISIBLE);
        } else {
            llAddDetail.setVisibility(View.GONE);
        }

        if (uid.equals(infoBean.getCreateUserId())) {
            llAddDetail.setVisibility(View.GONE);
        } else {
            llAddDetail.setVisibility(View.VISIBLE);
        }
        if (EanfangConst.WORK_INSPECT_STATUS_FINISH == infoBean.getStatus()) {
            btnAddDetail.setVisibility(View.GONE);
        }

        etTitle.setText(detailsBean.getTitle());
        etPosition.setText(detailsBean.getRegion());
        tvOneName.setText(Config.get().getBusinessNameByCode(detailsBean.getBusinessThreeCode(), 1));
        // TODO: 2017/12/18 二级，三级
//        tvTwoName.setText(detailsBeanX.getBusinessTwo());
//        tvThreeName.setText(detailsBeanX.getBusinessThree());

        etInputCheckContent.setText(detailsBean.getInfo());

        if (!StringUtils.isEmpty(detailsBean.getPictures())) {
            String[] urls = detailsBean.getPictures().split(",");

            if (urls.length>=1) {
                ivPic1.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[0]));
                ivPic1.setVisibility(View.VISIBLE);
            } else {
                ivPic1.setVisibility(View.GONE);
            }

            if (urls.length>=2) {
                ivPic2.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[1]));
                ivPic2.setVisibility(View.VISIBLE);
            } else {
                ivPic2.setVisibility(View.GONE);
            }
            if (urls.length>=3) {
                ivPic3.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[2]));
                ivPic3.setVisibility(View.VISIBLE);
            } else {
                ivPic3.setVisibility(View.GONE);
            }
        }

        btnAddDetail.setOnClickListener((v) -> {
            Intent intent = new Intent(mContext, AddDealwithInfoActivity.class);
            intent.putExtra("data", detailsBean);
            intent.putExtra("id", infoBean.getId());
            intent.putExtra("detailId", detailId);
            mContext.startActivity(intent);
        });

        if (detailsBean.getWorkInspectDetailDisposes() != null) {
            beanList.addAll(detailsBean.getWorkInspectDetailDisposes());
            maintenanceDetailAdapter = new AddCheckInfoDetailAdapter(R.layout.item_quotation_detail, beanList);

            dealListAdd.setLayoutManager(new LinearLayoutManager(mContext));
            dealListAdd.setAdapter(maintenanceDetailAdapter);
            dealListAdd.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    new LookDealwithCheckInfoDetailView(mContext, true, beanList.get(position), infoBean).show();
                }
            });
            maintenanceDetailAdapter.notifyDataSetChanged();

        }

    }
}
