package net.eanfang.worker.ui.adapter.repair;

import android.net.Uri;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.FaultListBean;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.R;

import cn.hutool.core.util.StrUtil;


/**
 * 描述：
 * 故障库Adapter
 *
 * @author Guanluocang
 * @date on 2018/5/28$  13:18$
 */
public class FaultLibraryAdapter extends BaseQuickAdapter<FaultListBean.ListBean, BaseViewHolder> {
    public FaultLibraryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FaultListBean.ListBean item) {
        ImageView imagev = helper.getView(R.id.iv_faultPic);
        if (!StrUtil.isEmpty(item.getPictures())) {
            String[] imgs = item.getPictures().split(",");
            GlideUtil.intoImageView(mContext,Uri.parse(com.eanfang.BuildConfig.OSS_SERVER  + imgs[0]),imagev);
        }
        helper.setText(R.id.tv_faultDes, item.getDescription());
        helper.setVisible(R.id.check_true,false);
    }
}
