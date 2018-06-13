package com.eanfang.ui.items;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.item.TreeItem;
import com.baozi.treerecyclerview.item.TreeItemGroup;
import com.eanfang.BuildConfig;
import com.eanfang.R;
import com.eanfang.model.TemplateBean;

/**
 * Created by O u r on 2018/5/31.
 */

public class OrgSelectItem extends TreeItem<TemplateBean.Preson> {


    @Override
    public int getLayoutId() {
        return R.layout.item_item_second_level;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder) {
        viewHolder.setText(R.id.tv_name, data.getName());
        viewHolder.getImageView(R.id.iv_user_header).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + data.getProtraivat()));

//        if (data.isChecked()) {
//            ((CheckBox) viewHolder.getView(R.id.cb_check)).setChecked(true);
//        } else {
//            ((CheckBox) viewHolder.getView(R.id.cb_check)).setChecked(false);
//        }
//
//        ((CheckBox) viewHolder.getView(R.id.cb_check)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                data.setChecked(isChecked);
//
//            }
//        });

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
//        TemplateBean.Preson preson = (TemplateBean.Preson) getItemManager().getItem(viewHolder.getAdapterPosition());
        getItemManager().notifyDataChanged();
    }
}
