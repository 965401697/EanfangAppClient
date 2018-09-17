package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.model.TemplateBean;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

/**
 * 描述：
 *
 * @author Guanluocang
 * @date on 2018/6/15$  19:24$
 */
public class RepairTeamWorkerAdapter extends BaseQuickAdapter<TemplateBean.Preson, BaseViewHolder> {
    public RepairTeamWorkerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, TemplateBean.Preson item) {
        helper.setText(R.id.tv_team_worker, item.getName());
        ((SimpleDraweeView) helper.getView(R.id.iv_header)).setImageURI(BuildConfig.OSS_SERVER + item.getProtraivat());
    }
}
