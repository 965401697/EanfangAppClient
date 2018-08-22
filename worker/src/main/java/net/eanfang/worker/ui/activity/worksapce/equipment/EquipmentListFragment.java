package net.eanfang.worker.ui.activity.worksapce.equipment;

import android.content.Intent;
import android.text.TextUtils;
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
import com.yaf.base.entity.CooperationEntity;


import net.eanfang.worker.R;
import net.eanfang.worker.ui.fragment.TemplateItemListFragment;

import static android.app.Activity.RESULT_OK;

/**
 * Created by O u r on 2018/6/27.
 */

public class EquipmentListFragment extends TemplateItemListFragment {


    private EquipmentListAdapter mAdapter;
    private String mType;

    public static EquipmentListFragment getInstance(String type) {
        EquipmentListFragment f = new EquipmentListFragment();
        f.mType = type;
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
                if (!PermKit.get().getExchangeDetailPrem()) return;
                Intent intent = new Intent(getActivity(), EquipmentDetailActivity.class);
                intent.putExtra("id", mAdapter.getData().get(position).getId());
                intent.putExtra("ownerCompanyId", ((EquipmentListActivity) getActivity()).ownerCompanyId);
                intent.putExtra("assigneeCompanyId", String.valueOf(EanfangApplication.get().getCompanyId()));
                intent.putExtra("businessOneCode", mType);
                startActivity(intent);

            }
        });
    }


    @Override
    protected void getData() {
        if (!TextUtils.isEmpty(((EquipmentListActivity) getActivity()).ownerCompanyId)) {
//            ((EquipmentListActivity) getActivity()).loadingDialog.show();
            if (((EquipmentListActivity) getActivity()).title.length() > 5) {
                ((EquipmentListActivity) getActivity()).setTitle(((EquipmentListActivity) getActivity()).title.substring(0, 6) + "...设备列表");
            } else {
                ((EquipmentListActivity) getActivity()).setTitle(((EquipmentListActivity) getActivity()).title + "设备列表");
            }
            getList();
        } else {
            QueryEntry queryEntry = new QueryEntry();
            queryEntry.setSize(5);
            queryEntry.getEquals().put("status", "1");
            queryEntry.setPage(1);
            queryEntry.getEquals().put("ownerOrgId", String.valueOf(EanfangApplication.getApplication().getCompanyId()));
//            ((EquipmentListActivity) getActivity()).loadingDialog.show();
            EanfangHttp.post(NewApiService.GET_COOPERATION_LIST)
                    .upJson(JsonUtils.obj2String(queryEntry))
                    .execute(new EanfangCallback<CooperationEntity>(getActivity(), true, CooperationEntity.class, true, (list) -> {
                        if (list.size() > 0) {
                            ((EquipmentListActivity) getActivity()).ownerCompanyId = String.valueOf(((CooperationEntity) list.get(0)).getAssigneeOrgId());
                            ((EquipmentListActivity) getActivity()).title = String.valueOf(((CooperationEntity) list.get(0)).getAssigneeOrg().getOrgName());
                            if (((EquipmentListActivity) getActivity()).title.length() > 5) {
                                ((EquipmentListActivity) getActivity()).setTitle(((EquipmentListActivity) getActivity()).title.substring(0, 6) + "...设备列表");
                            } else {
                                ((EquipmentListActivity) getActivity()).setTitle(((EquipmentListActivity) getActivity()).title + "设备列表");
                            }
                            mTvNoData.setVisibility(View.GONE);
                            getList();
                        } else {
//                            ((EquipmentListActivity) getActivity()).loadingDialog.dismiss();
                            mTvNoData.setVisibility(View.VISIBLE);
                            ((EquipmentListActivity) getActivity()).setTitle("设备列表");
                        }
                    }));
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {
                String id = data.getStringExtra("ownerCompanyId");
                String title = data.getStringExtra("title");
                if (!id.equals(((EquipmentListActivity) getActivity()).ownerCompanyId)) {
                    ((EquipmentListActivity) getActivity()).ownerCompanyId = id;
                    ((EquipmentListActivity) getActivity()).title = title;
                    mPage = 1;//下拉永远第一页
                    getData();
                }
            }
        }
    }

    private void getList() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setSize(10);
        queryEntry.setPage(mPage);
        queryEntry.getEquals().put("assigneeCompanyId", String.valueOf(EanfangApplication.get().getCompanyId()));
        queryEntry.getEquals().put("ownerCompanyId", ((EquipmentListActivity) getActivity()).ownerCompanyId);
        queryEntry.getEquals().put("businessOneCode", mType);
        EanfangHttp.post(NewApiService.DEVICE_LIST_CLIENT)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<EquipmentBean>(getActivity(), true, EquipmentBean.class) {
                    @Override
                    public void onSuccess(EquipmentBean bean) {
//                        ((EquipmentListActivity) getActivity()).loadingDialog.dismiss();
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
//                        ((EquipmentListActivity) getActivity()).loadingDialog.dismiss();
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
//                        ((EquipmentListActivity) getActivity()).loadingDialog.dismiss();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

}

