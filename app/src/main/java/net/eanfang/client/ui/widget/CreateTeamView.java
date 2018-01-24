package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.eanfang.base.BaseDialog;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/22  14:32
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class CreateTeamView extends BaseDialog {
    @BindView(R.id.tv_create_team)
    TextView tvCreateTeam;
    private Activity mContext;

    public CreateTeamView(Activity context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_create_team);
        ButterKnife.bind(this);
    }
}
