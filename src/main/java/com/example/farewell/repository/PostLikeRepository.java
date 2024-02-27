package com.example.farewell.repository;

import com.example.farewell.domain.entity.PostLike;
import com.example.farewell.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostIdAndUser(Long postId, User user);
}
