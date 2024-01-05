package com.example.farewell.controller;

import com.example.farewell.domain.dto.ResponseDto;
import com.example.farewell.domain.dto.post.PostWriteRequest;
import com.example.farewell.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.FileNameMap;

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
}
