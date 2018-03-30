package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.model.MainHistoryDetailBean;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    SimpleDraweeView ivPic1;
    @BindView(R.id.iv_pic2)
    SimpleDraweeView ivPic2;
    @BindView(R.id.iv_pic3)
    SimpleDraweeView ivPic3;
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
        tvBusinessType.setText(config.getBusinessNameByCode(bean.getBusinessThreeCode(), 1));
        tvDeviceType.setText(config.getBusinessNameByCode(bean.getBusinessThreeCode(), 2));
        tvDeviceName.setText(config.getBusinessNameByCode(bean.getBusinessThreeCode(), 3));
        tvBrandModel.setText(config.getModelNameByCode(bean.getBusinessFourCode(), 2));
        etAmount.setText(bean.getCount() + "");
        etPrice.setText(bean.getInstallPosition());
        tvMainLeave.setText(constDataUtils.getMaintainLevelList().get(bean.getMaintainLevel()));
        tvMainResult.setText(constDataUtils.getCheckResultList().get(bean.getCheckResult()));
        etQuestion.setText(bean.getQuestion());
        etMaintenanceMeasures.setText(bean.getSolution());
        etReasonAnalysis.setText(bean.getCause());

        if (!StringUtils.isEmpty(bean.getPictures())) {
            String[] urls = bean.getPictures().split(",");

            if (urls.length >= 1) {
                ivPic1.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + urls[0]));
                ivPic1.setVisibility(View.VISIBLE);
            } else {
                ivPic1.setVisibility(View.GONE);
            }

            if (urls.length >= 2) {
                ivPic2.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + urls[1]));
                ivPic2.setVisibility(View.VISIBLE);
            } else {
                ivPic2.setVisibility(View.GONE);
            }
            if (urls.length >= 3) {
                ivPic3.setImageURI(Uri.parse(BuildConfig.OSS_SERVER +urls[2]));
                ivPic3.setVisibility(View.VISIBLE);
            } else {
                ivPic3.setVisibility(View.GONE);
            }
        }

    }

}
