package net.eanfang.client.ui.activity.im;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.model.TemplateBean;
import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchPersonCompanyActivity extends BaseClientActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_no)
    TextView tvNo;
    private SearchPersonCompanyAdapter mAdapter;
    private ArrayList<TemplateBean> mDataList;

    private ArrayList<TemplateBean.Preson> searchDataList;
    private List<TemplateBean.Preson> presonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_person_company);
        ButterKnife.bind(this);
        setTitle("搜索人员");
        setLeftBack();
        setRightTitle("确定");
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presonList != null && presonList.size() > 0) {
                    Intent intent = new Intent(SearchPersonCompanyActivity.this, CreateGroupActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", (Serializable) presonList);
                    intent.putExtras(bundle);
                    intent.putExtra("groupName", getIntent().getStringExtra("groupName"));
                    intent.putExtra("imgKey", getIntent().getStringExtra("imgKey"));
                    intent.putExtra("locationPortrait", getIntent().getStringExtra("locationPortrait"));
                    startActivity(intent);
                    endTransaction(true);
                } else {
                    ToastUtil.get().showToast(SearchPersonCompanyActivity.this, "请选择人员");
                }
            }
        });

        mDataList = (ArrayList<TemplateBean>) getIntent().getSerializableExtra("data");


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            presonList = (List<TemplateBean.Preson>) bundle.getSerializable("list");
        }

        initViews();
    }

    private void initViews() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchPersonCompanyAdapter(R.layout.item_search_person_company);
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

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.cb_check) {

                    TemplateBean.Preson p = (TemplateBean.Preson) adapter.getData().get(position);
                    if (p.isChecked()) {
                        presonList.remove(p);
                        p.setChecked(false);
                    } else {
                        presonList.add(p);
                        p.setChecked(true);
                    }
                    adapter.notifyItemChanged(position);
                }
            }
        });
    }

    private void search(String name) {
        if (searchDataList == null) {
            searchDataList = new ArrayList<>();
        }
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
