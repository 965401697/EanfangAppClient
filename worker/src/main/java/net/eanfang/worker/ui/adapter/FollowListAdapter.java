package net.eanfang.worker.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.BuildConfig;
import com.eanfang.model.FollowDataBean;
import com.yaf.base.entity.WorkerEntity;
import com.yaf.sys.entity.AccountEntity;
import com.yaf.sys.entity.OrgEntity;
import com.yaf.sys.entity.UserEntity;

import net.eanfang.worker.ui.adapter.viewholder.FollowListViewHolder;


/**
 * @author liangkailun
 * Date ：2019/4/9
 * Describe : 关注列表页面适配器
 */
public class FollowListAdapter extends BaseQuickAdapter<FollowDataBean.FollowListBean, FollowListViewHolder> {


    public FollowListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(FollowListViewHolder helper, FollowDataBean.FollowListBean item) {
        if (item == null) {
            return;
        }
        UserEntity userEntity = item.getUserEntity();
        if (userEntity != null && userEntity.getAccountEntity() != null) {
            AccountEntity accountEntityBean = userEntity.getAccountEntity();
            //设置姓名、头像
            helper.getTvFollowItemName().setText(accountEntityBean.getRealName());
            helper.getIvFollowItemHeader().setImageURI(BuildConfig.OSS_SERVER
                    + accountEntityBean.getAvatar());
        }

        WorkerEntity workerEntity = item.getWorkerEntity();
        if (workerEntity != null) {
            //设置认证状态
            int visible = workerEntity.getVerifyStatus() == 1 ? View.VISIBLE : View.GONE;
            helper.getImgFollowItemAuth().setVisibility(visible);
        }
        OrgEntity orgEntity = item.getOrgEntity();
        if (orgEntity != null) {
            //设置工资名称
            helper.getTvFollowItemCompany().setText(orgEntity.getOrgName());
        }
        //设置好友状态显示
        helper.getTvFollowItemFriendStatus().setVisibility(
                item.getFriend() == 0 ? View.VISIBLE : View.GONE);
        item.setFollowsStatus(0);
        helper.getBtnFollowItemAddOrCancel().setSelected(false);
        helper.getBtnFollowItemAddOrCancel().setText("已关注");
    }


}
