package net.eanfang.client.ui.activity.worksapce;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.eanfang.witget.ArcProgressView;
import com.eanfang.witget.CustomRadioGroup;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.RepairOrderEntity;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.repair.RepairActivity;
import net.eanfang.client.ui.adapter.WorkerDetailAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.util.PrefUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.eanfang.util.V.v;

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
    // 业务类型 动态view
    @BindView(R.id.rg_type)
    CustomRadioGroup rgType;
    @BindView(R.id.rl_allType)
    RelativeLayout rlAllType;
    // 业务领域 动态view
    @BindView(R.id.rg_area)
    CustomRadioGroup rgArea;
    @BindView(R.id.rl_allArea)
    RelativeLayout rlAllArea;
    // 头部好评率
    @BindView(R.id.arcprogressview)
    ArcProgressView arcprogressview;
    // 口碑分
    @BindView(R.id.tv_mouthGrade)
    TextView tvMouthGrade;
    // 好评率分
    @BindView(R.id.tv_goodGrade)
    TextView tvGoodGrade;
    // 业务类型查看更多
    @BindView(R.id.iv_workerDetaiTypelDown)
    ImageView ivWorkerDetaiTypelDown;
    // 技师资质
    @BindView(R.id.tv_workerQualification)
    TextView tvWorkerQualification;
    // 技师培训
    @BindView(R.id.tv_workerTrain)
    TextView tvWorkerTrain;
    // 设计订单
    @BindView(R.id.tv_designOrder)
    TextView tvDesignOrder;
    // 维修订单
    @BindView(R.id.tv_repairOrder)
    TextView tvRepairOrder;
    // 施工订单
    @BindView(R.id.tv_workOrder)
    TextView tvWorkOrder;
    // 评价订单
    @BindView(R.id.tv_evaluteOrder)
    TextView tvEvaluteOrder;
    private boolean isTypeMore = false;
    // 业务领域查看更多
    @BindView(R.id.iv_workerDetailAreaDown)
    ImageView ivWorkerDetailAreaDown;
    private boolean isAreaMore = false;
    private String id;

    // 服务区域
    private ArrayList<String> mDataList1;
    // 服务类型
    private ArrayList<String> mDataList2;
    private ArrayList<String> mTypeSmall = new ArrayList<>();
    // 业务类型
    private ArrayList<String> mDataList3;
    private ArrayList<String> mAreaSmall = new ArrayList<>();

    private BaseQuickAdapter evaluateAdapter1;

    private RepairOrderEntity toRepairBean;
    private WorkerEntity detailsBean;
    // 扫码二维码进入技师详情页面 传入的entriy
    private WorkerEntity mQRWorkerEntity;
    private boolean isComeIn = false;
    private RepairOrderEntity mScanRepairBean;
    private String companyUserId = "";
    private String workerId = "";

    private boolean isCollect;

    // 传到下个页面 头像 名称 公司名
    private String headUrl = "";
    private String workerName = "";
    private String comapnyName = "";

    private int regionSize = 30;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_detail);
        ButterKnife.bind(this);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        rvList1.setLayoutManager(new GridLayoutManager(this, 2));

        // 正常报修流程 获取数据
        toRepairBean = v(() -> (RepairOrderEntity) getIntent().getSerializableExtra("toRepairBean"));
        companyUserId = v(() -> getIntent().getStringExtra("companyUserId"));
        workerId = v(() -> getIntent().getStringExtra("workerId"));

        // 客户端扫描二维码获取数据
        mQRWorkerEntity = (WorkerEntity) getIntent().getSerializableExtra("workEntriy");
        if (mQRWorkerEntity != null) {
            isComeIn = true;
            tvSelect.setText("选择该技师");
        }
        // 获取workid  userid
        if (isComeIn) {
            companyUserId = String.valueOf(mQRWorkerEntity.getCompanyUserId());
            workerId = String.valueOf(mQRWorkerEntity.getId());
        }

        // 获取是否收藏
        if (PrefUtils.getVBoolean(this, PrefUtils.ISCOLLECTED) == false) {
            setRightImageResId(R.mipmap.ic_worker_collect);
        } else {
            setRightImageResId(R.mipmap.ic_worker_collect_pressed);
        }
        setRightImageOnClickListener((v) -> {
            if (isCollect) {
                cancelCollected();
            } else {
                collected();
            }
        });
        setTitle("技师详情");
        setLeftBack();
    }

    private void initData() {
        isCollected();
        getWorkerDetailData();
    }

    private void setListener() {
        if (toRepairBean == null) {
            tvSelect.setVisibility(View.GONE);
//            return;
        }
        if (isComeIn) {
            tvSelect.setVisibility(View.VISIBLE);
        }
        // 选择该技师
        tvSelect.setOnClickListener((v) -> {
            // 扫码进入
            if (isComeIn) {
                if (StringUtils.isEmpty(companyUserId) || companyUserId.equals("0")) {
                    showToast("当前技师缺少公司，无法报修。");
                    return;
                }

                mScanRepairBean = new RepairOrderEntity();
                mScanRepairBean.setAssigneeUserId(detailsBean.getCompanyUserId());
                mScanRepairBean.setAssigneeTopCompanyId(detailsBean.getCompanyEntity().getTopCompanyId());
                mScanRepairBean.setAssigneeCompanyId(detailsBean.getCompanyEntity().getCompanyId());
                if (EanfangApplication.getApplication().getCompanyId() == 0) {
                    mScanRepairBean.setStatus(0);
                } else {
                    mScanRepairBean.setStatus(1);
                }
                mScanRepairBean.setAssigneeOrgCode(detailsBean.getDepartmentEntity().getOrgCode());
            } else {
                toRepairBean.setAssigneeUserId(detailsBean.getCompanyUserId());
                toRepairBean.setAssigneeTopCompanyId(detailsBean.getCompanyEntity().getTopCompanyId());
                toRepairBean.setAssigneeCompanyId(detailsBean.getCompanyEntity().getCompanyId());
                if (EanfangApplication.getApplication().getCompanyId() == 0) {
                    toRepairBean.setStatus(0);
                } else {
                    toRepairBean.setStatus(1);
                }
                toRepairBean.setAssigneeOrgCode(detailsBean.getDepartmentEntity().getOrgCode());
            }
            Intent intent;
            // 直接进入报修页面
            if (isComeIn) {
                intent = new Intent(WorkerDetailActivity.this, RepairActivity.class);
                intent.putExtra("qrcode", "scaning");
                intent.putExtra("repairbean", mScanRepairBean);
            } else {
                intent = new Intent(WorkerDetailActivity.this, OrderConfirmActivity.class);
                intent.putExtra("bean", toRepairBean);
                intent.putExtra("headUrl", headUrl);
                intent.putExtra("workName", workerName);
                intent.putExtra("companyName", comapnyName);
                intent.putExtra("doorFee", getIntent().getIntExtra("doorFee", 0));
            }
            startActivity(intent);

        });
        // 区域
        llArea.setOnClickListener((v) -> {

            if (mDataList1.isEmpty()) {
                showToast("稍等一下，我还没准备好。");
                return;
            }
            if (rvList1.getVisibility() == View.VISIBLE) {
                rvList1.setVisibility(View.GONE);
                ivDown.setImageResource(R.mipmap.arrow_down);
                evaluateAdapter1.setNewData(new ArrayList());
            } else {
                rvList1.setVisibility(View.VISIBLE);
                fillRegionData();
                ivDown.setImageResource(R.mipmap.arrow_up);
            }
        });

        // 业务类型查看更多
        rlAllType.setOnClickListener((v) -> {
            if (mDataList2.size() <= 4) {
                showToast("暂无更多");
                return;
            }
            if (!isTypeMore) {// 显示更多 默认不显示更多
                addView(WorkerDetailActivity.this, rgType, mDataList2);
                ivWorkerDetaiTypelDown.setImageResource(R.mipmap.ic_worker_detail_type_up);
                isTypeMore = true;
            } else {
                mTypeSmall.clear();
                mTypeSmall.add(mDataList2.get(0));
                mTypeSmall.add(mDataList2.get(1));
                mTypeSmall.add(mDataList2.get(2));
                mTypeSmall.add(mDataList2.get(3));
                addView(WorkerDetailActivity.this, rgType, mTypeSmall);
                ivWorkerDetaiTypelDown.setImageResource(R.mipmap.ic_worker_detail_type_down);
                isTypeMore = false;
            }
        });
        // 业务领域查看更多
        rlAllArea.setOnClickListener((v) -> {
            if (mDataList3.size() <= 4) {
                showToast("暂无更多");
                return;
            }
            if (!isAreaMore) {// 显示更多 默认不显示更多
                addView(WorkerDetailActivity.this, rgArea, mDataList3);
                ivWorkerDetailAreaDown.setImageResource(R.mipmap.ic_worker_detail_type_up);
                isAreaMore = true;
            } else {
                mAreaSmall.clear();
                mAreaSmall.add(mDataList3.get(0));
                mAreaSmall.add(mDataList3.get(1));
                mAreaSmall.add(mDataList3.get(2));
                mAreaSmall.add(mDataList3.get(3));
                addView(WorkerDetailActivity.this, rgArea, mAreaSmall);
                ivWorkerDetailAreaDown.setImageResource(R.mipmap.ic_worker_detail_type_down);
                isAreaMore = false;
            }
        });

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
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {

                    isCollect = bean.getBoolean("exists");
                    runOnUiThread(() -> {
                        if (isCollect) {
                            setRightImageResId(R.mipmap.ic_worker_collect_pressed);
                        } else {
                            setRightImageResId(R.mipmap.ic_worker_collect);
                        }
                    });
                }));
    }

    /**
     * 进行收藏
     */
    private void collected() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("assigneeId", companyUserId);
        jsonObject.put("ownerId", EanfangApplication.getApplication().getUserId());
        jsonObject.put("type", 0);
        EanfangHttp.post(RepairApi.GET_COLLECT_ADD)
                .upJson(jsonObject.toJSONString())
                .execute(new EanfangCallback(this, true, JSONObject.class, (bean) -> {
                    setRightImageResId(R.mipmap.ic_worker_collect_pressed);
                    PrefUtils.setBoolean(getApplicationContext(), PrefUtils.ISCOLLECTED, true);
                    showToast("收藏成功");
                    isCollect = true;
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
                .execute(new EanfangCallback(this, true, JSONObject.class, (bean) -> {
                    setRightImageResId(R.mipmap.ic_worker_collect);
                    PrefUtils.setBoolean(getApplicationContext(), PrefUtils.ISCOLLECTED, false);
                    showToast("取消收藏");
                    isCollect = false;
                }));
    }


    private void setData(WorkerEntity bean) {
        if (bean == null) {
            return;
        }
        if (bean.getAccountEntity() != null) {
            ivHeader.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + bean.getAccountEntity().getAvatar()));
            headUrl = bean.getAccountEntity().getAvatar();
            workerName = bean.getAccountEntity().getRealName();
            comapnyName = bean.getCompanyEntity().getOrgName();
            tvRealname.setText(bean.getAccountEntity().getRealName());
            if (!StringUtils.isEmpty(bean.getAccountEntity().getAreaCode())) {
                String region = Config.get().getAddressByCode(bean.getAccountEntity().getAreaCode());
                tvAddress.setText((region != null ? (region + "\r\n") : "") + bean.getAccountEntity().getAddress());
            }
        }
        if (bean.getCompanyEntity() != null) {
            comapnyName = bean.getCompanyEntity().getOrgName();
            tvCompanyName.setText(bean.getCompanyEntity().getOrgName());
        }
        tvNumber.setText(v(() -> bean.getRepairCount()) + "单");
        tvKoubei.setText(v(() -> bean.getPublicPraise() / 100) + "分");
        if (bean.getVerifyEntity() != null) {
            tvLevel.setText(GetConstDataUtils.getWorkingLevelList().get(bean.getVerifyEntity().getWorkingLevel()));
            tvYear.setText(GetConstDataUtils.getWorkingYearList().get(bean.getVerifyEntity().getWorkingYear()));
        }
        // 技师编号
        tvCode.setText(v(() -> bean.getWorkerNumber()) + "");
        // 口碑
        tvMouthGrade.setText(v(() -> bean.getPublicPraise() + "分"));
        // 好评率
        tvGoodGrade.setText(v(() -> bean.getGoodRate() + "%"));
        arcprogressview.setProgress(bean.getGoodRate() + 20);
        // 资质  0否，1是
        if (v(() -> bean.getQualification()) != null && bean.getQualification() == 0) {
            tvWorkerQualification.setVisibility(View.GONE);
        } else if (v(() -> bean.getQualification()) != null && bean.getQualification() == 1) {
            tvWorkerQualification.setVisibility(View.VISIBLE);
        }
        //  培训状态 （0否，1是）
        if (v(() -> bean.getTrainStatus()) != null && bean.getTrainStatus() == 0) {
            tvWorkerTrain.setVisibility(View.GONE);
        } else if (v(() -> bean.getTrainStatus()) != null && bean.getTrainStatus() == 1) {
            tvWorkerTrain.setVisibility(View.VISIBLE);
        }
        if (bean.getGoodRate() != 0) {
            tvHaopinglv.setText(bean.getGoodRate() + "%");
            ivHaopinglv.setProgress(bean.getGoodRate());
        }
        // 设计订单
        if (v(() -> bean.getDesignNum()) != null) {
            tvDesignOrder.setText(bean.getDesignNum() + "");
        }
        // 维修订单
        if (v(() -> bean.getRepairCount()) != null) {
            tvRepairOrder.setText(v(() -> bean.getRepairCount()) + "");
        }
        // 施工订单
        if (v(() -> bean.getInstallNum()) != null) {
            tvWorkOrder.setText(v(() -> bean.getInstallNum()) + "");
        }
        // 评价订单
        if (v(() -> bean.getEvaluateNum()) != null) {
            tvEvaluteOrder.setText(v(() -> bean.getEvaluateNum()) + "");
        }
        rbStar1.setRating(bean.getItem1());
        rbStar2.setRating(bean.getItem2());
        rbStar3.setRating(bean.getItem3());
        rbStar4.setRating(bean.getItem4());
        rbStar5.setRating(bean.getItem5());

        // 服务区域
        mDataList1 = new ArrayList<>();
        new Thread(() -> {
            if (bean.getRegionList() != null && !bean.getRegionList().isEmpty()) {
                mDataList1.addAll(Stream.of(bean.getRegionList()).map(regionId -> Config.get().getAddressById(regionId)).toList());
            }
        }).start();

        // 服务类型
        mDataList2 = new ArrayList<>();
        mDataList2.clear();
        List<Integer> serviceList = bean.getServiceList();
        if (serviceList != null && !serviceList.isEmpty()) {
            mDataList2.addAll(Stream.of(serviceList).map(id -> Config.get().getServiceNameById(id)).toList());
            if (mDataList2.size() <= 4) {
                addView(WorkerDetailActivity.this, rgType, mDataList2);
            } else {
                mTypeSmall.add(mDataList2.get(0));
                mTypeSmall.add(mDataList2.get(1));
                mTypeSmall.add(mDataList2.get(2));
                mTypeSmall.add(mDataList2.get(3));
                addView(WorkerDetailActivity.this, rgType, mTypeSmall);
            }
//
        }

        // 业务类型
        List<Integer> businessType = bean.getBusinessList();
        mDataList3 = new ArrayList<>();
        mDataList3.clear();
        if (businessType != null && !businessType.isEmpty()) {
            mDataList3.addAll(Stream.of(Config.get().getBusinessList(1)).filter(bus -> businessType.contains(bus.getDataId())).map(bus -> Config.get().getBusinessNameById(bus.getDataId())).toList());
            if (mDataList3.size() <= 4) {
                addView(WorkerDetailActivity.this, rgArea, mDataList3);
            } else {
                mAreaSmall.add(mDataList3.get(0));
                mAreaSmall.add(mDataList3.get(1));
                mAreaSmall.add(mDataList3.get(2));
                mAreaSmall.add(mDataList3.get(3));
                addView(WorkerDetailActivity.this, rgArea, mAreaSmall);
            }

        }

        initAdapter();
        initHonor(bean);
    }

    private void initAdapter() {

        evaluateAdapter1 = new WorkerDetailAdapter(R.layout.item_worker_detail1, new ArrayList());
        evaluateAdapter1.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);

        evaluateAdapter1.bindToRecyclerView(rvList1);

        rvList1.setNestedScrollingEnabled(false);

        findViewById(R.id.sv_worker_detail).setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                int width, height;
                Point p = new Point();
                getWindowManager().getDefaultDisplay().getSize(p);
                width = p.x;
                height = p.y;
                Rect rect = new Rect(0, 0, width, height);
                //如果荣誉证书在屏幕可见范围
                if (ivPic1.getLocalVisibleRect(rect)) {
                    //并且 服务区域是打开状态
                    if (rvList1.getVisibility() == View.VISIBLE) {
                        //加载服务区域
                        fillRegionData();
                    }
                }
            }
            return false;
        });
    }

    private void fillRegionData() {
        List<String> dataList = Stream.of(mDataList1).filter(data -> !evaluateAdapter1.getData().contains(data)).limit(regionSize).toList();
        evaluateAdapter1.addData(dataList);
        evaluateAdapter1.loadMoreComplete();
        if (dataList.size() < regionSize) {
            evaluateAdapter1.loadMoreEnd();
        }

    }


    private void initHonor(WorkerEntity bean) {
        if (!StringUtils.isEmpty(bean.getVerifyEntity().getHonorPics())) {
            String[] urls = bean.getVerifyEntity().getHonorPics().split(",");

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
                ivPic3.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + urls[2]));
                ivPic3.setVisibility(View.VISIBLE);
            } else {
                ivPic3.setVisibility(View.GONE);
            }
            if (urls.length >= 4) {
                ivPic4.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + urls[3]));
                ivPic4.setVisibility(View.VISIBLE);
            } else {
                ivPic4.setVisibility(View.GONE);
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public static void addView(final Context context, CustomRadioGroup
            parent, List<String> list) {
        parent.removeAllViews();
        LayoutParams pa = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < list.size(); i++) {
            final TextView textView = new TextView(context);
            pa.setMargins(22, 22, 22, 30);
            textView.setLayoutParams(pa);
            textView.setText(list.get(i));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(12);
            textView.setPadding(20, 20, 20, 20);
            textView.setTextColor(Color.parseColor("#666666"));
            textView.setBackgroundResource(R.drawable.bg_client_worker_detail_type);
            parent.addView(textView);
        }
    }

    public static void addViewTwo(final Context context, CustomRadioGroup parent) {
        parent.removeAllViews();
        LayoutParams pa = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < 12; i++) {
            final TextView textView = new TextView(context);
            pa.setMargins(30, 12, 30, 12);
            textView.setLayoutParams(pa);
            textView.setText("发的");
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(12);
            textView.setPadding(30, 20, 30, 20);
            textView.setTextColor(Color.parseColor("#666666"));
            textView.setBackgroundResource(R.drawable.bg_client_worker_detail_type);
            parent.addView(textView);
        }
    }
}
