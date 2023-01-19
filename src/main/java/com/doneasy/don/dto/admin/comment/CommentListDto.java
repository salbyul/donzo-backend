package com.doneasy.don.dto.admin.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentListDto {

    private Long id;
    private String contents;
    private Long reports;
    private String createdDate;
    private String status;
}
