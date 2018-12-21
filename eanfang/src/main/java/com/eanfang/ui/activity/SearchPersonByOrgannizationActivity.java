package com.eanfang.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.model.TemplateBean;
import com.eanfang.ui.adapter.SearchPersonByOrgannizationAdapter;
import com.eanfang.ui.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchPersonByOrgannizationActivity extends BaseActivity {


    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.et_search)
    EditText etSearch;
    @BindView(R2.id.tv_no)
    TextView tvNo;
    private SearchPersonByOrgannizationAdapter mAdapter;
    private ArrayList<TemplateBean> mDataList;

    private ArrayList<TemplateBean.Preson> searchDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_person_by_organnization);
        ButterKnife.bind(this);
        setTitle("搜索人员");
        setLeftBack();
        initViews();
        mDataList = (ArrayList<TemplateBean>) getIntent().getSerializableExtra("data");
    }

    private void initViews() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchPersonByOrgannizationAdapter(R.layout.item_search_person_origanzation);
        mAdapter.bindToRecyclerView(recyclerView);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(s)) {
                    search(s.toString());
                } else {
                    searchDataList.clear();
                    mAdapter.setNewData(searchDataList);
                    tvNo.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void search(String name) {
        if (searchDataList == null) searchDataList = new ArrayList<>();
        searchDataList.clear();
        for (TemplateBean t : mDataList) {
            for (TemplateBean.Preson p : t.getPresons()) {
                if (p.getName().contains(name)) {
                    searchDataList.add(p);
                }
            }
        }
        if (searchDataList.size() > 0) {
            mAdapter.setNewData(searchDataList);
            tvNo.setVisibility(View.GONE);
        } else {
            tvNo.setVisibility(View.VISIBLE);
        }
    }
}
