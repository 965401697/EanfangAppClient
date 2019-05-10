package net.eanfang.client.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkspaceInstallBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.install.InstallOrderDetailActivity;
import net.eanfang.client.ui.adapter.WorkspaceInstallAdapter;


/**
 * Created by MrHou
 *
 * @on 2017/12/21  14:12
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkInstallListFragment extends TemplateItemListFragment {

    private String mTitle;
    private int mType;
    private WorkspaceInstallAdapter mAdapter;

    public static WorkInstallListFragment getInstance(String title, int type) {
        WorkInstallListFragment sf = new WorkInstallListFragment();
        sf.mTitle = title;
        sf.mType = type;
        return sf;

    }

    public String getmTitle() {
        return mTitle;
    }

    @Override
    protected void initAdapter() {

        mAdapter = new WorkspaceInstallAdapter();
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {


            WorkspaceInstallBean.ListBean bean = (WorkspaceInstallBean.ListBean) adapter.getData().get(position);
            switch (view.getId()) {
                case R.id.tv_look:
                    if (PermKit.get().getInstallDetailPrem()) {
                        Bundle bundle = new Bundle();
                        //刷新已读未读
                        ((WorkspaceInstallBean.ListBean) adapter.getData().get(position)).setNewOrder(0);
                        adapter.notifyItemChanged(position);
                        bundle.putLong("orderId", bean.getId());
                        JumpItent.jump(getActivity(), InstallOrderDetailActivity.class, bundle);
                    }
                    break;
                /**
                 * 完工
                 * */
                case R.id.tv_finish:
                    if (bean.getStatus() == 2) {
                        if (PermKit.get().getInstallFinishPrem()) {
                            finishWork(String.valueOf(bean.getId()), position);
                        }
                    }
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    protected void getData() {
        String status = null;
        if (!"全部".equals(mTitle)) {
            status = GetConstDataUtils.getInstallStatus().indexOf(getmTitle()) + "";
        }

        QueryEntry queryEntry = new QueryEntry();
        if ("0".equals(mType)) {
            queryEntry.getEquals().put("ownerCompanyId", EanfangApplication.getApplication().getCompanyId() + "");
        } else if ("1".equals(mType)) {
            queryEntry.getEquals().put("createUserId", EanfangApplication.getApplication().getUserId() + "");
        }
        if (!mTitle.equals("全部")) {
            queryEntry.getEquals().put("status", status);
        }

        queryEntry.setPage(mPage);
        queryEntry.setSize(10);

        EanfangHttp.post(NewApiService.GET_WORK_INSTALL_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkspaceInstallBean>(getActivity(), true, WorkspaceInstallBean.class) {

                    @Override
                    public void onSuccess(WorkspaceInstallBean bean) {

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

    private void finishWork(String id, int position) {
        EanfangHttp.get(NewApiService.INSTALL_FINISH_WORK)
                .params("id", id)
                .execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class, (bean) -> {
                    showToast("成功");
                    mAdapter.remove(position);
                }));
    }

}
