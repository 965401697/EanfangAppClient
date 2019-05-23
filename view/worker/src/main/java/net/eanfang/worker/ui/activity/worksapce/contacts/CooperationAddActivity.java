package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.CooperationSearchBean;
import com.eanfang.sdk.selecttime.SelectTimeDialogFragment;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.CooperationEntity;
import com.eanfang.model.sys.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.adapter.CooperationAddAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CooperationAddActivity extends BaseWorkerActivity implements SelectTimeDialogFragment.SelectTimeListener {

    @BindView(R.id.iv_company_logo)
    SimpleDraweeView ivCompanyLogo;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.iv_user_header)
    SimpleDraweeView ivUserHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.recycler_view_classfiy)
    RecyclerView recyclerViewBusiness;
    @BindView(R.id.recycler_view_kind)
    RecyclerView recyclerViewOs;
    private CooperationAddAdapter businessCooperationAddAdapter;
    private CooperationAddAdapter osCooperationAddAdapter;

    List<String> mBusinessList = new ArrayList<>();
    List<String> mOsList = new ArrayList<>();


    List<CooperationEntity> businessServerBeanArrayList = new ArrayList<>();

    private CooperationSearchBean.ListBean mBean;

//    private TimePickerView mTimeYearMonthDay;

    private TextView currentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_add);
        ButterKnife.bind(this);
        setTitle("添加合作业务");
        setLeftBack();

        mBean = (CooperationSearchBean.ListBean) getIntent().getSerializableExtra("bean");

        initViews();

    }

    private void initViews() {

        ivCompanyLogo.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + mBean.getCompanyEntity().getLogoPic()));
        tvCompanyName.setText(TextUtils.isEmpty(mBean.getCompanyEntity().getName()) ? "" : mBean.getCompanyEntity().getName());
        if (!TextUtils.isEmpty(mBean.getCompanyEntity().getAreaCode())) {
            tvAddress.setText(Config.get().getAddressByCode(mBean.getCompanyEntity().getAreaCode()) + mBean.getCompanyEntity().getOfficeAddress());
        } else {
            tvAddress.setText("");
        }

        ivUserHeader.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + mBean.getAccountEntity().getAvatar()));
        tvName.setText(TextUtils.isEmpty(mBean.getAccountEntity().getRealName()) ? "" : mBean.getAccountEntity().getRealName());
        tvPhone.setText(TextUtils.isEmpty(mBean.getAccountEntity().getMobile()) ? "" : mBean.getAccountEntity().getMobile());

        List<BaseDataEntity> businessList = Config.get().getBusinessList(1);
        mOsList = GetConstDataUtils.getCooperationTypeList();

        for (BaseDataEntity business : businessList) {
            mBusinessList.add(business.getDataName());
        }


        recyclerViewBusiness.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewOs.setLayoutManager(new GridLayoutManager(this, 3));

        businessCooperationAddAdapter = new CooperationAddAdapter(R.layout.item_cooperation_add);
        osCooperationAddAdapter = new CooperationAddAdapter(R.layout.item_cooperation_add);

        businessCooperationAddAdapter.bindToRecyclerView(recyclerViewBusiness);
        osCooperationAddAdapter.bindToRecyclerView(recyclerViewOs);

        businessCooperationAddAdapter.setNewData(mOsList);
        osCooperationAddAdapter.setNewData(mBusinessList);


//        doSelectYearMonthDay();
    }


    private void sub() {
        List<CheckBox> businessCheckBoxes = businessCooperationAddAdapter.getCheckBoxList();
        List<CheckBox> osCheckBoxes = osCooperationAddAdapter.getCheckBoxList();

        if (businessCheckBoxes.size() == 0) {
            ToastUtil.get().showToast(this, "请选择业务类别");
            return;
        }

        if (osCheckBoxes.size() == 0) {
            ToastUtil.get().showToast(this, "请选择系统类别");
            return;
        }
        if (TextUtils.isEmpty(tvStartTime.getText().toString().trim())) {
            ToastUtil.get().showToast(this, "请选择合作开始时间");
            return;
        }
        if (TextUtils.isEmpty(tvEndTime.getText().toString().trim())) {
            ToastUtil.get().showToast(this, "请选择合作结束时间");
            return;
        }

        businessServerBeanArrayList.clear();//清空老数据

        for (CheckBox osCheckBox : osCheckBoxes) {

            for (CheckBox businessCheckBox : businessCheckBoxes) {
                CooperationEntity bean = new CooperationEntity();
                bean.setAssigneeOrgId(Long.parseLong(mBean.getCompanyEntity().getOrgId()));
                bean.setAssigneeTopCompanyId(Long.parseLong(mBean.getOrgEntity().getTopCompanyId()));
                bean.setOwnerTopCompanyId(WorkerApplication.get().getTopCompanyId());
                bean.setOwnerOrgId(WorkerApplication.get().getCompanyId());
                bean.setBusinessOneCode(Config.get().getBaseCodeByName(osCheckBox.getText().toString().trim(), 1, Constant.SYS_TYPE).get(0));
                bean.setBeginTime(GetDateUtils.getYeanDate(tvStartTime.getText().toString().trim()));
                bean.setEndTime(GetDateUtils.getYeanDate(tvEndTime.getText().toString().trim()));

                bean.setBusType(GetConstDataUtils.getCooperationTypeList().indexOf(businessCheckBox.getText().toString().trim()));

                businessServerBeanArrayList.add(bean);

            }


        }


        EanfangHttp.post(NewApiService.COOPERATION_WORKCOMPANY_SUB)
                .upJson(JSON.toJSONString(businessServerBeanArrayList))
                .execute(new EanfangCallback<JSONObject>(CooperationAddActivity.this, true, com.alibaba.fastjson.JSONObject.class) {

                    @Override
                    public void onSuccess(com.alibaba.fastjson.JSONObject bean) {
                        ToastUtil.get().showToast(CooperationAddActivity.this, "提交绑定成功");
                        endTransaction(true);
                    }
                });

    }


    /**
     * 选择年月日
     */
    public void doSelectYearMonthDay() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DATE));
        endDate.set(2040, 11, 31);
//        mTimeYearMonthDay = new TimePickerBuilder(CooperationAddActivity.this, new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {//选中事件回调
//                currentTextView.setText(ETimeUtils.getTimeByYearMonthDay(date));
//            }
//        })
//                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
//                .setCancelText("取消")//取消按钮文字
//                .setSubmitText("确定")//确认按钮文字
//                .setContentTextSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
////                .setTitleText("上门日期")//标题文字
//                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
//                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                .setRangDate(startDate, endDate)//起始终止年月日设定
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .isDialog(false)//是否显示为对话框样式
//                .build();
    }


    @OnClick({R.id.ll_start_time, R.id.ll_end_time, R.id.tv_sub})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_start_time:
                currentTextView = tvStartTime;
                new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
//                mTimeYearMonthDay.show();
                break;
            case R.id.ll_end_time:
                currentTextView = tvEndTime;
                new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
//                mTimeYearMonthDay.show();
                break;
            case R.id.tv_sub:
                sub();
                break;
        }
    }

    @Override
    public void getData(String time) {
        if (StringUtils.isEmpty(time) || " ".equals(time)) {
            currentTextView.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            currentTextView.setText(time);
        }
    }

}
