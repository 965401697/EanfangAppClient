package net.eanfang.worker.ui.activity.worksapce.maintenance;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.V;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.ShopMaintenanceExamResultEntity;

import net.eanfang.worker.R;

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
            ((SimpleDraweeView) helper.getView(R.id.iv_upload)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + imgUrl));
        } else {
            ((SimpleDraweeView) helper.getView(R.id.iv_upload)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER));
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

        helper.setText(R.id.tv_date, GetDateUtils.dateToDateTimeString(item.getTime()));
        helper.setText(R.id.tv_question, "存在问题：" + item.getExistQuestions());
        helper.setText(R.id.tv_handle, "处理过程：" + item.getDisposeCourse());
        helper.setText(R.id.tv_notice, "备注信息：" + item.getInfo());

        helper.addOnClickListener(R.id.tv_delete);
    }
}
