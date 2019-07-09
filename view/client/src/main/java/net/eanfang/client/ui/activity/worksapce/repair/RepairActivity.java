package net.eanfang.client.ui.activity.worksapce.repair;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.listener.MultiClickListener;
import com.eanfang.biz.model.RepairOpenAreaBean;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.biz.model.reapair.RepairPersonalInfoEntity;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;
import com.yaf.base.entity.RepairBugEntity;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.worksapce.OrderConfirmActivity;
import net.eanfang.client.ui.activity.worksapce.SelectWorkerActivity;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.widget.RepairSelectTimePop;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author admin
 * @on 2019年4月15日 16:46:24
 * @email guanluocang
 * @desc 我要报修
 */
public class RepairActivity extends BaseClientActivity {

    private static final int REQUEST_PROJECT_NAME_CODE = 1009;
    private static final int REQUEST_PERSONAL_INFO = 1008;
    /**
     * 到达时限
     */
    @BindView(R.id.tv_time)
    TextView tvTime;
    /**
     * 下一步
     */
    @BindView(R.id.tv_next)
    TextView tvNext;

    @BindView(R.id.iv_left)
    ImageView ivLeft;

    @BindView(R.id.tv_project_name)
    TextView tvProjectName;
    @BindView(R.id.et_notice)
    EditText etNotice;
    @BindView(R.id.ll_personalInfoTop)
    LinearLayout llPersonalInfoTop;
    @BindView(R.id.tv_createPersonalInfo)
    TextView tvCreatePersonalInfo;
    @BindView(R.id.ll_noPersonalInfo)
    LinearLayout llNoPersonalInfo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_default)
    TextView tvDefault;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_home_type)
    TextView tvHomeType;
    @BindView(R.id.tv_home_address)
    TextView tvHomeAddress;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.iv_info_right)
    ImageView ivInfoRight;
    @BindView(R.id.ll_projectName)
    LinearLayout llProjectName;

    /**
     * 选择时限 Popwindow
     */
    private RepairSelectTimePop repairSelectTimePop;

    /**
     * 扫码选择技师 传递的值
     */
    private RepairOrderEntity repairOrderEntity;
    private String isScan = "";


    private Long mOwnerOrgId = null;
    /**
     * 故障列表
     */
    private List<RepairBugEntity> beanList = new ArrayList<>();

    /**
     * 个人信息
     */
    private RepairPersonalInfoEntity.ListBean repairPersonalInfoEntity;
    /**
     * 項目ID
     */
    private String mProjectId = null;
    private LoginBean user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_repair);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }


    private void initView() {
        setTitle("我要报修");
        setLeftBack();
        // 扫码报修
        repairOrderEntity = (RepairOrderEntity) getIntent().getSerializableExtra("repairbean");
        isScan = getIntent().getStringExtra("qrcode");
        mOwnerOrgId = getIntent().getLongExtra("mOwnerOrgId", 0);
        beanList = (List<RepairBugEntity>) getIntent().getSerializableExtra("troubleList");
        ivInfoRight.setVisibility(View.VISIBLE);
        //个人客户 单位名称自己输入
        user = ClientApplication.get().getLoginBean();
        llProjectName.setVisibility(user.getAccount().getDefaultUser().getCompanyId() <= 0 ? View.GONE : View.VISIBLE);
//        repairPersonalInfoEntity = (RepairPersonalInfoEntity.ListBean) getIntent().getSerializableExtra("infoEntity");
    }

    private void initData() {
        doChekInfo();
    }


    private void initListener() {
        // 查看个人信息列表
        llPersonalInfoTop.setOnClickListener((v) -> {
            JumpItent.jump(this, RepairPersonInfoListActivity.class, REQUEST_PERSONAL_INFO);
        });
        tvNext.setOnClickListener(new MultiClickListener(this, this::doChekcInfo, this::goSelectWorker));
    }

    /**
     * 判断是否有无个人信息
     */
    private void doChekInfo() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("accId", ClientApplication.get().getAccId() + "");
        EanfangHttp.post(NewApiService.REPAIR_PERSONAL_INFO_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<RepairPersonalInfoEntity>(this, true, RepairPersonalInfoEntity.class, bean -> {
                    if (bean.getList() != null && bean.getList().size() > 0) {
                        llPersonalInfoTop.setVisibility(View.VISIBLE);
                        llNoPersonalInfo.setVisibility(View.GONE);
                        repairPersonalInfoEntity = bean.getList().get(0);
                        doSetPersonalInfo(bean.getList().get(0));
                    } else {
                        llPersonalInfoTop.setVisibility(View.GONE);
                        llNoPersonalInfo.setVisibility(View.VISIBLE);
                    }
                }));
    }


    /**
     * 字段填写检查约束
     */
    private boolean checkInfo() {

        if (StringUtils.isEmpty(tvTime.getText().toString().trim())) {
            showToast("请选择到达时限");
            return false;
        }
        return true;
    }

    /**
     * 填充数据
     */
    private RepairOrderEntity fillBean() {
        RepairOrderEntity bean = new RepairOrderEntity();
        bean.setBugEntityList(beanList);
        bean.setLatitude(repairPersonalInfoEntity.getLatitude());
        bean.setLongitude(repairPersonalInfoEntity.getLongitude());
        bean.setAddress(repairPersonalInfoEntity.getAddress());
        bean.setPlaceCode(Config.get().getAreaCodeByName(repairPersonalInfoEntity.getCity(), repairPersonalInfoEntity.getCounty()));
        bean.setPlaceId(Config.get().getBaseIdByCode(bean.getPlaceCode(), 3, Constant.AREA) + "");
        bean.setRepairCompany(repairPersonalInfoEntity.getConmpanyName());
        bean.setProjectId(mProjectId);
        bean.setProjectName(tvProjectName.getText().toString().trim());
        if (!StringUtils.isEmpty(etNotice.getText().toString().trim())) {
            bean.setRemarkInfo(etNotice.getText().toString().trim());
        }
        bean.setRepairContactPhone(repairPersonalInfoEntity.getPhone());
        bean.setRepairContacts(repairPersonalInfoEntity.getName());
        bean.setArriveTimeLimit(GetConstDataUtils.getArriveList().indexOf(tvTime.getText().toString().trim()));
        bean.setOwnerUserId(ClientApplication.get().getUserId());
        bean.setOwnerCompanyId(ClientApplication.get().getCompanyId());
        bean.setOwnerTopCompanyId(ClientApplication.get().getTopCompanyId());
        bean.setOwnerOrgCode(ClientApplication.get().getOrgCode());
        bean.setSex(repairPersonalInfoEntity.getGender());
        bean.setRepairWay(0);
        return bean;
    }

    /**
     * 扫码报修 填充数据
     */
    private RepairOrderEntity doQrFillBean() {
        // 扫码已经选择完技师 ，直接确认
        repairOrderEntity.setBugEntityList(beanList);
        repairOrderEntity.setLatitude(repairPersonalInfoEntity.getLatitude());
        repairOrderEntity.setLongitude(repairOrderEntity.getLongitude());
        repairOrderEntity.setAddress(repairPersonalInfoEntity.getAddress());
        repairOrderEntity.setPlaceCode(Config.get().getAreaCodeByName(repairPersonalInfoEntity.getCity(), repairPersonalInfoEntity.getCounty()));
        repairOrderEntity.setPlaceId(Config.get().getBaseIdByCode(repairOrderEntity.getPlaceCode(), 3, Constant.AREA) + "");
        repairOrderEntity.setRepairCompany(repairPersonalInfoEntity.getConmpanyName());
        repairOrderEntity.setProjectId(mProjectId);
        repairOrderEntity.setProjectName(tvProjectName.getText().toString().trim());
        if (!StringUtils.isEmpty(etNotice.getText().toString().trim())) {
            repairOrderEntity.setRemarkInfo(etNotice.getText().toString().trim());
        }
        repairOrderEntity.setRepairContactPhone(repairPersonalInfoEntity.getPhone());
        repairOrderEntity.setRepairContacts(repairPersonalInfoEntity.getName());
        repairOrderEntity.setArriveTimeLimit(GetConstDataUtils.getArriveList().indexOf(tvTime.getText().toString().trim()));
        repairOrderEntity.setOwnerUserId(ClientApplication.get().getUserId());
        repairOrderEntity.setOwnerCompanyId(ClientApplication.get().getCompanyId());
        repairOrderEntity.setOwnerTopCompanyId(ClientApplication.get().getTopCompanyId());
        repairOrderEntity.setOwnerOrgCode(ClientApplication.get().getOrgCode());
        repairOrderEntity.setSex(repairPersonalInfoEntity.getGender());
        repairOrderEntity.setRepairWay(0);
        return repairOrderEntity;
    }

    /**
     * 到达时限
     */

    public void onArriveTimeOptionPicker(View view) {
        List<String> timeList = GetConstDataUtils.getArriveList();
        if (timeList != null) {
            repairSelectTimePop = new RepairSelectTimePop(this, timeList, new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    tvTime.setText(timeList.get(i).toString());
                    repairSelectTimePop.dismiss();
                }
            });
        }
        repairSelectTimePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                repairSelectTimePop.backgroundAlpha(1.0f);
            }
        });
        repairSelectTimePop.showAtLocation(findViewById(R.id.ll_repair), Gravity.BOTTOM, 0, 0);

