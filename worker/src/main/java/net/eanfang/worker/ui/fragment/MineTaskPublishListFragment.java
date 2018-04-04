package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.View;

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
import net.eanfang.worker.ui.activity.worksapce.MineTaskPublishListActivity;
import net.eanfang.worker.ui.activity.worksapce.TaskPublishApplyListActivity;
import net.eanfang.worker.ui.adapter.PublishTaskListAdapter;
import net.eanfang.worker.ui.interfaces.OnDataReceivedListener;
import net.eanfang.worker.ui.widget.TaskPubApplyListDetailView;
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

public class MineTaskPublishListFragment extends BaseFragment implements
        OnDataReceivedListener, SwipyRefreshLayout.OnRefreshListener {
    RecyclerView rvList;
    SwipyRefreshLayout swiprefresh;
    PublishTaskListAdapter adapter;
    private List<MineTaskListBean.ListBean> mDataList;
    private String mTitle;
    private int mType;

    private static int page = 1;

    public static MineTaskPublishListFragment getInstance(String title, int type) {
        MineTaskPublishListFragment sf = new MineTaskPublishListFragment();
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
        swiprefresh = (SwipyRefreshLayout) findViewById(R.id.swiprefresh);
        swiprefresh.setOnRefreshListener(this);
        rvList = (RecyclerView) findViewById(R.id.rv_list);
    }

    @Override
    protected void setListener() {
    }

    private void initAdapter() {
        mDataList = ((MineTaskPublishListActivity) getActivity()).getWorkReportListBean().getList();
        adapter = new PublishTaskListAdapter(mDataList);
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
                            closeTask(mDataList.get(position).getId());
                            break;
                        //去支付
                        case 1:
                            showToast("去支付");
                            break;
                        //联系接包人
                        case 2:
                            CallUtils.call(getContext(), mDataList.get(position).getAssigneeUser().getAccountEntity().getMobile());
                            break;
                        //联系接包人
                        case 3:
                            CallUtils.call(getContext(), mDataList.get(position).getAssigneeUser().getAccountEntity().getMobile());
                            break;

                        default:
                            break;
                    }
                    break;
                case R.id.tv_do_second:
                    switch (mDataList.get(position).getStatus()) {
                        //查看申请列表
                        case 0:
                            startActivity(new Intent(getActivity(), TaskPublishApplyListActivity.class)
                                    .putExtra("shopTaskPublishId", mDataList.get(position).getId()));
                            break;
                        //查看申请
                        case 1:
                            new TaskPubApplyListDetailView(getActivity(), true, mDataList.get(position).getShopTaskApplyId()).show();
                            break;
                        //查看申请
                        case 2:
                            new TaskPubApplyListDetailView(getActivity(), true, mDataList.get(position).getShopTaskApplyId()).show();
                            break;
                        //确认验收
                        case 3:
                            acceptance(mDataList.get(position).getId(), mDataList.get(position).getCreateUserId(),
                                    mDataList.get(position).getShopTaskApplyId(), mDataList.get(position).getCreateCompanyId());
                            break;
                        //查看申请
                        case 4:
                            if (mDataList.get(position).getPublishStatus() != 0) {
                                new TaskPubApplyListDetailView(getActivity(), true, mDataList.get(position).getShopTaskApplyId()).show();
                            } else {
                                showToast("没有数据");
                            }
                            break;

                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        });
        rvList.setAdapter(adapter);

    }

    /**
     * 关闭任务
     */
    private void closeTask(Long id) {
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("publishStatus", 0);
        String json = JSONObject.toJSONString(object);
        EanfangHttp.post(NewApiService.TASK_PUBLISH_CLOSE)
                .upJson(json)
                .execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class, (bean) -> {
                    showToast("任务关闭");
                }));
    }

    /**
     * 确认验收
     */
    private void acceptance(Long id, Long createUserId, Long shopTaskApplyId, Long createCompanyId) {
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("createUserId", createUserId);
        object.put("shopTaskApplyId", shopTaskApplyId);
        object.put("createCompanyId", createCompanyId);
        String json = JSONObject.toJSONString(object);

        EanfangHttp.post(NewApiService.TASK_PUBLISH_FINISH)
                .upJson(json)
                .execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class, (bean) -> {
                    showToast("确认验收");
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
            queryEntry.getEquals().put("createUserId", EanfangApplication.getApplication().getUserId() + "");
        } else if (Constant.ASSIGNEE_DATA_CODE == (mType)) {
            queryEntry.getEquals().put("createCompanyId", EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyId() + "");
        }
        queryEntry.getEquals().put("status", status + "");

        queryEntry.setPage(page);
        queryEntry.setSize(5);

        EanfangHttp.post(NewApiService.TASK_PUBLISH_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<MineTaskListBean>(getActivity(), true, MineTaskListBean.class, (bean) -> {
                            getActivity().runOnUiThread(() -> {
                                ((MineTaskPublishListActivity) getActivity()).setWorkReportListBean(bean);
                                onDataReceived();
                            });
                        })
                );
    }


    @Override
    public void onDataReceived() {
//        initView();
        initAdapter();
        swiprefresh.setRefreshing(false);
    }

    @Override
    public boolean isFinishing() {
        return false;
    }
}
