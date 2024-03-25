package com.news.newsservice.service;

import com.news.newsservice.client.CommentServiceClient;
import com.news.newsservice.dto.CommentRequest;
import com.news.newsservice.dto.CommentResponse;
import com.news.newsservice.dto.NewsAndCommentsDto;
import com.news.newsservice.dto.NewsRequest;
import com.news.newsservice.dto.NewsResponse;
import com.news.newsservice.dto.converter.NewsDtoConverter;
import com.news.newsservice.exception.ResourceNotFoundException;
import com.news.newsservice.model.News;
import com.news.newsservice.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsDtoConverter newsDtoConverter;
    private final CommentServiceClient commentServiceClient;

    public NewsService(NewsRepository newsRepository, NewsDtoConverter newsDtoConverter,
            CommentServiceClient commentServiceClient) {
        this.newsRepository = newsRepository;
        this.newsDtoConverter = newsDtoConverter;
        this.commentServiceClient = commentServiceClient;
    }

    public NewsAndCommentsDto getNewsById(String newsId) {
        return new NewsAndCommentsDto(newsDtoConverter.convertToDto(getNewsByIdInDatabase(newsId)),
                commentServiceClient.getAllCommentsByNewsId(newsId).getBody());
    }

    public List<NewsResponse> getAllNews() {
        return newsRepository.findAll()
                .stream()
                .map(newsDtoConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public NewsResponse addNews(NewsRequest newsRequest) {
        News news = new News();
        news.setAuthor(newsRequest.getAuthor());
        news.setCreatedAt(LocalDateTime.now());
        news.setTitle(newsRequest.getTitle());
        news.setContent(newsRequest.getContent());
        news.setCategory(newsRequest.getCategory());
        NewsResponse savedNews = newsDtoConverter.convertToDto(news);
        newsRepository.save(news);
        return savedNews;
    }

    public void deleteNewsById(String newsId) {
        newsRepository.delete(getNewsByIdInDatabase(newsId));
    }

    public NewsResponse updateNewsById(NewsRequest newsRequest, String newsId) {
        News news = getNewsByIdInDatabase(newsId);
        if (!newsRequest.getAuthor().equals(news.getAuthor()))
            throw new IllegalArgumentException("Invalid author for news update");
        news.setTitle(newsRequest.getTitle());
        news.setContent(newsRequest.getContent());
        news.setCategory(newsRequest.getCategory());
        news.setUpdatedAt(LocalDateTime.now());
        newsRepository.save(news);
        return newsDtoConverter.convertToDto(news);
    }

    public CommentResponse addCommentToNews(CommentRequest commentRequest, String newsId) {
        return commentServiceClient.addCommentToNews(commentRequest, newsId).getBody();
    }

    private News getNewsByIdInDatabase(String newsId) {
        return newsRepository.findById(newsId)
                .orElseThrow(() -> new ResourceNotFoundException("News", "Id", newsId));
    }
}
