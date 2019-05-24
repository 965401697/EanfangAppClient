package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.LeaveBugBean;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.RepairFailureEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.repair.seefaultdetail.PsTroubleDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.seefaultdetail.TroubleDetailActivity;
import net.eanfang.worker.ui.adapter.LeaveBugAdapter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/2/28  11:30
 * @email houzhongzhou@yeah.net
 * @desc 遗留故障
 */

public class LeaveBugView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rev_list)
    RecyclerView revList;
    private Activity mContext;

    public LeaveBugView(Activity context, boolean isfull) {
        super(context, isfull);
        this.mContext = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_comment_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("遗留故障");
        ivLeft.setOnClickListener(v -> dismiss());
        getData(1);
    }

    private void getData(int page) {
        QueryEntry entry = new QueryEntry();
        entry.setPage(page);
        entry.setSize(100);
        EanfangHttp.post(RepairApi.GET_LEAVE_BUG_LIST)
                .upJson(JsonUtils.obj2String(entry))
                .execute(new EanfangCallback(mContext, true, LeaveBugBean.class) {
                    @Override
                    public void onSuccess(Object bean) {
                        super.onSuccess(bean);
                        if (bean instanceof LeaveBugBean) {
                            LeaveBugBean leaveBugBean = (LeaveBugBean) bean;
                            initAdapter(leaveBugBean.getList());
                        }
                    }
                });
    }

    private void initAdapter(List<RepairFailureEntity> dataList) {
        revList.setLayoutManager(new LinearLayoutManager(mContext));
        LeaveBugAdapter adapter = new LeaveBugAdapter(dataList);
        revList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (dataList.get(position).getRepairOrderEntity().getIsPhoneSolve() == 0) {
                    mContext.startActivity(new Intent(mContext, TroubleDetailActivity.class)
                            .putExtra("orderId", dataList.get(position).getId())
                            .putExtra("status", GetConstDataUtils.getBugDetailList().get(dataList.get(position).getStatus()))
                            .putExtra("repairOrderId", dataList.get(position).getBusRepairOrderId())
                    );
                } else {
                    mContext.startActivity(new Intent(mContext, PsTroubleDetailActivity.class)
                            .putExtra("orderId", dataList.get(position).getId())
                            .putExtra("status", GetConstDataUtils.getBugDetailList().get(dataList.get(position).getStatus()))
                            .putExtra("repairOrderId", dataList.get(position).getBusRepairOrderId())
                    );
                }
            }
        });
        revList.setAdapter(adapter);

    }
}
