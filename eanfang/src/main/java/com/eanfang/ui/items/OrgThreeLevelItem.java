package com.eanfang.ui.items;

import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.item.TreeItem;
import com.eanfang.R;
import com.eanfang.application.EanfangApplication;
import com.eanfang.model.SectionBean;
import com.eanfang.ui.activity.OrganizationLevelActivity;

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
        if (data.getCountStaff() == 0) {
            holder.setText(R.id.tv_company_name, data.getOrgName());
        } else {
            holder.setText(R.id.tv_company_name, data.getOrgName() + "(" + data.getCountStaff() + ")");
        }

        holder.getView(R.id.tv_unit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EanfangApplication.getApplication(), OrganizationLevelActivity.class);
                intent.putExtra("flag", 3);
                intent.putExtra("bean", data);
                holder.getView(R.id.tv_unit).getContext().startActivity(intent);
            }
        });

    }
}
