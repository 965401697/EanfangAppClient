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

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.eanfang.BuildConfig;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.config.Config;
import net.eanfang.client.config.Constant;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.apiservice.RepairApi;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.WorkerDetailAdapter;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.SelectWorkerBean;
import net.eanfang.client.ui.model.WorkerDetailsBean;
import net.eanfang.client.ui.model.repair.RepairOrderEntity;
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
    @BindView(R.id.rv_list2)
    RecyclerView rvList2;
    private String id;

    private ArrayList<String> mDataList1;
    private ArrayList<String> mDataList2;
    private ArrayList<String> mDataList3;

    private WorkerDetailsBean workerDetailsBean;
    private LinearLayout ll_repair;
    private LinearLayout ll_install;
    private RepairOrderEntity toRepairBean;
    private SelectWorkerBean selectWorkerBean;
    private ArrayList<String> picList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_detail);
        ButterKnife.bind(this);
        getData();
        initView();
        setListener();
        getWorkerDetailData();
        initAdapter();

        setTitle("技师详情");
        setLeftBack();
    }

    //获取技师信息
    private void getWorkerDetailData() {
        EanfangHttp.get(RepairApi.GET_REPAIR_WORKER_DETAIL)
                .params("workerId", selectWorkerBean.getId())
                .params("userId", selectWorkerBean.getVerifyEntity().getUserId())
                .execute(new EanfangCallback<WorkerDetailsBean>(this, true, WorkerDetailsBean.class, (bean) -> {
                    setData(bean);
                }));
    }

    private void setListener() {
        if (toRepairBean == null) {
            tvSelect.setVisibility(View.GONE);
//            return;
        }

        tvSelect.setOnClickListener((v) -> {
            Intent intent = new Intent(WorkerDetailActivity.this, OrderConfirmActivity.class);
            intent.putExtra("bean", toRepairBean);
            intent.putExtra("id", id);
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
        rvList3.setLayoutManager(new GridLayoutManager(this, 2));

        if (toRepairBean != null) {
            setRightImageResId(R.mipmap.heart);
        }
        setRightTitleOnClickListener((v) -> {
            setRightTitleClick();
        });

    }

    private void setRightTitleClick() {
        JSONObject json = new JSONObject();
        try {
//                    json.put("personuid", EanfangApplication.get().getUser().getPersonId());
            json.put("workeruid", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        EanfangHttp.post(ApiService.COLLECTION_WORK)
                .tag(this)
                .params("json", json.toString())
                .execute(new EanfangCallback(WorkerDetailActivity.this, false) {
                    @Override
                    public void onSuccess(Object bean) {
                        super.onSuccess(bean);
                        showToast("收藏成功");
                        setRightImageResId(R.mipmap.hearted);
                        setRightImageOnClickListener((v) -> {
                            showToast("已经收藏过该技师");
                        });
                    }

                    @Override
                    public void onFail(Integer code, String message, JSONObject jsonObject) {
                        super.onFail(code, message, jsonObject);
                        showToast(message);
                    }
                });
    }


    private void setData(WorkerDetailsBean bean) {
        workerDetailsBean = bean;

        ivHeader.setImageURI(Uri.parse(workerDetailsBean.getVerifyEntity().getHeadPic()));
        tvRealname.setText(workerDetailsBean.getVerifyEntity().getRealName());
        tvCompanyName.setText(workerDetailsBean.getCompanyEntity().getOrgName());
        tvNumber.setText(workerDetailsBean.getRepairCount() + "单");
        tvKoubei.setText(workerDetailsBean.getPublicPraise() / 100 + "分");
        tvLevel.setText(Config.getConfig().getConstBean().getShopConstant().get(Constant.WORKING_LEVEL).get(workerDetailsBean.getVerifyEntity().getWorkingLevel()));
        tvYear.setText(Config.getConfig().getConstBean().getShopConstant().get(Constant.WORKING_YEAR).get(workerDetailsBean.getVerifyEntity().getWorkingYear()));

        tvCode.setText(workerDetailsBean.getId() + "");
        String region = Config.getConfig().getAddress(workerDetailsBean.getPlaceCode());
        tvAddress.setText(region + "--" + workerDetailsBean.getVerifyEntity().getPlaceAddress());

        tvHaopinglv.setText(workerDetailsBean.getGoodRate() / 100 + "%");
        ivHaopinglv.setProgress(workerDetailsBean.getGoodRate() / 100);

        rbStar1.setRating(workerDetailsBean.getItem1());
        rbStar2.setRating(workerDetailsBean.getItem2());
        rbStar3.setRating(workerDetailsBean.getItem3());
        rbStar4.setRating(workerDetailsBean.getItem4());
        rbStar5.setRating(workerDetailsBean.getItem5());

//        mDataList1 = new ArrayList<>();
//        mDataList1.clear();
//        List<Integer> address = workerDetailsBean.getRegionList();
//        List<String> addressCode = Config.getConfig().getCode(address, Constant.AREA);
//        for (int i = 0; i < address.size(); i++) {
//            mDataList1.add(Config.getConfig().getAddress(addressCode.get(i)));
//        }

        mDataList2=new ArrayList<>();
        mDataList2.clear();
        List<Integer> serviceList = workerDetailsBean.getServiceList();
        for (int i=0;i<serviceList.size();i++){
            mDataList2.add(Config.getConfig().getServName(serviceList.get(i),Constant.BIZ_TYPE));
        }


        List<Integer> businessType = workerDetailsBean.getBusinessList();
        List<String> businessCode = Config.getConfig().getCode(businessType, Constant.SYS_TYPE);
        mDataList3 = new ArrayList<>();
        mDataList3.clear();
        for (int i = 0; i < businessType.size(); i++) {
//            mDataList3.add(Config.getConfig().getBusinessName(businessCode.get(i)));
            mDataList3.add(Config.getConfig().getServName(businessType.get(i),Constant.SYS_TYPE));
        }

        initAdapter();
//        initHonor();
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


    private void initHonor() {
        if (!StringUtils.isEmpty(workerDetailsBean.getVerifyEntity().getHonorPics())) {
            String[] urls = workerDetailsBean.getVerifyEntity().getHonorPics().split(",");

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
