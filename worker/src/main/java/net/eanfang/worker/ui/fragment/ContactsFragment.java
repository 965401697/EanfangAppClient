package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseFragment;
import com.yaf.sys.entity.OrgEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.AddFriendActivity;
import net.eanfang.worker.ui.activity.worksapce.AuthCompanyActivity;
import net.eanfang.worker.ui.activity.worksapce.ConstansActivity;
import net.eanfang.worker.ui.activity.worksapce.ExternalCompanyActivity;
import net.eanfang.worker.ui.activity.worksapce.MyFriendsListActivity;
import net.eanfang.worker.ui.activity.worksapce.MyGroupsListActivity;
import net.eanfang.worker.ui.activity.worksapce.PartnerActivity;
import net.eanfang.worker.ui.activity.worksapce.SubcompanyActivity;
import net.eanfang.worker.ui.adapter.ParentAdapter;
import net.eanfang.worker.ui.widget.CreateTeamView;

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
    private RelativeLayout rl_create_team;
    private TextView tv_noTeam;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initData(Bundle arguments) {
        getData();
    }

//    @Override
//    public void onResume() {
//        super.onResume();3
//        getData();
//    }

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
        rev_list = (RecyclerView) findViewById(R.id.rev_list);
        //只显示 安防公司
        mDatas = Stream.of(mDatas).filter(beans -> beans.getOrgUnitEntity() != null && beans.getOrgUnitEntity().getUnitType() == 3).toList();
        if (mDatas.size() <= 0 || mDatas == null) {
            tv_noTeam.setVisibility(View.VISIBLE);
            rev_list.setVisibility(View.GONE);
        } else {
            tv_noTeam.setVisibility(View.GONE);
            rev_list.setVisibility(View.VISIBLE);
            //设置布局样式
            rev_list.setLayoutManager(new LinearLayoutManager(getContext()));
            rev_list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

            Long companyId = EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgId();
            // 获取默认公司
            List<OrgEntity> firstList = Stream.of(mDatas).filter((bean) -> bean.getCompanyId().equals(companyId)).toList();
            if (firstList.size() > 0 && firstList != null) {
                mDatas.removeAll(firstList);
                mDatas.addAll(0, firstList);
            }
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
                    case R.id.ll_part_company:
                        startActivity(new Intent(getActivity(), PartnerActivity.class));
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
    }

    @Override
    protected void initView() {
        rl_create_team = (RelativeLayout) findViewById(R.id.rl_create_team);
        tv_noTeam = (TextView) findViewById(R.id.tv_noTeam);
        findViewById(R.id.ll_my_friends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启我的好友列表
                startActivity(new Intent(getActivity(), MyFriendsListActivity.class));
            }
        });
        findViewById(R.id.ll_my_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启我的群组列表
                startActivity(new Intent(getActivity(), MyGroupsListActivity.class));
            }
        });

        findViewById(R.id.iv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加好友界面
                startActivity(new Intent(getActivity(), AddFriendActivity.class));


            }
        });
    }

    @Override
    protected void setListener() {
        rl_create_team.setOnClickListener(v -> new CreateTeamView(getActivity(), () -> getData()).show());
    }
}
