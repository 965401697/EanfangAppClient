package com.eanfang.model.security;

import java.io.Serializable;
import java.util.List;

/**
 * @author guanluocang
 * @data 2019/3/20
 * @description 个人中心
 */
public class SecurityPersonalBean implements Serializable {

    private int spccount;
    private int likeCount;
    private PageUtilBean pageUtil;
    private int followerCount;
    private int asFollowerCount;

    public SecurityPersonalBean(int spccount, int likeCount, PageUtilBean pageUtil, int followerCount, int asFollowerCount) {
        this.spccount = spccount;
        this.likeCount = likeCount;
        this.pageUtil = pageUtil;
        this.followerCount = followerCount;
        this.asFollowerCount = asFollowerCount;
    }

    public SecurityPersonalBean() {
    }

    public int getSpccount() {
        return this.spccount;
    }

    public int getLikeCount() {
        return this.likeCount;
    }

    public PageUtilBean getPageUtil() {
        return this.pageUtil;
    }

    public int getFollowerCount() {
        return this.followerCount;
    }

    public int getAsFollowerCount() {
        return this.asFollowerCount;
    }

    public void setSpccount(int spccount) {
        this.spccount = spccount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void setPageUtil(PageUtilBean pageUtil) {
        this.pageUtil = pageUtil;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public void setAsFollowerCount(int asFollowerCount) {
        this.asFollowerCount = asFollowerCount;
    }

    public static class PageUtilBean {

        private List<SecurityListBean.ListBean> list;

        public PageUtilBean(List<SecurityListBean.ListBean> list) {
            this.list = list;
        }

        public PageUtilBean() {
        }

        public List<SecurityListBean.ListBean> getList() {
            return this.list;
        }

        public void setList(List<SecurityListBean.ListBean> list) {
            this.list = list;
        }
    }
}
