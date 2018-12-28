package net.eanfang.client.ui.activity.worksapce.defendlog;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.DefendLogBean;
import com.eanfang.util.CallUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.ProtectionLogEntity;
import com.yaf.sys.entity.UserEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.fragment.TemplateItemListFragment;

/**
 * Created by O u r on 2018/5/22.
 */

public class DefendLogFragment extends TemplateItemListFragment {

    private String mTitle;
    private int mType;
    private DefendLogListAdapter mAdapter;

    private QueryEntry mQueryEntry;

    public static DefendLogFragment getInstance(String title, int type) {
        DefendLogFragment f = new DefendLogFragment();
        f.mTitle = title;
        f.mType = type;
        return f;

    }

    public String getmTitle() {
        return mTitle;
    }

    @Override
    protected void initAdapter() {
        mAdapter = new DefendLogListAdapter(mType, R.layout.item_open_shop);

        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (!PermKit.get().getProtectionDetailPrem()) return;
                UserEntity bean = ((ProtectionLogEntity) (adapter.getData().get(position))).getAssigneeUser();
                startActivity(new Intent(getActivity(), DefendLogDetailActivity.class).putExtra("id", String.valueOf(((ProtectionLogEntity) adapter.getData().get(position)).getId())).putExtra("isVisible", false));
                //刷新数据
//                if (getmTitle().equals("未读日志") && String.valueOf(EanfangApplication.get().getAccId()).equals(String.valueOf(bean.getAccId()))) {
//                    adapter.remove(position);
//                } else if (((ProtectionLogEntity) adapter.getData().get(position)).getStatus() == 0 && String.valueOf(EanfangApplication.get().getAccId()).equals(String.valueOf(bean.getAccId()))) {
//                    ((ProtectionLogEntity) adapter.getData().get(position)).setStatus(1);
//                    adapter.notifyItemChanged(position);
//                }
            }
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_do_first:
                    if (mType == 1) {
                        CallUtils.call(getActivity(), ((ProtectionLogEntity) adapter.getData().get(position)).getAssigneeUser().getAccountEntity().getMobile());
                    } else {
                        CallUtils.call(getActivity(), ((ProtectionLogEntity) adapter.getData().get(position)).getOwnerUser().getAccountEntity().getMobile());
                    }
                    break;
                case R.id.tv_detail:
                    //刷新数据

                    startActivity(new Intent(getActivity(), DefendLogDetailActivity.class).putExtra("id", String.valueOf(((ProtectionLogEntity) adapter.getData().get(position)).getId())));
                    UserEntity bean = ((ProtectionLogEntity) (adapter.getData().get(position))).getAssigneeUser();
                    //刷新数据
//                    if (getmTitle().equals("未读日志") && String.valueOf(EanfangApplication.get().getAccId()).equals(String.valueOf(bean.getAccId()))) {
//                        adapter.remove(position);
//                    } else if (((ProtectionLogEntity) adapter.getData().get(position)).getStatus() == 0 && String.valueOf(EanfangApplication.get().getAccId()).equals(String.valueOf(bean.getAccId()))) {
//                        ((ProtectionLogEntity) adapter.getData().get(position)).setStatus(1);
//                        adapter.notifyItemChanged(position);
//                    }
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

//    @Override
//    public void onResume() {
//        super.onResume();
//        mQueryEntry = null;
//        mPage = 1;
//        getData();
//    }


    @Override
    protected void getData() {
        if (mQueryEntry == null)
            mQueryEntry = new QueryEntry();

        if (mType == 1) {
            mQueryEntry.getEquals().put("ownerUserId", EanfangApplication.getApplication().getUserId() + "");
        } else if (mType == 2) {
//            queryEntry.getEquals().put("assigneeUserId",  "980695066010497026");
            mQueryEntry.getEquals().put("assigneeUserId", EanfangApplication.getApplication().getUserId() + "");
        }
//        if (getmTitle().equals("未读日志")) {
//            queryEntry.getEquals().put(Constant.STATUS, "0");
//        } else if (getmTitle().equals("已读日志")) {
//            queryEntry.getEquals().put(Constant.STATUS, "1");
//        }

        mQueryEntry.setSize(10);
        mQueryEntry.setPage(mPage);

        EanfangHttp.post(NewApiService.OA_DEFEND_LOG_LIST)
                .upJson(JsonUtils.obj2String(mQueryEntry))
                .execute(new EanfangCallback<DefendLogBean>(getActivity(), true, DefendLogBean.class) {

                    @Override
                    public void onSuccess(DefendLogBean bean) {
                        mQueryEntry = null;
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

}
