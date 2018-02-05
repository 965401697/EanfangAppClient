package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.config.Config;
import com.eanfang.model.QuotationBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PickerSelectUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.DeviceParamAdapter;
import net.eanfang.worker.ui.widget.InputNameAndValueView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:14
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class QuotationDetailActivity extends BaseActivity {

    private RelativeLayout rl_device_type, rl_business_type;
    private RelativeLayout rl_device_name;
    private RelativeLayout rl_brand_model;
    private RelativeLayout rl_unit;

    private EditText et_amount;
    private EditText et_price;
    private TextView tv_commit;
    private ArrayList<String> unitList = new ArrayList<>();
    private TextView tv_device_type, tv_business_type;
    private TextView tv_device_name;
    private TextView tv_brand_model;
    private TextView tv_unit, tv_add_params;
    private DeviceParamAdapter paramAdapter;
    private RecyclerView rl_params;
    private EditText et_product_company, et_factory, et_remark;


    private QuotationBean.QuoteDevicesBean bean;
    List<QuotationBean.QuoteDevicesBean.ParamsBean> paramsBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_detail);
        supprotToolbar();
        setTitle("报价明细");
        setLeftBack();
        initView();


    }


    private void initView() {
        rl_device_type = (RelativeLayout) findViewById(R.id.rl_device_type);
        rl_business_type = (RelativeLayout) findViewById(R.id.rl_business_type);
        rl_device_name = (RelativeLayout) findViewById(R.id.rl_device_name);
        rl_brand_model = (RelativeLayout) findViewById(R.id.rl_brand_model);
        rl_unit = (RelativeLayout) findViewById(R.id.rl_unit);
        et_amount = (EditText) findViewById(R.id.et_amount);
        et_price = (EditText) findViewById(R.id.et_price);
        et_factory = (EditText) findViewById(R.id.et_factory);
        et_product_company = (EditText) findViewById(R.id.et_product_company);
        et_remark = (EditText) findViewById(R.id.et_remark);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        tv_device_type = (TextView) findViewById(R.id.tv_device_type);
        tv_business_type = (TextView) findViewById(R.id.tv_business_type);
        tv_device_name = (TextView) findViewById(R.id.tv_device_name);
        tv_brand_model = (TextView) findViewById(R.id.tv_brand_model);
        tv_unit = (TextView) findViewById(R.id.tv_unit);
        tv_add_params = (TextView) findViewById(R.id.tv_add_params);
        rl_params = (RecyclerView) findViewById(R.id.rl_params);
        registerListener();
    }

    private void registerListener() {
        rl_business_type.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tv_business_type,
                    Stream.of(Config.get().getBusinessList(1)).map(bus -> bus.getDataName()).toList());
        });

//        rl_device_type.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showBusinessType();
//            }
//        });
//        rl_device_name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (StringUtils.isEmpty(tv_device_type.getText().toString().trim())) {
//                    showToast("请先选择设备类型");
//                } else {
//                    showBusiness2Type();
//                }
//            }
//        });
//        rl_brand_model.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (StringUtils.isEmpty(tv_device_type.getText().toString().trim())) {
//                    showToast("请先选择设备类型");
//                    return;
//                }
//                if (StringUtils.isEmpty(tv_device_name.getText().toString().trim())) {
//                    showToast("请先选择设备名称");
//                } else {
//                    showBusiness3Type();
//                }
//            }
//        });
        rl_unit.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tv_unit, GetConstDataUtils.getDeviceUnitList());
        });

        tv_add_params.setOnClickListener((v) -> {
            new InputNameAndValueView(QuotationDetailActivity.this, new InputNameAndValueView.onBtnClick() {
                @Override
                public void onClick(String name, String value) {
                    paramsBeanList = new ArrayList<>();
                    QuotationBean.QuoteDevicesBean.ParamsBean paramsBean = new QuotationBean.QuoteDevicesBean.ParamsBean();
                    paramsBean.setParamName(name);
                    paramsBean.setParamValue(value);
                    paramsBeanList.add(paramsBean);
                    initAdapter();
                }
            }).show();

        });
        tv_commit.setOnClickListener(v -> commit());
    }

    private void initAdapter() {
        paramAdapter = new DeviceParamAdapter(R.layout.item_deveice_parm, (ArrayList) paramsBeanList);
        rl_params.setLayoutManager(new LinearLayoutManager(this));
        rl_params.setNestedScrollingEnabled(false);
        rl_params.setHasFixedSize(true);
        rl_params.setAdapter(paramAdapter);
    }

    private void commit() {
        bean = new QuotationBean.QuoteDevicesBean();
        if (!checkInfo()) {
            return;
        }
        int count = Integer.parseInt(et_amount.getText().toString().trim());
        bean.setCount(count);
        bean.setModelCode(Config.get().getBusinessNameByCode(tv_business_type.getText().toString().trim(), 1));
//        bean.setBugoneuid(bugOneCode);
//        bean.setBugtwouid("");
//        bean.setBugtwoname(tv_device_type.getText().toString().trim());
//        bean.setBugthreeuid("");
//        bean.setBugthreename(tv_device_name.getText().toString().trim());
//        bean.setBugfouruid("");
//        bean.setBugfourname(tv_brand_model.getText().toString().trim());
        bean.setRemarkInfo(et_remark.getText().toString().trim());
        bean.setProducerPlace(et_factory.getText().toString().trim());
        bean.setProducerName(et_product_company.getText().toString().trim());
        bean.setUnit(GetConstDataUtils.getDeviceUnitList().indexOf(tv_unit.getText().toString().trim()));
        int unitPrice = Integer.parseInt(et_price.getText().toString().trim());
        bean.setUnitPrice(unitPrice);
        bean.setSum(unitPrice * count);
        bean.setParams(paramsBeanList);
        setResult(101, getIntent().putExtra("result", bean));
        finish();
    }


    public boolean checkInfo() {
//        if (StringUtils.isEmpty(tv_device_type.getText().toString().trim())) {
//            showToast("请选择设备类型");
//            return false;
//        }
//        if (StringUtils.isEmpty(tv_device_name.getText().toString().trim())) {
//            showToast("请选择设备名称");
//            return false;
//        }
//        if (StringUtils.isEmpty(tv_brand_model.getText().toString().trim())) {
//            showToast("请选择品牌型号");
//            return false;
//        }
//        if (StringUtils.isEmpty(tv_unit.getText().toString().trim())) {
//            showToast("请选择单位");
//            return false;
//        }
//        if (StringUtils.isEmpty(et_amount.getText().toString().trim())) {
//            showToast("请输入数量");
//            return false;
//        }
//        if (StringUtils.isEmpty(et_price.getText().toString().trim())) {
//            // showToast("请输入单价");
//            //return false;
//
//        }

        return true;

    }


}

