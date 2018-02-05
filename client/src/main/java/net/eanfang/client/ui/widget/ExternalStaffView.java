package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.OuterListBean;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.sys.entity.UserEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.StaffAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/23  19:12
 * @email houzhongzhou@yeah.net
 * @desc 外部联系人
 */

public class ExternalStaffView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rev_list)
    RecyclerView revList;
    private Activity mContext;
    private Long orgid;

    public ExternalStaffView(Activity context, boolean isfull, Long id) {
        super(context, isfull);
        this.mContext = context;
        this.orgid = id;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_comment_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("人员");
        initData();
    }

    private void initData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("departmentId", orgid + "");
        EanfangHttp.post(UserApi.GET_STAFFTEMP_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<OuterListBean>(mContext, true, OuterListBean.class, (bean) -> {
                    List<UserEntity> list = bean.getList();
//                    List<UserEntity> staffList = Stream.of(list).filter(beans -> beans.getDepartmentId().equals(orgid)).toList();
                    initAdapter(list);
                }));

    }

    private void initAdapter(List<UserEntity> mDataList) {
        revList.setLayoutManager(new LinearLayoutManager(mContext));
        StaffAdapter parentAdapter = new StaffAdapter(mDataList);
        revList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                // TODO: 2018/1/23 点击人员处理

            }
        });
        revList.setAdapter(parentAdapter);
    }
}
