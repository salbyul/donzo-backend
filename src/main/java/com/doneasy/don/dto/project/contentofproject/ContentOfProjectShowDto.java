package com.doneasy.don.dto.project.contentofproject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContentOfProjectShowDto {

    private Long id;
    private String subtitle;
    private String content;
    private byte[] image;
    private int order_num;
}
