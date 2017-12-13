package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.base.BaseDialog;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.WorkspaceInstallAdapter;
import net.eanfang.client.ui.model.WorkspaceInstallBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/21  10:02
 * @email houzhongzhou@yeah.net
 * @desc 报装管控
 */

public class InstallCtrlView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    private List<WorkspaceInstallBean.AllBean> mDataList;


    public InstallCtrlView(Activity context, boolean isfull) {
        super(context, isfull);
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_istall_ctrl);
        ButterKnife.bind(this);
        initView();
        initData();
        setTitle("报装管控");
    }


    private void initData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("page", 1);
            jsonObject.put("rows", 10);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        EanfangHttp.get(ApiService.WORKSPACE_INSTALL)
                .tag(this)
                .params("json", jsonObject.toString())
                .execute(new EanfangCallback<WorkspaceInstallBean>(context, false) {
                    @Override
                    public void onSuccess(WorkspaceInstallBean bean) {
                        super.onSuccess(bean);
                        mDataList = bean.getAll();
                        initAdapter();
                    }

                    @Override
                    public void onFail(Integer code, String message, JSONObject jsonObject) {
                        super.onFail(code, message, jsonObject);
                        showToast("暂时没有查询到订单信息");
                    }
                });
    }

    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("报装管控");
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    private void initAdapter() {
        BaseQuickAdapter evaluateAdapter = new WorkspaceInstallAdapter(R.layout.item_workspace_install_list, mDataList);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                InstallCtrlItemView ctrlItemView = new InstallCtrlItemView(context, mDataList.get(position).getOrdernum());
                ctrlItemView.show();
            }
        });
        mRecyclerView.setAdapter(evaluateAdapter);
    }

}
