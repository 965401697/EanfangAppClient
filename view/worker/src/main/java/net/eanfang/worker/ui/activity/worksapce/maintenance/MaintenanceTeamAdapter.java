package net.eanfang.worker.ui.activity.worksapce.maintenance;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.TemplateBean;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

/**
 * Created by O u r on 2018/7/20.
 */

public class MaintenanceTeamAdapter extends BaseQuickAdapter<TemplateBean.Preson, BaseViewHolder> {
    public MaintenanceTeamAdapter() {
        super(R.layout.item_maintenance_team);
    }

    @Override
    protected void convert(BaseViewHolder helper, TemplateBean.Preson item) {
        ((SimpleDraweeView) helper.getView(R.id.iv_user_header)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getProtraivat()));
        helper.setText(R.id.tv_name, item.getName());
    }
}
