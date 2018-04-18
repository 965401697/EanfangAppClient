package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetDateUtils;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.BughandleConfirmEntity;
import com.yaf.base.entity.BughandleDetailEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.TroubleDetailAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  10:53
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class PsTroubleDetailActivity extends BaseWorkerActivity /*implements View.OnClickListener */ {

    private RecyclerView rv_trouble;
    private TextView tv_over_time;
    private TextView tv_repair_time;
    private ImageView iv_left;
    /**
     * 单据照片 (3张)
     */
    private BGASortableNinePhotoLayout snpl_form_photos;

    //遗留问题
    private TextView tv_remain_question;
    private List<BughandleDetailEntity> mDataList;
    private TroubleDetailAdapter quotationDetailAdapter;
    private BughandleConfirmEntity bughandleConfirmEntity;
    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            Intent intent = new Intent(PsTroubleDetailActivity.this, PhoneLookTroubleDetailActivity.class);
            intent.putExtra("id", bughandleConfirmEntity.getDetailEntityList().get(position).getId());
            startActivity(intent);
        }
    };
    private Long id;
    private String status;
    /**
     * 单据照片 (3张)
     */
    private ArrayList<String> picList4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ps_client_fill_repair_info);

        id = getIntent().getLongExtra("orderId", 0);
        status = getIntent().getStringExtra("status");
        initView();
        setTitle("故障处理");
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
        tv_over_time = (TextView) findViewById(R.id.tv_over_time);
        tv_repair_time = (TextView) findViewById(R.id.tv_repair_time);
        snpl_form_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_form_photos);

        //遗留问题
        tv_remain_question = (TextView) findViewById(R.id.tv_remain_question);
        iv_left.setOnClickListener((v) -> finishSelf());
    }

    private void setData() {
        tv_over_time.setText(GetDateUtils.dateToDateString(bughandleConfirmEntity.getOverTime()));
        tv_repair_time.setText(bughandleConfirmEntity.getWorkHour());

        //遗留问题
        tv_remain_question.setText(bughandleConfirmEntity.getLeftoverProblem());
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
        quotationDetailAdapter = new TroubleDetailAdapter(R.layout.item_quotation_detail, mDataList);
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

