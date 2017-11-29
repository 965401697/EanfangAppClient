package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.CompanyDetailActivity;
import net.eanfang.client.ui.activity.worksapce.OrderConfirmActivitys;
import net.eanfang.client.ui.activity.worksapce.SelectCompanyActivity;
import net.eanfang.client.ui.adapter.CompanyListAdapter;
import net.eanfang.client.ui.base.BaseFragment;
import net.eanfang.client.ui.model.InstallOrderConfirmBean;
import net.eanfang.client.ui.model.SelectCompanyBean;

import java.util.List;

/**
 * 选择公司列表
 * Created by yaosheng on 2017/4/23.
 */

public class CompanyListFragment extends BaseFragment {

    private View v;
    private String mTitle;
    private RecyclerView mRecyclerView;
    private List<SelectCompanyBean.All1Bean> mDataList;

    public static CompanyListFragment getInstance(List<SelectCompanyBean.All1Bean> mDataList) {
        CompanyListFragment sf = new CompanyListFragment();
        sf.mDataList = mDataList;
        return sf;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_company_list;
    }

    @Override
    protected void initData(Bundle arguments) {
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        initAdapter();

    }

    @Override
    protected void setListener() {

    }

    private void initAdapter() {
        BaseQuickAdapter evaluateAdapter = new CompanyListAdapter(R.layout.item_company_list, mDataList);
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), CompanyDetailActivity.class);
                InstallOrderConfirmBean installOrderConfirmBean = ((SelectCompanyActivity)getActivity()).getInstallOrderConfirmBean();
                installOrderConfirmBean.setWorkercompanyuid(mDataList.get(position).getCompanyuid());
                intent.putExtra("bean",installOrderConfirmBean);
                intent.putExtra("id",mDataList.get(position).getCompanyuid());

                startActivity(intent);
            }
        });
        evaluateAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_select){
                    Intent intent = new Intent(getActivity(), OrderConfirmActivitys.class);
                    InstallOrderConfirmBean installOrderConfirmBean = ((SelectCompanyActivity)getActivity()).getInstallOrderConfirmBean();
                    installOrderConfirmBean.setWorkercompanyuid(mDataList.get(position).getCompanyuid());
                    intent.putExtra("bean",installOrderConfirmBean);
                    intent.putExtra("id",mDataList.get(position).getCompanyuid());
                    startActivity(intent);
                }else if(view.getId() == R.id.tv_compare){
                    if (((SelectCompanyActivity)getActivity()).getCompareList().size() == 3){
                        showToast("最多添加3个公司对比");
                        return;
                    }
                    for (int i = 0; i < ((SelectCompanyActivity)getActivity()).getCompareList().size(); i++) {
                        if (mDataList.get(position).getCompanyuid().equals(((SelectCompanyActivity)getActivity()).getCompareList().get(i).getCompanyuid())){
                            showToast("当前公司已经添加到了公司对比");
                            return;
                        }
                    }
                    mDataList.get(position).setItemType(2);
                    ((SelectCompanyActivity)getActivity()).getCompareList().add(mDataList.get(position));
                    showToast("已成功添加到公司对比");
                }
            }
        });
        mRecyclerView.setAdapter(evaluateAdapter);
    }

}
