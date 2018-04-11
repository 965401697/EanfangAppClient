package com.yaf.base.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by O u r on 2018/4/8.
 */
@Entity
public class FinalDataEntity {
    @NotNull
    private String fileName;
    @NotNull
    private String List;
    @NotNull
    private int index;

    private String name;

    @Generated(hash = 256728454)
    public FinalDataEntity(@NotNull String fileName, @NotNull String List,
            int index, String name) {
        this.fileName = fileName;
        this.List = List;
        this.index = index;
        this.name = name;
    }

    @Generated(hash = 2053552509)
    public FinalDataEntity() {
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getList() {
        return this.List;
    }

    public void setList(String List) {
        this.List = List;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
