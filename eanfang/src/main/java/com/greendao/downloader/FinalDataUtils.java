package com.greendao.downloader;

import android.content.Context;
import android.util.Log;

import com.eanfang.application.EanfangApplication;
import com.yaf.base.entity.FinalDataEntity;

import java.util.List;

/**
 * Created by 0 u r on 2018/4/3.
 */

public class FinalDataUtils {
    private static final String TAG = "FinalDataUtils";
    private static FinalDataUtils finalDataUtils;
    private DaoManager mManager = EanfangApplication.getDaoManager();

    public static FinalDataUtils getInstance() {
        if (finalDataUtils == null) {
            finalDataUtils = new FinalDataUtils();
        }
        return finalDataUtils;
    }


    /**
     * 完成finalData记录的插入，如果表未创建，先创建finalData表
     *
     * @param
     * @return
     */
    public boolean insertFinalData(FinalDataEntity finalData) {
        boolean flag = false;
        flag = mManager.getDaoSession().getFinalDataEntityDao().insert(finalData) == -1 ? false : true;
        Log.i(TAG, "insert finalData :" + flag + "-->" + finalData.toString());
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     *
     * @param finalDataList
     * @return
     */
    public boolean insertMultFinalData(final List<FinalDataEntity> finalDataList) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (FinalDataEntity finalData : finalDataList) {
                        mManager.getDaoSession().insertOrReplace(finalData);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 删除所有记录
     *
     * @return
     */
    public boolean deleteAll() {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(FinalDataEntity.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     *
     * @return
     */
    public List<FinalDataEntity> queryAllFinalData() {
        return mManager.getDaoSession().loadAll(FinalDataEntity.class);
    }


    /**
     * 使用native sql进行查询操作
     */
    public List<FinalDataEntity> queryFinalDataByNativeSql(String sql, String[] conditions) {
        return mManager.getDaoSession().queryRaw(FinalDataEntity.class, sql, conditions);
    }
}
