package com.doneasy.don.dto.project.projectlist;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectListShowDto {

    private Long project_id;
    private String project_title;

    // 기부금액 + 댓글 카운트 + 응원카운트
    private int present_price;
    private int target_price;
    private String organization_nickname;
    private byte[] project_image;
    private String project_enddate;
    private String project_status;
}
