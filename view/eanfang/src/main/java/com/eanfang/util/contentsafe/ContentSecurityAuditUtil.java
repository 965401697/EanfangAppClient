package com.eanfang.util.contentsafe;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.base.BaseApplication;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.ContentSecruityBean;
import com.eanfang.model.ContentSecruityTokenBean;
import com.eanfang.util.StringUtils;
import com.okgo.callback.StringCallback;
import com.okgo.model.Response;

import java.util.List;

import cn.hutool.core.util.StrUtil;

/**
 * @author liangkailun
 * Date ：2019/5/6
 * Describe :内容安全审核工具类
 */
public class ContentSecurityAuditUtil {
    private static ContentSecurityAuditUtil mAuditUtil = null;
    private static final String TOKEN_URL = "https://aip.baidubce.com/oauth/2.0/token";
    private static final String SPAN_URL = "https://aip.baidubce.com/rest/2.0/antispam/v2/spam";
    private static final String GRANT_TYPE_KEY = "grant_type";
    private static final String GRANT_TYPE_VALUE = "client_credentials";
    private static final String CLIENT_ID_KEY = "client_id";
    private static final String CLIENT_ID_VALUE = "tMfdOYktLiU3BFzY9wZvcsUZ";
    private static final String CLIENT_SECRET_KEY = "client_secret";
    private static final String CLIENT_SECRET_VALUE = "4OggNKmyEKAGcHXvXVXYZ8dcFUcTmrth";
    private static final String ACCESS_TOKEN_KEY = "access_token";
    private static final String CONTENT_KEY = "content";
    private static final String SP_SAVE_TIME_KEY = "save_token_time";
    private static final String SP_SAVE_TOKEN_KEY = "save_token";
    private ContentAuditingListener mListener;

    public static ContentSecurityAuditUtil getInstance() {
        if (mAuditUtil == null) {
            synchronized (ContentSecurityAuditUtil.class) {
                if (mAuditUtil == null) {
                    mAuditUtil = new ContentSecurityAuditUtil();
                }
            }
        }
        return mAuditUtil;
    }

    /**
     * 获取token
     *
     * @param content 内容
     */
    private void getToken(String content) {
        EanfangHttp.post(TOKEN_URL)
                .params(GRANT_TYPE_KEY, GRANT_TYPE_VALUE)
                .params(CLIENT_ID_KEY, CLIENT_ID_VALUE)
                .params(CLIENT_SECRET_KEY, CLIENT_SECRET_VALUE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        ContentSecruityTokenBean bean = JSONObject.toJavaObject
                                (JSONObject.parseObject(body), ContentSecruityTokenBean.class);
                        spamContent(bean.getAccess_token(), content);
                        BaseApplication.get().set(SP_SAVE_TIME_KEY, System.currentTimeMillis() / 1000 + bean.getExpires_in());
                        BaseApplication.get().set(SP_SAVE_TOKEN_KEY, bean.getAccess_token());
                        Log.d("ContentUtil", "onSuccess: body:" + body);
                    }
                });
    }

    /**
     * 审核内容
     *
     * @param token   token
     * @param content 审核文字
     */
    private void spamContent(String token, String content) {
        if (StrUtil.isEmpty(content)) {
            mListener.auditingSuccess();
            return;
        }
        EanfangHttp.post(SPAN_URL)
                .params(ACCESS_TOKEN_KEY, token)
                .params(CONTENT_KEY, content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        ContentSecruityBean bean = JSONObject.toJavaObject
                                (JSONObject.parseObject(body), ContentSecruityBean.class);
                        if (bean.getResult().getSpam() == 1) {
                            List<ContentSecruityBean.ResultBean.AuditBean> auditBeans = bean.getResult().getReject();

//                            for (ContentSecruityBean.ResultBean.AuditBean auditBean : auditBeans) {
//                                if (auditBean.getHit() != null && auditBean.getHit().size() > 0) {
                            if (mListener != null) {
                                mListener.auditingFail(content);
                            }
//                                }
//                            }
                        } else {
                            if (mListener != null) {
                                mListener.auditingSuccess();
                            }
                        }
                        Log.d("ContentUtil", "onSuccess: body:" + body);
                    }
                });
    }


    /**
     * 对外暴露的审核方法
     *
     * @param content  内容
     * @param listener 内容审核回调
     */
    public void toAuditing(String content, ContentAuditingListener listener) {
        this.mListener = listener;
        long currentTime = System.currentTimeMillis() / 1000;
        long saveTime = CacheKit.get().getLong(SP_SAVE_TIME_KEY, 0L);
        String saveToken = CacheKit.get().getStr(SP_SAVE_TOKEN_KEY);
        if (saveTime > currentTime && !StringUtils.isEmpty(saveToken)) {
            spamContent(saveToken, content);
        } else {
            getToken(content);
        }
    }

}
