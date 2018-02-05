package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.model.PartnerBean;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.CallUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.CooperationAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/23  16:58
 * @email houzhongzhou@yeah.net
 * @desc
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

    public PartnerOrgTypeListView(Activity context, boolean isfull,List<PartnerBean.ListBean> dataList) {
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
        tvCallServicePhone.setOnClickListener((v) -> {
            CallUtils.call(context, "010-5877-8732");
        });
        initAdapter();
    }

    private void initAdapter() {
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        CooperationAdapter adapter=new CooperationAdapter(R.layout.item_bind_company,mDataList);
        rvList.setAdapter(adapter);
    }
}
