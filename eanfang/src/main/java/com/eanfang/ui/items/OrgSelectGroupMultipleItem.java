package com.eanfang.ui.items;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.factory.ItemHelperFactory;
import com.baozi.treerecyclerview.item.TreeItem;
import com.baozi.treerecyclerview.item.TreeSelectItemGroup;
import com.eanfang.R;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder) {


        if (data.isVisible()) {
            viewHolder.getView(R.id.cb_all_checked).setVisibility(View.INVISIBLE);
        } else {
            viewHolder.setChecked(R.id.cb_all_checked, isChildCheck());
        }


        viewHolder.setText(R.id.tv_company_name, data.getOrgName() + "(" + data.getPresons().size() + ")");

        if (isExpand())
//            viewHolder.getImageView(R.id.iv_select).setImageDrawable(viewHolder.getImageView(R.id.iv_select).getContext().getDrawable(R.drawable.ic_two_open));
        viewHolder.getImageView(R.id.iv_select).setImageDrawable(ContextCompat.getDrawable(viewHolder.getImageView(R.id.iv_select).getContext(), R.drawable.ic_two_open));
        else{
//            viewHolder.getImageView(R.id.iv_select).setImageDrawable(viewHolder.getImageView(R.id.iv_select).getContext().getDrawable(R.drawable.ic_two_close));
            viewHolder.getImageView(R.id.iv_select).setImageDrawable(ContextCompat.getDrawable(viewHolder.getImageView(R.id.iv_select).getContext(), R.drawable.ic_two_close));
        }
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
