package com.example.farewell.controller;

import com.example.farewell.domain.dto.ResponseDto;
import com.example.farewell.domain.dto.comment.CommentWriteRequest;
import com.example.farewell.domain.dto.comment.CommentWriteResponse;
import com.example.farewell.domain.dto.comment.CommentDto;
import com.example.farewell.domain.dto.comment.CommentLikeResponse;
import com.example.farewell.domain.entity.Comment;
import com.example.farewell.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{postId}")
    public ResponseEntity<ResponseDto<List<Comment>>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        ResponseDto<List<Comment>> responseDto = ResponseDto.success("댓글 조회 완료", comments);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<ResponseDto<CommentWriteResponse>> createComment(
            @PathVariable Long postId,
            @RequestBody CommentWriteRequest commentWriteRequest) {
        CommentDto commentDto = new CommentDto(commentWriteRequest.getContent(), commentWriteRequest.getUserId());
        Comment comment = commentService.createComment(postId, commentDto);
        CommentWriteResponse response = new CommentWriteResponse(comment.getId());
        ResponseDto<CommentWriteResponse> responseDto = ResponseDto.success("댓글 작성 완료", response);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


    @GetMapping("/like")
    public ResponseEntity<ResponseDto<CommentLikeResponse>> likeComment(@RequestParam Long commentId, @RequestParam Long userId) {
        CommentLikeResponse response = commentService.likeComment(commentId, userId);
        ResponseDto<CommentLikeResponse> responseDto = ResponseDto.success("댓글 좋아요/해제 완료", response);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
