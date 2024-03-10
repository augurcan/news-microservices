package com.news.commentservice.controller;

import com.news.commentservice.dto.CommentRequest;
import com.news.commentservice.dto.CommentResponse;
import com.news.commentservice.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/comment")
public class CommentController {
    Logger logger = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;
    private final Environment environment;

    public CommentController(CommentService commentService, Environment environment) {
        this.commentService = commentService;
        this.environment = environment;
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") String commentId){
        commentService.deleteComment(commentId);
        logger.info("Comment delete request on port number " + environment.getProperty("server.port"));
        return ResponseEntity.ok("Comment deleted.");
    }
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@RequestBody CommentRequest commentRequest,
                                                         @PathVariable("commentId") String commentId){
        logger.info("Comment update request on port number " + environment.getProperty("server.port"));
        return ResponseEntity.ok(commentService.updateComment(commentRequest,commentId));
    }
    @PostMapping
    public ResponseEntity<CommentResponse> addCommentToNews(@RequestBody CommentRequest commentRequest){
        logger.info("Comment add request on port number " + environment.getProperty("server.port"));
        return new ResponseEntity<>(commentService.addCommentToNews(commentRequest), HttpStatus.CREATED);
    }
    @GetMapping("/news/{newsId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsByNewsId(@PathVariable("newsId") String newsId){
        logger.info("Comment get request on port number " + environment.getProperty("server.port"));
        return ResponseEntity.ok(commentService.getCommentsByNewsId(newsId));
    }
}
