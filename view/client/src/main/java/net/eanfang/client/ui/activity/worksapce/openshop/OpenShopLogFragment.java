package net.eanfang.client.ui.activity.worksapce.openshop;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.OpenShopLogBean;
import com.eanfang.util.CallUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.OpenShopLogEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.fragment.TemplateItemListFragment;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by O u r on 2018/5/22.
 */

public class OpenShopLogFragment extends TemplateItemListFragment {

    private String mTitle;
    private int mType;
    private OpenShopLogAdapter mAdapter;

    private QueryEntry mQueryEntry;

    public static final int DETAIL_TASK_REQUSET_COOD = 9;

    private OpenShopLogEntity mDetailTaskBean;
    private int mPosition;

    public static OpenShopLogFragment getInstance(String title, int type) {
        OpenShopLogFragment f = new OpenShopLogFragment();
        f.mTitle = title;
        f.mType = type;
        return f;

    }

    public String getmTitle() {
        return mTitle;
    }

    @Override
    protected void initAdapter() {
        mAdapter = new OpenShopLogAdapter(mType, R.layout.item_open_shop);

        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (!PermKit.get().getOpenShopDetailPrem()) {
                    return;
                }


                mDetailTaskBean = ((OpenShopLogEntity) adapter.getData().get(position));
                mPosition = position;
                Bundle bundle = new Bundle();
                bundle.putBoolean("isVisible", false);
                bundle.putString("id", String.valueOf(((OpenShopLogEntity) adapter.getData().get(position)).getId()));
                JumpItent.jump(getActivity(), OpenShopLogDetailActivity.class, bundle, DETAIL_TASK_REQUSET_COOD);
                //刷新数据

//                if (getmTitle().equals("未读日志")) {
//                    if (String.valueOf(bean.getAccId()).equals(String.valueOf(EanfangApplication.get().getAccId())))
//                        adapter.remove(position);
//                } else if (getmTitle().equals("全部日志")) {
//                    if (String.valueOf(bean.getAccId()).equals(String.valueOf(EanfangApplication.get().getAccId()))) {
//                        ((OpenShopLogEntity) adapter.getData().get(position)).setStatus(1);
//                        adapter.notifyItemChanged(position);
//                    }
//                }

            }
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_do_first:
                    if (mType == 1) {
                        CallUtils.call(getActivity(), ((OpenShopLogEntity) adapter.getData().get(position)).getAssigneeUser().getAccountEntity().getMobile());
                    } else {
                        CallUtils.call(getActivity(), ((OpenShopLogEntity) adapter.getData().get(position)).getOwnerUser().getAccountEntity().getMobile());
                    }

                    break;
                case R.id.tv_detail:
                    //刷新数据
                    mDetailTaskBean = ((OpenShopLogEntity) adapter.getData().get(position));
                    mPosition = position;
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(((OpenShopLogEntity) adapter.getData().get(position)).getId()));
                    JumpItent.jump(getActivity(), OpenShopLogDetailActivity.class, bundle, DETAIL_TASK_REQUSET_COOD);

//                    if (getmTitle().equals("未读日志")) {
//                        if (String.valueOf(bean.getAccId()).equals(String.valueOf(EanfangApplication.get().getAccId())))
//                            adapter.remove(position);
//                    } else if (getmTitle().equals("全部日志")) {
//                        if (String.valueOf(bean.getAccId()).equals(String.valueOf(EanfangApplication.get().getAccId()))) {
//                            ((OpenShopLogEntity) adapter.getData().get(position)).setStatus(1);
//                            adapter.notifyItemChanged(position);
//                        }
//                    }
                    break;
                default:
                    break;
            }
        });

    }

    public void getTaskData(QueryEntry queryEntry) {
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


    @Override
    protected void getData() {
        if (mQueryEntry == null) {
            mQueryEntry = new QueryEntry();
        }

        if (mType == 1) {
            mQueryEntry.getEquals().put("ownerUserId", EanfangApplication.getApplication().getUserId() + "");
        } else if (mType == 2) {
            mQueryEntry.getEquals().put("assigneeUserId", EanfangApplication.getApplication().getUserId() + "");
        }

//        if (getmTitle().equals("未读日志")) {
//            mQueryEntry.getEquals().put(Constant.STATUS, "0");
//        } else if (getmTitle().equals("已读日志")) {
//            mQueryEntry.getEquals().put(Constant.STATUS, "1");
//        }

        mQueryEntry.setSize(10);
        mQueryEntry.setPage(mPage);

        EanfangHttp.post(NewApiService.OA_OPEN_SHOP_LIST)
                .upJson(JsonUtils.obj2String(mQueryEntry))
                .execute(new EanfangCallback<OpenShopLogBean>(getActivity(), true, OpenShopLogBean.class) {

                    @Override
                    public void onSuccess(OpenShopLogBean bean) {

                        if (mPage == 1) {
                            mAdapter.getData().clear();
                            mAdapter.setNewData(bean.getList());
                            mSwipeRefreshLayout.setRefreshing(false);
                            mAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                mAdapter.loadMoreEnd();
                                mQueryEntry = null;
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

    /**
     * 刷新已读未读的状态
     */
    public void refreshStatus() {
        if (mDetailTaskBean != null) {
            mDetailTaskBean.setNewOrder(0);
            mAdapter.notifyItemChanged(mPosition);
        }
    }

    @Subscribe
    public void onEvent(String createSuccess) {
        if (createSuccess.equals("addOpenShopSuccess")) {
            mQueryEntry = null;
            mPage = 1;
            getData();
        }
    }

}
