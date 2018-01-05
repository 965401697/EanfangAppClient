package net.eanfang.client.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eanfang.util.CallUtils;
import com.eanfang.util.GetDateUtils;

import net.eanfang.client.R;
import net.eanfang.client.config.Config;
import net.eanfang.client.config.Constant;
import net.eanfang.client.network.apiservice.RepairApi;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.OrderConfirmAdapter;
import net.eanfang.client.ui.base.BaseFragment;
import net.eanfang.client.ui.model.repair.RepairBugEntity;
import net.eanfang.client.ui.model.repair.RepairOrderEntity;
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
    private List<RepairBugEntity> mDataList = new ArrayList<>();
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
    private Long id;
    //2017年7月26日
    /**
     * 是否电话解决
     */
    private TextView tv_phone_solve;

    public static OrderDetailFragment getInstance(Long id) {
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

        BaseQuickAdapter evaluateAdapter = new OrderConfirmAdapter(R.layout.item_order_confirm, mDataList, "");
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_pic) {
                    ArrayList<String> picList = new ArrayList<String>();
                    String[] urls = mDataList.get(position).getPictures().split(",");
                    if (!StringUtils.isEmpty(urls[0])) {
                        picList.add(urls[0]);
                    }
                    if (!StringUtils.isEmpty(urls[1])) {
                        picList.add(urls[1]);
                    }
                    if (!StringUtils.isEmpty(urls[2])) {
                        picList.add(urls[2]);
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

        iv_phone.setOnClickListener(v ->
                CallUtils.call(getActivity(), iv_phone.getTag().toString())
        );
    }

    private void getData() {
        EanfangHttp.get(RepairApi.GET_REPAIR_DETAIL)
                .tag(this)
                .params("id", id)
                .execute(new EanfangCallback<RepairOrderEntity>(getActivity(), true, RepairOrderEntity.class, (bean) -> {
                    tv_company_name.setText(bean.getOwnerOrg().getBelongCompany().getOrgName());
                    tv_contract_name.setText(bean.getOwnerUser().getAccountEntity().getRealName());
                    tv_contract_phone.setText(bean.getOwnerUser().getAccountEntity().getMobile());
                    tv_time_limit.setText(Config.getConfig().getConstBean().getConst().get(Constant.ARRIVE_LIMIT).get(bean.getArriveTimeLimit()));
                    tv_address.setText(bean.getAddress());
                    if (bean.getBookTime() != null) {
                        tv_time.setText(GetDateUtils.dateToDateString(bean.getBookTime()));
                    }

                    tv_number.setText(bean.getOrderNum());
                    tv_feature_time.setText(GetDateUtils.dateToDateString(bean.getCreateTime()));
//                    tv_money.setText(bean.getTotalfee() + "");
//                    tv_alipay.setText(bean.getPaytype());
                    tv_phone_solve.setText(bean.getIsPhoneSolve());

                    //客户端
                    iv_pic.setImageURI(Uri.parse(bean.getAssigneeUser().getAccountEntity().getAvatar()));
                    tv_worker_name.setText(bean.getAssigneeUser().getAccountEntity().getRealName());
                    tv_worker_company.setText(bean.getAssigneeUser().getCompanyEntity().getOrgName());
                    iv_phone.setTag(bean.getAssigneeUser().getAccountEntity().getMobile());


                    mDataList = bean.getBugEntityList();
                    initAdapter();
                }));
    }

}
