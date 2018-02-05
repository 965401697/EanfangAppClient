package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.PartnerBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.PartnerAdapter;
import net.eanfang.client.ui.widget.PartnerOrgTypeListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/22  14:25
 * @email houzhongzhou@yeah.net
 * @desc 合作公司
 */

public class PartnerActivity extends BaseActivity {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rev_list)
    RecyclerView revList;
    private PartnerAdapter parentAdapter;
    PartnerBean partnerBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("合作公司");
        setLeftBack();
        initData();

    }


    private void initData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("ownerOrgId", EanfangApplication.getApplication().getUser()
                .getAccount().getDefaultUser().getCompanyEntity().getOrgId() + "");
        EanfangHttp.post(UserApi.GET_COOPERATION_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<PartnerBean>(this, true, PartnerBean.class, (bean) -> {
                    partnerBean = bean;
                    initAdapter(bean.getList());
                }));
    }

    private void initAdapter(List<PartnerBean.ListBean> mDataList) {
        revList.setLayoutManager(new LinearLayoutManager(this));
        parentAdapter = new PartnerAdapter(R.layout.item_quotation_detail, mDataList);
        revList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                new PartnerOrgTypeListView(PartnerActivity.this, true, mDataList).show();
            }
        });
        revList.setAdapter(parentAdapter);

    }
}
