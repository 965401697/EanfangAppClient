package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.CompanyConpareAdapter;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.SelectCompanyBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 公司对比
 * Created by Administrator on 2017/5/24.
 */

public class CompanyCompareActivity extends BaseActivity {

    private CompanyConpareAdapter adapter;
    private RecyclerView mRecyclerView;
    private List<SelectCompanyBean.All1Bean> mDataList;
    // 公司对比选中的公司列表
    private List<SelectCompanyBean.All1Bean> compareList = new ArrayList<>();
    private ArrayList<String> companyUidList = new ArrayList<>();
    private ImageView iv_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_compare);
        getData();
        initView();
        initData();
        initAdapter();
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        compareList = (List<SelectCompanyBean.All1Bean>) bundle.getSerializable("bean");
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL));
        setTitle("公司对比");
        setLeftBack();
        setRightImageResId(R.mipmap.pk);
    }

    @Override
    public void setLeftBack() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra("deleteList",companyUidList);
                setResult(32989,intent);
                finish();
            }
        });
    }
    private void initData() {
        mDataList = new ArrayList<>();
        SelectCompanyBean.All1Bean item = new SelectCompanyBean.All1Bean(1);
        mDataList.add(item);
        mDataList.addAll(compareList);
    }

    private void initAdapter() {
        adapter = new CompanyConpareAdapter(mDataList);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_delete){
                    companyUidList.add(mDataList.get(position).getCompanyuid());
                    mDataList.remove(position);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra("deleteList",companyUidList);
        setResult(32989,intent);
        finish();
    }
}
