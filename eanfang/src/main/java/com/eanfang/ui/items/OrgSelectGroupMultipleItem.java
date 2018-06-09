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
import com.baozi.treerecyclerview.item.TreeSelectItemGroup;
import com.eanfang.R;
import com.eanfang.model.OrganizationBean;
import com.eanfang.model.SectionBean;
import com.eanfang.model.TemplateBean;

import java.util.List;

/**
 * Created by O u r on 2018/5/31.
 */

public class OrgSelectGroupMultipleItem extends TreeSelectItemGroup<TemplateBean> {


    @Nullable
    @Override
    protected List<TreeItem> initChildList(TemplateBean templateBean) {
        return ItemHelperFactory.createTreeItemList(templateBean.getPresons(), OrgSelectItem.class, this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_second_level;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder) {

//        if (data == null || data.getPresons() == null) return;

        viewHolder.setChecked(R.id.cb_all_checked, isChildCheck());
        viewHolder.setText(R.id.tv_company_name, data.getOrgName() + "(" + data.getPresons().size() + ")");

//        viewHolder.getView(R.id.tv_all_checked).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (data.isChecked()) {
//                    //全不选
//                    data.setChecked(false);
//
//                    viewHolder.setText(R.id.tv_company_name, data.getOrgName() + "(" + data.getPresons().size() + ")");
//
//                    viewHolder.setText(R.id.tv_all_checked, "全选");
//                    for (TemplateBean.Preson p : data.getPresons()) {
//                        p.setChecked(false);
//                    }
//                    getItemManager().notifyDataChanged();
//                } else {
//                    //全选
//                    data.setChecked(true);
//                    // TODO: 2018/6/1 不生效
//                    viewHolder.setText(R.id.tv_company_name, data.getOrgName() + "(" + data.getPresons().size() + "/" + data.getPresons().size() + ")");
//
//                    for (TemplateBean.Preson p : data.getPresons()) {
//                        viewHolder.setText(R.id.tv_all_checked, "取消全选");
//                        p.setChecked(true);
//                    }
//                    getItemManager().notifyDataChanged();
//                }
//            }
//        });
    }

    @Override
    public void onClick(ViewHolder viewHolder) {
        super.onClick(viewHolder);
        selectAll(!isSelectAll());
        getItemManager().notifyDataChanged();
    }

    @Override
    public SelectFlag selectFlag() {
        return SelectFlag.MULTIPLE_CHOICE;
    }
}
