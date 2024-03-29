package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.biz.model.bean.MainHistoryDetailBean;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.util.StrUtil;

/**
 * Created by Mr.hou
 *
 * @on 2017/10/13  10:29
 * @email houzhongzhou@yeah.net
 * @desc 查看维保历史订单详情
 */

public class MeintenanceDetailDialog extends BaseDialog {


    @BindView(R.id.tv_business_type)
    TextView tvBusinessType;
    @BindView(R.id.tv_device_type)
    TextView tvDeviceType;
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_brand_model)
    TextView tvBrandModel;
    @BindView(R.id.et_amount)
    TextView etAmount;
    @BindView(R.id.et_price)
    TextView etPrice;
    @BindView(R.id.tv_main_leave)
    TextView tvMainLeave;
    @BindView(R.id.tv_main_result)
    TextView tvMainResult;
    @BindView(R.id.et_question)
    TextView etQuestion;
    @BindView(R.id.et_reason_analysis)
    TextView etReasonAnalysis;
    @BindView(R.id.et_maintenance_measures)
    TextView etMaintenanceMeasures;
    @BindView(R.id.iv_pic1)
    ImageView ivPic1;
    @BindView(R.id.iv_pic2)
    ImageView ivPic2;
    @BindView(R.id.iv_pic3)
    ImageView ivPic3;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;


    private MainHistoryDetailBean.MaintainDetailsBean bean;

    public MeintenanceDetailDialog(Activity context, MainHistoryDetailBean.MaintainDetailsBean eventlistBean) {
        super(context);
        this.bean = eventlistBean;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_meintenance_detail);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("查看明细");
        tvBusinessType.setText(Config.get().getBusinessNameByCode(bean.getBusinessThreeCode(), 1));
        tvDeviceType.setText(Config.get().getBusinessNameByCode(bean.getBusinessThreeCode(), 2));
        tvDeviceName.setText(Config.get().getBusinessNameByCode(bean.getBusinessThreeCode(), 3));
        tvBrandModel.setText(Config.get().getModelNameByCode(bean.getBusinessFourCode(), 2));
        etAmount.setText(bean.getCount() + "");
        etPrice.setText(bean.getInstallPosition());
        tvMainLeave.setText(GetConstDataUtils.getMaintainLevelList().get(bean.getMaintainLevel()));
        tvMainResult.setText(GetConstDataUtils.getCheckResultList().get(bean.getCheckResult()));
        etQuestion.setText(bean.getQuestion());
        etMaintenanceMeasures.setText(bean.getSolution());
        etReasonAnalysis.setText(bean.getCause());

        if (!StrUtil.isEmpty(bean.getPictures())) {
            String[] urls = bean.getPictures().split(",");

            if (urls.length >= 1) {
                GlideUtil.intoImageView(context,Uri.parse(BuildConfig.OSS_SERVER + urls[0]),ivPic1);
                ivPic1.setVisibility(View.VISIBLE);
            } else {
                ivPic1.setVisibility(View.GONE);
            }

            if (urls.length >= 2) {
                GlideUtil.intoImageView(context,Uri.parse(BuildConfig.OSS_SERVER + urls[1]),ivPic2);
                ivPic2.setVisibility(View.VISIBLE);
            } else {
                ivPic2.setVisibility(View.GONE);
            }
            if (urls.length >= 3) {
                GlideUtil.intoImageView(context,Uri.parse(BuildConfig.OSS_SERVER + urls[2]),ivPic3);
                ivPic3.setVisibility(View.VISIBLE);
            } else {
                ivPic3.setVisibility(View.GONE);
            }
        }

    }

}
