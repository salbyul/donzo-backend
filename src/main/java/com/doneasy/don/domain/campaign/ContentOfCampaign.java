package com.doneasy.don.domain.campaign;

import java.time.LocalDateTime;

public class ContentOfCampaign {

    private Long id;
    private String subtitle;
    private String contents;
    private String image_name;
    private Integer order;
    private ContentOfCampaignStatus status;
    private LocalDateTime created_date;
    private LocalDateTime modified_date;
    private Long campaign_id;
}
