package net.eanfang.worker.ui.activity.worksapce.repair;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.FaultListBean;
import com.eanfang.swipefresh.SwipyRefreshLayout;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.ToastUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.online.ExpertListActivity;
import net.eanfang.worker.ui.adapter.repair.FaultLibraryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;

/**
 * @author Guanluocang
 * @date on 2018/6/8  16:37
 * @decision 故障库
 * 已提取故障库相关内容
 */
public class FaultLibraryActivity extends BaseActivity implements SwipyRefreshLayout.OnRefreshListener, View.OnClickListener {
    private static final int RESULT_DATACODE = 2000;
    @BindView(R.id.rv_faultList)
    RecyclerView rvFaultList;
    @BindView(R.id.swiprefresh)
    SwipyRefreshLayout swiprefresh;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_go)
    TextView tvGo;
    //搜索状态不让用户上拉
    private boolean mFlag = false;
    private int i = 1;
    private FaultLibraryAdapter faultLibraryAdapter;
    private List<FaultListBean.ListBean> mFaultListBeanList = new ArrayList<>();
    // 系统类别id
    private String businessOneCode = "";

    private static int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fault_library);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        if (getIntent().getIntExtra("GZK", 0) == 3) {
            initView();
            initData();

            faultLibraryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    View viewByPosition = faultLibraryAdapter.getViewByPosition(position, R.id.check_true);
                    i++;
                    if (i % 2 == 1) {
                        viewByPosition.setVisibility(View.VISIBLE);
                    } else {
                        viewByPosition.setVisibility(View.GONE);
                    }
                }
            });
            tvGo.setOnClickListener(this);
        } else {
            tvGo.setVisibility(View.GONE);
            initView();
            initData();
            initListener();
        }


    }

    private void initView() {
        setLeftBack();
        if (getIntent().getIntExtra("GZK", 0) == 3) {
            setTitle("选择故障类型");
        } else {
            setTitle("故障库");
        }
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
                .execute(new EanfangCallback<FaultListBean>(FaultLibraryActivity.this, false, FaultListBean.class, (FaultListBean bean) -> {
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
                intent.putExtra("deviceFailureId", ((FaultListBean.ListBean) adapter.getData().get(position)).getId());
                intent.putExtra("failureTypeId", ((FaultListBean.ListBean) adapter.getData().get(position)).getFailureTypeId());
                setResult(RESULT_DATACODE, intent);
                finishSelf();
            }
        });

    }

    @Override
    public void onRefresh(int index) {
        if (!TextUtils.isEmpty(etSearch.getText().toString().trim())) {
            etSearch.setText("");
        }
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

    @Override
    public void onClick(View v) {
        Intent in = new Intent(FaultLibraryActivity.this, ExpertListActivity.class);
        startActivity(in);
    }
}
