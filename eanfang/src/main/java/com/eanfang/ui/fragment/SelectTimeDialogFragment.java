package com.eanfang.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.eanfang.R;
import com.eanfang.util.StringUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import cn.hutool.core.date.DateUtil;

/**
 * @author guanluocang
 * @data 2018/11/2
 * @description 时间选择控件两个
 */

public class SelectTimeDialogFragment extends DialogFragment implements OnDateSelectedListener {

    private SelectTimeListener selectTimeListener;

    private static SelectTimeDialogFragment mSelectTimeDialogFragment;

    private String mCalendarTime = "";
    private String mHourTime = "";

    private TimePicker timePicker;

    /**
     * 标识
     */
    private String mTag = "";

    public static SelectTimeDialogFragment newInstance(String tag) {
        SelectTimeDialogFragment selectTimeDialogFragment = new SelectTimeDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", tag);
        selectTimeDialogFragment.setArguments(bundle);
        return selectTimeDialogFragment;
    }


    public static SelectTimeDialogFragment getInstance() {
        if (mSelectTimeDialogFragment == null) {
            synchronized (SelectTimeDialogFragment.class) {
                if (mSelectTimeDialogFragment == null) {
                    mSelectTimeDialogFragment = new SelectTimeDialogFragment();
                }
            }
        }

        return mSelectTimeDialogFragment;
    }

    // 回调接口，用于传递数据给Activity
    public interface SelectTimeListener {
        void getData(String time);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            selectTimeListener = (SelectTimeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implementon MyDialogFragment_Listener");
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mTag = args.getString("name");
        }
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_select_time_dialog, null);

        MaterialCalendarView widget = view.findViewById(R.id.calendarView);

        timePicker = view.findViewById(R.id.timepicker);
        //是否使用24小时制
        timePicker.setIs24HourView(true);
        TimeListener times = new TimeListener();
        timePicker.setOnTimeChangedListener(times);
        widget.setSelectedDate(DateUtil.date());
        widget.setOnDateChangedListener(this);

        if (mTag.equals("tender")) {
            timePicker.setVisibility(View.GONE);
        }
        mCalendarTime = "";
        return new AlertDialog.Builder(getActivity())
                .setTitle("")
                .setView(view)
                .setPositiveButton("确定", onClickListener)
                .create();

    }

    public DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (StringUtils.isEmpty(mCalendarTime)) {
                mCalendarTime = (new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            }
            int hour = timePicker.getCurrentHour();
            int minute = timePicker.getCurrentMinute();
            String mHour = hour + "";
            String mMinute = minute + "";
            if (hour < 10) {
                mHour = "0" + mHour;
            }
            if (minute < 10) {
                mMinute = "0" + mMinute;
            }
            mHourTime = mHour + ":" + mMinute + ":00";
            if (mTag.equals("tender")) {
                selectTimeListener.getData(mCalendarTime);
            } else {
                selectTimeListener.getData(mCalendarTime + " " + mHourTime);
            }
        }
    };

    @Override
    public void onDateSelected(
            @NonNull MaterialCalendarView widget,
            @NonNull CalendarDay date,
            boolean selected) {
        mCalendarTime = DateUtil.formatDate(date.getDate());
    }

    // 时分选择控件

    class TimeListener implements TimePicker.OnTimeChangedListener {

        /**
         * view 当前选中TimePicker控件
         * hourOfDay 当前控件选中TimePicker 的小时
         * minute 当前选中控件TimePicker  的分钟
         */
        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            mHourTime = hourOfDay + ":" + minute;
        }

    }

}
