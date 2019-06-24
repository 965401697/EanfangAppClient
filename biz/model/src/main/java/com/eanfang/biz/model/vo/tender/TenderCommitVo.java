package com.eanfang.biz.model.vo.tender;

import androidx.databinding.ObservableField;

import com.eanfang.biz.model.vo.BaseVo;

import java.io.Serializable;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/6/24
 * @description 我要报价 vo
 */
@Getter()
@Accessors(chain = true)
public class TenderCommitVo extends BaseVo implements Serializable {

    /**
     * id
     */
    private ObservableField<String> shopTaskPublishId = new ObservableField<>();
    /**
     * 姓名
     */
    private ObservableField<String> applyContacts = new ObservableField<>();
    /**
     * 电话
     */
    private ObservableField<String> applyConstactsPhone = new ObservableField<>();
    /**
     * 公司名称
     */
    private ObservableField<String> applyCompanyName = new ObservableField<>();
    /**
     * 预算
     */
    private ObservableField<String> projectQuote = new ObservableField<>();
    /**
     * 预算单位
     */
    private ObservableField<String> budgetUnit = new ObservableField<>();
    /**
     * 描述
     */
    private ObservableField<String> description = new ObservableField<>();
    /**
     * 图片
     */
    private ObservableField<String> pictures = new ObservableField<>();

}
