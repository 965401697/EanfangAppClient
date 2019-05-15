package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.BindCompanyBean;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PickerSelectUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.PartnerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/23  21:44
 * @email houzhongzhou@yeah.net
 * @desc 绑定公司
 */

public class BindCompanyView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_company_namewe)
    TextView tvCompanyNamewe;
    @BindView(R.id.tv_service_type)
    TextView tvServiceType;
    @BindView(R.id.rl_service_type)
    RelativeLayout rlServiceType;
    @BindView(R.id.tv_business_type)
    TextView tvBusinessType;
    @BindView(R.id.rl_business_type)
    RelativeLayout rlBusinessType;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.ll_start_time)
    RelativeLayout llStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.ll_end_time)
    RelativeLayout llEndTime;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private Activity mContext;
    private Long orgId;
    private BindCompanyBean beanList;
    private String company_name;

    public BindCompanyView(Activity context, boolean isfull, Long id, String companyName) {
        super(context, isfull);
        this.mContext = context;
        this.orgId = id;
        this.company_name = companyName;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_bind_cooperation_company);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("绑定客户");
        tvCompanyNamewe.setText(company_name);
        rlServiceType.setOnClickListener((v) -> PickerSelectUtil.singleTextPicker(mContext, "", tvServiceType,
                GetConstDataUtils.getCooperationTypeList()));

        rlBusinessType.setOnClickListener((v) -> PickerSelectUtil.singleTextPicker(mContext, "", tvBusinessType,
                Stream.of(Config.get().getBusinessList(1)).map(bus -> bus.getDataName()).toList()));

        llStartTime.setOnClickListener((v) -> PickerSelectUtil.onDownYearMonthDayPicker(mContext, tvStartTime, (year, month, day) -> {
            tvStartTime.setText(year + "-" + month + "-" + day);
        }));

        llEndTime.setOnClickListener(v -> PickerSelectUtil.onUpYearMonthDayPicker(mContext, tvEndTime, (year, month, day) -> {
            tvEndTime.setText(year + "-" + month + "-" + day);
        }));
        btnCommit.setOnClickListener(v -> summit(fillBean()));
    }

    private String fillBean() {
        beanList = new BindCompanyBean();
        beanList.setAssigneeOrgId(orgId);
        beanList.setBusType(GetConstDataUtils.getCooperationTypeList().indexOf(tvServiceType.getText().toString().trim()));
        beanList.setBusinessOneCode(Config.get().getBusinessCodeByName(tvBusinessType.getText().toString().trim(), 1));
        beanList.setBeginTime(tvStartTime.getText().toString().trim());
        beanList.setEndTime(tvEndTime.getText().toString().trim());
        beanList.setOwnerOrgId(EanfangApplication.getApplication().getCompanyId());
        List<BindCompanyBean> list = new ArrayList<>();
        list.add(beanList);
        String json = JSONArray.toJSONString(list);
        return json;
    }


    private void summit(String json) {
        EanfangHttp.post(UserApi.GET_COOPERATION_CREATE)
                .upJson(json)
                .execute(new EanfangCallback<JSONObject>(mContext, true, JSONObject.class, (bean) -> {
                    mContext.startActivity(new Intent(mContext, PartnerActivity.class));
                    dismiss();
                }));
    }
}
