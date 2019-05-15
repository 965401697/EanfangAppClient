package net.eanfang.client.ui.activity.worksapce.oa.workreport;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eanfang.biz.model.TemplateBean;
import com.eanfang.ui.fragment.SelectTimeDialogFragment;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.CooperationAddAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FiltrateTypeActivity extends BaseClientActivity implements SelectTimeDialogFragment.SelectTimeListener, RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.recycler_type)
    RecyclerView recyclerType;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_end)
    TextView tvEnd;

    @BindView(R.id.rb_device_read)
    RadioButton rbDeviceRead;
    @BindView(R.id.rb_device_unread)
    RadioButton rbDeviceUnread;
    @BindView(R.id.rg_status)
    RadioGroup rgStatus;
    private CooperationAddAdapter typeAdapter;

    List<String> mTypeList = new ArrayList<>();
    private OAPersonAdaptet oaPersonAdaptet;

    private ArrayList<TemplateBean.Preson> newPresonList = new ArrayList<>();

//    private TimePickerView mTimeYearMonthDay;

    private TextView mCurrentText;
    /**
     * 状态选择
     */
    private int mStatus = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrate_type);
        ButterKnife.bind(this);
        setTitle("工作汇报筛选");
        setLeftBack();

        initViews();
    }

    private void initViews() {

        mTypeList = GetConstDataUtils.getWorkReportTypeList();


        recyclerType.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));

        typeAdapter = new CooperationAddAdapter(R.layout.item_cooperation_add);

        oaPersonAdaptet = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>());
        recyclerView.setAdapter(oaPersonAdaptet);

        typeAdapter.bindToRecyclerView(recyclerType);

        typeAdapter.setNewData(mTypeList);

        rgStatus.setOnCheckedChangeListener(this);
//        doSelectYearMonthDay();
    }

    @OnClick({R.id.ll_start, R.id.ll_end, R.id.tv_cancle, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_start:
                mCurrentText = tvStart;
                new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
//                mTimeYearMonthDay.show();
                break;
            case R.id.ll_end:
                mCurrentText = tvEnd;
                new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
//                mTimeYearMonthDay.show();
                break;
            case R.id.tv_cancle:
                finishSelf();
                break;
            case R.id.tv_sure:
                sub();
                break;
        }
    }

    private void sub() {

        QueryEntry queryEntry = new QueryEntry();

        if (typeAdapter.getCheckBoxList().size() != 0) {
            queryEntry = new QueryEntry();

            List<CheckBox> typeList = typeAdapter.getCheckBoxList();


            if (typeList.size() > 1) {
                List<String> typeIndexList = new ArrayList<>();

                for (CheckBox type : typeList) {
                    int typeIndex = mTypeList.indexOf(type.getText().toString().trim());
                    typeIndexList.add(String.valueOf(typeIndex));
                }
                queryEntry.getIsIn().put("type", typeIndexList);
            } else {
                queryEntry.getEquals().put("type", String.valueOf(mTypeList.indexOf(typeList.get(0).getText().toString().trim())));
            }
        }

        if (mStatus != 100) {
            queryEntry.getEquals().put("status", mStatus + "");
        }
        if (newPresonList.size() != 0) {

            if (queryEntry == null) {
                queryEntry = new QueryEntry();
            }

            if (newPresonList.size() > 1) {
                List<String> idList = new ArrayList<>();

                for (TemplateBean.Preson p : newPresonList) {
                    idList.add(p.getUserId());
                }
                queryEntry.getIsIn().put("createUserId", idList);
            } else {
                TemplateBean.Preson p = newPresonList.get(0);
                queryEntry.getEquals().put("createUserId", p.getUserId());
            }
        }

        if (!TextUtils.isEmpty(tvStart.getText().toString().trim())) {

            if (queryEntry == null) {
                queryEntry = new QueryEntry();
            }

            queryEntry.getGtEquals().put("createTime", tvStart.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(tvEnd.getText().toString().trim())) {

            if (queryEntry == null) {
                queryEntry = new QueryEntry();
            }

            queryEntry.getLtEquals().put("createTime", tvEnd.getText().toString().trim());
        }

        Intent intent = new Intent();
        if (queryEntry != null) {
            intent.putExtra("query", queryEntry);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {

        if (presonList.size() > 0) {


            Set hashSet = new HashSet();
            hashSet.addAll(oaPersonAdaptet.getData());
            hashSet.addAll(presonList);

            if (newPresonList.size() > 0) {
                newPresonList.clear();
            }
            newPresonList.addAll(hashSet);
            oaPersonAdaptet.setNewData(newPresonList);


        }
    }

    /**
     * 选择年月日
     */
    public void doSelectYearMonthDay() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(1960, 1, 1);
        endDate.set(2040, 11, 31);
//        mTimeYearMonthDay = new TimePickerBuilder(this, new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {//选中事件回调
//                mCurrentText.setText(ETimeUtils.getTimeByYearMonthDay(date));
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

    @Override
    public void getData(String time) {
        if (StringUtils.isEmpty(time) || " ".equals(time)) {
            mCurrentText.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            mCurrentText.setText(time);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_device_read://("已读",1)
                mStatus = 1;
                break;
            case R.id.rb_device_unread:// "未读",0
                mStatus = 0;
                break;
            default:
                break;

        }
    }
}
