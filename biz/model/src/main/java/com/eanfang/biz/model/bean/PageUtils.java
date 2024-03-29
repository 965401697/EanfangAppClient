package com.eanfang.biz.model.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 *
 * @author 29698868
 * @email 29698868@qq.com
 * @date 2017年11月4日 下午12:59:00
 */
public class PageUtils<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    //private static SerializeConfig config;
    static {
        //FastJsonConfig fjc = new FastJsonConfig();
        //1、序列化重点
        //指定date类型自动格式化
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm";
        //fjc.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);//禁止循环引用解决$ref输出引用
        //fjc.setSerializerFeatures(SerializerFeature.BrowserCompatible);//解决超大Long到浏览器无法处理
        //fjc.setSerializerFeatures(SerializerFeature.WriteDateUseDateFormat);//解决日期输出到客户端为数字

        //config=fjc.getSerializeConfig();

    }

    //总记录数
    private long totalCount;
    //每页记录数
    private int pageSize;
    //总页数
    private int totalPage;
    //当前页数
    private int currPage;
    //列表数据
    private List<T> list;

    public PageUtils() {
    }

    /**
     * 分页
     *
     * @param list       列表数据
     * @param totalCount 总记录数
     * @param pageSize   每页记录数
     * @param currPage   当前页数
     */
    public PageUtils(List<T> list, long totalCount, int pageSize, int currPage) {


        //this.list = JSONObject.toJSON(list, config);
        //String s=JSONObject.toJSONString(list);
        //System.out.println(s);
        //Object o=JSONObject.parse(s);
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
