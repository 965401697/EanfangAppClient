package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.ExternalListBean;
import com.eanfang.ui.base.BaseActivity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.ExternalAdapter;
import net.eanfang.worker.ui.widget.ExternalStaffView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/22  14:24
 * @email houzhongzhou@yeah.net
 * @desc 外部单位
 */

public class ExternalCompanyActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_external_company);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("外协单位");
        initData();
    }

    private void initData() {
        EanfangHttp.get(UserApi.GET_STAFFTEMP_OUTER_LIST)
                .execute(new EanfangCallback<ExternalListBean>(this, true, ExternalListBean.class, true, (list) -> {
                    initAdapter(list);
                }));
    }

    private void initAdapter(List<ExternalListBean> mDataList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ExternalAdapter parentAdapter = new ExternalAdapter(R.layout.item_quotation_detail, mDataList);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                new ExternalStaffView(ExternalCompanyActivity.this, true, mDataList.get(position).getOrgId()).show();
            }
        });
        recyclerView.setAdapter(parentAdapter);
    }
}
