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

import com.eanfang.base.BaseDialog;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.model.WorkCheckInfoBean;
import net.eanfang.client.util.ImagePerviewUtil;
import net.eanfang.client.util.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  17:36
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class LookDealwithCheckInfoDetailView extends BaseDialog implements View.OnClickListener {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private Activity mContext;
    private WorkCheckInfoBean.BeanBean.DetailsBeanX.DetailsBean bean;
    private String revUser;
    private int id;
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

    public LookDealwithCheckInfoDetailView(Activity context, boolean isfull, WorkCheckInfoBean.BeanBean.DetailsBeanX.DetailsBean bean,
                                           String revUser, int id) {
        super(context, isfull);
        this.mContext = context;
        this.bean = bean;
        this.revUser = revUser;
        this.id = id;
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

        ivPic1.setOnClickListener(this);
        ivPic2.setOnClickListener(this);
        ivPic3.setOnClickListener(this);
        btnAdopt.setOnClickListener(v -> dealWith("2"));
        btnReject.setOnClickListener(v -> dealWith("3"));

//        String uid = EanfangApplication.getApplication().getUser().getPersonId();
//        if (uid.equals(revUser)) {
//            if (EanfangConst.WORK_INSPECT_STATUS_FAIL.equals(bean.getStatus()) ||
//                    EanfangConst.WORK_INSPECT_STATUS_FINISH.equals(bean.getStatus()) ||
//                    EanfangConst.WORK_INSPECT_STATUS_REVIEW.equals(bean.getStatus())) {
//                llDealWith.setVisibility(View.GONE);
//            }
//
//        } else {
//            if (EanfangConst.WORK_INSPECT_STATUS_FAIL.equals(bean.getStatus()) ||
//                    EanfangConst.WORK_INSPECT_STATUS_FINISH.equals(bean.getStatus())) {
//                llDealWith.setVisibility(View.GONE);
//            } else {
//                llDealWith.setVisibility(View.VISIBLE);
//            }
//        }


        etTitle.setText(bean.getInspectDetailTitle());
        etInputCheckContent.setText(bean.getDisposeInfo());
        etRemark.setText(bean.getRemark());

        if (!TextUtils.isEmpty(bean.getPic1())) {
            ivPic1.setImageURI(Uri.parse(bean.getPic1()));
            ivPic1.setVisibility(View.VISIBLE);
        } else {
            ivPic1.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(bean.getPic2())) {
            ivPic2.setImageURI(Uri.parse(bean.getPic2()));
            ivPic2.setVisibility(View.VISIBLE);
        } else {
            ivPic2.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(bean.getPic3())) {
            ivPic3.setImageURI(Uri.parse(bean.getPic3()));
            ivPic3.setVisibility(View.VISIBLE);
        } else {
            ivPic3.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        ArrayList<String> picList = new ArrayList<String>();
        switch (v.getId()) {
            case R.id.iv_pic1:
                if (!StringUtils.isEmpty(bean.getPic1())) {
                    picList.add(bean.getPic1());
                }
                break;
            case R.id.iv_pic2:
                if (!StringUtils.isEmpty(bean.getPic2())) {
                    picList.add(bean.getPic2());
                }
                break;
            case R.id.iv_pic3:
                if (!StringUtils.isEmpty(bean.getPic3())) {
                    picList.add(bean.getPic3());
                }
                break;
            default:
                break;

        }
        ImagePerviewUtil.perviewImage(mContext, picList);

    }

    private void dealWith(String status) {
        EanfangHttp.get(ApiService.GET_REVIEW_WORK_DETAIL_DISPOSE)
                .tag(this)
                .params("status", status)
                .params("id", bean.getId())
                .execute(new EanfangCallback(mContext, true) {


                    @Override
                    public void onSuccess(Object bean) {
                        new WorkCheckInfoView(mContext, true, id).show();
                    }

                    @Override
                    public void onError(String message) {
                        showToast(message);
                    }
                });
    }
}
