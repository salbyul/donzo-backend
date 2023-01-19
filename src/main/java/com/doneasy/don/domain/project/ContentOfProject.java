package com.doneasy.don.domain.project;

import com.doneasy.don.dto.project.contentofproject.ContentOfProjectSaveDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ContentOfProject {

    private Long id;
    private String subtitle;
    private String content;
    private String image_name;
    private Integer order_num;
    private String created_date;
    private Long project_id;

    public static ContentOfProject getContentOfProject(ContentOfProjectSaveDto contentOfProjectSaveDto, int index, String image_name, Long project_id) {
        return new ContentOfProject(null, contentOfProjectSaveDto.getSubtitle(), contentOfProjectSaveDto.getContents(), image_name, index, null, project_id);

    }
}
