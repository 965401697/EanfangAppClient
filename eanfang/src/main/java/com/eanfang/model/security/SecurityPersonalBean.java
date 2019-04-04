package com.eanfang.model.security;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/3/20
 * @description 个人中心
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityPersonalBean implements Serializable {

    private int spccount;
    private int likeCount;
    private PageUtilBean pageUtil;
    private int followerCount;
    private int asFollowerCount;
    private int noReadCount;
    private int commentNoRead;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageUtilBean {

        private List<SecurityListBean.ListBean> list;
    }
}
