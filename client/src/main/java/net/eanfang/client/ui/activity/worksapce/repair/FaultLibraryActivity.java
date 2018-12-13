package net.eanfang.client.ui.activity.worksapce.repair;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FaultListBean;
import com.eanfang.swipefresh.SwipyRefreshLayout;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.repair.FaultLibraryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;

/**
 * @author Guanluocang
 * @date on 2018/5/28  11:37
 * @decision 故障库
 */
public class FaultLibraryActivity extends BaseActivity implements SwipyRefreshLayout.OnRefreshListener {

    private static final int RESULT_DATACODE = 2000;
    @BindView(R.id.rv_faultList)
    RecyclerView rvFaultList;
    @BindView(R.id.swiprefresh)
    SwipyRefreshLayout swiprefresh;
    @BindView(R.id.et_search)
    EditText etSearch;
    //搜索状态不让用户上拉
    private boolean mFlag = false;

    private FaultLibraryAdapter faultLibraryAdapter;
    private List<FaultListBean.ListBean> mFaultListBeanList = new ArrayList<>();
    // 系统类别id
    private String businessOneCode = "";

    private static int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fault_library);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }


    private void initView() {
        setLeftBack();
        setTitle("故障库");
        page = 1;
        businessOneCode = getIntent().getStringExtra("businessOneCode");
        rvFaultList.setLayoutManager(new LinearLayoutManager(this));

        swiprefresh.setOnRefreshListener(this);

        faultLibraryAdapter = new FaultLibraryAdapter(R.layout.layout_fault_list_item);
        faultLibraryAdapter.setNewData(mFaultListBeanList);
        faultLibraryAdapter.bindToRecyclerView(rvFaultList);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    searchData(s.toString());
                    mFlag = true;
                } else {
                    onRefresh(1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("businessOneCode", businessOneCode);
        queryEntry.getEquals().put("headDeviceId", "");
        queryEntry.setSize(7);
        queryEntry.setPage(page);
        EanfangHttp.post(RepairApi.GET_FAULT_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<FaultListBean>(FaultLibraryActivity.this, true, FaultListBean.class, bean -> {
                    if (bean != null) {
                        if (page == 1) {
                            mFaultListBeanList.clear();
                            swiprefresh.setRefreshing(false);
                            mFaultListBeanList = bean.getList();
                            faultLibraryAdapter.setNewData(mFaultListBeanList);
                        } else {
                            swiprefresh.setRefreshing(false);
                            mFaultListBeanList = bean.getList();
                            faultLibraryAdapter.addData(mFaultListBeanList);
                        }

                    }

                }));
    }

    private void searchData(String description) {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("businessOneCode", businessOneCode);
        queryEntry.getEquals().put("headDeviceId", "");
        queryEntry.getLike().put("description", description);

        EanfangHttp.post(RepairApi.GET_FAULT_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<FaultListBean>(FaultLibraryActivity.this, false, FaultListBean.class, bean -> {
                    if (bean != null) {
                        if (bean.getList().size() == 0) {
                            ToastUtil.get().showToast(FaultLibraryActivity.this, "暂无数据");
                        }
                        mFaultListBeanList.clear();
                        swiprefresh.setRefreshing(false);
                        mFaultListBeanList = bean.getList();
                        faultLibraryAdapter.setNewData(mFaultListBeanList);

                    }

                }));
    }

    private void initListener() {

        faultLibraryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("sketch", ((FaultListBean.ListBean) adapter.getData().get(position)).getSketch());
                intent.putExtra("faultDes", ((FaultListBean.ListBean) adapter.getData().get(position)).getDescription());
                intent.putExtra("faultImgs", ((FaultListBean.ListBean) adapter.getData().get(position)).getPictures());
                intent.putExtra("datasId", ((FaultListBean.ListBean) adapter.getData().get(position)).getId());
                setResult(RESULT_DATACODE, intent);
                finishSelf();
            }
        });

//        rvFaultList.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Intent intent = new Intent();
//                intent.putExtra("faultDes", mFaultListBeanList.get(position).getDescription());
//                intent.putExtra("faultImgs", mFaultListBeanList.get(position).getPictures());
//                intent.putExtra("datasId", mFaultListBeanList.get(position).getId());
//                setResult(RESULT_DATACODE, intent);
//                finishSelf();
//            }
//        });
    }

    @Override
    public void onRefresh(int index) {
        mFlag = false;
        dataOption(TOP_REFRESH);
    }

    /**
     * 上拉加载更多
     */
    //上拉加载更多
//        dataOption(BOTTOM_REFRESH);
    @Override
    public void onLoad(int index) {
        if (!mFlag) {
            dataOption(BOTTOM_REFRESH);
        } else {
            swiprefresh.setRefreshing(false);
        }
    }

    private void dataOption(int option) {
        switch (option) {
            case TOP_REFRESH:
                //下拉刷新
                page = 1;
                initData();
                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
                page++;
                initData();
                break;
            default:
                break;
        }
    }
}
