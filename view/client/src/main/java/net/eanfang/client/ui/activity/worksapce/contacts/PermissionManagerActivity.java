package net.eanfang.client.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.RoleBean;
import com.eanfang.biz.model.TemplateBean;
import com.eanfang.biz.model.device.User;
import com.eanfang.ui.activity.SelectOAPresonActivity;
import com.eanfang.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PermissionManagerActivity extends BaseClientActivity {

    @BindView(R.id.iv_user_header)
    SimpleDraweeView ivUserHeader;
    @BindView(R.id.tv_name_phone)
    TextView tvNamePhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_role)
    TextView tvRole;
    @BindView(R.id.ll_select_staff)
    LinearLayout llSelectStaff;
    @BindView(R.id.ll_role)
    LinearLayout llRole;
    @BindView(R.id.rl_checked_staff)
    RelativeLayout rlCheckedStaff;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private AolltRoleAdapter aolltRoleAdapter;

    private TemplateBean.Preson mBean;

    private ArrayList<String> roleIdList = new ArrayList<>();
    private ArrayList<String> roleNameList = new ArrayList<>();

    private final int ROLE_FLAG = 101;
    private String departmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        jumpSelectOAPresonAc();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_manager);
        ButterKnife.bind(this);
        setTitle("权限管理");
        setLeftBack();

        setRightTitle("确认授权");
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subMermission();
            }
        });

        initViews();
    }


    @OnClick({R.id.rl_checked_staff, R.id.ll_select_staff, R.id.ll_role})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_checked_staff:
            case R.id.ll_select_staff:
                jumpSelectOAPresonAc();
                break;

//            case R.id.ll_role:
//                Intent in = new Intent(this, AolltRoleActivity.class);
//                in.putStringArrayListExtra("roleNameList", roleNameList);
//                startActivityForResult(in, ROLE_FLAG);
//                break;
            default:
                break;
        }
    }
    private void jumpSelectOAPresonAc(){
        Intent intent = new Intent(this, SelectOAPresonActivity.class);
        intent.putExtra("isRadio", "isRadio");
        startActivity(intent);
    }
    private void subMermission() {
        if (TextUtils.isEmpty(departmentId)) {
            ToastUtil.get().showToast(this, "员工不能为空");
            return;
        }
        if (roleIdList.size() == 0) {
            ToastUtil.get().showToast(this, "角色不能为空");
            return;
        }

        //添加角色
        EanfangHttp.post(NewApiService.ADD_STAFF_ROLE + "/" + mBean.getUserId())
                .upJson(JSON.toJSONString(roleIdList))
                .execute(new EanfangCallback<JSONObject>(PermissionManagerActivity.this, true, JSONObject.class) {

                    @Override
                    public void onSuccess(JSONObject bean) {
                        ToastUtil.get().showToast(PermissionManagerActivity.this, "授权成功");

                        endTransaction(true);
                    }

                });


    }

    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {


        if (presonList.size() > 0) {
            mBean = presonList.get(0);
            departmentId = mBean.getDepartmentId();
            llSelectStaff.setVisibility(View.GONE);
            rlCheckedStaff.setVisibility(View.VISIBLE);
            llRole.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);


            EanfangHttp.get(UserApi.POST_USER_INFO + mBean.getId())
                    .execute(new EanfangCallback<User>(this, true, User.class, (b) -> {


                        ivUserHeader.setImageURI(BuildConfig.OSS_SERVER + b.getAvatar());
                        tvNamePhone.setText(b.getRealName() + "(" + b.getMobile() + ")");
                        if (!TextUtils.isEmpty(b.getAreaCode())) {
                            tvAddress.setText(Config.get().getAddressByCode(b.getAreaCode()) + b.getAddress());
                        }


                        EanfangHttp.get(NewApiService.MY_CURREMT_LIST_ROLE + mBean.getUserId())
                                .execute(new EanfangCallback<RoleBean>(PermissionManagerActivity.this, false, RoleBean.class, true, (list) -> {
                                    roleNameList.clear();
                                    for (RoleBean roleBean : list) {
                                        roleNameList.add(roleBean.getRoleName());
                                        initData();
                                    }
                                }));

                    }));

        } else {
            llSelectStaff.setVisibility(View.VISIBLE);
            rlCheckedStaff.setVisibility(View.GONE);
            llRole.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }


    }


    private void initData() {
        EanfangHttp.get(NewApiService.MY_LIST_ROLE)
                .execute(new EanfangCallback<RoleBean>(this, true, RoleBean.class, true, (list) -> {

                    for (RoleBean roleBean : list) {
                        if (roleNameList.contains(roleBean.getRoleName())) {
                            roleBean.setChecked(true);
                            roleIdList.add(roleBean.getRoleId());
                        }
                    }


                    aolltRoleAdapter.getData().clear();
                    aolltRoleAdapter.setNewData(list);
                }));
    }


    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        aolltRoleAdapter = new AolltRoleAdapter(R.layout.item_role);
        aolltRoleAdapter.bindToRecyclerView(recyclerView);


        aolltRoleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                RoleBean bean = (RoleBean) adapter.getData().get(position);

                if (bean.isChecked()) {
                    bean.setChecked(false);
                    roleIdList.remove(bean.getRoleId());
//                    roleNameList.remove(bean.getRoleName());

                } else {
                    bean.setChecked(true);
                    roleIdList.add(bean.getRoleId());
//                    roleNameList.add(bean.getRoleName());
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 角色选择的adapter
     */
    class AolltRoleAdapter extends BaseQuickAdapter<RoleBean, BaseViewHolder> {


        public AolltRoleAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, RoleBean item) {

            if (item.isChecked()) {
                ((ImageView) helper.getView(R.id.iv_checked)).setImageDrawable(getResources().getDrawable(R.drawable.ic_checked));
            } else {
                ((ImageView) helper.getView(R.id.iv_checked)).setImageDrawable(getResources().getDrawable(R.drawable.ic_no_checked));
            }

            helper.setText(R.id.tv_role, item.getRoleName());
        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == ROLE_FLAG) {
//                roleIdList.clear();
//                roleNameList.clear();
//
//                roleIdList = data.getStringArrayListExtra("roleIdList");
//                roleNameList = data.getStringArrayListExtra("roleNameList");
//
//                StringBuffer stringBuffer = new StringBuffer();
//                for (String s : roleNameList) {
//                    stringBuffer.append(s + ",");
//                }
//                tvRole.setText(stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1));
//
//
//            }
//        }
//    }

}
