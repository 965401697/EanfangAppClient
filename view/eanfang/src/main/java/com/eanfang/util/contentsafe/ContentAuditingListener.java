package com.eanfang.util.contentsafe;

/**
 * @author liangkailun
 * Date ：2019/5/6
 * Describe :
 */
public interface ContentAuditingListener {
    void auditingSuccess();

    void auditingFail(String failContent);
}
