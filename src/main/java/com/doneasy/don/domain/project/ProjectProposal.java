package com.doneasy.don.domain.project;

import com.doneasy.don.dto.project.projectproposal.ProjectProposalSaveDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProjectProposal {

    private Long id;
    private Target category;
    private LocalDate deadline;
    private LocalDate service_start_date;
    private LocalDate service_end_date;
    private String title;
    private Integer target_price;
    private ProjectProposalStatus status;
    private LocalDateTime created_date;
    private Long organization_id;

    public static ProjectProposal getInstance(ProjectProposalSaveDto projectProposalSaveDto, Long organization_id){
        String category = projectProposalSaveDto.getCategory();
        Target a;
        if (category.equals("seniorcitizen")) a = Target.ELDER_PEOPLE;
        else if (category.equals("children")) a= Target.CHILDREN;
        else if (category.equals("youth"))  a=Target.TEENAGER;
        else if (category.equals("environment")) a=Target.ENVIRONMENT;
        else if (category.equals("disabled")) a=Target.THE_DISABLED;
        else a=Target.SOCIETY;

        String deadline1 = projectProposalSaveDto.getDeadline();
        int year = Integer.parseInt(deadline1.substring(0, 4));
        int month = Integer.parseInt(deadline1.substring(5, 7));
        int day = Integer.parseInt(deadline1.substring(8));
        LocalDate date = LocalDate.of(year, month, day);

        String start = projectProposalSaveDto.getService_start_date();
        int year1 = Integer.parseInt(start.substring(0, 4));
        int month1 = Integer.parseInt(start.substring(5, 7));
        int day1 = Integer.parseInt(start.substring(8));
        LocalDate date1 = LocalDate.of(year1, month1, day1);

        String end = projectProposalSaveDto.getService_end_date();
        int year2 = Integer.parseInt(end.substring(0, 4));
        int month2 = Integer.parseInt(end.substring(5, 7));
        int day2 = Integer.parseInt(end.substring(8));
        LocalDate date2 = LocalDate.of(year2, month2, day2);

        return new ProjectProposal(null,a,date,date1,date2, projectProposalSaveDto.getTitle(), projectProposalSaveDto.getTarget_price(),ProjectProposalStatus.WAIT,LocalDateTime.now(),organization_id);



    }
}
