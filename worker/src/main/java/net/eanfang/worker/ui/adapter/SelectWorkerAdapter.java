package net.eanfang.worker.ui.adapter;

import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.WorkerEntity;


import net.eanfang.worker.R;

import java.util.List;

import static com.eanfang.util.V.v;

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
        // 工作年限
        helper.setText(R.id.tv_workTime, item.getVerifyEntity().getWorkingYear() + "");
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

        // 认证
        if (v(() -> item.getVerifyEntity().getStatus()) != null && item.getVerifyEntity().getStatus() == 2) {
            helper.getView(R.id.tv_auth).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_auth).setVisibility(View.GONE);
        }
        //  培训状态 （0否，1是）
        if (v(() -> item.getTrainStatus()) != null && item.getTrainStatus() == 0) {
            helper.getView(R.id.tv_train).setVisibility(View.GONE);
        } else if (v(() -> item.getTrainStatus()) != null && item.getTrainStatus() == 1) {
            helper.getView(R.id.tv_train).setVisibility(View.VISIBLE);
        }
        // 资质  0否，1是
        if (v(() -> item.getQualification()) != null && item.getQualification() == 0) {
            helper.getView(R.id.tv_qualification).setVisibility(View.GONE);
        } else if (v(() -> item.getQualification()) != null && item.getQualification() == 1) {
            helper.getView(R.id.tv_qualification).setVisibility(View.VISIBLE);
        }

//        Integer stars = (item.getItem1() + item.getItem2() + item.getItem3() + item.getItem4() + item.getItem5()) / 5;
//        helper.setRating(R.id.rb_star1, stars);
    }
}
