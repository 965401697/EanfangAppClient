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

import com.bigkoo.pickerview.OptionsPickerView;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.device.GetDeviceFailureOptionBean;
import com.eanfang.model.device.GetDeviceFailureSolutionOptionBean;
import com.eanfang.util.GetConstDataUtils;
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
    private BughandleDetailEntity bean;
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
        bean = (BughandleDetailEntity) getIntent().getSerializableExtra("bean");
        position = getIntent().getIntExtra("position", 0);
        id = getIntent().getLongExtra("id", 0);


        if (bean.getParamEntityList() == null) {
            List<BughandleParamEntity> list = new ArrayList<>();
            bean.setParamEntityList(list);
        }
        if (bean.getUseDeviceEntityList() == null) {
            List<BughandleUseDeviceEntity> list = new ArrayList<>();
            bean.setUseDeviceEntityList(list);
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

        paramAdapter = new ParamAdapter(R.layout.item_parm, (ArrayList) bean.getParamEntityList());
        rcy_parameter.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcy_parameter.setLayoutManager(new LinearLayoutManager(this));
        rcy_parameter.setAdapter(paramAdapter);
    }

    private void showRepairConslusion() {
        PickerSelectUtil.singleTextPicker(this, "维修结论", tv_repair_conclusion, GetConstDataUtils.getBugDetailList());
    }

    public void fillData() {
        if (StringUtils.isValid(bean.getFailureEntity().getBusinessThreeCode())) {
            tv_trouble_title.setText(Config.get().getBusinessNameByCode(bean.getFailureEntity().getBusinessThreeCode(), 1));
        }
        // TODO: 2018/1/9 待处理
//        //故障设备
//        if (StringUtils.isValid(bean.getInstrument())) {
//            tv_trouble_device.setText(bean.getInstrument());
//        }
//        //品牌型号
//        if (StringUtils.isValid(bean.getModelnum())) {
//            tv_brand_model.setText(bean.getModelnum());
//        }
        //设备编号
        if (StringUtils.isValid(bean.getFailureEntity().getDeviceNo())) {
            tv_device_no.setText(bean.getFailureEntity().getDeviceNo());
        }
        //故障位置
        if (StringUtils.isValid(bean.getFailureEntity().getBugPosition())) {
            tv_device_location.setText(bean.getFailureEntity().getBugPosition());
        }
        //故障描述
        if (StringUtils.isValid(bean.getFailureEntity().getBugDescription())) {
            et_trouble_desc.setText(bean.getFailureEntity().getBugDescription());
        }
    }


    private void submit() {

        bean.getFailureEntity().setBugDescription(et_trouble_desc.getText().toString().trim());
        bean.getFailureEntity().setBusinessThreeCode(Config.get().getBusinessCodeByName(tv_trouble_title.getText().toString().trim(), 3));
        bean.getFailureEntity().setDeviceNo(tv_device_no.getText().toString().trim());
        bean.getFailureEntity().setBugPosition(tv_device_location.getText().toString().trim());
        bean.getFailureEntity().setDeviceName("");
        bean.setCause(et_trouble_reason.getText().toString().trim());
        bean.setHandle(et_trouble_deal.getText().toString().trim());
        bean.setCheckProcess(et_trouble_point.getText().toString().trim());
        //维修结果
        bean.setStatus(GetConstDataUtils.getBugDetailList().indexOf(tv_repair_conclusion.getText().toString().trim()));

        getIntent().putExtra("position", position);
        getIntent().putExtra("bean", bean);
        setResult(2000, getIntent());
        finishSelf();

    }


    private void showOptions() {
        PickerSelectUtil.singleTextPicker(this, "参数", GetConstDataUtils.getDeviceParamList(), (index, item) -> {
            BughandleParamEntity param = new BughandleParamEntity();
            param.setParamName(item);
            bean.getParamEntityList().add(param);
            paramAdapter.notifyDataSetChanged();

        });
    }

}

