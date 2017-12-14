package net.eanfang.client.ui.activity.worksapce;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.CompanyDetailAdapter;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.CompanyDetailBean;
import net.eanfang.client.ui.model.InstallOrderConfirmBean;
import net.eanfang.client.util.PerviewUtil;
import net.eanfang.client.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 公司详情
 * Created by Administrator on 2017/4/8.
 */

public class CompanyDetailActivity extends BaseActivity implements View.OnClickListener {

    private String id;

    private CompanyDetailBean companyDetailBean;
    private SimpleDraweeView iv_header;
    private TextView tv_name;
    private TextView tv_haopinglv;
    private TextView tv_koubei;
    private TextView tv_install_number;
    private TextView tv_repair_number;
    private TextView tv_evaluate_number;
    private TextView tv_info;
    private TextView tv_guimo;
    private RecyclerView rv_list1;
    private TextView tv_item1;
    private LinearLayout ll_item1;
    private TextView tv_item2;
    private LinearLayout ll_item2;
    private RecyclerView rv_list3;
    private TextView tv_address;
    private TextView tv_level;
    private TextView tv_year;
    private SimpleDraweeView iv_license;
    private SimpleDraweeView iv_zizhi1;
    private SimpleDraweeView iv_zizhi2;
    private SimpleDraweeView honor1;
    private SimpleDraweeView iv_honor2;
    private SimpleDraweeView iv_honor3;
    private TextView tv_select;
    private ArrayList<String> mDataList1;
    private ArrayList<String> mDataList3;
    private LinearLayout ll_repair;
    private LinearLayout ll_install;
    private InstallOrderConfirmBean installOrderConfirmBean;
    private LinearLayout ll_area;
    private ImageView iv_down;

    public static void jumpActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, CompanyDetailActivity.class);
