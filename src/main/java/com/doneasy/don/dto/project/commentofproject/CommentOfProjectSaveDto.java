package com.doneasy.don.dto.project.commentofproject;

import lombok.Data;

@Data
public class CommentOfProjectSaveDto {

    private String content;
    private Long projectId;
}
