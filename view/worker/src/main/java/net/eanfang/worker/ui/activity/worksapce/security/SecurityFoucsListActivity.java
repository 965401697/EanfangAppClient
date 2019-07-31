package net.eanfang.worker.ui.activity.worksapce.security;

import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.biz.model.QueryEntry;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2019/3/29
 * @description 关注人列表
 */

public class SecurityFoucsListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_foucslist)
    RecyclerView rvFoucslist;
    @BindView(R.id.tv_no_datas)
    TextView tvNoDatas;
    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout swipreFresh;

    private QueryEntry queryEntry;
    private int mPage = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_security_foucs_list);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        iniView();
    }

    private void iniView() {
        setLeftBack();
        setTitle("我的关注");
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() {

    }
}
