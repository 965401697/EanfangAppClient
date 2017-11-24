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
import com.eanfang.base.BaseDialog;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.config.EanfangConst;
import net.eanfang.client.ui.activity.worksapce.AddDealwithInfoActivity;
import net.eanfang.client.ui.adapter.AddCheckInfoDetailAdapter;
import net.eanfang.client.ui.model.WorkCheckInfoBean;
import net.eanfang.client.util.ImagePerviewUtil;
import net.eanfang.client.util.StringUtils;

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

public class LookWorkCheckInfoView extends BaseDialog implements View.OnClickListener {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_add_detail)
    RelativeLayout llAddDetail;
    private Activity mContext;
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

    private WorkCheckInfoBean.BeanBean.DetailsBeanX detailsBeanX;
    private List<WorkCheckInfoBean.BeanBean.DetailsBeanX.DetailsBean> beanList = new ArrayList<>();
    private AddCheckInfoDetailAdapter maintenanceDetailAdapter;
    private String recevieUser;
    private int id;

    public LookWorkCheckInfoView(Activity context, boolean isfull, WorkCheckInfoBean.BeanBean.DetailsBeanX data, String recevieUser, int id) {
        super(context, isfull);
        this.mContext = context;
        this.detailsBeanX = data;
        this.recevieUser = recevieUser;
        this.id = id;
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
        ivPic1.setOnClickListener(this);
        ivPic2.setOnClickListener(this);
        ivPic3.setOnClickListener(this);

        String uid = EanfangApplication.getApplication().getUser().getPersonId();
        if (uid.equals(recevieUser)) {
            llAddDetail.setVisibility(View.VISIBLE);
        } else {
            llAddDetail.setVisibility(View.GONE);
        }

        if (uid.equals(detailsBeanX.getCreateUser())) {
            llAddDetail.setVisibility(View.GONE);
        } else {
            llAddDetail.setVisibility(View.VISIBLE);
        }
        if (EanfangConst.WORK_INSPECT_STATUS_FINISH.equals(detailsBeanX.getStatus())) {
            btnAddDetail.setVisibility(View.GONE);
        }

        etTitle.setText(detailsBeanX.getTitle());
        etPosition.setText(detailsBeanX.getRegion());
        tvOneName.setText(detailsBeanX.getBusinessOne());
        tvTwoName.setText(detailsBeanX.getBusinessTwo());
        tvThreeName.setText(detailsBeanX.getBusinessThree());

        etInputCheckContent.setText(detailsBeanX.getInfo());
        if (!TextUtils.isEmpty(detailsBeanX.getPic1())) {
            ivPic1.setImageURI(Uri.parse(detailsBeanX.getPic1()));
            ivPic1.setVisibility(View.VISIBLE);
        } else {
            ivPic1.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(detailsBeanX.getPic2())) {
            ivPic2.setImageURI(Uri.parse(detailsBeanX.getPic2()));
            ivPic2.setVisibility(View.VISIBLE);
        } else {
            ivPic2.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(detailsBeanX.getPic3())) {
            ivPic3.setImageURI(Uri.parse(detailsBeanX.getPic3()));
            ivPic3.setVisibility(View.VISIBLE);
        } else {
            ivPic3.setVisibility(View.GONE);
        }
        btnAddDetail.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, AddDealwithInfoActivity.class)
                .putExtra("data", detailsBeanX)
                .putExtra("id", id)));

        beanList.addAll(detailsBeanX.getDetails());
        maintenanceDetailAdapter = new AddCheckInfoDetailAdapter(R.layout.item_quotation_detail, beanList);

        dealListAdd.setLayoutManager(new LinearLayoutManager(mContext));
        dealListAdd.setAdapter(maintenanceDetailAdapter);

        dealListAdd.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                new LookDealwithCheckInfoDetailView(mContext, true, beanList.get(position), recevieUser, id).show();
            }
        });
        maintenanceDetailAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        ArrayList<String> picList = new ArrayList<String>();
        switch (v.getId()) {
            case R.id.iv_pic1:
                if (!StringUtils.isEmpty(detailsBeanX.getPic1())) {
                    picList.add(detailsBeanX.getPic1());
                }
                break;
            case R.id.iv_pic2:
                if (!StringUtils.isEmpty(detailsBeanX.getPic2())) {
                    picList.add(detailsBeanX.getPic2());
                }
                break;
            case R.id.iv_pic3:
                if (!StringUtils.isEmpty(detailsBeanX.getPic3())) {
                    picList.add(detailsBeanX.getPic3());
                }
                break;
            default:
                break;

        }
        ImagePerviewUtil.perviewImage(mContext, picList);

    }

}
