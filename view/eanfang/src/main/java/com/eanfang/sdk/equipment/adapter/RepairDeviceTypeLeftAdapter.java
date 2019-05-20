package com.eanfang.sdk.equipment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.eanfang.R;
import com.eanfang.model.sys.BaseDataEntity;
import com.eanfang.util.ViewHolder;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/5/4.
 */

public class RepairDeviceTypeLeftAdapter extends BaseAdapter {

    private Context context;
    private List<BaseDataEntity> list = new ArrayList<>();
    private LayoutInflater inflater;
    private int selectedPosition = -1; //选中位置


    public RepairDeviceTypeLeftAdapter(Context context, List<BaseDataEntity> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_repair_device_left, parent, false);
        }

        View view_selected = ViewHolder.get(convertView, R.id.view_selected);
        TextView tvName = ViewHolder.get(convertView, R.id.tv_name);
        tvName.setText(list.get(position).getDataName());

        if (selectedPosition == position) {
            view_selected.setVisibility(View.VISIBLE);
            tvName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        } else {
            view_selected.setVisibility(View.GONE);
            tvName.setTextColor(ContextCompat.getColor(context, R.color.color_client_neworder));
        }

        return convertView;
    }


}
