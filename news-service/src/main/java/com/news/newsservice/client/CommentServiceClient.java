package com.news.newsservice.client;

import com.news.newsservice.dto.CommentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="comment-service", path = "/v1/comment")
public interface CommentServiceClient {
    @GetMapping("/news/{newsId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsByNewsId(@PathVariable("newsId") String newsId);
}
