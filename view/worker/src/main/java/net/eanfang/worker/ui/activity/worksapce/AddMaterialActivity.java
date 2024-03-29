package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.biz.model.bean.ZjZgBean;
import com.eanfang.biz.model.entity.BughandleUseDeviceEntity;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.ToastUtil;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.util.StrUtil;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:32
 * @email houzhongzhou@yeah.net
 * @desc 电话未解决 添加耗材
 */

public class AddMaterialActivity extends BaseActivity {

    private static final int RESULT_ADD_MATERIAL = 2000;

    @BindView(R.id.ll_business)
    LinearLayout llBusiness;
    @BindView(R.id.ll_equipment)
    LinearLayout llEquipment;
    @BindView(R.id.ll_model)
    LinearLayout llModel;
    @BindView(R.id.tv_business)
    TextView tvBusiness;
    @BindView(R.id.tv_equipment)
    TextView tvEquipment;
    @BindView(R.id.tv_model)
    TextView tvModel;
    @BindView(R.id.et_num)
    EditText etNum;
    @BindView(R.id.et_remarks)
    EditText etRemarks;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.tv_num)
    TextView tvNum;

    private int maxWordsNum = 50; //输入限制的最大字数

    private String bugOneCode = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_material);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        setListener();

        bugOneCode = getIntent().getStringExtra("bugOneCode");
    }


    private void showBusinessSmallType() {
        if (StrUtil.isEmpty(bugOneCode)) {
            showToast("请先选择系统类别");
            return;
        }
        //二级
        PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getBusinessList(2)).filter(bean -> bean.getDataCode().startsWith(bugOneCode)).map(bus -> bus.getDataName()).toList(), (index, item) -> {
            tvBusiness.setText(item);
            tvEquipment.setText("");
            tvModel.setText("");
        });
    }

    private void showEquipmentName() {
        String busTwoCode = Config.get().getBusinessCodeByName(tvBusiness.getText().toString().trim(), 2);
        if (StrUtil.isEmpty(busTwoCode)) {
            showToast("请先选择设备类别");
            return;
        }
        PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getBusinessList(3)).filter(bus -> bus.getDataCode().startsWith(busTwoCode)).map(bus -> bus.getDataName()).toList(), ((index, item) -> {
            tvEquipment.setText(item);
            tvModel.setText("");
        }));
    }

    private void showModel() {
        String busOneName = Config.get().getBusinessNameByCode(bugOneCode, 1);
        String modelOne = Config.get().getBaseCodeByName(busOneName, 1, Constant.MODEL).get(0);
        if (StrUtil.isEmpty(modelOne)) {
            showToast("请先选择系统类别");
            return;
        }
        PickerSelectUtil.singleTextPicker(this, "", Stream.of(Config.get().getModelList(2)).filter(bus -> bus.getDataCode().startsWith(modelOne)).map(bus -> bus.getDataName()).toList(), ((index, item) -> {
            tvModel.setText(item);
        }));

    }

    private void initEquipmentName() {
        showEquipmentName();
    }

    private void initView() {
        setTitle("添加耗材");
        setLeftBack();
    }

    private void setListener() {

        llBusiness.setOnClickListener((v) -> {
            showBusinessSmallType();
        });

        llEquipment.setOnClickListener((v) -> {
            if (tvBusiness.getText().toString().isEmpty()) {
                ToastUtil.get().showToast(this, "请先选择设备类别");
                return;
            }
            initEquipmentName();
        });
        llModel.setOnClickListener((v) -> {
            if (tvEquipment.getText().toString().isEmpty()) {
                ToastUtil.get().showToast(this, "请先选择设备名称");
                return;
            }
            showModel();
        });
        btnAdd.setOnClickListener((v) -> {
            doSubmit();
        });
        etRemarks.addTextChangedListener(new TextWatcher() {
            CharSequence temp;
            int selectionStart;
            int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                tvNum.setText(s.length() + "/" + maxWordsNum);
                selectionStart = etRemarks.getSelectionStart();
                selectionEnd = etRemarks.getSelectionEnd();
                if (temp.length() > maxWordsNum) {
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    etRemarks.setText(s);
                    etRemarks.setSelection(tempSelection); //设置光标在最后
                }
            }
        });

    }

    public void doSubmit() {
        if (!checkInfo()) {
            return;
        }
        BughandleUseDeviceEntity bean = new BughandleUseDeviceEntity();
        bean.setCount(Integer.parseInt(etNum.getText().toString()));
        bean.setRemarkInfo(etRemarks.getText().toString());
        bean.setBusinessThreeCode(Config.get().getBusinessCodeByName(tvEquipment.getText().toString(), 3));
        bean.setModelCode(Config.get().getBaseCodeByName(tvModel.getText().toString(), 2, Constant.MODEL).get(0));
        bean.setDeviceName(tvEquipment.getText().toString());
        bean.setBusinessThreeId(Config.get().getBusinessIdByCode(bean.getBusinessThreeCode(), 3));

        Intent intent = new Intent();
        intent.putExtra("bean", bean);
        setResult(RESULT_ADD_MATERIAL, intent);
        finishSelf();
    }

    public boolean checkInfo() {
        if (tvBusiness.getText().toString().isEmpty()) {
            ToastUtil.get().showToast(this, "请选择设备类别");
            return false;
        }
        if (tvEquipment.getText().toString().isEmpty()) {
            ToastUtil.get().showToast(this, "请选择设备名称");
            return false;
        }

        if (etNum.getText().toString().isEmpty()) {
            ToastUtil.get().showToast(this, "请输入耗材数量");
            return false;
        }
        if (Long.parseLong(etNum.getText().toString()) > 999) {
            ToastUtil.get().showToast(this, "已超过最大数量999");
            return false;
        }
        if (etRemarks.getText().toString().isEmpty()) {
            ToastUtil.get().showToast(this, "请输入备注");
            return false;
        }

        return true;

    }

}

