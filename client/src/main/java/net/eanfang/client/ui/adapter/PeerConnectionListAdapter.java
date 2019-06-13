package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.BuildConfig;
import com.eanfang.model.PeerConnectionDataBean;
import com.yaf.sys.entity.OrgEntity;

import net.eanfang.client.ui.adapter.viewholder.PeerConnectionListViewHolder;


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
        helper.getTvConnectionItemName().setText(item.getRealName());
        helper.getIvConnectionItemHeader().setImageURI(BuildConfig.OSS_SERVER
                + item.getAvatar());
        //公司
        OrgEntity orgEntity = item.getOrgEntity();
        if (orgEntity != null) {
            helper.getTvConnectionItemCompany().setText(orgEntity.getOrgName());
        }
        if (item.getDefaultUser() != null) {
            item.getDefaultUser().setFollowStatus(1);
            helper.getBtnConnectionItemAddOrCancel().setText("+ 关注");
            helper.getBtnConnectionItemAddOrCancel().setSelected(true);
        }


    }


}
