package com.eanfang.rds.base;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class BaseRepo<T> {

    protected T remoteDataSource;

    public BaseRepo(T remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

}