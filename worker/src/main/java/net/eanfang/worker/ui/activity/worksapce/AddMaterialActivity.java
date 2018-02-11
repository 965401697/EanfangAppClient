package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;
import com.yaf.base.entity.BughandleUseDeviceEntity;

import net.eanfang.worker.R;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:32
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class AddMaterialActivity extends BaseActivity implements View.OnClickListener {


    private LinearLayout ll_business;
    private TextView tv_business;
    private LinearLayout ll_equipment;
    private TextView tv_equipment;
    private LinearLayout ll_model;
    private TextView tv_model;
    private LinearLayout ll_location;
    private EditText et_location;
    private LinearLayout ll_code;
    private EditText et_code;
    private TextView tv_right;
    private EditText et_desc;
    private Button btn_add;
    private String bugOneCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);
        initView();
        setListener();

        bugOneCode = getIntent().getStringExtra("bugOneCode");
    }


    private void showBusinessSmallType() {
        if (StringUtils.isEmpty(bugOneCode)) {
            showToast("请先选择系统类别");
        }
        //二级
        ll_business.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getBusinessList(2)).filter(bean -> bean.getDataCode().startsWith(bugOneCode)).map(bus -> bus.getDataName()).toList(), (index, item) -> {
                tv_business.setText(item);
                tv_equipment.setText("");
                tv_model.setText("");
            });
        });
    }

    private void showEquipmentName() {
        String busTwoCode = Config.get().getBusinessCodeByName(tv_business.getText().toString().trim(), 2);
        if (StringUtils.isEmpty(busTwoCode)) {
            showToast("请先选择设备类别");
        }
        PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getBusinessList(3)).filter(bus -> bus.getDataCode().startsWith(busTwoCode)).map(bus -> bus.getDataName()).toList(), ((index, item) -> {
            tv_equipment.setText(item);
            tv_model.setText("");
        }));
    }

    private void showModel() {
        String busOneName = Config.get().getBusinessNameByCode(bugOneCode, 1);
        String modelOne = Config.get().getBaseCodeByName(busOneName, 1, Constant.MODEL).get(0);
        if (StringUtils.isEmpty(modelOne)) {
            showToast("请先选择系统类别");
        }
        PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getModelList(2)).filter(bus -> bus.getDataCode().startsWith(modelOne)).map(bus -> bus.getDataName()).toList(), ((index, item) -> {
            tv_model.setText(item);
        }));

    }

    private void initModel() {
        showModel();
    }

    private void initEquipmentName() {
        showEquipmentName();
    }

    private void initView() {
        ll_business = (LinearLayout) findViewById(R.id.ll_business);
        tv_business = (TextView) findViewById(R.id.tv_business);
        ll_equipment = (LinearLayout) findViewById(R.id.ll_equipment);
        tv_equipment = (TextView) findViewById(R.id.tv_equipment);
        ll_model = (LinearLayout) findViewById(R.id.ll_model);
        tv_model = (TextView) findViewById(R.id.tv_model);
        ll_location = (LinearLayout) findViewById(R.id.ll_location);
        et_location = (EditText) findViewById(R.id.et_location);
        ll_code = (LinearLayout) findViewById(R.id.ll_code);
        et_code = (EditText) findViewById(R.id.et_code);
        btn_add = (Button) findViewById(R.id.btn_add);
        setTitle("添加耗材");
        setLeftBack();
    }

    private void setListener() {
        ll_business.setOnClickListener(this);
        ll_equipment.setOnClickListener(this);
        ll_model.setOnClickListener(this);
        btn_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_business:
                showBusinessSmallType();
                break;
            case R.id.ll_equipment:
                if (tv_business.getText().toString().isEmpty()) {
                    ToastUtil.get().showToast(this, "请先选择设备类别");
                    return;
                }
                initEquipmentName();
                break;
            case R.id.ll_model:
                if (tv_equipment.getText().toString().isEmpty()) {
                    ToastUtil.get().showToast(this, "请先选择设备名称");
                    return;
                }
                initModel();
                break;
            case R.id.btn_add:
                if (!checkInfo()) {
                    return;
                }

                BughandleUseDeviceEntity bean = new BughandleUseDeviceEntity();
                bean.setCount(Integer.parseInt(et_location.getText().toString()));
                bean.setRemarkInfo(et_code.getText().toString());
                bean.setBusinessThreeCode(Config.get().getBusinessCodeByName(tv_equipment.getText().toString(), 3));
                bean.setModelCode(Config.get().getBaseCodeByName(tv_model.getText().toString(), 2, Constant.MODEL).get(0));
                bean.setDeviceName(tv_equipment.getText().toString());
                bean.setBusinessThreeId(Config.get().getBusinessIdByCode(bean.getBusinessThreeCode(), 3));

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", bean);
                intent.putExtras(bundle);
                setResult(435554, intent);
                finish();
                break;
            default:
                break;
        }
    }

    public boolean checkInfo() {
        if (tv_business.getText().toString().isEmpty()) {
            ToastUtil.get().showToast(this, "请选择设备类别");
            return false;
        }
        if (tv_equipment.getText().toString().isEmpty()) {
            ToastUtil.get().showToast(this, "请选择设备名称");
            return false;
        }

        if (et_location.getText().toString().isEmpty()) {
            ToastUtil.get().showToast(this, "请选输入耗材数量");
            return false;
        }
        if (Long.parseLong(et_location.getText().toString()) > 999) {
            ToastUtil.get().showToast(this, "已超过最大数量999");
            return false;
        }

        return true;

    }

}

