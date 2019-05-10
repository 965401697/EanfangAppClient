package com.eanfang.rds.base;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class LViewModelProviders<B> {

    public static <T extends BaseViewModel> T of(@NonNull FragmentActivity activity, Class<T> modelClass) {
        T t = ViewModelProviders.of(activity).get(modelClass);
        t.setLifecycleOwner(activity);
        return t;
    }

    public static <T extends BaseViewModel> T of(@NonNull Fragment activity, Class<T> modelClass) {
        T t = ViewModelProviders.of(activity).get(modelClass);
        t.setLifecycleOwner(activity);
        return t;
    }

}