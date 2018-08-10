package net.eanfang.worker.ui.activity.worksapce.repair.seefaultdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.BughandleConfirmEntity;
import com.yaf.base.entity.BughandleDetailEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.im.SelectIMContactActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.seefaultdetail.faultdetail.PhoneLookTroubleDetailActivity;
import net.eanfang.worker.ui.adapter.FillTroubleDetailAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  10:53
 * @email houzhongzhou@yeah.net
 * @desc 电话解决
 */

public class PsTroubleDetailActivity extends BaseWorkerActivity /*implements View.OnClickListener */ {

    @BindView(R.id.rv_trouble)
    RecyclerView rvTrouble;
    @BindView(R.id.tv_remain_question)
    TextView tvRemainQuestion;
    /**
     * 单据照片 (3张)
     */
    @BindView(R.id.snpl_form_photos)
    BGASortableNinePhotoLayout snplFormPhotos;


    //遗留问题
    private List<BughandleDetailEntity> mDataList;
    private FillTroubleDetailAdapter quotationDetailAdapter;
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
    /**
     * 单据照片 (3张)
     */
    private ArrayList<String> picList4 = new ArrayList<>();

    //聊天分享的必要参数
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ps_client_fill_repair_info);
        ButterKnife.bind(this);
        initView();
        initData();

        if (!getIntent().getBooleanExtra("isVisible", false)) {
            setRightTitle("分享");
            setRightTitleOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(PsTroubleDetailActivity.this, SelectIMContactActivity.class);

                    bundle.putString("id", String.valueOf(bughandleConfirmEntity.getBusRepairOrderId()));
                    bundle.putString("orderNum", (String) bughandleConfirmEntity.getDetailEntityList().get(0).getFailureEntity().getDeviceName());
                    if (bughandleConfirmEntity.getDetailEntityList() != null && bughandleConfirmEntity.getDetailEntityList().size() > 0) {
                        bundle.putString("picUrl", bughandleConfirmEntity.getDetailEntityList().get(0).getFailureEntity().getPictures().split(",")[0]);
                    }
                    bundle.putString("creatTime", bughandleConfirmEntity.getDetailEntityList().get(0).getFailureEntity().getBugPosition());
                    if (bughandleConfirmEntity.getDetailEntityList().get(0).getFailureEntity().getRepairCount()!=null) {
                        bundle.putString("workerName", String.valueOf(bughandleConfirmEntity.getDetailEntityList().get(0).getFailureEntity().getRepairCount()));
                    }
                    bundle.putString("status", String.valueOf(1));//电话解决
                    bundle.putString("shareType", "2");

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    }

    private void initView() {
        setTitle("故障处理");
        setLeftBack();
        id = getIntent().getLongExtra("orderId", 0);
    }

    private void initData() {
        EanfangHttp.get(RepairApi.GET_BUGHANDLE_DETAIL)
                .params("id", id)
                .execute(new EanfangCallback<BughandleConfirmEntity>(this, true, BughandleConfirmEntity.class, (bean) -> {
                    bughandleConfirmEntity = bean;
                    setData();
                }));
    }

    private void setData() {
        //遗留问题
        tvRemainQuestion.setText(bughandleConfirmEntity.getLeftoverProblem());
        initAdapter();
        if (bughandleConfirmEntity.getInvoicesPictures() != null) {
            String[] invoicesPic = bughandleConfirmEntity.getInvoicesPictures().split(",");
            picList4.addAll(Stream.of(Arrays.asList(invoicesPic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }
        initNinePhoto();
    }

    private void initAdapter() {
        mDataList = bughandleConfirmEntity.getDetailEntityList();
        quotationDetailAdapter = new FillTroubleDetailAdapter(R.layout.layout_trouble_detail, mDataList);
        rvTrouble.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rvTrouble.setLayoutManager(new LinearLayoutManager(this));
        rvTrouble.setAdapter(quotationDetailAdapter);
        rvTrouble.addOnItemTouchListener(onItemClickListener);
    }

    private void initNinePhoto() {
        snplFormPhotos.setData(picList4);
        snplFormPhotos.setEditable(false);
        snplFormPhotos.setDelegate(new BGASortableDelegate(this));
    }
}

