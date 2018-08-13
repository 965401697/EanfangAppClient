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
import com.eanfang.model.WorkCheckListBean;
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

import static android.app.Activity.RESULT_OK;
import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;


/**
 * Created by Mr.hou
 *
 * @on 2017/9/5  9:29
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class MineTaskPublishListFragment extends TemplateItemListFragment {
    //    RecyclerView rvList;
//    SwipyRefreshLayout swiprefresh;
    PublishTaskListAdapter mAdapter;
    //    private List<MineTaskListBean.ListBean> mDataList;
    private String mTitle;
    private int mType;
    private int mCurrentPosition;
    private final int REQUEST_CODE = 101;
//    private static int page = 1;

    public static MineTaskPublishListFragment getInstance(String title, int type) {
        MineTaskPublishListFragment sf = new MineTaskPublishListFragment();
        sf.mTitle = title;
//        page = 1;
        sf.mType = type;
        return sf;

    }

    public String getmTitle() {
        return mTitle;
    }

//    @Override
//    protected int setLayoutResouceId() {
//        return R.layout.fragment_work_report_list;
//    }

//    @Override
//    protected void initData(Bundle arguments) {
//
//    }

//    @Override
//    protected void initView() {
//        swiprefresh = (SwipyRefreshLayout) findViewById(R.id.swiprefresh);
//        swiprefresh.setOnRefreshListener(this);
//        rvList = (RecyclerView) findViewById(R.id.rv_list);
//    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initAdapter() {

        mAdapter = new PublishTaskListAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                new TaskPublishDetailView(getActivity(), true, (MineTaskListBean.ListBean) adapter.getData().get(position), false).show();
            }
        });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {

            mCurrentPosition = position;
            MineTaskListBean.ListBean bean = (MineTaskListBean.ListBean) mAdapter.getData().get(position);
            switch (view.getId()) {
                case R.id.tv_do_first:
                    switch (bean.getStatus()) {
                        //关闭任务
                        case 0:
                            closeTask(bean.getId());
                            break;
                        //去支付
                        case 1:
                            showToast("去支付");
                            break;
                        //联系接包人
                        case 2:
                            CallUtils.call(getContext(), bean.getAssigneeUser().getAccountEntity().getMobile());
                            break;
                        //联系接包人
                        case 3:
                            CallUtils.call(getContext(), bean.getAssigneeUser().getAccountEntity().getMobile());
                            break;

                        default:
                            break;
                    }
                    break;
                case R.id.tv_do_second:
                    switch (bean.getStatus()) {
                        //查看申请列表
                        case 0:
                            startActivityForResult(new Intent(getActivity(), TaskPublishApplyListActivity.class)
                                    .putExtra("shopTaskPublishId", bean.getId()), REQUEST_CODE);
                            break;
                        //查看申请
                        case 1:
                            new TaskPubApplyListDetailView(getActivity(), true, bean.getShopTaskApplyId()).show();
                            break;
                        //查看申请
                        case 2:
                            new TaskPubApplyListDetailView(getActivity(), true, bean.getShopTaskApplyId()).show();
                            break;
                        //确认验收
                        case 3:
                            acceptance(bean.getId(), bean.getCreateUserId(),
                                    bean.getShopTaskApplyId(), bean.getCreateCompanyId());
                            break;
                        //查看申请
                        case 4:
                            if (bean.getPublishStatus() != 0) {
                                new TaskPubApplyListDetailView(getActivity(), true, bean.getShopTaskApplyId()).show();
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
                    mAdapter.remove(mCurrentPosition);
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
                    mAdapter.remove(mCurrentPosition);
                }));
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        getData();
//    }

    /**
     * 刷新
     */
//    @Override
//    public void onRefresh(int index) {
//        dataOption(TOP_REFRESH);
//
//    }

//    @Override
//    public void onLoad(int index) {
//        dataOption(BOTTOM_REFRESH);
//    }

//    private void dataOption(int option) {
//        switch (option) {
//            case TOP_REFRESH:
//                //下拉刷新
//                page--;
//                if (page <= 0) {
//                    page = 1;
//                }
//                getData();
//                break;
//            case BOTTOM_REFRESH:
//                //上拉加载更多
//                page++;
//                getData();
//                break;
//            default:
//                break;
//        }
//    }

    /**
     * 获取发包列表
     */
    @Override
    protected void getData() {
        int status = GetConstDataUtils.getTaskPublishStatus().indexOf(getmTitle());

        QueryEntry queryEntry = new QueryEntry();
        if (Constant.CREATE_DATA_CODE == (mType)) {
            queryEntry.getEquals().put("createUserId", EanfangApplication.getApplication().getUserId() + "");
        } else if (Constant.ASSIGNEE_DATA_CODE == (mType)) {
            queryEntry.getEquals().put("createCompanyId", EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyId() + "");
        }
        queryEntry.getEquals().put("status", status + "");

        queryEntry.setPage(mPage);
        queryEntry.setSize(10);

        EanfangHttp.post(NewApiService.TASK_PUBLISH_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<MineTaskListBean>(getActivity(), true, MineTaskListBean.class) {

                    @Override
                    public void onSuccess(MineTaskListBean bean) {

                        if (mPage == 1) {
                            mAdapter.getData().clear();
                            mAdapter.setNewData(bean.getList());
                            mSwipeRefreshLayout.setRefreshing(false);
                            mAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                mAdapter.loadMoreEnd();
                            }

                            if (bean.getList().size() > 0) {
                                mTvNoData.setVisibility(View.GONE);
                            } else {
                                mTvNoData.setVisibility(View.VISIBLE);
                            }


                        } else {
                            mAdapter.addData(bean.getList());
                            mAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                mAdapter.loadMoreEnd();
                            }
                        }

                    }

                    @Override
                    public void onNoData(String message) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.loadMoreEnd();//没有数据了
                        if (mAdapter.getData().size() == 0) {
                            mTvNoData.setVisibility(View.VISIBLE);
                        } else {
                            mTvNoData.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onCommitAgain() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE)
                mAdapter.remove(mCurrentPosition);
        }
    }


    //    @Override
//    public void onDataReceived() {
////        initView();
//        initAdapter();
//        swiprefresh.setRefreshing(false);
//    }

//    @Override
//    public boolean isFinishing() {
//        return false;
//    }
}
