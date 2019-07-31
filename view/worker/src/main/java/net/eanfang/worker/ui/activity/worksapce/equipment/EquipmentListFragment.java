package net.eanfang.worker.ui.activity.worksapce.equipment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.EquipmentBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.PermKit;
import com.eanfang.biz.model.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.fragment.TemplateItemListFragment;

/**
 * Created by O u r on 2018/6/27.
 * 已提取相关内容
 */

public class EquipmentListFragment extends TemplateItemListFragment {


    private EquipmentListAdapter mAdapter;
    private String mType;

    //    private String mOwnerCompanyId;
//    private Activity mActivity;
//, String ownerCompanyId, Activity activity
    public static EquipmentListFragment getInstance(String type) {
        EquipmentListFragment f = new EquipmentListFragment();
        f.mType = type;
//        f.mOwnerCompanyId = ownerCompanyId;
//        f.mActivity = activity;
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
                if (!PermKit.get().getExchangeDetailPrem()) {
                    return;
                }
                Intent intent = new Intent(getActivity(), EquipmentDetailActivity.class);
                intent.putExtra("id", String.valueOf(mAdapter.getData().get(position).getId()));
//                intent.putExtra("ownerCompanyId", mOwnerCompanyId);
                intent.putExtra("assigneeCompanyId", String.valueOf(WorkerApplication.get().getCompanyId()));
                intent.putExtra("businessOneCode", mType);
                startActivity(intent);

            }
        });
    }


    @Override
    protected void getData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setSize(10);
        queryEntry.setPage(mPage);
        queryEntry.getEquals().put("assigneeCompanyId", String.valueOf(WorkerApplication.get().getCompanyId()));
        if (!TextUtils.isEmpty(((EquipmentListActivity) getActivity()).mOwnerCompanyId)) {
            queryEntry.getEquals().put("ownerCompanyId", ((EquipmentListActivity) getActivity()).mOwnerCompanyId);
        }
        queryEntry.getEquals().put("businessOneCode", mType);
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
//                                ((EquipmentListActivity) getActivity()).setEquipmentTitle(bean.getList().get(0).getOwnerCompanyEnityt().getOrgName());
//                                ((EquipmentListActivity) getActivity()).setmOwnerCompanyId(String.valueOf(bean.getList().get(0).getOwnerCompanyEnityt().getCompanyId()));
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
    protected ViewModel initViewModel() {
        return null;
    }
}

