package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Constant;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.CallUtils;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.BughandleConfirmEntity;
import com.yaf.base.entity.BughandleDetailEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.TroubleDetailAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  10:53
 * @email houzhongzhou@yeah.net
 * @desc  电话解决
 */

public class PsTroubleDetailActivity extends BaseClientActivity /*implements View.OnClickListener */ {

    private RecyclerView rv_trouble;
    private TextView tv_complete;
    private TextView tv_complaint;
    /**
     * 单据照片 (3张)
     */
    private BGASortableNinePhotoLayout snpl_form_photos;

    private List<BughandleDetailEntity> mDataList;
    private TroubleDetailAdapter quotationDetailAdapter;
    private BughandleConfirmEntity bughandleConfirmEntity;
    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            Intent intent = new Intent(PsTroubleDetailActivity.this, PhoneLookTroubleDetailActivity.class);
            intent.putExtra(Constant.ID, bughandleConfirmEntity.getDetailEntityList().get(position).getId());
            startActivity(intent);
        }
    };
    private Long id, repairOrderId;
    private String status;
    private ImageView iv_left;
    /**
     * 单据照片 (3张)
     */
    private ArrayList<String> picList4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ps_client_fill_repair_info);

        id = getIntent().getLongExtra("orderId", 0);
        repairOrderId = getIntent().getLongExtra("repairOrderId", 0);
        status = getIntent().getStringExtra("status");
        initView();
        setTitle("电话解决");
        initData();


    }

    private void initData() {
        EanfangHttp.get(RepairApi.GET_BUGHANDLE_DETAIL)
                .params("id", id)
                .execute(new EanfangCallback<BughandleConfirmEntity>(this, true, BughandleConfirmEntity.class, (bean) -> {
                    bughandleConfirmEntity = bean;
                    setData();
                }));
    }

    private void initView() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);
        rv_trouble = (RecyclerView) findViewById(R.id.rv_trouble);
        snpl_form_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_form_photos);
        //两个操作按钮
        tv_complete = (TextView) findViewById(R.id.tv_complete);
        tv_complaint = (TextView) findViewById(R.id.tv_complaint);

        //非单确认状态 隐藏确认按钮
        if (!status.equals("待确认")) {
            findViewById(R.id.rl_client_option).setVisibility(View.GONE);
        }
        tv_complete.setOnClickListener(v -> flowConfirm());
        tv_complaint.setOnClickListener((v) -> {
            CallUtils.call(this, "010-5877-8731");
        });
        iv_left.setOnClickListener((v) -> finishSelf());
    }

    private void flowConfirm() {
        EanfangHttp.post(RepairApi.GET_FLOW_CONFIRE)
                .params("id", repairOrderId)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    showToast("确认成功");
                    finish();
                }));
    }

    private void setData() {

        initAdapter();
        initNinePhoto();
        picList4 = new ArrayList<>();
        if (bughandleConfirmEntity.getInvoicesPictures() != null) {
            String[] invoicesPic = bughandleConfirmEntity.getInvoicesPictures().split(",");
            picList4.addAll(Stream.of(Arrays.asList(invoicesPic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }


    }

    private void initAdapter() {
        mDataList = bughandleConfirmEntity.getDetailEntityList();
        quotationDetailAdapter = new TroubleDetailAdapter(R.layout.layout_trouble_adapter_item, mDataList);
        rv_trouble.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rv_trouble.setLayoutManager(new LinearLayoutManager(this));
        rv_trouble.setAdapter(quotationDetailAdapter);
        rv_trouble.addOnItemTouchListener(onItemClickListener);
    }

    private void initNinePhoto() {

        snpl_form_photos.setData(picList4);
        snpl_form_photos.setEditable(false);
        snpl_form_photos.setDelegate(new BGASortableDelegate(this));

    }
}

