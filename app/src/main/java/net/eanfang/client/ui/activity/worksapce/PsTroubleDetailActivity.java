package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.util.CallUtils;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.TroubleDetailAdapter;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.WorkspaceDetailBean;
import net.eanfang.client.util.ImagePerviewUtil;
import net.eanfang.client.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  10:53
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class PsTroubleDetailActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView rv_trouble;
    private TextView tv_over_time;
    private TextView tv_repair_time;
    /**
     * 电视墙/操作台正面全貌 (3张)
     */
    private BGASortableNinePhotoLayout snpl_moment_add_photos;
    /**
     * 电视墙/操作台背面全照(3张)
     */
    private BGASortableNinePhotoLayout snpl_monitor_add_photos;
    /**
     * 机柜正面/背面 (3张)
     */
    private BGASortableNinePhotoLayout snpl_tools_package_add_photos;

    //2017年7月20日
    private TextView tv_complete;
    private TextView tv_complaint;
    //录像机天数
    private TextView tv_store_time;
    //报警打印功能
    private TextView tv_print_on_alarm;
    //所有设备时间同步
    private TextView tv_time_right;
    //各类设备数据远传功能
    private TextView tv_machine_data_remote;
    //遗留问题
    private TextView tv_remain_question;
    //2017年7月21日
    //协助人员
    private TextView tv_team_worker;


    private List<WorkspaceDetailBean.BughandledetaillistBean> mDataList;
    private TroubleDetailAdapter quotationDetailAdapter;
    private WorkspaceDetailBean workspaceDetailBean;
    private int id;
    private String status;
    private String phoneSovle;
    private LinearLayout ll_gone_a, ll_gone_b;
    /**
     * 电视墙/操作台正面全貌 (3张)
     */
    private ArrayList<String> picList1;
    /**
     * 电视墙/操作台背面全照(3张)
     */
    private ArrayList<String> picList2;
    /**
     * 机柜正面/背面 (3张)
     */
    private ArrayList<String> picList3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_fill_repair_info);

        id = getIntent().getIntExtra("orderId", 0);
        status = getIntent().getStringExtra("status");
        phoneSovle = getIntent().getStringExtra("phoneSolve");
        initView();
        getData(id);
        supprotToolbar();
        setTitle("故障处理");

    }

    private void initView() {
        ll_gone_a = (LinearLayout) findViewById(R.id.ll_gone_a);
        ll_gone_b = (LinearLayout) findViewById(R.id.ll_gone_b);
        ll_gone_a.setVisibility(View.GONE);
        ll_gone_b.setVisibility(View.GONE);
        rv_trouble = (RecyclerView) findViewById(R.id.rv_trouble);
        tv_over_time = (TextView) findViewById(R.id.tv_over_time);
        tv_repair_time = (TextView) findViewById(R.id.tv_repair_time);
        snpl_moment_add_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_moment_add_photos);
        snpl_monitor_add_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_monitor_add_photos);
        snpl_tools_package_add_photos = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_tools_package_add_photos);

        //2017年7月20日
        //两个操作按钮
        tv_complete = (TextView) findViewById(R.id.tv_complete);
        tv_complaint = (TextView) findViewById(R.id.tv_complaint);
        //录像机天数
        tv_store_time = (TextView) findViewById(R.id.tv_store_time);
        //报警打印功能
        tv_print_on_alarm = (TextView) findViewById(R.id.tv_print_on_alarm);
        //所有设备时间同步
        tv_time_right = (TextView) findViewById(R.id.tv_time_right);
        //各类设备数据远传功能
        tv_machine_data_remote = (TextView) findViewById(R.id.tv_machine_data_remote);
        //遗留问题
        tv_remain_question = (TextView) findViewById(R.id.tv_remain_question);
        //协作人员
        tv_team_worker = (TextView) findViewById(R.id.tv_team_worker);

        //非单确认状态 隐藏确认按钮
        if (!status.equals("待确认")) {
            findViewById(R.id.rl_client_option).setVisibility(View.GONE);
            tv_complete.setVisibility(View.INVISIBLE);
            tv_complaint.setVisibility(View.INVISIBLE);
        }
        tv_complete.setOnClickListener(this);
        tv_complaint.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_complete:

                JSONObject object = new JSONObject();
                try {
                    object.put("ordernum", workspaceDetailBean.getOrder().getOrdernum());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                EanfangHttp.post(ApiService.TO_CONFIRM)
                        .tag(this)
                        .params("json", object.toString())
                        .execute(new EanfangCallback(PsTroubleDetailActivity.this, false) {
                            @Override
                            public void onSuccess(Object bean) {
                                super.onSuccess(bean);
                                showToast("确认成功");
                                finish();
                            }
                        });
                break;
            case R.id.tv_complaint:
                CallUtils.call(this, "010-5877-8731");
                break;
            default:
                break;
        }
    }


    private void getData(int id) {
        EanfangHttp.get(ApiService.GET_REAIR_ORDER_DETAIL)
                .tag(this)
                .params("orderId", id)
                .execute(new EanfangCallback<WorkspaceDetailBean>(this, true) {

                    @Override
                    public void onSuccess(WorkspaceDetailBean bean) {
                        setData(bean);
                    }
                });
    }

    private void setData(WorkspaceDetailBean detailBean) {
        workspaceDetailBean = detailBean;
        picList1 = new ArrayList<>();
        picList2 = new ArrayList<>();
        picList3 = new ArrayList<>();
        if (detailBean.getBughandleconfirm() != null) {
            WorkspaceDetailBean.BughandleconfirmBean detailzBean = detailBean.getBughandleconfirm();
            mDataList = detailBean.getBughandledetaillist();

            if (StringUtils.isValid(detailzBean.getPic1())) {
                picList1.add(detailzBean.getPic1());
            }
            if (StringUtils.isValid(detailzBean.getPic2())) {
                picList1.add(detailzBean.getPic2());
            }
            if (StringUtils.isValid(detailzBean.getPic3())) {
                picList1.add(detailzBean.getPic3());
            }
            if (StringUtils.isValid(detailzBean.getPic4())) {
                picList2.add(detailzBean.getPic4());
            }
            if (StringUtils.isValid(detailzBean.getPic5())) {
                picList2.add(detailzBean.getPic5());
            }
            if (StringUtils.isValid(detailzBean.getPic6())) {
                picList2.add(detailzBean.getPic6());
            }
            if (StringUtils.isValid(detailzBean.getPic7())) {
                picList3.add(detailzBean.getPic7());
            }
            if (StringUtils.isValid(detailzBean.getPic8())) {
                picList3.add(detailzBean.getPic8());
            }
            if (StringUtils.isValid(detailzBean.getPic9())) {
                picList3.add(detailzBean.getPic9());
            }
            initAdapter();
            fillData(detailBean);
            initNinePhoto();
        } else {
            showToast("没有数据。。");
            return;

        }
    }

    private void fillData(WorkspaceDetailBean bean) {
        tv_over_time.setText(bean.getBughandleconfirm().getOvertime());
        tv_repair_time.setText(bean.getBughandleconfirm().getWorktime());
        //录像机天数
        tv_store_time.setText(bean.getBughandleconfirm().getStoretime());
        //报警打印功能
        tv_print_on_alarm.setText(bean.getBughandleconfirm().getPrintonalarm());
        //所有设备时间同步
        tv_time_right.setText(bean.getBughandleconfirm().getTimeright());
        //各类设备数据远传功能
        tv_machine_data_remote.setText(bean.getBughandleconfirm().getMachinedataremote());
        //遗留问题
        tv_remain_question.setText(bean.getBughandleconfirm().getRemainquestion());
        //协作人员
        tv_team_worker.setText(bean.getBughandleconfirm().getTeamworker());
    }


    private void initAdapter() {
        quotationDetailAdapter = new TroubleDetailAdapter(R.layout.item_quotation_detail, mDataList);
        rv_trouble.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rv_trouble.setLayoutManager(new LinearLayoutManager(this));
        rv_trouble.setAdapter(quotationDetailAdapter);
        rv_trouble.addOnItemTouchListener(onItemClickListener);
    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            Intent intent;
            if (phoneSovle.equals("否")) {
                intent = new Intent(PsTroubleDetailActivity.this, LookTroubleDetailActivity.class);

            } else {
                intent = new Intent(PsTroubleDetailActivity.this, PhoneLookTroubleDetailActivity.class);

            }
            intent.putExtra("bean", workspaceDetailBean.getBughandledetaillist().get(position));
            startActivity(intent);
        }
    };


    private void initNinePhoto() {
        snpl_moment_add_photos.setData(picList1);
        snpl_monitor_add_photos.setData(picList2);
        snpl_tools_package_add_photos.setData(picList3);

        snpl_moment_add_photos.setEditable(false);
        snpl_monitor_add_photos.setEditable(false);
        snpl_tools_package_add_photos.setEditable(false);

        snpl_moment_add_photos.setDelegate(new BGASortableNinePhotoLayout.Delegate() {
            @Override
            public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {

            }

            @Override
            public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {

            }

            @Override
            public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
                ImagePerviewUtil.perviewImage(PsTroubleDetailActivity.this, models);
            }
        });

        snpl_monitor_add_photos.setDelegate(new BGASortableNinePhotoLayout.Delegate() {
            @Override
            public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {

            }

            @Override
            public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {

            }

            @Override
            public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
                ImagePerviewUtil.perviewImage(PsTroubleDetailActivity.this, models);
            }
        });

        snpl_tools_package_add_photos.setDelegate(new BGASortableNinePhotoLayout.Delegate() {
            @Override
            public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {

            }

            @Override
            public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {

            }

            @Override
            public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
                ImagePerviewUtil.perviewImage(PsTroubleDetailActivity.this, models);
            }
        });
    }
}
