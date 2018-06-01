package com.eanfang.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 描述：
 *
 * @author Guanluocang
 * @date on 2018/5/30$  16:13$
 */
@Getter
@Setter
public class RepairOpenAreaBean implements Serializable{

    private String baseDataId;
    private String createTime;
    private String createUserId;
    private int doorFee;
    private int id;
    private int status;

}
