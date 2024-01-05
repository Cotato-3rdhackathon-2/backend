package com.example.farewell.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long id;
    private LocalDateTime createdAt;
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment")
    private List<CommentLike> commentLikes = new ArrayList<>();

}
