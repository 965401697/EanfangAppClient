package com.eanfang.network.event.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
@Getter
@Setter
@AllArgsConstructor
public class BaseEvent {
    private int action;
}