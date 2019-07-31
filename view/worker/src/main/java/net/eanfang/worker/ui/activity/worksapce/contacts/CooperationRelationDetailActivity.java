package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JsonUtils;
import com.eanfang.biz.model.QueryEntry;
import com.eanfang.biz.model.entity.CooperationEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hutool.core.date.DateUtil;

public class CooperationRelationDetailActivity extends BaseWorkerActivity {

    @BindView(R.id.iv_company_logo)
    CircleImageView ivCompanyLogo;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.iv_user_header)
    CircleImageView ivUserHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_business)
    TextView tvBusiness;
    @BindView(R.id.tv_classify)
    TextView tvClassify;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    private StringBuffer classifySb = new StringBuffer();
    private StringBuffer osSb = new StringBuffer();

    JSONArray array = new JSONArray();
    private String companyName;
    private CooperationEntity cooperationEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cooperation_relation_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("合作业务");
        setLeftBack();
        initData();
    }

    private void initData() {

        cooperationEntity = (CooperationEntity) getIntent().getSerializableExtra("bean");

        QueryEntry queryEntry = new QueryEntry();

        queryEntry.getEquals().put("assigneeOrgId", String.valueOf(cooperationEntity.getAssigneeOrgId()));
        queryEntry.getEquals().put("ownerOrgId", String.valueOf(cooperationEntity.getOwnerOrgId()));
        queryEntry.getEquals().put("beginTime", DateUtil.date(cooperationEntity.getBeginTime()).toString("yyyy.MM.dd"));
        queryEntry.getEquals().put("endTime", DateUtil.date(cooperationEntity.getEndTime()).toString("yyyy.MM.dd"));
        queryEntry.getEquals().put("status", String.valueOf(cooperationEntity.getStatus()));


        if (cooperationEntity.getStatus() == 1) {
            tvSure.setVisibility(View.VISIBLE);
            tvSure.setBackgroundColor(getResources().getColor(R.color.white));
            tvSure.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvSure.setText("审核通过");
        } else if (cooperationEntity.getStatus() == 2) {
            tvSure.setVisibility(View.VISIBLE);
            tvSure.setBackgroundColor(getResources().getColor(R.color.white));
            tvSure.setTextColor(getResources().getColor(R.color.color_bottom));
            tvSure.setText("失效/拒绝");

        }


        EanfangHttp.post(NewApiService.GET_COOPERATION_DETAIL)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<CooperationEntity>(this, true, CooperationEntity.class, true, (list) -> {
                    initViews(list);
                }));
    }

    private void initViews(List<CooperationEntity> list) {

        if (list.size() > 0) {

            companyName = list.get(0).getAssigneeOrg().getOrgName();
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + list.get(0).getAssigneeOrg().getOrgUnitEntity().getLogoPic()),ivCompanyLogo);
            tvCompanyName.setText(companyName);
            if (!TextUtils.isEmpty(list.get(0).getAssigneeOrg().getOrgUnitEntity().getAreaCode())) {
                tvAddress.setText(Config.get().getAddressByCode(list.get(0).getAssigneeOrg().getOrgUnitEntity().getAreaCode()) + list.get(0).getAssigneeOrg().getOrgUnitEntity().getOfficeAddress());
            }
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + list.get(0).getCreateUserEntity().getAccountEntity().getAvatar()),ivUserHeader);
            tvName.setText(list.get(0).getCreateUserEntity().getAccountEntity().getRealName());
            tvPhone.setText(list.get(0).getCreateUserEntity().getAccountEntity().getMobile());

            tvStartTime.setText(DateUtil.date(list.get(0).getBeginTime()).toString("yyyy.MM.dd"));
            tvEndTime.setText(DateUtil.date(list.get(0).getEndTime()).toString("yyyy.MM.dd"));


            List<String> mOsList = GetConstDataUtils.getCooperationTypeList();

            for (CooperationEntity cooperationEntity : list) {

                array.add(cooperationEntity.getId());

                //业务类型
                String service = mOsList.get(cooperationEntity.getBusType());

                Log.e("zzw", service + "");
                //系统类型
                String business = Config.get().getBusinessNameByCode(cooperationEntity.getBusinessOneCode(), 1);

                if (!TextUtils.isEmpty(service)) {
                    if (!classifySb.toString().contains(service)) {
                        classifySb.append(" " + service + " ");
                    }
                }
                if (!TextUtils.isEmpty(business)) {
                    if (!osSb.toString().contains(business)) {
                        osSb.append(" " + business + " ");
                    }
                }
            }

            tvBusiness.setText(classifySb.toString());
            tvClassify.setText(osSb.toString());

        }
    }

    @OnClick(R.id.tv_sure)
    public void onViewClicked() {

        if (cooperationEntity.getStatus() == 0) {

            EanfangHttp.post(NewApiService.COOPERATION_AUDIT)
                    .upJson(array.toJSONString())
                    .execute(new EanfangCallback<com.alibaba.fastjson.JSONObject>(CooperationRelationDetailActivity.this, true, com.alibaba.fastjson.JSONObject.class) {

                        @Override
                        public void onSuccess(com.alibaba.fastjson.JSONObject bean) {
                            Intent intent = new Intent(CooperationRelationDetailActivity.this, CooperactionRelationSubActivity.class);
                            intent.putExtra("companyName", companyName);
                            startAnimActivity(intent);
                            endTransaction(true);
                        }
                    });
        }
    }
}
