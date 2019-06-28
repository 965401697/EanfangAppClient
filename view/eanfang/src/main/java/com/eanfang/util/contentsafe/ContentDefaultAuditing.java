package com.eanfang.util.contentsafe;

import android.content.Context;

import com.eanfang.R;
import com.eanfang.util.ToastUtil;

/**
 * @author liangkailun
 * Date ：2019/5/6
 * Describe :
 */
public abstract class ContentDefaultAuditing implements ContentAuditingListener {
    private final Context mContext;

    public ContentDefaultAuditing(Context context){
        this.mContext = context;
    }
    @Override
    public abstract void auditingSuccess();

    @Override
    public void auditingFail(String failContent) {
        ToastUtil.get().showToast(mContext, mContext.getString(R.string.text_content_audit_fail, failContent));
    }
}
