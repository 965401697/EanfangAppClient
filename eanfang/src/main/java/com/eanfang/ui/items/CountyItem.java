package com.eanfang.ui.items;

import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.item.TreeItem;
import com.eanfang.R;
import com.yaf.sys.entity.BaseDataEntity;

import androidx.annotation.NonNull;

/**
 * Created by O u r on 2018/7/27.
 */

public class CountyItem extends TreeItem<BaseDataEntity> {
    @Override
    public int getLayoutId() {
        return R.layout.item_item_second_level;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder) {
        viewHolder.setText(R.id.tv_name, data.getDataName() + "(" + data.getLevel() + ")");
    }
}
