package com.eanfang.ui.items;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.CheckBox;

import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.factory.ItemHelperFactory;
import com.baozi.treerecyclerview.item.TreeItem;
import com.baozi.treerecyclerview.item.TreeItemGroup;
import com.eanfang.R;
import com.eanfang.application.EanfangApplication;
import com.eanfang.model.OrganizationBean;
import com.eanfang.model.SectionBean;
import com.eanfang.ui.activity.SelectPresonActivity;

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

            if (data.getFlag() == 1) {
                sectionBeans.setFlag(1);
            } else if (data.getFlag() == 2) {
                sectionBeans.setFlag(2);
            } else if (data.getFlag() == 3) {
                sectionBeans.setFlag(3);
            }
        }
        if (totle == 0) {
            viewHolder.setText(R.id.tv_company_name, data.getOrgName());
        } else {
            viewHolder.setText(R.id.tv_company_name, data.getOrgName() + "(" + totle + ")");
        }

        if (isExpand()) {
            viewHolder.getImageView(R.id.iv_select).setImageDrawable(viewHolder.getImageView(R.id.iv_select).getContext().getDrawable(R.drawable.ic_one_close));
        } else {
            viewHolder.getImageView(R.id.iv_select).setImageDrawable(viewHolder.getImageView(R.id.iv_select).getContext().getDrawable(R.drawable.ic_one_opne));
        }
        //说明是人员单选
        if (data.getFlag() == 1 || data.getFlag() == 3 || data.getFlag() == 0) {
            if (totle != 0) {
                viewHolder.getView(R.id.ll_staff).setVisibility(View.VISIBLE);
            } else {
                viewHolder.getView(R.id.ll_staff).setVisibility(View.INVISIBLE);
            }
            viewHolder.getView(R.id.ll_staff).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EanfangApplication.getApplication(), SelectPresonActivity.class);
                    intent.putExtra("flag", 1);
                    intent.putExtra("bean", data);
                    viewHolder.getView(R.id.tv_unit).getContext().startActivity(intent);
                }
            });
            //选择部门
        } else if (data.getFlag() == 2) {
            viewHolder.getView(R.id.cb_checked).setVisibility(View.VISIBLE);

            if (data.isChecked()) {
                ((CheckBox) viewHolder.getView(R.id.cb_checked)).setChecked(true);
            } else {
                ((CheckBox) viewHolder.getView(R.id.cb_checked)).setChecked(false);
            }
        }
    }

}
