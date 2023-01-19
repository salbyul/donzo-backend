package com.doneasy.don.domain.project;

import com.doneasy.don.dto.project.commentofproject.CommentOfProjectSaveDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentOfProject {

    private Long id;
    private CommentOfProjectStatus status;
    private String content;
    private LocalDateTime created_date;
    private Long member_id;
    private Long project_id;

    public static CommentOfProject getInstance(CommentOfProjectSaveDto commentOfProjectSaveDto, Long memberId) {
        return new CommentOfProject(null, null, commentOfProjectSaveDto.getContent(), null, memberId, commentOfProjectSaveDto.getProjectId());
    }
}
