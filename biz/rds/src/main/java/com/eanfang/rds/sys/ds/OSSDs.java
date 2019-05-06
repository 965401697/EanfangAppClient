package com.eanfang.rds.sys.ds;

import com.eanfang.kit.bean.OSSBean;
import com.eanfang.network.callback.RequestCallback;
import com.eanfang.rds.base.BaseRemoteDataSource;
import com.eanfang.rds.base.BaseViewModel;
import com.eanfang.rds.sys.api.IOSSApi;

public class OSSDs extends BaseRemoteDataSource  implements IOSSDs{

    public OSSDs(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void getToken(RequestCallback<OSSBean> callback) {
        execute(getService(IOSSApi.class).getToken(),callback);
    }
}
