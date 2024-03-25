package com.news.newsservice.controller;

import com.news.newsservice.dto.CommentRequest;
import com.news.newsservice.dto.CommentResponse;
import com.news.newsservice.dto.NewsAndCommentsDto;
import com.news.newsservice.dto.NewsRequest;
import com.news.newsservice.dto.NewsResponse;
import com.news.newsservice.service.NewsService;
import feign.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/news")
public class NewsController {
    Logger logger = LoggerFactory.getLogger(NewsController.class);
    private final NewsService newsService;
    private final Environment environment;

    public NewsController(NewsService newsService, Environment environment) {
        this.newsService = newsService;
        this.environment = environment;
    }

    @GetMapping("/{newsId}")
    public ResponseEntity<NewsAndCommentsDto> getNewsById(@PathVariable("newsId") String newsId) {
        logger.info("News get request on port number " + environment.getProperty("server.port"));
        return ResponseEntity.ok(newsService.getNewsById(newsId));
    }

    @GetMapping
    public ResponseEntity<List<NewsResponse>> getAllNews() {
        logger.info("News get request on port number " + environment.getProperty("server.port"));
        return ResponseEntity.ok(newsService.getAllNews());
    }

    @DeleteMapping("/{newsId}")
    public ResponseEntity<String> deleteNews(@PathVariable("newsId") String newsId) {
        newsService.deleteNewsById(newsId);
        logger.info("News delete request on port number " + environment.getProperty("server.port"));
        return ResponseEntity.ok("News deleted");
    }

    @PutMapping("/{newsId}")
    public ResponseEntity<NewsResponse> updateNews(@RequestBody NewsRequest newsRequest,
            @PathVariable("newsId") String newsId) {
        logger.info("News update request on port number " + environment.getProperty("server.port"));
        return ResponseEntity.ok(newsService.updateNewsById(newsRequest, newsId));
    }

    @PostMapping
    public ResponseEntity<NewsResponse> addNews(@RequestBody NewsRequest newsRequest) {
        logger.info("News add request on port number " + environment.getProperty("server.port"));
        return new ResponseEntity<>(newsService.addNews(newsRequest), HttpStatus.CREATED);
    }

    @PostMapping("/{newsId}/comment")
    public ResponseEntity<CommentResponse> addCommentToNews(@RequestBody CommentRequest commentRequest,
            @PathVariable("newsId") String newsId) {
        logger.info("Comment post request on port number " + environment.getProperty("server.port"));
        CommentResponse addedComment = newsService.addCommentToNews(commentRequest, newsId);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedComment);
    }
}
