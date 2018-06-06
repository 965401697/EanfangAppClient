package com.eanfang.ui.items;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.factory.ItemHelperFactory;
import com.baozi.treerecyclerview.item.TreeItem;
import com.baozi.treerecyclerview.item.TreeItemGroup;
import com.eanfang.R;
import com.eanfang.application.EanfangApplication;
import com.eanfang.model.SectionBean;
import com.eanfang.ui.activity.OrganizationLevelActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by O u r on 2018/5/31.
 */

public class OrgTwoLevelItem extends TreeItemGroup<SectionBean> {
    @Nullable
    @Override
    protected List<TreeItem> initChildList(SectionBean sectionBean) {
        if (sectionBean.getChildren() == null) {
            return new ArrayList<TreeItem>();
        }
        return ItemHelperFactory.createTreeItemList(sectionBean.getChildren(), OrgThreeLevelItem.class, this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_two_section;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder) {
        if (data.getCountStaff() == 0) {
            viewHolder.setText(R.id.tv_company_name, data.getOrgName());
        } else {
            viewHolder.setText(R.id.tv_company_name, data.getOrgName() + "(" + data.getCountStaff() + ")");
        }


        if (isExpand())
            viewHolder.getImageView(R.id.iv_select).setImageDrawable(viewHolder.getImageView(R.id.iv_select).getContext().getDrawable(R.drawable.ic_two_open));
        else {
            viewHolder.getImageView(R.id.iv_select).setImageDrawable(viewHolder.getImageView(R.id.iv_select).getContext().getDrawable(R.drawable.ic_two_close));
        }

        viewHolder.getView(R.id.tv_unit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (data.getChildren() == null) return;

                Intent intent = new Intent(EanfangApplication.getApplication(), OrganizationLevelActivity.class);
                intent.putExtra("flag", 2);
                intent.putExtra("bean", data);
                viewHolder.getView(R.id.tv_unit).getContext().startActivity(intent);
            }
        });
    }
}
