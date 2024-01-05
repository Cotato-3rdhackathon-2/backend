package com.example.farewell.repository;

import com.example.farewell.domain.entity.CommentLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentIdAndUser_Id(Long postId, Long userId);
}
