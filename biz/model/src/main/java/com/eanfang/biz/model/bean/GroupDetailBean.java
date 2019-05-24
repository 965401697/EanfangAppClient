package com.eanfang.biz.model.bean;

import com.eanfang.biz.model.entity.SysGroupEntity;
import com.eanfang.biz.model.entity.SysGroupUserEntity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 群组详情
 */
@Getter
@Setter
@Accessors(chain = true)
public class GroupDetailBean {
    private SysGroupEntity group;
    private List<SysGroupUserEntity> list;
}
