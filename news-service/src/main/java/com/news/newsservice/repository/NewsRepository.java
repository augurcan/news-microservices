package com.news.newsservice.repository;

import com.news.newsservice.model.News;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NewsRepository extends MongoRepository<News,String> {
}
