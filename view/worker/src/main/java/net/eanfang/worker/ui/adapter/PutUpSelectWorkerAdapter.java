package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.util.GetConstDataUtils;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.worker.R;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 我要报修中的选择技师的adapter
 * Created by Administrator on 2017/3/15.
 */

public class PutUpSelectWorkerAdapter extends BaseQuickAdapter<WorkerEntity, BaseViewHolder> {
    public PutUpSelectWorkerAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkerEntity item) {
//        if (!StringUtils.isEmpty(item.getHeadpic())){
//            Uri uri = Uri.parse(item.getHeadpic());
//            ImageView imagev = helper.getView(R.id.iv_header);
//            imagev.setImageURI(uri);
//        }

        float starNum = (float) 5.0;
        if (item.getPublicPraise() > 0) {
            starNum = item.getPublicPraise();
        }

        String per = "100%";
        if (item.getGoodRate() >= 0) {
            per = item.getGoodRate() + "%";
        }


        helper.setText(R.id.tv_name, item.getAccountEntity().getRealName())
                .setText(R.id.tv_company, item.getCompanyEntity().getOrgName())
                .setText(R.id.tv_koubei, starNum + "分")
                .setText(R.id.tv_haopinglv, per)
                .setText(R.id.tv_years, GetConstDataUtils.getWorkingYearList().get(item.getVerifyEntity().getWorkingYear()));

        MaterialRatingBar rb_star = helper.getView(R.id.rb_star);
        rb_star.setRating(starNum);
        helper.addOnClickListener(R.id.btn_select);

    }
}
