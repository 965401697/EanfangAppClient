package net.eanfang.worker.ui.activity.worksapce.oa.task;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eanfang.biz.model.TemplateBean;
import com.eanfang.sdk.selecttime.SelectTimeDialogFragment;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.oa.workreport.OAPersonAdaptet;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FiltrateTaskActivity extends BaseWorkerActivity implements SelectTimeDialogFragment.SelectTimeListener, RadioGroup.OnCheckedChangeListener {

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
        setContentView(R.layout.activity_filtrate_task);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("布置任务筛选");
        setLeftBack();

        initViews();
    }

    private void initViews() {

        mTypeList = GetConstDataUtils.getWorkReportTypeList();


        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));

        oaPersonAdaptet = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>());
        recyclerView.setAdapter(oaPersonAdaptet);

        rgStatus.setOnCheckedChangeListener(this);
//        doSelectYearMonthDay();
    }

    @OnClick({R.id.ll_start, R.id.ll_end, R.id.tv_cancle, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_start:
                mCurrentText = tvStart;
//                mTimeYearMonthDay.show();
                new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
                break;
            case R.id.ll_end:
                mCurrentText = tvEnd;
//                mTimeYearMonthDay.show();
                new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
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