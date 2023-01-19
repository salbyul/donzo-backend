package com.doneasy.don.web.controller.project.projectlist;

import com.doneasy.don.dto.project.projectlist.ProjectListShowDto;
import com.doneasy.don.service.project.projectlist.ProjectListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/project-board")
@RequiredArgsConstructor
public class ProjectListController {

    private final ProjectListService projectListService;
    @PostMapping("/find-all-donate/{tag}")
    public ResponseEntity getDonatingProjectBoard(@PathVariable String tag) throws IOException {
//        System.out.println("tag:" + tag);

        List<ProjectListShowDto> list = projectListService.findByTagDonating(tag);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping("/find-all-donated/{tag}")
    public ResponseEntity getDonatedProjectBoard(@PathVariable String tag) throws IOException {
        List<ProjectListShowDto> list = projectListService.findByTagDonated(tag);

        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProjectListShowDto>> getSearch(@RequestParam(required = false, name = "s") String search) throws IOException {
        if(search == null) {
                List<ProjectListShowDto> findAll = projectListService.findAll();
                return new ResponseEntity(findAll, HttpStatus.OK);
        }
        List<ProjectListShowDto> list = projectListService.findByTitle(search);
//        System.out.println(list);
        return new ResponseEntity(list, HttpStatus.OK);
    }
}
