package com.news.commentservice.service;

import com.news.commentservice.dto.CommentRequest;
import com.news.commentservice.dto.CommentResponse;
import com.news.commentservice.dto.converter.CommentDtoConverter;
import com.news.commentservice.exception.ResourceNotFoundException;
import com.news.commentservice.model.Comment;
import com.news.commentservice.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentDtoConverter commentDtoConverter;

    public CommentService(CommentRepository commentRepository, CommentDtoConverter commentDtoConverter) {
        this.commentRepository = commentRepository;
        this.commentDtoConverter = commentDtoConverter;
    }
    public void deleteComment(String commentId){
        commentRepository.delete(getCommentByIdInDatabase(commentId));
    }
    public CommentResponse addCommentToNews(CommentRequest commentRequest){
        Comment comment = new Comment();
        comment.setAuthor(commentRequest.getAuthor());
        comment.setNewsId(comment.getNewsId());
        comment.setContent(commentRequest.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        Comment savedComment =commentRepository.save(comment);
        return commentDtoConverter.convertToDto(savedComment);
    }
    public List<CommentResponse> getCommentsByNewsId(String newsId){
        return commentRepository.findAll()
                .stream()
                .map(commentDtoConverter::convertToDto)
                .filter(comment -> comment.getNewsId().equals(newsId))
                .collect(Collectors.toList());
    }
    public CommentResponse updateComment(CommentRequest commentRequest,String commentId){
        Comment comment = getCommentByIdInDatabase(commentId);
        if (!commentRequest.getAuthor().equals(comment.getAuthor()))
            throw new IllegalArgumentException("Invalid author for comment update");
        comment.setContent(commentRequest.getContent());
        comment.setUpdatedAt(LocalDateTime.now());
        commentRepository.save(comment);
        return commentDtoConverter.convertToDto(comment);
    }
    private Comment getCommentByIdInDatabase(String commentId){
        return commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Comment","Id",commentId));
    }
}
