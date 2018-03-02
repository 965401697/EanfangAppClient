package net.eanfang.worker.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.MineTaskListBean;
import com.eanfang.swipefresh.SwipyRefreshLayout;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.CallUtils;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.MineTakePublishListActivity;
import net.eanfang.worker.ui.adapter.PublishTakeListAdapter;
import net.eanfang.worker.ui.interfaces.OnDataReceivedListener;
import net.eanfang.worker.ui.widget.TaskPublishDetailView;

import java.util.List;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;


/**
 * Created by Mr.hou
 *
 * @on 2017/9/5  9:29
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class MineTakePublishListFragment extends BaseFragment implements
        OnDataReceivedListener, SwipyRefreshLayout.OnRefreshListener {
    TextView tvNoDatas;
    RecyclerView rvList;
    SwipyRefreshLayout swiprefresh;
    PublishTakeListAdapter adapter;
    private List<MineTaskListBean.ListBean> mDataList;
    private String mTitle;
    private int mType;

    private static int page = 1;

    public static MineTakePublishListFragment getInstance(String title, int type) {
        MineTakePublishListFragment sf = new MineTakePublishListFragment();
        sf.mTitle = title;
        page = 1;
        sf.mType = type;
        return sf;

    }

    public String getmTitle() {
        return mTitle;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_work_report_list;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        tvNoDatas = (TextView) findViewById(R.id.tv_no_datas);
        swiprefresh = (SwipyRefreshLayout) findViewById(R.id.swiprefresh);
        swiprefresh.setOnRefreshListener(this);
        rvList = (RecyclerView) findViewById(R.id.rv_list);
    }

    @Override
    protected void setListener() {
    }

    private void initAdapter() {
        mDataList = ((MineTakePublishListActivity) getActivity()).getWorkReportListBean().getList();
        adapter = new PublishTakeListAdapter(mDataList);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                new TaskPublishDetailView(getActivity(), true, mDataList.get(position), false).show();
            }
        });
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_do_first:
                    switch (mDataList.get(position).getStatus()) {
                        //关闭任务
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            CallUtils.call(getContext(), mDataList.get(position).getContactsPhone());
                            break;

                        default:
                            break;
                    }
                    break;
                case R.id.tv_do_second:
                    switch (mDataList.get(position).getStatus()) {
                        //查看申请
                        case 0:
                        case 1:
                        case 3:
                        case 4:
                            new TaskPublishDetailView(getActivity(), true, mDataList.get(position), false).show();
                            break;
                        //确认验收
                        case 2:
                            acceptance(mDataList.get(position).getId(), mDataList.get(position).getCreateUserId(),
                                    mDataList.get(position).getShopTaskApplyId(), mDataList.get(position).getContactsPhone());
                            break;


                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        });
        if (mDataList.size() > 0) {
            rvList.setAdapter(adapter);
            tvNoDatas.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        } else {
            tvNoDatas.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }


    /**
     * 确认验收
     */
    private void acceptance(Long id, Long createUserId, Long shopTaskApplyId, String contactsPhone) {
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("createUserId", createUserId);
        object.put("shopTaskApplyId", shopTaskApplyId);
        object.put("contactsPhone", contactsPhone);
        String json = JSONObject.toJSONString(object);

        EanfangHttp.post(NewApiService.TAKE_APPLY_LIST_CHECK)
                .upJson(json)
                .execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class, (bean) -> {
                    showToast("申请验收");
                }));
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh(int index) {
        dataOption(TOP_REFRESH);

    }

    @Override
    public void onLoad(int index) {
        dataOption(BOTTOM_REFRESH);
    }

    private void dataOption(int option) {
        switch (option) {
            case TOP_REFRESH:
                //下拉刷新
                page--;
                if (page <= 0) {
                    page = 1;
                }
                getData();
                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
                page++;
                getData();
                break;
            default:
                break;
        }
    }

    /**
     * 获取发包列表
     */
    private void getData() {
        int status = GetConstDataUtils.getTaskPublishStatus().indexOf(getmTitle());

        QueryEntry queryEntry = new QueryEntry();
        if (Constant.CREATE_DATA_CODE == (mType)) {
            queryEntry.getEquals().put(Constant.ASSIGNEE_USER_ID, EanfangApplication.getApplication().getUserId() + "");
        } else if (Constant.ASSIGNEE_DATA_CODE == (mType)) {
            queryEntry.getEquals().put("assigneeCompanyId", EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyId() + "");
        }
        queryEntry.getEquals().put(Constant.STATUS, status + "");

        queryEntry.setPage(page);
        queryEntry.setSize(5);

        EanfangHttp.post(NewApiService.TASK_APPLY_PUBLISH_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<MineTaskListBean>(getActivity(), true, MineTaskListBean.class, (bean) -> {
                            getActivity().runOnUiThread(() -> {
                                ((MineTakePublishListActivity) getActivity()).setWorkReportListBean(bean);
                                onDataReceived();
                            });
                        })
                );
    }


    @Override
    public void onDataReceived() {
        initView();
        initAdapter();
        swiprefresh.setRefreshing(false);
    }
}
