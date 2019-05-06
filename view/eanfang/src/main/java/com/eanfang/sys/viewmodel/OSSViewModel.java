package com.eanfang.sys.viewmodel;

import android.app.Activity;

import androidx.lifecycle.LifecycleOwner;

import com.eanfang.BuildConfig;
import com.eanfang.kit.bean.OSSBean;
import com.eanfang.kit.sdk.SDKManager;
import com.eanfang.kit.sdk.alisdk.alioss.OSSCallBack;
import com.eanfang.rds.base.BaseViewModel;
import com.eanfang.rds.sys.ds.OSSDs;
import com.eanfang.rds.sys.repo.OSSRepo;

public class OSSViewModel extends BaseViewModel {
    private OSSRepo ossRepo;
    public OSSViewModel() {
        ossRepo=new OSSRepo(new OSSDs(this));
    }
    public void getToken(Activity activity, String imgKey, String compressPath, OSSCallBack callback){
        ossRepo.getToken().observe( lifecycleOwner,(OSSBean bean) ->
             SDKManager.getIOSS().initOSS(activity,bean, BuildConfig.OSS_ENDPOINT,BuildConfig.OSS_BUCKET).asyncPutImage(imgKey, compressPath, callback)
        );
    }
}
