package net.eanfang.worker.ui.activity.worksapce.equipment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.ToastUtil;
import com.yaf.base.entity.CooperationEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EquipmentCooperationCompanyActivity extends BaseWorkerActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_no_datas)
    TextView mTvNoData;

    private int mPage = 1;
    private EquipmentCooperationRelationAdapter mAdapter;
    private String ownerCompanyId;
    private CooperationEntity mOldCooperationEntity;
    private int moldPosition = -1;
    private CooperationEntity cooperationEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_cooperation_company);
        ButterKnife.bind(this);
        setTitle("合作公司列表");
        setLeftBack();
        setRightTitle("确定");
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(ownerCompanyId)) {
                    Intent intent = new Intent();
                    intent.putExtra("bean", cooperationEntity);
//                    intent.putExtra("bean", mAdapter.getData().get(moldPosition));
                    setResult(RESULT_OK, intent);
                    finishSelf();
                } else {
                    ToastUtil.get().showToast(EquipmentCooperationCompanyActivity.this, "请选择一个合作公司");
                }
            }
        });
        ownerCompanyId = getIntent().getStringExtra("ownerCompanyId");
        initView();
        mPage = 1;
    }

    private void initView() {

        rvList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new EquipmentCooperationRelationAdapter(R.layout.item_equipment_cooperation_company, ownerCompanyId);
        mAdapter.bindToRecyclerView(rvList);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {


            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.cb_check) {
                    cooperationEntity = (CooperationEntity) adapter.getData().get(position);

                    if (!ownerCompanyId.equals(String.valueOf(cooperationEntity.getAssigneeOrgId()))) {
                        ownerCompanyId = String.valueOf(cooperationEntity.getAssigneeOrgId());
                        mAdapter.setmOwnerCompanyId(String.valueOf(cooperationEntity.getAssigneeOrgId()));
                    } else {
                        ownerCompanyId = "";
                        mAdapter.setmOwnerCompanyId("");
                    }

                    adapter.notifyDataSetChanged();

//                    if (mOldCooperationEntity == null) {
//                        mOldCooperationEntity = cooperationEntity;
//                        moldPosition = position;
//                        cooperationEntity.setChecked(true);
//                        adapter.notifyItemChanged(position);
//                    } else if (moldPosition == position) {
//                        mOldCooperationEntity.setChecked(false);
//                        adapter.notifyItemChanged(moldPosition);
//
//                        mOldCooperationEntity = null;
//                        moldPosition = -1;
//                    } else {
//                        mOldCooperationEntity.setChecked(false);
//                        cooperationEntity.setChecked(true);
//                        adapter.notifyItemChanged(moldPosition);
//                        adapter.notifyItemChanged(position);
//
//                        moldPosition = position;
//                        mOldCooperationEntity = cooperationEntity;
//                    }
                }
            }
        });
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


    private void getData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setSize(10);
        queryEntry.getEquals().put("status", "1");
        queryEntry.setPage(mPage);

        queryEntry.getEquals().put("ownerOrgId", String.valueOf(EanfangApplication.getApplication().getCompanyId()));

        EanfangHttp.post(NewApiService.GET_COOPERATION_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<CooperationEntity>(this, true, CooperationEntity.class, true, (list) -> {

                    if (mPage == 1) {
                        mAdapter.getData().clear();
                        mAdapter.setNewData(list);
                        mAdapter.loadMoreComplete();
                        if (list.size() < 10) {
                            mAdapter.loadMoreEnd();
                        }

                        if (list.size() > 0) {
                            mTvNoData.setVisibility(View.GONE);
                        } else {
                            mTvNoData.setVisibility(View.VISIBLE);
                        }


                    } else {
                        mAdapter.addData(list);
                        mAdapter.loadMoreComplete();
                        if (list.size() < 10) {
                            mAdapter.loadMoreEnd();
                        }
                    }

                }));
    }
}
