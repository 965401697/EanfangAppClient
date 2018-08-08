package net.eanfang.worker.ui.activity.worksapce.oa;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.model.TemplateBean;
import com.eanfang.util.ToastUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OASearchActivity extends BaseWorkerActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;

    private List<TemplateBean> mTemplateBeanList;
    private List<TemplateBean.Preson> presonList = new ArrayList<>();
    private List<TemplateBean.Preson> newPresonList = new ArrayList<>();
    private OASearchAdapter oaSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oasearch);
        ButterKnife.bind(this);
        setTitle("搜索人员");
        setLeftBack();
        setRightTitle("确定");
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(newPresonList);
                endTransaction(true);
            }
        });

        Bundle bundle = getIntent().getExtras();
        mTemplateBeanList = (List<TemplateBean>) bundle.getSerializable("list");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        oaSearchAdapter = new OASearchAdapter();
        oaSearchAdapter.bindToRecyclerView(recyclerView);
        oaSearchAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TemplateBean.Preson preson = (TemplateBean.Preson) adapter.getData().get(position);
                if (view.getId() == R.id.cb_checked) {
                    if (preson.isChecked()) {
                        preson.setChecked(false);
                        newPresonList.remove(preson);
                    } else {
                        preson.setChecked(true);
                        newPresonList.add(preson);
                    }
                    adapter.notifyItemChanged(position);
                }
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    initPhoneData(s.toString());
                } else {
                    llSearch.setVisibility(View.INVISIBLE);
                    ToastUtil.get().showToast(OASearchActivity.this, "没有搜索到人员");
                    oaSearchAdapter.getData().clear();
                    oaSearchAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }


    /**
     * 根据手机号查找用户
     *
     * @param
     */
    private void initPhoneData(String content) {
        presonList.clear();
        for (TemplateBean templateBean : mTemplateBeanList) {
            for (TemplateBean.Preson preson : templateBean.getPresons()) {
                if (preson.getMobile().contains(content) || preson.getName().contains(content)) {
                    presonList.add(preson);
                }
            }
        }
        if (presonList.size() == 0) {
            ToastUtil.get().showToast(this, "没有搜索到人员");
            oaSearchAdapter.getData().clear();
            oaSearchAdapter.notifyDataSetChanged();
            llSearch.setVisibility(View.INVISIBLE);
        } else {
            llSearch.setVisibility(View.VISIBLE);
            oaSearchAdapter.getData().clear();
            oaSearchAdapter.addData(presonList);
            oaSearchAdapter.notifyDataSetChanged();
        }
    }
}
