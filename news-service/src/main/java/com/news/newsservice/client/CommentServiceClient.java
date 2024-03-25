package com.news.newsservice.client;

import com.news.newsservice.dto.CommentRequest;
import com.news.newsservice.dto.CommentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "comment-service", path = "/v1/comment")
public interface CommentServiceClient {
    @PostMapping
    public ResponseEntity<CommentResponse> addCommentToNews(@RequestBody CommentRequest commentRequest,
            @RequestParam("newsId") String newsId);

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllCommentsByNewsId(@RequestParam("newsId") String newsId);
}
