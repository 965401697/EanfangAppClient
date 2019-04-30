package net.eanfang.worker.ui.adapter.repair;

import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.FaultListBean;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;


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
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.iv_faultPic);
        if (!StringUtils.isEmpty(item.getPictures())) {
            String[] imgs = item.getPictures().split(",");
            simpleDraweeView.setImageURI(Uri.parse(com.eanfang.BuildConfig.OSS_SERVER  + imgs[0]));
        }
        helper.setText(R.id.tv_faultDes, item.getDescription());
        helper.setVisible(R.id.check_true,false);
    }
}
