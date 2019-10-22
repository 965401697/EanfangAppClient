package com.eanfang.biz.model.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by O u r on 2018/4/17.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupCreatBean implements Serializable {

    /**
     * createTime : 2018-04-17 17:05:58
     * createUser : 984379128553951234
     * groupId : 15
     * groupName : 测试一下
     * rcloudGroupId : 3b85b041f29c4e5da67e4ca0521ab568
     */

    private String createTime;
    private String createUser;
    private int groupId;
    private String groupName;
    private String rcloudGroupId;
    private String headPortrait;

}
