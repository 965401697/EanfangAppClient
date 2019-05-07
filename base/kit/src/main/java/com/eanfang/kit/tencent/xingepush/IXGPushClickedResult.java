package com.eanfang.kit.tencent.xingepush;

import lombok.Getter;
import lombok.Setter;

/**
 * created by wtt
 * at 2019/4/3
 * summary:
 */
@Setter
@Getter
public class IXGPushClickedResult {
    private long msgId = 0L;
    private String title = "";
    private String content = "";
    private String customContent = "";
    private String activityName = "";
    private int actionType = 0;
    private int notificationActionType = 1;
    private int pushChannel = 0;

}
