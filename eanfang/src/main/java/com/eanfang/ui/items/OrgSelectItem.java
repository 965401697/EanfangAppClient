package com.eanfang.ui.items;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;

import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.item.TreeItem;
import com.baozi.treerecyclerview.item.TreeItemGroup;
import com.eanfang.BuildConfig;
import com.eanfang.R;
import com.eanfang.model.TemplateBean;
import com.eanfang.ui.activity.SelectOrganizationActivity;

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

        if (data.isVisible()) {
            viewHolder.getView(R.id.cb_check).setVisibility(View.INVISIBLE);
        }

        TreeItemGroup parentItem = getParentItem();
        if (parentItem instanceof OrgSelectGroupMultipleItem) {
            viewHolder.setChecked(R.id.cb_check, ((OrgSelectGroupMultipleItem) parentItem).getSelectItems().contains(this));
        }
        if (parentItem instanceof OrgSelectGroupSingleItem) {
            if (data.isChecked()) {
                ((CheckBox) viewHolder.getView(R.id.cb_check)).setChecked(true);
            } else {
                ((CheckBox) viewHolder.getView(R.id.cb_check)).setChecked(false);
            }
        }
    }

}
