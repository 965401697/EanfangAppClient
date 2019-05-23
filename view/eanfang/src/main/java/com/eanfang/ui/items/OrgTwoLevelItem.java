package com.eanfang.ui.items;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.factory.ItemHelperFactory;
import com.baozi.treerecyclerview.item.TreeItem;
import com.baozi.treerecyclerview.item.TreeItemGroup;
import com.eanfang.R;
import com.eanfang.base.BaseApplication;
import com.eanfang.kit.cache.CacheKit;
import com.eanfang.model.SectionBean;
import com.eanfang.ui.activity.SelectPresonActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by O u r on 2018/5/31.
 */

public class OrgTwoLevelItem extends TreeItemGroup<SectionBean> {
    @Nullable
    @Override
    protected List<TreeItem> initChildList(SectionBean sectionBean) {
        if (sectionBean.getChildren() == null) {
            return new ArrayList<TreeItem>();
        }
        return ItemHelperFactory.createTreeItemList(sectionBean.getChildren(), OrgThreeLevelItem.class, this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_two_section;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder) {

        if (data.getChildren() != null) {
            for (SectionBean.ChildrenBean childrenBean : data.getChildren()) {
                if (data.getFlag() == 1) {
                    childrenBean.setFlag(1);
                } else if (data.getFlag() == 2) {
                    childrenBean.setFlag(2);
                    if (data.isAdd()) {
                        childrenBean.setAdd(true);
                    }
                } else if (data.getFlag() == 3) {
                    childrenBean.setFlag(3);
                }
            }
        }

        if (data.getCountStaff() > 0) {
            viewHolder.setText(R.id.tv_company_name, data.getOrgName() + "(" + data.getCountStaff() + ")");
            viewHolder.getView(R.id.ll_staff).setVisibility(View.VISIBLE);
        } else {
            viewHolder.setText(R.id.tv_company_name, data.getOrgName());
            viewHolder.getView(R.id.ll_staff).setVisibility(View.INVISIBLE);
        }

        try {
            if (CacheKit.get().isClient()) {
                viewHolder.getView(R.id.cb_checked).setBackground(ContextCompat.getDrawable(viewHolder.getImageView(R.id.iv_select).getContext(), R.drawable.selector_single_checked_client));

            } else {
                viewHolder.getView(R.id.cb_checked).setBackground(ContextCompat.getDrawable(viewHolder.getImageView(R.id.iv_select).getContext(), R.drawable.selector_single_checked_worker));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isExpand()) {
            viewHolder.getImageView(R.id.iv_select).setImageDrawable(ContextCompat.getDrawable(viewHolder.getImageView(R.id.iv_select).getContext(), R.drawable.ic_one_opne));
        } else {
            viewHolder.getImageView(R.id.iv_select).setImageDrawable(ContextCompat.getDrawable(viewHolder.getImageView(R.id.iv_select).getContext(), R.drawable.ic_one_close));
        }

        //说明是单选
        if (data.getFlag() == 1 || data.getFlag() == 3 || data.getFlag() == 0) {

            viewHolder.getView(R.id.ll_staff).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (data.getChildren() == null && data.getStaff() == null) {
                        return;
                    }

                    Intent intent = new Intent(BaseApplication.get(), SelectPresonActivity.class);
                    intent.putExtra("flag", 2);
                    if (data.getFlag() == 1) {
                        intent.putExtra("isRadio", "isRadio");
                    }

                    if (data.getFlag() == 3) {
                        intent.putExtra("isOrganization", "isOrganization");
                    }

                    intent.putExtra("bean", data);
                    viewHolder.getView(R.id.tv_unit).getContext().startActivity(intent);
                }
            });

        } else if (data.getFlag() == 2) {

            viewHolder.getView(R.id.ll_staff).setVisibility(View.INVISIBLE);
            viewHolder.getView(R.id.cb_checked).setVisibility(View.VISIBLE);
            if (data.isChecked()) {
                ((CheckBox) viewHolder.getView(R.id.cb_checked)).setChecked(true);
            } else {
                ((CheckBox) viewHolder.getView(R.id.cb_checked)).setChecked(false);
            }


        }

    }

}
