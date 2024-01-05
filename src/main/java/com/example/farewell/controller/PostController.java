package com.example.farewell.controller;

import com.example.farewell.domain.dto.ResponseDto;
import com.example.farewell.domain.dto.post.PostWriteRequest;
import com.example.farewell.domain.entity.Post;
import com.example.farewell.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @PostMapping("")
    public ResponseDto<?> writePost(@RequestBody PostWriteRequest postWriteRequest) {
        System.out.println(postWriteRequest.getTitle());
        return ResponseDto.success("게시글 생성 완료", postService.createPost(postWriteRequest));
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
    public ResponseDto<?> likePost(@RequestParam Long postId, @RequestParam Long userId){
        return ResponseDto.success("좋아요/해제 완료", postService.likePost(postId,userId));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Post>> getPostsByCategory(@PathVariable String category) {
        List<Post> posts = postService.getPostsByCategory(category);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
