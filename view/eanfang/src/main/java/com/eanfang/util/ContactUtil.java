package com.eanfang.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;

import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.BaseApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.AccountMailBean;

import org.json.JSONObject;

import java.util.ArrayList;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author liangkailun
 * Date ：2019/5/7
 * Describe :获取用户通讯录信息
 */
public class ContactUtil {
    private static final String TAG = "ContactUtil";
    private static final String ADDRESS_LIST_SAVE_TIME = "address_list_save_time";
    private static final int POST_TIME = 15 * 24 * 60 * 60;

    /**
     * 上传联系人列表到服务器
     */
    public static void postAccount(Activity context) {
        Observable.create((ObservableOnSubscribe<Long>) emitter -> {
            String str = BaseApplication.get().get(ADDRESS_LIST_SAVE_TIME, 0);
            long saveTime = NumberUtil.parseLong(str, 0);
            if (DateUtil.currentSeconds() - saveTime > POST_TIME) {
                EanfangHttp.post(NewApiService.ACCOUNT_POST)
                        .upJson(JSON.toJSONString(getAllContacts(context)))
                        .execute(new EanfangCallback(context, false, JSONObject.class, bean -> {
                            emitter.onNext(DateUtil.currentSeconds());
                            emitter.onComplete();
                        }));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((saveTime) -> {
                    BaseApplication.get().set(ADDRESS_LIST_SAVE_TIME, saveTime);
                });
//        ThreadPoolManager threadPoolManager = ThreadPoolManager.newInstance();
//        threadPoolManager.addExecuteTask(() -> {
//            long saveTime = Long.valueOf(BaseApplication.get().get(ADDRESS_LIST_SAVE_TIME, 0));
//            if (System.currentTimeMillis() - saveTime > POST_TIME) {
//                EanfangHttp.post(NewApiService.ACCOUNT_POST)
//                        .upJson(JSON.toJSONString(getAllContacts(context)))
//                        .execute(new EanfangCallback(context, false, JSONObject.class, bean -> {
//                            BaseApplication.get().set(ADDRESS_LIST_SAVE_TIME, System.currentTimeMillis());
//                        }));
//            }
//        });
    }

    /**
     * 获取联系人信息
     *
     * @param context
     * @return
     */
    private static AccountMailBean getAllContacts(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        AccountMailBean mailBean = new AccountMailBean();
        AccountMailBean.AccountMailListBean listBean = new AccountMailBean.AccountMailListBean();
        AccountMailBean.AccountEntityBean bean = new AccountMailBean.AccountEntityBean();
        ArrayList<AccountMailBean.AccountMailListBean.EntityListBean> contacts = new ArrayList();
        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                //新建一个联系人实例
                AccountMailBean.AccountMailListBean.EntityListBean temp = new AccountMailBean.AccountMailListBean.EntityListBean();
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                temp.setUserName(name);
                //获取联系人所有电话号
                Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                if (phones != null) {
                    while (phones.moveToNext()) {
                        String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        temp.setMobile(phoneNumber);
                    }
                    phones.close();
                }
                //获取联系人所有邮箱.
                Cursor emails = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);
                if (emails != null) {
                    while (emails.moveToNext()) {
                        String email = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        temp.setEmail(email);
                    }
                    emails.close();
                }
                //查询==地址==类型的数据操作.StructuredPostal.TYPE_WORK
                Cursor address = context.getContentResolver().query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " = " + contactId,
                        null, null);
                if (address != null) {
                    while (address.moveToNext()) {
                        String workAddress = address.getString(address.getColumnIndex(
                                ContactsContract.CommonDataKinds.StructuredPostal.DATA));
                        //添加地址的信息
                        temp.setWorkUnit(workAddress);
                    }
                    address.close();
                }
                contacts.add(temp);
            }
            cursor.close();
        }
        listBean.setEntityList(contacts);
        mailBean.setAccountEntity(bean);
        mailBean.setAccountMailList(listBean);
        return mailBean;
    }

}


