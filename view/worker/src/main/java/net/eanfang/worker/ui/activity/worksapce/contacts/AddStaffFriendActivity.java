package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FriendListBean;
import com.eanfang.util.Cn2Spell;
import com.eanfang.util.ToastUtil;
import com.eanfang.witget.SideBar;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddStaffFriendActivity extends BaseWorkerActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.side_bar)
    SideBar sideBar;
    private AddStaffAdapter staffAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff_friend);
        ButterKnife.bind(this);
        setTitle("添加员工");
        setLeftBack();
        startTransaction(true);

        initData();
        initViews();
    }

    private void initViews() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        staffAdapter = new AddStaffAdapter(R.layout.item_add_comtact, 2);
        staffAdapter.bindToRecyclerView(recyclerView);

        sideBar.setOnStrSelectCallBack(new SideBar.ISideBarSelectCallBack() {
            @Override
            public void onSelectStr(int index, String selectStr) {
                for (int i = 0; i < staffAdapter.getData().size(); i++) {
                    if (selectStr.equalsIgnoreCase(staffAdapter.getData().get(i).getFirstLetter())) {
                        recyclerView.scrollToPosition(i); // 选择到首字母出现的位置
                        return;
                    }
                }
            }
        });

        staffAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                FriendListBean friendListBean = (FriendListBean) adapter.getData().get(position);
                if (view.getId() == R.id.tv_add) {
                    if (TextUtils.isEmpty(friendListBean.getAccId())) {
                        ToastUtil.get().showToast(AddStaffFriendActivity.this, friendListBean.getRealName() + "没有注册易安防");
                    } else {
                        Intent intent = new Intent(AddStaffFriendActivity.this, AddStaffNextActivity.class);
                        intent.putExtra("bean", friendListBean);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void initData() {

        EanfangHttp.post(UserApi.POST_FRIENDS_LIST)
                .params("accId", WorkerApplication.get().getAccId())
                .execute(new EanfangCallback<FriendListBean>(this, true, FriendListBean.class, true, (list) -> {
                    if (list.size() > 0) {

                        for (FriendListBean bean : list) {
                            // 根据姓名获取拼音
                            bean.setPinyin(bean.getNickName());
                            bean.setFirstLetter(Cn2Spell.getPinYin(bean.getNickName()).substring(0, 1).toUpperCase()); // 获取拼音首字母并转成大写
                            if (!Cn2Spell.getPinYin(bean.getNickName()).substring(0, 1).toUpperCase().matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
                                bean.setFirstLetter("#");
                            }
                        }

                        List<FriendListBean> friendListBeanList = list;

                        Collections.sort(friendListBeanList, new FriendListBean());

                        staffAdapter.setNewData(list);
                    }
                }));
    }
}
