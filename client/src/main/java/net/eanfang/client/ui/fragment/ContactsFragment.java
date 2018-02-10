package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.annimon.stream.Stream;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseFragment;
import com.yaf.sys.entity.OrgEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.AuthCompanyActivity;
import net.eanfang.client.ui.activity.worksapce.ConstansActivity;
import net.eanfang.client.ui.activity.worksapce.ExternalCompanyActivity;
import net.eanfang.client.ui.activity.worksapce.SubcompanyActivity;
import net.eanfang.client.ui.adapter.ParentAdapter;
import net.eanfang.client.ui.widget.CreateTeamView;

import java.util.Collections;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:07
 * @email houzhongzhou@yeah.net
 * @desc 通讯录
 */

public class ContactsFragment extends BaseFragment {
    private List<OrgEntity> mDatas;
    private ParentAdapter parentAdapter;
    private RecyclerView rev_list;
    private ImageView iv_add;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initData(Bundle arguments) {
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        EanfangHttp.get(UserApi.GET_STAFFINCOMPANY_LISTTREE)
                .execute(new EanfangCallback<OrgEntity>(getActivity(), true, OrgEntity.class, true, (list) -> {
                    if (list != null && !list.isEmpty()) {
                        //排除默认公司
                        mDatas = Stream.of(list).filter(bean -> bean.getOrgId() != 0).toList();
                    } else {
                        mDatas = Collections.EMPTY_LIST;
                    }
                    initAdapter();
                }));
    }

    private void initAdapter() {
        rev_list.setLayoutManager(new LinearLayoutManager(getContext()));
        //客户公司
        mDatas = Stream.of(mDatas).filter(beans -> beans.getOrgUnitEntity().getUnitType() == 2).toList();
        parentAdapter = new ParentAdapter(mDatas);
        rev_list.setAdapter(parentAdapter);
        parentAdapter.notifyDataSetChanged();
        parentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                //组织结构
                case R.id.tv_org:
                    if (mDatas.get(position) != null) {
                        startActivity(new Intent(getActivity(), ConstansActivity.class).putExtra("data", mDatas.get(position)));
                    }
                    break;
                //子公司
                case R.id.tv_child_company:
                    startActivity(new Intent(getActivity(), SubcompanyActivity.class));
                    break;
                //外协单位
                case R.id.tv_outside_company:
                    startActivity(new Intent(getActivity(), ExternalCompanyActivity.class));
                    break;
                case R.id.tv_auth_status:
                    startActivity(new Intent(getActivity(), AuthCompanyActivity.class)
                            .putExtra("orgid", mDatas.get(position).getOrgId())
                            .putExtra("orgName", mDatas.get(position).getOrgName())
                    );
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    protected void initView() {
        setTitle("通讯录");
        rev_list = (RecyclerView) findViewById(R.id.rev_list);
        iv_add = (ImageView) findViewById(R.id.iv_add);

    }

    @Override
    protected void setListener() {
        iv_add.setOnClickListener(v -> new CreateTeamView(getActivity()).show());
    }
}
