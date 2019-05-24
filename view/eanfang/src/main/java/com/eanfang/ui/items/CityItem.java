package com.eanfang.ui.items;

import com.annimon.stream.Stream;
import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.factory.ItemHelperFactory;
import com.baozi.treerecyclerview.item.TreeItem;
import com.baozi.treerecyclerview.item.TreeSelectItemGroup;
import com.eanfang.R;
import com.eanfang.biz.model.entity.BaseDataEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by O u r on 2018/7/27.
 */

public class CityItem extends TreeSelectItemGroup<BaseDataEntity> {
    @Nullable
    @Override
    protected List<TreeItem> initChildList(BaseDataEntity baseDataEntity) {
        return ItemHelperFactory.createTreeItemList(Stream.of(baseDataEntity).filter(bean -> bean.getParentId() != null && bean.getParentId().intValue() == baseDataEntity.getDataId()).toList(), CountyItem.class, this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_item_second_level;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder) {
        viewHolder.setText(R.id.tv_name, data.getDataName() + "(" + data.getLevel() + ")");
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
