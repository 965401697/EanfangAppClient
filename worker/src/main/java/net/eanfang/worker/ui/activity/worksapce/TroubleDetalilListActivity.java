package net.eanfang.worker.ui.activity.worksapce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.PageUtils;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.BughandleConfirmEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.ToubleDetailListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/11  22:33
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class TroubleDetalilListActivity extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.list_trouble)
    RecyclerView listTrouble;
    private ToubleDetailListAdapter toubleDetailListAdapter;
    private Activity mContext;
    private Long busRepairOrderId;
    private int isPhoneSolve;
    private Intent intent;
    private List<BughandleConfirmEntity> mDataList;

    public TroubleDetalilListActivity(Activity context, boolean isfull, Long id, int isPhoneSolve) {
        super(context, isfull);
        this.mContext = context;
        this.busRepairOrderId = id;
        this.isPhoneSolve = isPhoneSolve;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_trouble_list_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("busRepairOrderId", busRepairOrderId + "");
        EanfangHttp.post(RepairApi.POST_BUGHANDLE_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<PageUtils<JSONObject>>(mContext, true, PageUtils.class, (list) -> {
                    mDataList = JSONArray.parseArray(JSONArray.toJSONString(list.getList()), BughandleConfirmEntity.class);
                    if (mDataList.size() == 1) {
                        dismiss();
                        jump(0);
                    } else if (mDataList.size() == 0) {
                        showToast("暂无数据");
                    } else {
                        initAdapter();
                    }

                }));
    }

    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("故障处理");
        listTrouble.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private void jump(int position) {
        /**
         * 获取：是否电话解决（0：未解决，1：已解决）
         */
        if (isPhoneSolve == 0) {
            intent = new Intent(mContext, TroubleDetailActivity.class);
        } else {
            intent = new Intent(mContext, PsTroubleDetailActivity.class);
        }
        intent.putExtra("orderId", mDataList.get(position).getId());
        intent.putExtra("phoneSolve", isPhoneSolve);
        intent.putExtra("bean", mDataList.get(position));
        mContext.startActivity(intent);
    }

    private void initAdapter() {
        toubleDetailListAdapter = new ToubleDetailListAdapter(mDataList);
        listTrouble.setAdapter(toubleDetailListAdapter);
        listTrouble.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                jump(position);
            }
        });
    }
}
