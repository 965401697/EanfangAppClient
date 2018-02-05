package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseActivity;
import com.yaf.sys.entity.OrgEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.ConstactsAdapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setTitle("分公司");
        setLeftBack();
    }

    private void initData() {
        EanfangHttp.get(UserApi.GET_BRANCH_OFFICE_LIST)
                .execute(new EanfangCallback<OrgEntity>(this, true, OrgEntity.class, true, (list) -> {
                    initAdapter(list);
                }));
    }

    private void initAdapter(List<OrgEntity> mDatas) {
        revList.setLayoutManager(new LinearLayoutManager(this));
        ConstactsAdapter adapter = new ConstactsAdapter(mDatas);
        revList.setAdapter(adapter);
    }
}