//        intent.putExtra("id", EanfangApplication.get().getUser().getCompanyId());
        ((BaseActivity) context).startAnimActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);

        getData();
        initView();
        setListener();
        initData();

        setTitle("公司详情");
        setLeftBack();

    }

    private void setListener() {
        if (installOrderConfirmBean == null) {
            tv_select.setVisibility(View.GONE);
//            return;
        }
        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                installOrderConfirmBean.setWorkercompanyuid(id);
                Intent intent = new Intent(CompanyDetailActivity.this, OrderConfirmActivity.class);
                intent.putExtra("bean", installOrderConfirmBean);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        ll_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rv_list1.getVisibility() == View.VISIBLE) {
                    rv_list1.setVisibility(View.GONE);
                    iv_down.setImageResource(R.mipmap.arrow_down);
                } else {
                    rv_list1.setVisibility(View.VISIBLE);
                    iv_down.setImageResource(R.mipmap.arrow_up);
                }
            }
        });
    }

    private void initView() {
        ll_repair = (LinearLayout) findViewById(R.id.install_repair).findViewById(R.id.ll_item2);
        ll_install = (LinearLayout) findViewById(R.id.install_repair).findViewById(R.id.ll_item1);
        iv_header = (SimpleDraweeView) findViewById(R.id.iv_header);
        iv_header.setOnClickListener(this);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name.setOnClickListener(this);
        tv_haopinglv = (TextView) findViewById(R.id.tv_haopinglv);
        tv_haopinglv.setOnClickListener(this);
        tv_koubei = (TextView) findViewById(R.id.tv_koubei);
        tv_koubei.setOnClickListener(this);
        tv_install_number = (TextView) findViewById(R.id.tv_install_number);
        tv_install_number.setOnClickListener(this);
        tv_repair_number = (TextView) findViewById(R.id.tv_repair_number);
        tv_repair_number.setOnClickListener(this);
        tv_evaluate_number = (TextView) findViewById(R.id.tv_evaluate_number);
        tv_evaluate_number.setOnClickListener(this);
        tv_info = (TextView) findViewById(R.id.tv_info);
        tv_info.setOnClickListener(this);
        tv_guimo = (TextView) findViewById(R.id.tv_guimo);
        tv_guimo.setOnClickListener(this);
        rv_list1 = (RecyclerView) findViewById(R.id.rv_list1);
        rv_list1.setOnClickListener(this);
        tv_item1 = (TextView) findViewById(R.id.tv_item1);
        tv_item1.setOnClickListener(this);
        ll_item1 = (LinearLayout) findViewById(R.id.ll_item1);
        ll_item1.setOnClickListener(this);
        tv_item2 = (TextView) findViewById(R.id.tv_item2);
        tv_item2.setOnClickListener(this);
        ll_item2 = (LinearLayout) findViewById(R.id.ll_item2);
        ll_item2.setOnClickListener(this);
        rv_list3 = (RecyclerView) findViewById(R.id.rv_list3);
        rv_list3.setOnClickListener(this);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_address.setOnClickListener(this);
        tv_level = (TextView) findViewById(R.id.tv_level);
        tv_level.setOnClickListener(this);
        tv_year = (TextView) findViewById(R.id.tv_year);
        tv_year.setOnClickListener(this);
        iv_license = (SimpleDraweeView) findViewById(R.id.iv_license);
        iv_license.setOnClickListener(this);
        iv_zizhi1 = (SimpleDraweeView) findViewById(R.id.iv_zizhi1);
        iv_zizhi1.setOnClickListener(this);
        iv_zizhi2 = (SimpleDraweeView) findViewById(R.id.iv_zizhi2);
        iv_zizhi2.setOnClickListener(this);
        honor1 = (SimpleDraweeView) findViewById(R.id.honor1);
        honor1.setOnClickListener(this);
        iv_honor2 = (SimpleDraweeView) findViewById(R.id.iv_honor2);
        iv_honor3 = (SimpleDraweeView) findViewById(R.id.iv_honor3);
        iv_honor2.setOnClickListener(this);
        tv_select = (TextView) findViewById(R.id.tv_select);
        ll_area = (LinearLayout) findViewById(R.id.ll_area);
        iv_down = (ImageView) findViewById(R.id.iv_down);

        rv_list1.setLayoutManager(new GridLayoutManager(this, 2));
        rv_list3.setLayoutManager(new GridLayoutManager(this, 2));

        if (installOrderConfirmBean != null) {
            setRightImageResId(R.mipmap.heart);
        }
        setRightImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EanfangHttp.get(ApiService.COLLECTION_COMPANY_WORK)
                        .tag(this)
                        .params("fixcompanyuid", id)
                        .execute(new EanfangCallback(CompanyDetailActivity.this, false) {
                            @Override
                            public void onSuccess(Object bean) {
                                super.onSuccess(bean);
                                showToast("收藏成功");
                                setRightImageResId(R.mipmap.hearted);
                                setRightImageOnClickListener((v) -> {
                                            showToast("已经收藏过该公司");
                                        }
                                );
                            }

                            @Override
                            public void onFail(Integer code, String message, JSONObject jsonObject) {
                                super.onFail(code, message, jsonObject);
                                showToast(message);
                            }
                        });
            }
        });

    }

    private void initData() {

        JSONObject object = new JSONObject();
        try {
            object.put("uid", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        EanfangHttp.get(ApiService.COMPANY_DETAIL)
                .tag(this)
                .params("json", object.toString())
                .execute(new EanfangCallback<CompanyDetailBean>(this, false) {
                    @Override
                    public void onSuccess(CompanyDetailBean bean) {
                        super.onSuccess(bean);
                        companyDetailBean = bean;
                        setData();
                    }

                    @Override
                    public void onFail(Integer code, String message, JSONObject jsonObject) {
                        super.onFail(code, message, jsonObject);
                        showToast(message);
                    }
                });

    }

    private void initAdapter() {
        BaseQuickAdapter evaluateAdapter1 = new CompanyDetailAdapter(R.layout.item_worker_detail1, mDataList1);
        rv_list1.setAdapter(evaluateAdapter1);

        BaseQuickAdapter evaluateAdapter3 = new CompanyDetailAdapter(R.layout.item_worker_detail1, mDataList3);
        rv_list3.setAdapter(evaluateAdapter3);
    }

    private void getData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        installOrderConfirmBean = (InstallOrderConfirmBean) intent.getSerializableExtra("bean");
    }


    private void setData() {

        if ("1".equals(companyDetailBean.getCollected())) {
            setRightImageResId(R.mipmap.hearted);
            setRightImageOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("已经收藏过该公司");
                }
            });
        }
        tv_name.setText(companyDetailBean.getCompanyname());
        if (StringUtils.isEmpty(companyDetailBean.getGoodpercent())) {
            tv_haopinglv.setText("100%");
        } else {
            tv_haopinglv.setText(Double.parseDouble(companyDetailBean.getGoodpercent()) * 100 + "%");
        }
        if (StringUtils.isEmpty(companyDetailBean.getPraise())) {
            tv_koubei.setText("5.0分");
        } else {
            tv_koubei.setText(companyDetailBean.getPraise());
        }

        //公司宣传图
        iv_header.setImageURI(companyDetailBean.getAdpic());

        tv_install_number.setText(companyDetailBean.getInstallamount() + "次");
        tv_repair_number.setText(companyDetailBean.getRepairamount() + "次");
        tv_evaluate_number.setText(companyDetailBean.getReviewamount() + "次");
        tv_info.setText(companyDetailBean.getDescription());
        tv_guimo.setText(companyDetailBean.getCompanyscale());
        tv_address.setText(companyDetailBean.getRegisterprovince() + companyDetailBean.getRegistercity() + companyDetailBean.getRegisterdetailplace());
        tv_level.setText(companyDetailBean.getWorklevel());
        tv_year.setText(companyDetailBean.getWorkyear());

        //companyDetailBean.

        if (!StringUtils.isEmpty(companyDetailBean.getLicencepic())) {
            iv_license.setImageURI(Uri.parse(companyDetailBean.getLicencepic()));
        }

        if (!StringUtils.isEmpty(companyDetailBean.getZizhipic1())) {
            iv_zizhi1.setImageURI(Uri.parse(companyDetailBean.getZizhipic1()));
        } else {
            iv_zizhi1.setVisibility(View.GONE);
        }

        if (!StringUtils.isEmpty(companyDetailBean.getZizhipic2())) {
            iv_zizhi2.setImageURI(Uri.parse(companyDetailBean.getZizhipic2()));
        } else {
            iv_zizhi2.setVisibility(View.GONE);
        }

        if (!StringUtils.isEmpty(companyDetailBean.getHonorpic1())) {
            honor1.setImageURI(companyDetailBean.getHonorpic1());
        } else {
            honor1.setVisibility(View.GONE);
        }
        if (!StringUtils.isEmpty(companyDetailBean.getHonorpic2())) {
            iv_honor2.setImageURI(companyDetailBean.getHonorpic2());
        } else {
            iv_honor2.setVisibility(View.GONE);
        }
        if (!StringUtils.isEmpty(companyDetailBean.getHonorpic3())) {
            iv_honor3.setImageURI(companyDetailBean.getHonorpic3());
        } else {
            iv_honor3.setVisibility(View.GONE);
        }

        mDataList1 = new ArrayList<>();
        mDataList1.clear();
        List<CompanyDetailBean.ServiceplaceBean> serviceplaceBeanList = companyDetailBean.getServiceplace();
        for (int i = 0; i < serviceplaceBeanList.size(); i++) {
            mDataList1.add(serviceplaceBeanList.get(i).getCity() + "-" + serviceplaceBeanList.get(i).getZone());
        }

        //解析公司详情里的业务类型
        String companyBusiness = companyDetailBean.getBusinesstype();
        String[] businessCodeArray = companyBusiness.split(",");
        mDataList3 = new ArrayList<>();
        mDataList3.clear();

        for (int i = 0; i < businessCodeArray.length; i++) {
//            mDataList3.add(GetConstDataUtils.getBugOneNameByCode(businessCodeArray[i]));
        }

        if ("0".equals(companyDetailBean.getServicetypeinstall())) {
            ll_install.setVisibility(View.GONE);
        }
        if ("0".equals(companyDetailBean.getServicetyperepair())) {
            ll_repair.setVisibility(View.GONE);
        }
        initAdapter();
    }

    @Override
    public void onClick(View v) {
        ArrayList<String> images;
        switch (v.getId()) {

            //公司宣传图
            case R.id.iv_header:
                PerviewUtil.perviewImageUitl(companyDetailBean.getAdpic(), getApplicationContext());
                break;
            //公司营业执照
            case R.id.iv_license:
                PerviewUtil.perviewImageUitl(companyDetailBean.getLicencepic(), getApplicationContext());
                break;
            //公司资质1
            case R.id.iv_zizhi1:
                PerviewUtil.perviewImageUitl(companyDetailBean.getZizhipic1(), getApplicationContext());
                break;
            //公司资质2
            case R.id.iv_zizhi2:
                PerviewUtil.perviewImageUitl(companyDetailBean.getZizhipic2(), getApplicationContext());
                break;
            //公司荣誉证书1
            case R.id.honor1:
                PerviewUtil.perviewImageUitl(companyDetailBean.getHonorpic1(), getApplicationContext());
                break;
            //公司荣誉证书2
            case R.id.iv_honor2:
                PerviewUtil.perviewImageUitl(companyDetailBean.getHonorpic2(), getApplicationContext());
                break;
            //公司荣誉证书3
            case R.id.iv_honor3:
                PerviewUtil.perviewImageUitl(companyDetailBean.getHonorpic3(), getApplicationContext());
                break;
            default:
                break;
        }


    }
}
