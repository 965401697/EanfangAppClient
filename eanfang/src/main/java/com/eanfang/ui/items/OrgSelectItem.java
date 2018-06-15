package com.eanfang.ui.items;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.CheckBox;

import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.item.TreeItem;
import com.baozi.treerecyclerview.item.TreeItemGroup;
import com.eanfang.BuildConfig;
import com.eanfang.R;
import com.eanfang.model.TemplateBean;
import com.eanfang.util.SharePreferenceUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by O u r on 2018/5/31.
 */

public class OrgSelectItem extends TreeItem<TemplateBean.Preson> {


    private static ViewHolder oldViewHolder;
    private static TreeItemGroup oldParentItem;

    @Override
    public int getLayoutId() {
        return R.layout.item_item_second_level;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder) {
        viewHolder.setText(R.id.tv_name, data.getName());
        viewHolder.getImageView(R.id.iv_user_header).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + data.getProtraivat()));

        TreeItemGroup parentItem = getParentItem();
        if (parentItem instanceof OrgSelectGroupMultipleItem) {
            viewHolder.setChecked(R.id.cb_check, ((OrgSelectGroupMultipleItem) parentItem).getSelectItems().contains(this));
        }
        if (parentItem instanceof OrgSelectGroupSingleItem) {
            viewHolder.setChecked(R.id.cb_check, ((OrgSelectGroupSingleItem) parentItem).getSelectItems().contains(this));
        }
    }

    @Override
    public void onClick(ViewHolder viewHolder) {
        super.onClick(viewHolder);


//        TreeItemGroup parentItem = getParentItem();
//
//        if (parentItem instanceof OrgSelectGroupSingleItem) {
//
//            if (oldViewHolder != null) {
//                oldViewHolder.setChecked(R.id.cb_check, false);
//            }
//
//            oldViewHolder = viewHolder;
//            oldParentItem = parentItem;
//            viewHolder.setChecked(R.id.cb_check, true);
//        } else {
        getItemManager().notifyDataChanged();
//        }
    }
}
