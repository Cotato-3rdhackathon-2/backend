package com.example.farewell.service;
import com.example.farewell.domain.dto.post.PostWriteRequest;
import com.example.farewell.domain.dto.post.PostWriteResponse;
import com.example.farewell.domain.entity.Post;
import com.example.farewell.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.net.FileNameMap;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostWriteResponse createPost(PostWriteRequest postWriteRequest) {
        System.out.println("postWriteRequest = " + postWriteRequest.getContent());
        Post savedPost = postRepository.save(Post.builder()
                .title(postWriteRequest.getTitle())
                .content(postWriteRequest.getContent())
                .category(postWriteRequest.getCategory())
                .createAt(LocalDateTime.now())
                .build());
        return new PostWriteResponse(savedPost.getId());

    }


    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }


}
