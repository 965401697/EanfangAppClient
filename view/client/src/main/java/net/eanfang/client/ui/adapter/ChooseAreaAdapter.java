package net.eanfang.client.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.util.Cn2Spell;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostChooseAreaBean;


/**
 * 岗位监测按地区筛选
 *
 * @author liangkailun
 */
public class ChooseAreaAdapter extends BaseQuickAdapter<LeavePostChooseAreaBean.ListBean, BaseViewHolder> {
    /**
     * 0:地区选择 1：岗位选择
     */
    private int chooseType;

    public ChooseAreaAdapter(int layoutResId, int chooseType) {
        super(layoutResId);
        this.chooseType = chooseType;
    }

    @Override
    protected void convert(BaseViewHolder helper, LeavePostChooseAreaBean.ListBean item) {
        if (item == null) {
            return;
        }
        //根据position获取首字母作为目录catalog
        String catalog = Cn2Spell.getPinYin(item.getStationPlaceName()).substring(0, 1).toUpperCase();

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (helper.getAdapterPosition() == getPositionForSection(catalog)) {
            helper.getView(R.id.tv_letter).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_letter, catalog.toUpperCase());
        } else {
            helper.getView(R.id.tv_letter).setVisibility(View.GONE);
        }
        if (chooseType == 0) {
            helper.setText(R.id.tv_area_name, item.getStationPlaceName());
        }else {
            helper.setText(R.id.tv_area_name, item.getStationName());
        }

    }

    /**
     * 获取catalog首次出现位置
     */
    public int getPositionForSection(String catalog) {
        for (int i = 0; i < getData().size(); i++) {
            String sortStr = Cn2Spell.getPinYin(getData().get(i).getStationPlaceName()).substring(0, 1).toUpperCase();
            if (sortStr != null) {
                if (catalog.equalsIgnoreCase(sortStr)) {
                    return i;
                }
            }
        }
        return -1;
    }
}
