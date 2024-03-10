package com.news.newsservice.dto;

import com.news.newsservice.model.News;

import java.util.List;

public class NewsAndCommentsDto {
    NewsResponse newsResponse;
    List<CommentResponse> commentResponseList;

    public NewsAndCommentsDto() {
    }

    public NewsAndCommentsDto(NewsResponse newsResponse, List<CommentResponse> commentResponseList) {
        this.newsResponse = newsResponse;
        this.commentResponseList = commentResponseList;
    }

    public NewsResponse getNewsResponse() {
        return newsResponse;
    }

    public void setNewsResponse(NewsResponse newsResponse) {
        this.newsResponse = newsResponse;
    }

    public List<CommentResponse> getCommentResponseList() {
        return commentResponseList;
    }

    public void setCommentResponseList(List<CommentResponse> commentResponseList) {
        this.commentResponseList = commentResponseList;
    }
}
