package com.news.newsservice.dto.converter;

import com.news.newsservice.dto.NewsResponse;
import com.news.newsservice.model.News;
import org.springframework.stereotype.Component;

@Component
public class NewsDtoConverter {
    public NewsResponse convertToDto(News news){
        return new NewsResponse(news.getId(), news.getTitle(), news.getContent(),
                news.getCategory(),news.getAuthor(),news.getCreatedAt(),
                news.getUpdatedAt());
    }
}
