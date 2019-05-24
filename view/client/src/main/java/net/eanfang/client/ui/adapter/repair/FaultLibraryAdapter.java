package net.eanfang.client.ui.adapter.repair;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.FaultListBean;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;

/**
 * 描述：
 * 故障库Adapter
 *
 * @author Guanluocang
 * @date on 2018/5/28$  13:18$
 */
public class FaultLibraryAdapter extends BaseQuickAdapter<FaultListBean.ListBean, BaseViewHolder> {
    private Context context;
    public FaultLibraryAdapter(Context context,int layoutResId) {
        super(layoutResId);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FaultListBean.ListBean item) {
        ImageView imageView = helper.getView(R.id.iv_faultPic);
        if (!StringUtils.isEmpty(item.getPictures())) {
            String[] imgs = item.getPictures().split(",");
            GlideUtil.intoImageView(context,Uri.parse(com.eanfang.BuildConfig.OSS_SERVER  + imgs[0]),imageView);
        }
        helper.setText(R.id.tv_faultDes, item.getDescription());
        helper.setVisible(R.id.check_true,false);
    }
}
