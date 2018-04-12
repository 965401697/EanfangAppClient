package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;
import com.eanfang.util.V;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.RepairOrderEntity;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.WorkerDetailAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.util.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;


/**
 * 技师详情
 * Created by Administrator on 2017/4/8.
 */

public class WorkerDetailActivity extends BaseClientActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_header)
    SimpleDraweeView ivHeader;
    @BindView(R.id.tv_realname)
    TextView tvRealname;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_koubei)
    TextView tvKoubei;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.iv_down)
    ImageView ivDown;
    @BindView(R.id.ll_area)
    LinearLayout llArea;
    @BindView(R.id.rv_list1)
    RecyclerView rvList1;
    @BindView(R.id.rv_list2)
    RecyclerView rvList2;
    @BindView(R.id.rv_list3)
    RecyclerView rvList3;
    @BindView(R.id.iv_pic1)
    SimpleDraweeView ivPic1;
    @BindView(R.id.iv_pic2)
    SimpleDraweeView ivPic2;
    @BindView(R.id.iv_pic3)
    SimpleDraweeView ivPic3;
    @BindView(R.id.iv_pic4)
    SimpleDraweeView ivPic4;
    @BindView(R.id.iv_haopinglv)
    NumberProgressBar ivHaopinglv;
    @BindView(R.id.tv_haopinglv)
    TextView tvHaopinglv;
    @BindView(R.id.rb_star1)
    MaterialRatingBar rbStar1;
    @BindView(R.id.rb_star2)
    MaterialRatingBar rbStar2;
    @BindView(R.id.rb_star3)
    MaterialRatingBar rbStar3;
    @BindView(R.id.rb_star4)
    MaterialRatingBar rbStar4;
    @BindView(R.id.rb_star5)
    MaterialRatingBar rbStar5;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    private String id;

    private ArrayList<String> mDataList1;
    private ArrayList<String> mDataList2;
    private ArrayList<String> mDataList3;

    private RepairOrderEntity toRepairBean;
    private WorkerEntity detailsBean;
    private String companyUserId;
    private String workerId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_detail);
        ButterKnife.bind(this);
        getData();
        initView();
        getWorkerDetailData();
        setListener();
        initAdapter();

        setTitle("技师详情");
        setLeftBack();
    }

    //获取技师信息
    private void getWorkerDetailData() {
        EanfangHttp.get(RepairApi.GET_REPAIR_WORKER_DETAIL)
                .params("workerId", workerId)
                .params("userId", companyUserId)
                .execute(new EanfangCallback<WorkerEntity>(this, true, WorkerEntity.class, (bean) -> {
                    detailsBean = bean;
                    setData(bean);
                }));
    }

    private void setListener() {
        if (toRepairBean == null) {
            tvSelect.setVisibility(View.GONE);
//            return;
        }

        tvSelect.setOnClickListener((v) -> {
            toRepairBean.setAssigneeUserId(detailsBean.getCompanyUserId());
            toRepairBean.setAssigneeTopCompanyId(detailsBean.getCompanyEntity().getTopCompanyId());
            toRepairBean.setStatus(1);
            toRepairBean.setAssigneeOrgCode(detailsBean.getDepartmentEntity().getOrgCode());
            Intent intent = new Intent(WorkerDetailActivity.this, OrderConfirmActivity.class);
            intent.putExtra("bean", toRepairBean);
            startActivity(intent);
        });
        llArea.setOnClickListener((v) -> {
            if (rvList1.getVisibility() == View.VISIBLE) {
                rvList1.setVisibility(View.GONE);
                ivDown.setImageResource(R.mipmap.arrow_down);
            } else {
                rvList1.setVisibility(View.VISIBLE);
                ivDown.setImageResource(R.mipmap.arrow_up);
            }
        });

    }

    private void initView() {
        rvList1.setLayoutManager(new GridLayoutManager(this, 2));
        rvList2.setLayoutManager(new GridLayoutManager(this, 2));
        rvList3.setLayoutManager(new GridLayoutManager(this, 2));

        if (PrefUtils.getVBoolean(this, PrefUtils.ISCOLLECTED) == false) {
            setRightImageResId(R.mipmap.heart);
        } else {
            setRightImageResId(R.mipmap.hearted);
        }
        setRightImageOnClickListener((v) -> {
            isCollected();
        });

    }

    private void collected() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("assigneeId", companyUserId);
        jsonObject.put("ownerId", EanfangApplication.getApplication().getUserId());
        jsonObject.put("type", 0);
        EanfangHttp.post(RepairApi.GET_COLLECT_ADD)
                .upJson(jsonObject.toJSONString())
                .execute(new EanfangCallback(this, false, JSONObject.class, (bean) -> {
                    setRightImageResId(R.mipmap.hearted);
                    PrefUtils.setBoolean(getApplicationContext(), PrefUtils.ISCOLLECTED, true);
                    showToast("收藏成功");
                }));
    }

    /**
     * 是否收藏过
     */
    private void isCollected() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("assigneeId", companyUserId);
        jsonObject.put("ownerId", EanfangApplication.getApplication().getUserId());
        jsonObject.put("type", 0);

        EanfangHttp.post(RepairApi.GET_COLLECT_EXISTS)
                .upJson(jsonObject.toJSONString())
                .execute(new EanfangCallback<JSONObject>(this, false, JSONObject.class, (bean) -> {
                    boolean isCollect = bean.getBoolean("exists");
                    runOnUiThread(() -> {
                        if (isCollect == false) {
                            collected();
                        } else {
                            cancelCollected();
                        }
                    });
                }));
    }

    /**
     * 取消收藏
     */
    private void cancelCollected() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("assigneeId", companyUserId + "");
        queryEntry.getEquals().put("ownerId", EanfangApplication.getApplication().getUserId() + "");
        queryEntry.getEquals().put("type", "0");
        EanfangHttp.post(RepairApi.GET_COLLECT_CANCEL)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback(this, false, JSONObject.class, (bean) -> {
                    setRightImageResId(R.mipmap.heart);
                    PrefUtils.setBoolean(getApplicationContext(), PrefUtils.ISCOLLECTED, false);
                    showToast("取消收藏");
                }));
    }


    private void setData(WorkerEntity bean) {
        if (bean == null) {
            return;
        }
        if (bean.getAccountEntity() != null) {
            ivHeader.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + bean.getAccountEntity().getAvatar()));
            tvRealname.setText(bean.getAccountEntity().getRealName());
            String region = Config.get().getAddressByCode(bean.getAccountEntity().getAreaCode());
            tvAddress.setText((region != null ? (region + "\r\n") : "") + bean.getAccountEntity().getAddress());
        }
        if (bean.getCompanyEntity() != null) {
            tvCompanyName.setText(bean.getCompanyEntity().getOrgName());
        }
        tvNumber.setText(V.v(() -> bean.getRepairCount()) + "单");
        tvKoubei.setText(V.v(() -> bean.getPublicPraise() / 100) + "分");
        if (bean.getVerifyEntity() != null) {
            tvLevel.setText(GetConstDataUtils.getWorkingLevelList().get(bean.getVerifyEntity().getWorkingLevel()));
            tvYear.setText(GetConstDataUtils.getWorkingYearList().get(bean.getVerifyEntity().getWorkingYear()));
        }
        tvCode.setText(V.v(() -> bean.getWorkerNumber()) + "");

        if (bean.getGoodRate() != 0) {
            tvHaopinglv.setText(bean.getGoodRate() + "%");
            ivHaopinglv.setProgress(bean.getGoodRate());
        }
        rbStar1.setRating(bean.getItem1());
        rbStar2.setRating(bean.getItem2());
        rbStar3.setRating(bean.getItem3());
        rbStar4.setRating(bean.getItem4());
        rbStar5.setRating(bean.getItem5());

        mDataList1 = new ArrayList<>();
        mDataList1.clear();
        if (bean.getRegionList() != null && !bean.getRegionList().isEmpty()) {
            mDataList1.addAll(Stream.of(bean.getRegionList()).map(regionId -> Config.get().getAddressById(regionId)).toList());
        }

        mDataList2 = new ArrayList<>();
        mDataList2.clear();
        List<Integer> serviceList = bean.getServiceList();
        if (serviceList != null && !serviceList.isEmpty()) {
            mDataList2.addAll(Stream.of(serviceList).map(id -> Config.get().getServiceNameById(id)).toList());
        }

        List<Integer> businessType = bean.getBusinessList();
        mDataList3 = new ArrayList<>();
        mDataList3.clear();
        if (businessType != null && !businessType.isEmpty()) {
            mDataList3.addAll(Stream.of(Config.get().getBusinessList(1)).filter(bus -> businessType.contains(bus.getDataId())).map(bus -> Config.get().getBusinessNameById(bus.getDataId())).toList());
        }
        initAdapter();
        initHonor(bean);
    }

    private void initAdapter() {
        BaseQuickAdapter evaluateAdapter1 = new WorkerDetailAdapter(R.layout.item_worker_detail1, mDataList1);
        evaluateAdapter1.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvList1.setAdapter(evaluateAdapter1);

        BaseQuickAdapter evaluateAdapter2 = new WorkerDetailAdapter(R.layout.item_worker_detail1, mDataList2);
        evaluateAdapter1.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvList2.setAdapter(evaluateAdapter2);

        BaseQuickAdapter evaluateAdapter3 = new WorkerDetailAdapter(R.layout.item_worker_detail1, mDataList3);
        evaluateAdapter3.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvList3.setAdapter(evaluateAdapter3);
    }

    private void getData() {
        toRepairBean = V.v(() -> (RepairOrderEntity) getIntent().getSerializableExtra("toRepairBean"));
        companyUserId = V.v(() -> getIntent().getStringExtra("companyUserId"));
        workerId = V.v(() -> getIntent().getStringExtra("workerId"));
    }


    private void initHonor(WorkerEntity bean) {
        if (!StringUtils.isEmpty(bean.getVerifyEntity().getHonorPics())) {
            String[] urls = bean.getVerifyEntity().getHonorPics().split(",");

            if (urls.length >= 1) {
                ivPic1.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(BuildConfig.OSS_SERVER+urls[0]));
                ivPic1.setVisibility(View.VISIBLE);
            } else {
                ivPic1.setVisibility(View.GONE);
            }

            if (urls.length >= 2) {
                ivPic2.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(BuildConfig.OSS_SERVER+urls[1]));
                ivPic2.setVisibility(View.VISIBLE);
            } else {
                ivPic2.setVisibility(View.GONE);
            }
            if (urls.length >= 3) {
                ivPic3.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(BuildConfig.OSS_SERVER+urls[2]));
                ivPic3.setVisibility(View.VISIBLE);
            } else {
                ivPic3.setVisibility(View.GONE);
            }
            if (urls.length >= 4) {
                ivPic4.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(BuildConfig.OSS_SERVER+urls[3]));
                ivPic4.setVisibility(View.VISIBLE);
            } else {
                ivPic4.setVisibility(View.GONE);
            }
        }


    }
}
