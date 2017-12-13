package net.eanfang.client.ui.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by MrHou
 *
 * @on 2017/12/11  17:36
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class ConstAllBean implements Serializable {
    private Map<String, List<String>> Const;

    public Map<String, List<String>> getConst() {
        return Const;
    }

    public void setConst(Map<String, List<String>> aConst) {
        Const = aConst;
    }
}

