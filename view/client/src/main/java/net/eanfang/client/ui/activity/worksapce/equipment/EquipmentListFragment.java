package net.eanfang.client.ui.activity.worksapce.equipment;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.EquipmentBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.PermKit;
import com.eanfang.biz.model.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.ui.fragment.TemplateItemListFragment;

/**
 * Created by O u r on 2018/6/27.
 * //已提取
 */

public class EquipmentListFragment extends TemplateItemListFragment {


    private int mPage = 1;
    private EquipmentListAdapter mAdapter;
    private String mType;

    /**
     * 是否报修
     */
    private Boolean isRepair = false;
    private QueryEntry mQueryEntry;

    public static EquipmentListFragment getInstance(String type, Boolean mIsRepair) {
        EquipmentListFragment f = new EquipmentListFragment();
        f.mType = type;
        f.isRepair = mIsRepair;
        return f;

    }

    @Override
    protected void initAdapter() {
        mAdapter = new EquipmentListAdapter(R.layout.item_equipment_list);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!PermKit.get().getDeviceArchiveDetailPerm()) {
                    return;
                }
                /**
                 * 报修直接返回 or 查看设备详情
                 * */
                if (isRepair) {
                    Intent intent = new Intent();
                    intent.putExtra("custDeviceEntity", mAdapter.getData().get(position));
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();

                } else {
                    Intent intent = new Intent(getActivity(), EquipmentDetailActivity.class);
                    intent.putExtra("id", String.valueOf(mAdapter.getData().get(position).getId()));
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    protected void getData() {
        if (mQueryEntry == null) {
            mQueryEntry = new QueryEntry();
        }
        mQueryEntry.setSize(10);
        mQueryEntry.setPage(mPage);
        mQueryEntry.getLike().put("businessThreeCode", mType + "%");
        EanfangHttp.post(NewApiService.DEVICE_LIST_CLIENT)
                .upJson(JsonUtils.obj2String(mQueryEntry))
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
                                //释放对象
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
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mQueryEntry = null;
        mPage = 1;
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

}

