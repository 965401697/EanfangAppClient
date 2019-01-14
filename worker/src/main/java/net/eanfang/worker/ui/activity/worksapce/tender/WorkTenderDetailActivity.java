package net.eanfang.worker.ui.activity.worksapce.tender;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.IfbOrderEntity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetDateUtils;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.ValueCallback;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.worktender.WorkTenderAdjunctAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2019/1/9
 * @description 招标详情
 */

public class WorkTenderDetailActivity extends BaseActivity {

    @BindView(R.id.tv_tender_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_shop_unit)
    TextView tvShopUnit;
    @BindView(R.id.tv_project_address)
    TextView tvProjectAddress;
    @BindView(R.id.tv_release_time)
    TextView tvReleaseTime;
    @BindView(R.id.tv_tender_file_price)
    TextView tvTenderFilePrice;
    @BindView(R.id.tv_budget_amount)
    TextView tvBudgetAmount;
    @BindView(R.id.tv_begin_time)
    TextView tvBeginTime;
    @BindView(R.id.tv_begin_address)
    TextView tvBeginAddress;
    @BindView(R.id.tv_get_tender_file_time)
    TextView tvGetTenderFileTime;
    @BindView(R.id.tv_get_tender_file_address)
    TextView tvGetTenderFileAddress;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_contact_phone)
    TextView tvContactPhone;
    @BindView(R.id.tv_fax)
    TextView tvFax;
    @BindView(R.id.tv_shop_unit_two)
    TextView tvShopUnitTwo;
    @BindView(R.id.tv_shop_unit_address)
    TextView tvShopUnitAddress;
    @BindView(R.id.tv_shop_unit_phone)
    TextView tvShopUnitPhone;
    @BindView(R.id.tv_agent_name)
    TextView tvAgentName;
    @BindView(R.id.tv_agent_address)
    TextView tvAgentAddress;
    @BindView(R.id.tv_agent_phone)
    TextView tvAgentPhone;
    @BindView(R.id.ll_notice_info)
    LinearLayout llNoticeInfo;
    @BindView(R.id.ll_contact)
    LinearLayout llContact;
    @BindView(R.id.rv_attch)
    RecyclerView rvAttch;

    private Long mId;

    //公告信息显示
    private boolean isNotice = true;
    // 联系人信息显示
    private boolean isContact = true;
    //附件
    private boolean isAdjunct = true;

    private WorkTenderAdjunctAdapter workTenderAdjunctAdapter;
    private List<String> mAdjunctList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_tender_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("标信详情");
        mId = getIntent().getLongExtra("id", 0);
        workTenderAdjunctAdapter = new WorkTenderAdjunctAdapter();
        rvAttch.setLayoutManager(new LinearLayoutManager(this));
        workTenderAdjunctAdapter.bindToRecyclerView(rvAttch);
        doGetData();
        workTenderAdjunctAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String name = String.valueOf(adapter.getData().get(position));
                String[] url = name.split("_");
                QbSdk.openFileReader(WorkTenderDetailActivity.this, com.eanfang.BuildConfig.OSS_SERVER + url[1], null, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {

                    }
                });
            }
        });
    }

    private void doGetData() {
        EanfangHttp.post(NewApiService.TENDER_DETAIL + "/" + mId)
                .execute(new EanfangCallback<IfbOrderEntity>(this, true, IfbOrderEntity.class, bean -> {
                    doSetData(bean);
                }));
    }

    private void doSetData(IfbOrderEntity ifbOrderEntity) {
        //公告标题
        tvTitle.setText(ifbOrderEntity.getAnnouncementName());
        //项目名称
        tvName.setText(ifbOrderEntity.getPurchaseName());
        //系统类别
        tvType.setText(Config.get().getBusinessNameByCode(ifbOrderEntity.getBusinessOneCode(), 1));
        //采购单位
        tvShopUnit.setText(ifbOrderEntity.getPurchaseCompanyName());
        //项目地区
        tvProjectAddress.setText(Config.get().getAddressByCode(ifbOrderEntity.getProjectArea()));
        //发布时间
        tvReleaseTime.setText(GetDateUtils.dateToDateTimeStringForChinse(ifbOrderEntity.getReleaseTime()));
        //招标文件售价
        tvTenderFilePrice.setText(ifbOrderEntity.getIfbFilePrice() + "");
        //预算金额
        tvBudgetAmount.setText(ifbOrderEntity.getBudgetPrice() + "");
        //开标时间
        tvBeginTime.setText(GetDateUtils.dateToDateTimeStringForChinse(ifbOrderEntity.getIfbOpenTime()));
        //开标地点
        tvBeginAddress.setText(ifbOrderEntity.getIfbOpenAddress());
        //获取招标文件时间
        tvGetTenderFileTime.setText(GetDateUtils.dateToDateTimeStringForChinse(ifbOrderEntity.getIfbFileStartTime()) + "至\n" +
                GetDateUtils.dateToDateTimeStringForChinse(ifbOrderEntity.getIfbFileEndTime()));
        //获取招标文件地点
        tvGetTenderFileAddress.setText(ifbOrderEntity.getIfbFileAddress());

        //联系人
        tvContact.setText(ifbOrderEntity.getIfbContacts());
        //联系电话
        tvContactPhone.setText(ifbOrderEntity.getIfbContactPhone());
        //传真
        tvFax.setText(ifbOrderEntity.getIfbContactPhone());
        //采购单位
        tvShopUnitTwo.setText(ifbOrderEntity.getPurchaseCompanyName());
        //采购单位地址
        tvShopUnitAddress.setText(ifbOrderEntity.getPurchaseUnitAddress());
        //采购单位电话
        tvShopUnitPhone.setText(ifbOrderEntity.getPurchaseUnitPhone());
        //代理机构名称
        tvAgentName.setText(ifbOrderEntity.getAgencyCompanyName());
        //代理机构地址
        tvAgentAddress.setText(ifbOrderEntity.getAgencyAddress());
        //代理机构电话
        tvAgentPhone.setText(ifbOrderEntity.getAgencyPhone());

        String[] info = ifbOrderEntity.getIfbFiles().split(",");
        for (int i = 0; i < info.length; i++) {
            mAdjunctList.add(info[i]);
        }
        workTenderAdjunctAdapter.setNewData(mAdjunctList);
    }

    @OnClick({R.id.iv_notice_show, R.id.iv_adjadjunct_show, R.id.iv_contact_show})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 公告信息
            case R.id.iv_notice_show:
                if (isNotice) {
                    llNoticeInfo.setVisibility(View.GONE);
                } else {
                    llNoticeInfo.setVisibility(View.VISIBLE);
                }
                break;
            //附件
            case R.id.iv_adjadjunct_show:
                if (isAdjunct) {
                    rvAttch.setVisibility(View.GONE);
                } else {
                    rvAttch.setVisibility(View.VISIBLE);
                }
                break;
            // 联系人信息
            case R.id.iv_contact_show:
                if (isContact) {
                    llContact.setVisibility(View.GONE);
                } else {
                    llContact.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }
}
