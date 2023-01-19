package com.doneasy.don.service.project.projectlist;

import com.doneasy.don.domain.project.Project;
import com.doneasy.don.domain.project.ProjectProposal;
import com.doneasy.don.domain.project.ProjectReview;
import com.doneasy.don.domain.project.ProjectStatus;
import com.doneasy.don.domain.user.Organization;
import com.doneasy.don.dto.project.projectlist.ProjectListShowDto;
import com.doneasy.don.repository.project.*;
import com.doneasy.don.repository.user.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectListService {

    private final ProjectRepository projectRepository;
    private final ProjectProposalRepository projectProposalRepository;
    private final OrganizationRepository organizationRepository;
    private final SupportOfProjectRepository supportOfProjectRepository;
    private final CommentOfProjectRepository commentOfProjectRepository;
    private final DonationOfProjectRepository donationOfProjectRepository;
    private final ContentOfProjectRepository contentOfProjectRepository;
    private final ProjectReviewRepository projectReviewRepository;
    private final ContentOfProjectReviewRepository contentOfProjectReviewRepository;


    @Value("${admin.image.dir}")
    private String path;

    public List<ProjectListShowDto> findByTagDonating(String tag) throws IOException {
        List<ProjectProposal> projectProposalList;

        // 태크에 맞는 모든 제안서 가져오기
        if (tag.equals("ALL")) {
            projectProposalList = projectProposalRepository.findByAllTag();
        } else {
            projectProposalList = projectProposalRepository.findByTag(tag);
        }

        List<ProjectListShowDto> list = new ArrayList<>();
        for (ProjectProposal projectProposal : projectProposalList) {
            Optional<Project> optionalProject = Optional.ofNullable(projectRepository.findByProjectProposalId(projectProposal.getId()));
            if (optionalProject.isEmpty()) {
                continue;
            }
            Project project = optionalProject.get();
            if (project.getStatus() == ProjectStatus.DONE) continue;
            Organization organization = organizationRepository.findById(project.getOrganization_id());
            Optional<Integer> support = Optional.ofNullable(supportOfProjectRepository.findCountByProjectId(project.getId()));
            Optional<Integer> comment = Optional.ofNullable(commentOfProjectRepository.findByProjectProposalId(project.getId()));
            Optional<Integer> donation = Optional.ofNullable(donationOfProjectRepository.findSumPriceByProjectId(project.getId()));
            String imageName = contentOfProjectRepository.findImageNameByProjectProposalId(projectProposal.getId());

            list.add(new ProjectListShowDto(project.getId(), projectProposal.getTitle(), donation.orElse(0) + (support.orElse(0) + comment.orElse(0)) * 100, projectProposal.getTarget_price(), organization.getNickname(), getImage(imageName), projectProposal.getDeadline().toString(), project.getStatus().toString()));
        }
        return list;
    }

    public List<ProjectListShowDto> findByTagDonated(String tag) throws IOException {
        List<ProjectProposal> projectProposalList;

        // 태그에 맞는 제안서 가져오기
        if (tag.equals("ALL")) {
            projectProposalList = projectProposalRepository.findByAllTagDonated();
        } else {
            projectProposalList = projectProposalRepository.findByTagDonated(tag);
        }

        List<ProjectListShowDto> list = new ArrayList<>();
        for (ProjectProposal projectProposal : projectProposalList) {
            Project project = projectRepository.findByProjectProposalId(projectProposal.getId());

            if (project == null) continue;

            ProjectReview projectReview = projectReviewRepository.findProjectReviewByProjectId(project.getId());

            if (projectReview == null) continue;

            Organization organization = organizationRepository.findById(projectReview.getOrganization_id());

            // 기부금액 가져오기
            Optional<Integer> support = Optional.ofNullable(supportOfProjectRepository.findCountByProjectId(project.getId()));
            Optional<Integer> comment = Optional.ofNullable(commentOfProjectRepository.findByProjectProposalId(project.getId()));
            Optional<Integer> donation = Optional.ofNullable(donationOfProjectRepository.findSumPriceByProjectId(project.getId()));

            String imageName = contentOfProjectReviewRepository.findImageNameByProjectReviewId(projectReview.getId());
            log.info("projectProposalId: {}", projectProposal.getId());

            list.add(new ProjectListShowDto(project.getId(), projectReview.getTitle(), donation.orElse(0) + (support.orElse(0) + comment.orElse(0)) * 100, projectProposal.getTarget_price(), organization.getNickname(), getImage(imageName), projectProposal.getDeadline().toString(), project.getStatus().toString()));

        }
        return list;
    }


    // TODO path 바꿔야 함
    private byte[] getImage(String image_name) throws IOException {
        if (image_name == null) return null;
        File file = new File(path + image_name);
        return Files.readAllBytes(file.toPath());
    }

    public List<ProjectListShowDto> findByTitle(String search) throws IOException {
        // 모금 중 검색
        List<ProjectProposal> projectProposalList = projectProposalRepository.findByTitle(search);
        List<ProjectReview> projectReviewList = projectReviewRepository.findByTitle(search);
        log.info("projectproposal SIZE: {}", projectProposalList.size());
        List<ProjectListShowDto> list = new ArrayList<>();

        for (ProjectReview projectReview : projectReviewList) {
            Project findProject = projectRepository.findById(projectReview.getProject_id());
            ProjectProposal findProjectProposal = projectProposalRepository.findById(findProject.getProjectProposal_id());
            Organization findOrganization = organizationRepository.findById(findProject.getOrganization_id());

            String imageName = contentOfProjectReviewRepository.findImageNameByProjectReviewId(projectReview.getId());

            list.add(new ProjectListShowDto(projectReview.getProject_id(), projectReview.getTitle(), getDonationResult(projectReview.getProject_id()), findProjectProposal.getTarget_price(), findOrganization.getNickname(), getImage(imageName), findProjectProposal.getService_end_date().toString(), findProject.getStatus().toString()));
        }

        for (ProjectProposal projectProposal : projectProposalList) {

            Project project = projectRepository.findByProjectProposalId(projectProposal.getId());

            if (project == null) continue;

            Organization organization = organizationRepository.findById(project.getOrganization_id());

            // 금액
            Integer donationResult = getDonationResult(project.getId());

            String imageName;

            imageName = contentOfProjectRepository.findImageNameByProjectProposalId(projectProposal.getId());
            log.info("imageName: {}", imageName);
            list.add(new ProjectListShowDto(project.getId(), projectProposal.getTitle(), donationResult, projectProposal.getTarget_price(), organization.getNickname(), getImage(imageName), projectProposal.getDeadline().toString(), project.getStatus().toString()));


        }
        return list;
    }

    public List<ProjectListShowDto> findAll() throws IOException {
        List<ProjectProposal> projectProposalList = projectProposalRepository.findAll();
        List<ProjectListShowDto> list = new ArrayList<>();
        for (ProjectProposal projectProposal : projectProposalList) {
            Project project = projectRepository.findByProjectProposalId(projectProposal.getId());
            Organization organization = organizationRepository.findById(project.getOrganization_id());
            Integer support = supportOfProjectRepository.findCountByProjectId(project.getId());
            Integer comment = commentOfProjectRepository.findByProjectProposalId(project.getId());
            Integer donation = donationOfProjectRepository.findSumPriceByProjectId(project.getId());
            String imageName = contentOfProjectRepository.findImageNameByProjectProposalId(projectProposal.getId());

            list.add(new ProjectListShowDto(project.getId(), projectProposal.getTitle(), donation + (support + comment) * 100, projectProposal.getTarget_price(), organization.getNickname(), getImage(imageName), projectProposal.getDeadline().toString(), project.getStatus().toString()));
        }
        return list;
    }

    public Integer getDonationResult(Long projectId) {

        Optional<Integer> support = Optional.ofNullable(supportOfProjectRepository.findCountByProjectId(projectId));
        Optional<Integer> comment = Optional.ofNullable(commentOfProjectRepository.findByProjectProposalId(projectId));
        Optional<Integer> donation = Optional.ofNullable(donationOfProjectRepository.findSumPriceByProjectId(projectId));
        return donation.orElse(0) + (support.orElse(0) * 100) + (comment.orElse(0) * 100);
    }
}
