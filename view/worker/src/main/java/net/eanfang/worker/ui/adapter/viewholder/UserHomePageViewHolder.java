package net.eanfang.worker.ui.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.worker.R;

import lombok.Getter;


/**
 * @author liangkailun
 * Date ：2019/4/10
 * Describe :用户主页的viewholder
 */
@Getter
public class UserHomePageViewHolder extends BaseViewHolder {
    private ImageView imgCompany;
    private TextView tvCompany;
    private TextView tvInServiceTime;
    private TextView tvJobPosition;
    public UserHomePageViewHolder(View view) {
        super(view);
        imgCompany = view.findViewById(R.id.img_company);
        tvCompany = view.findViewById(R.id.tv_company);
        tvInServiceTime = view.findViewById(R.id.tv_inServiceTime);
        tvJobPosition = view.findViewById(R.id.tv_jobPosition);
    }
}
