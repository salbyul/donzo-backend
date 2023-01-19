package com.doneasy.don.web.controller.admin;

import com.doneasy.don.dto.admin.comment.CommentListDto;
import com.doneasy.don.repository.project.CommentOfProjectRepository;
import com.doneasy.don.service.admin.comment.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCommentController {

    private final CommentService commentService;
    private final CommentOfProjectRepository commentOfProjectRepository;

    @GetMapping("/comment")
    public ResponseEntity<List<CommentListDto>> getAll() {
        List<CommentListDto> all = commentService.findAll();
        return new ResponseEntity(all, HttpStatus.OK);
    }

    @PostMapping("/comment/blind")
    public ResponseEntity statusToBlind(@RequestBody HashMap<String, Long> map) {
        Long id = map.get("id");
        commentOfProjectRepository.statusToBlind(id);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/comment/active")
    public ResponseEntity statusSToActive(@RequestBody HashMap<String, Long> map) {
        Long id = map.get("id");
        commentOfProjectRepository.statusToActive(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
