package com.eanfang.model;

import com.yaf.base.entity.ProjectEntity;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by O u r on 2018/9/26.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectListBean implements Serializable {
    private List<ProjectEntity> list;
}
