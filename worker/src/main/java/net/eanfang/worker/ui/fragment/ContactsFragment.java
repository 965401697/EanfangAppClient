package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.OrganizationBean;
import com.eanfang.ui.activity.SelectOrganizationActivity;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JumpItent;
import com.eanfang.util.ToastUtil;
import com.eanfang.witget.recycleview.FullyLinearLayoutManager;
import com.yaf.sys.entity.OrgEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.im.MorePopWindow;
import net.eanfang.worker.ui.activity.worksapce.ExternalCompanyActivity;
import net.eanfang.worker.ui.activity.im.MyFriendsListActivity;
import net.eanfang.worker.ui.activity.im.MyGroupsListActivity;
import net.eanfang.worker.ui.activity.worksapce.PartnerActivity;
import net.eanfang.worker.ui.activity.worksapce.SubcompanyActivity;
import net.eanfang.worker.ui.activity.worksapce.contacts.CompanyManagerActivity;
import net.eanfang.worker.ui.activity.worksapce.contacts.CreatTeamActivity;
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
    // 通讯录点击展开
    private boolean isFirstShow = true;
    private int mOldPosition = 0;
    private OrgEntity mOrgEntity;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initData(Bundle arguments) {
//        getData();
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
        rev_list = (RecyclerView) findViewById(R.id.rev_list);
        rev_list.setHasFixedSize(true);
        rev_list.setNestedScrollingEnabled(false);
        //设置布局样式
        rev_list.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        rev_list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
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
            parentAdapter = new ParentAdapter();
            parentAdapter.bindToRecyclerView(rev_list);
            parentAdapter.setNewData(mDatas);

            //重置数据
            isFirstShow = true;
            mOldPosition = 0;
            if (parentAdapter.getData().size() > 0) {
                mOrgEntity = parentAdapter.getData().get(0);
                mOrgEntity.setFlag(true);
            }

            rev_list.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    OrgEntity bean = (OrgEntity) adapter.getData().get(position);
                    if (position == mOldPosition) {
                        if (isFirstShow) {
                            isFirstShow = false;
                        } else {
                            isFirstShow = true;
                        }
                    } else {
                        mOrgEntity.setFlag(false);
                        isFirstShow = true;
                    }

                    bean.setFlag(isFirstShow);
                    mOrgEntity = bean;
                    parentAdapter.notifyItemChanged(mOldPosition);
                    mOldPosition = position;
                    parentAdapter.notifyItemChanged(position);
                }
            });
            parentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                if (!String.valueOf(((OrgEntity) adapter.getData().get(position)).getCompanyId()).equals(String.valueOf(EanfangApplication.get().getCompanyId()))) {
                    ToastUtil.get().showToast(getActivity(), "请到工作台切换当前被点击的公司");
                    return;
                }

                switch (view.getId()) {
                    //组织结构
                    case R.id.ll_org:

                        OrganizationBean organizationBean = new OrganizationBean();

                        organizationBean.setOrgName(mDatas.get(position).getOrgName());

                        int num = 0;

                        if (mDatas.get(position).getStaff() != null) {
                            num = mDatas.get(position).getStaff().size();
                        }

                        if (mDatas.get(position).getChildren() != null) {
                            num += mDatas.get(position).getChildren().size();
                        }

                        organizationBean.setCountStaff(num);

                        Intent intent = new Intent(getActivity(), SelectOrganizationActivity.class);
                        intent.putExtra("companyId", String.valueOf(mDatas.get(position).getCompanyId()));
                        intent.putExtra("organizationBean", organizationBean);
                        intent.putExtra("isOrganization", "isOrganization");//是否是组织架构
                        startActivity(intent);
                        break;
                    //子公司
                    case R.id.ll_child_company:
                        startActivity(new Intent(getActivity(), SubcompanyActivity.class));
                        break;
                    //外协单位
                    case R.id.ll_outside_company:
                        startActivity(new Intent(getActivity(), ExternalCompanyActivity.class));
                        break;
                    case R.id.ll_part_company:
                        startActivity(new Intent(getActivity(), PartnerActivity.class));
                        break;
                    case R.id.ll_out_contacts:
                        ToastUtil.get().showToast(getActivity(), "待开通");
                        break;
                    case R.id.tv_auth_status:
//                        startActivity(new Intent(getActivity(), AuthCompanyActivity.class)
//                                .putExtra("orgid", mDatas.get(position).getOrgId())
//                                .putExtra("orgName", mDatas.get(position).getOrgName())
//                        );
                        break;
                    case R.id.iv_setting:
                        Bundle bundle = new Bundle();
                        bundle.putLong("orgid", mDatas.get(position).getOrgId());
                        bundle.putString("orgName", mDatas.get(position).getOrgName());
                        JumpItent.jump(getActivity(), CompanyManagerActivity.class, bundle);
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

        findViewById(R.id.ll_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加好友界面
//                startActivity(new Intent(getActivity(), AddFriendActivity.class));

                MorePopWindow morePopWindow = new MorePopWindow(getActivity(), true);
                morePopWindow.showPopupWindow(findViewById(R.id.ll_add));
            }
        });
    }

    @Override
    protected void setListener() {
        rl_create_team.setOnClickListener(v -> new CreateTeamView(getActivity(), () -> getData()).show());
        rl_create_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreatTeamActivity.class));
            }
        });
    }
}
