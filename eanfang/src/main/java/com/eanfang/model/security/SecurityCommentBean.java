package com.eanfang.model.security;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/3/4
 * @description 安防圈评论Bean
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecurityCommentBean implements Serializable {

    private String type;
    private String status;
    private String commentsContent;
    private String commentsImg;
    private Long asId;
    private Long commentsAnswerId;
    private Long commentsCompanyId;
    private Long commentsTopCompanyId;
    private Long commentsAnswerAccId;
    private Integer topCommentsId;
    private Integer parentCommentsId;

}
