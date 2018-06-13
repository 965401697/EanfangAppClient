package com.eanfang.ui.items;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.factory.ItemHelperFactory;
import com.baozi.treerecyclerview.item.TreeItem;
import com.baozi.treerecyclerview.item.TreeSelectItemGroup;
import com.eanfang.R;
import com.eanfang.model.TemplateBean;
import com.eanfang.util.V;

import java.util.List;

/**
 * Created by O u r on 2018/5/31.
 */

public class OrgSelectGroupSingleItem extends TreeSelectItemGroup<TemplateBean> {


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


        viewHolder.getView(R.id.cb_all_checked).setVisibility(View.INVISIBLE);
        if (data.getPresons() != null) {
            viewHolder.setText(R.id.tv_company_name, data.getOrgName() + "(" + data.getPresons().size() + ")");
        } else {
            viewHolder.setText(R.id.tv_company_name, data.getOrgName());
        }

    }

    @Override
    public SelectFlag selectFlag() {
        return SelectFlag.SINGLE_CHOICE;
    }
}
