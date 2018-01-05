package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.BuildConfig;
import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.config.Config;
import net.eanfang.client.config.Constant;
import net.eanfang.client.network.apiservice.RepairApi;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.WorkerDetailAdapter;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.SelectWorkerBean;
import net.eanfang.client.ui.model.WorkerDetailsBean;
import net.eanfang.client.ui.model.repair.RepairOrderEntity;
import net.eanfang.client.util.JsonUtils;
import net.eanfang.client.util.PrefUtils;
import net.eanfang.client.util.QueryEntry;
import net.eanfang.client.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;


/**
 * 技师详情
 * Created by Administrator on 2017/4/8.
 */

public class WorkerDetailActivity extends BaseActivity {

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
    private SelectWorkerBean selectWorkerBean;
    private WorkerDetailsBean detailsBean;


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
                .params("workerId", selectWorkerBean.getId())
                .params("userId", selectWorkerBean.getCompanyUserId())
                .execute(new EanfangCallback<WorkerDetailsBean>(this, true, WorkerDetailsBean.class, (bean) -> {
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
        jsonObject.put("assigneeId", selectWorkerBean.getCompanyUserId());
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
        jsonObject.put("assigneeId", selectWorkerBean.getCompanyUserId());
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
        queryEntry.getEquals().put("assigneeId", selectWorkerBean.getCompanyUserId() + "");
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


    private void setData(WorkerDetailsBean bean) {
        ivHeader.setImageURI(Uri.parse(bean.getVerifyEntity().getHeadPic()));
        tvRealname.setText(bean.getVerifyEntity().getRealName());
        tvCompanyName.setText(bean.getCompanyEntity().getOrgName());
        tvNumber.setText(bean.getRepairCount() + "单");
        tvKoubei.setText(bean.getPublicPraise() / 100 + "分");
        tvLevel.setText(Config.getConfig().getConstBean().getShopConstant().get(Constant.WORKING_LEVEL).get(bean.getVerifyEntity().getWorkingLevel()));
        tvYear.setText(Config.getConfig().getConstBean().getShopConstant().get(Constant.WORKING_YEAR).get(bean.getVerifyEntity().getWorkingYear()));

        tvCode.setText(bean.getId() + "");
        String region = Config.getConfig().getAddress(bean.getPlaceCode());
        tvAddress.setText(region + "--" + bean.getVerifyEntity().getPlaceAddress());
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
        List<Integer> address = bean.getRegionList();
        List<String> addressCode = Config.getConfig().getCode(address, Constant.AREA);
        for (int i = 0; i < address.size(); i++) {
            mDataList1.add(Config.getConfig().getAddress(addressCode.get(i)));
        }

        List<Integer> serviceList = bean.getServiceList();
        mDataList2 = new ArrayList<>();
        mDataList2.clear();
        for (int i = 0; i < serviceList.size(); i++) {
            mDataList2.add(Config.getConfig().getServName(serviceList.get(i), Constant.BIZ_TYPE));
        }


        List<Integer> businessType = bean.getBusinessList();
        mDataList3 = new ArrayList<>();
        mDataList3.clear();
        for (int i = 0; i < businessType.size(); i++) {
            mDataList3.add(Config.getConfig().getServName(businessType.get(i), Constant.SYS_TYPE));
        }

        initAdapter();
//        initHonor(bean);
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
        toRepairBean = (RepairOrderEntity) getIntent().getSerializableExtra("toRepairBean");
        selectWorkerBean = (SelectWorkerBean) getIntent().getSerializableExtra("selectBean");
    }


    private void initHonor(WorkerDetailsBean bean) {
        if (!StringUtils.isEmpty(bean.getVerifyEntity().getHonorPics())) {
            String[] urls = bean.getVerifyEntity().getHonorPics().split(",");

            if (!TextUtils.isEmpty(urls[0])) {
                ivPic1.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[0]));
                ivPic1.setVisibility(View.VISIBLE);
            } else {
                ivPic1.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(urls[1])) {
                ivPic2.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[1]));
                ivPic2.setVisibility(View.VISIBLE);
            } else {
                ivPic2.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(urls[2])) {
                ivPic3.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[2]));
                ivPic3.setVisibility(View.VISIBLE);
            } else {
                ivPic3.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(urls[3])) {
                ivPic4.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[3]));
                ivPic4.setVisibility(View.VISIBLE);
            } else {
                ivPic4.setVisibility(View.GONE);
            }
        }


    }
}
