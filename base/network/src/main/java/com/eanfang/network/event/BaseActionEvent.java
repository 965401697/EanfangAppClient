package com.eanfang.network.event;

import com.eanfang.network.event.base.BaseEvent;

import lombok.Getter;
import lombok.Setter;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
@Getter
@Setter
public class BaseActionEvent extends BaseEvent {

    public static final int DEFAULT = 0;

    public static final int SHOW_LOADING_DIALOG = 1;

    public static final int DISMISS_LOADING_DIALOG = 2;

    public static final int SHOW_TOAST = 3;

    public static final int FINISH = 4;

    public static final int FINISH_WITH_RESULT_OK = 5;

    private String message;

    public BaseActionEvent() {
        super(DEFAULT);
    }

    public BaseActionEvent(int action) {
        super(action);
    }
}