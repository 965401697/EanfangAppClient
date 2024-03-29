package net.eanfang.worker.ui.adapter;

import android.net.Uri;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.entity.BughandleDetailEntity;
import com.eanfang.config.Config;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.R;

import java.util.List;

import cn.hutool.core.util.StrUtil;


/**
 * Created by wen on 2017/4/23.
 * 故障处理 的 明细 Adapter
 */

public class FillTroubleDetailAdapter extends BaseQuickAdapter<BughandleDetailEntity, BaseViewHolder> {
    public FillTroubleDetailAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BughandleDetailEntity item) {
        String bugOne = Config.get().getBusinessNameByCode(item.getFailureEntity().getBusinessThreeCode(), 1);
        String bugTwo = Config.get().getBusinessNameByCode(item.getFailureEntity().getBusinessThreeCode(), 2);
        String bugThree = Config.get().getBusinessNameByCode(item.getFailureEntity().getBusinessThreeCode(), 3);

        // 故障简述
        if (item.getFailureEntity().getSketch() != null) {
            helper.setText(R.id.tv_name, "故障" + (helper.getAdapterPosition() + 1) + ".      " + item.getFailureEntity().getSketch());
        }

        helper.setText(R.id.tv_model, bugThree);
        helper.setText(R.id.tv_location, item.getFailureEntity().getBugPosition());
        if (StrUtil.isEmpty(item.getCheckProcess())) {
            helper.setVisible(R.id.tv_waitFinish, true);
            helper.setVisible(R.id.tv_finish, false);
        } else {
            helper.setVisible(R.id.tv_waitFinish, false);
            helper.setVisible(R.id.tv_finish, true);
        }
        ImageView draweeView = helper.getView(R.id.iv_pic);
        if (!StrUtil.isEmpty(item.getFailureEntity().getPictures())) {
            String[] urls = item.getFailureEntity().getPictures().split(",");
            GlideUtil.intoImageView(mContext, Uri.parse(BuildConfig.OSS_SERVER + urls[0]), draweeView);
            helper.addOnClickListener(R.id.ll_item);
        } else {
            draweeView.setImageResource(R.mipmap.ic_nodata);
        }
        helper.addOnClickListener(R.id.iv_pic);
    }
}
