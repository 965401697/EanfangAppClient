package net.eanfang.worker.ui.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.worker.R;


/**
 * @author liangkailun
 * Date ：2019/4/10
 * Describe :用户主页的viewholder
 */
public class UserHomePageViewHolder extends BaseViewHolder {
    public ImageView mImgCompany;
    public TextView mTvCompany;
    public TextView mTvInServiceTime;
    public TextView mTvJobPosition;
    public UserHomePageViewHolder(View view) {
        super(view);
        mImgCompany = view.findViewById(R.id.img_company);
        mTvCompany = view.findViewById(R.id.tv_company);
        mTvInServiceTime = view.findViewById(R.id.tv_inServiceTime);
        mTvJobPosition = view.findViewById(R.id.tv_jobPosition);
    }
}
