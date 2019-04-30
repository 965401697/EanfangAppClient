package net.eanfang.worker.ui.fragment.tender;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.IfbOrderEntity;
import com.eanfang.model.TenderBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.ui.activity.worksapce.tender.WorkTenderDetailActivity;
import net.eanfang.worker.ui.adapter.worktender.WorkTenderAdapter;
import net.eanfang.worker.ui.fragment.TemplateItemListFragment;

import org.greenrobot.eventbus.Subscribe;

public class WorkTenderFragment extends TemplateItemListFragment {


    private String mTitle;
    private int mType;
    private QueryEntry mQueryEntry;
    //点击详情的返回刷新的code
    public static final int DETAILL_REQUEST_CODE = 2002;


    private WorkTenderAdapter workTenderAdapter;

    public static WorkTenderFragment getInstance(String title, int type) {
        WorkTenderFragment sf = new WorkTenderFragment();
        sf.mTitle = title;
        sf.mType = type;
        return sf;

    }

    public String getmTitle() {
        return mTitle;
    }


    @Override
    protected void initAdapter() {
        workTenderAdapter = new WorkTenderAdapter();
        workTenderAdapter.bindToRecyclerView(mRecyclerView);
        workTenderAdapter.setOnLoadMoreListener(this);
        workTenderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {


            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WorkTenderDetailActivity.class);
                intent.putExtra("id", ((IfbOrderEntity) adapter.getData().get(position)).getId());
                getActivity().startActivityForResult(intent, DETAILL_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void getData() {
        if (mQueryEntry == null) {
            mQueryEntry = new QueryEntry();
        }
        //正在公告
        if (mType == 0) {
            mQueryEntry.getEquals().put("status", 0 + "");
            // 已过期
        } else if (mType == 1) {
            mQueryEntry.getEquals().put("status", 1 + "");
        } else if (mType == 2) {
            mQueryEntry.getEquals().put("status", "");
        }
        mQueryEntry.setPage(mPage);
        mQueryEntry.setSize(10);

        EanfangHttp.post(NewApiService.TENDER_LIST)
                .upJson(JsonUtils.obj2String(mQueryEntry))
                .execute(new EanfangCallback<TenderBean>(getActivity(), true, TenderBean.class) {

                    @Override
                    public void onSuccess(TenderBean bean) {


                        if (mPage == 1) {
                            workTenderAdapter.getData().clear();
                            workTenderAdapter.setNewData(bean.getList());
                            mSwipeRefreshLayout.setRefreshing(false);
                            workTenderAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                workTenderAdapter.loadMoreEnd();
                                //释放对象
                                mQueryEntry = null;
                            }

                            if (bean.getList().size() > 0) {
                                mTvNoData.setVisibility(View.GONE);
                            } else {
                                mTvNoData.setVisibility(View.VISIBLE);
                            }


                        } else {
                            workTenderAdapter.addData(bean.getList());
                            workTenderAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                workTenderAdapter.loadMoreEnd();
                            }
                        }

                    }

                    @Override
                    public void onNoData(String message) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        workTenderAdapter.loadMoreEnd();//没有数据了
                        if (workTenderAdapter.getData().size() == 0) {
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

    public void getTenderData(QueryEntry queryEntry) {
        this.mQueryEntry = queryEntry;
        mPage = 1;
        getData();
    }


    @Override
    public void onRefresh() {
        mQueryEntry = null;
        mPage = 1;
        getData();
    }

    /**
     * 刷新已读未读的状态
     */
    public void refreshStatus() {
//        if (ifbOrderEntity != null) {
//            ifbOrderEntity.setNewOrder(0);
//            workTenderAdapter.notifyItemChanged(mPosition);
//        }
    }

    @Subscribe
    public void onEvent(String createSuccess) {
        if (createSuccess.equals("addTenderSuccess")) {
            mQueryEntry = null;
            mPage = 1;
            getData();
        }
    }
}
