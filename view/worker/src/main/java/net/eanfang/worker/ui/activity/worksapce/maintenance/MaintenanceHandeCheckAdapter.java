package net.eanfang.worker.ui.activity.worksapce.maintenance;

import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.base.kit.V;
import com.yaf.base.entity.ShopMaintenanceExamResultEntity;

import net.eanfang.worker.R;

import cn.hutool.core.date.DateUtil;

/**
 * Created by O u r on 2018/7/17.
 */

public class MaintenanceHandeCheckAdapter extends BaseQuickAdapter<ShopMaintenanceExamResultEntity, BaseViewHolder> {

    private int mType;

    public MaintenanceHandeCheckAdapter(int layoutResId, int type) {
        super(layoutResId);
        this.mType = type;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void convert(BaseViewHolder helper, ShopMaintenanceExamResultEntity item) {
        //        将业务类型的图片显示到列表
        String imgUrl = V.v(() -> item.getPicture().split(",")[0]);
        if (!StringUtils.isEmpty(imgUrl) && imgUrl.length() > 10) {
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + imgUrl),helper.getView(R.id.iv_upload));
        } else {
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER ),helper.getView(R.id.iv_upload));
        }

        if (item.getStatus() == 0) {
            ((ImageView) helper.getView(R.id.iv_chapter)).setImageDrawable(helper.getView(R.id.iv_chapter).getContext().getDrawable(R.mipmap.chapter_complete));
        } else if (item.getStatus() == 1) {
            ((ImageView) helper.getView(R.id.iv_chapter)).setImageDrawable(helper.getView(R.id.iv_chapter).getContext().getDrawable(R.mipmap.chapter_leave));
        } else {
            ((ImageView) helper.getView(R.id.iv_chapter)).setImageDrawable(helper.getView(R.id.iv_chapter).getContext().getDrawable(R.mipmap.chapter_damage));
        }

        if (mType != 0) {
            helper.setVisible(R.id.rl_delete, false);
        } else {
            helper.setVisible(R.id.rl_delete, true);
        }

        helper.setText(R.id.tv_date, DateUtil.date(item.getTime()).toString());
        helper.setText(R.id.tv_question, "存在问题：" + item.getExistQuestions());
        helper.setText(R.id.tv_handle, "处理过程：" + item.getDisposeCourse());
        helper.setText(R.id.tv_notice, "备注信息：" + item.getInfo());

        helper.addOnClickListener(R.id.tv_delete);
    }
}
