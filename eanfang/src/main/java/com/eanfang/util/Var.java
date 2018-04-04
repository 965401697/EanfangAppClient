package com.eanfang.util;

import java.util.HashMap;
import java.util.Map;

public class Var {

    private ChangeListener changeListener;
    private int var = 0;
    private static Map<String, Var> varInstanceMap = new HashMap<>();

    private Var() {
    }

    public static Var get(String key) {
        if (!varInstanceMap.containsKey(key)) {
            synchronized (Var.class) {
                if (!varInstanceMap.containsKey(key)) {
                    varInstanceMap.put(key, new Var());
                }
            }
        }
        return varInstanceMap.get(key);
    }

    public static void remove(String key) {
        if (varInstanceMap.containsKey(key)) {
            varInstanceMap.remove(key);
        }
    }

    public void setChangeListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public int getVar() {
        return var;
    }

    public void setVar(int var) {
        this.var = var;
        if (null != changeListener) {
            changeListener.onSuccess(var);
        }
    }

    public interface ChangeListener {
        void onSuccess(Integer var);
    }
}


