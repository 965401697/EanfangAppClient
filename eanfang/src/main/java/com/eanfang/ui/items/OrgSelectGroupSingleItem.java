package com.eanfang.ui.items;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;

import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.factory.ItemHelperFactory;
import com.baozi.treerecyclerview.item.TreeItem;
import com.baozi.treerecyclerview.item.TreeItemGroup;
import com.eanfang.R;
import com.eanfang.model.TemplateBean;
import com.eanfang.util.SharePreferenceUtil;

import java.util.List;

/**
 * Created by O u r on 2018/5/31.
 */

public class OrgSelectGroupSingleItem extends TreeItemGroup<TemplateBean> {

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

        viewHolder.getView(R.id.cb_all_checked).setVisibility(View.INVISIBLE);
        try {
            if (SharePreferenceUtil.get().get("app", "").equals("client")) {
                ((CheckBox) viewHolder.getView(R.id.cb_all_checked)).setBackground(ContextCompat.getDrawable(viewHolder.getImageView(R.id.iv_select).getContext(), R.drawable.selector_single_checked_client));

            } else {
                ((CheckBox) viewHolder.getView(R.id.cb_all_checked)).setBackground(ContextCompat.getDrawable(viewHolder.getImageView(R.id.iv_select).getContext(), R.drawable.selector_single_checked_worker));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data.getPresons() != null) {
            viewHolder.setText(R.id.tv_company_name, data.getOrgName() + "(" + data.getPresons().size() + ")");
        } else {
            viewHolder.setText(R.id.tv_company_name, data.getOrgName());
        }
        if (isExpand())
//            viewHolder.getImageView(R.id.iv_select).setImageDrawable(viewHolder.getImageView(R.id.iv_select).getContext().getDrawable(R.drawable.ic_two_open));
            viewHolder.getImageView(R.id.iv_select).setImageDrawable(ContextCompat.getDrawable(viewHolder.getImageView(R.id.iv_select).getContext(), R.drawable.ic_two_open));
        else {
//            viewHolder.getImageView(R.id.iv_select).setImageDrawable(viewHolder.getImageView(R.id.iv_select).getContext().getDrawable(R.drawable.ic_two_close));
            viewHolder.getImageView(R.id.iv_select).setImageDrawable(ContextCompat.getDrawable(viewHolder.getImageView(R.id.iv_select).getContext(), R.drawable.ic_two_close));
        }
    }

}
