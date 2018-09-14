package net.eanfang.worker.ui.fragment;

import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.MineTaskListBean;
import com.eanfang.util.CallUtils;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.PublishTakeListAdapter;
import net.eanfang.worker.ui.widget.TaskPublishDetailView;


/**
 * Created by Mr.hou
 *
 * @on 2017/9/5  9:29
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class MineTakePublishListFragment extends TemplateItemListFragment {
    //    RecyclerView rvList;
//    SwipyRefreshLayout swiprefresh;
    PublishTakeListAdapter mAdapter;
    //    private List<MineTaskListBean.ListBean> mDataList = new ArrayList<>();
    private String mTitle;
    private int mType;

    private int mCurrentPosition = 0;

//    private static int page = 1;

    public static MineTakePublishListFragment getInstance(String title, int type) {
        MineTakePublishListFragment sf = new MineTakePublishListFragment();
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

    //    @Override
//    protected void setListener() {
//    }
    @Override
    protected void initAdapter() {
//        if (V.v(() -> ((MineTakePublishListActivity) getActivity()).getWorkReportListBean().getList()) != null) {
//            mDataList = ((MineTakePublishListActivity) getActivity()).getWorkReportListBean().getList();
//        }
        mAdapter = new PublishTakeListAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);
//        rvList.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                new TaskPublishDetailView(getActivity(), true, (MineTaskListBean.ListBean) adapter.getData().get(position), false).show();
            }
        });

//        rvList.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                new TaskPublishDetailView(getActivity(), true, mDataList.get(position), false).show();
//            }
//        });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            MineTaskListBean.ListBean bean = (MineTaskListBean.ListBean) adapter.getData().get(position);
            switch (view.getId()) {
                case R.id.tv_do_first:
                    switch (bean.getStatus()) {
                        //关闭任务
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            CallUtils.call(getContext(), bean.getContactsPhone());
                            break;

                        default:
                            break;
                    }
                    break;
                case R.id.tv_do_second:
                    switch (bean.getStatus()) {
                        //查看申请
                        case 0:
                        case 1:
                        case 3:
                        case 4:
                            new TaskPublishDetailView(getActivity(), true, bean, false).show();
                            break;
                        //确认验收
                        case 2:
                            acceptance(bean.getId(), bean.getCreateUserId(),
                                    bean.getShopTaskApplyId(), bean.getContactsPhone());
                            break;


                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        });
//        rvList.setAdapter(adapter);
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
                    mAdapter.remove(mCurrentPosition);
                    showToast("申请验收");
                }));
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        getData();
//    }
//
//    /**
//     * 刷新
//     */
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
            queryEntry.getEquals().put(Constant.ASSIGNEE_USER_ID, EanfangApplication.getApplication().getUserId() + "");
        } else if (Constant.ASSIGNEE_DATA_CODE == (mType)) {
            queryEntry.getEquals().put("assigneeCompanyId", EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyId() + "");
        }
        if (getmTitle().equals("全部")) {

        } else {
            queryEntry.getEquals().put("status", status + "");
        }

        queryEntry.setPage(mPage);
        queryEntry.setSize(10);

        EanfangHttp.post(NewApiService.TASK_APPLY_PUBLISH_LIST)
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


//    @Override
//    public void onDataReceived() {
////        initView();
//        initAdapter();
//        swiprefresh.setRefreshing(false);
//    }
}
