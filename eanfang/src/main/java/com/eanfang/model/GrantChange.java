package com.eanfang.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MrHou
 *
 * @on 2018/1/26  15:01
 * @email houzhongzhou@yeah.net
 * @desc
 */

public final class GrantChange {
    @Getter
    @Setter
    private List<Integer> addIds;

    @Getter
    @Setter
    private List<Integer> delIds;
}