//        PickerSelectUtil.singleTextPicker(this, "到达时限", tvTime, );
    }


    @OnClick({R.id.tv_project_name, R.id.ll_noPersonalInfo, R.id.iv_input_voice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //  创建项目名称
            case R.id.tv_project_name:
                JumpItent.jump(this, RepairProjectListActivity.class, REQUEST_PROJECT_NAME_CODE);
                break;
            //  创建个人信息
            case R.id.ll_noPersonalInfo:
                JumpItent.jump(this, RepairPersonInfoListActivity.class, REQUEST_PERSONAL_INFO);
                break;
            case R.id.iv_input_voice:
                 RxPerm.get(this).voicePerm((isSuccess)->{
                    RecognitionManager.getSingleton().startRecognitionWithDialog(RepairActivity.this, etNotice);
                });
                break;
            default:
                break;
        }
    }

    public boolean doChekcInfo() {
        if (repairPersonalInfoEntity == null) {
            showToast("请添加用户信息");
            return false;
        }
        if (user.getAccount().getDefaultUser().getCompanyId() > 0) {
            if (StringUtils.isEmpty(tvProjectName.getText().toString().trim())) {
                showToast("请填写项目名称");
                return false;
            }
        }

        if (StringUtils.isEmpty(tvTime.getText().toString().trim())) {
            showToast("请选择到达时限");
            return false;
        }

        return true;
    }

    /**
     * 选择技师
     */
    private void goSelectWorker() {
        int mAreaId = Config.get().getBaseIdByCode(Config.get().getAreaCodeByName(repairPersonalInfoEntity.getCity(), repairPersonalInfoEntity.getCounty()), 3, Constant.AREA);
        if (!checkInfo()) {
            return;
        }
        // 查找上门费 判断当前城市是否开通  如果没有开通，提示 您报修的城市暂未开通服务。不能继续选技师
        EanfangHttp.post(RepairApi.GET_REAPIR_PAY_PRICE)
                .params("baseDataId", mAreaId)
                .execute(new EanfangCallback<RepairOpenAreaBean>(RepairActivity.this, true, RepairOpenAreaBean.class, bean -> {
                    /**
                     * status   0：停用，1启用
                     * */
                    if (bean.getStatus() == 1) {
                        // 扫码已经选择完技师 ，直接确认
                        if (!StringUtils.isEmpty(isScan) && isScan.equals("scaning")) {
                            Intent intent_scan = new Intent(RepairActivity.this, OrderConfirmActivity.class);
                            intent_scan.putExtra("topInfo", repairPersonalInfoEntity);
                            intent_scan.putExtra("bean", doQrFillBean());
                            intent_scan.putExtra("doorFee", bean.getDoorFee());
                            intent_scan.putExtra("headUrl", getIntent().getStringExtra("headUrl"));
                            intent_scan.putExtra("workName", getIntent().getStringExtra("workName"));
                            intent_scan.putExtra("companyName", getIntent().getStringExtra("companyName"));
                            startActivity(intent_scan);
                        } else {
                            Intent intent = new Intent(RepairActivity.this, SelectWorkerActivity.class);
                            intent.putExtra("bean", fillBean());
                            intent.putExtra("topInfo", repairPersonalInfoEntity);
                            intent.putExtra("doorFee", bean.getDoorFee());
                            intent.putExtra("mOwnerOrgId", mOwnerOrgId);
                            intent.putStringArrayListExtra("businessIds", (ArrayList<String>) Stream.of(beanList).map(beans -> Config.get().getBusinessIdByCode(beans.getBusinessThreeCode(), 1) + "").distinct().toList());
                            startActivity(intent);
                        }
                    } else {
                        showToast("所在城市暂未开通服务");
                    }
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            //创建项目名称
            case REQUEST_PROJECT_NAME_CODE:
                tvProjectName.setText(data.getStringExtra("projectName"));
                mProjectId = data.getStringExtra("projectId");
                break;
            // 修改当前默认
            case REQUEST_PERSONAL_INFO:
                repairPersonalInfoEntity = (RepairPersonalInfoEntity.ListBean) data.getSerializableExtra("infoEntity");
                doSetPersonalInfo(repairPersonalInfoEntity);
                break;
            default:
                break;
        }
    }

    public void doSetPersonalInfo(RepairPersonalInfoEntity.ListBean bean) {
        if (bean == null) {
            llPersonalInfoTop.setVisibility(View.GONE);
            llNoPersonalInfo.setVisibility(View.VISIBLE);
            return;
        }
        llPersonalInfoTop.setVisibility(View.VISIBLE);
        llNoPersonalInfo.setVisibility(View.GONE);
        //姓名
        tvName.setText(bean.getName());
        // 性别0女1男
        tvSex.setText(bean.getGender() == 0 ? " (女士) " : " (先生) ");
        // 电话
        tvPhone.setText(bean.getPhone());
        // 单位
        if (!StringUtils.isEmpty(bean.getSelectAddress())) {
            tvHomeType.setText("[" + bean.getSelectAddress() + "]");
        }
        tvHomeAddress.setText(bean.getConmpanyName());
        // 地址
        tvAddress.setText(bean.getAddress());
        tvDefault.setVisibility(bean.getIsDefault() == 1 ? View.VISIBLE : View.GONE);
    }
}
