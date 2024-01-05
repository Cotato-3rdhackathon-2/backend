package com.example.farewell.controller;

import com.example.farewell.domain.dto.ResponseDto;
import com.example.farewell.domain.dto.oauth.UserId;
import com.example.farewell.domain.dto.post.PostWriteRequest;
import com.example.farewell.domain.entity.Post;
import com.example.farewell.jwt.TokenProvider;
import com.example.farewell.service.PostService;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    private final TokenProvider tokenProvider;

    @PostMapping("")
    public ResponseDto<?> writePost(@RequestBody PostWriteRequest postWriteRequest, HttpServletRequest request) {
        System.out.println(postWriteRequest.getTitle());
        Long userId = tokenProvider.getUserId(extractTokenFromHeader(request));
        return ResponseDto.success("게시글 생성 완료", postService.createPost(postWriteRequest, userId));
    }

    @GetMapping
    public ResponseDto<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseDto.success("조회 완료", posts);
    }

    @GetMapping("/{postId}")
    public ResponseDto<Post> getPostById(@PathVariable Long postId) {
        return ResponseDto.success("조회 완료", postService.getPostById(postId).get());
    }

    @GetMapping("/likes")
    public ResponseDto<?> likePost(@RequestParam Long postId, HttpServletRequest request) {
        Long userId = tokenProvider.getUserId(extractTokenFromHeader(request));
        return ResponseDto.success("좋아요/해제 완료", postService.likePost(postId, userId));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Post>> getPostsByCategory(@PathVariable String category) {
        List<Post> posts = postService.getPostsByCategory(category);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    private String extractTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
