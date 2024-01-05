package com.example.farewell.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "user_id")
    private Long id;
    private String kakaoNickname;
    private String kakaoId;
    private String email;
    private String nickname;

    @OneToMany(mappedBy = "comment")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "comment_like")
    private List<CommentLike> commentLikes = new ArrayList<>();

}
