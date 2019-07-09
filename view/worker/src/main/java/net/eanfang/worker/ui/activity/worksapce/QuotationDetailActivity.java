package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.biz.model.QuotationBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;

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

    private RelativeLayout rl_device_type, rl_business_type, rl_device_name, rl_brand_model, rl_unit;
    private TextView tv_device_type, tv_business_type, tv_commit, tv_device_name, tv_brand_model, tv_unit;
    private ImageView iv_add_params;
    private DeviceParamAdapter paramAdapter;
    private RecyclerView rl_params;
    private EditText et_product_company, et_factory, et_remark, et_amount, et_price;
    private QuotationBean.QuoteDevicesBean bean = new QuotationBean.QuoteDevicesBean();
    List<QuotationBean.QuoteDevicesBean.ParamsBean> paramsBeanList = new ArrayList<>();

    private final String PLEASE_SELECLT = "请选择";
    private final String PLEASE_INPUT = "请输入";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_quotation_detail);
        super.onCreate(savedInstanceState);
        setTitle("报价明细");
        setLeftBack();
        initView();


    }


    private void initView() {
        rl_device_type = findViewById(R.id.rl_device_type);
        rl_business_type = findViewById(R.id.rl_business_type);
        rl_device_name = findViewById(R.id.rl_device_name);
        rl_brand_model = findViewById(R.id.rl_brand_model);
        rl_unit = findViewById(R.id.rl_unit);
        et_amount = findViewById(R.id.et_amount);
        et_price = findViewById(R.id.et_price);
        et_factory = findViewById(R.id.et_factory);
        et_product_company = findViewById(R.id.et_product_company);
        et_remark = findViewById(R.id.et_remark);
        tv_commit = findViewById(R.id.tv_commit);
        tv_device_type = findViewById(R.id.tv_device_type);
        tv_business_type = findViewById(R.id.tv_business_type);
        tv_device_name = findViewById(R.id.tv_device_name);
        tv_brand_model = findViewById(R.id.tv_brand_model);
        tv_unit = findViewById(R.id.tv_unit);
        iv_add_params = findViewById(R.id.iv_add_params);
        rl_params = findViewById(R.id.rl_params);
        registerListener();
        initAdapter();
    }

    private void registerListener() {

        //系统类别
        rl_business_type.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getBusinessList(1)).map(bus -> bus.getDataName()).toList(), (index, item) -> {
                tv_business_type.setText(item);
                tv_device_type.setText("请输入");
                tv_device_name.setText("请输入");
                tv_brand_model.setText("请输入");
            });
        });

        //故障设备类别
        rl_device_type.setOnClickListener((v) -> {
            String busOneCode = Config.get().getBusinessCodeByName(tv_business_type.getText().toString().trim(), 1);
            if (StringUtils.isEmpty(busOneCode)) {
                showToast("请先选择系统类别");
                return;
            }
            PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getBusinessList(2)).filter(bus -> bus.getDataCode().startsWith(busOneCode)).map(bus -> bus.getDataName()).toList(), ((index, item) -> {
                tv_device_type.setText(item);
                tv_device_name.setText("请输入");
                tv_brand_model.setText("请输入");
            }));
        });
        //故障设备名称
        rl_device_name.setOnClickListener((v) -> {
            String busTwoCode = Config.get().getBusinessCodeByName(tv_device_type.getText().toString().trim(), 2);
            if (StringUtils.isEmpty(busTwoCode)) {
                showToast("请先选择设备类别");
                return;
            }
            PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getBusinessList(3)).filter(bus -> bus.getDataCode().startsWith(busTwoCode)).map(bus -> bus.getDataName()).toList(), ((index, item) -> {
                tv_device_name.setText(item);
                tv_brand_model.setText("请输入");
            }));
        });
        //品牌型号
        rl_brand_model.setOnClickListener((v) -> {
            List<String> busOneCodeList = Config.get().getBaseCodeByName(tv_business_type.getText().toString().trim(), 1, Constant.MODEL);
            if (busOneCodeList.size() == 0) {
                showToast("请先选择系统类别");
                return;
            }
            String busOneCode = Config.get().getBaseCodeByName(tv_business_type.getText().toString().trim(), 1, Constant.MODEL).get(0);
            PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getModelList(2)).filter(bus -> bus.getDataCode().startsWith(busOneCode)).map(bus -> bus.getDataName()).toList(), ((index, item) -> {
                tv_brand_model.setText(item);
            }));

        });
        rl_unit.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tv_unit, GetConstDataUtils.getDeviceUnitList());
        });

        iv_add_params.setOnClickListener((v) -> {
            new InputNameAndValueView(QuotationDetailActivity.this, (name, value) -> {
                QuotationBean.QuoteDevicesBean.ParamsBean paramsBean = new QuotationBean.QuoteDevicesBean.ParamsBean();
                paramsBean.setParamName(name);
                paramsBean.setParamValue(value);
                paramsBeanList.add(paramsBean);
                paramAdapter.notifyDataSetChanged();
            }).show();

        });
        tv_commit.setOnClickListener(v -> commit());
    }

    private void initAdapter() {
        paramAdapter = new DeviceParamAdapter(R.layout.item_deveice_parm, (ArrayList) paramsBeanList);
        rl_params.setLayoutManager(new LinearLayoutManager(this));
        rl_params.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rl_params.setAdapter(paramAdapter);
    }

    private void commit() {
        if (!checkInfo()) {
            return;
        }
        int count = Integer.parseInt(et_amount.getText().toString().trim());
        bean.setCount(count);
        bean.setModelCode(Config.get().getBaseCodeByName(tv_brand_model.getText().toString().trim(), 2, Constant.MODEL).get(0));
        bean.setBusiness_three_code(Config.get().getBusinessCodeByName(tv_device_name.getText().toString().trim(), 3));
        bean.setRemarkInfo(et_remark.getText().toString().trim());
        bean.setProducerPlace(et_factory.getText().toString().trim());
        bean.setProducerName(et_product_company.getText().toString().trim());
        bean.setUnit(GetConstDataUtils.getDeviceUnitList().indexOf(tv_unit.getText().toString().trim()));
        int unitPrice = Integer.valueOf(et_price.getText().toString().trim());
        bean.setUnitPrice(unitPrice * 100);
        bean.setSum((unitPrice * count) * 100);
//        paramsBeanList.addAll(paramsBeanList);
        bean.setParams(paramsBeanList);
        setResult(2, getIntent().putExtra("quotedevices", bean));
        finish();
    }


    public boolean checkInfo() {

        if (tv_business_type.getText().toString().trim().equals(PLEASE_SELECLT)) {
            showToast("请先选择系统类别");
            return false;
        }

        if (tv_device_type.getText().toString().trim().equals(PLEASE_INPUT)) {
            showToast("请选择设备类型");
            return false;
        }
        if (tv_device_name.getText().toString().trim().equals(PLEASE_INPUT)) {
            showToast("请选择设备名称");
            return false;
        }
        if (tv_brand_model.getText().toString().trim().equals(PLEASE_INPUT)) {
            showToast("请选择品牌型号");
            return false;
        }

        if (StringUtils.isEmpty(tv_unit.getText().toString().trim())) {
            showToast("请选择单位");
            return false;
        }
        if (StringUtils.isEmpty(et_amount.getText().toString().trim())) {
            showToast("请输入数量");
            return false;
        }
        if (et_amount.getText().toString().trim().length()>5) {
            showToast("数量超过最大值9999");
            return false;
        }
        if (StringUtils.isEmpty(et_price.getText().toString().trim())) {
            showToast("请输入单价");
            return false;
        }
        if (et_price.getText().toString().trim().length()>6) {
            showToast("单价超过最大值99999");
            return false;
        }

        return true;

    }


}

