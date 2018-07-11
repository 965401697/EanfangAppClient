package com.eanfang.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 描述：报修统计Bean
 *
 * @author Guanluocang
 * @date on 2018/7/10$  18:33$
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataStatisticsBean implements Serializable {

    private int value;
    private String name;
}
