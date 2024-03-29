package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.biz.model.entity.OrgEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.adapter.ConstactsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/22  14:20
 * @email houzhongzhou@yeah.net
 * @desc 分公司
 */

public class SubcompanyActivity extends BaseActivity {

    @BindView(R.id.rev_list)
    RecyclerView revList;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_contact_unit);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        setTitle("分公司");
        setLeftBack();
    }

    private void initData() {
        EanfangHttp.get(UserApi.GET_BRANCH_OFFICE_LIST)
                .params("companyId", WorkerApplication.get().getCompanyId())
                .execute(new EanfangCallback<OrgEntity>(this, true, OrgEntity.class, true, (list) -> {
                    initAdapter(list);
                }));
    }

    private void initAdapter(List<OrgEntity> mDatas) {
        revList.setLayoutManager(new LinearLayoutManager(this));
        ConstactsAdapter adapter = new ConstactsAdapter(mDatas);
        revList.setAdapter(adapter);
        if (mDatas.size() > 0) {
            revList.setVisibility(View.VISIBLE);
            tvNodata.setVisibility(View.GONE);
        } else {
            revList.setVisibility(View.GONE);
            tvNodata.setVisibility(View.VISIBLE);
        }
    }
}
