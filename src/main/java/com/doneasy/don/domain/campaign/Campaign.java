package com.doneasy.don.domain.campaign;

import java.time.LocalDateTime;

public class Campaign {

    private Long id;
    private String how_to_use_first;
    private String how_to_use_second;
    private String how_to_use_third;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private Integer goal;
    private String word;
    private CampaignStatus status;
    private LocalDateTime created_date;
    private LocalDateTime modified_date;
    private Long organization_id;
}
