package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FriendListBean;
import com.eanfang.util.Cn2Spell;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.ToastUtil;
import com.eanfang.witget.SideBar;
import com.yaf.sys.entity.AccountEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchStaffActivity extends BaseWorkerActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.side_bar)
    SideBar sideBar;
    private AddStaffAdapter staffAdapter;

    private List<FriendListBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_staff2);
        ButterKnife.bind(this);
        setTitle("添加员工");
        setLeftBack();
        startTransaction(true);

        mList = getPhoneContacts();
        initViews();

        PermissionUtils.get(this).getStorageAndLocationPermission(() -> {
            initData();
        });
    }

    private void initViews() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        staffAdapter = new AddStaffAdapter(R.layout.item_add_comtact, 1);
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
                        ToastUtil.get().showToast(SearchStaffActivity.this, friendListBean.getRealName() + "没有注册易安防");
                    } else {
                        Intent intent = new Intent(SearchStaffActivity.this, AddStaffNextActivity.class);
                        intent.putExtra("bean", friendListBean);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void initData() {
        List<String> phoneList = new ArrayList<>();
        if (mList.size() > 0) {
            for (FriendListBean bean : mList)
                phoneList.add(bean.getMobile());
        }

        EanfangHttp.post(UserApi.GET_MOBILE_INFO)
                .upJson(JSON.toJSONString(phoneList))
                .execute(new EanfangCallback<AccountEntity>(this, true, AccountEntity.class, true, (list) -> {
                    if (list.size() > 0) {
                        for (FriendListBean bean : mList) {
                            for (AccountEntity accountEntity : list) {
                                if (bean.getMobile().equals(accountEntity.getMobile())) {
                                    bean.setNickName(accountEntity.getNickName());
                                    bean.setAvatar(accountEntity.getAvatar());
                                    bean.setAccId(String.valueOf(accountEntity.getAccId()));
                                    bean.setAreaCode(accountEntity.getAreaCode());
                                    bean.setAddress(accountEntity.getAddress());
                                    break;
                                }
                            }
                        }

                    }

                    if (mList.size() > 0) {

                        for (FriendListBean bean : mList) {
                            // 根据姓名获取拼音
                            bean.setPinyin(bean.getRealName());
                            bean.setFirstLetter(Cn2Spell.getPinYin(bean.getRealName()).substring(0, 1).toUpperCase()); // 获取拼音首字母并转成大写
                            if (!Cn2Spell.getPinYin(bean.getRealName()).substring(0, 1).toUpperCase().matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
                                bean.setFirstLetter("#");
                            }
                        }

                        List<FriendListBean> friendListBeanList = mList;

                        Collections.sort(friendListBeanList, new FriendListBean());
                    }

                    staffAdapter.setNewData(mList);
                }));
    }


    private List<FriendListBean> getPhoneContacts() {
        // 获取联系人数据
        ContentResolver cr = getContentResolver();
        //获取所有电话信息（而不是联系人信息），这样方便展示
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,// 姓名
                ContactsContract.CommonDataKinds.Phone.NUMBER,// 电话号码
        };
        Cursor cursor = cr.query(uri, projection, null, null, null);
        if (cursor == null) {
            return null;
        }
        //最终要返回的数据
        List<FriendListBean> result = new ArrayList<FriendListBean>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String number = cursor.getString(1);
            //保存到对象里
            FriendListBean info = new FriendListBean();
            info.setRealName(name);
            info.setMobile(number);
            //保存到集合里
            result.add(info);
        }
        //用完记得关闭
        cursor.close();
        return result;
    }

    @OnClick({R.id.ll_search, R.id.ll_my_friends})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_search:
                startActivity(new Intent(this, AddStaffActivity.class));
                break;
            case R.id.ll_my_friends:
                startActivity(new Intent(this, AddStaffFriendActivity.class));
                break;
        }
    }
}
