package com.example.farewell.service;

import com.example.farewell.domain.dto.post.PostLikeResponse;
import com.example.farewell.domain.dto.post.PostWriteRequest;
import com.example.farewell.domain.dto.post.PostWriteResponse;
import com.example.farewell.domain.entity.Post;
import com.example.farewell.domain.entity.PostLike;
import com.example.farewell.repository.PostLikeRepository;
import com.example.farewell.repository.PostRepository;
import com.example.farewell.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.net.FileNameMap;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;

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


    @Transactional
    public PostLikeResponse likePost(Long postId, Long userId) {
        Optional<PostLike> postLike = postLikeRepository.findByPostIdAndUser_Id(postId, userId);
        boolean isLiked;
        if (postLike.isPresent()) {
            postLikeRepository.delete(postLike.get());
            isLiked = false;
        } else {
            postLikeRepository.save(PostLike.builder()
                    .user(userRepository.findById(userId).get())
                    .post(postRepository.findById(postId).get())
                    .build());
            isLiked = true;
        }
        return new PostLikeResponse(isLiked);
    }
    public List<Post> getPostsByCategory(String category) {return postRepository.findByCategory(category);}
}
