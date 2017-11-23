package net.eanfang.client.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eanfang.util.CallUtils;

import net.eanfang.client.BuildConfig;
import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.OrderConfirmAdapter;
import net.eanfang.client.ui.base.BaseFragment;
import net.eanfang.client.ui.model.WorkspaceDetailBean;
import net.eanfang.client.util.ImagePerviewUtil;
import net.eanfang.client.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/23  19:48
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class OrderDetailFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager llm;
    private List<WorkspaceDetailBean.BugsBean> mDataList = new ArrayList<>();
    private TextView tv_company_name;
    private TextView tv_contract_name;
    private TextView tv_contract_phone;
    private TextView tv_time_limit;
    private TextView tv_address;
    private TextView tv_time;
    private android.support.v7.widget.RecyclerView rv_list;
    private com.facebook.drawee.view.SimpleDraweeView iv_pic;
    private TextView tv_worker_name;
    private TextView tv_worker_company;
    private ImageView iv_phone;
    private TextView tv_number;
    private TextView tv_feature_time;
    private TextView tv_money;
    private TextView tv_alipay;
    private int id;
    //2017年7月26日
    /**
     * 是否电话解决
     */
    private TextView tv_phone_solve;

    public static OrderDetailFragment getInstance(int id) {
        OrderDetailFragment sf = new OrderDetailFragment();
        sf.id = id;
        return sf;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_order_detail;
    }

    @Override
    protected void initData(Bundle arguments) {
        getData();
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_list);
        llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);

        tv_company_name = findViewById(R.id.tv_company_name);
        tv_contract_name = findViewById(R.id.tv_contract_name);
        tv_contract_phone = findViewById(R.id.tv_contract_phone);
        tv_time_limit = findViewById(R.id.tv_time_limit);
        tv_address = findViewById(R.id.tv_address);
        tv_time = findViewById(R.id.tv_time);
        rv_list = findViewById(R.id.rv_list);
        iv_pic = findViewById(R.id.iv_pic);
        tv_worker_name = findViewById(R.id.tv_worker_name);
        tv_worker_company = findViewById(R.id.tv_worker_company);
        iv_phone = findViewById(R.id.iv_phone);
        tv_number = findViewById(R.id.tv_number);
        tv_feature_time = findViewById(R.id.tv_feature_time);
        tv_money = findViewById(R.id.tv_money);
        tv_alipay = findViewById(R.id.tv_alipay);
        tv_phone_solve = findViewById(R.id.tv_phone_solve);

    }


    @Override
    protected void setListener() {

    }

    private void initAdapter() {
        if (mDataList.size() == 0) {
            return;
        }
        if (TextUtils.isEmpty(mDataList.get(0).getBugonename())) {

        }
        BaseQuickAdapter evaluateAdapter = new OrderConfirmAdapter(R.layout.item_order_confirm, mDataList, "");
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_pic) {
                    ArrayList<String> picList = new ArrayList<String>();
                    if (!StringUtils.isEmpty(mDataList.get(position).getBugpic1())) {
                        picList.add(mDataList.get(position).getBugpic1());
                    }
                    if (!StringUtils.isEmpty(mDataList.get(position).getBugpic2())) {
                        picList.add(mDataList.get(position).getBugpic2());
                    }
                    if (!StringUtils.isEmpty(mDataList.get(position).getBugpic3())) {
                        picList.add(mDataList.get(position).getBugpic3());
                    }
                    if (picList.size() == 0) {
                        showToast("当前没有图片");
                        return;
                    }
                    ImagePerviewUtil.perviewImage(getActivity(), picList);
                }
                if (view.getId() == R.id.ll_item) {
                    View secondItem = llm.findViewByPosition(position).findViewById(R.id.second_item);
                    if (secondItem.getVisibility() == View.VISIBLE) {
                        secondItem.setVisibility(View.GONE);
                    } else {
                        secondItem.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        mRecyclerView.setAdapter(evaluateAdapter);

        iv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallUtils.call(getActivity(), iv_phone.getTag().toString());
            }
        });
    }

    private void getData() {
        EanfangHttp.get(ApiService.GET_REAIR_ORDER_DETAIL)
                .tag(this)
                .params("orderId", id)
                .execute(new EanfangCallback<WorkspaceDetailBean>(getActivity(), true) {

                    @Override
                    public void onSuccess(WorkspaceDetailBean bean) {
                        tv_company_name.setText(bean.getOrder().getCompanyname());
                        tv_contract_name.setText(bean.getOrder().getClientconnector());
                        tv_contract_phone.setText(bean.getOrder().getClientphone());
                        tv_time_limit.setText(bean.getOrder().getArrivetime());
                        tv_address.setText(bean.getOrder().getDetailplace());
                        tv_time.setText(bean.getOrder().getPretime());
                        tv_number.setText(bean.getOrder().getOrdernum());
                        tv_feature_time.setText(bean.getOrder().getCreatetime());
                        tv_money.setText(bean.getOrder().getTotalfee() + "");
                        tv_alipay.setText(bean.getOrder().getPaytype());
                        tv_phone_solve.setText(bean.getOrder().getPhonesolve());

                        //客户端
                        if (BuildConfig.APP_TYPE == 0) {
                            iv_pic.setImageURI(Uri.parse(bean.getWorkerHeadPic()));
                            tv_worker_name.setText(bean.getWorkerRealName());
                            tv_worker_company.setText(bean.getWorkerCompanyName());
                            iv_phone.setTag(bean.getWorkerPhone());
                        } else {//技师端
                            iv_pic.setImageURI(Uri.parse(bean.getClientHeadPic()));
                            tv_worker_name.setText(bean.getClientRealName());
                            tv_worker_company.setText(bean.getClientCompanyName());
                            iv_phone.setTag(bean.getClientPhone());
                        }

                        mDataList = bean.getBugs();
                        initAdapter();
                    }

                    @Override
                    public void onError(String message) {
                        showToast(message);
                    }
                });
    }

}
