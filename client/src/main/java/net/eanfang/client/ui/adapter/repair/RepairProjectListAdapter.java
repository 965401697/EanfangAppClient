package net.eanfang.client.ui.adapter.repair;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaf.base.entity.ProjectEntity;

import net.eanfang.client.R;

/**
 * @author guanluocang
 * @data 2019/4/24
 * @description 项目列表 adapter
 */

public class RepairProjectListAdapter extends BaseQuickAdapter<ProjectEntity, BaseViewHolder> {
    public RepairProjectListAdapter() {
        super(R.layout.layout_repair_project_list_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectEntity item) {
        helper.setText(R.id.tb_projectName, item.getProjectName())
                .setText(R.id.tb_companyName, item.getOwnerOrgEntity().getOrgUnitEntity().getName())
                .setText(R.id.tb_name, item.getOwnerAssumeUserEntity().getAccountEntity().getRealName())
                .setText(R.id.tb_phone, item.getOwnerAssumeUserEntity().getAccountEntity().getMobile());
    }
}
