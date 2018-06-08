package net.eanfang.worker.ui.activity.worksapce.repair;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.NumberUtil;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.yaf.base.entity.BughandleDetailEntity;
import com.yaf.base.entity.BughandleParamEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.repair.AddParamAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/6/5  18:37
 * @decision 设备参数
 */
public class DeviceParameterActivity extends BaseActivity {


    public static final int ADD_DEVICE_PARAM_RESULT = 1000;
    @BindView(R.id.rv_param)
    RecyclerView rvParam;
    // 选择类别
    @BindView(R.id.tl_select_type)
    RelativeLayout tlSelectType;
    // 参数
    @BindView(R.id.et_input_content)
    EditText etInputContent;
    // 完成
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    // 添加参数
    @BindView(R.id.tv_add_param)
    TextView tvAddParam;
    // 类别名称
    @BindView(R.id.tv_device_param_name)
    TextView tvDeviceParamName;
    @BindView(R.id.ll_add_param)
    LinearLayout llAddParam;


    private List<BughandleParamEntity> paramEntityList = new ArrayList<>();
    private AddParamAdapter paramAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_parameter);
        ButterKnife.bind(this);
        initView();
        initAdapter();
        initListener();
    }

    private void initView() {
        setTitle("设备参数");
        setRightTitle("确认");
        setLeftBack();
        paramEntityList = (List<BughandleParamEntity>) getIntent().getSerializableExtra("paramEntityList");
    }

    private void initListener() {
        tlSelectType.setOnClickListener((v) -> {
            showOptions();
        });
        tvFinish.setOnClickListener((v) -> {
            String content = etInputContent.getText().toString().trim();
            String name = tvDeviceParamName.getText().toString().trim();
            if (!StringUtils.isEmpty(content) && !StringUtils.isEmpty(name)) {
                BughandleParamEntity param = new BughandleParamEntity();
                param.setParamValue(content);
                param.setParamName(name);
                paramEntityList.add(param);
                paramAdapter.setNewData(paramEntityList);
                paramAdapter.notifyDataSetChanged();
                tvDeviceParamName.setText("");
                etInputContent.setText("");
                llAddParam.setVisibility(View.GONE);
                param = null;
            } else {
                showToast("请填写参数内容");
            }
        });
        paramAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            paramEntityList.remove(position);
            paramAdapter.notifyDataSetChanged();
        });
        tvAddParam.setOnClickListener((v) -> {
            llAddParam.setVisibility(View.VISIBLE);
        });
        setRightTitleOnClickListener((v) -> {
            if (paramEntityList.size() > 0) {
                Intent intent = new Intent();
                intent.putExtra("addParam", (Serializable) paramEntityList);
                setResult(ADD_DEVICE_PARAM_RESULT, intent);
                finishSelf();
            } else {
                showToast("请完善参数");
            }
        });
    }

    private void initAdapter() {
        paramAdapter = new AddParamAdapter(R.layout.layout_add_param, paramEntityList);
        rvParam.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvParam.setLayoutManager(new LinearLayoutManager(this));
        rvParam.setAdapter(paramAdapter);
    }

    /**
     * 选择类别
     */
    private void showOptions() {
        PickerSelectUtil.singleTextPicker(this, "参数", GetConstDataUtils.getDeviceParamList(), (index, item) -> {
            tvDeviceParamName.setText(item);
        });
    }

}
