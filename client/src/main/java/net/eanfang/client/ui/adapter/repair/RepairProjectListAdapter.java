package net.eanfang.client.ui.adapter.repair;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;

import org.json.JSONObject;

/**
 * @author guanluocang
 * @data 2019/4/24
 * @description 项目列表 adapter
 */

public class RepairProjectListAdapter extends BaseQuickAdapter<JSONObject,BaseViewHolder>{
    public RepairProjectListAdapter() {
        super(R.layout.layout_repair_personal_info_top);
    }

    @Override
    protected void convert(BaseViewHolder helper, JSONObject item) {

    }
}
