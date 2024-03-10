package com.news.commentservice.dto.converter;

import com.news.commentservice.dto.CommentResponse;
import com.news.commentservice.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoConverter {
    public CommentResponse convertToDto(Comment comment){
        return new CommentResponse(comment.getId(),comment.getContent(),
                comment.getCreatedAt(),comment.getUpdatedAt(),
                comment.getAuthor(),comment.getNewsId());
    }
}
