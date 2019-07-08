package com.eanfang.ui.items;

import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.item.TreeItem;
import com.baozi.treerecyclerview.item.TreeItemGroup;
import com.eanfang.BuildConfig;
import com.eanfang.R;
import com.eanfang.base.BaseApplication;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.biz.model.TemplateBean;
import com.eanfang.util.GlideUtil;

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
        GlideUtil.intoImageView(BaseApplication.get().getApplicationContext(),Uri.parse(BuildConfig.OSS_SERVER + data.getProtraivat()),
                viewHolder.getImageView(R.id.iv_user_header));

        if (data.isVisible()) {
            viewHolder.getView(R.id.cb_check).setVisibility(View.INVISIBLE);
        }
        try {
            if (CacheKit.get().isClient()) {
                viewHolder.getView(R.id.cb_check).setBackground(ContextCompat.getDrawable(viewHolder.getTextView(R.id.tv_name).getContext(), R.drawable.selector_single_checked_client));

            } else {
                viewHolder.getView(R.id.cb_check).setBackground(ContextCompat.getDrawable(viewHolder.getTextView(R.id.tv_name).getContext(), R.drawable.selector_single_checked_worker));
            }
        } catch (Exception e) {
            e.printStackTrace();
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
