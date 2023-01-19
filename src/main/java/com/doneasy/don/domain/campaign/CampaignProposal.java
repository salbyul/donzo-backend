package com.doneasy.don.domain.campaign;

import java.time.LocalDateTime;

public class CampaignProposal {

    private Long id;
    private String how_to_use_first;
    private String how_to_use_second;
    private String how_to_use_third;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private int goal;
    private String word;
    private CampaignProposalStatus status;
    private LocalDateTime created_date;
    private Long organization_id;
}
