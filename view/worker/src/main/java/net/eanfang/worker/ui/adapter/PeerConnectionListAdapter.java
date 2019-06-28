package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.PeerConnectionDataBean;
import com.eanfang.biz.model.entity.OrgEntity;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.ui.adapter.viewholder.PeerConnectionListViewHolder;


/**
 * @author liangkailun
 * Date ：2019/4/9
 * Describe : 同行人脉列表页面适配器
 */
public class PeerConnectionListAdapter extends BaseQuickAdapter<PeerConnectionDataBean.ListBean, PeerConnectionListViewHolder> {


    public PeerConnectionListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(PeerConnectionListViewHolder helper, PeerConnectionDataBean.ListBean item) {
        if (item == null) {
            return;
        }
        helper.getMTvConnectionItemName().setText(item.getRealName());
        GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER
                + item.getAvatar(),helper.getMIvConnectionItemHeader());
        //公司
        OrgEntity orgEntity = item.getOrgEntity();
        if (orgEntity != null) {
            helper.getMTvConnectionItemCompany().setText(orgEntity.getOrgName());
        }
        if (item.getDefaultUser() != null) {
            item.getDefaultUser().setFollowStatus(1);
            helper.getMBtnConnectionItemAddOrCancel().setText("+ 关注");
            helper.getMBtnConnectionItemAddOrCancel().setSelected(true);
        }
    }


}
