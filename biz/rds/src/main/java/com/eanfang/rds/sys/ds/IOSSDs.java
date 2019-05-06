package com.eanfang.rds.sys.ds;

import com.eanfang.kit.bean.OSSBean;
import com.eanfang.network.callback.RequestCallback;

public interface IOSSDs {
    void getToken( RequestCallback<OSSBean> callback);
}
