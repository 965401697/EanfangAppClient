package com.eanfang.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/1/11
 * @description 招标信息
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TenderBean implements Serializable {

    private List<IfbOrderEntity> list;


    public List<IfbOrderEntity> getList() {
        return list;
    }

    public void setList(List<IfbOrderEntity> list) {
        this.list = list;
    }

}
