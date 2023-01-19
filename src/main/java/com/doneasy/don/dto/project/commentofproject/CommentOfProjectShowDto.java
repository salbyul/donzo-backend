package com.doneasy.don.dto.project.commentofproject;

import lombok.Data;

@Data
public class CommentOfProjectShowDto {

    private Long id;
    private Long memberId;
    private String member_id;
    private String content;
    private String created_date;
}
