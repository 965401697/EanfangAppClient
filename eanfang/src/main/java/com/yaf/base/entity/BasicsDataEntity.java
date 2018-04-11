package com.yaf.base.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by O u r on 2018/4/8.
 */
@Entity
public class BasicsDataEntity {

    //
    private String path;

    @Generated(hash = 2002863742)
    public BasicsDataEntity(String path) {
        this.path = path;
    }

    @Generated(hash = 2120521529)
    public BasicsDataEntity() {
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
