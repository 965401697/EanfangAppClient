package net.eanfang.worker.ui.adapter;

import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.model.SignListBean;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

import java.util.List;


/**
 * Created by wen on 2017/4/23.
 */

public class SignListAdapter extends BaseQuickAdapter<SignListBean.ListBean, BaseViewHolder> {
    public SignListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SignListBean.ListBean item) {
        SimpleDraweeView ivPic1, ivPic2, ivPic3;
        ivPic1 = helper.getView(R.id.iv_pic1);
        ivPic2 = helper.getView(R.id.iv_pic2);
        ivPic3 = helper.getView(R.id.iv_pic3);
        helper.setText(R.id.tv_sign_time, item.getSignTime());
        helper.setText(R.id.tv_address, item.getDetailPlace());
        helper.setText(R.id.tv_visit_name, item.getVisitorName());
        helper.setText(R.id.tv_remark, item.getRemarkInfo());
        if (item.getVisitorName() != null) {
            String first_name = item.getVisitorName();
            if (first_name.length() == 2) {
                helper.setText(R.id.tv_first_name, first_name);
            } else if (first_name.length() == 3) {
                first_name = first_name.substring(1, 2);
                helper.setText(R.id.tv_first_name, first_name);
            } else if (first_name.length() == 4) {
                first_name = first_name.substring(2, 3);
                helper.setText(R.id.tv_first_name, first_name);
            }
        }
        if (!StringUtils.isEmpty(item.getPictures())) {
            String[] urls = item.getPictures().split(",");

            if (urls.length >= 1) {
                ivPic1.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + urls[0]));
                ivPic1.setVisibility(View.VISIBLE);
            } else {
                ivPic1.setVisibility(View.GONE);
                ivPic2.setVisibility(View.GONE);
                ivPic3.setVisibility(View.GONE);
            }

            if (urls.length >= 2) {
                ivPic2.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[1]));
                ivPic2.setVisibility(View.VISIBLE);
            } else {
                ivPic2.setVisibility(View.GONE);
                ivPic3.setVisibility(View.GONE);
            }
            if (urls.length >= 3) {
                ivPic3.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[2]));
                ivPic3.setVisibility(View.VISIBLE);
            } else {
                ivPic3.setVisibility(View.GONE);
            }
        } else {
            helper.setVisible(R.id.ll_image, false);
        }
    }
}
