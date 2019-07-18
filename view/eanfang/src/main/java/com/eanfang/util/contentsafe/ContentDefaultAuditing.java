package com.eanfang.util.contentsafe;

import android.content.Context;

import com.eanfang.R;
import com.eanfang.base.network.holder.ToastHolder;
import com.eanfang.util.ToastUtil;

/**
 * @author liangkailun
 * Date ：2019/5/6
 * Describe :
 */
public abstract class ContentDefaultAuditing implements ContentAuditingListener {
    private final Context mContext;

    public ContentDefaultAuditing(Context context) {
        this.mContext = context;
    }

    @Override
    public abstract void auditingSuccess();

    @Override
    public void auditingFail(String failContent) {
        ToastHolder.showToast("内容包含关键词，换一个试试吧");
//        ToastUtil.get().showToast(mContext, mContext.getString(R.string.text_content_audit_fail, failContent));
    }
}
