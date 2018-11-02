package com.eanfang.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.eanfang.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.format.DateTimeFormatter;

/**
 * @author guanluocang
 * @data 2018/11/1
 * @description 选择时间D
 */

public class SelectCalendarDialogFragment extends DialogFragment implements OnDateSelectedListener {


    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    private SelectCalendarTimeListener selectTimeListener;

    // 回调接口，用于传递数据给Activity
    public interface SelectCalendarTimeListener {
        void getData(String time);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            selectTimeListener = (SelectCalendarTimeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implementon MyDialogFragment_Listener");
        }
    }


    

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_select_time, null);

        MaterialCalendarView widget = view.findViewById(R.id.calendarView);

        widget.setOnDateChangedListener(this);

        return new AlertDialog.Builder(getActivity())
                .setTitle("")
                .setView(view)
                .create();
    }


    @Override
    public void onDateSelected(
            @NonNull MaterialCalendarView widget,
            @NonNull CalendarDay date,
            boolean selected) {
        selectTimeListener.getData(FORMATTER.format(date.getDate()));
        dismiss();
    }


}
