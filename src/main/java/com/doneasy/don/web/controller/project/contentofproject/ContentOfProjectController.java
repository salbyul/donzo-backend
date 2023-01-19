package com.doneasy.don.web.controller.project.contentofproject;

import com.doneasy.don.domain.project.ContentOfProject;
import com.doneasy.don.dto.project.contentofproject.ContentOfProjectShowDto;
import com.doneasy.don.repository.project.ContentOfProjectRepository;
import com.doneasy.don.service.project.contentofproject.ContentOfProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/content-of-project")
public class ContentOfProjectController {

    private final ContentOfProjectRepository contentOfProjectRepository;
    private final ContentOfProjectService contentOfProjectService;

    @PostMapping("/get-content")
    public ResponseEntity<List<ContentOfProject>> getContent(HttpServletRequest req, HttpServletResponse res, Long id) throws IOException {
//        System.out.println(id);
        List<ContentOfProject> findContent = contentOfProjectRepository.findAllByProjectId(id);
        log.info("size: {}", findContent.size());

        List<String> imageName = new ArrayList<>();
        for (int i = 0; i < findContent.size(); i++) {
            imageName.add(findContent.get(i).getImage_name());
        }

        List<byte[]> array = contentOfProjectService.getByteArray(imageName);
        List<ContentOfProjectShowDto> list = new ArrayList<>();
        for (ContentOfProject ofProject : findContent) {
            list.add(new ContentOfProjectShowDto(ofProject.getId(), ofProject.getSubtitle(), ofProject.getContent(), array.get(findContent.indexOf(ofProject)), ofProject.getOrder_num()));
        }
        return new ResponseEntity(list, HttpStatus.OK);
    }

}
