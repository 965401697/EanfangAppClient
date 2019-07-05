package net.eanfang.client.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.bean.AllBrandBean;

import net.eanfang.client.R;


/**
 * @author liangkailun
 * Date ：2019-06-20
 * Describe :全部品牌adapter
 */
public class HomeAllBrandAdapter extends BaseQuickAdapter<AllBrandBean.ListBean, HomeAllBrandAdapter.HomeAllBrandViewHolder> {


    public HomeAllBrandAdapter() {
        super(R.layout.item_home_all_brand);
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    @Override
    protected void convert(HomeAllBrandViewHolder helper, AllBrandBean.ListBean item) {
        Glide.with(mContext).load(BuildConfig.OSS_SERVER + item.getRemarkInfo()).into(helper.imgItemHomeAllBrand);

    }

    class HomeAllBrandViewHolder extends BaseViewHolder {
        private ImageView imgItemHomeAllBrand;

        public HomeAllBrandViewHolder(View view) {
            super(view);
            imgItemHomeAllBrand = view.findViewById(R.id.img_item_home_all_brand);
        }
    }
}
