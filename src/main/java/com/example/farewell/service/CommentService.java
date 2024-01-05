package com.example.farewell.service;

import com.example.farewell.domain.entity.Comment;
import com.example.farewell.domain.entity.Post;
import com.example.farewell.domain.entity.User;
import com.example.farewell.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostService postService, UserService userService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService = userService;
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public Comment createComment(Long postId, String content, Long userId) {
        Post post = postService.getPostById(postId).orElseThrow(() -> new EntityNotFoundException("게시물을 찾을 수 없습니다."));
        User user = userService.getUserById(userId).orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .content(content)
                .createdAt(LocalDateTime.now())
                .post(post)
                .user(user)
                .build();

        return commentRepository.save(comment);
    }
}
