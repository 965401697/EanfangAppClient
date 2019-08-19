package net.eanfang.client.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.annimon.stream.Stream;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.BaseFragment;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.base.kit.cache.CacheMod;
import com.eanfang.base.network.config.HttpConfig;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.biz.model.bean.OrganizationBean;
import com.eanfang.biz.model.entity.OrgEntity;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.activity.SelectOrganizationActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.ToastUtil;
import com.eanfang.witget.recycleview.FullyLinearLayoutManager;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.im.FollowListActivity;
import net.eanfang.client.ui.activity.im.MorePopWindow;
import net.eanfang.client.ui.activity.im.MyFriendsListActivity;
import net.eanfang.client.ui.activity.im.MyGroupsListActivity;
import net.eanfang.client.ui.activity.im.PeerConnectionListActivity;
import net.eanfang.client.ui.activity.worksapce.ExternalCompanyActivity;
import net.eanfang.client.ui.activity.worksapce.PartnerActivity;
import net.eanfang.client.ui.activity.worksapce.SubcompanyActivity;
import net.eanfang.client.ui.activity.worksapce.contacts.CompanyManagerActivity;
import net.eanfang.client.ui.activity.worksapce.contacts.CreatTeamActivity;
import net.eanfang.client.ui.adapter.ParentAdapter;

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

    public final int CREAT_TEAM_CODE = 49;

    public static boolean isRefresh = false;
    /**
     * 是否解散成功
     */
    public static boolean isDisslove = false;


    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_contact, container, false);
            initView();
            setListener();
            getData();

        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    private void getData() {

        ((SwipeRefreshLayout) findViewById(R.id.swipre_fresh)).setRefreshing(true);

        EanfangHttp.get(UserApi.GET_STAFFINCOMPANY_LISTTREE)
                .execute(new EanfangCallback<OrgEntity>(getActivity(), true, OrgEntity.class, true, (list) -> {


                    if (list != null && !list.isEmpty()) {
                        //排除默认公司
                        mDatas = Stream.of(list).filter(bean -> bean.getOrgId() != 0).toList();

                    } else {
                        mDatas = Collections.EMPTY_LIST;
                    }
                    ((SwipeRefreshLayout) findViewById(R.id.swipre_fresh)).setRefreshing(false);

                    initAdapter();
                }));

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initAdapter() {

        //客户公司
        mDatas = Stream.of(mDatas).filter(beans -> beans.getOrgUnitEntity() != null && beans.getOrgUnitEntity().getUnitType() == 2).toList();
        if (mDatas.size() <= 0 || mDatas == null) {
            tv_noTeam.setVisibility(View.VISIBLE);
            rev_list.setVisibility(View.GONE);
        } else {
            //显示与隐藏
            tv_noTeam.setVisibility(View.GONE);
            rev_list.setVisibility(View.VISIBLE);

            Long companyId = ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyEntity().getOrgId();
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

    protected void initView() {
        findViewById(R.id.nested_view).getViewTreeObserver().addOnScrollChangedListener(() -> findViewById(R.id.swipre_fresh).setEnabled(findViewById(R.id.nested_view).getScrollY() == 0));

        rl_create_team = findViewById(R.id.rl_create_team);
        tv_noTeam = findViewById(R.id.tv_noTeam);

        findViewById(R.id.ll_my_friends).setOnClickListener(v -> {
            //开启我的好友列表
            startActivity(new Intent(getActivity(), MyFriendsListActivity.class));
        });
        findViewById(R.id.ll_my_group).setOnClickListener(v -> {
            //开启我的群组列表
            startActivity(new Intent(getActivity(), MyGroupsListActivity.class));
        });

        findViewById(R.id.rl_focus).setOnClickListener(v -> startActivity(new Intent(getActivity(), FollowListActivity.class)));

        findViewById(R.id.rl_peer_connection).setOnClickListener(v -> {
            //同行人脉
            startActivity(new Intent(getActivity(), PeerConnectionListActivity.class));
        });

        findViewById(R.id.ll_add).setOnClickListener(v -> {
            //添加好友界面
            MorePopWindow morePopWindow = new MorePopWindow(getActivity(), true);
            morePopWindow.showPopupWindow(findViewById(R.id.ll_add));
        });


        ((SwipeRefreshLayout) findViewById(R.id.swipre_fresh)).setOnRefreshListener(this::getData);

        rev_list = findViewById(R.id.rev_list);
        rev_list.setNestedScrollingEnabled(false);

        //设置布局样式
        rev_list.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        rev_list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


        parentAdapter = new ParentAdapter(getActivity());
        parentAdapter.bindToRecyclerView(rev_list);
        parentAdapter.setOnItemClickListener((adapter, view, position) -> {
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
        });


        parentAdapter.setOnItemChildClickListener((adapter, view, position) -> {

            if (!String.valueOf(((OrgEntity) adapter.getData().get(position)).getCompanyId()).equals(String.valueOf(ClientApplication.get().getCompanyId()))) {
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
                    Bundle bundle_company = new Bundle();
                    bundle_company.putString("companyId", String.valueOf(mDatas.get(position).getCompanyId()));
                    JumpItent.jump(getActivity(), SubcompanyActivity.class, bundle_company);
                    break;
                //外协单位
                case R.id.ll_outside_company:
                    startActivity(new Intent(getActivity(), ExternalCompanyActivity.class));
                    break;
                case R.id.ll_part_company:
                    if (PermKit.get().getCooperationListAllPerm()) {
                        startActivity(new Intent(getActivity(), PartnerActivity.class));
                    }
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
                    Intent intent_company = new Intent(getActivity(), CompanyManagerActivity.class);
                    intent_company.putExtra("orgid", mDatas.get(position).getOrgId());
                    intent_company.putExtra("orgName", mDatas.get(position).getOrgName());
                    intent_company.putExtra("isAuth", mDatas.get(position).getVerifyStatus() + "");
                    intent_company.putExtra("adminUserId", String.valueOf(mDatas.get(position).getAdminUserId()));
                    startActivityForResult(intent_company, CREAT_TEAM_CODE);
                    break;
                default:
                    break;
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        //创建完企业刷新公司
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 49) {
            getData();
        }
    }

    private void SwitchCompany() {
        EanfangHttp.get(NewApiService.SWITCH_COMPANY_ALL_LIST)
                .params("companyId", 0)
                .execute(new EanfangCallback<LoginBean>(getActivity(), false, LoginBean.class, (bean) -> {
                    if (bean != null) {
                        PermKit.permList.clear();

                        CacheKit.get().put(LoginBean.class.getName(), bean, CacheMod.All);
                        HttpConfig.get().setToken(bean.getToken());
                        EanfangHttp.setToken(bean.getToken());
                        EanfangHttp.setClient();
                    }
                }));
    }

    protected void setListener() {
        rl_create_team.setOnClickListener(v -> startActivityForResult(new Intent(getActivity(), CreatTeamActivity.class), CREAT_TEAM_CODE));
    }
}
