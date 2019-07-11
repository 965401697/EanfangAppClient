package net.eanfang.client.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.base.BaseDialog;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.client.databinding.DialogChooseAlertTimeBinding;
import net.eanfang.client.ui.adapter.LeavePostChooseAlertTimeAdapter;

import lombok.Setter;

/**
 * @author liangkailun
 * Date ：2019-07-11
 * Describe :
 */
public class ChooseAlertTimeDialog extends BaseDialog {
    private final String mAlertTime;
    private DialogChooseAlertTimeBinding binding;
    private LeavePostChooseAlertTimeAdapter mAdapter;
    @Setter
    private ChooseTimeListener mChooseTimeListener;
    public ChooseAlertTimeDialog(String alertTime) {
        this.mAlertTime = alertTime;
    }
    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    @Override
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        binding = DialogChooseAlertTimeBinding.inflate(getLayoutInflater());
        binding.recDialogChooseAlertTime.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new LeavePostChooseAlertTimeAdapter(mAlertTime);
        mAdapter.bindToRecyclerView(binding.recDialogChooseAlertTime);
        mAdapter.setNewData(GetConstDataUtils.getDetectTime());
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            String alertTime = (String) adapter.getData().get(position);
            if (mChooseTimeListener != null) {
                mChooseTimeListener.chooseAlertTime(alertTime);
            }
            dismiss();
        });
        binding.dialogChooseAlertTimeCancel.setOnClickListener(view -> dismiss());
        return binding.getRoot();
    }

    public interface ChooseTimeListener {
        /**
         * 选择实现
         *
         * @param time
         */
        void chooseAlertTime(String time);
    }

}
