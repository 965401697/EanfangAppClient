package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.base.BaseDialog;
import com.eanfang.util.CallUtils;
import com.google.gson.JsonObject;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.network.apiservice.UserApi;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.CooperationAdapter;
import net.eanfang.client.ui.model.PartnerBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/23  16:58
 * @email houzhongzhou@yeah.net
 * @desc 客户管理
 */

public class PartnerOrgTypeListView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_call_service_phone)
    TextView tvCallServicePhone;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private Activity mContext;
    private List<PartnerBean.ListBean> mDataList;

    public PartnerOrgTypeListView(Activity context, boolean isfull, List<PartnerBean.ListBean> dataList) {
        super(context, isfull);
        this.mContext = context;
        this.mDataList = dataList;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_partner_orgtype_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("客户管理");
        ivLeft.setOnClickListener(v -> dismiss());
        tvCallServicePhone.setOnClickListener((v) -> CallUtils.call(context, "010-5877-8732"));
        initAdapter();
    }

    private void initAdapter() {
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        CooperationAdapter adapter = new CooperationAdapter(R.layout.item_bind_company, mDataList);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            switch (view.getId()) {
                case R.id.btn_confirm:
                    audit(1);
                    break;
                case R.id.btn_unconfirm:
                    audit(2);
                    break;
                default:
                    break;
            }
        });
        rvList.setAdapter(adapter);
    }

    private void audit(int stauts) {
        EanfangHttp.post(UserApi.GET_COOPERATION_AUDIT)
                .params("id", EanfangApplication.getApplication().getUserId())
                .params("status", stauts)
                .execute(new EanfangCallback<JsonObject>(mContext, true, JSONObject.class, (bean) -> {
                    dismiss();
                }));
    }
}
