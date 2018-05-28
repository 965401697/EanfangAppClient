package net.eanfang.client.ui.activity.worksapce.repair;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FaultListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.repair.FaultLibraryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/5/28  11:37
 * @decision 故障库
 */
public class FaultLibraryActivity extends BaseActivity {

    private static final int RESULT_DATACODE = 200;
    @BindView(R.id.rv_faultList)
    RecyclerView rvFaultList;

    private FaultLibraryAdapter faultLibraryAdapter;
    private List<FaultListBean.DataBean.ListBean> mFaultListBeanList = new ArrayList<>();
    // 系统类别id
    private String businessOneCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fault_library);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }


    private void initView() {
        setLeftBack();
        setTitle("故障库");
        businessOneCode = getIntent().getStringExtra("businessOneCode");
        rvFaultList.setLayoutManager(new LinearLayoutManager(this));
        faultLibraryAdapter = new FaultLibraryAdapter(R.layout.layout_fault_list_item);
        faultLibraryAdapter.setNewData(mFaultListBeanList);
        faultLibraryAdapter.bindToRecyclerView(rvFaultList);
    }

    private void initData() {
        QueryEntry queryEntry = new QueryEntry();
        if (!Constant.ALL.equals(getTitle())) {
            queryEntry.getEquals().put("businessOneCode", businessOneCode);
            queryEntry.getEquals().put("headDeviceId", "");
        }
        queryEntry.setSize(10);
        queryEntry.setPage(1);
        EanfangHttp.post(RepairApi.GET_FAULT_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<FaultListBean>(FaultLibraryActivity.this, true, FaultListBean.class, bean -> {
                    if (bean != null) {
                        mFaultListBeanList = bean.getData().getList();
                        faultLibraryAdapter.addData(mFaultListBeanList);
                    }

                }));
    }

    private void initListener() {
        rvFaultList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("faultDes", mFaultListBeanList.get(position).getDescription());
                intent.putExtra("faultImgs", mFaultListBeanList.get(position).getPictures());
                setResult(RESULT_DATACODE, intent);
                finishSelf();
            }
        });
    }
}
