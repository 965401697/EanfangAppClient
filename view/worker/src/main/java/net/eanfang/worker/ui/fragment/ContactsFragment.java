package net.eanfang.worker.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.FastjsonConfig;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.bean.LoginBean;
import com.eanfang.model.OrganizationBean;
import com.eanfang.network.config.HttpConfig;
import com.eanfang.ui.activity.SelectOrganizationActivity;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.ToastUtil;
import com.eanfang.witget.recycleview.FullyLinearLayoutManager;
import com.eanfang.model.sys.OrgEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.im.MorePopWindow;
import net.eanfang.worker.ui.activity.im.MyFriendsListActivity;
import net.eanfang.worker.ui.activity.im.MyGroupsListActivity;
import net.eanfang.worker.ui.activity.worksapce.ExternalCompanyActivity;
import net.eanfang.worker.ui.activity.worksapce.PartnerActivity;
import net.eanfang.worker.ui.activity.worksapce.SubcompanyActivity;
import net.eanfang.worker.ui.activity.worksapce.contacts.CompanyManagerActivity;
import net.eanfang.worker.ui.activity.worksapce.contacts.CreatTeamActivity;
import net.eanfang.worker.ui.adapter.ParentAdapter;

import java.util.Collections;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:07
 * @email houzhongzhou@yeah.net
 * @desc 通讯录
 */

public class ContactsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private List<OrgEntity> mDatas;
    private ParentAdapter parentAdapter;
    private RecyclerView rev_list;
    private RelativeLayout rl_create_team;

    // 通讯录点击展开
    private boolean isFirstShow = true;
    private int mOldPosition = 0;
    private OrgEntity mOrgEntity;
    private View view;

    public static boolean isRefresh = false;

    public final int CREAT_TEAM_CODE = 49;

    /**
     * 是否解散成功
     */
    public static boolean isDisslove = false;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_contact;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_contact, container, false);
            initView();
            setListener();
            getData();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        return view;
    }

    @Override
    protected void initData(Bundle arguments) {
//        getData();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
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
                    ((SwipeRefreshLayout) view.findViewById(R.id.swipre_fresh)).setRefreshing(false);

                    initAdapter();
                }));

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initAdapter() {

        //客户公司
        mDatas = Stream.of(mDatas).filter(beans -> beans.getOrgUnitEntity() != null && beans.getOrgUnitEntity().getUnitType() == 3).toList();
        if (mDatas.size() <= 0 || mDatas == null) {
            rl_create_team.setVisibility(View.VISIBLE);
            rev_list.setVisibility(View.GONE);
        } else {
            //显示与隐藏
            rl_create_team.setVisibility(View.GONE);
            rev_list.setVisibility(View.VISIBLE);

            Long companyId = EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgId();
            // 获取默认公司 进行排序和添加到第一个item
            List<OrgEntity> firstList = Stream.of(mDatas).filter((bean) -> bean.getCompanyId().equals(companyId)).toList();
            if (firstList.size() > 0 && firstList != null) {
                mDatas.removeAll(firstList);
                mDatas.addAll(0, firstList);
            }

            parentAdapter.setNewData(mDatas);

            //重置数据
            isFirstShow = true;
            mOldPosition = 0;
            if (parentAdapter.getData().size() > 0) {
                mOrgEntity = parentAdapter.getData().get(0);
                mOrgEntity.setFlag(true);
            }
        }
    }

    @Override
    protected void initView() {

        view.findViewById(R.id.nested_view).getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                view.findViewById(R.id.swipre_fresh).setEnabled(view.findViewById(R.id.nested_view).getScrollY() == 0);
            }
        });


        rl_create_team = view.findViewById(R.id.rl_create_team);

        view.findViewById(R.id.ll_my_friends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启我的好友列表
                startActivity(new Intent(getActivity(), MyFriendsListActivity.class));
            }
        });
        view.findViewById(R.id.ll_my_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启我的群组列表
                startActivity(new Intent(getActivity(), MyGroupsListActivity.class));
            }
        });

//        findViewById(R.id.rl_focus).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        view.findViewById(R.id.ll_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加好友界面
//                startActivity(new Intent(getActivity(), AddFriendActivity.class));

                MorePopWindow morePopWindow = new MorePopWindow(getActivity(), true);
                morePopWindow.showPopupWindow(view.findViewById(R.id.ll_add));
            }
        });


        ((SwipeRefreshLayout) view.findViewById(R.id.swipre_fresh)).setOnRefreshListener(this);


        rev_list = view.findViewById(R.id.rev_list);
//        rev_list.setHasFixedSize(true);//应该reycylerview reqestlayout()计算
        rev_list.setNestedScrollingEnabled(false);

        //设置布局样式
        rev_list.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        rev_list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


        parentAdapter = new ParentAdapter();
        parentAdapter.bindToRecyclerView(rev_list);

        parentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                OrgEntity bean = (OrgEntity) adapter.getData().get(position);
                if (position == mOldPosition) {
                    isFirstShow = !isFirstShow;
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

                    if (!PermKit.get().getCompanyDepartmentListPerm()) {
                        return;
                    }

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
                    if (!PermKit.get().getCooperationListAllPerm()) {
                        return;
                    }
                    startActivity(new Intent(getActivity(), PartnerActivity.class));
                    break;
                case R.id.ll_out_contacts:
                    ToastUtil.get().showToast(getActivity(), "待开通");
                    break;
                case R.id.tv_auth_status:
//                        startActivity(new Intent(getActivity(), AuthCompanyDataActivity.class)
//                                .putExtra("orgid", mDatas.get(position).getOrgId())
//                                .putExtra("orgName", mDatas.get(position).getOrgName())
//                        );
                    break;
                case R.id.iv_setting:
                    Bundle bundle = new Bundle();
                    bundle.putLong("orgid", mDatas.get(position).getOrgId());
                    bundle.putString("orgName", mDatas.get(position).getOrgName());
                    bundle.putString("isAuth", mDatas.get(position).getVerifyStatus() + "");
                    bundle.putString("adminUserId", String.valueOf(mDatas.get(position).getAdminUserId()));
                    JumpItent.jump(getActivity(), CompanyManagerActivity.class, bundle, CREAT_TEAM_CODE);
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isRefresh) {
            getData();
            isRefresh = false;
        }
        if (isDisslove) {
            getData();
            SwitchCompany();
            isDisslove = false;
        }
    }

    @Override
    protected void setListener() {
//        rl_create_team.setOnClickListener(v -> new CreateTeamView(getActivity(), () -> getData()).show());
        rl_create_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), CreatTeamActivity.class), CREAT_TEAM_CODE);
            }
        });
//        rl_create_team.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent("com.eanfang.intent.action.SELECTCONTACT");
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 49) {
            getData();
        }
    }

    @Override
    public void onRefresh() {
        getData();
    }

    private void SwitchCompany() {
        EanfangHttp.get(NewApiService.SWITCH_COMPANY_ALL_LIST)
                .params("companyId", 0)
                .execute(new EanfangCallback<LoginBean>(getActivity(), false, LoginBean.class, (bean) -> {
                    if (bean != null) {
                        PermKit.permList.clear();
                        EanfangApplication.get().remove(LoginBean.class.getName());
                        EanfangApplication.get().set(LoginBean.class.getName(),bean);
                        EanfangHttp.setToken(EanfangApplication.get().getUser().getToken());
                        HttpConfig.get().setToken(EanfangApplication.get().getUser().getToken());
                        EanfangHttp.setClient();
                    }
                }));
    }
}