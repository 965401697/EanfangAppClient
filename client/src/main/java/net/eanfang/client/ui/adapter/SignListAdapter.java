package net.eanfang.client.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.SignListBean;

import net.eanfang.client.R;


/**
 * Created by wen on 2017/4/23.
 */

public class SignListAdapter extends BaseQuickAdapter<SignListBean, BaseViewHolder> {
    public SignListAdapter() {
        super(R.layout.item_sign_list);
    }

    private SignSecondAdapter signListSecondAdapter;

    @Override
    protected void convert(BaseViewHolder helper, SignListBean item) {
        String[] times = item.getSignDay().split("-");
        helper.setText(R.id.tv_month, times[1]);
        helper.setText(R.id.tv_year, times[0] + "年");

        RecyclerView rv_footer = helper.getView(R.id.rv_footer);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_footer.setLayoutManager(linearLayoutManager);
        signListSecondAdapter = new SignSecondAdapter();
        signListSecondAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        signListSecondAdapter.bindToRecyclerView(rv_footer);
        signListSecondAdapter.setNewData(item.getList());

//        SimpleDraweeView ivPic1, ivPic2, ivPic3;
//        ivPic1 = helper.getView(R.id.iv_pic1);
//        ivPic2 = helper.getView(R.id.iv_pic2);
//        ivPic3 = helper.getView(R.id.iv_pic3);
//        helper.setText(R.id.tv_sign_time, item.getSignTime());
//        helper.setText(R.id.tv_address, Config.get().getAddressByCode(item.getZoneCode()) + item.getDetailPlace());
//        helper.setText(R.id.tv_visit_name, item.getVisitorName());
//        helper.setText(R.id.tv_remark, item.getRemarkInfo());

//        ((SimpleDraweeView) helper.getView(R.id.iv_header)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + EanfangApplication.get().getUser().getAccount().getAvatar()));
//        helper.setText(R.id.tv_visit_name, EanfangApplication.get().getUser().getAccount().getRealName());


//        if (!StringUtils.isEmpty(item.getPictures())) {
//            String[] urls = item.getPictures().split(",");
//
//            if (urls.length >= 1) {
//                ivPic1.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + urls[0]));
//                ivPic1.setVisibility(View.VISIBLE);
//            } else {
//                ivPic1.setVisibility(View.GONE);
//                ivPic2.setVisibility(View.GONE);
//                ivPic3.setVisibility(View.GONE);
//            }
//
//            if (urls.length >= 2) {
//                ivPic2.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[1]));
//                ivPic2.setVisibility(View.VISIBLE);
//            } else {
//                ivPic2.setVisibility(View.GONE);
//                ivPic3.setVisibility(View.GONE);
//            }
//            if (urls.length >= 3) {
//                ivPic3.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[2]));
//                ivPic3.setVisibility(View.VISIBLE);
//            } else {
//                ivPic3.setVisibility(View.GONE);
//            }
//        } else {
//            helper.setVisible(R.id.ll_image, false);
//        }
    }
}
