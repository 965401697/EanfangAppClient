package net.eanfang.client.ui.activity.worksapce.contacts;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.FriendListBean;
import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.AddStaffAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddStaffActivity extends BaseClientActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;

    private AddStaffAdapter mAddStaffAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);
        ButterKnife.bind(this);
        setTitle("添加员工");
        setLeftBack();
        initViews();
        startTransaction(true);

        setRightTitle("添加");
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAddStaffAdapter = new AddStaffAdapter(R.layout.item_add_staff);
        mAddStaffAdapter.bindToRecyclerView(recyclerView);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 11) {

                    initPhoneData(etSearch.getText().toString().trim());
                }
            }
        });
    }


    public void add() {
        if (mAddStaffAdapter.getData().size() == 0) {
            ToastUtil.get().showToast(AddStaffActivity.this, "请先搜索员工");
            return;
        }

        if (mAddStaffAdapter.getData().get(0).getAccId().equals(String.valueOf(EanfangApplication.getApplication().getAccId()))) {
            ToastUtil.get().showToast(AddStaffActivity.this, "自己不能添加自己");
            return;
        }

        Intent intent = new Intent(AddStaffActivity.this, AddStaffNextActivity.class);
        intent.putExtra("bean", mAddStaffAdapter.getData().get(0));
        startActivity(intent);
    }

    /**
     * 根据手机号查找用户
     *
     * @param
     */
    private void initPhoneData(String phone) {
        EanfangHttp.post(UserApi.POST_FIND_FRIEND)
                .params("mobile", phone)
//                            .params("email", etPhone.getText().toString().trim())//邮箱也可以加的
                .execute(new EanfangCallback<FriendListBean>(AddStaffActivity.this, true, FriendListBean.class, true, (list) -> {
                    if (list.size() > 0) {
                        llSearch.setVisibility(View.VISIBLE);
                        mAddStaffAdapter.setNewData(list);
                    } else {
                        mAddStaffAdapter.getData().clear();
                        llSearch.setVisibility(View.INVISIBLE);
                        ToastUtil.get().showToast(AddStaffActivity.this, "没有搜索到好友");
                    }
                }));
    }


    private String[] getPhoneContacts(Uri uri) {
        String[] contact = new String[2];
        //得到ContentResolver对象**
        ContentResolver cr = getContentResolver();
        //取得电话本中开始一项的光标**
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            //取得联系人姓名**
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            contact[0] = cursor.getString(nameFieldColumnIndex);
            //取得电话号码**
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
            if (phone != null) {
                phone.moveToFirst();
                contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            phone.close();
            cursor.close();
        } else {
            return null;
        }
        return contact;
    }
}
