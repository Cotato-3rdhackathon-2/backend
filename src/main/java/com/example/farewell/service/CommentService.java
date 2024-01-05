package com.example.farewell.service;

import com.example.farewell.domain.dto.comment.CommentDto;
import com.example.farewell.domain.dto.comment.CommentLikeResponse;
import com.example.farewell.domain.entity.Comment;
import com.example.farewell.domain.entity.CommentLike;
import com.example.farewell.domain.entity.Post;
import com.example.farewell.repository.CommentLikeRepository;
import com.example.farewell.repository.CommentRepository;
import com.example.farewell.repository.PostRepository;
import com.example.farewell.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Transactional
    public Comment createComment(Long postId, CommentDto commentDto) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            Comment comment = Comment.builder()
                    .content(commentDto.getContent())
                    .createdAt(LocalDateTime.now())
                    .post(post)
                    .user(userRepository.findById(commentDto.getUserId()).orElseThrow())
                    .build();
            return commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException("게시물을 찾을 수 없습니다.");
        }
    }

    @Transactional
    public CommentLikeResponse likeComment(Long commentId, Long userId) {
        Optional<CommentLike> commentLike = commentLikeRepository.findByCommentIdAndUser_Id(commentId, userId);
        boolean isLiked;
        if (commentLike.isPresent()) {
            commentLikeRepository.delete(commentLike.get());
            isLiked = false;
        } else {
            Comment comment = commentRepository.findById(commentId).get();
            commentLikeRepository.save(CommentLike.builder()
                    .user(userRepository.findById(userId).get())
                    .comment(comment)
                    .post(comment.getPost())
                    .build());
            isLiked = true;
        }
        return new CommentLikeResponse(isLiked);
    }
}
