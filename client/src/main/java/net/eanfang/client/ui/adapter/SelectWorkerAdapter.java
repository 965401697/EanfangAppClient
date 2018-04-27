package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.model.CollectionWorkerListBean;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.client.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/4/27.
 */

public class SelectWorkerAdapter extends BaseQuickAdapter<WorkerEntity, BaseViewHolder> {


    public SelectWorkerAdapter(int layoutResId, List<WorkerEntity> mWorkData) {
        super(layoutResId);
        this.mData = mWorkData;
    }

    public void refreshList(List refreshData) {
        this.mData = refreshData;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkerEntity item) {
        // 头像
        SimpleDraweeView iv_header = helper.getView(R.id.iv_header);
        if (!StringUtils.isEmpty(item.getAccountEntity().getAvatar())) {
            iv_header.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getAccountEntity().getAvatar()));
        }
        // 公司名称
        helper.setText(R.id.tv_companyName, item.getCompanyEntity().getOrgName());
        //姓名
        helper.setText(R.id.tv_name, item.getAccountEntity().getRealName());
        if (item.getPublicPraise() != 0) {
            // 口碑
            helper.setText(R.id.tv_koubei, item.getPublicPraise() / 100 + "");
        }
        if (item.getGoodRate() != 0) {
            // 好评率
            helper.setText(R.id.tv_haopinglv, item.getGoodRate() + "%" + "");
        }
        Integer stars = (item.getItem1() + item.getItem2() + item.getItem3() + item.getItem4() + item.getItem5()) / 5;
        helper.setRating(R.id.rb_star1, stars);
    }
}
