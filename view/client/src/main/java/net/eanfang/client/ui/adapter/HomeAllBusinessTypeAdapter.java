package net.eanfang.client.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.BusinessTypeBean;

import net.eanfang.client.R;


/**
 * @author liangkailun
 * Date ：2019-06-20
 * Describe : 全部业务
 */
public class HomeAllBusinessTypeAdapter extends BaseQuickAdapter<BusinessTypeBean, HomeAllBusinessTypeAdapter.HomeAllBusinessViewHolder> {


    public HomeAllBusinessTypeAdapter() {
        super(R.layout.item_home_all_business);
    }

    @Override
    protected void convert(HomeAllBusinessViewHolder helper, BusinessTypeBean item) {
        helper.imgBusinessRes.setImageResource(item.getImgRes());
        helper.tvBusinessName.setText(item.getTypeName());
    }

    class HomeAllBusinessViewHolder extends BaseViewHolder {
        private ImageView imgBusinessRes;
        private TextView tvBusinessName;

        public HomeAllBusinessViewHolder(View view) {
            super(view);
            imgBusinessRes = view.findViewById(R.id.img_item_home_all_business);
            tvBusinessName = view.findViewById(R.id.tv_item_home_all_business);
        }
    }
}
