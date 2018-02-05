package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.ui.base.BaseDialog;
import com.yaf.sys.entity.OrgEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.StaffAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/22  17:48
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class ThirdLeaveView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rev_lista)
    RecyclerView revLista;
    private Activity mContext;
    private OrgEntity userEntity;

    public ThirdLeaveView(Activity context, boolean isfull, OrgEntity userEntity) {
        super(context, isfull);
        this.userEntity = userEntity;
        this.mContext = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("员工");
        revLista.setLayoutManager(new LinearLayoutManager(mContext));
        StaffAdapter adapter = new StaffAdapter(userEntity.getStaff());
        revLista.setAdapter(adapter);
    }
}
