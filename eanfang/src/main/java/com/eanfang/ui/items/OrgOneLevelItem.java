package com.eanfang.ui.items;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.factory.ItemHelperFactory;
import com.baozi.treerecyclerview.item.TreeItem;
import com.baozi.treerecyclerview.item.TreeItemGroup;
import com.eanfang.R;
import com.eanfang.model.OrganizationBean;
import com.eanfang.model.SectionBean;

import java.util.List;


/**
 * Created by O u r on 2018/5/31.
 */

public class OrgOneLevelItem extends TreeItemGroup<OrganizationBean> {
    @Nullable
    @Override
    protected List<TreeItem> initChildList(OrganizationBean organizationBean) {
        return ItemHelperFactory.createTreeItemList(organizationBean.getSectionBeanList(), OrgTwoLevelItem.class, this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_one_organization;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder) {
        int totle = 0;
        for (SectionBean sectionBeans : data.getSectionBeanList()) {
            totle += sectionBeans.getCountStaff();
        }
        if (totle == 0) {
            viewHolder.setText(R.id.tv_company_name, data.getOrgName());
        } else {
            viewHolder.setText(R.id.tv_company_name, data.getOrgName() + "(" + totle + ")");
        }

        if (isExpand())
            viewHolder.getImageView(R.id.iv_select).setImageDrawable(viewHolder.getImageView(R.id.iv_select).getContext().getDrawable(R.drawable.ic_one_close));
        else {
            viewHolder.getImageView(R.id.iv_select).setImageDrawable(viewHolder.getImageView(R.id.iv_select).getContext().getDrawable(R.drawable.ic_one_opne));
        }

        viewHolder.getView(R.id.tv_unit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


}
