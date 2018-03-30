package net.eanfang.worker.ui.activity.worksapce;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Optional;
import com.bigkoo.pickerview.OptionsPickerView;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.device.GetDeviceFailureOptionBean;
import com.eanfang.model.device.GetDeviceFailureSolutionOptionBean;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.yaf.base.entity.BughandleDetailEntity;
import com.yaf.base.entity.BughandleParamEntity;
import com.yaf.base.entity.BughandleUseDeviceEntity;
import com.yaf.base.entity.RepairFailureEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.ParamAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:37
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class PhoneSolveTroubleDetailActivity extends BaseWorkerActivity {
    public static final String TAG = PhoneSolveTroubleDetailActivity.class.getSimpleName();
    //2017年9月29日
    @BindView(R.id.ll_device_failure_solution)
    LinearLayout ll_device_failure_solution;
    @BindView(R.id.tv_device_failure_solution)
    TextView tv_device_failure_solution;
    @BindView(R.id.ll_deviceFailure)
    LinearLayout ll_deviceFailure;
    private Context context = this;
    private TextView tv_trouble_device;
    private RelativeLayout rl_trouble_device;
    private TextView tv_brand_model;
    private RelativeLayout rl_brand_model;
    private TextView tv_device_no;
    private RelativeLayout rl_device_no;
    private TextView tv_device_location;
    private RelativeLayout rl_device_location;
    private TextView tv_add;
    private RecyclerView rcy_parameter;
    private EditText et_trouble_desc;
    private EditText et_trouble_point;
    private EditText et_trouble_reason;
    private EditText et_trouble_deal;
    private Button btn_add_trouble;
    private BughandleDetailEntity bughandleDetailEntity;
    private TextView tv_trouble_title;
    private OptionsPickerView pvOptions;
    private ParamAdapter paramAdapter;
    private HashMap<String, String> uploadMap;

    //2017年7月20日
    //维修结果
    private LinearLayout ll_repair_conclusion;
    private TextView tv_repair_conclusion;
    private int position;
    //2017年7月24日
    private String companyName;
    private Long id;
    private Long confirmId;

    private GetDeviceFailureSolutionOptionBean solutionOptionBean;
    private GetDeviceFailureOptionBean failureOptionBean;
    private RepairFailureEntity repairFailureEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ps_trouble_detail);
        ButterKnife.bind(this);
        initView();
        initData();


        initAdapter();

        lookFailureDetail();

        fillData();

        initListener();


    }

    private void lookFailureDetail() {

        EanfangHttp.get(RepairApi.GET_FAILURE_DETAIL)
                .params("id", id)
                .execute(new EanfangCallback<RepairFailureEntity>(this, true, RepairFailureEntity.class, (bean) -> {
                    repairFailureEntity = bean;
                }));
    }

    private void initView() {
        tv_trouble_device = (TextView) findViewById(R.id.tv_trouble_device);
        rl_trouble_device = (RelativeLayout) findViewById(R.id.rl_trouble_device);
        tv_brand_model = (TextView) findViewById(R.id.tv_brand_model);
        rl_brand_model = (RelativeLayout) findViewById(R.id.rl_brand_model);
        tv_device_no = (TextView) findViewById(R.id.tv_device_no);
        rl_device_no = (RelativeLayout) findViewById(R.id.rl_device_no);
        tv_device_location = (TextView) findViewById(R.id.tv_device_location);
        rl_device_location = (RelativeLayout) findViewById(R.id.rl_device_location);
        tv_add = (TextView) findViewById(R.id.tv_add);
        rcy_parameter = (RecyclerView) findViewById(R.id.rcy_parameter);
        et_trouble_desc = (EditText) findViewById(R.id.et_trouble_desc);
        et_trouble_point = (EditText) findViewById(R.id.et_trouble_point);
        et_trouble_reason = (EditText) findViewById(R.id.et_trouble_reason);
        et_trouble_deal = (EditText) findViewById(R.id.et_trouble_deal);
        //2017年7月20日
        //维修结论
        ll_repair_conclusion = (LinearLayout) findViewById(R.id.ll_repair_conclusion);
        tv_repair_conclusion = (TextView) findViewById(R.id.tv_repair_conclusion);
        btn_add_trouble = (Button) findViewById(R.id.btn_add_trouble);
        tv_trouble_title = (TextView) findViewById(R.id.tv_trouble_title);

        supprotToolbar();
        setTitle("故障明细");
    }

    private void initData() {
        companyName = getIntent().getStringExtra("companyName");
        bughandleDetailEntity = (BughandleDetailEntity) getIntent().getSerializableExtra("bean");
        position = getIntent().getIntExtra("position", 0);
        id = getIntent().getLongExtra("id", 0);
        confirmId = getIntent().getLongExtra("confirmId", 0);


        if (bughandleDetailEntity.getParamEntityList() == null) {
            List<BughandleParamEntity> list = new ArrayList<>();
            bughandleDetailEntity.setParamEntityList(list);
        }
        if (bughandleDetailEntity.getUseDeviceEntityList() == null) {
            List<BughandleUseDeviceEntity> list = new ArrayList<>();
            bughandleDetailEntity.setUseDeviceEntityList(list);
        }

    }


    private void initListener() {
        ll_deviceFailure.setOnClickListener((v) -> {
//            if (failureOptionBean == null) {
//                showToast("暂时没有参考内容");
//                return;
//            }
//            List<String> opts = Stream.of(failureOptionBean.getBean()).map(bean -> bean.getTitle()).toList();
//            PickerSelectUtil.singleTextPicker(this, "参考设备故障", opts, (index, item) -> {
//                et_trouble_desc.setText(failureOptionBean.getBean().get(index).getDescription());
//                //修改bean中设备uid
//                bean.setDeviceUid(failureOptionBean.getBean().get(index).getDeviceUid());
//                bean.setDeviceFailureUid(failureOptionBean.getBean().get(index).getUid());
//                doHttpSolution();
//            });
        });

        ll_device_failure_solution.setOnClickListener((v) -> {
//            if (solutionOptionBean == null) {
//                showToast("暂时没有参考内容");
//                return;
//            }
//            List<String> opts = Stream.of(solutionOptionBean.getBean()).map(bean -> bean.getTitle()).toList();
//            PickerSelectUtil.singleTextPicker(this, "参考解决方案", opts, (index, item) -> {
//                tv_device_failure_solution.setText(item);
//                et_trouble_point.setText(solutionOptionBean.getBean().get(index).getCheckInfo());
//                et_trouble_reason.setText(solutionOptionBean.getBean().get(index).getCauseInfo());
//                et_trouble_deal.setText(solutionOptionBean.getBean().get(index).getDisposeInfo());
//            });
        });

        ll_repair_conclusion.setOnClickListener(v -> {
            showRepairConslusion();
        });
        tv_add.setOnClickListener(v -> {
            showOptions();
        });

        btn_add_trouble.setOnClickListener(v -> submit());


    }


    private void initAdapter() {

        paramAdapter = new ParamAdapter(R.layout.item_parm, (ArrayList) bughandleDetailEntity.getParamEntityList());
        rcy_parameter.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcy_parameter.setLayoutManager(new LinearLayoutManager(this));
        rcy_parameter.setAdapter(paramAdapter);
    }

    private void showRepairConslusion() {
        PickerSelectUtil.singleTextPicker(this, "维修结论", tv_repair_conclusion, constDataUtils.getBugDetailList());
    }

    public void fillData() {
        if (StringUtils.isValid(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode())) {
            String bugOne = config.getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 1);
            String bugTwo = config.getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 2);
            String bugThree = config.getBusinessNameByCode(bughandleDetailEntity.getFailureEntity().getBusinessThreeCode(), 3);
            tv_trouble_title.setText(bugOne + "-" + bugTwo + "-" + bugThree);
        } else {
            tv_trouble_title.setText("");
        }
        //品牌型号
        tv_brand_model.setText(Optional.ofNullable(config.getModelNameByCode(bughandleDetailEntity.getFailureEntity().getModelCode(), 2)).orElse(""));

        //故障设备
        tv_trouble_device.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getDeviceName()).orElse(""));
        //设备编号
        tv_device_no.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getDeviceNo()).orElse(""));
        //故障位置
        tv_device_location.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getBugPosition()).orElse(""));
        //故障描述
        et_trouble_desc.setText(Optional.ofNullable(bughandleDetailEntity.getFailureEntity().getBugDescription()).orElse(""));
    }

    private boolean checkData() {

        if (StringUtils.isEmpty(et_trouble_desc.getText().toString().trim())) {
            showToast("请输入故障描述");
            return false;
        }
        if (StringUtils.isEmpty(tv_repair_conclusion.getText().toString().trim())) {
            showToast("请选择是否误报");
            return false;
        }
        if (StringUtils.isEmpty(et_trouble_reason.getText().toString().trim())) {
            showToast("请输入原因");
            return false;
        }
        if (StringUtils.isEmpty(tv_repair_conclusion.getText().toString().trim())) {
            showToast("请选择维修结论");
            return false;
        }

        return true;
    }

    private void submit() {

        if (!checkData()) {
            return;
        }

        bughandleDetailEntity.getFailureEntity().setBugDescription(et_trouble_desc.getText().toString().trim());
        bughandleDetailEntity.getFailureEntity().setBusinessThreeCode(config.getBusinessCodeByName(tv_trouble_title.getText().toString().trim(), 3));
        bughandleDetailEntity.getFailureEntity().setDeviceNo(tv_device_no.getText().toString().trim());
        bughandleDetailEntity.getFailureEntity().setBugPosition(tv_device_location.getText().toString().trim());
        bughandleDetailEntity.getFailureEntity().setDeviceName("");
        bughandleDetailEntity.setCause(et_trouble_reason.getText().toString().trim());
        bughandleDetailEntity.setHandle(et_trouble_deal.getText().toString().trim());
        bughandleDetailEntity.setCheckProcess(et_trouble_point.getText().toString().trim());
        bughandleDetailEntity.setBusBughandleConfirmId(confirmId);
        //维修结果
        bughandleDetailEntity.setStatus(constDataUtils.getBugDetailList().indexOf(tv_repair_conclusion.getText().toString().trim()));

        doHttpSubmit();

    }

    private void doHttpSubmit() {

        EanfangHttp.post(RepairApi.GET_REPAIR_BUGHANDLE_CREATE_DETAIL)
                .upJson(JSONObject.toJSONString(bughandleDetailEntity))
                .execute(new EanfangCallback(this, true, JSONObject.class, (bean) -> {
                    runOnUiThread(() -> {
                        getIntent().putExtra("beans", bughandleDetailEntity);
                        getIntent().putExtra("position", position);
                        setResult(2000, getIntent());
                        finishSelf();
                    });
                }));


    }

    private void showOptions() {
        PickerSelectUtil.singleTextPicker(this, "参数", constDataUtils.getDeviceParamList(), (index, item) -> {
            BughandleParamEntity param = new BughandleParamEntity();
            param.setParamName(item);
            bughandleDetailEntity.getParamEntityList().add(param);
            paramAdapter.notifyDataSetChanged();

        });
    }

}

