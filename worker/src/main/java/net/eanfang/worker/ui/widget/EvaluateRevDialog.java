package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eanfang.model.GiveEvaluateBean;
import com.eanfang.ui.base.BaseDialog;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:39
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class EvaluateRevDialog extends BaseDialog {

    @BindView(R.id.rb_star1)
    MaterialRatingBar rbStar1;
    @BindView(R.id.rb_star2)
    MaterialRatingBar rbStar2;
    @BindView(R.id.rb_star3)
    MaterialRatingBar rbStar3;
    @BindView(R.id.rb_star4)
    MaterialRatingBar rbStar4;
    @BindView(R.id.rb_star5)
    MaterialRatingBar rbStar5;

    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.titles_bar)
    LinearLayout titlesBar;
    private GiveEvaluateBean.ListBean bean;

    public EvaluateRevDialog(Activity context, GiveEvaluateBean.ListBean bean) {
        super(context);
        this.bean = bean;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_evaluate_client);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titlesBar.setVisibility(View.GONE);
        tvSelect.setVisibility(View.GONE);
        rbStar1.setNumStars(bean.getItem1());
        rbStar2.setNumStars(bean.getItem2());
        rbStar3.setNumStars(bean.getItem3());
        rbStar4.setNumStars(bean.getItem4());
        rbStar5.setNumStars(bean.getItem5());
        rbStar1.setIsIndicator(true);
        rbStar2.setIsIndicator(true);
        rbStar3.setIsIndicator(true);
        rbStar4.setIsIndicator(true);
        rbStar5.setIsIndicator(true);

    }


}

