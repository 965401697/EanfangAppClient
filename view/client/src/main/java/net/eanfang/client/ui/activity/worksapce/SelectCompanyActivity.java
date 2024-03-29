package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.bean.CompanyBean;
import com.eanfang.biz.model.entity.RepairOrderEntity;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.JumpItent;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivitySelectCompanyBinding;
import net.eanfang.client.ui.activity.worksapce.online.DividerItemDecoration;
import net.eanfang.client.ui.activity.worksapce.repair.QuickRepairActivity;
import net.eanfang.client.ui.activity.worksapce.repair.SecurityCompanyDetailActivity;
import net.eanfang.client.ui.adapter.repair.SelectCompanyAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;

/**
 * @author guanluocang
 * @data 2019/7/17
 * @description 选择安防公司
 */

public class SelectCompanyActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private SelectCompanyAdapter selectCompanyAdapter;
    private ActivitySelectCompanyBinding activitySelectCompanyBinding;

    private int page = 1;

    /**
     * title 文字
     */
    private List<TextView> mScreenTitleList = new ArrayList<>();
    /**
     * title 图片
     */
    private List<ImageView> mScreenIconList = new ArrayList<>();
    private boolean mScreenIconUp = true;
    private String mClickType = "mouth";

    /**
     * 排序 参数
     * 排序 值
     */
    private String mOrderByType = "";
    private String mOrderByValue = "";

    private int mAreaId;

    private RepairOrderEntity mRepairOrderEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activitySelectCompanyBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_company);
        super.onCreate(savedInstanceState);
        initData("", "");
        initListener();
    }

    @Override
    protected void initView() {
        setTitle("找安防公司");
        setLeftBack(true);
        selectCompanyAdapter = new SelectCompanyAdapter();
        activitySelectCompanyBinding.rvCompany.setLayoutManager(new LinearLayoutManager(this));
        activitySelectCompanyBinding.rvCompany.addItemDecoration(new DividerItemDecoration(getContext()));
        activitySelectCompanyBinding.swipeFresh.setOnRefreshListener(this);
        selectCompanyAdapter.bindToRecyclerView(activitySelectCompanyBinding.rvCompany);
        selectCompanyAdapter.setOnLoadMoreListener(this, activitySelectCompanyBinding.rvCompany);
        mScreenTitleList.add(activitySelectCompanyBinding.include.tvMouth);
        mScreenTitleList.add(activitySelectCompanyBinding.include.tvPraise);
        mScreenTitleList.add(activitySelectCompanyBinding.include.tvRepair);
        mScreenTitleList.add(activitySelectCompanyBinding.include.tvConstruction);
        mScreenTitleList.add(activitySelectCompanyBinding.include.tvHot);

        mScreenIconList.add(activitySelectCompanyBinding.include.ivHot);
        mScreenIconList.add(activitySelectCompanyBinding.include.ivMouth);
        mScreenIconList.add(activitySelectCompanyBinding.include.ivPraise);
        mScreenIconList.add(activitySelectCompanyBinding.include.ivRepair);
        mScreenIconList.add(activitySelectCompanyBinding.include.ivConstruction);

    }

    private void initData(String mOrderByType, String mOrderByValue) {
        mAreaId = getIntent().getIntExtra("areaId", 0);
        EanfangHttp.post(NewApiService.HOME_COMPANY_LIST)
                .params("page", page)
                .params("size", 10)
                .params("areaId", mAreaId)
                .params(mOrderByType, mOrderByValue)
                .execute(new EanfangCallback<CompanyBean>(this, true, CompanyBean.class) {
                    @Override
                    public void onSuccess(CompanyBean bean) {
                        if (page == 1) {
                            selectCompanyAdapter.getData().clear();
                            selectCompanyAdapter.setNewData(bean.getList());
                            activitySelectCompanyBinding.swipeFresh.setRefreshing(false);
                            selectCompanyAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                selectCompanyAdapter.loadMoreEnd();
                                //释放对象
                            }
                        } else {
                            selectCompanyAdapter.addData(bean.getList());
                            selectCompanyAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                selectCompanyAdapter.loadMoreEnd();
                            }
                        }

                    }

                    @Override
                    public void onNoData(String message) {
                        activitySelectCompanyBinding.swipeFresh.setRefreshing(false);
                        selectCompanyAdapter.loadMoreEnd();//没有数据了
                    }

                    @Override
                    public void onCommitAgain() {
                        activitySelectCompanyBinding.swipeFresh.setRefreshing(false);
                    }
                });
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    private void initListener() {
        // 热门排序
        activitySelectCompanyBinding.include.llHot.setOnClickListener((v) -> {
            doChangetState("hot");
        });
        //口碑
        activitySelectCompanyBinding.include.llMouth.setOnClickListener((v) -> {
            doChangetState("mouth");
        });
        // 评价
        activitySelectCompanyBinding.include.llPraise.setOnClickListener((v) -> {
            doChangetState("praise");
        });
        // 维修
        activitySelectCompanyBinding.include.llRepair.setOnClickListener((v) -> {
            doChangetState("repair");
        });
        // 施工
        activitySelectCompanyBinding.include.llConstruction.setOnClickListener((v) -> {
            doChangetState("construction");
        });
        selectCompanyAdapter.setOnItemChildClickListener(((adapter1, view, position) -> {
            switch (view.getId()) {
                case R.id.btn_home_company_install:
                    Bundle bundle_install = new Bundle();
                    bundle_install.putString("type", "install");
                    JumpItent.jump(SelectCompanyActivity.this, QuickRepairActivity.class, bundle_install);
                    break;
                case R.id.btn_home_company_repair:
                    mRepairOrderEntity = new RepairOrderEntity();
                    Bundle bundle_repair = new Bundle();
                    bundle_repair.putString("type", "repair");
                    mRepairOrderEntity.setAssigneeCompanyId(selectCompanyAdapter.getData().get(position).getOrgEntity().getCompanyId());
                    mRepairOrderEntity.setAssigneeTopCompanyId(selectCompanyAdapter.getData().get(position).getOrgEntity().getTopCompanyId());
                    mRepairOrderEntity.setAssigneeOrgCode(selectCompanyAdapter.getData().get(position).getOrgEntity().getOrgCode());
                    bundle_repair.putSerializable("mRepairOrderEntity", mRepairOrderEntity);
                    JumpItent.jump(SelectCompanyActivity.this, QuickRepairActivity.class, bundle_repair);
                    break;
                default:
                    break;
            }
        }));
        selectCompanyAdapter.setOnItemClickListener(((adapter1, view, position) -> {
            Bundle mBundleOrgDetail = new Bundle();
            mBundleOrgDetail.putString("mOrgId", String.valueOf(selectCompanyAdapter.getData().get(position).getOrgEntity().getOrgId()));
            JumpItent.jump(SelectCompanyActivity.this, SecurityCompanyDetailActivity.class, mBundleOrgDetail);
        }));
    }

    private void doChangetState(String mType) {
        switch (mType) {
            //热门
            case "hot":
                doUpdataTextColor("orderByOne", "hot", activitySelectCompanyBinding.include.tvHot, activitySelectCompanyBinding.include.ivHot);
                break;
            //口碑
            case "mouth":
                doUpdataTextColor("orderByTwo", "mouth", activitySelectCompanyBinding.include.tvMouth, activitySelectCompanyBinding.include.ivMouth);
                break;
            // 评价
            case "praise":
                doUpdataTextColor("orderByThree", "praise", activitySelectCompanyBinding.include.tvPraise, activitySelectCompanyBinding.include.ivPraise);
                break;
            // 维修
            case "repair":
                doUpdataTextColor("orderByFour", "repair", activitySelectCompanyBinding.include.tvRepair, activitySelectCompanyBinding.include.ivRepair);
                break;
            // 施工
            case "construction":
                doUpdataTextColor("orderByFive", "construction", activitySelectCompanyBinding.include.tvConstruction, activitySelectCompanyBinding.include.ivConstruction);
                break;
            default:
                break;
        }
    }

    /**
     * @param orderByType 接口排序条件 two three four five
     * @param clickType   点击类型 口碑 评价 报修 施工
     * @param textView
     * @param imageView
     */
    private void doUpdataTextColor(String orderByType, String clickType, TextView textView, ImageView imageView) {
        mOrderByType = orderByType;
        page = 1;
        // 判断文字
        for (TextView mTextViews : mScreenTitleList) {
            // 变黄
            if (textView.equals(mTextViews)) {
                mTextViews.setTextColor(ContextCompat.getColor(this, R.color.color_home_company_install_bg));
            } else {
                // 变黑
                mTextViews.setTextColor(ContextCompat.getColor(this, R.color.color_client_neworder));
            }
        }
        //判断图片
        for (ImageView mImageViews : mScreenIconList) {
            // 热门 desc
            if (imageView.equals(activitySelectCompanyBinding.include.ivHot)) {
                initData(mOrderByType, "desc");
                mImageViews.setImageResource(R.mipmap.ic_client_screen_none);
            } else {
                if (mImageViews.equals(imageView)) {
                    if (clickType.equals(mClickType)) {
                        if (mScreenIconUp) {
                            mScreenIconUp = false;
                            mOrderByValue = "asc";
                            mImageViews.setImageResource(R.mipmap.ic_client_screen_up);
                        } else {
                            mScreenIconUp = true;
                            mOrderByValue = "desc";
                            mImageViews.setImageResource(R.mipmap.ic_client_screen_down);
                        }
                    } else {
                        mClickType = clickType;
                        mScreenIconUp = false;
                        mOrderByValue = "asc";
                        mImageViews.setImageResource(R.mipmap.ic_client_screen_up);
                    }
                    initData(mOrderByType, mOrderByValue);
                } else {
                    mImageViews.setImageResource(R.mipmap.ic_client_screen_none);
                }
            }

        }

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        dataOption(TOP_REFRESH);
    }


    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        dataOption(BOTTOM_REFRESH);
    }

    private void dataOption(int option) {
        switch (option) {
            case TOP_REFRESH:
                //下拉刷新
                page = 1;
                initData(mOrderByType, mOrderByValue);
                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
                page++;
                initData(mOrderByType, mOrderByValue);
                break;
            default:
                break;
        }
    }
}
