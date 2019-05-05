package com.eanfang.ui.items;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;

import androidx.core.content.ContextCompat;

import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.item.TreeItem;
import com.eanfang.R;
import com.eanfang.application.EanfangApplication;
import com.eanfang.kit.cache.CacheKit;
import com.eanfang.model.SectionBean;
import com.eanfang.ui.activity.SelectPresonActivity;

/**
 * Created by O u r on 2018/5/31.
 */

public class OrgThreeLevelItem extends TreeItem<SectionBean.ChildrenBean> {


    @Override
    public int getLayoutId() {
        return R.layout.item_three_section;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder) {


        if (data.getFlag() == 2) {
            holder.getView(R.id.ll_staff).setVisibility(View.INVISIBLE);
            if (data.isAdd()) {
                holder.getView(R.id.cb_checked).setVisibility(View.VISIBLE);
            } else {
                holder.getView(R.id.cb_checked).setVisibility(View.INVISIBLE);
            }
            if (data.getCountStaff() == 0) {
                holder.setText(R.id.tv_company_name, data.getOrgName());
            } else {
                holder.setText(R.id.tv_company_name, data.getOrgName() + "(" + data.getCountStaff() + ")");
            }

            try {
                if (CacheKit.get().isClient()) {
                    holder.getView(R.id.cb_checked).setBackground(ContextCompat.getDrawable(holder.getView(R.id.ll_staff).getContext(), R.drawable.selector_single_checked_client));

                } else {
                    holder.getView(R.id.cb_checked).setBackground(ContextCompat.getDrawable(holder.getView(R.id.ll_staff).getContext(), R.drawable.selector_single_checked_worker));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (data.isChecked()) {
                ((CheckBox) holder.getView(R.id.cb_checked)).setChecked(true);
            } else {
                ((CheckBox) holder.getView(R.id.cb_checked)).setChecked(false);
            }

        } else if (data.getFlag() == 1 || data.getFlag() == 3 || data.getFlag() == 0) { // 0:多选
            if (data.getCountStaff() == 0) {
                holder.setText(R.id.tv_company_name, data.getOrgName());
                holder.getView(R.id.ll_staff).setVisibility(View.INVISIBLE);
            } else {
                holder.setText(R.id.tv_company_name, data.getOrgName() + "(" + data.getCountStaff() + ")");
                holder.getView(R.id.ll_staff).setVisibility(View.VISIBLE);
            }

            holder.getView(R.id.ll_staff).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EanfangApplication.getApplication(), SelectPresonActivity.class);
                    intent.putExtra("flag", 3);

                    if (data.getFlag() == 1) {
                        intent.putExtra("isRadio", "isRadio");
                    }

                    if (data.getFlag() == 3) {
                        intent.putExtra("isOrganization", "isOrganization");
                    }

                    intent.putExtra("bean", data);
                    holder.getView(R.id.tv_unit).getContext().startActivity(intent);
                }
            });
        }

    }
}
