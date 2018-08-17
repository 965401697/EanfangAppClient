package net.eanfang.client.ui.activity.worksapce.equipment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.EquipmentBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.ui.fragment.TemplateItemListFragment;

import static android.app.Activity.RESULT_OK;

/**
 * Created by O u r on 2018/6/27.
 */

public class EquipmentListFragment extends TemplateItemListFragment {


    private int mPage = 1;
    private EquipmentListAdapter mAdapter;
    private String mType;

    public static EquipmentListFragment getInstance(String type) {
        EquipmentListFragment f = new EquipmentListFragment();
        f.mType = type;
        return f;

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        refresh();
    }

    public void refresh() {
        mPage = 1;//下拉永远第一页
        getData();
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        mPage++;
        getData();
    }

    @Override
    protected void initAdapter() {
        mAdapter = new EquipmentListAdapter(R.layout.item_equipment_list);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!PermKit.get().getDeviceArchiveDetailPerm()) return;
                Intent intent = new Intent(getActivity(), EquipmentDetailActivity.class);
                intent.putExtra("id", mAdapter.getData().get(position).getId());
                startActivity(intent);
            }
        });
        getData();
    }

    @Override
    public void getData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setSize(10);
        queryEntry.setPage(mPage);
        queryEntry.getLike().put("businessThreeCode", mType + "%");
        EanfangHttp.post(NewApiService.DEVICE_LIST_CLIENT)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<EquipmentBean>(getActivity(), true, EquipmentBean.class) {
                    @Override
                    public void onSuccess(EquipmentBean bean) {

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

