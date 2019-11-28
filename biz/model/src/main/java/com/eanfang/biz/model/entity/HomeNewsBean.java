package com.eanfang.biz.model.entity;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/11/27
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class HomeNewsBean implements Serializable {
    private List<String> list;
}
