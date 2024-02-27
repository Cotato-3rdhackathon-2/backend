package com.example.farewell.controller;

import com.example.farewell.domain.dto.ResponseDto;
import com.example.farewell.domain.dto.comment.CommentWriteRequest;
import com.example.farewell.domain.dto.comment.CommentWriteResponse;
import com.example.farewell.domain.dto.comment.CommentDto;
import com.example.farewell.domain.dto.comment.CommentLikeResponse;
import com.example.farewell.domain.entity.Comment;
import com.example.farewell.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{postId}")
    public ResponseDto<List<Comment>> getCommentsByPostId(@PathVariable("postId") Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseDto.success("댓글 조회 완료", comments);
    }

    @PostMapping("/{postId}")
    public ResponseDto<CommentWriteResponse> createComment(@PathVariable("postId") Long postId, @RequestBody CommentWriteRequest commentWriteRequest) {
        CommentDto commentDto = new CommentDto(commentWriteRequest.getContent(), commentWriteRequest.getUserId());
        Comment comment = commentService.createComment(postId, commentDto);
        CommentWriteResponse response = new CommentWriteResponse(comment.getId());
        return ResponseDto.success("댓글 작성 완료", response);
    }

    @GetMapping("/likes")
    public ResponseDto<CommentLikeResponse> likeComment(@RequestParam("commentId") Long commentId, @RequestParam("userId") Long userId) {
        CommentLikeResponse response = commentService.likeComment(commentId, userId);
        return ResponseDto.success("댓글 좋아요/해제 완료", response);
    }
}
